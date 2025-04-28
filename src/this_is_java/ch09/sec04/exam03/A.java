package this_is_java.ch09.sec04.exam03;

public class A {
    // 메서드
    public void method1(int arg) {  // final int age
        int var = 1;    // final int var = 1;

        // 지역 클래스
        class B {
            // 메서드
            void method2() {
                System.out.println("arg: " + arg);
                System.out.println("var: " + var);

                // 로컬 변수 수정
                // arg = 2; // (X)
                // var = 2; // (X)
            }
        }

        // 로컬 객체 생성
        B b = new B();
        // 로컬 객체 메서드 호출
        b.method2();

        // 로컬 변수 수정
        // arg = 3; // (X)
        // var = 3; // (X)
    }
}
