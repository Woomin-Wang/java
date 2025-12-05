package inflearn_java_middle.generic.ex5;

import java.util.List;

public class Box <T> {
    private T value;

    public void set(T value) {
        this.value = value;
    }

    public T get() {
        return value;
    }
}
