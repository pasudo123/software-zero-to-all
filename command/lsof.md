## lsof

* "List Open Files"의 약자
* UNIX 및 유닉스 계열 운영 체제에서 사용되는 명령어. 시스템에서 현재 열려 있는 파일 목록을 표시합니다.

## 8080 포트 pid 확인

아래처럼 사용할 수 있다.

```shell
❯ lsof -i :8080
COMMAND   PID      USER   FD   TYPE             DEVICE SIZE/OFF NODE NAME
java    22828 pasudo123  111u  IPv6 0x7a856c15c61ecf9f      0t0  TCP *:http-alt (LISTEN)
```
