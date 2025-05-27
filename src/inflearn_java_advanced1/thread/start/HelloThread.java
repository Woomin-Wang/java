package inflearn_java_advanced1.thread.start;

public class HelloThread extends Thread {

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " run()");
    }
}
