package this_is_java.ch06.review;

public class Computer {
    int sum(int... values) {
        int sum = 0;

        for (int value : values) {
            sum += value;
        }
        return sum;
    }
}
