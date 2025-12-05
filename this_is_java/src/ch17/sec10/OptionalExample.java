package this_is_java.ch17.sec10;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

public class OptionalExample {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();

//        예외 발생(java.util.NoSuchElementException)
//        double ave = list.stream()
//                .mapToInt(Integer :: intValue)
//                .average()
//                .getAsDouble();

        OptionalDouble optional = list.stream()
                .mapToInt(Integer::intValue)
                .average();
        if(optional.isPresent()) {
            System.out.println("방법1-평균: " + optional.getAsDouble());
        } else {
            System.out.println("방법1-평균: 0.0");
        }

        double avg = list.stream()
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0.0);
        System.out.println("방법2-평균: "+ avg);

        list.stream()
                .mapToInt(Integer::intValue)
                .average()
                .ifPresent(a -> System.out.println("방법3-평균: " + a));
    }
}
