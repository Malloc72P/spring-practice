package jpql;

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

        try {
            transaction.begin();
            //------------------------
            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("member1");
            member.setAge(20);
            member.changeTeam(team);
            em.persist(member);

            Member member2 = new Member();
            member2.setUsername("member2");
            member2.setAge(20);
            em.persist(member2);

            em.flush();
            em.clear();

            /*List<Member> resultList = em.createQuery("select m from Member m order by m.age desc", Member.class)
                    .setFirstResult(1)
                    .setMaxResults(5)
                    .getResultList();*/

//            List<Member> resultList = em.createQuery("select m from Member m left outer join m.team t", Member.class)
//                    .getResultList();

//            List<Member> resultList = em.createQuery("select m from Member m left join m.team t on t.name = 'A'", Member.class)
//                    .getResultList();

//            List resultList = em.createQuery("select m.username, 'hello', true from Member m " +
//                            "where m.type = :userType")
//                    .setParameter("userType", MemberType.ADMIN)
//                    .getResultList();

//            List resultList = em.createQuery("select " +
//                            "case when m.age <= 10 then '학생요금' " +
//                            "when m.age >= 60 then '경로요금' " +
//                            "else '일반요금' " +
//                            "end " +
//                            "from Member m")
//                    .getResultList();

//            List<String> resultList = em.createQuery("select coalesce(m.username, '이름없는회원') as name from Member m", String.class)
//                    .getResultList();

//            List<String> resultList = em.createQuery("select 'a' || 'b' from Member  m", String.class).getResultList();
//            resultList.forEach(System.out::println);

            List<String> resultList = em.createQuery("select function('group_concat', m.username) from Member m", String.class).getResultList();
            resultList.forEach(System.out::println);


//            resultList.forEach(System.out::println);

            //------------------------
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }

}
