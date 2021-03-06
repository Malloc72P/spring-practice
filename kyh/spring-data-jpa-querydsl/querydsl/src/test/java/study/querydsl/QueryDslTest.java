package study.querydsl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.querydsl.dto.MemberDTO;
import study.querydsl.dto.QMemberDTO;
import study.querydsl.dto.UserDTO;
import study.querydsl.entity.Member;
import study.querydsl.entity.QMember;
import study.querydsl.entity.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

import static com.querydsl.jpa.JPAExpressions.select;
import static org.assertj.core.api.Assertions.assertThat;
import static study.querydsl.entity.QMember.member;
import static study.querydsl.entity.QTeam.team;

@SpringBootTest
@Transactional
public class QueryDslTest {

    @Autowired
    private EntityManager em;

    @Autowired
    private EntityManagerFactory emf;

    private JPAQueryFactory queryFactory;

    @BeforeEach
    void before() {
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

        queryFactory = new JPAQueryFactory(em);
    }

    @Test
    void jpqlTest() {
        Member findMember = em.createQuery(
                        "select m from Member m " +
                                "where m.username = :username", Member.class)
                .setParameter("username", "member1")
                .getSingleResult();

        assertThat(findMember.getUsername()).isEqualTo("member1");
    }

    @Test
    void queryDslTest() {
        Member findMember = queryFactory.select(member)
                .from(member)
                .where(member.username.eq("member1"))
                .fetchOne();

        assert findMember != null;
        assertThat(findMember.getUsername()).isEqualTo("member1");
    }

    @Test
    void search() {
        Member findMember = queryFactory.selectFrom(member)
                .where(member.username.eq("member1"),
                        member.age.eq(10))
                .fetchOne();

        assertThat(findMember.getUsername()).isEqualTo("member1");
    }

    @Test
    void resultFetch() {
        List<Member> fetch = queryFactory
                .selectFrom(member)
                .fetch();

        Member fetchOne = queryFactory.selectFrom(member)
                .where(member.username.eq("member1"))
                .fetchOne();

        Member fetchFirst = queryFactory.selectFrom(member)
                .limit(1)
                .fetchFirst();

        QueryResults<Member> fetchResults = queryFactory.selectFrom(member)
                .fetchResults();

        long total = fetchResults.getTotal();
        List<Member> contents = fetchResults.getResults();
        System.out.println("total = " + total);

        for (Member content : contents) {
            System.out.println("content = " + content);
        }
    }

    @Test
    void sortTest() {
        em.persist(new Member(null, 100));
        em.persist(new Member("member5", 100));
        em.persist(new Member("member6", 100));

        List<Member> members = queryFactory.selectFrom(member)
                .where(member.age.eq(100))
                .orderBy(member.age.desc(), member.username.asc().nullsLast())
                .fetch();

        assertThat(members.get(0).getUsername()).isEqualTo("member5");
        assertThat(members.get(1).getUsername()).isEqualTo("member6");
        assertThat(members.get(2).getUsername()).isNull();
    }

    @Test
    void testPaging() {
        List<Member> members = queryFactory.selectFrom(member)
                .orderBy(member.username.desc())
                .offset(1)
                .limit(2)
                .fetch();

        assertThat(members.size()).isEqualTo(2);
    }

    @Test
    void testAggregation() {
        List<Tuple> result = queryFactory
                .select(
                        member.count(),
                        member.age.sum(),
                        member.age.avg(),
                        member.age.max(),
                        member.age.min()
                )
                .from(member)
                .fetch();

        Tuple tuple = result.get(0);
        assertThat(tuple.get(member.count())).isEqualTo(4);
        assertThat(tuple.get(member.age.sum())).isEqualTo(100);
        assertThat(tuple.get(member.age.avg())).isEqualTo(25);
        assertThat(tuple.get(member.age.max())).isEqualTo(40);
        assertThat(tuple.get(member.age.min())).isEqualTo(10);
    }

    /**
     * ??? ????????? ??? ?????? ?????? ????????? ?????????
     */
    @Test
    void groupByTest() {
        List<Tuple> result = queryFactory
                .select(team.name, member.age.avg())
                .from(member)
                .join(member.team, team)
                .groupBy(team.name)
                .orderBy(team.name.asc())
                .fetch();

        Tuple teamA = result.get(0);
        Tuple teamB = result.get(1);

        assertThat(teamA.get(team.name)).isEqualTo("teamA");
        assertThat(teamA.get(member.age.avg())).isEqualTo(15);

        assertThat(teamB.get(team.name)).isEqualTo("teamB");
        assertThat(teamB.get(member.age.avg())).isEqualTo(35);
    }

    /**
     * ??? A??? ????????? ?????? ?????? ??????
     */
    @Test
    void joinTest() {
        List<Member> teamA = queryFactory
                .selectFrom(member)
                .leftJoin(member.team, team)
                .where(team.name.eq("teamA"))
                .fetch();

        assertThat(teamA).extracting("username")
                .containsExactly("member1", "member2");
    }

