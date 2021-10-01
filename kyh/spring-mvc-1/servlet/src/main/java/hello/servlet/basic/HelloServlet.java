package hello.servlet.basic;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/*
 * 2. 서블릿 등록
 * urlPattern에 해당하는 요청이 들어오면 해당 서블릿이 실행됨
 * HttpServlet을 상속해야함
 * */
@Slf4j
@WebServlet(name = "helloServlet", urlPatterns = "/hello")
public class HelloServlet extends HttpServlet {

    /*
     * 3. 서블릿이 호출되면 서비스 메서드가 호출된다
     * HTTP 요청이 오면 WAS가 Request, Response객체를 만들어서 서블릿에게 전달한다
     * request객체를 사용해서 HTTP 요청메세지의 내용을 편하게 꺼내서 읽을 수 있다.
     * response객체를 사용하면 편하게 HTTP 응답메세지에 내용을 입력할 수 있다
     * */
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.info("HelloServlet service called");
        log.info("request : {}", request);
        log.info("response : {}", response);
        String username = request.getParameter("username");
        log.info("queryParam : {}", username);

        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");

        PrintWriter writer = response.getWriter();
        String sb = "<h1>Hello</h1>" +
                "<h3>한글도 나오는지 테스트</h3>";

        writer.println(sb);

    }
}
