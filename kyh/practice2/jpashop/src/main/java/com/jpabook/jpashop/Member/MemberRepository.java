package com.jpabook.jpashop.Member;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class MemberRepository {

    @PersistenceContext//이 애너테이션이 있으면 스프링부트가 엔티티 매니저를 주입해준다
    private EntityManager entityManager;
    //spring-boot-starter-data-jpa를 등록하면 엔티티매니저를 생성하는등의 작업이 자동으로 들어간다
    //엔티티매니저 생성하는 등의 코드도 자동으로 들어간다

    public Long save(Member member) {
        //매개변수 member는 저장하고 나면 사이드이펙트를 일으킬 수 있다
        //대신 아이디만 있으면 다시 조회해서 쓸 수 있으니까 아이디 정도만 리턴한다
        entityManager.persist(member);
        return member.getId();
    }

    public Member find(Long id) {
        return entityManager.find(Member.class, id);
    }
}
