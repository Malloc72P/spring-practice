package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        try {
            Address address = new Address("home-city", "home-street", "12345");

            Member member1 = new Member();
            member1.setUsername("hello");
            member1.setHomeAddress(address);

            member1.getFavoriteFoods().add("치킨");
            member1.getFavoriteFoods().add("피자");
            member1.getFavoriteFoods().add("콜라");

            member1.getAddressHistory().add(new Address("city1", "street1", "12345"));
            member1.getAddressHistory().add(new Address("city2", "street2", "12345"));
            member1.getAddressHistory().add(new Address("city3", "street3", "12345"));

            em.persist(member1);

            em.flush();
            em.clear();

            Member findMember = em.find(Member.class, member1.getId());
            findMember.setHomeAddress(new Address("new-1", "st-1", "12121"));

            findMember.getFavoriteFoods().remove("치킨");
            findMember.getFavoriteFoods().add("한식");

            findMember.getAddressHistory().remove(new Address("city1", "street1", "12345"));
            findMember.getAddressHistory().add(new Address("new-city1", "new-street1", "new-12345"));

            em.persist(findMember);

            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

        emf.close();
    }
}
