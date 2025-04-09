package inflearn_java_basic.static2.ex2;

public class MathArrayUtils {
    public static int sum(int[] array) {
        int sum = 0;
        for (int num: array) {
            sum += num;
        }
        return sum;
    }

    public static double average(int[] array) {
        int sum = 0;
        for (int num: array) {
            sum += num;
        }
        return (double) sum / array.length;
    }

//    public static int min(int[] array) {
//        return min(array);
//    }
//
//    public static int max(int[] array) {
//        return max(array);
//    }
}
