package hello.exception.servlet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import static javax.servlet.RequestDispatcher.*;

@Slf4j
@Controller
public class ErrorPageController {

    @RequestMapping("error-page/404")
    public String errorPage404(HttpServletRequest request) {
        log.info("테스팅 : 404!");
        printError(request);
        return "error-page/404";
    }

    @RequestMapping("error-page/500")
    public String errorPage500(HttpServletRequest request) {
        log.info("테스팅 : 500!");
        printError(request);
        return "error-page/500";
    }

    @RequestMapping(value = "/error-page/500", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> errorPage500API(HttpServletRequest request) {
        log.info("errorPage500API");
        Map<String, Object> result = new HashMap<>();
        Exception ex = (Exception) request.getAttribute(ERROR_EXCEPTION);

        result.put("status", request.getAttribute(ERROR_STATUS_CODE));
        result.put("message", ex.getMessage());

        Integer statusCode = (Integer) request.getAttribute(ERROR_STATUS_CODE);
        return new ResponseEntity<>(result, HttpStatus.valueOf(statusCode));
    }

    private void printError(HttpServletRequest request) {
        log.info("ERROR_EXCEPTION : {}", request.getAttribute(ERROR_EXCEPTION));
        log.info("ERROR_EXCEPTION_TYPE : {}", request.getAttribute(ERROR_EXCEPTION_TYPE));
        log.info("ERROR_MESSAGE : {}", request.getAttribute(ERROR_MESSAGE));
        log.info("ERROR_REQUEST_URI : {}", request.getAttribute(ERROR_REQUEST_URI));
        log.info("ERROR_SERVLET_NAME : {}", request.getAttribute(ERROR_SERVLET_NAME));
        log.info("ERROR_STATUS_CODE : {}", request.getAttribute(ERROR_STATUS_CODE));
        log.info("dispatchType : {}", request.getDispatcherType());
    }

}
