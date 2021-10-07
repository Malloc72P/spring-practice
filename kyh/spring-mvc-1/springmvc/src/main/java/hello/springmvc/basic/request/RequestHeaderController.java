package hello.springmvc.basic.request;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

@Slf4j
@RestController
@RequestMapping("/headers")
public class RequestHeaderController {

    /**
     * 컨트롤러는 인터페이스로 틀이 잡혀있지 않다
     * 덕분에 애너테이션 기반 스프링 컨트롤러는 스프링이 지원하는 다양한 파라미터를 지원한다
     * 여기서 한번 실습해본다
     *
     * @return dummy
     */
    @RequestMapping
    public String headers(HttpServletRequest request,
                          HttpServletResponse response,
                          HttpMethod httpMethod,
                          Locale locale,
                          @RequestHeader MultiValueMap<String, String> headerMap,
                          @RequestHeader("host") String host,
                          @CookieValue(value = "myCookie", required = false) String cookie) {
        log.info("request : {}", request);
        log.info("response : {}", response);
        log.info("httpMethod : {}", httpMethod);
        log.info("locale : {}", locale);
        log.info("headerMap : {}", headerMap);
        log.info("host : {}", host);
        log.info("cookie : {}", cookie);
        return "ok";
    }
}
