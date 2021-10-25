package hello.login.web.session;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Slf4j
@RestController
@RequiredArgsConstructor
public class SessionInfoController {

    @GetMapping("/session-info")
    public String sessionInfo(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "세션이 없습니다";
        }
        session.getAttributeNames()
                .asIterator()
                .forEachRemaining(name -> log.info("session name = {}, value = {}",name, session.getAttribute(name)));

        log.info("session.getId() = {}", session.getId());
        log.info("session.getMaxInactiveInterval() = {}", session.getMaxInactiveInterval());
        log.info("session.getCreationTime() = {}", session.getCreationTime());
        log.info("new Date(session.getLastAccessedTime()) = {}", new Date(session.getLastAccessedTime()));
        log.info("session.isNew() = {}", session.isNew());

        return "세션 출력";
    }

}
