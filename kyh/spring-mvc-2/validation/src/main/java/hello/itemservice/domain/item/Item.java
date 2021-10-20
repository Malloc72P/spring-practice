package hello.itemservice.domain.item;

import lombok.AllArgsConstructor;
import lombok.Data;

/*
@ScriptAssert(
        lang = "javascript", script = "_this.price * _this.quantity >= 10000",
        message = "총 합이 10000을 넘어야 합니다"
)
*/
@Data
@AllArgsConstructor
public class Item {

    private Long id;
    private String itemName;
    private Integer price;
    private Integer quantity;

/*    @NotNull(groups = UpdateCheck.class)
    private Long id;

    @NotBlank(groups = {SaveCheck.class, UpdateCheck.class})
    private String itemName;

    @NotNull(groups = {SaveCheck.class, UpdateCheck.class})
    @Range(min = 1000, max = 1_000_000)
    private Integer price;

    @NotNull(groups = {SaveCheck.class, UpdateCheck.class})
    @Max(value = 9999, groups = SaveCheck.class)
    private Integer quantity;*/

    public Item() {
    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }

    public boolean isTotalPriceInRange() {
        if (isPriceAndQuantityNull()) {
            return false;
        }
        return totalPrice() > 10000;
    }

    public int totalPrice() {
        if (isPriceAndQuantityNull()) {
            return 0;
        }
        return price * quantity;
    }

    public boolean isPriceAndQuantityNull() {
        return price == null || quantity == null;
    }
}
