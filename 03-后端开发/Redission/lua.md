```java

import org.redisson.api.RedissonClient;
import org.redisson.api.RScript;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LuaScriptService {

    @Autowired
    private RedissonClient redissonClient;

    public Long executeLuaScript(String key, Long incrementValue) {
        // 获取 Lua 脚本
        RScript script = redissonClient.getScript();

        // 执行 Lua 脚本，返回结果
        return script.eval(RScript.Mode.READ_WRITE,
            "if redis.call('exists', KEYS[1]) == 1 then return redis.call('incrby', KEYS[1], ARGV[1]) else redis.call('set', KEYS[1], ARGV[1]) return ARGV[1] end",
            RScript.ReturnType.INTEGER, 
            java.util.Collections.singletonList(key), // KEYS[1]
            java.util.Collections.singletonList(String.valueOf(incrementValue)) // ARGV[1]
        );
    }
}

```

### 参数说明：
+ **RScript.Mode.READ_WRITE**：表示该脚本会修改 Redis 数据，因此我们选择 `READ_WRITE` 模式。
+ **eval()** 方法的参数：
    - **Lua 脚本内容**：要执行的 Lua 脚本字符串。
    - **RScript.ReturnType.INTEGER**：表示返回值为整数类型。可以根据你的脚本返回类型选择其他类型（如 `STRING`、`BOOLEAN`、`VOID` 等）。
    - **KEYS**：传入的 Redis 键列表。通过 `java.util.Collections.singletonList(key)` 传递。
    - **ARGV**：传入的参数列表，表示脚本的输入参数。通过 `java.util.Collections.singletonList(String.valueOf(incrementValue))` 传递。



