package inflearn_java_advanced1.thread.cas.increment;

public class BasicInteger implements IncrementInteger {

    private int value;

    @Override
    public void increment() {
        value++;
    }

    @Override
    public int get() {
        return value;
    }
}
