package ch04;

public class IfDiceExample {
    public static void main(String[] args) {
        int num = (int) (Math.random() * 6) + 1;

        if (num == 1) {
            System.out.println("1번 나왔습니다.");
        } else if (num == 2) {
            System.out.println("2번 나왔습니다.");
        } else if (num == 3) {
            System.out.println("3번 나왔습니다.");
        } else if (num == 4) {
            System.out.println("4번 나왔습니다.");
        } else if (num == 5) {
            System.out.println("5번 나왔습니다.");
        } else {
            System.out.println("6번 나왔습니다.");
        }
    }
}
