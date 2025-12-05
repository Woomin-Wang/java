package inflearn_java_basic.poly.ex6;

public class Bird extends Animal implements Fly{
    @Override
    public void sound() {
        System.out.println("짹짹");
    }

    @Override
    public void fly() {
        System.out.println("bird fly");
    }
}
