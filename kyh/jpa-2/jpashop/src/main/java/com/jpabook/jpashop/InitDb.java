package com.jpabook.jpashop;

import com.jpabook.jpashop.domain.*;
import com.jpabook.jpashop.domain.item.Book;
import com.jpabook.jpashop.repository.MemberRepository;
import com.jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1();
        initService.dbInit2();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;

        public void dbInit1() {
            Member member = createMember("서울", "st1", "12345");
            em.persist(member);

            Book book1 = createBook("JPA1 Book", 10000);
            em.persist(book1);

            Book book2 = createBook("JPA2 Book", 20000);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 10000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 20000, 2);

            Delivery delivery = createDelivery(member);
            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);
        }

        public void dbInit2() {
            Member member = createMember("진주", "st2", "54321");
            em.persist(member);

            Book book1 = createBook("JPA1 Book", 10000);
            em.persist(book1);

            Book book2 = createBook("JPA2 Book", 20000);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 10000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 20000, 2);

            Delivery delivery = createDelivery(member);
            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);
        }

        private Delivery createDelivery(Member member) {
            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());
            return delivery;
        }

        private Book createBook(String bookName, int bookPrice) {
            Book book1 = new Book();
            book1.setName(bookName);
            book1.setPrice(bookPrice);
            book1.setStockQuantity(100);
            return book1;
        }

        private Member createMember(String name, String street, String zipcode) {
            Member member = new Member();
            member.setName("UserA");
            member.setAddress(new Address(name, street, zipcode));
            return member;
        }
    }

}
