package nas.core.singleton;

import nas.core.AppConfig;
import nas.core.member.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.*;

public class SingletonTest {

    @Test
    @DisplayName("스프링 없는 순수 DI 컨테이너")
    void pureContainer() {
        AppConfig appConfig = new AppConfig();

        // 1차 조회
        MemberService memberService1 = appConfig.memberService();
        // 2차 조회
        MemberService memberService2 = appConfig.memberService();

        //참조값이 다름을 확인
        assertThat(memberService1).isNotSameAs(memberService2);
    }

    @Test
    @DisplayName("싱글톤 패턴을 적용한 객체 사용")
    void singletonServiceTest() {
        //SingletonService singletonService = new SingletonService();
        //'SingletonService()' has private access in 'nas.core.singleton.SingletonService

        //요청할때마다 객체를 생성하지 않으니 JVM 메모리도 절약하고, 소멸되는 객체도 줄어드니 GC로 인한 성능저하도 줄어든다.
        SingletonService singletonService1 = SingletonService.getInstance();
        SingletonService singletonService2 = SingletonService.getInstance();

        //하지만 싱글톤 패턴도 문제가 있다
        //싱글톤패턴을 적용하기 위한 코드를 추가해야 하는것도 단점이다.
        //의존관계상 클라이언트가 구체클래스에 의존하게 된다.
        /*
        DIP를 위반하게 된다. 인스턴스를 꺼내려면 구체클래스의 정적메서드를 거쳐야 하기 때문이다
        클라이언트가 구체클래스에 의존한다면 OCP도 위반할 가능성이 생긴다
        유연한 테스트가 어렵다고 한다
        private생성자를 사용하므로, 자식클래스를 만들기 어렵다
        유연성이 떨어진다. 의존성 주입이 어려워지기 때문이다.
        그래서 안티패턴으로 불리기도 한다

        싱글톤 패턴이 가진 단점을 다 해결하면서, 싱글톤으로 얻은 이득은 유지하려면 스프링의 싱글톤 컨테이너를 사용해야 한다.
        * */

        assertThat(singletonService1).isSameAs(singletonService2);
    }

    @Test
    @DisplayName("스프링의 싱글톤 컨테이너를 사용하는 경우")
    void usingSpringSingletonContainer() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        // 1차 조회
        MemberService memberService1 = ac.getBean(MemberService.class);
        // 2차 조회
        MemberService memberService2 = ac.getBean(MemberService.class);

        //참조값이 다름을 확인
        assertThat(memberService1).isSameAs(memberService2);
    }
}
