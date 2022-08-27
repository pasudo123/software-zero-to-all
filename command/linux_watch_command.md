## watch command 👀
* 주기적으로 실행한 프로그램의 결과를 전체화면에서 출력하는 명령어이다.

## mac에서 brew 로 설치
https://formulae.brew.sh/formula/watch

## watch 간단 사용법
```shell
// watch 명령어 확인
$ which watch
/usr/bin/watch

// 1초 간격으로 ps -ef 명령어 출력한다.
$ watch -n 1 ps -ef
```
* `-n {interval-time}` 옵션을 주어서 간격을 설정할 수 있다.
* `-d` 옵션을 주어서 변경되는 부분에 대해서 하이라이팅을 설정할 수 있다.

## /proc/uptime 을 이용
* /proc/uptime 이란 시스템이 얼마나 살아있는지 조회하는 명령어
* 두가지가 출력되는데 
    * `첫번째 값은` 부팅시간을 초단위로 표시한 `system uptime` 이다.
    * `두번째 값은` `idle time` 이다.

### watch + /proc/uptime
```
// proc/uptime 출력
$ cat /proc/uptime
1115.21 6681.18 

// proc/uptime 을 1초 주기로 출력해본다.
$ watch -n 1 cat /proc/uptime
Every 1.0s: cat /proc/uptime         developer1997: Tue Dec 15 16:07:37 2020
1367.19 8191.71
```

* `/proc/uptime` 뿐만 아니라 `/proc/meminfo` 같이 메모리 정보도 확인할 수 있다.
* 아파치 프로세스만 보도록 아래의 명령어도 실행할 수 있다.
    * `watch -d -n 1 'ps -ef | grep httpd | grep -v grep'`

## <a id="reference"></a> reference 🚀
* http://linuxchannel.net/docs/sys-watch.txt