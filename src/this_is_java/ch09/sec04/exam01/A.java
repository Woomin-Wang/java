package this_is_java.ch09.sec04.exam01;

public class A {
    // 생성자
    A() {
        // 로컬 클래스 선언
        class B {
        }
        // 로컬 객체 생성
        B b = new B();
    }

    // 메서드
    void method() {
        // 로컬 클래스 선언
        class B {
        }
        // 로컬 객체 생성
        B b = new B();
    }
}
