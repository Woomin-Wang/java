package inflearn_java_advanced1.thread.executor;

import java.util.Set;
import java.util.concurrent.Callable;

import static inflearn_java_advanced1.util.MyLogger.log;
import static inflearn_java_advanced1.util.ThreadUtils.sleep;

public class CallableTask implements Callable<Integer> {

    private final String name;
    private int sleepMs = 1000;

    public CallableTask(String name) {
        this.name = name;
    }

    public CallableTask(String name, int sleepMs) {
        this.name = name;
        this.sleepMs = sleepMs;
    }

    @Override
    public Integer call() throws Exception {
        log(name + "실행");
        sleep(sleepMs);
        log(name + "완료");
        return sleepMs;
    }
}
