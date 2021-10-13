package hello.thymeleaf.basic;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 템플릿 조각에 대해 학습하기 위한 컨트롤러
 *
 * @author na seungchul
 */
@Controller
@RequestMapping("/template")
public class TemplateController {

    /**
     * 템플릿 프래그먼트 예제에 대한 컨트롤러
     *
     * @return 템플릿 프래그먼트 예제의 뷰 이름
     */
    @GetMapping("/fragment")
    public String template() {
        return "template/fragment/fragmentMain";
    }

}
