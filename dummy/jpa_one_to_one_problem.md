## spring jpa 에서 @OneToOne, 1:1 관계에 대한 지연로딩이 안된다.
* OneToOne 관계에서 mappedBy 가 있냐 없냐에 따라서, 지연로딩 여부가 결정된다. 왜그런걸까?
* mappedBy 는 연관관계의 주인을 결정하는데, 해당 내용에서는 주인을 BookDetail 로 설정/해제 한다.

## 1. 엔티티 관계도
```shell
Book : BookDetail = 1:1
```

Book
```kotlin
@Entity
@Table
class Book(
    // 내용 생략
): BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @OneToOne(
        mappedBy = "book",
        fetch = FetchType.LAZY,
        targetEntity = BookDetail::class,
        optional = true,
    )
    var detail: BookDetail? = null

    fun setBy(detail: BookDetail) {
        this.detail = detail
    }
}
```

BookDetail
```kotlin
@Entity
@Table(name = "book_detail")
class BookDetail(
    @Column(columnDefinition = "TEXT")
    var content: String
) : BaseEntity(){

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @OneToOne(targetEntity = Book::class)
    @MapsId
    var book: Book? = null
        protected set

    fun setBy(book: Book) {
        this.book = book
        book.setBy(this)
    }
}
```

## 2. 테스트코드 쿼리 확인용
```kotlin
@OneToOne(
    mappedBy = "book",
    fetch = FetchType.LAZY,
    targetEntity = BookDetail::class,
    optional = true,
)
var detail: BookDetail? = null
```

```kotlin
@RepositorySupport
internal class BookRepositoryTest(
    private val entityManager: TestEntityManager,
    private val bookRepository: BookRepository
) {

    private val log = LoggerFactory.getLogger(javaClass)

    @Test
    fun findAllTest() {
        repeat(5) {
            val book = Book.from(BookCreateDto(
                "책이름-${Random.nextLong(10, 99)}",
                "작가-${Random.nextLong(10, 99)}",
                "출발판-${Random.nextLong(10, 99)}",
                Random.nextLong(10000, 99999)
            ))
            entityManager.persist(book)
            val detail = BookDetail("책상세-${Random.nextLong(10000, 99999)}")
            detail.setBy(book)
            entityManager.persist(detail)
        }

        entityManager.flush()
        entityManager.clear()

        // when
        val books = bookRepository.findAll()

        // then
        books.size shouldBe 5
    }
}
```

## 3-1. 연관관계 주인을 BookDetail 로 설정하고, 테스트 코드 실행 결과(mappedBy 설정)
```shell
// book 만 전체 조회한다.
val books = bookRepository.findAll()
```

```shell
Hibernate: 
    select
        book0_.id as id1_0_,
        book0_.created_at as created_2_0_,
        book0_.modified_at as modified3_0_,
        book0_.author as author4_0_,
        book0_.isbn as isbn5_0_,
        book0_.library_id as library_8_0_,
        book0_.name as name6_0_,
        book0_.publisher as publishe7_0_ 
    from
        book book0_
Hibernate: 
    select
        bookdetail0_.book_id as book_id4_1_0_,
        bookdetail0_.created_at as created_1_1_0_,
        bookdetail0_.modified_at as modified2_1_0_,
        bookdetail0_.content as content3_1_0_ 
    from
        book_detail bookdetail0_ 
    where
        bookdetail0_.book_id=?
Hibernate: 
    select
        bookdetail0_.book_id as book_id4_1_0_,
        bookdetail0_.created_at as created_1_1_0_,
        bookdetail0_.modified_at as modified2_1_0_,
        bookdetail0_.content as content3_1_0_ 
    from
        book_detail bookdetail0_ 
    where
        bookdetail0_.book_id=?
Hibernate: 
    select
        bookdetail0_.book_id as book_id4_1_0_,
        bookdetail0_.created_at as created_1_1_0_,
        bookdetail0_.modified_at as modified2_1_0_,
        bookdetail0_.content as content3_1_0_ 
    from
        book_detail bookdetail0_ 
    where
        bookdetail0_.book_id=?

// book 과 bookDetail 을 넣은 횟수만큼 N+1 쿼리가 날라감
```

