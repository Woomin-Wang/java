package inflearn_java_advanced2.io.member;

import java.util.List;

public interface MemberRepository {

    void add(Member member);

    List<Member> findAll();
}
