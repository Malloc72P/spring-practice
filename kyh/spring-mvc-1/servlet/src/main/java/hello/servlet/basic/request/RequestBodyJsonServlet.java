package hello.servlet.basic.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.servlet.basic.HelloData;
import org.springframework.util.StreamUtils;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebServlet(name = "requestBodyJsonServlet", urlPatterns = "/request-body-json")
public class RequestBodyJsonServlet extends HttpServlet {

    /*
    * JSON 포맷으로 데이터를 보냈다고 하더라도 문자열의 형태로 데이터를 전송하게 된다
    * 그래서 메시지바디의 내용을 문자열로 변환하는건 얘도 똑같다
    * 다만 문자열이 JSON 형식을 지켜서 주고받는것 뿐이다
    *
    * 문자열을 JSON포맷으로 파싱해서 객체로 만들어주는 라이브러리가 있다
    * JACKSON 이라고 한다
    * */
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletInputStream inputStream = request.getInputStream();
        String data = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        System.out.println("data = " + data);

        ObjectMapper objectMapper = new ObjectMapper();
        HelloData helloData = objectMapper.readValue(data, HelloData.class);
        System.out.println("helloData = " + helloData);
    }
}
