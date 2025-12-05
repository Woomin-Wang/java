package inflearn_java_middle.exception.basic.unchecked;

import inflearn_java_middle.exception.basic.checked.MyCheckedException;

public class Client {
    public void call() {
        throw new MyUncheckedException("ex");
    }
}
