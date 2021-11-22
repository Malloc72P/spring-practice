package hellojpa.test2;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain2 {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        for (int testCount = 1; testCount <= 5; testCount++) {
            EntityManager entityManager = emf.createEntityManager();
            EntityTransaction transaction = entityManager.getTransaction();

            try {
                transaction.begin();
                System.out.println("----------------------------------------begin");

                for (int i = 1; i <= 3; i++) {
                    TestMember testMember = new TestMember(testCount + "-member-" + i);
                    entityManager.persist(testMember);
                    System.out.println("testMember.getId() = " + testMember.getId());
                }
                System.out.println("----------------------------------------after persist");
                transaction.commit();
                System.out.println("----------------------------------------commit");
            } catch (Exception e) {
                transaction.rollback();
            } finally {
                entityManager.close();
            }
        }

        emf.close();
    }
}
