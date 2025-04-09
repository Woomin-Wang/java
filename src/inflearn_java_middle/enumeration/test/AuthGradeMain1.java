package inflearn_java_middle.enumeration.test;

public class AuthGradeMain1 {
    public static void main(String[] args) {
        printAuthGrade(AuthGrade.GUEST);
        printAuthGrade(AuthGrade.LOGIN);
        printAuthGrade(AuthGrade.ADMIN);
    }

    private static void printAuthGrade(AuthGrade authGrade) {
        System.out.println("grade = " + authGrade.name() + ", level = " + authGrade.getLevel() + ", 설명 = " + authGrade.getDescription());
    }
}
