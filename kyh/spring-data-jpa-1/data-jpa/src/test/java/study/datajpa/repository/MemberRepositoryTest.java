package study.datajpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;
import study.datajpa.entity.Team;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Test
    public void testMember() {
        System.out.println("memberRepository.getClass() = " + memberRepository.getClass());
        Member member = new Member("memberA");

        Member savedMember = memberRepository.save(member);

        Member findMember = memberRepository.findById(savedMember.getId())
                .orElseThrow(NoSuchElementException::new);

        assertThat(findMember)
                .extracting(
                        Member::getId,
                        Member::getUsername
                )
                .containsExactly(
                        savedMember.getId(),
                        savedMember.getUsername()
                );
        assertThat(findMember).isEqualTo(savedMember);
    }

    @Test
    public void findByUsernameAndAgeGreaterThen() {
        Member m1 = new Member("AAA", 20);
        Member m2 = new Member("BBB", 10);

        memberRepository.save(m1);
        memberRepository.save(m2);

        List<Member> result = memberRepository.findByUsernameAndAgeGreaterThan("AAA", 15);
        assertThat(result.get(0).getUsername()).isEqualTo("AAA");
        assertThat(result.get(0).getAge()).isGreaterThan(15);
        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    public void findHelloBy() {
        memberRepository.findHelloBy();
    }

    @Test
    void findByUsername() {
        Member m1 = new Member("AAA", 20);
        Member m2 = new Member("BBB", 10);

        memberRepository.save(m1);
        memberRepository.save(m2);

        List<Member> result = memberRepository.findByUsername("AAA");
        assertThat(result.get(0).getUsername()).isEqualTo("AAA");
        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    void testQuery() {
        Member m1 = new Member("AAA", 20);
        Member m2 = new Member("BBB", 10);

        memberRepository.save(m1);
        memberRepository.save(m2);

        List<Member> result = memberRepository.findUser("AAA", 20);
        assertThat(result.get(0).getUsername()).isEqualTo("AAA");
        assertThat(result.get(0).getAge()).isEqualTo(20);
        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    void testUsernameList() {
        Member m1 = new Member("AAA", 20);
        Member m2 = new Member("BBB", 10);

        memberRepository.save(m1);
        memberRepository.save(m2);

        List<String> usernameList = memberRepository.findUsernameList();
        assertThat(usernameList).contains("AAA", "BBB");
    }

    @Test
    void testFindMemberAsMemberDto() {
        Team team = new Team("teamA");
        teamRepository.save(team);

        Member m1 = new Member("AAA", 20);
        Member m2 = new Member("BBB", 10);

        m1.changeTeam(team);
        m2.changeTeam(team);

        memberRepository.save(m1);
        memberRepository.save(m2);

        List<MemberDto> memberDtos = memberRepository.findMemberAsMemberDto();
        assertThat(memberDtos).contains(
                new MemberDto(m1.getId(), m1.getUsername(), team.getName()),
                new MemberDto(m2.getId(), m2.getUsername(), team.getName())
        );
    }

    @Test
    void findByNames() {
        Member m1 = new Member("AAA", 20);
        Member m2 = new Member("BBB", 10);

        memberRepository.save(m1);
        memberRepository.save(m2);

        List<Member> members = memberRepository.findByNames(Arrays.asList("AAA", "BBB"));
        assertThat(members).contains(m1, m2);
    }

    @Test
    void returnType() {
        Member m1 = new Member("AAA", 20);
        Member m2 = new Member("BBB", 10);

        memberRepository.save(m1);
        memberRepository.save(m2);

        List<Member> members = memberRepository.findListByUsername("AAA");
        Optional<Member> optionalMember = memberRepository.findOptionalMemberByUsername("AAA");
        Member member = memberRepository.findMemberByUsername("AAA");

        assertThat(members.size()).isEqualTo(1);
        assertThat(members).contains(m1);

        assertThat(optionalMember.get()).isSameAs(m1);

        assertThat(member).isSameAs(m1);
    }
}
