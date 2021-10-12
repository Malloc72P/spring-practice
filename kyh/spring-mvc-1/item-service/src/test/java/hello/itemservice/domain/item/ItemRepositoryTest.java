package hello.itemservice.domain.item;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 아이템 리포지토리 테스트(스프링 통합 테스트 X)
 *
 * @author na seungchul
 */
class ItemRepositoryTest {

    /**
     * 테스트 대상
     * <ul>
     *     <li>{@link ItemRepository}</li>
     * </ul>
     */
    ItemRepository itemRepository = new ItemRepository();

    /**
     * 저장기능 테스트
     */
    @Test
    void save() {
        //given
        Item itemA = new Item("itemA", 10000, 10);

        //when
        Item savedOne = itemRepository.save(itemA);

        //then
        Item foundItem = itemRepository.findById(savedOne.getId());
        assertThat(foundItem).isEqualTo(savedOne);
    }

    /**
     * 아이템 전체조회 테스트
     */
    @Test
    void findALl() {
        //given
        Item item1 = new Item("item1", 10000, 10);
        Item item2 = new Item("item2", 20000, 20);

        itemRepository.save(item1);
        itemRepository.save(item2);

        //when
        List<Item> items = itemRepository.findAll();


        //then
        assertThat(items.size()).isEqualTo(2);
        assertThat(items).contains(item1, item2);
    }

    /**
     * 아이템 수정 테스트
     */
    @Test
    void updateItem() {
        //given
        Item item = new Item("item1", 10000, 10);

        Item savedOne = itemRepository.save(item);
        Long savedOneId = savedOne.getId();

        //when
        Item updateParam = new Item("item2", 20000, 30);
        itemRepository.update(savedOneId, updateParam);

        //then
        Item foundItem = itemRepository.findById(savedOneId);

        assertThat(foundItem.getItemName()).isEqualTo(item.getItemName());
        assertThat(foundItem.getPrice()).isEqualTo(item.getPrice());
    }
}