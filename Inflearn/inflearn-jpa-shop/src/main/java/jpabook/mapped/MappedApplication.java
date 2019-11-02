//package jpabook.mapped;
//
//import jpabook.mapped.domain.Movie;
//
//import javax.persistence.EntityManager;
//import javax.persistence.EntityManagerFactory;
//import javax.persistence.EntityTransaction;
//import javax.persistence.Persistence;
//
///**
// * Created by pasudo123 on 2019-09-18
// * Blog: https://pasudo123.tistory.com/
// * Email: oraedoa@gmail.com
// **/
//public class MappedApplication {
//
//    public static void main(String[]args) {
//
//        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("shop");
//
//        EntityManager entityManager = entityManagerFactory.createEntityManager();
//
//        EntityTransaction tx = entityManager.getTransaction();
//
//        /** 트랜잭션 수행 **/
//        tx.begin();
//
//        try{
//
//            Movie movie = new Movie();
//            movie.setActor("홍길동");
//            movie.setDirector("봉준호");
//            movie.setName("기생충");
//            movie.setPrice(1000);
//
//            entityManager.persist(movie);
//            entityManager.flush();
//
//            tx.commit();
//
//
//        } catch(Exception e) {
//            System.out.println("Error :: " + e);
//            tx.rollback();
//        } finally {
//            entityManager.close();
//        }
//
//        entityManagerFactory.close();
//    }
//}
