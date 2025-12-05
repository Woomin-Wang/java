package inflearn_java_middle.enumeration.test;

import java.util.Scanner;

public class AuthGradeMain2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String grade;

        System.out.print("당신의 등급을 입력하세요[GUEST, LOGIN, ADMIN]: ");
        grade = scanner.nextLine();

        AuthGrade authGrade = AuthGrade.valueOf(grade);
        System.out.println("당신의 등급은 " + authGrade.getDescription() + "입니다.");

    }
}
