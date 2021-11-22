package nas.core.singleton;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import static org.assertj.core.api.Assertions.assertThat;

public class StatefulServiceTest {

    @Test
    void statefulServiceSingleton() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatefulService statefulService1 = ac.getBean(StatefulService.class);
        StatefulService statefulService2 = ac.getBean(StatefulService.class);

        //사용자A 10000원 주문
        statefulService1.order("userA", 10000);
        //A가 주문하는 사이에 사용자B가 끼어들어서 20000원 주문
        //싱글톤 빈이므로 같은 객체이다. 공유되는 빈인데 상태가 있고, 외부에서 이 상태를 쓰기 시작하면 문제가 생긴다.
        //서로가 서로의 작업을 덮어써버릴 수 있다.
        //이러면 어떤 결과가 발생할지 예측하기 어려워지고, 큰 문제가 될 수 있다.(서비스가 죽는다거나, 돈물어준다거나)
        //따라서 싱글톤 빈은 무상태로 설계 해야 한다.

        statefulService2.order("userB", 20000);

        //사용자A 주문조회
        int price = statefulService1.getPrice();
        assertThat(price).isEqualTo(20000);
    }

    static class TestConfig {
        @Bean
        public StatefulService statefulService() {
            return new StatefulService();
        }
    }
}
