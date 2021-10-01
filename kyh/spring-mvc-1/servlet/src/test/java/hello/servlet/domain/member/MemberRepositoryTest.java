package hello.servlet.domain.member;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MemberRepositoryTest {

    MemberRepository memberRepository = MemberRepository.getInstance();

    /*
     * 각각의 테스트 결과가 다른 테스트에 영향을 주지 않도록 리셋한다.
     * 각각의 테스트가 끝난 다음 호출된다
     * */
    @AfterEach
    void afterEach() {
        memberRepository.clearStore();
    }

    @Test
    void save() {
        Member hello = new Member("hello", 20);
        Member savedMember = memberRepository.save(hello);
        Member findMember = memberRepository.findById(savedMember.getId());
        assertThat(findMember).isEqualTo(savedMember);
    }

    @Test
    void findAll() {
        Member member1 = new Member("member1", 20);
        Member member2 = new Member("member2", 30);

        memberRepository.save(member1);
        memberRepository.save(member2);

        List<Member> memberList = memberRepository.findAll();

        assertThat(memberList.size()).isEqualTo(2);
        assertThat(memberList).contains(member1, member2);
    }
}
