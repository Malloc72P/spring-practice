package hello.itemservice.domain.item;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 아이템 도메인 클래스
 * id, itemName, price는 null일 수 있어서 Wrapper클래스를 사용함
 * quantity는 null을 허용하지 않는다
 */
@Getter
@Setter
@ToString
public class Item {

    private Long id;
    private String itemName;
    private Integer price;
    private int quantity;

    protected Item() {
    }

    public Item(String itemName, Integer price, int quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
