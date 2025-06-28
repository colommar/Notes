在并发编程的时候，我们会使用future，但如果使用future.get()的话，会有一个问题，你提交了多个任务，然后for一个List，主流程实际上还是阻塞在那里了，并没有很好的提高性能，也不能按照任务完成时间解决问题。

我能想到的一个方法是用线程池，然后通过线程工厂提交任务的时候，让线程来回调我们某个方法，这样我们就能按照完成时间拿到任务结果了。

用CompletionService，下面是我之前写的两个 demo

```java

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 演示如何优先获取任务完成的结果，而不是按照提交顺序
 * 使用 CompletionService 实现按完成顺序获取结果
 */
public class PriorityTaskCompletionDemo {

    public static void main(String[] args) {
        // 创建线程池
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                5, 10, 60L, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(100),
                new ThreadFactory() {
                    private final AtomicInteger counter = new AtomicInteger(1);
                    @Override
                    public Thread newThread(Runnable r) {
                        Thread thread = new Thread(r);
                        thread.setName("TaskThread-" + counter.getAndIncrement());
                        return thread;
                    }
                }
        );

        // 使用 CompletionService，它会按完成顺序返回结果
        CompletionService<TaskResult> completionService = new ExecutorCompletionService<>(executor);

        // 定义不同执行时间的任务
        List<Callable<TaskResult>> tasks = createTasks();

        // 提交所有任务
        List<Future<TaskResult>> futures = new ArrayList<>();
        for (int i = 0; i < tasks.size(); i++) {
            Callable<TaskResult> task = tasks.get(i);
            Future<TaskResult> future = completionService.submit(task);
            futures.add(future);
            System.out.println("提交任务 " + (i + 1) + "，预期执行时间: " +
                    ((TaskResult) task).getExpectedTime() + "ms");
        }

        System.out.println("\n=== 开始按完成顺序获取结果 ===\n");

        // 按完成顺序获取结果（优先获取先完成的任务）
        long startTime = System.currentTimeMillis();
        List<TaskResult> results = new ArrayList<>();

        try {
//            // 方法1：使用 take() - 阻塞等待下一个完成的任务
//            for (int i = 0; i < tasks.size(); i++) {
//                Future<TaskResult> future = completionService.take(); // 按完成顺序获取
//                TaskResult result = future.get();
//                long completionTime = System.currentTimeMillis() - startTime;
//
//                System.out.printf("任务 %d 完成！执行时间: %dms, 总耗时: %dms\n",
//                        result.getTaskId(), result.getActualTime(), completionTime);
//                results.add(result);
//            }

             // 方法2：使用 poll() - 非阻塞，可以设置超时
             for (int i = 0; i < tasks.size(); i++) {
                 Future<TaskResult> future = completionService.poll(5, TimeUnit.SECONDS);
                 if (future != null) {
                     TaskResult result = future.get();
                     System.out.println("任务 " + result.getTaskId() + " 完成！");
                     results.add(result);
                 } else {
                     System.out.println("等待超时");
                     break;
                 }
             }

        } catch (InterruptedException | ExecutionException e) {
            System.err.println("获取结果时发生异常: " + e.getMessage());
        }

        System.out.println("\n=== 结果统计 ===");
        System.out.println("按完成顺序的结果:");
        for (int i = 0; i < results.size(); i++) {
            TaskResult result = results.get(i);
            System.out.printf("%d. 任务%d (预期%dms, 实际%dms)\n",
                    i + 1, result.getTaskId(),
                    result.getExpectedTime(), result.getActualTime());
        }

        // 关闭线程池
        executor.shutdown();
        try {
            if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }
    }

    /**
     * 创建不同执行时间的任务
     */
    private static List<Callable<TaskResult>> createTasks() {
        List<Callable<TaskResult>> tasks = new ArrayList<>();

        // 任务1：快速任务 (100ms)
        tasks.add(new TaskResult(1, 100));

        // 任务2：慢速任务 (2000ms)
        tasks.add(new TaskResult(2, 2000));

        // 任务3：中等任务 (500ms)
        tasks.add(new TaskResult(3, 500));

        // 任务4：快速任务 (50ms)
        tasks.add(new TaskResult(4, 50));

        // 任务5：慢速任务 (3000ms)
        tasks.add(new TaskResult(5, 3000));

        return tasks;
    }

    /**
     * 任务结果类
     */
    static class TaskResult implements Callable<TaskResult> {
        private final int taskId;
        private final long expectedTime;
        private long actualTime;

        public TaskResult(int taskId, long expectedTime) {
            this.taskId = taskId;
            this.expectedTime = expectedTime;
        }

        @Override
        public TaskResult call() throws Exception {
            long startTime = System.currentTimeMillis();

            // 模拟任务执行
            Thread.sleep(expectedTime);

            this.actualTime = System.currentTimeMillis() - startTime;
            return this;
        }

        // Getters
        public int getTaskId() { return taskId; }
        public long getExpectedTime() { return expectedTime; }
        public long getActualTime() { return actualTime; }
    }
}
```

```
提交任务 1，预期执行时间: 100ms
提交任务 2，预期执行时间: 2000ms
提交任务 3，预期执行时间: 500ms
提交任务 4，预期执行时间: 50ms
提交任务 5，预期执行时间: 3000ms

=== 开始按完成顺序获取结果 ===

任务 4 完成！执行时间: 53ms, 总耗时: 53ms
任务 1 完成！执行时间: 109ms, 总耗时: 100ms
任务 3 完成！执行时间: 506ms, 总耗时: 506ms
任务 2 完成！执行时间: 2003ms, 总耗时: 2002ms
任务 5 完成！执行时间: 3015ms, 总耗时: 3015ms

=== 结果统计 ===
按完成顺序的结果:
1. 任务4 (预期50ms, 实际53ms)
2. 任务1 (预期100ms, 实际109ms)
3. 任务3 (预期500ms, 实际506ms)
4. 任务2 (预期2000ms, 实际2003ms)
5. 任务5 (预期3000ms, 实际3015ms)

进程已结束，退出代码为 0
```


```
提交任务 1，预期执行时间: 100ms
提交任务 2，预期执行时间: 2000ms
提交任务 3，预期执行时间: 500ms
提交任务 4，预期执行时间: 50ms
提交任务 5，预期执行时间: 3000ms

=== 开始按完成顺序获取结果 ===

任务 4 完成！
任务 1 完成！
任务 3 完成！
任务 2 完成！
任务 5 完成！

=== 结果统计 ===
按完成顺序的结果:
1. 任务4 (预期50ms, 实际52ms)
2. 任务1 (预期100ms, 实际110ms)
3. 任务3 (预期500ms, 实际501ms)
4. 任务2 (预期2000ms, 实际2012ms)
5. 任务5 (预期3000ms, 实际3012ms)

进程已结束，退出代码为 0
```