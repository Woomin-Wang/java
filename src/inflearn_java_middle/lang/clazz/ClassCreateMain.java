package inflearn_java_middle.lang.clazz;

public class ClassCreateMain {
    public static void main(String[] args) throws Exception {
        Class helloClass = Class.forName("inflearn_java_middle.lang.clazz.Hello");
        Hello hello = (Hello) helloClass.getDeclaredConstructor().newInstance();
        String result  = hello.hello();
        System.out.println("result = " + result);
    }
}
