package tech.shunzi.common;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程辅助类
 */
public class ThreadID {

    private static Map<Long, Integer> threadIDs = new ConcurrentHashMap<>();

    private static AtomicInteger counter = new AtomicInteger();

    /**
     * 获取当前线程 ID
     * @return
     */
    public static int get() {
        long id = Thread.currentThread().getId();
        return threadIDs.computeIfAbsent(id, key -> counter.getAndIncrement());
    }
}
