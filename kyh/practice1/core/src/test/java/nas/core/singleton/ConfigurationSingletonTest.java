package nas.core.singleton;

import nas.core.AppConfig;
import nas.core.member.MemberRepository;
import nas.core.member.MemberService;
import nas.core.member.MemberServiceImpl;
import nas.core.order.OrderService;
import nas.core.order.OrderServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

public class ConfigurationSingletonTest {

    @Test
    void configurationTest() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
        OrderServiceImpl orderService = ac.getBean("orderService", OrderServiceImpl.class);
        MemberRepository memberRepository = ac.getBean("memberRepository", MemberRepository.class);

        assertThat(memberService.getMemberRepository())
                .isSameAs(orderService.getMemberRepository());

        assertThat(memberService.getMemberRepository())
                .isSameAs(memberRepository);

        assertThat(orderService.getMemberRepository())
                .isSameAs(memberRepository);
    }

    @Test
    void configurationDeep() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        AppConfig config = ac.getBean(AppConfig.class);
        System.out.println("config = " + config);
    }
}
