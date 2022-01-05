## kill command 👀
* 실행중인 프로세스에 `시그널` 를 보내기 위해 널리 사용되는 운영체제 명령어
* `시그널` 은 특정 이벤트가 발생했을 때, 신호를 보내서 알려주는 것이다.

__지원하는 시그널__
```shell
pasudo123@developer1997:/mnt/c/Windows/system32$ kill -l

 1) SIGHUP       2) SIGINT       3) SIGQUIT      4) SIGILL       5) SIGTRAP
 6) SIGABRT      7) SIGBUS       8) SIGFPE       9) SIGKILL     10) SIGUSR1
11) SIGSEGV     12) SIGUSR2     13) SIGPIPE     14) SIGALRM     15) SIGTERM
16) SIGSTKFLT   17) SIGCHLD     18) SIGCONT     19) SIGSTOP     20) SIGTSTP
21) SIGTTIN     22) SIGTTOU     23) SIGURG      24) SIGXCPU     25) SIGXFSZ
26) SIGVTALRM   27) SIGPROF     28) SIGWINCH    29) SIGIO       30) SIGPWR
31) SIGSYS      34) SIGRTMIN    35) SIGRTMIN+1  36) SIGRTMIN+2  37) SIGRTMIN+3
38) SIGRTMIN+4  39) SIGRTMIN+5  40) SIGRTMIN+6  41) SIGRTMIN+7  42) SIGRTMIN+8
43) SIGRTMIN+9  44) SIGRTMIN+10 45) SIGRTMIN+11 46) SIGRTMIN+12 47) SIGRTMIN+13
48) SIGRTMIN+14 49) SIGRTMIN+15 50) SIGRTMAX-14 51) SIGRTMAX-13 52) SIGRTMAX-12
53) SIGRTMAX-11 54) SIGRTMAX-10 55) SIGRTMAX-9  56) SIGRTMAX-8  57) SIGRTMAX-7
58) SIGRTMAX-6  59) SIGRTMAX-5  60) SIGRTMAX-4  61) SIGRTMAX-3  62) SIGRTMAX-2
63) SIGRTMAX-1  64) SIGRTMAX
```
* `SIGHUP`, `SIGINT`, `SIGKILL`, `SIGTERM` 을 살펴볼 예정
* SIGHUP (1)
    * HangUP 의 약어로 로그아웃 또는 터미널 접속이 끊겼을때, 보내는 시그널이다.
    * SIGHUP 시그널은 원래 모뎀 연결 끊김 등의 시리얼 라인이 끊어지면서 발생하는 시그널이다.
    * `kill -HUP {pid}` 를 하게되면 해당 pid 프로세스가 다시 시작된다. 데몬 프로세스의 설정을 마치고 설정 내용을 `재적용` 시킬 때 자주 사용된다.
* SIGINT (2)
    * 터미널 인터럽트 신호
    * 키보드로부터 오는 이터럽트 시그널로 실행을 중지 (Ctrl + c 입력시에 보내지는 시그널)
* SIGKILL (9)
    * 킬
    * 무조건 종료, 즉 프로세스를 강제로 종료시키는 시그널
* SIGTERM (15)
    * 종료신호
    * Terminate 의 약자로 가능한 정상종료 시키는 시그널로 `kill 명령의 디폴트 시그널`

__사용방법__
```shell
$ kill {signal} {pid}
$ kill -9 123
$ kill -15 123
$ kill 123      // 생략 시 SIGTERM 으로 시그널이 전송된다.
```

일반적으로 프로세스가 시그널을 받았을 때, 기본 처리 방법은 프로세스를 종료하는 것이 대부분이다. 이 때, `시그널을 수신한 프로세스의 반응은 아래와 같다.`
* 시그널에 대해 기본적인 방법으로 대응한다. (대부분의 시그널에 대해서 프로세스는 종료하게 된다.)
* 시그널을 무시한다. 단, SIGKILL 과 SIGSTOP 은 무시될 수 없다.
* 프로그래머가 지정한 핸들러를 호출한다.

## SIGTERM & SIGKILL 의 차이점은 무엇인가?
* `SIGTERM` 은 gracefully 하게 프로세스를 종료시킬 수 있다. 반면에 ``SIGKILL` 은 프로세스를 즉시 종료시킨다.
* `SIGTERM` 신호는 처리/무시/차단 할 수 있지만, `SIGKILL` 은 처리하거나 차단할 수 없다.
* `SIGTERM` 은 자식 프로세스를 죽이지 못하지만, `SIGKILL` 은 자식 프로세스를 죽일 수 있다.
* `SIGKILL` 을 사용하면 좀비 프로세스가 생성될 수 있다. `SIGKILL` 을 받은 프로세스는 자식 프로세스에게 `SIGKILL` 신호를 알릴 기회를 얻지 못하기 때문이다.

# <a id="reference"></a> reference 🚀
* [kill wiki](https://en.wikipedia.org/wiki/Kill_(command))