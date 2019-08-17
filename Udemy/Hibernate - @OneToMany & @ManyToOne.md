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


## @OneToMany & @ManyToOne 단방향 및 양방향 연관관계
- 외래키가 있는 쪽이 있다면 일반적으로 조인을 통해서 다른 테이블의 데이터를 조회할 수 있다.
- JPA 는 연관관계가 맺어져 있고, 외래키가 있다면 A 에서 B 의 데이터를 또는 B 에서 A 의 데이터를 조회할 수 있다.
- 단방향은 연관관계의 주인만이 연관된 엔티티의 데이터를 조회할 수 있지만, 양방향은 연관관계의 주인 뿐만 아니라 타 엔티티들도 주인 엔티티의 값을 조회할 수 있다.
- __예전에 내용을 보았는데 DB 설계를 할 시에 모든 것은 단방향으로 설계하고 필요시에 양방향 연관관계를 설정해주는 것이 좋다고 한다.__
    - __왜냐하면 양방향 설정은 DB 설계를 바꾸는 것이 아닌 단순하게 자바코드만 일부 수정하면 되는 문제이기 때문이다.__
    - __따라서 DB 설계는 단방향으로 설계하되 필요시 양방향으로 하는 것이 좋다__
- __양방향은 또한 특정 엔티티의 CRUD 작업이 연관된 엔티티에 일어났을 가능성이 농후하기 때문에 엔티티의 변경점을 탐색하기 어렵다는 단점이 있다.__

### Book 과 BookStore 의 단방향 관계
__Book Entity__
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

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "BOOK_STORE_ID", foreignKey = @ForeignKey(name = "FK_BOOK_BOOK_STORE_ID"))
    private BookStore bookStore;

    @Builder
    public Book(String name) {
        this.name = name;
    }

    void setBookStore(BookStore bookStore) {
        this.bookStore = bookStore;
    }
}
```

__Book Store Entity__
```java
@Entity
@Table(name = "BOOK_STORE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
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

### Test Code 1 : 단방향, Book 에서 BookStore 조회
```java
@Test
public void 단방향_탐색_테스트() {

    List<Book> bookList = bookRepository.findAll();

    for(Book book : bookList){
        System.out.println("책 이름 :: " + book.getName());
        System.out.println(book.getBookStore());
    }
}
```

```
Hibernate: select book0_.id as id1_0_, book0_.book_store_id as book_sto3_0_, book0_.name as name2_0_ from book book0_
Hibernate: select bookstore0_.id as id1_1_0_, bookstore0_.name as name2_1_0_ from book_store bookstore0_ where bookstore0_.id=?
책 이름 :: 참을 수 없는 존재의 가벼움1
BookStore(id=1, name=밀란쿤데라 서점2)
```
- 첫번째는 Book Entity 에 대해서 __select Query__ 가 날라간다.
- 두번째는 Book Store Entity 에 대해서 __select Query__ 가 날라간다.

### Book 과 BookStore 의 양방향 관계 : 코드를 수정한다.
- 양방향에서는 연관관계의 주인에 대한 설정이 중요하다.
    - __연과관계의 주인은 항상 FK, 즉 외래키를 들고 있다.__
    - Book 과 BookStore 엔티티에서 연관관계가 맺어져있고 그 연관관계에 대한 주인은 Many 쪽이며, @JoinColumn 어노테이션을 가지고 있는 Book 엔티티가 연관관계의 주인이다.
    - 그리고 연관관계의 주인이 아닌 쪽에선 mappedBy = "" 속성을 이용하여 연관관계의 주인이 누구인지 명시해주어야 한다.

__Book Entity__
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

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "BOOK_STORE_ID", foreignKey = @ForeignKey(name = "FK_BOOK_BOOK_STORE_ID"))
    private BookStore bookStore;

    @Builder
    public Book(String name) {
        this.name = name;
    }

    void setBookStore(BookStore bookStore) {
        this.bookStore = bookStore;
        getBookStore().getBookList().add(this);
    }
}
```

__Book Store Entity__
```java
@Entity
@Table(name = "BOOK_STORE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class BookStore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @OneToMany(mappedBy = "bookStore", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Book> bookList = new ArrayList<>();

    @Builder
    public BookStore(String name){
        this.name = name;
    }
}
```

- 편의메소드라고 불리우는 것이 있는데, 일반적인 RDBMS 관점에서는 특정 테이블이 타 테이블의 PK 값을 가지고 있으면 된다. 하지만 객체지향의 관점에서는 특정 레퍼런스에 해당 되는 레퍼런스 값을 삽입해줌으로써 연관관계를 설정한다. 

```java
void setBookStore(BookStore bookStore) {
    this.bookStore = bookStore;
    getBookStore().getBookList().add(this);
}
```

위 코드를 살펴보면, Book 엔티티에 bookStore 레퍼런스를 집어넣는데, 필드값을 세팅해줌과 동시에 해당 bookStore 필드를 가져와 내부에 있는 getBookList() 를 호출해서 ```this``` 키워드로 Book 엔티티 자기 자신을 추가해준다. 이 부분이 편의메소드이다. 2019년 8월 17일 현재, 나도 이 부분에 대해서 더 공부를 하여야 한다.

__양방향에서의 탐색코드 작성__
```java
@Test
public void 양방향_탐색_테스트() {

    List<BookStore> bookStoreList = bookStoreRepository.findAll();

    for(BookStore store : bookStoreList){
        System.out.println("서점 이름 :: " + store.getName());

        for(Book book : store.getBookList()){
            System.out.println("책 이름 :: " + book.getName());
        }
    }
}
```

```
Hibernate: select bookstore0_.id as id1_1_, bookstore0_.name as name2_1_ from book_store bookstore0_
Hibernate: select booklist0_.book_store_id as book_sto3_0_0_, booklist0_.id as id1_0_0_, booklist0_.id as id1_0_1_, booklist0_.book_store_id as book_sto3_0_1_, booklist0_.name as name2_0_1_ from book booklist0_ where booklist0_.book_store_id=?
서점 이름 :: 밀란쿤데라 서점2
책 이름 :: 참을 수 없는 존재의 가벼움1
```

> 단방향일때는 BookStore 에서 Book 를 조회할 수 없었지만, 양방향이 되고 소스코드가 수정되고 나서는 가능하게 되었다.
