package com.jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "orders")//order는 예약어라서 쓰면 안되니까 이렇게 정해줘야 함
@Getter
@Setter
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = LAZY)//여러 주문은 하나의 멤버와 관계를 맺으므로 다대일
    @JoinColumn(name = "member_id")//매핑을 어떤 컬럼으로 할지를 정하는 애너테이션
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery = new Delivery();

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    //연관관계 메서드
    public void setMember(Member member) {
        this.member = member;//자신의 필드에도 넣는다
        member.getOrders().add(this);//연관관계의 상대측에도 자신의 객체를 넣는다
    }

    public void addOrderItem(OrderItem orderItem) {
        this.orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    /*
     * 생성 메서드
     * 관계가 얽혀있는 객체는 생성하는게 복잡하다
     * 이러한 객체는 생성 메서드를 만드는게 좋다
     * 연관관계, 주문시간 등의 필요한 정보들을 세팅하자
     * 이러한 방식으로 하면 생성지점 변경시 생성메서드만 수정하면 된다
     * */
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    /*
    * 주문 취소
    * 이미 배송돈 상품은 취소 불가능
    * */
    public void cancel() {
        if (delivery.getStatus() == DeliveryStatus.COMP) {
            throw new IllegalStateException("이미 배송 완료된 상품은 취소가 불가능합니다");
        }
        this.setStatus(OrderStatus.CANCEL);
        for (OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
    }

    /*
     * 조회 로직
     * 각각의 OrderItem에게 총 가격을 물어본다
     * */
    public int getTotalPrice() {
        int totalPrice = 0;
        for (OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }
}
