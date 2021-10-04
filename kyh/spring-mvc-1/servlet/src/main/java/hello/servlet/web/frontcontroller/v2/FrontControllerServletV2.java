package hello.servlet.web.frontcontroller.v2;

import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v2.controller.MemberFormControllerV2;
import hello.servlet.web.frontcontroller.v2.controller.MemberListControllerV2;
import hello.servlet.web.frontcontroller.v2.controller.MemberSaveControllerV2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontControllerServletV2", urlPatterns = "/front-controller/v2/*")
public class FrontControllerServletV2 extends HttpServlet {

    private Map<String, ControllerV2> controllerMap = new HashMap<>();

    public FrontControllerServletV2() {
        controllerMap.put("/front-controller/v2/members/new-form", new MemberFormControllerV2());
        controllerMap.put("/front-controller/v2/members/save", new MemberSaveControllerV2());
        controllerMap.put("/front-controller/v2/members", new MemberListControllerV2());
    }

    /*
     * V1의 한계
     * 여전히 컨트롤러에서 뷰로 이동하는 부분에 중복이 존재한다
     *
     * 해결 : 포워드하는 중복코드를 제거
     * 프론트 컨트롤러가 MyView의 render()라는 메서드를 호출한다.
     * JSP로 포워드하는 공통처리기능을 대신 수행한다
     * 이를 위해서 인터페이스를 고쳤다. 예전엔 void였지만 이제는 MyView를 리턴한다
     * 각각의 컨트롤러는 직접 포워드하지 않고, MyView를 리턴한다다
     *
     * 또한 컨트롤러 인터페이스의 리턴타입을 MyView로 해서 얻는점이 하나 더 있다
     * 개발자가 컨트롤러를 만들 때, View를 반환해야 하는구나를 알 수 있다.
     * 엉뚱한걸 리턴하면 컴파일 에러가 발생해서 고치기 쉽다
     * */
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        ControllerV2 controllerV2 = controllerMap.get(requestURI);
        if (controllerV2 == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        MyView myView = controllerV2.process(request, response);
        myView.render(request, response);
    }
}
