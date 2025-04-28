package this_is_java.ch09.sec05.exam01;

public class A {
    // A의 인스턴스 필드와 메서드
    int field1;

    void method1() {
    }

    // A의 정적 필드와 메서드
    static int field2;

    static void method2() {
    }

    // 내부 클래스
    class B {
        void method() {
            // A의 인스턴스 필드 메서드 사용
            field1 = 10;
            method1();

            // A의 정적 필드 메서드 사용
            field2 = 10;
            method2();
        }
    }

    // 정적 중첩 클래스
    static class C {
        void method() {
            // B의 인스턴스 필드 메서드 사용
            // field1 = 10; // (X)
            // method1(); // (X)

            // B의 정적 필드 메서드 사용
            field2 = 10;
            method2();
        }
    }
}
