package hello.springmvc.basic.requestmapping;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * 요청을 매핑하는 방법에 대해 학습합니다
 */
@Slf4j
@RestController
public class MappingController {

    /**
     * RequestMapping에서 URL을 매핑할 수 있습니다
     *
     * @return
     */
    @RequestMapping(value = "hello-basic")
    public String helloBasic() {
        log.info("helloBasic");
        return "ok";
    }

    /**
     * URL과 요청메서드로 매핑할 수 있습니다
     *
     * @return ok
     */
    @RequestMapping(value = "/mapping-get-v1", method = RequestMethod.GET)
    public String mappingGetV1() {
        log.info("mappingGetV1");
        return "ok";
    }

    /**
     * RequestMapping대신 편리한 축약 애너테이션을 사용할 수 있습니다
     * 더 직관적이어서 좋습니다
     *
     * @return ok
     */
    @GetMapping("/mapping-get-v2")
    public String mappingGetV2() {
        log.info("mapping-get-v2");
        return "ok";
    }

    /**
     * PathVariable을 사용해서 URL의 경로변수를 사용합니다
     *
     * @param userId 경로변수입니다
     * @return ok
     */
    @GetMapping("/mapping/{userId}")
    public String mappingPath(@PathVariable("userId") String userId) {
        log.info("mappingPath userId : {}", userId);
        return userId;
    }

    /**
     * PathVariable은 중복해서 사용가능합니다
     * 따로 이름을 정해주지 않으면 변수명과 이름이 동일한 요청 파라미터를 꺼내옵니다
     *
     * @param userId  유저 아이디
     * @param orderId 주문 아이디
     * @return ok
     */
    @GetMapping("/mapping/users/{userId}/orders/{orderId}")
    public String mappingPath(@PathVariable String userId, @PathVariable Long orderId) {
        log.info("userId : {}", userId);
        log.info("orderId : {}", orderId);
        return "ok";
    }

    /**
     * 특정 파라미터 조건 매핑.
     * 특정 파라미터가 있거나, 없는 조건을 추가할 수 있습니다
     * 특정 파라미터가 있어야 컨트롤러 메서드가 호출됩니다
     * 없으면 400코드로 응답메세지가 전송됩니다
     *
     * @return ok
     */
    @GetMapping(value = "/mapping-param", params = "mode=debug")
    public String mappingParam() {
        log.info("mappingParam");
        return "ok";
    }

    /**
     * 특정 헤더 조건 매핑
     * 특정 헤더가 있어야 컨트롤러 메서드가 호출됩니다
     * 헤더가 없으면 404코드로 응답이 전송됩니다
     *
     * @return ok
     */
    @GetMapping(value = "/mapping-header", headers = "mode=debug")
    public String mappingHeader() {
        log.info("mappingHeader");
        return "ok";
    }

    /**
     * HTTP 요청의 Content-Type이 특정 미디어 타입이어야 컨트롤러 메서드가 호출됩니다
     * 미디어 타입이 맞지 않으면 415 Unsupported Media Type 코드로 응답메세지가 전송됩니다
     *
     * @return ok
     */
    @PostMapping(value = "/mapping-consume", consumes = "application/json")
    public String mappingConsumes() {
        log.info("mappingConsumes");
        return "ok";
    }

    /**
     * Http요청의 Accept헤더를 기반으로 미디어 타입을 매핑합니다
     * 안맞으면 406 Not Acceptable 응답이 전송됩니다
     * 클라이언트의 Accept헤더는, "난 이런 타입을 받아들일 수 있어"를 나타낸다
     * 이 메서드의 경우, text/html을 받아들일 수 있는 경우, 즉 accept에 text/html이 있는 경우에만 메서드를 호출한다
     *
     * @return ok
     */
    @PostMapping(value = "/mapping-produce", produces = MediaType.TEXT_HTML_VALUE)
    public String mappingProduces() {
        log.info("mappingProduces");
        return "ok";
    }
}
