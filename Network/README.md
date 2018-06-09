## Network
__참고강의__
* [ 2015년 2학기 컴퓨터 네트워크 - 이석복 교수(한양대학교) ](http://www.kocw.net/home/search/kemView.do?kemId=1169634&ar=relateCourse)
* [ 2016년 2학기 컴퓨터 네트워크 - 이석복 교수(한양대학교) ](http://www.kocw.net/home/search/kemView.do?kemId=1223614&ar=relateCourse)

__작성방식__
* 강의를 보면서 블로그에 게시글 작성 
* 작성된 게시글을 통해서 질문글 추출
* 추출된 질문글을 다시 블로그에 답변글로 게시

## 1
* 프로토콜이란 무엇인가
* 네트워크 코어는 무엇인가
* 라우터는 어떤 기능을 하는가
* 라우터는 데이터를 어떻게 전달하는가
* Circuit Switching 과 Packet Switching 개념
* Circuit Switching 과 Packet Switching 차이
* Packet Switching 시 Loss 와 Delay 개념 및 발생 이유
* Delay 종류 네가지
* Packet Loss 가 일어난 경우 어떤 동작이 발생하는가
#### [ 1번 게시글 보기 ](http://pasudo123.tistory.com/118?category=746034)
#### [ 1번 질문에 대한 답변 확인 ](http://pasudo123.tistory.com/187?category=751090)

## 2
* 네트워크란 무엇인가
* IPC(Inter Process Communication) 이란
* 웹서버 기본 포트번호는 얼마인가
* TCP의 특징 (간략)
* Http Protocol 특징 (간략)
* Persistent HTTP 개념
* Non-Persistent HTTP 개념
* Web Caches (Proxy Server) 개념
* Web Caches (Proxy Server) 의 단점
* Conditional GET 개념
#### [ 2번 게시글 보기 ](http://pasudo123.tistory.com/119?category=746034)
  
## 3
* SMTP 프로토콜 개념
* Mail Queue 개념
* HTTP Protocol & SMTP Protocol 송수신 모습 (__push__ & __pull__)
* Socket 이란
#### [ 3번 게시글 보기 ](http://pasudo123.tistory.com/121?category=746034)
  
## 4
* DNS 란
* DNS 구성요소
* DNS 동작원리
* DNS 데이터
#### [ 4번 게시글 보기 ](http://pasudo123.tistory.com/124?category=746034)

## 5
* Transport Layer 란
* Transport Layer 에서 만들어지는 데이터의 단위
* TCP 특징 (간략히)
* Reliable / In-Order Delivery 란
* Flow control & Congestion control 은 무엇인가
* 멀티플렉싱 & 디멀티플렉싱의 차이
* 포트번호는 Transport Layer 에서 어떻게 작용하는지
* Checksum 이란
* 패킷에 대한 에러가 있는 경우 이를 확인하기 위한 메커니즘
* Sequece Number 란
#### [ 5번 게시글 보기 ](http://pasudo123.tistory.com/125?category=746034)

## 6
* pack Error 가 생길 수 있는 네트워크에서 어느 메커니즘을 사용해야 하는 것인가
* Sequence number 개념 및 사용이유
* pipelined protocols 개념
* TCP 특징
* TCP & UDP 헤더필드가 중요한 이유
* TCP segment structure 설명
* window size 설명
* TCP sender 동작방실 설명
* 패킷 유실을 판단할 근거 두 가지 (재전송을 위한 근거 두가지)
#### [ 6번 게시글 보기 ](http://pasudo123.tistory.com/127?category=746034)

## 7
* 애플리케이션 레이어에서 전송하는 데이터 단위
* 트랜스포트 레이어에서 전송하는 데이터 단위
* 네트워크 레이어에서 전송하는 데이터 단위
* 애플리케이션에 있는 프로세스 계층들이 reliable 하게 통신할 수 있도록 해주는 프로토콜 ?
* TCP 에서 senderBuffer & receiveBuffer 설명
* TCP 에서 제공하는 세가지 주요 기능
* Nagle's Algorithm 이란
* Connection Management : 3-way handshaking 설명
* TCP Closing a Connection 설명
#### [ 7번 게시글 보기 ](http://pasudo123.tistory.com/131?category=746034)

## 8
* window size 역할
* Congestion Control 이란
* Congestion Control 로 인해 발생되는 두 가지 현상
* Congestion Control 은 선순환 or 악순환 ?
* 네트워크 상황을 판단하기 위한 방법
* additive increase & multiple decrease 란 무엇인가
* congestion window size (= window size) : 네트워크 상황에 따라서 다이내믹하게 변화
* TCP sending rate 란
* TCP Tahoe & TCP Reno 개념 및 차이점
* 3-Duplicate ACKs 란
* TCP Fairness 란
#### [ 8번 게시글 보기 ](http://pasudo123.tistory.com/135?category=746034)

## 9
* Network Layer 데이터단위
* 라우터의 주요 두가지 기능
  * 포워딩 & 라우팅
* IP Protocol
* subnets 
#### [ 9번 게시글 보기 ](http://pasudo123.tistory.com/138?category=746034)

## 10
* 서브넷이란
* NAT 이란
* NAT가 가진 문제점 두 가지 설명
* NAT 환경에서 서버 사용의 문제점
#### [ 10번 게시글 보기 ](http://pasudo123.tistory.com/141?category=746034)

## 11
* NAT 란
* NAT 의 문제점은 무엇이고 설명
* NAT 환경에서 서버 사용을 지양해야 하는지, 그리고 이에 따른 해결방법
* IPv4 가 가진 문제점
* cmd 창에서 ipconfig -all 을 치고 난 뒤 나타나는 것들 설명하기
  * IP
  * Subnet mask
  * 기본 게이트웨이(Router)
  * DNS
  * DHCP
* DHCP 란
* DHCP 동작방식
* IP 헤더 필드에서 16Bit Identifier 와 flag & offset 
#### [ 11번 게시글 보기 ](http://pasudo123.tistory.com/142?category=746034)

## 12
* ICMP 란 무엇인가
* 라우팅 알고리즘 종류 (두가지)
* link state 알고리즘이란
* dijkstra 알고리즘이란
* link state 알고리즘의 브로드캐스트 범위
* distance vector 알고리즘 식과 해당 식 내용 설명
#### [ 12번 게시글 보기 ](http://pasudo123.tistory.com/144?category=746034)

## 13
* distance vector 알고리즘 계산
* distance vector 의 두 개의 노드의 값이 기존의 값보다 __작게__ 변경된 경우
* distance vector 의 두 개의 노드의 값이 기존의 값보다 __크게__ 변경된 경우
* distance vector 에서 count-infinite 란 무엇인가
* link state 알고리즘의 브로드캐스트 범위
* distance vector 알고리즘은 네트워크의 어느 부분에서
#### [ 13번 게시글 보기 ](http://pasudo123.tistory.com/146?category=746034)

## 14
* count infinite 현상이란 무엇인가
* poison reverse 기법이란 무엇인가
* AS(Autonomous System) 이란 무엇인가
* AS 간의 Provider & Customer 의 관계란 무엇인가
* AS 간의 Peering RelationShip 은 무엇인가
* BGP 란 
#### [ 14번 게시글 보기 ](http://pasudo123.tistory.com/149?category=746034)

## 15
* 데이터 링크 레이어의 데이터 단위는 무엇인가
* 네트워크 레이어에서 패킷이 출발지에서 목적지까지 도달하는 경우 어디로 처음 보내는가
* 기본 게이트웨이(= 게이트웨이 라우터) 에서 프레임 혼선 문제, collision 설명하기
* 홉(hop)이란 무엇인가
* 링크 레이어는 어디에 구현되어 있는가
* Medium Access Control 은 무엇인가
* Mac Protocol 의 세가지 방식
  * Channel Partitioning
  * Random Access
  * Taking Turns
* CSMA/CD 란 무엇인가
* binary (exponential) backoff 란 무엇인가
* backoff 와 delay 간의 상관성
#### [ 15번 게시글 보기 ](http://pasudo123.tistory.com/154?category=746034)

## 16
* Ethernet 이란
* Collision Detect 는 어디서 일어나야 하는 것인가
* Minimum Frame Size 64Byte 란 무엇을 의미하는가
* 데이터 링크 레이어의 데이터 전송 단위인 프레임에서 Source Address & Dest Address 는 무엇인가
* MAC Address 는 48bit로 이루어져 있는데 이는 무엇을 의미하는가? (24bit + 24bit)
* ARP 란
* ARP Table 이란
* A 라는 지점의 Source 에서 Google 서버로 접속하기 위해 어떤 과정이 존재하는가? (시나리오 설명)
* 네트워크 코어의 라우터는 어느 테이블을 참조하는가
* 프레임 전송원리에 대해서 설명
#### [ 16번 게시글 보기 ](http://pasudo123.tistory.com/157?category=746034)

## 17
* switch란 무엇인가
* switch table 이란 무엇인가
* flooding 이란 
* 무선 공유기와 스위치의 차이정믄 무엇인가
* 공유기에 공유기를 꽂는다면 어떻게 되는가
* 가상의 IP 주소가 더 필요한 경우는 어떻게 행동으로 옮겨야 하는가
#### [ 17번 게시글 보기 ](http://pasudo123.tistory.com/158?category=746034)

## 18
* wifi 개략적인 설명
* passive scanning 이란
* CSMA/CA (Collision Avoidance) 설명
* CSMA/CA 에서 ACK 는 무슨 역할을 수행하는가
* RTS-CTS 란 무엇인가
* wifi 에서 사용하는 프레임 설명
#### [ 18번 게시글 보기 ](http://pasudo123.tistory.com/159?category=746034)

## 19
* 비콘 프레임은 무엇인가
* CSMA/CA + RTS-CTS 는 무엇인가
* wifi 에서 사용하는 프레임 필드의 address1, address2, address3 설명하기
* 일상생활에서 커피숍을 방문하였을 경우 wifi 연결을 한다면 어떻게 되는지 말해보아라
#### [ 19번 게시글 보기 ](http://pasudo123.tistory.com/163?category=746034)
