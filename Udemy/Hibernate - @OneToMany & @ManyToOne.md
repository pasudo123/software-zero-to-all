## Hibernate 에서 쓰이는 @OneToMany & @ManyToOne
- ORM 에서 하나의 엔티티가 여러 개의 엔티티를 컬렉션으로 가지고 있는 경우 (@OneToMany)
- ORM 에서 여러 개의 엔티티들으 하나의 엔티티를 레퍼런스로 가지고 있는 경우 (@ManyToOne)

## 알아볼 예제
- __책과 책방(=서점)__
- 책은 하나의 서점에 소속되어 있다. (__@ManyToOne__)
- 서점은 여러 개의 책을 소유할 수 있다. (__@OneToMany__)
- 책 (= Book) & 서점 (= BookStore)


### Book Entity
```java
@Entity
@Table(name = "BOOK")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString(exclude = "bookStore")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @ManyToOne
    @JoinColumn(name = "BOOK_STORE", foreignKey = @ForeignKey(name = "FK_BOOK_STORE"))
    private BookStore bookStore;

    @Builder
    public Book(String name){
        this.name = name;
    }

    public void setBookStore(BookStore bookStore){
        this.bookStore = bookStore;
    }
}
```

- @ManyToOne 어노테이션을 붙이고, 해당 BookStore 엔티티와 다대일 관계에 있음을 명시한다.
- @JoinColumn(name = "") 은 Book 엔티티 내에 조인되는 컬럼값을 무엇으로 할 지 지정한다.
- foreignKey = @ForeignKey(name = "") 은 조인되는 컬럼값은 외래키이며 해당 외래키 명을 무엇으로 할 지 지정한다.

### Book Store Entity
```java
@Entity
@Table(name = "BOOK_STORE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class BookStore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Builder
    public BookStore(String name){
        this.name = name;
    }
}
```


### Test Code 1 :: 서점에 책을 세팅한다.
```java
@Test
@Transactional
public void 책_및_서점_생성_테스트() {

    Book book = Book.builder()
            .name("참을 수 없는 존재의 가벼움")
            .build();

    BookStore bookStore = BookStore
            .builder()
            .name("밀란쿤데라 서점")
            .build();


    entityManager.persist(book);

    book.setBookStore(bookStore);

    entityManager.persist(bookStore);

    entityManager.flush();
}
```

```
Hibernate: insert into book (book_store_id, name) values (?, ?)
Hibernate: insert into book_store (name) values (?)
Hibernate: update book set book_store_id=?, name=? where id=?
```

- 엔티티 매니저를 통해 비영속 상태의 엔티티 Book 을 영속상태로 만든다. 영속상태로 만들어 해당 엔티티는 데이터베이스에 저장되고 이후에 메뢰 에서도 동일한 상태로 존재한다.
- 이후에 영속상태에 있는 Book 에 BookStore 를 세팅한다.
- 엔티티 매니저를 통해 비영속 상태의 엔티티 BookStore 를 영속상태로 만든다.
- 마지막으로 flush 메소드를 통해서 영속상태의 Book,BookStore 를 __데이터베이스와 동기화 시키는 작업을 수행__ 하면서 이 때 Update 쿼리가 날라간다. __만약에 변경이 일어나지 않았다면 업데이트 쿼리가 날라가지 않았을 것__ 이다.


### Test Code 2 :: book.setBookStore(bookStore) 의 순서 변경
```java
book.setBookStore(bookStore);

entityManager.persist(book);
entityManager.persist(bookStore);
entityManager.flush();
```

- 순서를 변경하면 update 쿼리는 flush() 를 수행하는 경우 날라가지 않는다. 왜냐하면 영속성 컨텍스트에 의해 관리되기 이전에 setBookStore() 메소드를 수항하였기 때문이다.

```
Hibernate: insert into book_store (name) values (?)
Hibernate: insert into book (book_store_id, name) values (?, ?)
```


### Test Code 3 :: entityManager 를 사용 (X), JpaRepository 사용 및 cascade = CascadeType.PERSIST) 적용
##### Book Entity 에 cascade 설정
```
@ManyToOne(cascade = CascadeType.PERSIST)
@JoinColumn(name = "BOOK_STORE_ID", foreignKey = @ForeignKey(name = "FK_BOOK_BOOK_STORE_ID"))
private BookStore bookStore;
```

- cascade 설정하면 연관된 엔티티들도 동일한 연산작업을 수행한다. (PERSIST, MERGE, DETACH, REFRESH, REMOVE, ALL 이 존재한다.)

```java
book.setBookStore(bookStore);
bookRepository.save(book);
```

- 위와 같이 수행하면 앞선 __Test Code 2__ 내용과 같이 동일하게 쿼리가 날라감을 확인할 수 있다.
