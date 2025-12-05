package inflearn_java_middle.generic.ex1;

public class GenericBox<T> {

    private T value;

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
