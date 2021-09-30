package hello.servlet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/*
 * 1. @ServletComponentScan
 * 스프링이 자동으로 내 패키지 아래의 서블릿을 찾아서 등록해준다
 * 즉 서블릿 자동등록하는 설정인 것이다
 * */
@ServletComponentScan
@SpringBootApplication
public class ServletApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServletApplication.class, args);
    }

}
