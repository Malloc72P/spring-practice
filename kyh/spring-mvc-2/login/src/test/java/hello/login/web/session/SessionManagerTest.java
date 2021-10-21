package hello.login.web.session;

import hello.login.domain.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.assertj.core.api.Assertions.assertThat;

class SessionManagerTest {

    private SessionManager sessionManager = new SessionManager();

    @Test
    void sessionTest() {
        //로그인에 성공해서 서버는 세션을 저장하고, 쿠키로 세션아이디를 담아서 클라에게 내려준다
        MockHttpServletResponse response = new MockHttpServletResponse();
        Member member = new Member();
        sessionManager.createSession(member, response);

        //클라는 요청헤더에 세션아이디를 담아서 요청한다
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setCookies(response.getCookies());

        //세션 조회해서 저장했던 객체를 꺼낼 수 있는지 확인한다
        Object result = sessionManager.getSession(request);
        assertThat(result).isEqualTo(member);

        //세션 만료를 할 수 있어야 한다
        sessionManager.expire(request);
        Object expiredSession = sessionManager.getSession(request);
        assertThat(expiredSession).isNull();
    }
}
