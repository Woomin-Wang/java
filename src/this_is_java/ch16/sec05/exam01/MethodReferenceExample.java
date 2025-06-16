package this_is_java.ch16.sec05.exam01;

public class MethodReferenceExample {
    public static void main(String[] args) {
        Person person = new Person();

//        person.action((x, y) -> Computer.staticMethod(x, y));
        person.action(Computer :: staticMethod);

        Computer computer = new Computer();
//        person.action((x, y) -> computer.instanceMethod(x, y));
        person.action(computer::instanceMethod);
    }
}
