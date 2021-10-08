package hello.springmvc.basic.response;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 뷰 템플릿으로 응답하는 경우에 대해 실습합니다
 */
@Controller
public class ResponseViewController {

    /**
     * ModelAndView를 통해 뷰 템플릿에 데이터를 넘깁니다
     *
     * @return ModelAndView 사용
     */
    @RequestMapping("/response-view-v1")
    public ModelAndView responseViewV1() {
        ModelAndView modelAndView = new ModelAndView("response/hello");
        modelAndView.addObject("data", "hello");
        return modelAndView;
    }

    /**
     * 문자열을 반환하는 경우, 리턴값은 뷰의 논리적인 이름이 됩니다
     * 모델은 매개변수를 통해 받아서 쓸 수 있습니다
     *
     * @param model 뷰 템플릿으로 데이터를 보내는 용도입니다
     * @return 뷰의 논리명을 반환합니다.
     */
    @RequestMapping("/response-view-v2")
    public String responseViewV2(Model model) {
        model.addAttribute("data", "hello");
        return "response/hello";
    }

    /**
     * 컨트롤러로 @Controller를 쓰는데 Void를 반환하는 경우, 요청 URL을 뷰의 이름으로 사용합니다
     * 불명확하므로 권장되지 않습니다
     *
     * @param model 뷰 템플릿으로 데이터를 전달합니다
     */
    @RequestMapping("/response/hello")
    public void responseViewV3(Model model) {
        model.addAttribute("data", "hello");
    }
}
