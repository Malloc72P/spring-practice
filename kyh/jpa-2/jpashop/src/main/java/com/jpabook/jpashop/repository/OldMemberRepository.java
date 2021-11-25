package com.jpabook.jpashop.repository;

import com.jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

//이게 있으면 ComponentScan에 의해 자동으로 스캔되고 빈으로 등록되고 관리된다
@Repository
@RequiredArgsConstructor
public class OldMemberRepository {

    //@PersistenceContext
    //JPA가 제공하느 표준 애너테이션이다
    //이게 있으면 JPA 엔티티 매니저를 여기에 주입해준다
    //스프링이 엔티티 매니저를 만들고 주입해준다
    //이것 역시 생성자 주입을 사용할 수 있다
    //원래 EntityManager는 PersistenceContext 애너테이션을 사용해야만 주입이 가능하다. Autowired는 안된다
    //근데 스프링 부트라서 생성자주입이 가능한 것이다
    private final EntityManager entityManager;

    public void save(Member member) {
        entityManager.persist(member);
        /*퍼시스트하면 영속성 컨택스트에 객체를 넣는다
        트랜잭션이 커밋되는 시점에 디비에 insert된다
        * */
    }

    public Member findOne(Long id) {
        return entityManager.find(Member.class, id);
    }

    /*
    JPQL을 사용함
    JPQL은 SQL과 딜리 from의 대상이 테이블이 아닌 엔티티임
    SQL은 테이블을 대상, JPQL은 엔티티, 객체를 대상으로 함
    * */
    public List<Member> findAll() {
        return entityManager.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public List<Member> findByName(String name) {
        return entityManager.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}
