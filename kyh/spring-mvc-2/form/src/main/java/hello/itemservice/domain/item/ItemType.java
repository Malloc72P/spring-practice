package hello.itemservice.domain.item;

/**
 * 상품 유형을 위한 ENUM <br>
 * 설명을 위해 description 필드를 추가함
 *
 * @author na seungchul
 */
public enum ItemType {

    BOOK("도서"),
    FOOD("음식"),
    ETC("기타");

    private final String description;

    ItemType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
