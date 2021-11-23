package com.jpabook.jpashop.domain;

import com.jpabook.jpashop.domain.item.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice;//주문당시가격
    private int count;//주문수량

    /*
     * 생성메서드를 가지고 있다
     * 그런데 생성자를 public으로 두면 외부에서 인스턴스를 생성할 수 있다.
     * 어디서는 생성메서드로 만들고, 어디서는 생성자로 만들어서 직접 설정한다면 나중에 유지보수가 어렵다
     * 그러니 생성자를 protected로 하자
     * private은 안된다. JPA에서 허용하지 않는다
     * 아니면 롬복을 사용하는것도 좋다
     * 조건을 많이 주는 개발방법은 좋다. 실수할 일을 줄이기 때문이다
     * @NoArgsConstructor(access = AccessLevel.PROTECTED)
     * */
//    protected OrderItem() {
//    }

    /*
     * 주문상품 생성 메서드
     *
     * 상품을 주문하였으니 재고수량을 까야 함
     * */
    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        item.removeStock(count);
        return orderItem;
    }

    /*
     * 비즈니스 로직 - 주문상품 취소
     * 재고 수량을 원복시켜준다
     * */
    public void cancel() {
        getItem().addStock(this.count);
    }

    /*
     * 조회 로직
     * */
    public int getTotalPrice() {
        return orderPrice * count;
    }
}
