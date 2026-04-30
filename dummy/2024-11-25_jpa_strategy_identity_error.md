## spring jpa 에서 @GeneratedValue(strategy = GenerationType.IDENTITY) 에러 발생시
* RDB 는 MySQL 을 사용한다.
* 여기서 JpaRepository.save() 를 수행하면 실제 DB 까지 반영은 되지 않으나, ID 값은 부여가 된다.
  * AUTO_INCREMENT 특성으로 인하여 ID 값이 생성
  * DB 에 기본키 생성을 위임. persist() 시점에 DB 를 통해 id 값은 부여된다.

### 위 상황에서 에러가 발생하면? 코드를 본다.

#### Food Entity
```kotlin
@Entity
@Table(name = "food")
class Food(
    @Column(name = "name", length = 32, nullable = false)
    val name: String,
    @Column(name = "sub_name")
    val subName: String? = null
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}
```

#### FoodService, FoodRepository
```kotlin
@Service
class V2FoodService(
    private val foodRepository: FoodRepository
) {

    private val log = LoggerFactory.getLogger(javaClass)

    @Transactional
    fun create(dto: FoodDto): Long {
        val suffix = UUID.randomUUID().toString().substring(IntRange(0, 5))
        val food = Food(name = "${dto.name}:$suffix", subName = dto.subName)
        foodRepository.save(food)

        val foodId = food.id

        log.info("food.id=${foodId}")

        if (Random.nextBoolean()) {
            throw RuntimeException("error")
        }

        return foodId!!
    }
}
```
* foodRepository.save() 를 통해서 food.id 값을 확인할 수 있다. `순차적으로 증가되는 것을 확인`
  * 1 -> 2 -> 3 -> ... -> N
* 하지만 throw 발생 시에 DB 에는 저장되지 않으며 food.id 저장 처리는 롤백된다. 결국 사용할 수 없는 ID 값이다.
* 이후에 create() 메소드가 재요청 시 food.id 값은 이전 (food.id + 1) 값. 즉 AUTO_INCREMENT 다음 ID 값이 할당된다.