package inflearn_java_advanced1.thread.volatile1;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class VolatileFlagMain {
    public static void main(String[] args) {
        MyTask task = new MyTask();
        Thread thread = new Thread(task, "word");
        log("runFlag = " + task.runFlag);
        thread.start();

        sleep(1000);
        log("runFlag를 false로 변경 시도");
        task.runFlag = false;
        log("runFlag = " + task.runFlag);
        log("main 종료");
    }

    static class MyTask implements Runnable {
        //boolean runFlag = true;
        volatile boolean runFlag = true;

        @Override
        public void run() {
            log("Task 시작");
            while(runFlag) {
                // rubFlag가 false로 변하면 탈출
            }
            log("Task 종료");
        }
    }
}
