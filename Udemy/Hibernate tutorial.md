
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

__하이버네이트 이용하기__
1. 하이버네이트 설정 파일을 추가
2. 자바 애노테이션 클래스 추가
3. 데이터베이스 작업을 위한 코드를 작성
(하이버네이트는 JDBC를 이용해 데이터베이스와 통신한다.)

__하이버네이트 설정파일__
```xml
<hibernate-configuration>
    <session-factory>

        <!-- JDBC Connection Settings -->
        <property name=""></property>
        <property name="connection.driver_class">com.mysql.jdbc.Driver</property>
        <property name="connection.url">jdbc:mysql://localohost:3306/hb_student_tracker?useSSL=false&serverTimezone=UTC</property>
        <property name="connection.username">hbstudent</property>
        <property name="connection.password">hbstudent</property>

        <property name="connection.pool_size">1</property>

        <property name="dialect">org.hibernate.dialect.MySQLDialect</property>

        <property name="show_sql">true</property>

        <property name="current_session_context_class">thread</property>

        <!-- DB schema will be updated if needed -->
        <!--<property name="hbm2ddl.auto">update</property>-->
    </session-factory>
</hibernate-configuration>
```

> @Entity
데이터베이스 테이블과 매핑시킴을 명시하는 애노테이션

> @Table
데이터베이스 테이블을 선정

- SessionFactory
    - 하이버네이트 설정 파일을 읽는다.
    - 세션 오브젝트를 생성한다.
    - 앱에서 딱 한번 구동된다.

- Session
    - JDBC Connection 으로 래핑되어 있다.
    - 세션은 메인 오브젝트로서 객체를 저장 및 반환하기 위해 사용된다.
    - 수명주기가 짧다
    - 세션 팩토리로부터 반환된다.

> @GeneratedValue
- 식별자 자동 생성을 지정한다.
- 복합키보다는 인공키 사용을 권장한다.

> @GeneratedValue(strategy = GenerationType.AUTO)
- 특정한 데이터베이스에 적합한 전략을 생성 (데이터베이스에 관계없이 식별자를 자동으로 생성하라는 의미)

> @GenerationValue(strategy = GenerationType.IDENTITY)
- 기본키 생성을 데이터베이스에 위임한다. (MySQL 데이터베이스에 사용하는 일반적인 방법)

> @GenerationValue(strategy = GenerationType.SEQUENCE)
- 데이터베이스의 특별한 오브젝트 시퀀스를 사용하여 기본키를 생성

> @GenerationValue(strategy = GenerationType.TABLE)
- 데이터베이스 키 생성 전용 테이블을 하나 만들고 이를 사용하여 기본키를 생성

## Question
- JPA 엔티티는 왜 기본생성자 (NoArgsConsructor) 를 필요로 하는가?
    - 하이버네이트는 ```new``` 키워드를 통해서 엔티티 인스턴스를 생성한다. 만약 엔티티 클래스 네임이 Student 라고 한다면 하이버네이트는 new Student() 인스턴스를  만드는 것이다. 하이버네이트 내부적으로 __리플렉션 기법__ 을 이용해 해당 클래스를 생성하는데 이때 이용하는 것이 디폴트, 기본 생성자 인 것이다. 따라서 디버깅 하면 Constructor constructor 에 대해 __.newInstance() 메소드__ 를 호출하고 있음을 볼 수 있었다. 해당 메소드를 사용하기 위해선 해당 클래스에 대한 생성자가 인수가 없어야 한다. 추가적으로 해당 생성자는 공개적일 필요가 없다. 

> 그러고 보면, 롬복 애노테이션 중에서 NoArgsConstructor(Access = accessLevel.PROECTED) 하는 이유가 다 이런이유 때문이라고 생각한다.
 
- 하이버네이트는 무엇인가?
    - 하이버네이트는 JPA 라는 자바 ORM 표준의 구현체이며 명세라고 일컫을 수 있다. 하이버네이트를 만든 팀에서 JPA 어노테이션을 쓰는 것을 권장하고 있다.
