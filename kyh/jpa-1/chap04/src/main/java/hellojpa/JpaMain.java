package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        try {
            Team team1 = new Team();
            team1.setName("teamA");
            em.persist(team1);

            Team team2 = new Team();
            team2.setName("teamB");
            em.persist(team2);

            Member member1 = new Member();
            member1.setUsername("member1");
            member1.setTeam(team1);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("member2");
            member2.setTeam(team2);
            em.persist(member2);

            Member member3 = new Member();
            member3.setUsername("member3");
            member3.setTeam(team2);
            em.persist(member3);

            List<Member> memberList = new ArrayList<>();
            memberList.add(member1);
            memberList.add(member2);
            memberList.add(member3);

            String query = "update Member m set m.age = 20";
            int resultCount = em.createQuery(query).executeUpdate();
            System.out.println("resultCount = " + resultCount);

            em.clear();

            List<Long> memberIds = memberList.stream()
                    .mapToLong(Member::getId)
                    .boxed()
                    .collect(Collectors.toList());

            String findByIds = "select m from Member m where m.id in :memberIds";
            List<Member> newMemberList = em.createQuery(findByIds, Member.class)
                    .setParameter("memberIds", memberIds)
                    .getResultList();

            newMemberList.forEach(member -> System.out.println("age : " + member.getAge()));

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
