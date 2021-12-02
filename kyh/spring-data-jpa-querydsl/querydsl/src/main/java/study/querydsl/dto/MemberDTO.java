package study.querydsl.dto;

import com.querydsl.core.annotations.QueryProjection;

public class MemberDTO {
    private String username;
    private int age;

    public MemberDTO() {
        System.out.println("MemberDTO.MemberDTO");
    }

    @QueryProjection
    public MemberDTO(String username, int age) {
        System.out.println("MemberDTO.MemberDTO - allArgsConstructor");
        this.username = username;
        this.age = age;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        System.out.println("MemberDTO.setUsername");
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        System.out.println("MemberDTO.setAge");
        this.age = age;
    }

    @Override
    public String toString() {
        System.out.println("MemberDTO.toString");
        return "MemberDTO{" +
                "username='" + username + '\'' +
                ", age=" + age +
                '}';
    }
}
