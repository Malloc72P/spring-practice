package com.jpabook.jpashop.repository.order.query;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class OrderQueryRepository {

    private final EntityManager em;

    public List<OrderQueryDto> findOrderQueryDTOS() {
        List<OrderQueryDto> orders = findOrders();
        orders.forEach(o -> {
            List<OrderItemQueryDto> orderItems = findOrderItems(o.getOrderId());
            o.setOrderItems(orderItems);
        });
        return orders;
    }

    private List<OrderItemQueryDto> findOrderItems(Long orderId) {
        return em.createQuery("select new com.jpabook.jpashop.repository.order.query.OrderItemQueryDto" +
                        "(oi.order.id, i.name, oi.orderPrice, oi.count) " +
                        "from OrderItem oi " +
                        "join oi.item i " +
                        "where oi.order.id = :orderId", OrderItemQueryDto.class)
                .setParameter("orderId", orderId)
                .getResultList();
    }

    private List<OrderQueryDto> findOrders() {
        return em.createQuery("select new com.jpabook.jpashop.repository.order.query.OrderQueryDto" +
                                "(o.id, m.name, o.orderDate, o.status, d.address) " +
                                "from Order o " +
                                "join o.member m " +
                                "join o.delivery d",
                        OrderQueryDto.class)
                .getResultList();
    }

    public List<OrderQueryDto> optimizedFindOrderQueryDTOS() {
        //오더 전체 조회
        List<OrderQueryDto> orders = findOrders();

        //조회된 오더의 아이디 리스트 생성
        List<Long> orderIds = orders.stream()
                .map(OrderQueryDto::getOrderId)
                .collect(Collectors.toList());

        //오더 아이디 리스트로 관련된 모든 오더아이템과 아이템을 조회
        List<OrderItemQueryDto> orderItems = em.createQuery(
                        "select new com.jpabook.jpashop.repository.order.query.OrderItemQueryDto" +
                                "(oi.order.id, i.name, oi.orderPrice, oi.count) " +
                                "from OrderItem oi " +
                                "join oi.item i " +
                                "where oi.order.id in :orderIds", OrderItemQueryDto.class)
                .setParameter("orderIds", orderIds)
                .getResultList();

        //오더 아이디가 같은것 끼리 그룹으로 묶음
        Map<Long, List<OrderItemQueryDto>> orderItemMap = orderItems.stream()
                .collect(Collectors.groupingBy(OrderItemQueryDto::getOrderId));

        //오더에 오더 아이템즈를 넣음
        orders.forEach(o -> orderItemMap.get(o.getOrderId()));

        return orders;
    }
}
