package study.querydsl.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import study.querydsl.dto.MemberSearchCondition;
import study.querydsl.dto.MemberTeamDTO;
import study.querydsl.dto.QMemberTeamDTO;

import java.util.List;

import static org.springframework.util.StringUtils.hasText;
import static study.querydsl.entity.QMember.member;
import static study.querydsl.entity.QTeam.team;

@RequiredArgsConstructor
public class MemberRepositoryCustomImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<MemberTeamDTO> search(MemberSearchCondition condition) {
        return createSearchQuery(condition)
                .fetch();
    }

    @Override
    public Page<MemberTeamDTO> searchPageSimple(MemberSearchCondition condition, Pageable pageable) {
        QueryResults<MemberTeamDTO> queryResults = createSearchQuery(condition)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<MemberTeamDTO> content = queryResults.getResults();
        long total = queryResults.getTotal();

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public Page<MemberTeamDTO> searchPageComplex(MemberSearchCondition condition, Pageable pageable) {
        List<MemberTeamDTO> contents = createSearchQuery(condition)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return PageableExecutionUtils.getPage(contents, pageable, createCountQuery(condition)::fetchOne);
    }

    private JPAQuery<MemberTeamDTO> createSearchQuery(MemberSearchCondition condition) {
        return queryFactory
                .select(new QMemberTeamDTO(
                        member.id,
                        member.username,
                        member.age,
                        team.id,
                        team.name
                ))
                .from(member)
                .leftJoin(member.team, team)
                .where(searchCondition(condition));
    }

    private JPAQuery<Long> createCountQuery(MemberSearchCondition condition) {
        return queryFactory
                .select(member.id.count())
                .from(member)
                .leftJoin(member.team, team)
                .where(searchCondition(condition));
    }

    private BooleanBuilder searchCondition(MemberSearchCondition condition) {
        return new BooleanBuilder()
                .and(usernameEq(condition.getUsername()))
                .and(teamNameEq(condition.getTeamName()))
                .and(ageBetween(condition.getAgeLoe(), condition.getAgeGoe()));
    }

    private BooleanBuilder ageBetween(Integer ageLoeParam, Integer ageGoeParam) {
        return new BooleanBuilder()
                .and(ageLoe(ageLoeParam))
                .and(ageGoe(ageGoeParam));
    }

    private BooleanExpression ageLoe(Integer ageLoe) {
        return ageLoe != null ? member.age.loe(ageLoe) : null;
    }

    private BooleanExpression ageGoe(Integer ageGoe) {
        return ageGoe != null ? member.age.goe(ageGoe) : null;
    }

    private BooleanExpression teamNameEq(String teamName) {
        return hasText(teamName) ? team.name.eq(teamName) : null;
    }

    private BooleanExpression usernameEq(String username) {
        return hasText(username) ? member.username.eq(username) : null;
    }
}
