package inflearn_java_middle.generic.ex5;

public class Box <T> {

    private T value;

    public void set(T value) {
        this.value = value;
    }

    public T get() {
        return value;
    }
}
