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

    @OneToOne(fetch = LAZY)
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

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }
}
