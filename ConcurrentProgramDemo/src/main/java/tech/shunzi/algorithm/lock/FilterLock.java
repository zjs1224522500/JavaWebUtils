package tech.shunzi.algorithm.lock;

import tech.shunzi.common.ThreadID;

import java.util.concurrent.atomic.AtomicInteger;

class FilterLock implements Lock {
    // level 表示每个线程的等待级别
    private AtomicInteger[] level;
    // 模拟一个阻塞（忙等待）的线程队列，其实该队列只需要 N - 1 个元素
    private AtomicInteger[] victim;
    private int numberOfThreads;

    public FilterLock(int numberOfThreads) {
        level = new AtomicInteger[numberOfThreads];
        victim = new AtomicInteger[numberOfThreads];
        this.numberOfThreads = numberOfThreads;
        for (int i = 0;i < numberOfThreads; i++) {
            level[i] = new AtomicInteger();
            victim[i] = new AtomicInteger();
        }
    }

    public void lock() {
        // eg. 四线程，此时线程号为 1 和 3
        int me = ThreadID.get();

        // 控制线程所处的层级
        for (int i = 0; i < numberOfThreads; i++) {
            // level[1] = i, level[3] = i
            level[me].set(i);
            // victim[i] = 1 or 3
            victim[i].set(me);

            // 检查除了自己外的其他线程是否是在本层级或是下一层级中。以及本线程是否要在目前层级中阻塞等待。
            for (int k = 0; k < numberOfThreads;k++) {
                while ((k != me) && (level[k].get() >= i && victim[i].get() == me)) {

                }
            }
        }
    }

    public void unlock() {
        int me = ThreadID.get();
        level[me].set(0);
    }
}