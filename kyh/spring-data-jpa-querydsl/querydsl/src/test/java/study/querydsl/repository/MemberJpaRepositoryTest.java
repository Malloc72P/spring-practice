package study.querydsl.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.querydsl.dto.MemberSearchCondition;
import study.querydsl.dto.MemberTeamDTO;
import study.querydsl.entity.Member;
import study.querydsl.entity.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class MemberJpaRepositoryTest {

    @Autowired
    private MemberJpaRepository memberRepository;

    @Autowired
    private EntityManager em;

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
    void testRepository2() {
        Member member = new Member("member1");
        memberRepository.save(member);

        Member findMember = memberRepository.findById(member.getId()).get();
        assertThat(findMember).isEqualTo(member);

        List<Member> result1 = memberRepository.findAll();
        assertThat(result1).contains(member);

        List<Member> result2 = memberRepository.findByUsername("member1");
        assertThat(result2).contains(member);

        List<Member> result3 = memberRepository.findByUsernameWithQueryDSL("member1");
        assertThat(result2).contains(member);
    }

    @Test
    void searchTestBuilder() {
        initTestData();

        MemberSearchCondition searchCondition = MemberSearchCondition.builder()
                .ageGoe(35)
                .ageLoe(40)
                .teamName("teamB")
                .build();

        List<MemberTeamDTO> memberTeamDTOS = memberRepository.searchByBuilder(searchCondition);

        assertThat(memberTeamDTOS)
                .extracting("username")
                .containsExactly("member4");
    }

    @Test
    void searchTestWhereParameter() {
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
