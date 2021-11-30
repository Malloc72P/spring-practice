package study.datajpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;
import study.datajpa.entity.Team;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback(false)
class MemberRepositoryTest {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private MemberQueryRepository memberQueryRepository;

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

    @Test
    void paging() {
        List<Member> members = Arrays.asList(
                new Member("m1", 10),
                new Member("m2", 10),
                new Member("m3", 10),
                new Member("m4", 10),
                new Member("m5", 10),
                new Member("m6", 10),
                new Member("m7", 10)
        );
        memberRepository.saveAll(members);

        int age = 10;
        int pageNum = 0;
        int size = 3;

        PageRequest pageRequest = PageRequest.of(pageNum, size, Sort.by(Sort.Direction.DESC, "username"));

        Page<Member> memberPage = memberRepository.findByAge(age, pageRequest);

        //페이지 개수 계산
        assertThat(memberPage.getTotalPages()).isEqualTo(3);

        //현재 페이지 계산
        assertThat(memberPage.getNumber()).isEqualTo(0);

        //첫번째, 마지막 페이지인지, 그리고 다음 페이지 있는지 검증
        assertThat(memberPage.isFirst()).isTrue();
        assertThat(memberPage.hasNext()).isTrue();
        assertThat(memberPage.isLast()).isFalse();

        //컨텐츠 조회
        assertThat(memberPage.getContent().size()).isEqualTo(3);
        assertThat(memberPage.getContent()).contains(
                members.get(6),
                members.get(5),
                members.get(4));

        //전체 엘리먼트 개수 조회
        assertThat(memberPage.getTotalElements()).isEqualTo(7);

        Page<MemberDto> memberDtoPage = memberPage
                .map(member -> new MemberDto(member.getId(), member.getUsername(), null));
    }

    @Test
    void bulkUpdate() {
        List<Member> members = Arrays.asList(
                new Member("m1", 20),
                new Member("m2", 20),
                new Member("m3", 20),
                new Member("m4", 20),
                new Member("m5", 20),
                new Member("m6", 20),
                new Member("m7", 20)
        );
        memberRepository.saveAll(members);

        int i = memberRepository.bulkAgePlus(20);
        assertThat(i).isEqualTo(7);

        List<Member> m7 = memberRepository.findByUsername("m7");
        assertThat(m7.get(0).getAge()).isEqualTo(21);
    }

    @Test
    public void findMemberLazy() {
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");

        teamRepository.save(teamA);
        teamRepository.save(teamB);

        Member member1 = new Member("member1");
        Member member2 = new Member("member2");

        member1.changeTeam(teamA);
        member2.changeTeam(teamB);

        memberRepository.save(member1);
        memberRepository.save(member2);

        em.flush();
        em.clear();

        List<Member> members = memberRepository.findAll();

        for (Member member : members) {
            System.out.println("member.getUsername() = " + member.getUsername());
            System.out.println("team = " + member.getTeam().getName());
        }
    }

    @Test
    public void findMemberFetch() {
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");

        teamRepository.save(teamA);
        teamRepository.save(teamB);

        Member member1 = new Member("member1");
        Member member2 = new Member("member2");

        member1.changeTeam(teamA);
        member2.changeTeam(teamB);

        memberRepository.save(member1);
        memberRepository.save(member2);

        em.flush();
        em.clear();

        List<Member> members = memberRepository.findWithNamedEntityGraph();

        for (Member member : members) {
            System.out.println("member.getUsername() = " + member.getUsername());
            System.out.println("team = " + member.getTeam().getName());
        }
    }

    @Test
    public void findEntityGraphByUsername() {
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");

        teamRepository.save(teamA);
        teamRepository.save(teamB);

        Member member1 = new Member("member1");
        Member member2 = new Member("member2");

        member1.changeTeam(teamA);
        member2.changeTeam(teamB);

        memberRepository.save(member1);
        memberRepository.save(member2);

        em.flush();
        em.clear();

        List<Member> members = memberRepository.findEntityGraphByUsername("member1");

        for (Member member : members) {
            System.out.println("member.getUsername() = " + member.getUsername());
            System.out.println("team = " + member.getTeam().getName());
        }
    }

    @Test
    void queryHint() {
        Member member1 = new Member("member1", 10);
        memberRepository.save(member1);

        em.flush();
        em.clear();

        Member findMember = memberRepository.findReadOnlyByUsername("member1");
        findMember.setUsername("member2");
    }

    @Test
    void queryLock() {
        Member member1 = new Member("member1", 10);
        memberRepository.save(member1);

        em.flush();
        em.clear();

        Member findMember = memberRepository.findLockByUsername("member1");
        findMember.setUsername("member2");
    }

    @Test
    public void callCustom() {
        List<Member> result = memberRepository.findMemberCustom();
    }
}
