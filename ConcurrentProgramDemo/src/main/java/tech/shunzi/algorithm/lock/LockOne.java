package tech.shunzi.algorithm.lock;

import tech.shunzi.common.ThreadID;

import java.util.concurrent.atomic.AtomicBoolean;

public class LockOne implements Lock {

    private AtomicBoolean[] flag = new AtomicBoolean[2];

    public LockOne() {
        flag[0] = new AtomicBoolean(false);
        flag[1] = new AtomicBoolean(false);
    }

    // 因为 Lock 本身不互斥，所以两个线程可能同时都将对方的 flag 标识设置为 true
    // 导致两个线程都被阻塞在 while(true) {} 。
    @Override
    public void lock() {
        int i = ThreadID.get();
        int j = 1 - i;
        flag[i].set(true);

        // 当两个线程都执行完上面的置flag操作时，等待另一个线程unlock置flag为false时，会发生死锁。
        while (flag[j].get()) {
            System.out.printf("Thread %d is waiting!\n", i);
        }
    }

    @Override
    public void unlock() {
        int i = ThreadID.get();
        flag[i].set(false);
    }
}
