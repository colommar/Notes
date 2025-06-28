网卡选择本地环回即可。

可以看到即便关闭了 http：keep-alive，也依然有 tcp 的 keep-alive

其实这个是 tcp 的 keep-alive，是操作系统层面的。

+ **HTTP 的 Keep-Alive** 是控制是否复用 TCP 连接的机制。
+ **TCP 的 Keep-Alive** 是操作系统级别的机制，主要用于确保连接的活跃性和网络可靠性，尽管 HTTP 没有明确请求 Keep-Alive，TCP 仍可能定期发送 Keep-Alive 包。

下面 Linux 命令禁用。

```java
sysctl net.ipv4.tcp_keepalive_time  # 查看TCP Keep-Alive时间
sysctl -w net.ipv4.tcp_keepalive_time=600  # 设置TCP Keep-Alive时间为600秒

```

![](https://cdn.nlark.com/yuque/0/2025/png/43087421/1740200872402-15aba081-98b5-4417-9a52-82f8dd75047c.png)![](https://cdn.nlark.com/yuque/0/2025/png/43087421/1740200877703-50274f29-7f4f-468f-ae2f-1497254ec46d.png)

![](https://cdn.nlark.com/yuque/0/2025/png/43087421/1740200884505-aa0e50b1-2788-4c6a-86a4-3c43ff699b7a.png)

