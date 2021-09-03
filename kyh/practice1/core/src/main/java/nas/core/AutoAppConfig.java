package nas.core;

import nas.core.member.MemberRepository;
import nas.core.member.MemoryMemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
//이게 붙은 클래스를 찾아서 자동으로 스프링 빈으로 등록한다
@ComponentScan(
        //AppConfig에는 Configuration애너테이션이 붙어있어서 자동스캔 대상이다.
        //근데 얘는 수동설정하던 방식이라서 제외하고 싶다
        //그럴때 아래와 같이 설정하면 된다
        //실무에선 이렇게 안하는데, 우리는 예제코드를 다 지우기 싫어서 이렇게 필터링을 거는 것.(별 의미 없다)
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
//        basePackages = "nas.core"
)
public class AutoAppConfig {
    @Bean(name = "memoryMemberRepository")
    MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }
}
