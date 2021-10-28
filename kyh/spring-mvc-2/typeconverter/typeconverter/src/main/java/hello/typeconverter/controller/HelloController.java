package hello.typeconverter.controller;

import hello.typeconverter.type.IpPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/hello")
public class HelloController {

    @GetMapping("/v1")
    public String helloV1(HttpServletRequest request) {
        String data = request.getParameter("data");
        int intValue = Integer.parseInt(data);
        log.info("intValue = {}", intValue);
        return "ok";
    }

    @GetMapping("/v2")
    public String helloV2(@RequestParam Integer data) {
        log.info("data = {}", data);
        return "ok";
    }

    @GetMapping("/ip-port")
    public String ipPort(@RequestParam IpPort data) {
        log.info("data = {}", data);
        return "ok";
    }
}
