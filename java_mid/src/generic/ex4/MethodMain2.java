package inflearn_java_middle.generic.ex4;

import inflearn_java_middle.generic.animal.*;

public class MethodMain2 {

    public static void main(String[] args) {
        Dog dog = new Dog("멍멍이", 100);
        Cat cat = new Cat("냐옹이", 100);

        AnimalMethod.checkup(dog);
        AnimalMethod.checkup(cat);

        Dog targetDog = new Dog("큰멍멍이", 200);
        Dog biggerDog = AnimalMethod.getBigger(dog, targetDog);
        System.out.println("biggerDog = " + biggerDog);

        // 제네릭 메서드 bigger의 타입 매개변수 T가 명시적으로 Dog로 지정되었기 때문에,
        // Cat 타입의 인수는 컴파일 타임에 타입 불일치 오류가 발생합니다.
        // Dog biggerDog = AnimalMethod.<Dog>bigger(dog, cat); // 타입 매개변수 추론으로 인한 컴파일 오류
    }
}
