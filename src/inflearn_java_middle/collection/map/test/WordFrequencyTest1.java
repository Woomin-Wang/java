package inflearn_java_middle.collection.map.test;

import java.util.HashMap;

public class WordFrequencyTest1 {
    public static void main(String[] args) {
        String text = "orange banana apple apple banana apple";

        HashMap<String, Integer> hashMap = new HashMap<>();

        String[] words = text.split(" ");

        for (String word : words) {
//            Integer count = hashMap.get(word);
//            if(count == null) {
//                count = 0;
//            }
            hashMap.put(word, hashMap.getOrDefault(word, 0) + 1);
        }
        System.out.println("hashMap = " + hashMap);
    }
}
