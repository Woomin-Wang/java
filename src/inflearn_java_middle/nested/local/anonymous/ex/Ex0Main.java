package inflearn_java_middle.nested.local.anonymous.ex;

public class Ex0Main {
    public static void hello(String text) {
        System.out.println("프로그램 시작");
        System.out.println("Hello" + text);
        System.out.println("프로그램 종료");
    }

    public static void main(String[] args) {
        hello("Java");
        hello("Spring");
    }
}
