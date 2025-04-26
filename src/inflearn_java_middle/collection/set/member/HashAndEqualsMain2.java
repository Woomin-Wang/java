package inflearn_java_middle.collection.set.member;

import inflearn_java_middle.collection.set.MyHashSetV2;

public class HashAndEqualsMain2 {

    public static void main(String[] args) {
        MyHashSetV2 set = new MyHashSetV2(10);
        MemberOnlyHash m1 = new MemberOnlyHash("A");
        MemberOnlyHash m2 = new MemberOnlyHash("A");

        System.out.println("m1.hashCode() = " + m1.hashCode());
        System.out.println("m2.hashCode() = " + m2.hashCode());
        // No Override equals(), Object 기능인 동일 비교
        System.out.println("m1.equals(m2) = " + m1.equals(m2));

        System.out.println("m1의 참조값 = " + System.identityHashCode(m1));
        System.out.println("m2의 참조값 = " + System.identityHashCode(m2));

        // 중복 등록, 인스턴스의 주소는 당연히 다르고, 해시값은 같음.
        // add -> contains -> equals 에서 동일 비교할 때 주소값을 비교하기 때문에 중복 등록
        set.add(m1);
        set.add(m2);
        System.out.println(set);

        // 검색 실패
        MemberOnlyHash searchValue = new MemberOnlyHash("A");
        System.out.println("searchValue.hashCode() = " + searchValue.hashCode());
        // contains() 안에 equals()의 동일 비교로 인하여 무조건 false
        boolean result = set.contains(searchValue);
        System.out.println("result = " + result);

    }
}
