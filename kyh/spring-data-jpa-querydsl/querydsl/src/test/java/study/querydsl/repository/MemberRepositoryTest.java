package study.querydsl.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import study.querydsl.dto.MemberSearchCondition;
import study.querydsl.dto.MemberTeamDTO;
import study.querydsl.entity.Member;
import study.querydsl.entity.Team;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static study.querydsl.entity.QMember.member;

@SpringBootTest
@Transactional
public class MemberRepositoryTest {

    @Autowired
    private EntityManager em;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void testRepository1() {
        Member member = new Member("member1");
        memberRepository.save(member);

        Member findMember = memberRepository.findById(member.getId()).get();
        assertThat(findMember).isEqualTo(member);

        List<Member> result1 = memberRepository.findAll();
        assertThat(result1).contains(member);

        List<Member> result2 = memberRepository.findByUsername("member1");
        assertThat(result2).contains(member);
    }

    @Test
    void searchTestSimple() {
        initTestData();

        MemberSearchCondition searchCondition = MemberSearchCondition.builder()
                .ageGoe(35)
                .ageLoe(40)
                .teamName("teamB")
                .build();

        List<MemberTeamDTO> memberTeamDTOS = memberRepository.search(searchCondition);

        assertThat(memberTeamDTOS)
                .extracting("username")
                .containsExactly("member4");
    }

    @Test
    void searchTestPaging() {
        initTestData();

        MemberSearchCondition searchCondition = MemberSearchCondition.builder()
                .build();

        PageRequest pageRequest = PageRequest.of(0, 3);

        Page<MemberTeamDTO> memberTeamDTOS = memberRepository.searchPageSimple(searchCondition, pageRequest);

        assertThat(memberTeamDTOS.getSize()).isEqualTo(3);

        assertThat(memberTeamDTOS.getContent())
                .extracting("username")
                .containsExactly("member1", "member2", "member3");
    }

    @Test
    void searchTestPaging2() {
        initTestData();

        MemberSearchCondition searchCondition = MemberSearchCondition.builder()
                .build();

        PageRequest pageRequest = PageRequest.of(0, 3);

        Page<MemberTeamDTO> memberTeamDTOPage = memberRepository.searchPageComplex(searchCondition, pageRequest);

        assertThat(memberTeamDTOPage.getTotalElements()).isEqualTo(4);

        assertThat(memberTeamDTOPage.getSize()).isEqualTo(3);

        assertThat(memberTeamDTOPage.getContent())
                .extracting("username")
                .containsExactly("member1", "member2", "member3");
    }

    @Test
    public void queryDslPredicateExecutorTest() {
        Iterable<Member> members = memberRepository.findAll(
                member.age.between(0, 40)
                        .and(member.username.eq("member1"))
        );
        for (Member member1 : members) {
            System.out.println("member1 = " + member1);
        }
    }

    private void initTestData() {
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");

        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 20, teamA);
        Member member3 = new Member("member3", 30, teamB);
        Member member4 = new Member("member4", 40, teamB);

        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);

        em.flush();
        em.clear();
    }
}
