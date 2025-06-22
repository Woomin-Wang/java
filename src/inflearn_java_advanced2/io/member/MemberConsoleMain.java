package inflearn_java_advanced2.io.member;

import inflearn_java_advanced2.io.member.impl.MemoryMemberRepository;

import java.util.Scanner;

public class MemberConsoleMain {

    private static final MemberRepository repository = new MemoryMemberRepository();

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        while(true) {
            System.out.println("1.회원 등록 | 2.회원 목록 조회 | 3.종료");
            System.out.print("선택: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    System.out.println("프로그램을 종료합니다.");
                    return;
                default:
                    System.out.println("잘못된 선택입니다. 다시 입력하세요.");
            }
        }

    }
}
