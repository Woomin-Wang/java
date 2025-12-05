package inflearn_java_middle.generic.ex4;

import inflearn_java_middle.generic.animal.Animal;

public class ComplexBox <T extends Animal> {

    private T animal;

    public void set(T animal) {
        this.animal = animal;
    }

    public <T> T printAndReturn(T t) {
        System.out.println("animal.className: " + animal.getClass().getName()); // Dog
        System.out.println("z.className: " + t.getClass().getName()); // Cat
        return t;
    }
}
