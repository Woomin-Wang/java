package inflearn_java_basic.class1;

public class ClassStart4 {
    public static void main(String[] args) {
        Student student1 = new Student();
        student1.name = "학생1";
        student1.age = 15;
        student1.grade = 90;

        Student student2 = new Student();
        student2.name = "학생2";
        student2.age = 16;
        student2.grade = 80;

        Student[] students = new Student[2];
        students[0] = student1;
        students[1] = student2;

        for (Student std : students) {
            System.out.println("이름: " + std.name + "나이: " + std.age + "점수: " + std.grade);
        }
    }
}
