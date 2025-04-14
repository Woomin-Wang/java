package inflearn_java_middle.nested.local.anonymous.ex;

import java.util.Random;

public class Ex1RefMainV4 {

    public static void hello(Process process) {
        System.out.println("프로그램 시작");
        process.run();
        System.out.println("프로그램 종료");
    }

    public static void main(String[] args) {

        //정적 중첩 클래스(V1) -> 지역 클래스(V2) -> 익명 클래스(V3) -> 참조값 직접 전달(V4)
        hello(new Process() {
            @Override
            public void run() {
                int randomValue = new Random().nextInt(6) + 1;
                System.out.println("주사위 = " + randomValue);
            }
        });

        //정적 중첩 클래스(V1) -> 지역 클래스(V2) -> 익명 클래스(V3) -> 참조값 직접 전달(V4)
        hello(new Process() {
            @Override
            public void run() {
                for (int i = 0; i < 3; i++) {
                    System.out.println("i = " + i);
                }
            }
        });
    }
}
