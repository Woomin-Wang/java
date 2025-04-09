package inflearn_java_middle.nested.local.anonymous.ex;

import java.util.Random;

public class Ex1RefMainV5 {
    public static void hello(Process process) {
        System.out.println("Program Start");
        process.run();
        System.out.println("Program Finish");
    }

    public static void main(String[] args) {
        hello(() -> {
            int randomValue = new Random().nextInt(6 ) + 1;
            System.out.println("Dice = " + randomValue);
        });

        hello(()->{
            for (int i = 01; i<=3; i++) {
                System.out.println("i = " + i);
            }
        });
    }
}
