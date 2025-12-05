package this_is_java.ch05.review;

public class ArrayCreateByValueListExample {
    public static void main(String[] args) {
        int[] scores;

        scores = new int[]{83, 90, 87};

        printItem(new int[]{83, 90, 87});
    }

    public static void printItem(int[] scores) {
        for (int score : scores) {
            System.out.println(score);
        }
    }
}
