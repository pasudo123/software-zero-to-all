# kotlin 정렬

## sortedWith
- 비교 기준에 따라 정렬할 때 사용

```kotlin
fun `sortedWith`() {
    val persons = listOf(
        Person("a_park", 30),
        Person("b_song", 20),
        Person("b_song", 25),
        Person("c_lee", 10),
    )
    
    // (1) compareBy 를 이용한 Comparator 생성. 단순 정렬기준으로 사용
    val sortedWithName = persons.sortedWith(compareBy { it.name })
    // [Person(name=a_park, age=30), Person(name=b_song, age=20), Person(name=b_song, age=25), Person(name=c_lee, age=10)]
    println(sortedWithName)

    val sortedWithAge = persons.sortedWith(compareBy { it.age })
    // [Person(name=c_lee, age=10), Person(name=b_song, age=20), Person(name=b_song, age=25), Person(name=a_park, age=30)]
    println(sortedWithAge)

    // (2) sortedWith, compareBy, thenBy 를 이용 다중 정렬
    val sortedWithMultiple = persons.sortedWith(compareBy<Person> { it.name }.thenByDescending { it.age })
    // [Person(name=a_park, age=30), Person(name=b_song, age=25), Person(name=b_song, age=20), Person(name=c_lee, age=10)]
    println(sortedWithMultiple)

    // (3) sortedWith {} 를 이용한 커스텀 정렬로직 구현. 직접 비교함수를 정의한다
    val sortedCustom = persons.sortedWith { prev, next -> next.age - prev.age }
    //[Person(name=a_park, age=30), Person(name=b_song, age=25), Person(name=b_song, age=20), Person(name=c_lee, age=10)]
    println(sortedCustom)
}
```

## 블로그에 작성한 글.
- https://pasudo123.tistory.com/498

