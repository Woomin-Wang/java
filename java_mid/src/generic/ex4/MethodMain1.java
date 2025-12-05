package inflearn_java_middle.generic.ex4;

public class MethodMain1 {

    public static void main(String[] args) {

        Integer i = 10;
        Object object = GenericMethod.objMethod(i);
        Integer result = (Integer) GenericMethod.objMethod(i);

        // 타입 인자(Type Argument)
        System.out.println("명시적 타입 입자 전달");
        Integer result1 = GenericMethod.<Integer>genericMethod(i);
        Integer integerValue = GenericMethod.<Integer>numberMethod(10);
        Double doubleValue = GenericMethod.<Double>numberMethod(20.0);

        System.out.println("타입 추론");
        Integer result2 = GenericMethod.genericMethod(i);
        Integer integerValue1 = GenericMethod.numberMethod(10);
        Double doubleValue1 = GenericMethod.numberMethod(20.0);

        // Number는 int, double의 부모 클래스, 컴파일 오류
        // Integer integerValue = GenericMethod.<Integer>numberMethod(new Object());

        // 컴파일 오류
        // String str1 = GenericMethod.<String>genericMethod(i);
        // String str2= GenericMethod.<String>numberMethod();
    }
}
