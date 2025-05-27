package inflearn_java_advanced1.thread.start;

public class DaemonThreadMain {
    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName() + ": main() start");
        DamonThread damonThread = new DamonThread();
        damonThread.setDaemon(true);
        //damonThread.setDaemon(false);
        damonThread.start();

        System.out.println(Thread.currentThread().getName() + ": main() end");
    }

    static class DamonThread extends Thread {
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + ": run() ");
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(Thread.currentThread().getName() + ": run() end ");
        }
    }
}


