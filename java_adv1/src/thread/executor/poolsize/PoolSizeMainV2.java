package inflearn_java_advanced1.thread.executor.poolsize;

import inflearn_java_advanced1.thread.executor.RunnableTask;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static inflearn_java_advanced1.thread.executor.ExecutorUtils.printState;
import static util.MyLogger.log;

public class PoolSizeMainV2 {

    public static void main(String[] args) {

        ExecutorService es = Executors.newFixedThreadPool(2);

        log("poll 생성");
        printState(es);

        for (int i = 1; i <= 6; i++) {
            String taskName = "task" + i;
            es.execute(new RunnableTask(taskName));
            printState(es, taskName);
        }
        es.close();
        log("== shutdown 호출 ==");
    }
}
