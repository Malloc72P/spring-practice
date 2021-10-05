package hello.servlet.web.springmvc.v3;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/springmvc/v3/members")
public class SpringMemberControllerV3 {

    private MemberRepository memberRepository = MemberRepository.getInstance();

    /**
     * 회원 등록 폼을 응답한다
     *
     * @return 인터페이스로 컨트롤러가 고정되어있지 않기 때문에, 문자열을 반환해도 된다
     */
    @GetMapping("/new-form")
    public String newForm() {
        return "new-form";
    }

    /**
     * 회원 목록 페이지를 응답해준다
     *
     * @param model 모델객체를 만들 필요 없다. 그냥 매개변수로 받으면 된다
     * @return 회원목록뷰의 논리명을 반환한다
     */
    @GetMapping
    public String members(Model model) {
        List<Member> members = memberRepository.findAll();
        model.addAttribute("members", members);
        return "members";
    }

    /**
     * 멤버를 저장하는 메서드
     *
     * @param username 유저 이름. @RequestParam을 쓰면 요청 파라미터에서 꺼내서 매개변수로 넣어준다
     * @param age      나이. 요청파라미터에서 꺼내주는 것 뿐만 아니라, 타입 캐스팅까지 해준다
     * @param model    모델 객체를 만들필요도 없다. 매개변수로 받아다가 쓰면 된다
     * @return 뷰의 논리명만 써도 된다
     */
    @PostMapping("/save")
    public String save(@RequestParam("username") String username,
                       @RequestParam("age") int age,
                       Model model) {
        Member member = new Member(username, age);
        memberRepository.save(member);

        model.addAttribute("member", member);
        return "save-result";
    }
}
