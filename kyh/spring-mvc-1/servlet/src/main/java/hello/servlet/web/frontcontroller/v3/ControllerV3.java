package hello.servlet.web.frontcontroller.v3;

import hello.servlet.web.frontcontroller.ModelView;

import java.util.Map;

/*
* 서블릿에 전혀 종속적이지 않은 컨트롤러 구현을 목표로 한다
* */
public interface ControllerV3 {
    ModelView process(Map<String, String> paramMap);
}
