# synchronized
代码块，成员变量，成员函数。

不能锁住 int 等，Integer 可以。

没获取到，就阻塞，等待操作系统传消息，唤醒线程。

锁升级

# ReentranLock
公平，非公平锁

要手动调用 unlock 释放

可重入锁

```java
public class ReentrantLockExample {
    private final ReentrantLock lock = new ReentrantLock();

    public void methodA() {
        lock.lock();  // 获取锁
        try {
            // 做一些事情
            methodB();  // 调用另外一个需要获取同样锁的方法
        } finally {
            lock.unlock();  // 释放锁
        }
    }

    public void methodB() {
        lock.lock();  // 尝试获取锁，这里会阻塞，因为当前线程已经持有锁
        try {
            // 做一些事情
        } finally {
            lock.unlock();  // 释放锁
        }
    }
}

```

![](https://cdn.nlark.com/yuque/0/2025/png/43087421/1739527517658-0fe4431a-f295-4154-b29d-6248b718179c.png)

可重入锁通过 private volatile int state 保证，`state` 的值通常通过以下方式分离：

+ `state & 0xFFFF` 获取 **低 16 位**，表示重入次数。
+ `state >>> 16` 获取 **高 16 位**，表示锁的持有者线程的 ID。

等为 0 的时候，才会完全释放，其他线程可用

# volatile
### `**volatile**`** 的工作原理**
+ 当一个变量被声明为 `volatile` 时，它会告诉 JVM 和 CPU，不要将该变量的值缓存在线程的本地缓存中。每次读取这个变量时，都必须从主内存中获取最新值，而每次写入该变量时，都直接写入主内存。
+ `volatile` 确保所有线程对该变量的读写操作都直接发生在主内存中，而不是线程本地的缓存中，避免了不同线程看到不同的值。

`**volatile**`** 的作用**

+ **保证内存可见性**：当一个线程修改了 `volatile` 变量的值，其他线程能够立即看到这个值。
+ **禁止指令重排序**：`volatile` 还会在编译器和 CPU 中插入内存屏障，禁止对访问 `volatile` 变量的读写操作进行指令重排序（在单个线程内，指令的执行顺序是保证的，但跨线程的顺序可能会受到优化或硬件执行策略的影响）。这保证了某些操作的顺序性。

# 线程池
# 线程怎么创建
+ 实现 Runnable 接口，实现 run 方法，这个就是 task
+ new thread ，lambda 表达式

```java
public Thread(Runnable task) {
        this(null, null, 0, task, 0, null);
    }
```

