## Network
질문
  
## 1
* 프로토콜인란 무엇인가
* 네트워크 코어는 무엇인가
* 라우터는 어떤 기능을 하는가
* 라우터는 데이터를 어떻게 전달하는가
* Circuit Switching 과 Packet Switching 개념
* Circuit Switching 과 Packet Switching 차이
* Packet Switching 시 Loss 와 Delay 개념 및 발생 이유
* Delay 종류 네가지
* Packet Loss 가 일어난 경우 어떤 동작이 발생하는가
  
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
  
## 3
* SMTP 프로토콜 개념
* Mail Queue 개념
* HTTP Protocol & SMTP Protocol 송수신 모습 (__push__ & __pull__)
* Socket 이란
  
## 4
* DNS 란
* DNS 구성요소
* DNS 동작원리
* DNS 데이터

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

## 9
* Network Layer 데이터단위
* 라우터의 주요 두가지 기능
  * 포워딩 & 라우팅
* IP Protocol
* subnets 

## 10
* 서브넷이란
* NAT 이란
* NAT가 가진 문제점 두 가지 설명
* NAT 환경에서 서버 사용의 문제점

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
