package com.jpabook.jpashop.service;

import com.jpabook.jpashop.domain.Delivery;
import com.jpabook.jpashop.domain.Member;
import com.jpabook.jpashop.domain.Order;
import com.jpabook.jpashop.domain.OrderItem;
import com.jpabook.jpashop.domain.item.Item;
import com.jpabook.jpashop.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/*
 * 오더의 핵심 비즈니스 로직은 오더 클래스 안에 있다
 * 오더서비스는 단순히 리포지토리로 엔티티 조회하고 저장하며, 핵심로직은 엔티티의 메서드를 호출해서 처리한다
 * 엔티티에 핵심 비즈니스 로직을 몰아 넣는 방식을 도메인 모델 패턴이라고 한다
 *
 * 트랜잭셔 스크립트 패턴은 반대로 서비스 계층에 핵심 비즈니스 로직이 들어간다
 * */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    /*
     * 주문하기 기능
     * Order의 orderItems와 delivery는 cascade가 되어있어서 order를 persist하면 전파된다
     * cascade의 범위를 정하는게 문제다
     * 오더가 딜리버리를 관리하고 오더가 오더아이템을 관리한다.
     * 딜리버리는 오더말고는 아무도 안쓴다. 오더 아이템도 마찬가지이다.
     * 이런 경우엔 cascade로 관리해도 좋다
     * 다른곳에서 참조하는게 아니어야 한다
     *
     * 트랜잭션이 커밋되고 엔티티매니저가 플러시되는 순간 디비에 쿼리가 날아간다
     *
     * 생성로직이 서비스에도 있고 엔티티에도 있고 이렇게 분산되어 있으면 유지보수가 어렵다.
     * 생성에 문제가 있으면 봐야 할 곳도 많고 어디서 뭘 하는지를 봐야 한다
     * 그러니 한군데에서 책임지고 하는게 좋다
     * */
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {
        //엔티티 조회
        Member member = memberRepository.findById(memberId).get();
        Item item = itemRepository.findOne(itemId);

        //배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        //주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        //주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        //주문 저장
        orderRepository.save(order);

        return order.getId();
    }

    /*
     * 주문취소 기능
     *
     * 근데 핵심비즈니스로직은 Order안에 있다
     * JPA의 강점이 여기에 있다
     * 마이바티스나 jdbcTemplate등 직접 sql을 날리면, 데이터를 변경한 다음 업데이트 쿼리를 직접 짜서 날려야 한다
     * 아이템의 재고를 올려주는 쿼리도 날리고 그래야 한다.
     * 서비스 계층에서 비즈니스 로직을 쓸수밖에 없다
     * 근데 JPA를 활용하면 데이터만 바꾸면 JPA가 알아서 바뀐 변경 포인트를 감지하면서
     * 변경내역을 찾아서 디비에 업데이트 쿼리가 날아간다(더티체킹)
     * */
    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findOne(orderId);
        order.cancel();
    }

    public List<Order> findOrders(OrderSearch orderSearch) {
//        return orderRepository.findAllByCriteria(orderSearch);
        return orderRepository.findAll(orderSearch);
    }
}
