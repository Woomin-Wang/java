package inflearn_java_middle.enumeration.ex2;

public class ClassRefMain {
    public static void main(String[] args) {
        System.out.println(ClassGrade.BASIC.getClass());
        System.out.println(ClassGrade.GOLD.getClass());
        System.out.println(ClassGrade.DIAMOND.getClass());

        System.out.println(ClassGrade.BASIC);
        System.out.println(ClassGrade.GOLD);
        System.out.println(ClassGrade.DIAMOND);
    }
}
