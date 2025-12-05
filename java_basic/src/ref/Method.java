package inflearn_java_basic.ref;

public class Method {
    public static void main(String[] args) {

    }

    static void initStudent(Student student, String name, int age, int grade) {
        student.name = name;
        student.age = age;
        student.grade = grade;
    }

    static void printStudent(Student student) {
        System.out.println("이름: " + student.name + " 나이: " + student.age + "성적: " + student.grade);
    }
}
