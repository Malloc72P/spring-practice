package hello.springmvc.basic.request;

import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * 요청 파라미터를 읽는 방법에 대해 학습합니다
 */
@Slf4j
@Controller
public class RequestParamController {

    /**
     * 서블릿 기술을 사용해서 요청 파라미터 읽기
     * 메서드를 지정하지 않았으므로 URL만 맞으면 메서드가 실행됩니다
     * 따라서 이 메서드는 URL 또는 메세지 바디의 요청 파라미터를 처리하게 됩니다
     *
     * @param request  서블릿의 요청객체
     * @param response 서블릿의 응답객체
     * @throws IOException 응답객체의 Writer 사용으로 인해 발생 가능
     */
    @RequestMapping("/request-param-v1")
    public void requestParamV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));
        log.info("username : {}", username);
        log.info("age : {}", age);
        response.getWriter().write("ok");
    }

    /**
     * RequestParam을 사용하여 요청 파라미터를 변수에 바인딩합니다
     * ResponseBody를 사용해서 뷰 조회를 하지 않고 메세지 바디에 리턴값을 넣게 할 수 있습니다
     *
     * @param memberName 유저이름
     * @param memberAge  유저 나이
     * @return dummy
     */
    @ResponseBody
    @RequestMapping("/request-param-v2")
    public String requestParamV2(
            @RequestParam("username") String memberName,
            @RequestParam("age") int memberAge) {
        log.info("memberName : {}, memberAge : {}", memberName, memberAge);
        return "ok";
    }

    /**
     * RequestParam은 따로 이름을 안주면 변수명으로 요청 파라미터를 찾아서 바인딩합니다
     *
     * @param username 유저이름
     * @param age      유저 나이
     * @return dummy
     */
    @ResponseBody
    @RequestMapping("/request-param-v3")
    public String requestParamV3(
            @RequestParam String username,
            @RequestParam int age) {
        log.info("username : {}, age : {}", username, age);
        return "ok";
    }

    /**
     * 사실 RequestParam까지 생략할 수 있다
     * 단, String, int, Integer와 같이 단순타입이어야 한다
     *
     * @param username 유저이름
     * @param age      유저 나이
     * @return dummy
     */
    @ResponseBody
    @RequestMapping("/request-param-v4")
    public String requestParamV4(String username, int age) {
        log.info("username : {}, age : {}", username, age);
        return "ok";
    }

    /**
     * RequestParam은 필수 여부를 설정할 수 있습니다. 기본값은 true로, 필수 파라미터입니다
     * 필수인데 안보내면 400 Bad Request로 응답메세지가 전송됩니다
     * 그런데 required=true 여도, 빈 문자열을 보내는 경우,(username=&age=20) 빈 문자열이 넘어오긴 하므로
     * 400 코드로 응답메세지가 전송되지 않습니다
     * 필수가 아니면 안보내도 되지만, null값이 들어가게 되므로 프리미티브 타입은 에러가 발생합니다
     * 추가로 RequestParam은 디폴트값을 설정할 수 있습니다
     * 값을 보내지 않거나 빈문자열을 보내는 경우 디폴트 값이 적용됩니다
     *
     * @param username 유저이름
     * @param age      유저 나이
     * @return dummy
     */
    @ResponseBody
    @RequestMapping("/request-param-required")
    public String requestParamRequired(
            @RequestParam(defaultValue = "guest") String username,
            @RequestParam(required = false, defaultValue = "-1") Integer age) {
        log.info("username : {}, age : {}", username, age);
        return "ok";
    }

    /**
     * Map이나 MultiValueMap으로 꺼내서 읽을 수 있습니다
     *
     * @param paramMap           모든 요청 파라미터는 여기에 담겨있습니다
     * @param paramMultiValueMap 모든 요청 파라미터는 여기에 담겨있습니다.
     *                           값을 리스트로 가지고 있어서 하나의 키에 여러 값이 있어도 괜찮습니다
     * @return dummy
     */
    @ResponseBody
    @RequestMapping("/request-param-map")
    public String requestParamMap(
            @RequestParam Map<String, Object> paramMap,
            @RequestParam MultiValueMap<String, Object> paramMultiValueMap
    ) {
        log.info("username : {}, age : {}", paramMap.get("username"), paramMap.get("age"));
        log.info("username : {}, age : {}", paramMultiValueMap.get("username"), paramMultiValueMap.get("age"));
        return "ok";
    }

    /**
     * 객체를 받으려면 RequestParam으로 요청 파라미터를 하나씩 받은 다음, 컨트롤러에서 직접 객체를 생성할 수 있습니다
     * 하지만 너무 불편합니다. Spring MVC는 ModelAttribute를 사용해서 편하게 객체를 생성할 수 있습니다
     * 요청파라미터의 이름으로 객체의 프로퍼티를 찾고, Setter를 호출해서 파라미터의 값을 객체에 입력합니다
     * 프로퍼티는 필드를 말합니다. getter, setter로 프로퍼티에 접근할 수 있습니다
     *
     * @param data ModelAttribute를 통해 객체를 생성하고 요청파라미터를 객체의 프로퍼티에 넣습니다
     * @return dummy
     */
    @ResponseBody
    @RequestMapping("/model-attribute-v1")
    public String modelAttributeV1(@ModelAttribute HelloData data) {
        log.info("data : {}", data);
        return "ok";
    }

    /**
     * ModelAttribute도 생략가능합니다.
     * ArgumentResolver로 지정해둔 타입은 RequestParam으로 처리하고, 그 외의 것은 ModelAttribute로 처리합니다
     * Integer, String, int등의 타입을 RequestParam으로 처리합니다
     *
     * @param data ModelAttribute를 사용해서 요청파라미터로 객체를 생성합니다
     * @return dummy
     */
    @ResponseBody
    @RequestMapping("/model-attribute-v2")
    public String modelAttributeV2(HelloData data) {
        log.info("data : {}", data);
        return "ok";
    }
}
