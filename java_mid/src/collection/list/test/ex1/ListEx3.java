package inflearn_java_middle.collection.list.test.ex1;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ListEx3 {
    public static void main(String[] args) {

        List<Integer> list = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);


        System.out.println("n개의 정수를 입력하세요 (종료 0)");
        while(true) {
            int inputNum = scanner.nextInt();

            if(inputNum == 0) {
                break;
            } else {
                list.add(inputNum);
            }
        }
        int total = 0;
        for (int i = 0; i < list.size(); i++) {
            total += list.get(i);
        }
        double average = (double) total / list.size();

        System.out.println("입력한 정수의 합계: " + total);
        System.out.println("입력한 정수의 평균: " + average);
    }
}
