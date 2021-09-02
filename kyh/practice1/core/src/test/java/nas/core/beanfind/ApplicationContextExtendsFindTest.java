package nas.core.beanfind;

import nas.core.discount.DiscountPolicy;
import nas.core.discount.FixDiscountPolicy;
import nas.core.discount.RateDiscountPolicy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ApplicationContextExtendsFindTest {
    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);

    @Test
    @DisplayName("부모타입으로 조회시 자식이 둘 이상 있으면 중복오류가 발생함")
    void findBeanByParentTypeDuplicate() {
        assertThatThrownBy(() -> ac.getBean(DiscountPolicy.class))
                .isInstanceOf(NoUniqueBeanDefinitionException.class);
        Map<String, DiscountPolicy> beansOfType = ac.getBeansOfType(DiscountPolicy.class);
        assertThat(beansOfType.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("부모타입으로 조회시 자식이 둘 이상 있으면 빈 이름을 지정하라")
    void findBeanByParentTypeDuplicate2() {
        DiscountPolicy discountPolicy = ac.getBean("rateDiscountPolicy", DiscountPolicy.class);
        assertThat(discountPolicy).isInstanceOf(RateDiscountPolicy.class);
    }

    @Test
    @DisplayName("부모타입으로 조회시 자식이 둘 이상 있으면 구체타입을 사용하는 방법도 있기는 하다")
    void findBeanByParentTypeDuplicate3() {
        DiscountPolicy discountPolicy = ac.getBean(RateDiscountPolicy.class);
        assertThat(discountPolicy).isInstanceOf(RateDiscountPolicy.class);
    }

    @Test
    @DisplayName("Object타입으로 꺼내보자")
    void findAll() {
        Map<String, Object> beansOfType = ac.getBeansOfType(Object.class);
        for (String key : beansOfType.keySet()) {
            System.out.println();
            System.out.println("key = " + key);
            System.out.println("beansOfType.get(key) = " + beansOfType.get(key));
            System.out.println();
        }
    }

    @Configuration
    static class TestConfig {
        // 빈 설정을 보고 역할과 구현을 확인할 수 있다
        // 리턴타입으로 구체클래스를 사용해도 문제는 없지만, 역할에 해당하는 인터페이스로 한다면,
        // RateDiscountPolicy의 역할이 DiscountPolicy구나를 설정만 보고도 알 수 있는 것이다.
        @Bean
        public DiscountPolicy rateDiscountPolicy() {
            return new RateDiscountPolicy();
        }

        @Bean
        public DiscountPolicy fixDiscountPolicy() {
            return new FixDiscountPolicy();
        }
    }
}
