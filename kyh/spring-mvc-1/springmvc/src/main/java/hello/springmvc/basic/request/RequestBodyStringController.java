package hello.springmvc.basic.request;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

@Slf4j
@Controller
public class RequestBodyStringController {

    /**
     * 요청메세지의 메세지바디를 직접 읽으려면 요청객체에서 스트림을 꺼내서 사용하면 됩니다
     *
     * @param request  요청객체
     * @param response 응답객체
     * @throws IOException 요청, 응답객체의 입출력 스트림을 사용하므로 발생가능함
     */
    @PostMapping("/request-body-string-v1")
    public void requestBodyString(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("messageBody : {}", messageBody);

        response.getWriter().write("ok");
    }

    /**
     * 사실 요청객체는 필요 없습니다. 입출력 스트림만 매개변수로 받을 수 있습니다
     *
     * @param inputStream 요청메세지의 바디에 대한 입력스트림
     * @param writer      응답메세지의 바디에 대한 출력스트림
     * @throws IOException 입출력 스트림을 사용하므로 발생가능함
     */
    @PostMapping("/request-body-string-v2")
    public void requestBodyStringV2(InputStream inputStream, Writer writer) throws IOException {
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("messageBody : {}", messageBody);

        writer.write("ok");
    }

    /**
     * 사실 입출력 스트림도 필요 없습니다. HttpEntity를 사용하면 HTTP헤더와 바디내용을 편리하게 조회할 수 있습니다
     * 리턴타입으로 사용하는 경우 응답메세지의 바디에 넣을 데이터를 직접 입력할 수 있습니다. 이 경우 뷰를 조회하지 않고,
     * HttpEntity의 내용을 그대로 응답메세지에 넣어서 전송합니다.
     * HttpEntity는 요청파라미터를 조회하는 기능과 관계없습니다. 그건 Requestparam이나 ModelAttribute를 사용합니다
     * 요청은 RequestEntity, 응답은 ResponseEntity객체를 사용할 수 있습니다. 둘 다 HttpEntity를 상속받은 객체입니다
     *
     * @param httpEntity HTTP 헤더와 바디정보를 편리하게 조회할 수 있는 객체입니다
     * @return HttpEntity를 메시지 바디에 데이터를 직접 넣어서 응답메세지를 전송합니다
     */
    @PostMapping("/request-body-string-v3")
    public ResponseEntity<String> requestBodyStringV3(RequestEntity<String> httpEntity) {
        String body = httpEntity.getBody();
        log.info("body : {}", body);
        return new ResponseEntity<>("ok", HttpStatus.CREATED);
    }

    /**
     * 애너테이션을 방식도 있습니다. 아예 바디만 받을 수 있습니다
     *
     * @param messageBody RequestBody로 바디내용을 문자열로 받습니다
     * @return ResponseBody 애너테이션이 있으므로 리턴하는 값을 그대로 바디에 담아 전송합니다
     */
    @ResponseBody
    @PostMapping("/request-body-string-v4")
    public String requestBodyStringV4(@RequestBody String messageBody) {
        log.info("messageBody : {}", messageBody);
        return "ok";
    }
}
