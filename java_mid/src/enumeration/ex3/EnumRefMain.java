package inflearn_java_middle.enumeration.ex3;

public class EnumRefMain {
    public static void main(String[] args) {
        System.out.println(Grade.BASIC.getClass());
        System.out.println(Grade.GOLD.getClass());
        System.out.println(Grade.DIAMOND.getClass());

        System.out.println(refValue(Grade.BASIC));
        System.out.println(refValue(Grade.GOLD));
        System.out.println(refValue(Grade.DIAMOND));
    }

    private static String refValue(Object grade) {
        return Integer.toHexString(System.identityHashCode(grade));
    }
}
