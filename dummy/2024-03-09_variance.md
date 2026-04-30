## 불공변성(invariance), 공변성(covariance), 반공변성(contvariance)
- 불공변성은 어떤 타입도 모두 허용한다.
- 공변성은 자신과 부모타입만 허용한다. produce, 생산의 개념, read-only
  - out 키워드 사용
- 반공변성은 자신과 서브타입만 허용한다. consume, 소비의 개념, write-only
  - in 키워드 사용

> 위 개념을 적용하면 코틀린 코드에 안정성과 유연성이 생김?
> 예시를 보자

## 공변성 예시
```kotlin
open class Animal(val name: String)

class Dog(name: String) : Animal(name)
class Cat(name: String) : Animal(name)

class AnimalGroup<out T : Animal>(
    private val animals: MutableList<T>
) {

    fun getAnimal(index: Int): T {
        return animals[index]
    }
  
    /**
     * 아래코드는 컴파일에러가 발생된다.
     * 공변성을 통한 out keyword 의 사용은 결국 초기에 객체를 생성하고
     * 오직 읽기전용으로만 데이터를 동작시킨다. 
     */
    fun addAnimal(animal: T) {
        animals.add(animal)
    }
}

fun main() {
    val dogs = mutableListOf(Dog("Buddy"), Dog("Max"))
    val cats = mutableListOf(Cat("Whiskers"), Cat("Mittens"))

    val dogList = AnimalGroup(dogs)
    val catList = AnimalGroup(cats)

    val dog: Dog = dogList.getAnimal(0)
    println("Dog: ${dog.name}")

    val cat: Cat = catList.getAnimal(0)
    println("Cat: ${cat.name}")

    // 공변성 사용시 새로운 데이터를 추가하는 것이 불가하다 : 컴파일 에러가 발생한다.
    dogList.addAnimal(Dog("Fido"))
}
```

## 반공변성 예시
```kotlin
open class Animal(val name: String)

class Dog(name: String) : Animal(name)
class Cat(name: String) : Animal(name)

/**
 * Comparator 는 자바 단에서 <? super U> 로 처리되고 있기 때문에
 * 반공변성 성격을 띄고있다.
 */
interface InverseComparator<T> : Comparator<T> {
    override fun compare(a: T, b: T): Int
}

fun main() {
    
  // 다양한 타입의 객체와 비교할 수 있다.  
  val togetherComparator = object : InverseComparator<Animal> {
    override fun compare(a: Animal, b: Animal): Int {
      return a.name.compareTo(b.name)
    }
  }
  
  val dogs = listOf(Dog("Buddy"), Dog("Max"), Dog("Apple"))
  val cats = listOf(Cat("Whiskers"), Cat("Mittens"), Cat("Coco"))
  val together = dogs + cats
  
  val sortedDogs = dogs.sortedWith(togetherComparator)
  val sortedCats = cats.sortedWith(togetherComparator)
  val sortedTogethers = together.sortedWith(togetherComparator)
  
  println("Sorted dogs: ${sortedDogs.map { it.name }}")
  println("Sorted cats: ${sortedCats.map { it.name }}")
  println("Sorted togethers: ${sortedTogethers.map { it.name }}")
}
```