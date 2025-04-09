package inflearn_java_middle.time;

import java.time.LocalDateTime;
import java.time.temporal.ChronoField;

public class GetTimeMain {
    public static void main(String[] args) {
        LocalDateTime dt = LocalDateTime.of(2023,1,1,13,30,59);
        System.out.println("YEAR = " + dt.get(ChronoField.YEAR));
//        System.out.println("MONTH_OF_YEAR = " + dt.get());
    }
}
