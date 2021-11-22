package com.jpabook.jpashop.repository;

import com.jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager entityManager;

    /*
    * 저장하기 메서드
    * 저장하기 전까지는 아이디값이 없다. == 새로 생성해야함 == persist로 신규등록해야함
    * 아이디가 있다 == 디비에 있던걸 가져옴 == 업데이트해야함 == merge는 업데이트는 아니지만 비슷한 것임
    * */
    public void save(Item item) {
        if (item.getId() == null) {
            entityManager.persist(item);
        } else {
            Item merge = entityManager.merge(item);
        }
    }

    public Item findOne(Long id) {
        return entityManager.find(Item.class, id);
    }

    public List<Item> findAll() {
        return entityManager.createQuery("select i from Item i", Item.class)
                .getResultList();
    }
}
