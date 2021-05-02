package nas.core;

import nas.core.discount.DiscountPolicy;
import nas.core.discount.FixDiscountPolicy;
import nas.core.member.MemberService;
import nas.core.member.MemberServiceImpl;
import nas.core.member.MemoryMemberRepository;
import nas.core.order.OrderService;
import nas.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public MemoryMemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    @Bean
    public OrderService orderService() {
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    @Bean
    public DiscountPolicy discountPolicy() {
        //return new RateDiscountPolicy();
        return new FixDiscountPolicy();
    }
}
