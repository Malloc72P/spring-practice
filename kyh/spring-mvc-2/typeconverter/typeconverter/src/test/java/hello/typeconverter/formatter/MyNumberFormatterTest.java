package hello.typeconverter.formatter;

import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

class MyNumberFormatterTest {

    MyNumberFormatter numberFormatter = new MyNumberFormatter();

    @Test
    void parse() throws ParseException {
        Number number = numberFormatter.parse("1,000", Locale.KOREA);
        assertThat(number).isEqualTo(1000L);
    }

    @Test
    void print() {
        String print = numberFormatter.print(1000, Locale.KOREA);
        assertThat(print).isEqualTo("1,000");
    }
}
