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

    /**
     * 템플릿 레이아웃 예제에 대한 컨트롤러
     *
     * @return 템플릿 레이아웃 예제의 뷰 이름
     */
    @GetMapping("/layout")
    public String layout() {
        return "template/layout/layoutMain";
    }

    /**
     * 템플릿 레이아웃을 확장한 예제에 대한 컨트롤러 <br>
     * 모든 페이지의 레이아웃을 정한 경우에 대한 예제이다
     *
     * @return 해당 예제의 뷰 이름
     */
    @GetMapping("/layoutExtend")
    public String layoutExtend() {
        return "template/layoutExtend/layoutExtendMain";
    }
}
