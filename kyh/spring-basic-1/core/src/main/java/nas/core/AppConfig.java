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
        System.out.println("AppConfig.memberService");
        return new MemberServiceImpl(memberRepository());
    }


    @Bean
    public OrderService orderService() {
        System.out.println("AppConfig.orderService");
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    @Bean
    public MemoryMemberRepository memberRepository() {
        System.out.println("AppConfig.memberRepository");
        return new MemoryMemberRepository();
    }

    @Bean
    public DiscountPolicy discountPolicy() {
        //return new RateDiscountPolicy();
        return new FixDiscountPolicy();
    }
}
