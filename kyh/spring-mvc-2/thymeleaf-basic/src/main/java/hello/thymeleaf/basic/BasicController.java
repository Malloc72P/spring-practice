package hello.thymeleaf.basic;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
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

    /**
     * 타임리프에서 LocalDateTime을 다루는 방법에 대한 예제의 컨트롤러
     *
     * @param model 테스트 데이터 전달용(LocalDateTime)
     * @return 타임리프에서 LocalDateTime을 다루는 방법에 대한 예제의 뷰의 이름
     */
    @GetMapping("/date")
    public String date(Model model) {
        model.addAttribute("localDateTime", LocalDateTime.now());
        return "/basic/date";
    }

    /**
     * 타임리프에서 링크를 입력할 때는 @{...} 표현식을 사용해야한다
     *
     * @param model 뷰에서 만들 링크에 필요한 파라미터를 담는다
     * @return 링크표현식 예제에 대한 뷰의 이름
     */
    @GetMapping("/link")
    public String link(Model model) {
        model.addAttribute("param1", "data1");
        model.addAttribute("param2", "data2");
        return "/basic/link";
    }

    /**
     * 리터럴 예제에 대한 컨트롤러
     *
     * @param model 리터럴 예제에 필요한 데이터를 전달하는 모델객체
     * @return 리터럴 예제에 대한 뷰의 이름
     */
    @GetMapping("/literal")
    public String literal(Model model) {
        model.addAttribute("data", "Spring!");
        return "/basic/literal";
    }

    /**
     * 타임리프 연산 예제에 대한 컨트롤러
     *
     * @param model 데이터가 null인 경우와 아닌 경우를 테스트 해보기 위한 데이터를 전달한다
     * @return 연산 예제에 대한 뷰의 이름
     */
    @GetMapping("/operation")
    public String operation(Model model) {
        model.addAttribute("nullData", null);
        model.addAttribute("data", "Spring!");
        return "/basic/operation";
    }

    /**
     * 타임리프 속성설정 예제에 대한 컨트롤러
     *
     * @return 속성설정 예제 뷰의 이름
     */
    @GetMapping("/attribute")
    public String attribute() {
        return "/basic/attribute";
    }

    /**
     * 타임리프 each 예제를 위한 컨트롤러
     *
     * @param model 테스트 데이터 전달용(users, 원소 3개)
     * @return each 예제의 뷰 이름
     */
    @GetMapping("/each")
    public String each(Model model) {
        addUsers(model);
        return "/basic/each";
    }

    /**
     * 조건부 평가 예제에 대한 컨트롤러
     *
     * @param model 테스트 데이터 전달용(users 원소 3개)
     * @return 조건부 평가 예제의 뷰 이름
     */
    @GetMapping("/condition")
    public String condition(Model model) {
        addUsers(model);
        return "/basic/condition";
    }

    /**
     * 타임리프 주석예제에 대한 컨트롤러
     *
     * @param model 테스트 데이터 전달용 모델객체
     * @return 주석예제의 뷰 이름
     */
    @GetMapping("/comments")
    public String comments(Model model) {
        model.addAttribute("data", "Spring!");
        return "/basic/comments";
    }

    /**
     * 타임리프 블록예제에 대한 컨트롤러
     *
     * @param model 테스트 데이터 전달용(users, 원소 3개)
     * @return 블록에제의 뷰 이름
     */
    @GetMapping("/block")
    public String block(Model model) {
        addUsers(model);
        return "/basic/block";
    }

    /**
     * 자바스크립트 인라인 예제에 대한 컨트롤러
     * @param model 테스트 데이터 전달용(users와 user)
     * @return 자바스크립트 인라인 예제의 뷰 이름
     */
    @GetMapping("/javascript")
    public String javascript(Model model) {
        addUsers(model);
        model.addAttribute("user", new User("userD", 40));
        return "/basic/javascript";
    }


    /**
     * 예제코드의 테스트 데이터 생성을 위한 메서드
     *
     * @param model 모델에 List<User> users를 만들어서 넣어준다(유저 3명 들어있음)
     */
    private void addUsers(Model model) {
        List<User> list = new ArrayList<>();
        list.add(new User("userA", 10));
        list.add(new User("userB", 20));
        list.add(new User("userC", 30));

        model.addAttribute("users", list);
    }
}
