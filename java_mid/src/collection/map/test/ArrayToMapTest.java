package inflearn_java_middle.collection.map.test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ArrayToMapTest {
    public static void main(String[] args) {
        String[][] productArr = {{"Java", "10000"}
                                , {"Spring", "20000"}
                                , {"JPA", "30000"}};

        // 주어진 배열로부터 Map 생성
        HashMap<String, Integer> hashMap = new HashMap<>();

        for (String[] strings : productArr) {
            hashMap.put(strings[0], Integer.valueOf(strings[1]));
        }

        // Map의 모든 데이터 출력
        for (Map.Entry<String, Integer> entry : hashMap.entrySet()) {
            System.out.println("제품: " + entry.getKey() + ", 가격: " + entry.getValue());
        }
    }
}
