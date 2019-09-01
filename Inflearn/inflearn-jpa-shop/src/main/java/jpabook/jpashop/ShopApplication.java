package jpabook.jpashop;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 * Created by pasudo123 on 2019-09-01
 * Blog: https://pasudo123.tistory.com/
 * Email: oraedoa@gmail.com
 **/
public class ShopApplication {

    public static void main(String[]args){

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("test");

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        EntityTransaction tx = entityManager.getTransaction();

        /** 트랜잭션 수행 **/
        tx.begin();

        try{

            Order order = entityManager.find(Order.class, 1L);
            Member foundMember = order.getMember();



            tx.commit();

        } catch(Exception e) {
            System.out.println("Error :: " + e);
            tx.rollback();
        } finally {
            entityManager.close();
        }

        entityManagerFactory.close();
    }
}

