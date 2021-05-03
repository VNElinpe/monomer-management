package top.vnelinpe.management.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.*;

/**
 * 线程池工具类
 *
 * @author VNElinpe
 * @version 1.0
 * @date 2020/10/20 9:21
 */
@Slf4j
@Component
public class ThreadPoolHolder {
    @Value("${thread-pool.core}")
    private int core;
    @Value("${thread-pool.max}")
    private int max;
    @Value("${thread-pool.alive-time}")
    private int aliveTime;
    @Value("${thread-pool.list-size}")
    private int listSize;

    private ThreadPoolExecutor pool;

    @PostConstruct
    private void init() {
        pool = new ThreadPoolExecutor(core, max, aliveTime, TimeUnit.SECONDS, new ArrayBlockingQueue<>(listSize), (runnable, executor) -> {
            try {
                // 阻塞式地添加
                executor.getQueue().put(runnable);
            } catch (InterruptedException e) {
                log.error("添加任务失败，exception={}", e.getMessage());
            }
        });
    }

    public void execute(Runnable runnable) {
        pool.execute(runnable);
    }

    public <T> Future<T> submit(Callable<T> callable){
        return pool.submit(callable);
    }

    public Future<?> submit(Runnable runnable){
        return pool.submit(runnable);
    }

    @PreDestroy
    private void destroy() {
        pool.shutdown();
        try {
            pool.awaitTermination(10, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            log.error("等待任务池关闭失败，exception={}", e.getMessage());
        }
    }
}
