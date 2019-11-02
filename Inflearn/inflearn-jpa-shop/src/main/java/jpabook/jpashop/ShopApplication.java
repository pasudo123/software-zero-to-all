package jpabook.jpashop;

import jpabook.jpql.*;

import javax.persistence.*;
import java.util.List;

/**
 * Created by pasudo123 on 2019-09-01
 * Blog: https://pasudo123.tistory.com/
 * Email: oraedoa@gmail.com
 **/
public class ShopApplication {

    public static void main(String[] args) {

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("shop");

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        EntityTransaction tx = entityManager.getTransaction();

        /** 트랜잭션 수행 **/
        tx.begin();

        try {

            Team team = new Team();
            team.setName("홍팀");
            entityManager.persist(team);

            Member member1 = new Member();
            member1.setAge(150);
            member1.setUsername("이순신");
            member1.setMemberType(MemberType.ADMIN);

            Member member2 = new Member();
            member2.setAge(200);
            member2.setUsername("강감찬");
            member2.setMemberType(MemberType.ADMIN);

            // 편의 메소드 작성 (양방향 연관관계)
            member1.setTeam(team);
            member2.setTeam(team);

            entityManager.persist(member1);
            entityManager.persist(member2);

            entityManager.flush();
            entityManager.clear();

            /** Team 의 입장에서 양방향 연관관계 **/
            /** 매핑된 엔티티의 개수를 반환한다. **/
            String query = "SELECT size(t.members) FROM Team t";
            List<Integer> list = entityManager.createQuery(query, Integer.class).getResultList();

            /** 2가 출력된다. **/
            for(Integer element : list) {
                System.out.println(element);
            }

            tx.commit();

        } catch (Exception e) {
            System.out.println("Error :: " + e);
            tx.rollback();
        } finally {
            entityManager.close();
        }

        entityManagerFactory.close();

    }
}

