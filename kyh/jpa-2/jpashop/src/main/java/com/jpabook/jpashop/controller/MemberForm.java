package com.jpabook.jpashop.controller;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

/* Form 객체
 * 계층간에 데이터 전달을 위해서 사용함
 * DTO와 역할이 같음
 * 이름을 DTO가 아닌 Form이라고 지은 이유는 제약을 더 두기 위함이다
 * Form은 웹 기술임. 따라서 웹 계층인 컨트롤러까지만 사용해야 함을 나타내기 위해서 DTO대신 Form을 사용했다
 * DTO는 컨트롤러, 서비스, 리포지토리 어디든 사용할 수 있다
 * @NotEmpty와 같은 검증을 위한 애너테이션을 달면 검증을 할 수 있다
 * 컨트롤러에서는 @Valid를 달면 된다
 * */
@Getter
@Setter
public class MemberForm {

    @NotEmpty(message = "회원 이름은 필수 입니다")
    private String name;

    private String city;
    private String street;
    private String zipcode;
}
