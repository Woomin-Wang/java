package inflearn_java_middle.collection.set.member;

public class MemberNohashNoEq {

    private String id;

    public MemberNohashNoEq(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "MemberNohashNoEq{" +
                "id='" + id + '\'' +
                '}';
    }
}