    @Test
    void thetaJoin() {
        em.persist(new Member("teamA"));
        em.persist(new Member("teamB"));

        List<Member> result = queryFactory
                .select(member)
                .from(member, team)
                .where(member.username.eq(team.name))
                .fetch();

        assertThat(result)
                .extracting("username")
                .containsExactly("teamA", "teamB");
    }

    @Test
    void onTest() {
        em.persist(new Member("teamA"));
        em.persist(new Member("teamB"));

        List<Tuple> result = queryFactory
                .select(member, team)
                .from(member)
                .leftJoin(team)
                .on(member.username.eq(team.name))
                .fetch();

        for (Tuple tuple : result) {
            System.out.println("tuple = " + tuple);
        }
    }

    @Test
    void noFetchJoin() {
        em.flush();
        em.clear();
        Member findMember = queryFactory
                .selectFrom(member)
                .where(member.username.eq("member1"))
                .fetchOne();
        boolean loaded =
                emf.getPersistenceUnitUtil()
                        .isLoaded(findMember.getTeam());
        assertThat(loaded).as("?????? ?????? ?????????").isFalse();
    }

    @Test
    void yesFetchJoin() {
        em.flush();
        em.clear();
        Member findMember = queryFactory
                .selectFrom(member)
                .join(member.team, team)
                .fetchJoin()
                .where(member.username.eq("member1"))
                .fetchOne();
        boolean loaded =
                emf.getPersistenceUnitUtil()
                        .isLoaded(findMember.getTeam());
        assertThat(loaded).as("?????? ?????? ??????").isTrue();
    }

    @Test
    void subQuery() {
        QMember m1 = new QMember("m1");

        List<Member> result = queryFactory.selectFrom(member)
                .where(member.age.eq(
                        select(m1.age.max())
                                .from(m1)
                )).fetch();

        assertThat(result).extracting("age")
                .containsExactly(40);
    }

    @Test
    void subQueryOnSelect() {
        QMember m1 = new QMember("m1");

        List<Tuple> result = queryFactory
                .select(member.username,
                        select(m1.age.avg())
                                .from(m1)
                ).from(member)
                .fetch();

        for (Tuple tuple : result) {
            System.out.println("tuple = " + tuple);
        }
    }

    @Test
    void basicCaseTest() {
        List<String> fetch = queryFactory
                .select(member.age
                        .when(10).then("??????")
                        .when(20).then("?????????")
                        .otherwise("??????")
                ).from(member)
                .fetch();

        for (String s : fetch) {
            System.out.println("s = " + s);
        }
    }

    @Test
    void complexCaseTest() {
        List<String> fetch = queryFactory
                .select(new CaseBuilder()
                        .when(member.age.between(0, 20)).then("20??? ??????")
                        .when(member.age.between(21, 30)).then("21??? ??????")
                        .otherwise("??????")
                ).from(member)
                .fetch();

        for (String s : fetch) {
            System.out.println("s = " + s);
        }
    }

    @Test
    void constantTest() {
        List<Tuple> result = queryFactory
                .select(member.username, Expressions.constant("A"))
                .from(member)
                .fetch();

        for (Tuple tuple : result) {
            System.out.println("tuple = " + tuple);
        }
    }

    @Test
    void concatTest() {
        List<String> result = queryFactory
                .select(member.username.concat("_").concat(member.age.stringValue()))
                .from(member)
                .where(member.username.eq("member1"))
                .fetch();

        for (String s : result) {
            System.out.println("s = " + s);
        }
    }

    @Test
    void simpleProjectionTest() {
        List<String> result = queryFactory.select(member.username)
                .from(member)
                .fetch();

        for (String s : result) {
            System.out.println("s = " + s);
        }
    }

    @Test
    void tupleProjection() {
        List<Tuple> result = queryFactory.select(member.username, member.age)
                .from(member)
                .fetch();

        for (Tuple s : result) {
            System.out.println("s.get(member.username) = " + s.get(member.username));
            System.out.println("s.get(member.age) = " + s.get(member.age));
        }
    }

    @Test
    void dtoProjectionBySetter() {
        List<MemberDTO> result = queryFactory
                .select(Projections.bean(MemberDTO.class,
                        member.username,
                        member.age))
                .from(member)
                .fetch();

        for (MemberDTO memberDTO : result) {
            System.out.println("memberDto = " + memberDTO);
        }
    }

    @Test
    void dtoProjectionByField() {
        List<MemberDTO> result = queryFactory
                .select(Projections.fields(MemberDTO.class,
                        member.username,
                        member.age))
                .from(member)
                .fetch();

        for (MemberDTO memberDTO : result) {
            System.out.println("memberDto = " + memberDTO);
        }
    }

