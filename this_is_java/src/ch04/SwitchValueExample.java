package ch04;

public class SwitchValueExample {
    public static void main(String[] args) {
        String grade = "B";

        int score = switch (grade) {
            case "A" -> 100;
            case "B" -> 80;
            default -> 60;
        };
        System.out.println("score = " + score);
    }
}
