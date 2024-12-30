# 레디스가 빠른이유.
2024년, 팀에서 레디스를 사용하고 있다. <BR>
레디스가 빠르다는 사실은 안다. 하지만 레디스가 빠르다는 사실을 어떻게 해야 잘 설명할 수 있을까... 

<BR>

## (1) 인메모리 데이터베이스
- 디스크를 사용하지 않는다. RAM 을 사용
- 랜덤 Disk IO 보다 더 빠른 쓰기/읽기 속도

<BR>

## (2) IO 멀티플렉싱
> 하나의 스레드가 여러 stream(fd: file descriptor)을 동시에 감시할 수 있게하는 기술. <BR>
> 이 기술을 통해서 여러 IO 작업을 병렬로 처리하는 것처럼 보이게 가능하다. 결국 여러 IO 작업이 발생할 때마다 blocking 없이 효율적으로 처리하고자 한다.

- 멀티플렉싱 기법을 통하여 단일 스레드로도 높은 성능을 유지할 수 있으며, 동시 클라이언트의 요청을 효율적으로 처리한다.

> `fd` 는 UNIX 계열 운영체제에서 열린파일(소켓, 파이프, 디바이스 등) 각각을 가리키는 추상화된 숫자를 의미. <BR>

### 주요 멀티플렉싱 기법 (select -> poll -> epoll, kqueue 순으로 점차 개선)
- **select()**
  - 단일 스레드가 여러 fd 를 감시할 수 있게한다.
  - 매 호출하다 fd_set 을 설정하여 select() 를 호출하면 커널이 어떤 fd 가 준비되었는지 체크한다.
    - fd_setsize 는 기본적으로 1024 제한이 있다. 즉 한번에 검사할 수 있는 fd 의 갯수가 제한적이며 대규모 연결처리는 비효율적
  - `준비` 는 읽기/쓰기/예외/에러 등의 이벤트가 발생한 fd 를 가리킨다. 
- **poll()**
  - 폴링배열(pollfd[])에 fd 와 이벤트종류를 담고, poll() 을 호출하면 각 fd 이벤트 발생여부를 커널이 업데이트
    - 배열기반 인터페이스를 사용, 구조체 배열을 사용.
    - 매번 전체배열을 커널에 전달해야하기 때문에 대규모 연결처리는 비효율적. O(n) 에 가까운 처리비용 발생.
  - select 와 유사하지만, fd 수에 제한이 없고 유연하게 동작
- **epoll()**
  - 리눅스 전용 
  - epoll 인스턴스를 커널공간에 하나 생성.
  - epoll 은 두가지 호출, epoll_create, epoll_ctl, epoll_wait 를 사용하여 fd 를 관리하고 이벤트를 감시한다.
    - epoll_create : epoll 을 사용하기 위해 사용하는 함수명
    - epoll_ctl(epfd, EPOLL_CTL_ADD, fd, &event) : 감시할 fd 와 이벤트타입(읽기/쓰기) 등을 추가하는 함수명
    - epoll_wait(epfd, events, maxevents, timeoout) : epoll 인스턴스에서 이벤트가 발생한, 즉 준비된 fd 를 한번에 모아서 가져오기 위해 사용하는 함수명
  - 많은 수의 fd 를 처리할 때 성능이 뛰어나다.
- **kqueue()**
  - BSD, macOS 계통은 kqueue,kevent 를 사용. 
  - epoll 과 유사하게 높은 성능을 제공한다.
  - kevent 구조체를 사용하여 이벤트를 감시하고 관리하다.


### IO 멀티플렉싱 동작원리
- 1.이벤트 등록
    - 감시할 파일 fd(ex. 소켓) 를 IO 멀티플렉싱 시스템 호출에 등록
    - 해당 단계에서 관심있는 이벤트(읽기/쓰기 등) 지정
- 2.이벤트 대기
    - IO 멀티플렉싱 시스템 호출은 하나 이상의 파일 디스크립터에서 이벤트가 발생할 때까지 블록킹. 이 블록킹은 CPU 자원을 효율적으로 사용 
- 3.이벤트 처리
    - 이벤트가 발생하면 해당 fd 를 처리한다. 블로킹없이 여러 IO 작업을 처리

<BR>

## (3) 단일스레드 & 이벤트루프 메커니즘
- 여러 스레드가 동시 자원에 접근할 때, 발생하는 경합조건 및 메커니즘을 피할 수 있다.
- 다중 스레드 환경의 컨텍스트 스위칭으로 인한 오버헤드가 발생하지 않는다. 따라서 CPU 자원을 더 효율적으로 사용할 수 있다.
- 이벤트루프를 사용하여 비동기 IO 를 관리한다.
- 이벤트가 발생할때까지 기다리면서 발생한 이벤트를 처리한다. -> IO 멀티플렉싱 동작을 `지속적으로 반복` 한다.
- [레디스 오픈소스 이벤트루프 사이즈 설정 후, 실행](https://github.com/redis/redis/blob/690ef36330b192ed29d8c24d08a24d6d5cfd40a4/deps/hiredis/examples/example-ae.c#L43)
- [이벤트루프 코드](https://github.com/redis/redis/blob/690ef36330b192ed29d8c24d08a24d6d5cfd40a4/src/ae.c#L342)

<BR>

## (4) RESP 프로토콜 (REdis Serialization Protocol)
- 텍스트 기반 프로토콜. 명령어와 데이터를 직관적으로 표현한다.
- 프로토콜이 단순하여 오버헤드가 낮다.

<BR>

## (5) 효율적인 데이터 구조
- 고성능 데이터 구조를 지원. 
    - ex) String, List, Set, Sorted Set, Hash, ...

<BR>

# 참고
- chatGPT
- https://youtu.be/5TRFpFBccQM?si=c9ZAggm8Qg96322r
- [IO Multiplexing (IO 멀티플렉싱) 기본 개념부터 심화까지 -1부-](https://blog.naver.com/n_cloudplatform/222189669084)
- [IO Multiplexing (IO 멀티플렉싱) 기본 개념부터 심화까지 -2부-](https://blog.naver.com/n_cloudplatform/222255261317)
- [비동기 서버에서 이벤트 루프를 블록하면 안 되는 이유 1부 - 멀티플렉싱 기반의 다중 접속 서버로 가기까지](https://engineering.linecorp.com/ko/blog/do-not-block-the-event-loop-part1#mcetoc_1gdcaies0s)
