package hello.upload.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

@Slf4j
@Controller
@RequestMapping("/servlet/v2")
public class ServletUploadControllerV2 {

    @Value("${file.dir}")
    private String fileDir;

    @GetMapping("/upload")
    public String newFile() {
        return "upload-form";
    }

    @PostMapping("/upload")
    public String saveFileV1(HttpServletRequest request) throws ServletException, IOException {
        log.info("request = {}", request.getClass().toString());
        String itemName = request.getParameter("itemName");
        log.info("itemName = {}", itemName);

        Collection<Part> parts = request.getParts();
        log.info("parts = {}", parts);

        for (Part part : parts) {
            log.info("===part===");
            log.info("part = {}", part);
            //파트의 헤더 읽기
            Collection<String> headerNames = part.getHeaderNames();
            for (String headerName : headerNames) {
                log.info("{} = {}", headerName, part.getHeader(headerName));
            }
            //편의 메서드
            log.info("part.getSubmittedFileName() = {}", part.getSubmittedFileName());
            log.info("part.getSize() = {}", part.getSize());
            //데이터 읽기
            InputStream inputStream = part.getInputStream();
            String body = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
//            log.info("body = {}", body);

            if (StringUtils.hasText(part.getSubmittedFileName())) {
                String fullPath = fileDir + part.getSubmittedFileName();
                log.info("파일 저장 : {}", fullPath);
                part.write(fullPath);
            }
        }

        return "upload-form";
    }
}

