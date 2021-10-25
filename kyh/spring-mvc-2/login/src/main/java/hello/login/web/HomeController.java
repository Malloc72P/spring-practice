package hello.login.web;

import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import hello.login.web.session.SessionConst;
import hello.login.web.session.SessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberRepository memberRepository;

    private final SessionManager sessionManager;

    //    @GetMapping("/")
    public String home() {
        return "home";
    }

    //    @GetMapping("/")
    public String homeLogin(@CookieValue(name = "memberId", required = false) Long memberId,
                            Model model) {
        if (memberId == null) {
            return "home";
        }

        Member loginMember = memberRepository.findById(memberId);
        if (loginMember == null) {
            return "home";
        }

        model.addAttribute("member", loginMember);
        return "loginhome";
    }

    //    @GetMapping("/")
    public String homeLoginV2(HttpServletRequest request, Model model) {
        Member loginMember = (Member) sessionManager.getSession(request);

        if (loginMember == null) {
            return "home";
        }

        model.addAttribute("member", loginMember);
        return "loginhome";
    }

//    @GetMapping("/")
    public String homeLoginV3(HttpServletRequest request, Model model) {
        //세션을 가져오되, 없으면 생성하지 않고 비로그인 홈으로 보낸다
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "home";
        }

        //세션에서 회원정보를 꺼낸다. 없으면 비로그인 홈으로 보낸다
       Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        if (loginMember == null) {
            return "home";
        }

        model.addAttribute("member", loginMember);
        return "loginhome";
    }

    /**
     * request에서 세션객체를 얻고, 세션에서 값을 꺼내는 작업도 복잡하다 <br>
     * 스프링은 @SessionAttribute를 제공해서 이 복잡함을 해결해준다 <br>
     * 이 기능은 세션을 생성하지 않는다.
     * @param loginMember 세션 어트리뷰트. 로그인 된 유저의 경우, null대신 회원정보가 있다.
     * @param model 모델
     * @return 로그인 유저는 로그인버전 홈, 비 로그인 유저는 일반 홈
     */
    @GetMapping("/")
    public String homeLoginV3Spring(@SessionAttribute(name=SessionConst.LOGIN_MEMBER, required = false) Member loginMember,
                                    Model model) {
        if (loginMember == null) {
            return "home";
        }

        model.addAttribute("member", loginMember);
        return "loginhome";
    }

}
