package this_is_java.ch15.sec02.exam02;
import this_is_java.ch15.sec02.exam01.Board;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class VectorExample {
    public static void main(String[] args) {
        List<Board> list = new Vector<>();
        //List<Board> list = new ArrayList<>();

        Thread threadA = new Thread() {
            public void run() {
                for (int i = 1; i <= 1000; i++) {
                    list.add(new Board("제목"+i, "내용"+i, "글쓴이"+i));
                }
            }
        };

        Thread threadB = new Thread() {
            public void run() {
                for (int i = 1001; i <= 2000; i++) {
                    list.add(new Board("제목"+i, "내용"+i, "글쓴이"+i));
                }
            }
        };

        threadA.start();
        threadB.start();

        try {
            threadA.join();
            threadB.join();
        } catch (Exception e) {
        }

        int size = list.size();
        System.out.println("총 객체 수: " + size);
        System.out.println();
    }
}
