# Tell Don't ASK
### 데이터를 달라고 하지 않는다. => 기능을 실행한다.
```java
// (1)번을 (2)번처럼 행하기.
(1) if(member.getExpireDate().getTime() < System.currentTimeMillus) { /// }
(2) if(member.isExpired()) { /// }
```

## LoD : Law Of Demeter : 최소지식의 원칙

# 관련링크
* [CQRS 란 무엇인가?](https://justhackem.wordpress.com/2016/09/17/what-is-cqrs/)
* [나만 모르고 있던 CQRS](https://www.popit.kr/cqrs-eventsourcing/)
* [Law of Demeter](https://dzone.com/articles/the-genius-of-the-law-of-demeter)
