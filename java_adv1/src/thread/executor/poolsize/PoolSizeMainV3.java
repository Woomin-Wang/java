package inflearn_java_advanced1.thread.executor.poolsize;

import inflearn_java_advanced1.thread.executor.RunnableTask;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static inflearn_java_advanced1.thread.executor.ExecutorUtils.printState;
import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class PoolSizeMainV3 {

    public static void main(String[] args) {

        ExecutorService es = Executors.newCachedThreadPool();

        log("poll 생성");
        printState(es);

        for (int i = 1; i <= 4  ; i++) {
            String taskName = "task" + i;
            es.execute(new RunnableTask(taskName));
            printState(es, taskName);
        }

        sleep(3000);
        log("== maximumPoolSize 대기 시간 초과 ==");
        printState(es);

        es.close();
        log("== shutdown 호출 ==");
        printState(es);
    }
}
