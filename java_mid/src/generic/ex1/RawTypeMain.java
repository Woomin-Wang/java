package inflearn_java_middle.generic.ex1;

public class RawTypeMain {

    public static void main(String[] args) {
        GenericBox integerBox = new GenericBox();
        //GenericBox<Object> integerBox = new GenericBox<>(); // 권장
        integerBox.setValue(10);
        Integer result = (Integer) integerBox.getValue();
        System.out.println("result = " + result);
    }
}
