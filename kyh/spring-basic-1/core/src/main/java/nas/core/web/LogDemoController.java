package nas.core.web;

import lombok.RequiredArgsConstructor;
import nas.core.common.MyLogger;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class LogDemoController {

    private final LogDemoService logDemoService;
    private final ObjectProvider<MyLogger> myLoggerObjectProvider;
//    private final MyLogger myLogger;

    @RequestMapping("/log-demo")
    @ResponseBody//문자열 그대로 응답을 보낼 수 있다.
    public String testLog(HttpServletRequest request) throws InterruptedException {
        MyLogger myLogger = myLoggerObjectProvider.getObject();
        String requestURL = request.getRequestURL().toString();
        myLogger.setRequestURL(requestURL);
        myLogger.log("controller test");
        Thread.sleep(300);
        logDemoService.logic("testId");
        return "OK";
    }
}
