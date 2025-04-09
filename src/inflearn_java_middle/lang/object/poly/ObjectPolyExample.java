package inflearn_java_middle.lang.object.poly;

public class ObjectPolyExample {
    public static void main(String[] args) {
        Dog dog = new Dog();
        Car car = new Car();
    }

    private static void action(Object obj) {
        if (obj instanceof Dog dog) {
            dog.sound();
        } else if (obj instanceof Car car) {
            car.move();
        }
    }
}
