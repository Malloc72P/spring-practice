package com.jpabook.jpashop.domain;

import javax.persistence.Embeddable;

@Embeddable//어딘가에 내장될 수 있음을 나타냄
public class Address {

    private String city;
    private String street;
    private String zipcode;

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
