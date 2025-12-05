package this_is_java.ch09.sec05.exam02;

public class A {
    // 인스턴스 필드
    String field = "A-field";

    // 인스턴스 메서드
    void method() {
        System.out.println("A-method");
    }

    // 내부 클래스 (= 인스턴스 멤버 클래스)
    class B {
        // B 인스턴스 필드
        String field = "B-field";

        // B 인스턴스 메서드
        void method() {
            System.out.println("B-method");
        }

        // B 인스턴스 메서드
        void print() {
            // B 객체의 필드와 메서드 사용
            System.out.println(this.field);
            this.method();

            // A 객체의 필드와 메서드 사용
            System.out.println(A.this.field);
            A.this.method();
        }
    }

    // A의 인스턴스 메서드
    void useB() {
        B b = new B();
        b.print();
    }
}
