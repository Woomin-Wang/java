package this_is_java.ch06.sec07.exam02;

public class KoreanExample {
    public static void main(String[] args) {
        Korean k1 = new Korean("박자바", "011225-1234567");
        System.out.println(k1.nation);
        System.out.println(k1.name);
        System.out.println(k1.ssn);

        Korean k2 = new Korean("김자바", "930525-1239124");
        System.out.println(k2.nation);
        System.out.println(k2.name);
        System.out.println(k2.ssn);
    }
}
