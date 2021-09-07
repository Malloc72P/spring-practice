package nas.core.autowired;

import nas.core.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.lang.Nullable;

import java.util.Optional;

public class AutowiredTest {

    @Test
    void autowiredOption() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestBean.class);

    }

    static class TestBean {

        private final Member member;

        TestBean(@Nullable Member member, Optional<Member> member2) {
            System.out.println("TestBean.TestBean");
            System.out.println("member = " + member);
            System.out.println("member2 = " + member2);
            this.member = member;
        }

        //Member는 빈으로 등록되지 않았다.
        @Autowired(required = false)
        public void setNoBean1(Member member) {
            System.out.println("TestBean.setNoBean1");
            System.out.println("member = " + member);
        }

        @Autowired
        public void setNoBean2(@Nullable Member member) {
            System.out.println("TestBean.setNoBean2");
            System.out.println("member = " + member);
        }

        @Autowired
        public void setNoBean3(Optional<Member> member) {
            System.out.println("TestBean.setNoBean3");
            System.out.println("member = " + member);
        }
    }
}
