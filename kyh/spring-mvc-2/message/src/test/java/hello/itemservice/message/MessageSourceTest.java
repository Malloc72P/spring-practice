package hello.itemservice.message;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Message Source 학습을 위한 예제코드
 */
@SpringBootTest
public class MessageSourceTest {

    /**
     * Resources 폴더 밑의 모든 메세지를 들고 있는 메세지 소스 객체가 주입된다
     */
    @Autowired
    MessageSource messageSource;

    /**
     * MessageSource를 통해 메세지를 사용해본다
     */
    @Test
    void helloMessage() {
        String message = messageSource.getMessage("hello", null, null);

        assertThat(message).isEqualTo("안녕");
    }

    /**
     * 메세지를 찾을 수 없는 경우, NoSuchMessageException이 발생한다
     */
    @Test
    void notFoundMessageCode() {
        assertThatThrownBy(() -> messageSource.getMessage("no_code", null, null))
                .isInstanceOf(NoSuchMessageException.class);
    }

    /**
     * 파라미터로 Default message를 넘기는 경우, 메세지를 못찾았을 때 예외를 발생시키지 않고, <br>
     * Default message를 반환한다
     */
    @Test
    void notFoundMessageCodeDefaultMessage() {
        String message = messageSource.getMessage("no_code", null, "기본 메세지", null);
        assertThat(message).isEqualTo("기본 메세지");
    }

    /**
     * 메세지에 아규먼트를 넘기려면 Object[]로 담아서 넘겨야 한다
     */
    @Test
    void argumentMessage() {
        String message = messageSource.getMessage("hello.name", new Object[]{"Spring"}, null);
        assertThat(message).isEqualTo("안녕 Spring");
    }

    /**
     * 로케일을 넘기면 그에 맞는 메세지가 리턴된다
     */
    @Test
    void defaultLang() {
        assertThat(messageSource.getMessage("hello", null, null)).isEqualTo("안녕");
        assertThat(messageSource.getMessage("hello", null, Locale.KOREA)).isEqualTo("안녕");
        assertThat(messageSource.getMessage("hello", null, Locale.ENGLISH)).isEqualTo("hello");
    }
}
