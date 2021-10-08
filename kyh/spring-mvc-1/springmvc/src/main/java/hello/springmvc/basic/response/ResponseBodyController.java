package hello.springmvc.basic.response;

import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Controller
public class ResponseBodyController {

    /**
     * 서블릿 기술을 사용하는 가장 기초적인 방법입니다
     *
     * @param response 서블릿의 응답객체
     */
    @GetMapping("/response-body-string-v1")
    public void responseBodyV1(HttpServletResponse response) throws IOException {
        response.getWriter().write("ok");
    }

    /**
     * 서블릿 기술을 안쓰면서 ResponseEntity로 해결하는 방법입니다
     *
     * @return ResponseEntity로 상태코드와 함께 문자열을 응답합니다
     */
    @GetMapping("/response-body-string-v2")
    public ResponseEntity<String> responseBodyV2() {
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    /**
     * 애너테이션 방법으로 응답메세지의 바디에 데이터를 담아 응답하는 방법입니다
     *
     * @return 응답메세지 바디에 담길 데이터를 반환합니다
     */
    @ResponseBody
    @GetMapping("/response-body-string-v3")
    public String responseBodyV3() {
        return "ok";
    }

    /**
     * HTTP 메세지 컨버터를 이용해서 객체를 JSON포맷으로 응답메세지 바디에 담아 전송하는 방식입니다
     * ResponseEntity의 제네릭을 사용해서 객체의 타입을 지정합니다
     *
     * @return ResponseEntity로 응답합니다
     */
    @GetMapping("/response-body-json-v1")
    public ResponseEntity<HelloData> responseBodyJsonV1() {
        HelloData helloData = new HelloData();
        helloData.setUsername("asdf");
        helloData.setAge(21);

        return new ResponseEntity<>(helloData, HttpStatus.OK);
    }

    /**
     * ResponseEntity대신 애너테이션 방식을 사용하는 방법입니다
     * 상태코드는 @ResponseStatus애너테이션으로 지정할 수 있습니다
     * 참고로 ResponseBody를 붙이기 귀찮다면 RestController를 클래스 레벨에 붙이면 됩니다
     * 그러면 그 안의 모든 컨트롤러 메서드에는 ResponseBody가 적용됩니다
     * 애초에 @RestController안에 @Controller랑 @ResponseBody가 있습니다
     *
     * @return 응답메세지 바디에 담길 내용을 반환합니다
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/response-body-json-v2")
    public HelloData responseBodyJsonV2() {
        HelloData helloData = new HelloData();
        helloData.setUsername("asdf");
        helloData.setAge(21);

        return helloData;
    }
}
