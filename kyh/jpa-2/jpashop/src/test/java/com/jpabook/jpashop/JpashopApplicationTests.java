package com.jpabook.jpashop;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JpashopApplicationTests {

	@Test
	void contextLoads() {
		Hello hello = new Hello();
		int expected = 123;
		hello.setValue(expected);
		int value = hello.getValue();
		Assertions.assertThat(value).isEqualTo(expected);
	}

}
