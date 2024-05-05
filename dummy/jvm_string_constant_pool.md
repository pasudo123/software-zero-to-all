## String Constant Pool
* JVM 메모리 영역 중에 하나.
* 스트링 리터럴로 처리된 문자열의 경우에는 새롭게 생성하지 않고 heap 영역에 보관해서 재사용할 수 있게 해준다.
* Java8 부터 PermGen 이 제거되고 String Constant Pool 은 힙영역의 일부가 되었다. 
  * 가비지 컬렉션 범위에 포함되어 메모리 관리가 더 효율적이게 되었다.
  * 참고로 Java8 PermGen 은 Metaspace 영역으로 대체되었다. ([과거 작성글](https://github.com/pasudo123/oom-zero/tree/main/oom-reference#jdk8-%EC%97%90%EC%84%9C-metaspace-%EC%98%81%EC%97%AD-%EC%B6%94%EA%B0%80))

## intern()
* String 객체를 String Constant Pool 에 저장하는 명령어. 이미 pool 에 동일한 문자열이 있다면 저장하지 않고 꺼내서 사용.
* 주기적으로 사용하면 JVM 은 pool 에서 매번 찾는 과정을 거쳐야 하기 때문에 성능저하를 야기할 수 있다.

## code
코틀린 언어로 작성.

```kotlin
fun main() {
  val hello1 = "hello"
  val hello2 = "hello"

  println("hello1 == hello2 >> ${hello1 == hello2}")
  println("hello1 === hello2 >> ${hello1 === hello2}")

  var hello3 = String(charArrayOf('h', 'e', 'l', 'l', 'o'))
  println("hello3 >> ${hello3}")
  println("hello1 == hello3 >> ${hello1 == hello3}")

  // intern 메소드 수행, 스트링 객체를 string constant pool 에 보관
  println("intern 메소드 수행 전, hello1 === hello3 >> ${hello1 === hello3}")
  hello3 = hello3.intern()
  println("intern 메소드 수행 후, hello1 === hello3 >> ${hello1 === hello3}")
}
```

결과값
```shell
hello1 == hello2 >> true
hello1 === hello2 >> true
hello3 >> hello
hello1 == hello3 >> true
intern 메소드 수행 전, hello1 === hello3 >> false
intern 메소드 수행 후, hello1 === hello3 >> true
```

  

