package hello.servlet.web.servletmvc;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "mvcMemberFormServlet", urlPatterns = "/servlet-mvc/members/new-form")
public class MvcMemberFormServlet extends HttpServlet {

    /*
    * 고객요청오면 서비스 호출됨
    * 얘가 jsp를 다시 호출함 = 제어권을 넘겨주는 것
    * RequestDispatcher의 forward()로 다른 서블릿이나 JSP로 이동할 수 있다.
    * 서버 내부에서 다시 호출이 발생한다
    * 리다이렉트는 실제 클라이언트로 3xx코드의 리다이렉션 응답이 나갔다가 클라가 다시 리다이렉션 경로로 요청한다
    * 반면 RequestDispatcher의 forward()는 서버 내부에서 발생하는 일이라서 클라는 모른다
    * */
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String viewPath = "/WEB-INF/views/new-form.jsp";
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(viewPath);
        requestDispatcher.forward(request, response);
    }
}
