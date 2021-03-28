# practice-network
실무에서 만나는 네트워크 지식들

## ACL (Access Control Block)
* 목적
  * 트래픽을 필터링한다.
    * 내부에서 외부로 접근, 외부에서 내부로 접근
  * 방화벽을 구성한다.
    * 외부에서 내부로 접근
  * IP 주소 및 서브넷 정의
* ACL 설정 시 파악요소
  * 출발지/목적지
  * 서비스유형 (TCP, UDP 인지, 포트번호 확인)
  * 허용(permit)/차단(deny)
  * in/out : 라우터 인터페이스에 적용할 때, 이게 인바운드인지 혹은 아웃바운드인지 확인이 필요하다. (인바운드 아웃바운드 여부는 라우터 기준으로 한다.)

```
                        (Internet)
                            |
A Server ----- SW1 ----- Router1 ----- SW2 ----- B Server
```
* 종류
  * standard ACL(1 ~ 99, 1300 ~ 1999)
    * L3헤더 (IP 헤더) source address 를 조건으로 패킷을 분류하고 정책을 정의 (출발지가 어디인지 결정)
    * 검사 결과에 따라 전체 protocol suite 에 대한 packet 출력을 허용하거나 거부
  * extended ACL(100 ~ 199, 2000 ~ 2699)
    * source address, destination address, protocol, TTL 등의 정보과 L4헤더의 source port, destination port, TCP flag 등의 정보를 기반으로 패킷을 분류하고 정책을 정희
