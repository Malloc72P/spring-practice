package hello.thymeleaf.basic;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /**
     * 이스케이프 처리를 하지 않는 경우, 자바스크립트를 끼워넣을 수 있다
     *
     * @param model 테스트 데이터 전달용(자바스크립트가 들어있다)
     * @return 이스케이프 처리를 하지 않는 뷰의 이름
     */
    @GetMapping("/text-accident")
    public String noEscapeAccident(Model model) {
        model.addAttribute("data", "<script type='application/javascript'>alert('kaboom!')</script>");
        return "/basic/text-basic";
    }

    /**
     * 타임리프에서 변수 사용예제에 대한 컨트롤러
     *
     * @param model 테스트 데이터 전달용(List, Map)
     * @return 변수사용 예제가 있는 뷰의 이름
     */
    @GetMapping("/variable")
    public String variable(Model model) {
        User userA = new User("userA", 10);
        User userB = new User("userB", 20);

        List<User> list = new ArrayList<>();
        list.add(userA);
        list.add(userB);

        Map<String, User> map = new HashMap<>();
        map.put("userA", userA);
        map.put("userB", userB);

        model.addAttribute("user", userA);
        model.addAttribute("users", list);
        model.addAttribute("userMap", map);

        return "/basic/variable";
    }

    /**
     * 타임리프 변수 사용예제를 위해 임시로 만든 클래스
     */
    @Data
    @AllArgsConstructor
    static class User {
        private String username;
        private int age;
    }

    /**
     * 타임리프에서 기본객체에 접근하는 예제에 대한 컨트롤러.<br>
     * request, response, session 객체등에 접근해본다.<br>
     * 참고로 HTTP 세션은 사용자가 브라우저를 종료하기 전까지 유지된다
     *
     * @param session 타임리프에서 접근해볼 세션객체
     * @return 세션객체 예제가 있는 뷰의 이름
     */
    @GetMapping("/basic-objects")
    public String basicObjects(HttpSession session) {
        session.setAttribute("sessionData", "Hello Session");
        return "/basic/basic-objects";
    }

    /**
     * 타임리프는 뷰에서 빈에 바로 접근할 수 있다.<br>
     * 예제에서 이 빈에 접근해본다
     */
    @Component("helloBean")
    static class HelloBean {
        public String hello(String data) {
            return "Hello " + data;
        }
    }

}
