package this_is_java.ch07.sec10.exam02;

public class AbstractMethodExample {
    public static void main(String[] args) {
        Dog dog = new Dog();
        dog.sound();

        Cat cat = new Cat();
        cat.sound();

        animalSound(dog);
        animalSound(cat);
    }

    public static void animalSound(Animal animal) {
        animal.sound();
    }
}
