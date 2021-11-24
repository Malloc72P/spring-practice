package com.jpabook.jpashop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded//내장타입을 쓸 때 둘 중 하나만 애너테이션을 달면 되지만 둘 다 붙여놓는편이 좋다. 한쪽만 봐도 내장타입이란걸 알 수 있으니까
    private Address address;

    //멤버는 주문을 여러개 가지고 있으므로 일대다
    @JsonIgnore
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

}
