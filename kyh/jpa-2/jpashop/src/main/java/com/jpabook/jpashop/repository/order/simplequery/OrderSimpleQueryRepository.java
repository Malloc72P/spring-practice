package com.jpabook.jpashop.repository.order.simplequery;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderSimpleQueryRepository {

    private final EntityManager entityManager;

    public List<SimpleOrderQueryDto> findOrderDTOS() {
        return entityManager.createQuery(
                "select new com.jpabook.jpashop.repository.order.simplequery.SimpleOrderQueryDto(" +
                        "o.id, o.member.name, o.orderDate, o.status, o.delivery.address) " +
                        "from Order o " +
                        "join o.member m " +
                        "join o.delivery d", SimpleOrderQueryDto.class
        ).getResultList();
    }
}