    @Test
    void dtoProjectionByConstructor() {
        List<MemberDTO> result = queryFactory
                .select(Projections.constructor(MemberDTO.class,
                        member.username,
                        member.age))
                .from(member)
                .fetch();

        for (MemberDTO memberDTO : result) {
            System.out.println("memberDto = " + memberDTO);
        }
    }

    @Test
    void dtoProjectionWithAlias() {
        List<UserDTO> result = queryFactory
                .select(Projections.fields(UserDTO.class,
                        member.username.as("name"),
                        member.age))
                .from(member)
                .fetch();

        for (UserDTO memberDTO : result) {
            System.out.println("memberDto = " + memberDTO);
        }
    }

    @Test
    void dtoProjectionWithSubQueryAlias() {
        QMember m1 = new QMember("m1");
        List<UserDTO> result = queryFactory
                .select(Projections.fields(UserDTO.class,
                        member.username.as("name"),
                        ExpressionUtils.as(
                                JPAExpressions
                                        .select(m1.age.max())
                                        .from(m1), "age")
                ))
                .from(member)
                .fetch();

        for (UserDTO memberDTO : result) {
            System.out.println("memberDto = " + memberDTO);
        }
    }

    @Test
    void dtoProjectionWithQueryProjection() {
        List<MemberDTO> result = queryFactory
                .select(new QMemberDTO(member.username, member.age))
                .from(member)
                .fetch();

        for (MemberDTO memberDTO : result) {
            System.out.println("memberDto = " + memberDTO);
        }
    }

    @Test
    void booleanBuilderTest() {
        String usernameParam = "member1";
        int ageParam = 10;

        List<Member> result = searchMember1(usernameParam, null);
        assertThat(result.size()).isEqualTo(1);
    }

    private List<Member> searchMember1(String usernameParam, Integer ageParam) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if (usernameParam != null) {
            booleanBuilder.and(member.username.eq(usernameParam));
        }
        if (ageParam != null) {
            booleanBuilder.and(member.age.eq(ageParam));
        }

        return queryFactory
                .selectFrom(member)
                .where(booleanBuilder)
                .fetch();
    }

    @Test
    void whereParamTest() {
        String usernameParam = "member1";
        int ageParam = 10;

        List<Member> result = searchMember2(usernameParam, null);
        assertThat(result.size()).isEqualTo(1);

    }

    private List<Member> searchMember2(String usernameParam, Integer ageParam) {
        return queryFactory
                .selectFrom(member)
                .where(allEq(usernameParam, ageParam))
                .fetch();
    }

    private BooleanExpression userNameEq(String usernameParam) {
        return usernameParam == null ? null : member.username.eq(usernameParam);
    }

    private BooleanExpression ageEq(Integer ageParam) {
        return ageParam == null ? null : member.age.eq(ageParam);
    }

    private Predicate allEq(String usernameParam, Integer ageCond) {
        return userNameEq(usernameParam).and(ageEq(ageCond));
    }

    @Test
    void batchUpdateTest1() {
        queryFactory
                .update(member)
                .set(member.username, "?????????")
                .where(member.age.lt(28))
                .execute();

        em.flush();
        em.clear();

        List<Member> result = queryFactory
                .selectFrom(member)
                .where(member.age.lt(28))
                .fetch();

        for (Member member1 : result) {
            System.out.println("member1 = " + member1);
        }
    }

    @Test
    void batchUpdateTest2() {
        queryFactory
                .update(member)
                .set(member.age, member.age.add(10))
                .where(member.age.lt(28))
                .execute();
    }

    @Test
    void bulkDelete() {
        em.flush();
        em.clear();

        queryFactory
                .delete(member)
                .where(member.age.lt(28))
                .execute();

        em.flush();
        em.clear();

        List<Member> result = queryFactory
                .selectFrom(member)
                .where(member.age.lt(28))
                .fetch();

        for (Member member1 : result) {
            System.out.println("member1 = " + member1);
        }
    }

    @Test
    void sqlFunctionCall() {
        List<String> result = queryFactory
                .select(
                        Expressions.stringTemplate(
                                "function('replace', {0}, {1}, {2})",
                                member.username, "member", "M"
                        )
                ).from(member)
                .fetch();
        for (String s : result) {
            System.out.println("s = " + s);
        }
    }

    @Test
    void sqlFunctionTest1() {
        List<String> result = queryFactory
                .select(
                        Expressions.stringTemplate("function('lower', {0})", member.username)
                ).from(member)
                .fetch();

        for (String s : result) {
            System.out.println("s = " + s);
        }
    }

    @Test
    void sqlFunctionTest2() {
        List<String> result = queryFactory
                .select(member.username.lower())
                .from(member)
                .fetch();

        for (String s : result) {
            System.out.println("s = " + s);
        }
    }
}
