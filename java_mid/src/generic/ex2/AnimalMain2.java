package inflearn_java_middle.generic.ex2;

import inflearn_java_middle.generic.animal.Animal;
import inflearn_java_middle.generic.animal.Cat;
import inflearn_java_middle.generic.animal.Dog;
import this_is_java.ch06.sec13.exam02.package1.B;

public class AnimalMain2 {

    public static void main(String[] args) {
        Animal animal = new Animal("동물", 0);
        Dog dog = new Dog("멍멍이", 100);
        Cat cat = new Cat("냐옹이", 50);

        Box<Animal> animalBox = new Box<>();
        animalBox.setValue(animal);
        animalBox.setValue(dog);
        animalBox.setValue(cat);

        Animal findAnimal = animalBox.getValue();
        System.out.println("findAnimal = " + findAnimal);

    }
}
