package inflearn_java_advanced1.thread.collection.simple.list;

import java.util.ArrayList;
import java.util.List;

public class SimpleListMainV0 {

    public static void main(String[] args) {

        List<String> list = new ArrayList<>();

        list.add("A");
        list.add("B");
        System.out.println("list = " + list);
    }
}
