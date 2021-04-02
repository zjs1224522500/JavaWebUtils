package tech.shunzi.share;

public class TestThread extends Thread {

    private boolean flag = false;

    // volatile 关键字通过 CPU 总线嗅探机制告知其他线程该变量副本已经失效，需要重新从主内存中读取
    // volatile 保证了不同线程对共享变量操作的可见性，也就是说一个线程修改了 volatile 修饰的变量，
    // 当修改后的变量写回主内存时，其他线程能立即看到最新值。
    private volatile boolean v_flag = false;

    // 大量使用 volatile 关键字会引起总线风暴


    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 修改变量值
        flag = true;
        v_flag = true;
        System.out.println("flag = " + flag);
        System.out.println("v_flag = " + v_flag);

    }

    public boolean isFlag() {
        return flag;
    }

    public boolean isV_flag() {
        return v_flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public void setV_flag(boolean v_flag) {
        this.v_flag = v_flag;
    }

    public static synchronized void testMethod() {
        // do nothing
    }

    public static void main(String[] args) {
        TestThread thread = new TestThread();
        thread.start();
        // 主线程执行
        for (int i = 0; i < 1000000000 ; i++ ) {

            // 如果执行路径中出现了 synchronized 关键字
            // 线程进入该关键字修饰的代码块后，线程获取到锁，会清空本地内存
            // 然后从主内存中拷贝共享变量的最新值到本地内存作为副本，这时候就会输出相应的语句了
            // testMethod();
            if (thread.isFlag()) {

                // 为了输出这条语句
                // 1. 可以使用 volatile 关键字来触发 CPU 总线嗅探从而解决缓存一致性问题
                // 2. 同时也可以使用 synchronized 来清空本地内存，然后去从主内存中读取共享变量最新的值
                System.out.println("主线程访问到最新的 flag 变量");
                System.out.println(i);
                break;
            } else {
//                System.out.println("No Flag");
//                System.out.println(i);
//                break;

            }


        }

        for (;;) {
            if (thread.isV_flag()) {
                System.out.println("主线程访问到最新的 v_flag 变量");
            }
        }
    }
}