package tech.shunzi.algorithm.lock;

public class LockCounter {
    private long value;
    private Lock lock;

    public LockCounter(Lock lock) {
        this.lock = lock;
    }

    public long get() {
        // 不想用锁，只是为了暴露取值API。
        return value;
    }

    public long getAndIncrement() {
        lock.lock();
        try {
            long temp = value;
            value = temp + 1;
            return temp;
        } finally {
            lock.unlock();
        }
    }
}
