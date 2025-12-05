package inflearn_java_basic.poly.basic;

public class CastingMain5 {
    public static void main(String[] args) {
        Parent parent1 = new Parent();
        System.out.println("parent1 호출");

        Parent parent2 = new Child();
        System.out.println("parent2 호출");

    }

//    private static void call(Parent parent) {
//        parent.parentMethod();
//
//    }
}
