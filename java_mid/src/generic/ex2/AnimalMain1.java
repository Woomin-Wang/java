package inflearn_java_middle.generic.ex2;

import inflearn_java_middle.generic.animal.Animal;
import inflearn_java_middle.generic.animal.Cat;
import inflearn_java_middle.generic.animal.Dog;
import this_is_java.ch06.sec13.exam02.package1.B;
import this_is_java.ch06.sec13.exam02.package1.C;

public class AnimalMain1 {

    public static void main(String[] args) {
        Animal animal = new Animal("동물", 0);
        Dog dog = new Dog("멍멍이", 100);
        Cat cat = new Cat("냐옹이", 50);

        Box<Dog> dogBox = new Box<>();
        dogBox.setValue(dog);
        Dog findDog = dogBox.getValue();
        System.out.println("findDog = " + findDog);

        Box<Cat> catBox = new Box<>();
        catBox.setValue(cat);
        Cat findCat = catBox.getValue();
        System.out.println("findCat = " + findCat);

        Box<Animal> animalBox = new Box<>();
        animalBox.setValue(animal);
        Animal findAnimal  = animalBox.getValue();
        System.out.println("findAnimal = " + findAnimal);
    }
}
