package hello.springmvc.basic.request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 요청메세지의 바디로부터 JSON데이터를 읽는 방법에 대해 학습합니다
 */
@Slf4j
@Controller
public class RequestBodyJsonController {

    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 요청객체를 통해 스트림을 받고 ObjectMapper를 사용할 수 있습니다
     *
     * @param request  요청객체
     * @param response 응답객체
     * @throws IOException 요청, 응답객체의 입출력 스트림 사용으로 인해 발생가능합니다
     */
    @PostMapping("/request-body-json-v1")
    public void requestBodyJsonV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        log.info("messageBody : {}", messageBody);

        HelloData data = objectMapper.readValue(messageBody, HelloData.class);
        log.info("data : {}", data);

        response.getWriter().write("ok");
    }

    /**
     * RequestBody, ResponseBody를 사용하면 좀 더 편해집니다
     *
     * @param messageBody 요청메세지 바디
     * @return dummy, ResponseBody를 사용하므로 응답메세지의 바디에 그대로 ok가 담겨서 전송됨
     * @throws JsonProcessingException ObjectMapper로 인해 발생가능
     */
    @ResponseBody
    @PostMapping("/request-body-json-v2")
    public String requestBodyJsonV2(@RequestBody String messageBody) throws JsonProcessingException {
        HelloData data = objectMapper.readValue(messageBody, HelloData.class);
        log.info("data : {}", data);
        return "ok";
    }

    /**
     * 객체 파라미터를 통해 RequestBody에 직접 만든 객체를 지정할 수 있습니다.
     * 그러면 Http Message Converter 가 우리가 원하는 타입으로 변환해서 반환해줍니다.
     * 우리가 ObjectMapper를 사용할 필요가 없습니다
     * 참고로 RequestBody는 생략할 수 없습니다. 생략하는건 RequestParam이나 ModelAttribute때나 가능합니다.
     * 생략하면 ModelAttribute가 붙게 되므로 바디에서 JSON데이터를 읽을 수 없습니다
     * 또한 HTTP 요청의 Content-Type이 application/json이어야 합니다. 그래야 JSON을 처리할 수 있는 HTTP 메세지 컨버터가 실행됩니다
     *
     * @param helloData 객체 파라미터를 통해 우리가 원하는 타입인 HelloData로 지정함
     * @return dummy
     */
    @ResponseBody
    @PostMapping("/request-body-json-v3")
    public String requestBodyJsonV3(@RequestBody HelloData helloData) {
        log.info("helloData : {}", helloData);
        return "ok";
    }

    /**
     * HttpEntity나 RequestEntity도 직접 만든 객체를 제네릭으로 지정할 수 있습니다
     *
     * @param helloData RequestEntity로, 제네릭으로 우리가 만든 타입을 지정했습니다
     * @return dummy
     */
    @ResponseBody
    @PostMapping("/request-body-json-v4")
    public String requestBodyJsonV4(RequestEntity<HelloData> helloData) {
        log.info("helloData : {}", helloData);
        log.info("helloData.getBody() : {}", helloData.getBody());
        return "ok";
    }

    /**
     * ResponseBody의 경우에도 우리가 만든 타입을 지정할 수 있다.
     * 객체를 HTTP 메세지 컨버터로 JSON포맷으로 변환하고 바디에 담아서 응답할 수 있다
     * @param helloData dummy
     * @return dummy
     */
    @ResponseBody
    @PostMapping("/request-body-json-v5")
    public HelloData requestBodyJsonV5(@RequestBody HelloData helloData) {
        log.info("helloData : {}", helloData);
        return helloData;
    }
}
