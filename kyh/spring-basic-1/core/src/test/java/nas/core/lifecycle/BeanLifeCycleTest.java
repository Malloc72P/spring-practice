package nas.core.lifecycle;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BeanLifeCycleTest {

    @Test
    void lifecycleTest() {
        ConfigurableApplicationContext applicationContext = new AnnotationConfigApplicationContext(LifeCycleConfig.class);
        NetworkClient client = applicationContext.getBean(NetworkClient.class);
        applicationContext.close();
    }

    @Configuration
    static class LifeCycleConfig {
//        @Bean(initMethod = "init", destroyMethod = "close")
        @Bean
        public NetworkClient networkClient() {
            NetworkClient networkClient = new NetworkClient();
            //객체를 생성한 다음 설정이 들어왔다. 초기화하고나서 설정하면 정상적인 초기화가 힘들다.
            //값을 세팅하고 나서 초기화를 호출해야 할 때가 많다. 외부에서 설정자를 호출해서 설정한다음 초기화를 하려면 어떻게 해야 할까
            networkClient.setUrl("http://hello-spring.dev");
/*            스프링 빈은 객체생성, 의존관계 주입의 과정을 거친다(생성자는 객체생성하면서 의존관계까지 주입해버린다는 특징이 있음을 유의하자)
            객체생성, 의존관계 주입이 끝나야 데이터를 사용할 수 있는 준비가 완료된다.
            초기화라는건 커넥션 풀 준비같은걸 말한다. 의존관계 설정까지 다 완료된 다음 해야 한다.
            근데 의존관계 준비가 다 되었다는걸 개발자가 어떻게 알 수 있냐가 문제인데, 빈 생명주기 콜백을 여기서 사용하면 된다.
            스프링 빈은 이벤트 라이프사이클이 있다.
            스프링 컨테이너 생성, 빈 생성, 의존관계 주입(수정자를 이용한 의존성 주입), 초기화 콜백, 사용, 소멸 전 콜백, 스프링 종료
            초기화 콜백은 빈이 생성되고 의존관계 주입이 완료된 다음 호출된다
            소멸 전 콜백은 빈이 소멸되기 직전에 호출된다.
            참고로 객체생성과 초기화는 분리해서 생각해야 한다.
            객체생성은 인스턴스 생성에만 집중한다. 메모리에 인스턴스를 생성하고, 필수값 설정에만 집중한다.
            의존성 주입은 자기 일에만 집중한다.
            초기화는 객체생성 및 의존성 주입에서 준비해놓은 값을 활용해서 외부 커넥션을 연결하는 등의 무거운 작업을 수행한다.
            생성자 안에서 해도 되는건 객체 내부의 값을 세팅하는 수준에 그쳐야 한다.
            외부와 연결을 맺는 것은 초기화 메서드에서 하는 것이 좋다. 그래야 유지보수할 때 편하다
            또, 위와 같이 작업을 분리해두면, 객체생성해놓고 외부와 연결하는 작업은 나중으로 미룰 수 있다.
            스프링은 인터페이스를 통해서도 빈 생명주기 콜백을 지원하고, 설정정보를 통해서도 지원하고 애너테이션을 통해서 하는것도 지원한다.*/
            return networkClient;
        }
    }

}
