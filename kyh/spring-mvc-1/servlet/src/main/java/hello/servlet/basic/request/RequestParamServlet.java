package hello.servlet.basic.request;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "requestParamServlet", urlPatterns = "/request-param")
public class RequestParamServlet extends HttpServlet {

    /*
     * 1. 쿼리 파라미터를 조회해본다
     * 이름이 같은 쿼리파라미터가 여러개 전송되는 경우,getParameterValues를 써야 한다
     * getParameter를 쓰면 앞의 결과의 첫번째 요소가 반환된다
     * */
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("전체 파라미터 조회 START");
        request.getParameterNames().asIterator()
                .forEachRemaining(paramName -> System.out.println(paramName + " = " + request.getParameter(paramName)));

        System.out.println("전체 파라미터 조회 END");
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));
        System.out.printf("username : %s\nage : %d\n\n", username, age);

        String[] parameterValues = request.getParameterValues("username");
        for (String value : parameterValues) {
            System.out.println("value = " + value);
        }
    }
}
