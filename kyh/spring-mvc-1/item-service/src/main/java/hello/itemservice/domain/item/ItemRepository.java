package hello.itemservice.domain.item;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 아이템 리포지토리 클래스
 *
 * @author na seungchul
 */
@Repository
public class ItemRepository {

    /**
     * 멀티스레드 환경에서의 싱글톤 빈이므로 동시성 문제가 있다
     * 그래서 원래는 해시맵을 사용하면 안되고, ConcurrentHashMap과 같은 것을 사용해야 한다
     * 키도 AtomicLong을 사용해야 한다
     */
    private static final Map<Long, Item> store = new HashMap<>();
    private static long sequence = 0L;

    /**
     * 아이템 저장
     *
     * @param item 저장할 아이템
     * @return 저장된 아이템
     */
    public Item save(Item item) {
        item.setId(++sequence);
        store.put(item.getId(), item);
        return item;
    }

    /**
     * 아이템 전체 조회
     *
     * @return 조회된 아이템 List
     */
    public List<Item> findAll() {
        return new ArrayList<>(store.values());
    }

    /**
     * 아이템 검색(아이디)
     *
     * @param id 검색할 아이템의 아이디
     * @return 검색된 아이템
     */
    public Item findById(Long id) {
        return store.get(id);
    }

    /**
     * 아이템 수정
     *
     * @param itemId      수정할 아이템의 아이디
     * @param updateParam 수정내용. 여기에 담긴 정보로 아이템을 수정함
     */
    public void update(Long itemId, Item updateParam) {
        Item findItem = findById(itemId);
        findItem.setItemName(updateParam.getItemName());
        findItem.setPrice(updateParam.getPrice());
        findItem.setQuantity(updateParam.getQuantity());
    }

    /**
     * 테스트용 메서드. 상점 정보를 초기화함
     */
    public void clearStore() {
        store.clear();
    }
}
