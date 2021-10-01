package hello.servlet.basic.request;

import org.springframework.util.StreamUtils;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebServlet(name = "requestBodyStringServlet", urlPatterns = "/request-body-string")
public class RequestBodySpringServlet extends HttpServlet {

    /*
     * 1. 메시지 바디에 데이터를 담아서 보내는 경우이다
     * ServletInputStream을 뽑아내서 읽을 수 있다
     * StreamUtils라는 스프링 제공 유틸을 사용하면 편하게 문자열로 뽑아낼 수 있다
     * 여기서는 순수 텍스트를 받아본다
     * */
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletInputStream inputStream = request.getInputStream();
        String data = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        System.out.println("data = " + data);
    }
}
