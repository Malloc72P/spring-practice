package hello.springmvc.basic.requestmapping;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * HTTP 요청 매핑을 어떻게 하는지 학습합니다
 * 요청 매핑만 해보고 나머지는 더미 API로 만듭니다
 * 회원 관리 API
 * 회원 목록조회 GET /users
 * 회원등록 POST /users
 * 회원 조회 GET /users/{userId}
 * 회원 수정 PATCH /users/{userId}
 * 회원 삭제 DELETE /users/{userId}
 */
@Slf4j
@RestController
@RequestMapping("/mapping/users")
public class MappingClassController {

    /**
     * 회원 목록조회 더미 API
     *
     * @return dummy
     */
    @GetMapping
    public String users() {
        log.info("users called");
        return "getUser";
    }

    /**
     * 회원 추가 더미 API
     *
     * @return dummy
     */
    @PostMapping
    public String addUser() {
        log.info("addUser called");
        return "post user";
    }

    /**
     * 특정 회원 상세조회 더미 API
     *
     * @param userId 조회할 회원의 ID
     * @return dummy
     */
    @GetMapping("/{userId}")
    public String findUser(@PathVariable String userId) {
        log.info("findUser called");
        log.info("userId : {}", userId);
        return "findUser";
    }

    /**
     * 회원 정보 수정 더미 API
     *
     * @param userId 정보 수정할 회원의 아이디
     * @return dummy
     */
    @PatchMapping("/{userId}")
    public String updateUser(@PathVariable String userId) {
        log.info("updateUser called");
        log.info("userId : {}", userId);
        return "updateUser";
    }

    /**
     * 회원 삭제 더미 API
     *
     * @param userId 삭제할 회원의 아이디
     * @return dummy
     */
    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable String userId) {
        log.info("deleteUser called");
        log.info("userId : {}", userId);
        return "deleteUser";
    }
}
