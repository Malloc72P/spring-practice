package nas.core.discount;

import nas.core.member.Grade;
import nas.core.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RateDiscountPolicyTest {

    DiscountPolicy discountPolicy = new RateDiscountPolicy();

    @Test
    @DisplayName("Vip는 10퍼 할인 적용이 필요")
    void vipShouldBeApplied() {
        Member memberVIP = new Member(1L, "memberVIP", Grade.VIP);
        int discount = discountPolicy.discount(memberVIP, 10000);
        Assertions.assertThat(discount).isEqualTo(1000);
    }

    @Test
    @DisplayName("VIP아니면 할인 적용되면 안됨")
    void noneVipShouldNotBeApplied() {
        Member noneVip = new Member(1L, "memberVIP", Grade.BASIC);
        int discount = discountPolicy.discount(noneVip, 10000);
        Assertions.assertThat(discount).isEqualTo(0);
    }
}
