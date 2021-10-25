package hello.login.web.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Slf4j
public class LogInterceptor implements HandlerInterceptor {

    public static final String LOGIN_ID = "loginId";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        String uuid = UUID.randomUUID().toString();

        request.setAttribute(LOGIN_ID, uuid);

        /*@RequestMapping사용하는 경우 HandlerMethod
        정적 리소스인 경우 ResourceHttpRequestHandler
        if (handler instanceof HttpMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
        }*/

        log.info("preHandle : [{}][{}][{}]", uuid, requestURI, handler);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        String requestURI = request.getRequestURI();
        String uuid = extractUUID(request);

        log.info("postHandle : [{}]", modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String requestURI = request.getRequestURI();
        String uuid = extractUUID(request);

        log.info("afterCompletion : [{}][{}][{}]", uuid, requestURI, handler);
        if (ex != null) {
            log.error("afterCompletion error!", ex);
        }
    }

    private String extractUUID(HttpServletRequest request) {
        return (String) request.getAttribute(LOGIN_ID);
    }
}
