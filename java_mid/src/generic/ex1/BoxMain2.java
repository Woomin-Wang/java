package inflearn_java_middle.generic.ex1;

public class BoxMain2 {
    public static void main(String[] args) {

        ObjectBox intergerBox = new ObjectBox();
        intergerBox.setValue(10);
        Integer integer = (Integer) intergerBox.getValue();
        System.out.println("integer = " + integer);

        ObjectBox stringBox = new ObjectBox();
        stringBox.setValue("hello");
        String str = (String) stringBox.getValue();
        System.out.println("str = " + str);

        //잘못된 타입의 인수 전달시
        intergerBox.setValue("문자100");
        Integer result = (Integer) intergerBox.getValue(); //String -> Integer 캐스팅 예외
        System.out.println("result = " + result);
    }
}
