# System Structure & Program Execution 01

<img src="https://github.com/pasudo123/SoftwareZeroToALL/blob/master/Image/2020-09-28_computer_structure_detail2.PNG" />

## 컴퓨터 시스템의 구조

* 메모리는 CPU 의 작업공간이다.
  * CPU 에서는 매 클럭 사이클마다 기계어를 하나씩 읽어서 실행한다.
* IO Device Input/Output device 로 나뉘어진다.
  * 각각의 IO Device 는 작은 CPU 가 존재하는데, 이를 Device Controller 라고 칭한다.
  * 각각의 IO Device 는 작업공간이 필요한데, 이를 Local Buffer 라고 칭한다.
* CPU 내에는 메모리보다 더 빠르면서 정보를 저장할 수 있는 공간, register 가 존재한다.
* CPU 가 현재 실행하는게 사용자 프로그램인지 운영체제인지 확인하는 mode bit 이 존재한다.
* CPU 에는 Interrupted Line 이 존재한다. CPU 는 항상 메모리에 있는 instruction 만 실행한다. Instruction 하나만 실행되면, 그 다음 실행될 기계어의 Instruction 주소값이 하나 증가되면서 다른 작업이 실행되고 있다. 이 때, 중간에 IO device 의 입력이라던가 디스크의 다른 Instruction 을 실행하게 되는 경우가 있는데 그런 변수에 대해 대처하기 위해서 Interrupted Line 이 존재한다. (모든 IO 작업은 `OS 가 수행` 한다.)
* 만약 메모리에 올라간 프로그램이 `무한루프` 에 빠지게 된다면?
  * 프로그램이 종료되지 않고 다른 프로그램으로 넘어가지 못한다.
  * 따라서 컴퓨터 안에는 Timer 라는 하드웨어를 두고 있는데, 일정한 프로그램이 CPU 를 독점하는 것을 막는다.
  * 따라서 운영체제가 CPU 를 얻게되었을 때, ??ms 를 Timer 에 세팅해서 특정 프로그램이 CPU 를 쓸 수 있도록 한다. ??ms 가 다 되면 이후에 이제 해당 프로그램이 CPU 점유하는 것을 끝낸다.

<BR>

## Mode bit
사용자 프로그램의 잘못된 수행으로 다른 프로그램 및 운영체제에 피해가 가지 않도록 하기위한 보호장치가 필요. Mode bit 은 두 가지 Operation 을 제공한다. 

* `0` 이면 운영체제가 CPU 에서 실행중인 상태
  * Memory 접근 및 IO 디바이스 접근 가능
* `1` 이면 사용자 프로그램이 CPU 에서 실행중인 상태
  * IO 디바이스 접근 및 운영체제 혹은 다른 프로그램 접근에 대해 제한

<BR>

## Timer
특정프로그램이 CPU 를 독점 하는 것을 막기 위함
* 정해진 시간이 흐른 뒤, 운영체제에게 제어권을 넘어가도록 인터럽트를 발생시킴
* 타이머는 매 클럭 틱마다 1씩 감소
* 타이머 값이 0이 되면, 타이머 인터럽트 발생
* CPU 를 특정 프로그램이 독점하는 것으로부터 보호
* 타이머는 time sharing 을 구현하기 위해 널리 이용됨
* 타이머는 현재 시간을 계산하기 위해서 사용하기도 함

<BR>

## I/O device Controller
* 하드웨어
* I/O 장치 유형을 관리하는 일종의 작은 CPU
* 제어정보를 위해 control register, status register 를 가짐
  * CPU 가 device controller 에 일을 시킬 때, 지시하기 위함
* local buffer 를 가짐 (일종의 data register)
* I/O 는 실제 device 와 local buffer 사이에서 일어남
* device controller 는 i/o 가 끝났을 경우에 interrupt로 CPU 에 그 사실을 알림

## device driver
* OS 코드 중 각 장치별 접근할 수 있는 인터페이스가 있는데, 이에 따른 처리 루틴이 정의된 소프트웨어. CPU 가 접근할 수 있다.

<BR>

## Direct Memory Access (DMA Controller)
* DMA Controller 를 두게 되면 DMA Controller 도 CPU 처럼 메모리에 접근할 수 있다.
* i/o 장치가 자주 인터럽트를 발생시키기 때문에, local buffer 의 내용을 메모리로 복사시키는 역할을 수행한다. 따라서 CPU 는 메모리에 집중해서 작업을 수행할 수 있다.

<BR>

## 사용자 프로그램은 I/O 를 어떻게 하는지
* 사용자 프로그램이 운영체제의 커널, 함수를 호출한다. (= system call )
* `trap` 을 사용하여 인터럽트 벡터의 특정 위치로 이동
  * trap 이란 소프트웨어 인터럽트를 의미

<BR>

## 인터럽트
인터럽트 당한 시점의 register 와 program counter 를 save 한 후에 CPU의 제어를 인터럽트 처리 루틴에 넘긴다.
* 인터럽트
  * 하드웨어 인터럽트 (= interrupt)
    * 하드웨어가 발생시킨 인터럽트
  * 소프트웨어 인터럽트 (= trap)
    * Exception : 프로그램이 오류를 범한 경우
    * System Call : 프로그램이 커널함수를 호출하는 경우
* 인터럽트 벡터
  * 해당 인터럽트의 처리루틴 주소를 가지고 있음
* 인터럽트 처리루틴
  * 해당 인터럽트를 처리하는 커널 함수

> I/O 를 하기 위해서 사용자 프로그램이 OS 에게 System call 을 호출한다. 그리고 OS 는 Device Controller 에게 일을 수행시키도록 명령한다. 수행이 다 된 이후에 Device Cotntroller 가 CPU 에게 인터럽트를 건다.
