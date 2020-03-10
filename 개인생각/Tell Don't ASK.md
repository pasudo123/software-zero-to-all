# Tell Don't ASK
### 데이터를 달라고 하지 않는다. => 물어보지 않는다. 그냥 시킨다. 그냥 기능을 수행한다.
```java
// (1)번을 (2)번처럼 행하기.
(1) if(member.getExpireDate().getTime() < System.currentTimeMillus) { /// }
(2) if(member.isExpired()) { /// }
```

* 객체와 객체가 협력하는 경우, 다른 객체에게 정보를 요구하지 않고 행위를 시킨다. (=> 정보은닉의 중요성)
* 정보를 입수했을 때, __해당 정보를 한정적인 범위 내에서__ 처리하도록 한다.
  * 외부에서 해당 정보를 가지고 동작을 수행할 경우에, __정보를 가지고 있는 쪽에 동작을 요청__ 하도록 한다.
* `getter()` 를 통해서 데이터를 요청하지 말자.

### (1) 데이터를 생성하는 단계
* 데이터를 최초로 수신한 객체는 일단 다른 곳으로 데이터를 전파시킬 수 없어야 한다.
* `getter()` 를 통해서 다른 객체의 매개변수로 데이터를 전송해서는 안된다.

### (2) 데이터 객체 생성 단계
* (2-1) 데이터 처리를 전담하는 객체가 있다면, 최초로 데이터를 생성한 객체는 처리 객체로 데이터를 넘겨주면 된다.
```java
데이터처리객체.receive(생성데이터)
```
* (2-2) 데이터 처리를 전담하는 객체가 미존재, 여러 객체들이 데이터에 대한 의존성을 가지고 있다.
  * 데이터 생성 객체를 다른 객체로 전달하여 준다.
```java
Data dataObject = new Data(inputData);

someObject.receive(dataObject);
```

### (3) 데이터 처리 단계
* (3-1) (링크참조)[https://effectiveprogramming.tistory.com/entry/Tell-dont-ask]
* (3-2) 데이터 생성 객체를 받은 각 객체들은 데이터를 모르기 때문에 직접 자신의 행위를 변경할 수 없다. 따라서 데이터 객체에게 자신의 상태를 변경해달라고 요청하여야 한다.
```java
// this 는 someObject 이다.
dataObject.doubleDispatch(this);

// 데이터 객체, dataObject 쪽
public void doubleDispatch(Object someObject){
  someObject.doSomething();
}

/**
 * 위의 방식을 켄트벡의 구현패턴에서 더블디스패치라고 부른다.
 * 데이터를 알고있는 쪽에서 데이터에 종속적으로 동작하는 객체를 넘겨받아서 자기가 알고있는 데이터를 기반으로 넘겨받은 객체의 행위를 호출하는 것.
 * 결국 데이터를 넘겨주지 않고도 넘어갔을 때 일어나야하는 행위를 호출할 수 있다.
 */
```

## LoD : Law Of Demeter : 최소지식의 원칙
## 정보의 전달을 금지하는 원칙.

# 관련링크
* [TDA 원칙](https://effectiveprogramming.tistory.com/entry/Tell-dont-ask)
* [CQRS 란 무엇인가?](https://justhackem.wordpress.com/2016/09/17/what-is-cqrs/)
* [나만 모르고 있던 CQRS](https://www.popit.kr/cqrs-eventsourcing/)
* [Law of Demeter](https://dzone.com/articles/the-genius-of-the-law-of-demeter)
