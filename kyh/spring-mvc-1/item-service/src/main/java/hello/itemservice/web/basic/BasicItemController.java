package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * 상품 관련 요청을 처리하는 컨트롤러
 *
 * @author na seungchul
 */
@Slf4j
@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {

    private final ItemRepository itemRepository;

    /**
     * 테스트를 위한 초기화 메서드.
     * 더미데이터를 넣는다
     */
    @PostConstruct
    public void init() {
        itemRepository.save(new Item("itemA", 10000, 10));
        itemRepository.save(new Item("itemB", 20000, 20));
    }

    /**
     * 상품 목록조회요청을 처리하는 컨트롤러
     *
     * @param model 모델 - 상품목록인 items가 담긴다
     * @return 뷰 네임 - 상품목록페이지
     */
    @GetMapping()
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }

    /**
     * 상품 상세조회 요청을 처리하는 컨트롤러
     *
     * @param itemId 상세조회할 상품의 아이디
     * @param model  모델 - 조회된 상품이 담긴다
     * @return 뷰 네임 - 상품 상세조회 페이지
     */
    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "/basic/item";
    }

    /**
     * 상품 등록 페이지 요청을 처리하는 컨트롤러
     *
     * @return 뷰 네임 - 상품등록페이지
     */
    @GetMapping("/add")
    public String addForm() {
        log.info("addForm");
        return "/basic/addForm";
    }

    /**
     * 상품 저장 요청을 처리하는 컨트롤러.
     *
     * @param item 저장할 상품정보를 담은 변수(요청파라미터를 Item으로 변환함)
     * @return 뷰 네임 - 상품 상세 페이지
     */
    @PostMapping("/add")
    public String save(Item item) {
        itemRepository.save(item);
        return "/basic/item";
    }
}