## 3-2. 연관관계 주인을 별도로 설정하지 않고 실행 (mappedBy 미설정)
```shell
// book 만 전체 조회한다.
val books = bookRepository.findAll()
```

```shell
// book 만 한번 조회
Hibernate: 
    select
        book0_.id as id1_0_,
        book0_.created_at as created_2_0_,
        book0_.modified_at as modified3_0_,
        book0_.author as author4_0_,
        book0_.detail_book_id as detail_b8_0_,
        book0_.isbn as isbn5_0_,
        book0_.library_id as library_9_0_,
        book0_.name as name6_0_,
        book0_.publisher as publishe7_0_ 
    from
        book book0_
```

## 4. 차이
* mappedBy 를 설정여부에 따라서, book 에 컬럼 `book.detail_book_id 존재여부` 가 결정된다.
* book 에서 lazy loading 으로 detail 을 들고오기 위해선 사전에 detail_book_id 가 미존재한다면, 애초에 book 을 들고올때 BookDetail 까지 같이 들고온다.
* detail_book_id 가 존재한다면, book 을 통해서 이후 BookDetail 까지 들고오는게 가능해지기 때문에 mappedBy 가 없는 건은 lazy loading 이 되고있다.
* 결국 해당 컬럼 존재여부에 따라서 프록시 설정을 통해서 지연로딩의 동작여부가 결정된다고 볼 수 있다.

## 5. 해결
1. fetchJoin 을 한다. : book 조회 시, bookDetail 도 같이 조회할 수 있도록 한다.
2. 구조를 다시 변경한다. : 처리비용이 많이 들 것 같다.. 요건 별도로 확인이 필요하다. 어떻게 하면 좀 더 효율적으로 할 수 있는가?

## 5.1 해결 : @OneToOne 을 @OneToMany, @ManyToOne 으로 구조를 변경한다. (2023년 12월 24일 갱신)
* 위 코드에서 springboot 3.x 및 querydsl 버전을 5.5.0 으로 변경한 뒤에 다시 테스트했는데, fetchJoin() 형태로 같이 들고오고 있었다.
* 그래서 동작이 예전에 작성했던 내용과는 달라짐을 확인하였다. -> 결국 구조를 변경하는 걸로 해결방안을 아래처럼 잡으면 lazy 하게 처리가 가능했다.

### 5.1.1 연관관계
```
Book : BookDetail = 1 : N 관계로 설정
```

### 5.1.2 관련코드
```kotlin
// 안봐도 상관없는 코드는 생략했다.
@Entity
@Table(name = "book_detail")
class BookDetail: BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    // 연관관계 주인, 외래키를 들고있는 테이블을 선정
    @ManyToOne(targetEntity = Book::class)
    var book: Book? = null
        protected set
}

@Entity
@Table(name = "book")
class Book: BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    // 연관관계 자식, FetchType.LAZY 에 주목
    @OneToMany(
        mappedBy = "book",
        fetch = FetchType.LAZY,
        targetEntity = BookDetail::class,
    )
    var detail: MutableList<BookDetail> = mutableListOf()
}
```

### 결과쿼리
fetchType.LAZY 이기 때문에 쿼리는 findAll() 수행 시. 단 한번만 쿼리가 나가는 걸 알 수 있었다.   
만약 EAGER 였으면 N + 1 현상이 발생한다.
```
select
    b1_0.id,
    b1_0.author,
    b1_0.created_at,
    b1_0.isbn,
    b1_0.library_id,
    b1_0.modified_at,
    b1_0.name,
    b1_0.publisher 
from
    book b1_0

// findAll() 수행한 결과에 한개의 book detail 만 조회하면 추가 쿼리가 아래처럼 1번만 나간다.
select
    d1_0.book_id,
    d1_0.id,
    d1_0.content,
    d1_0.created_at,
    d1_0.modified_at 
from
    book_detail d1_0 
where
    d1_0.book_id=?
```
