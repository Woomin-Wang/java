package inflearn_java_basic.poly.ex5;

public class Dog implements Animal{
    @Override
    public void sound() {
        System.out.println("bark");
    }

    @Override
    public void move() {
        System.out.println("dog move on street");
    }
}
