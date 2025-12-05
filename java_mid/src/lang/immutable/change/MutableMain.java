package inflearn_java_middle.lang.immutable.change;

public class MutableMain {
    public static void main(String[] args) {
        MutableObj obj = new MutableObj(10);
        obj.add(20);

        System.out.println("obj.value = " + obj.getValue());
    }
}
