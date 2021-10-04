package hello.servlet.web.frontcontroller.v1;

import hello.servlet.web.frontcontroller.v1.controller.MemberFormControllerV1;
import hello.servlet.web.frontcontroller.v1.controller.MemberListControllerV1;
import hello.servlet.web.frontcontroller.v1.controller.MemberSaveControllerV1;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/*
 * url이 v1의 하위면 무조건 이 서블릿이 실행된다
 * */
@WebServlet(name = "frontControllerServletV1", urlPatterns = "/front-controller/v1/*")
public class FrontControllerServletV1 extends HttpServlet {

    private Map<String, ControllerV1> controllerMap = new HashMap<>();

    public FrontControllerServletV1() {
        controllerMap.put("/front-controller/v1/members/new-form", new MemberFormControllerV1());
        controllerMap.put("/front-controller/v1/members/save", new MemberSaveControllerV1());
        controllerMap.put("/front-controller/v1/members", new MemberListControllerV1());
    }

    /*
    * 매핑정보에서 컨트롤러를 꺼낸다
    * 없으면 404상태코드로 응답한다
    * 찾았다면 다형성을 활용해서 처리한다
    * ControllerV1 인터페이스를 사용했으므로, 클라이언트 코드는 인터페이스라는 역할에만 의존한다
    * 실제 구현체가 뭐가 올지 모른다
    * 런타임에 인터페이스가 가리키는 실제 객체의 타입에 따라 실행될 코드가 결정된다
    *
    * 모든 컨트롤러는 서블릿과 비슷한 모양의 컨트롤러 인터페이스를 도입한다. 각 컨트롤러는 이를 구현한다
    * 프론트 컨트롤러는 앞서 구현한 컨트롤러를 호출한다
    * 인터페이스를 호출해서 구현과 관계 없이 로직의 일관성을 가져갈 수 있다
    * */
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        ControllerV1 controllerV1 = controllerMap.get(requestURI);
        if (controllerV1 == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        controllerV1.process(request, response);
    }
}
