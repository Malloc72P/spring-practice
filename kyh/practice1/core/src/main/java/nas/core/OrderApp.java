package nas.core;

import nas.core.member.Grade;
import nas.core.member.Member;
import nas.core.member.MemberService;
import nas.core.member.MemberServiceImpl;
import nas.core.order.Order;
import nas.core.order.OrderService;
import nas.core.order.OrderServiceImpl;

public class OrderApp {
    public static void main(String[] args) {
        MemberService memberService = new MemberServiceImpl();
        OrderService orderService = new OrderServiceImpl();

        Long memberId = 1L;
        Member member = new Member(memberId, "memberA", Grade.VIP);
        memberService.join(member);

        Order order = orderService.createOrder(memberId, "itemA", 10000);
        System.out.println("order : " + order);

    }
}
