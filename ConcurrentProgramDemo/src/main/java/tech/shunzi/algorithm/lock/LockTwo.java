package tech.shunzi.algorithm.lock;

import tech.shunzi.common.ThreadID;

public class LockTwo implements Lock {

    private volatile int victim;

    @Override
    public void lock() {
        int i = ThreadID.get();

        // 阻塞率先执行 victim = i 的线程（礼让执行）
        victim = i;

        // 作为牺牲者的线程是无法结束lock方法，进入临界区的。
        while (victim == i) {
        }
    }

    @Override
    public void unlock() {
        // empty
    }
}
