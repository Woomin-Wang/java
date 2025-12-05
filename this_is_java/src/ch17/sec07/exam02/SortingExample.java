package this_is_java.ch17.sec07.exam02;

import this_is_java.ch17.sec07.exam01.Student;

import java.util.ArrayList;
import java.util.List;

public class SortingExample {
    public static void main(String[] args) {

        List<this_is_java.ch17.sec07.exam01.Student> studentList = new ArrayList<>();
        studentList.add(new this_is_java.ch17.sec07.exam01.Student("홍길동", 30));
        studentList.add(new this_is_java.ch17.sec07.exam01.Student("신용권", 10));
        studentList.add(new Student("유미선", 20));

         studentList.stream()
                 .sorted((s1, s2) -> Integer.compare(s1.getScore(), s2.getScore()))
                 .forEach(s -> System.out.println(s.getName() + ": " + s.getScore()));
        System.out.println();

         studentList.stream()
                 .sorted((s1, s2) -> Integer.compare(s2.getScore(), s1.getScore()))
                 .forEach(s -> System.out.println(s.getName() + ": " + s.getScore()));
    }
}
