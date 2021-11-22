package nas.core.scan.filter;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target(ElementType.TYPE)//타겟은 필드에 붙냐 클래스에 붙냐 라는 것.
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyIncludeComponent {
}
