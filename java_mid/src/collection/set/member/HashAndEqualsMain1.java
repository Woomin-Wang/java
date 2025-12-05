package inflearn_java_middle.collection.set.member;

import inflearn_java_middle.collection.set.MyHashSetV2;

public class HashAndEqualsMain1 {

    public static void main(String[] args) {
        MyHashSetV2 set = new MyHashSetV2(10);
        MemberNohashNoEq m1 = new MemberNohashNoEq("A");
        MemberNohashNoEq m2 = new MemberNohashNoEq("A");

        // No Override HashCode(), 객체의 메모리 주소 기반 해시코드를 반환
        System.out.println("m1.hashCode() = " + m1.hashCode());
        System.out.println("m2.hashCode() = " + m2.hashCode());
        // No Override equals(), Object 기능인 동일 비교
        System.out.println("m1.equals(m2) = " + m1.equals(m2));

        // 중복 등록, 인스턴스마다 주소값이 다르기 때문에
        set.add(m1);
        set.add(m2);
        System.out.println(set);

        // 검색 실패
        MemberNohashNoEq searchValue = new MemberNohashNoEq("A");
        System.out.println("searchValue.hashCode() = " + searchValue.hashCode());
        // contains() 안에 equals()의 동일 비교로 인하여 무조건 false
        boolean result = set.contains(searchValue);
        System.out.println("result = " + result);

    }
}
