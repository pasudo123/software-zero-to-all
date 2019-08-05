
## Hibernate Tutorial
자바 오브젝트를 데이터베이스에 저장 및 영속화 시킬 수 있는 프레임워크이다. 
* 하이버네이트는 SQL 의 모든 로우 레벨을 다루고 있다.
* 개발함에 있어서 JDBC 코드를 최소화한다.
* 하이버네이트는 ORM 을 제공한다.

__Saving a Java Object With Hibernate__
```java
Student student = new Student("홍", "길동", "hong@gmail.com")

// PK 를 리턴한다.
int id = (Integer) session.save(student); 

// PK 를 통해서 데이터베이스에서 객체를 반환한다.
Student myStudent = session.get(Student.class, id);
```

__Querying for Java Object__
```java
// Hibernate Query Language (HQL)
Query query = session.createQuery("from Student);

// 학생목록을 리턴한다.
List<Student> students = query.list();
```

하이버네이트와 JDBC 의 관계
* 하이버네이트는 데이터베이스와 통신하기 위해 JDBC 를 이용한다.
* Java App < -- > ( Hibernate | JDBC ) < -- > Database

__Database 계정 생성 및 테이블 작성__
```sql
CREATE USER 'hbstudent'@'localhost' IDENTIFIED BY 'hbstudent';

GRANT ALL PRIVILEGES ON * . * TO 'hbstudent'@'localhost';

-- 
CREATE DATABASE  IF NOT EXISTS `hb_student_tracker`;
USE `hb_student_tracker`;

--
-- Table structure for table `student`
--

DROP TABLE IF EXISTS `student`;

CREATE TABLE `student` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
```
