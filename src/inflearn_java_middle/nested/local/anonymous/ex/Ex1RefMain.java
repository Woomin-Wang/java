package inflearn_java_middle.nested.local.anonymous.ex;

import java.util.Random;

public class Ex1RefMain {
    public static void hello(Process process) {
        System.out.println("Program Start");

        process.run();

        System.out.println("Program Finish");
    }

    public static void main(String[] args) {
        hello(new Process() {
            @Override
            public void run() {
                int randomValue = new Random().nextInt(6) + 1;
                System.out.println("주사위 = " + randomValue);
            }
        });

        hello(new Process() {
            @Override
            public void run() {
                for (int i = 1; i <=3; i++) {
                    System.out.println("i = " + i);
                }
            }
        });
    }
}
