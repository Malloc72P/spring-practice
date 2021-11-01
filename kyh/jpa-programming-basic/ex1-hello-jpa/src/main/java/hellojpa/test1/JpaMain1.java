package hellojpa.test1;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;
import java.util.function.Consumer;

public class JpaMain1 {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        createMember(emf);
//        updateMember(emf);
//        testCache(emf);
//        testDirtyChecking(emf);
//        testFlush(emf);
//        testDetached(emf);
        findAllByAgeCondition(emf);

        emf.close();
    }

    private static void createMember(EntityManagerFactory emf) {
        doTransaction(emf, entityManager -> {
            Member member1 = new Member(1L, "smith");
            Member member2 = new Member(2L, "john");
            entityManager.persist(member1);
            entityManager.persist(member2);
        });
    }

    private static void updateMember(EntityManagerFactory emf) {
        doTransaction(emf, (entityManager) -> {
            Member member = entityManager.find(Member.class, 1L);
            System.out.println("member = " + member);
            member.setUsername("new-name");
        });
    }

    private static void findAllByAgeCondition(EntityManagerFactory entityManagerFactory) {
        doTransaction(entityManagerFactory, entityManager -> {
            //JPQL은 엔티티 객체를 대상으로 쿼리를 날린다
            //검색을 할때도 엔티티 객체를 대상으로 검색하는게 가능하다
            List<Member> members = entityManager
                    .createQuery("select m from Member as m", Member.class)
                    .setFirstResult(1)
                    .setMaxResults(5)
                    .getResultList();

            for (Member member : members) {
                System.out.println("member = " + member);
            }
        });
    }

    private static void testCache(EntityManagerFactory entityManagerFactory) {
        doTransaction(entityManagerFactory, entityManager -> {
            Member member1 = entityManager.find(Member.class, 1L);
            Member member2 = entityManager.find(Member.class, 1L);
            Member member3 = entityManager.find(Member.class, 1L);

            System.out.println("member = " + member1);
            System.out.println("member2 = " + member2);
            System.out.println("member3 = " + member3);

            if (member1 == member2 && member2 == member3) {
                System.out.println("ok");
            }
        });
    }

    private static void testDirtyChecking(EntityManagerFactory entityManagerFactory) {
        doTransaction(entityManagerFactory, entityManager -> {
            Member member1 = entityManager.find(Member.class, 1L);
            member1.setUsername("dirty-harry");
        });
    }

    private static void testFlush(EntityManagerFactory entityManagerFactory) {
        doTransaction(entityManagerFactory, entityManager -> {
            Member member1 = entityManager.find(Member.class, 1L);
            member1.setUsername("flushed");
            entityManager.flush();
            System.out.println("------------------------");
        });
    }

    private static void testDetached(EntityManagerFactory entityManagerFactory) {
        doTransaction(entityManagerFactory, entityManager -> {
            Member member1 = entityManager.find(Member.class, 1L);
            entityManager.detach(member1);
            member1.setUsername("detached");
        });
    }

    private static void doTransaction(EntityManagerFactory emf, Consumer<EntityManager> consumer) {
        //트랜잭션 단위로 뭔가를 작업할 때 엔티티 매니저를 꼭 만들어야 한다.
        //JPA는 데이터를 변경하는 모든 작업을 트랜잭션 안에서 해줘야 한다
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        try {
            consumer.accept(entityManager);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            entityManager.clear();
        }
    }
}
