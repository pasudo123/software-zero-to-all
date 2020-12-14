## nohup command 👀
* HUP (HangUP) Signal 을 무시하는 명령어이다.
    * `no hang up` 의 준말
* HUP 이란 kill 명령어의 시그널 번호 `1`, `SIGHUP` 을 의미한다.
    * `SIGHUP` 은 로그아웃으로 세션과의 연결이 끊기는 시그널을 뜻한다.
* `nohup` 은 세션이 끊기더라도 프로세스는 데몬형태로 실행되어 종료되지 않는다.
* 출력은 따로 리디렉션이 되지 않은 경우, nohup 을 실행시킨 위치에서 nohup.out 이라고 불리는 파일에 쓰여진다.

__간단한 배쉬파일 작성__
* sample.sh
* 1초마다 `1`부터 `10000`까지 계속해서 숫자를 더해간다.
```bash
#!/bin/sh
x=1 
while [ $x -le 10000 ]
do
	echo "current number $x"
	sleep 1s
	x=$(($x+1))
done	

```

__nohup 사용방법__
```shell
// 일반적인 jar 패키징 파일 실행 : nohup.out 파일이 해당 위치에 생긴다.
$ nohup ./sample.sh
nohup: ignoring input and appending output to 'nohup.out'


// nohup.out 의 출력을 다른 파일에 할 경우
$ nohup ./sample.sh > new_nohup.out
nohup: ignoring input and redirecting stderr to stdout


// nohup.out 의 출력을 하지 않을 경우 : nohup 파일이 생기지 않는다.
$ nohup ./sample.sh > /dev/null
nohup: ignoring input and redirecting stderr to stdout
```
* 한가지 특이점은 `ctrl + c (=SIGINT)` 를 누르면 nohup 은 종료된다.
* `tail -f nohup.out` 을 하여도 파일은 작성되는데 실시간 출력은 되지 않는다.


__& 의 활용__
```shell
// 프로그램 수행 시, 명령어 맨 끝에 `&` 을 붙여주면 프로그램은 백그라운드로 실행된다.
& ./sample.sh &
[1] 597 
```
* `&` 만 실행하면 꾸준하게 화면에 출력된다. `ctrl + c (=SIGINT)` 명령어도 무시해버린다.
    * 무한하게 계속 출력된다.
    * 다른 터미널에서 pid 값을 조회해서 종료시켜야 한다.
* `nohup` 과 `&` 를 혼합해서 써야한다.

__혼합사용__
```shell
& nohup ./sample.sh > number.log &
[1] 790
& tail -f number.log
```
* 출력은 num

## nohup vs &(background)
* nohup 은 세션 종료가 되더라도 데몬형태로 프로세스가 띄어진 상태이다.
    * 특정 터미널을 통해서 `nohup ./sample.sh` 실행이후 해당 터미널을 닫고 다른 터미널을 통해서 `ps -ef | grep sample` 을 하면 pid 가 찍히는 것을 확인할 수 있다.
* `&` 은 background 형태로 실행된 상태이지만, 세션 종료 시 프로세스도 같이 종료된다. (터미널을 끄면 자동으로 pid 가 종료된다.)
    * `&` 은 단독으로 stdout/stderr 를 리다이렉트하지 못한다. 따라서 출력/오류시에 그대로 그냥 현재 실행한 터미널에서 바로 실행되는 것이다.
* 결과적으로 둘을 혼합해서 써야햔다.

## <a id="reference"></a> reference 🚀
* https://stackoverflow.com/questions/15595374/whats-the-difference-between-nohup-and-ampersand