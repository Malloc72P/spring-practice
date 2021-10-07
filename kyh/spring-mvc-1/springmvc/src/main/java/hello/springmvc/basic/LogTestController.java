package hello.springmvc.basic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 스프링에서 SLF4J 인터페이스로 로그를 찍는 방법에 대한 예제코드
 */
@Slf4j
@RestController
public class LogTestController {

//    private final Logger log = LoggerFactory.getLogger(LogTestController.class);

    /**
     * SLF4J를 사용해서 로그를 레벨별로 출력합니다
     * @return 예제이므로 의미없는 문자열을 반환합니다
     */
    @GetMapping("/log-test")
    public String logTest() {
        String name = "Spring";

        log.trace("trace log = {}" + name);
        log.trace("trace log = {}", name);

        log.debug("debug log = {}", name);
        log.info("info log = {}", name);
        log.warn("warn log = {}", name);
        log.error("error log = {}", name);

        return "ok";
    }
}
