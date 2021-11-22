package com.jpabook.jpashop.service;

import com.jpabook.jpashop.domain.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

//스프링 통합 테스트를 위해서 SpringBootTest를 한다
//테스트에 transactional이 있으면, 테스트 후에 롤백한다
@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Test
    //@Rollback(value = false)
    public void 회원가입() throws Exception {
        //given
        Member member = new Member();
        member.setName("kim");

        //when
        Long savedId = memberService.join(member);
        Member foundMember = memberService.findOne(savedId);

        //then
        assertThat(member).isSameAs(foundMember);
        assertThat(member)
                .extracting(
                        Member::getId,
                        Member::getName)
                .contains(
                        foundMember.getId(),
                        foundMember.getName()
                );
    }

    @Test
    public void 중복_회원_예외() throws Exception {
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");

        memberService.join(member1);
        assertThatThrownBy(() -> memberService.join(member2))
                .isInstanceOf(IllegalStateException.class);
    }

}