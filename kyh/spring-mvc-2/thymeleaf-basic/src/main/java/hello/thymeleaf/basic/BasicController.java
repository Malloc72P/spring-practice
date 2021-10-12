package hello.thymeleaf.basic;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 타임리프 학습을 위한 예제 컨트롤러
 *
 * @author na seungchul
 */
@Controller
@RequestMapping("/basic")
public class BasicController {

    /**
     * 이스케이프 처리를 하는 경우에 대한 컨트롤러
     *
     * @param model 테스트 데이터 전달용
     * @return 이스케이프 처리를 하는 뷰의 이름
     */
    @GetMapping("/text-basic")
    public String textBasic(Model model) {
        model.addAttribute("data", "Hello Spring!");
        return "/basic/text-basic";
    }

    /**
     * 이스케이프 처리를 하지 않는 경우에 대한 컨트롤러
     *
     * @param model 테스트 데이터 전달용
     * @return 이스케이프 처리를 하지 않는 뷰의 이름
     */
    @GetMapping("/text-unescaped")
    public String textUnescaped(Model model) {
        model.addAttribute("data", "Hello <b>Spring!</b>");
        return "/basic/text-unescaped";
    }

}
