

```java
@Autowired
    private RedissonClient redissonClient;

    @Test
    public void test() {
        System.out.println("RedissonClient 实现类: " + redissonClient.getClass().getName());
        RRateLimiter rateLimiter = redissonClient.getRateLimiter("user");
        System.out.println("RRateLimiter 实现类: " + rateLimiter.getClass().getName());
        rateLimiter.trySetRate(RateType.OVERALL, 1L, 1L, RateIntervalUnit.SECONDS);
        System.out.println(rateLimiter.tryAcquire(1L));
    }
```

输出

```java
RedissonClient 实现类: org.redisson.Redisson
RRateLimiter 实现类: org.redisson.RedissonRateLimiter
true
```

![](https://cdn.nlark.com/yuque/0/2025/png/43087421/1739855281025-21bb9804-7a85-471b-a1ea-780f246b0953.png)

实际会返回一个 RedissonRateLimiter （RRateLimiter 实现类）

![](https://cdn.nlark.com/yuque/0/2025/png/43087421/1739855377897-d2ee8ff3-5f14-4f00-ba99-250aaa49e72b.png)

```java
    public boolean trySetRate(RateType type, long rate, long rateInterval, RateIntervalUnit unit) {
        return (Boolean)this.get(this.trySetRateAsync(type, rate, rateInterval, unit));
    }

    public RFuture<Boolean> trySetRateAsync(RateType type, long rate, long rateInterval, RateIntervalUnit unit) {
        return this.commandExecutor.evalWriteNoRetryAsync(this.getRawName(), LongCodec.INSTANCE, RedisCommands.EVAL_BOOLEAN, "redis.call('hsetnx', KEYS[1], 'rate', ARGV[1]);redis.call('hsetnx', KEYS[1], 'interval', ARGV[2]);return redis.call('hsetnx', KEYS[1], 'type', ARGV[3]);", Collections.singletonList(this.getRawName()), new Object[]{rate, unit.toMillis(rateInterval), type.ordinal()});
    }
```

把上面的返回单独抽出来看 

```java
return this.commandExecutor.evalWriteNoRetryAsync(
    this.getRawName(), 
    LongCodec.INSTANCE, 
    RedisCommands.EVAL_BOOLEAN, 
    "redis.call('hsetnx', KEYS[1], 'rate', ARGV[1]);"
    + "redis.call('hsetnx', KEYS[1], 'interval', ARGV[2]);"
    + "return redis.call('hsetnx', KEYS[1], 'type', ARGV[3]);",
    Collections.singletonList(this.getRawName()), 
    new Object[]{rate, unit.toMillis(rateInterval), type.ordinal()}
);
```

```lua
redis.call('hsetnx', KEYS[1], 'rate', ARGV[1]);
redis.call('hsetnx', KEYS[1], 'interval', ARGV[2]);
return redis.call('hsetnx', KEYS[1], 'type', ARGV[3]);
```

<font style="color:rgb(6, 6, 7);">传递给 Lua 脚本的参数：</font>

+ `ARGV[1]`<font style="color:rgb(6, 6, 7);">: 限流速率（</font>`rate`<font style="color:rgb(6, 6, 7);">）。</font>
+ `ARGV[2]`<font style="color:rgb(6, 6, 7);">: 限流间隔（以毫秒为单位，通过 </font>`unit.toMillis(rateInterval)`<font style="color:rgb(6, 6, 7);"> 转换）。</font>
+ `ARGV[3]`<font style="color:rgb(6, 6, 7);">: 限流类型（</font>`type.ordinal()`<font style="color:rgb(6, 6, 7);">，枚举类型转换为整型）</font>

![](https://cdn.nlark.com/yuque/0/2025/png/43087421/1739855455136-1801b3eb-309f-45bf-b2a9-2513a5667682.png)

![](https://cdn.nlark.com/yuque/0/2025/png/43087421/1739855465934-37f6c6b2-1968-4015-b9b2-28125a52c1e9.png)

```java
private <T> RFuture<T> tryAcquireAsync(RedisCommand<T> command, Long value) {
        byte[] random = this.getServiceManager().generateIdArray();
        return this.commandExecutor.evalWriteAsync(this.getRawName(), LongCodec.INSTANCE, command, "local rate = redis.call('hget', KEYS[1], 'rate');local interval = redis.call('hget', KEYS[1], 'interval');local type = redis.call('hget', KEYS[1], 'type');assert(rate ~= false and interval ~= false and type ~= false, 'RateLimiter is not initialized')local valueName = KEYS[2];local permitsName = KEYS[4];if type == '1' then valueName = KEYS[3];permitsName = KEYS[5];end;assert(tonumber(rate) >= tonumber(ARGV[1]), 'Requested permits amount could not exceed defined rate'); local currentValue = redis.call('get', valueName); local res;if currentValue ~= false then local expiredValues = redis.call('zrangebyscore', permitsName, 0, tonumber(ARGV[2]) - interval); local released = 0; for i, v in ipairs(expiredValues) do local random, permits = struct.unpack('Bc0I', v);released = released + permits;end; if released > 0 then redis.call('zremrangebyscore', permitsName, 0, tonumber(ARGV[2]) - interval); if tonumber(currentValue) + released > tonumber(rate) then currentValue = tonumber(rate) - redis.call('zcard', permitsName); else currentValue = tonumber(currentValue) + released; end; redis.call('set', valueName, currentValue);end;if tonumber(currentValue) < tonumber(ARGV[1]) then local firstValue = redis.call('zrange', permitsName, 0, 0, 'withscores'); res = 3 + interval - (tonumber(ARGV[2]) - tonumber(firstValue[2]));else redis.call('zadd', permitsName, ARGV[2], struct.pack('Bc0I', string.len(ARGV[3]), ARGV[3], ARGV[1])); redis.call('decrby', valueName, ARGV[1]); res = nil; end; else redis.call('set', valueName, rate); redis.call('zadd', permitsName, ARGV[2], struct.pack('Bc0I', string.len(ARGV[3]), ARGV[3], ARGV[1])); redis.call('decrby', valueName, ARGV[1]); res = nil; end;local ttl = redis.call('pttl', KEYS[1]); if ttl > 0 then redis.call('pexpire', valueName, ttl); redis.call('pexpire', permitsName, ttl); end; return res;", Arrays.asList(this.getRawName(), this.getValueName(), this.getClientValueName(), this.getPermitsName(), this.getClientPermitsName()), new Object[]{value, System.currentTimeMillis(), random});
    }
```

这个真是一坨啊

```java
return this.commandExecutor.evalWriteAsync(
    this.getRawName(),
    LongCodec.INSTANCE,
    command,
    luaScript,
    Arrays.asList(KEYS),
    new Object[] { ARGV }
);
```

```lua
local rate = redis.call('hget', KEYS[1], 'rate')
local interval = redis.call('hget', KEYS[1], 'interval')
local type = redis.call('hget', KEYS[1], 'type')

assert(rate ~= false and interval ~= false and type ~= false, 'RateLimiter is not initialized')

local valueName = KEYS[2]
local permitsName = KEYS[4]

if type == '1' then
    valueName = KEYS[3]
    permitsName = KEYS[5]
end

assert(tonumber(rate) >= tonumber(ARGV[1]), 'Requested permits amount could not exceed defined rate')

local currentValue = redis.call('get', valueName)
local res

if currentValue ~= false then
    local expiredValues = redis.call('zrangebyscore', permitsName, 0, tonumber(ARGV[2]) - interval)
    local released = 0

    for i, v in ipairs(expiredValues) do
        local random, permits = struct.unpack('Bc0I', v)
        released = released + permits
    end

    if released > 0 then
        redis.call('zremrangebyscore', permitsName, 0, tonumber(ARGV[2]) - interval)
        if tonumber(currentValue) + released > tonumber(rate) then
            currentValue = tonumber(rate) - redis.call('zcard', permitsName)
        else
            currentValue = tonumber(currentValue) + released
        end
        redis.call('set', valueName, currentValue)
    end

    if tonumber(currentValue) < tonumber(ARGV[1]) then
        local firstValue = redis.call('zrange', permitsName, 0, 0, 'withscores')
        res = 3 + interval - (tonumber(ARGV[2]) - tonumber(firstValue[2]))
    else
        redis.call('zadd', permitsName, ARGV[2], struct.pack('Bc0I', string.len(ARGV[3]), ARGV[3], ARGV[1]))
        redis.call('decrby', valueName, ARGV[1])
        res = nil
    end
else
    redis.call('set', valueName, rate)
    redis.call('zadd', permitsName, ARGV[2], struct.pack('Bc0I', string.len(ARGV[3]), ARGV[3], ARGV[1]))
    redis.call('decrby', valueName, ARGV[1])
    res = nil
end

local ttl = redis.call('pttl', KEYS[1])
if ttl > 0 then
    redis.call('pexpire', valueName, ttl)
    redis.call('pexpire', permitsName, ttl)
end

return res
```

1. **<font style="color:rgb(6, 6, 7);">获取限流器配置</font>**
    - <font style="color:rgb(6, 6, 7);">使用 </font>`hget`<font style="color:rgb(6, 6, 7);"> 获取限流器的配置值：</font>
        * `rate`<font style="color:rgb(6, 6, 7);">：限流速率（每单位时间最多允许的令牌数）。</font>
        * `interval`<font style="color:rgb(6, 6, 7);">：限流间隔（单位时间）。</font>
        * `type`<font style="color:rgb(6, 6, 7);">：限流类型（</font>`0`<font style="color:rgb(6, 6, 7);"> 或 </font>`1`<font style="color:rgb(6, 6, 7);">，可能表示全局限流或基于客户端限流）。</font>
2. **<font style="color:rgb(6, 6, 7);">断言检查</font>**
    - <font style="color:rgb(6, 6, 7);">确保限流器已初始化（</font>`rate`<font style="color:rgb(6, 6, 7);">、</font>`interval`<font style="color:rgb(6, 6, 7);"> 和 </font>`type`<font style="color:rgb(6, 6, 7);"> 不为 </font>`nil`<font style="color:rgb(6, 6, 7);">）。</font>
3. **<font style="color:rgb(6, 6, 7);">选择键名</font>**
    - <font style="color:rgb(6, 6, 7);">根据限流类型选择 </font>`valueName`<font style="color:rgb(6, 6, 7);"> 和 </font>`permitsName`<font style="color:rgb(6, 6, 7);">：</font>
        * `valueName`<font style="color:rgb(6, 6, 7);">：保存当前可用令牌数的键名。</font>
        * `permitsName`<font style="color:rgb(6, 6, 7);">：保存发放的令牌及其时间戳的有序集合的键名。</font>
4. **<font style="color:rgb(6, 6, 7);">检查请求是否合法</font>**
    - <font style="color:rgb(6, 6, 7);">确保请求的令牌数量（</font>`ARGV[1]`<font style="color:rgb(6, 6, 7);">）不超过限流器的速率（</font>`rate`<font style="color:rgb(6, 6, 7);">）。</font>
5. **<font style="color:rgb(6, 6, 7);">获取当前令牌数</font>**
    - <font style="color:rgb(6, 6, 7);">从 </font>`valueName`<font style="color:rgb(6, 6, 7);"> 中获取当前可用的令牌数（</font>`currentValue`<font style="color:rgb(6, 6, 7);">）。</font>
6. **<font style="color:rgb(6, 6, 7);">处理过期令牌</font>**
    - <font style="color:rgb(6, 6, 7);">使用 </font>`zrangebyscore`<font style="color:rgb(6, 6, 7);"> 扫描 </font>`permitsName`<font style="color:rgb(6, 6, 7);"> 中已过期的令牌（时间戳小于 </font>`ARGV[2] - interval`<font style="color:rgb(6, 6, 7);">）。</font>
    - <font style="color:rgb(6, 6, 7);">遍历过期的令牌并累加其数量（</font>`released`<font style="color:rgb(6, 6, 7);">）。</font>
    - <font style="color:rgb(6, 6, 7);">移除过期的令牌（</font>`zremrangebyscore`<font style="color:rgb(6, 6, 7);">）。</font>
    - <font style="color:rgb(6, 6, 7);">更新 </font>`currentValue`<font style="color:rgb(6, 6, 7);"> 并重新设置 </font>`valueName`<font style="color:rgb(6, 6, 7);">。</font>
7. **<font style="color:rgb(6, 6, 7);">发放令牌</font>**
    - <font style="color:rgb(6, 6, 7);">如果当前可用令牌数（</font>`currentValue`<font style="color:rgb(6, 6, 7);">）足够，给 </font>`permitsName`<font style="color:rgb(6, 6, 7);"> 添加新令牌（</font>`zadd`<font style="color:rgb(6, 6, 7);">），同时减少 </font>`valueName`<font style="color:rgb(6, 6, 7);"> 的值。</font>
    - <font style="color:rgb(6, 6, 7);">如果当前可用令牌数不足，计算需要等待的时间（</font>`res`<font style="color:rgb(6, 6, 7);">），并返回。</font>
8. **<font style="color:rgb(6, 6, 7);">设置过期时间</font>**
    - <font style="color:rgb(6, 6, 7);">获取限流器配置键（</font>`KEYS[1]`<font style="color:rgb(6, 6, 7);">）的过期时间（</font>`ttl`<font style="color:rgb(6, 6, 7);">）。</font>
    - <font style="color:rgb(6, 6, 7);">如果 </font>`ttl`<font style="color:rgb(6, 6, 7);"> 存在，则为 </font>`valueName`<font style="color:rgb(6, 6, 7);"> 和 </font>`permitsName`<font style="color:rgb(6, 6, 7);"> 设置相同的过期时间。</font>
9. **<font style="color:rgb(6, 6, 7);">返回结果</font>**
    - <font style="color:rgb(6, 6, 7);">如果无法获取令牌，返回等待时间（</font>`res`<font style="color:rgb(6, 6, 7);">）。</font>
    - <font style="color:rgb(6, 6, 7);">如果成功获取令牌，返回 </font>`nil`<font style="color:rgb(6, 6, 7);">。</font>

<font style="color:rgb(6, 6, 7);"></font>

