package inflearn_java_middle.generic.test.ex3;

public class UnitPrinter {

    // 제네릭 메서드 구현
    public static <T extends BioUnit> void printV1(Shuttle<T> t) {
        T unit = t.out();
        System.out.println("이름:" + unit.getName() + " HP:" + unit.getHp());
    }

    // 와일드카드 구현
    public static void printV2(Shuttle<? extends BioUnit> t1) {
        BioUnit unit = t1.out();
        System.out.println("이름:" + unit.getName() + " HP:" + unit.getHp());
    }
}
