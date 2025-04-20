package inflearn_java_middle.generic.ex4;

import inflearn_java_middle.generic.animal.Animal;

public class AnimalMethod {

    public static <T extends Animal> void checkup(T animal) {
        System.out.println("동물 이름: " + animal.getName());
        System.out.println("동물 크기: " + animal.getSize());
        animal.sound();
    }

    public static <T extends Animal> T bigger(T animal, T target) {
        return animal.getSize() > target.getSize() ? animal : target;
    }
}
