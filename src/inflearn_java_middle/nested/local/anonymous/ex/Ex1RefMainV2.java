package inflearn_java_middle.nested.local.anonymous.ex;

import java.util.Random;

public class Ex1RefMainV2 {

    public static void hello(Process process) {
        System.out.println("프로그램 시작");
        process.run();
        System.out.println("프로그램 종료");
    }

    public static void main(String[] args) {

        //정적 중첩 클래스(V1) -> 지역 클래스(V2)
        class Dice implements Process {
            @Override
            public void run() {
                int randomValue = new Random().nextInt(6) + 1;
                System.out.println("주사위 = " + randomValue);
            }
        }

        //정적 중첩 클래스(V1) -> 지역 클래스(V2)
        class Sum implements Process {
            @Override
            public void run() {
                for (int i = 0; i < 3; i++) {
                    System.out.println("i = " + i);
                }
            }
        }
        System.out.println("Hello 실행");
        hello(new Dice());
        hello(new Sum());
    }

}
