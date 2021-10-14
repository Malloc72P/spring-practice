package hello.itemservice.domain.item;

import lombok.Data;

import java.util.List;

/**
 * 상품 클래스 <br>
 * <ul>
 *     <li>open : 판매 여부</li>
 *     <li>regions : 등록 지역</li>
 *     <li>itemType : 상품 종류</li>
 *     <li>deliveryCode : 배송 방식</li>
 * </ul>
 * @author na seungchul
 */
@Data
public class Item {

    private Long id;
    private String itemName;
    private Integer price;
    private Integer quantity;

    private Boolean open;
    private List<String> regions;
    private ItemType itemType;
    private String deliveryCode;

    public Item() {
    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
