# DockerZeroToALL
* 도커를 정리.

<BR>

## <a id="index"></a>index
* [docker 기본개념](https://pasudo123.tistory.com/398)
* [docker 컨테이너를 외부에 노출](#how-to-expose-to-external-that-container-of-docker)
* [Dockerfile 작성하기](#how-to-write-way-Dockerfile)
* [docker command](#docker-command-text)
    * [docker build command](#docker-build-command)
    * [docker build command option](#docker-build-command-option)
    * [docker image command](#docker-image-command)
    * [docker images command option](#docker-images-command-option)
    * [docker container command](#docker-container-command)
    * [docker container command option](#docker-container-command-option)
    * [docker ps](#docker-ps-command)
* [도커 명령어 reference](https://docs.docker.com/engine/reference/commandline/docker/)

<BR>

## <a id="how-to-expose-to-external-that-container-of-docker"></a>docker 컨테이너를 외부에 노출하는 방법
* [목차이동](#index)
* 컨테이너는 가상IP 와 마찬가지로 가상 IP 주소를 할당받는다.
```shell
/ # ifconfig
eth0      Link encap:Ethernet  HWaddr 02:42:AC:11:00:02
          inet addr:172.17.0.2  Bcast:172.17.255.255  Mask:255.255.0.0
          UP BROADCAST RUNNING MULTICAST  MTU:1500  Metric:1
          RX packets:17 errors:0 dropped:0 overruns:0 frame:0
          TX packets:0 errors:0 dropped:0 overruns:0 carrier:0
          collisions:0 txqueuelen:0
          RX bytes:1382 (1.3 KiB)  TX bytes:0 (0.0 B)

lo        Link encap:Local Loopback
          inet addr:127.0.0.1  Mask:255.0.0.0
          UP LOOPBACK RUNNING  MTU:65536  Metric:1
          RX packets:0 errors:0 dropped:0 overruns:0 frame:0
          TX packets:0 errors:0 dropped:0 overruns:0 carrier:0
          collisions:0 txqueuelen:1000
          RX bytes:0 (0.0 B)  TX bytes:0 (0.0 B)
```
* 기본적으로 도커는 컨테이너에 172.17.0.x 의 IP 를 순차적으로 할당받는다.
* 도커의 NAT IP 인 172.17.0.2 가 존재
* eth0 인터페이스, lo 로컬호스트 인터페이스가 있다.
* 외부에 컨테이너를 노출시키기 위해선 호스트 포트와 컨테이너 포트가 바인딩되어야 한다.
    * [docker container command 참고 : docker run ~](#docker-ps-command)
* __유의점__
    * 컨테이너 내부의 __서비스가__ 어떤 포트로 동작되고 있는지 파악해야 한다.
    * 가령, 웹서버 nginx 컨테이너를 띄어놓고 호스트와 컨테이너의 포트 바인딩을 `80:81` 로 하였다면 컨테이너의 `81` 포트는 현재 컨테이너에 있는 `80` 포트로 띄어진 nginx 프로세스에 연결되지 못하기 때문에 접근이 안된다.
```
// 특정한 컨테이너의 포트 바인딩 조회
PS C:\Users\pasudo123> docker port {container-id}
14480/tcp -> 0.0.0.0:14480
{dest-port}/tcp -> 0.0.0.0.{sour-port}

// 0.0.0.0 은 호스트의 활용 가능한 모든 네트워크 인터페이스에 바인딩함을 의미.
// 호스트의 어떤 IP 로 {sour-port} 를 접근하든 컨테이너의 {dest-port} 에 연결
```

<BR>

## <a id="how-to-write-way-Dockerfile"></a>Dockerfile 작성하기
* [목차이동](#index)


<BR>

## <a id="docker-command-text"></a>docker command
* [목차이동](#index)
```
// 도커 버전 확인
$ docker -v
```

<BR>

## <a id="docker-build-command"></a>docker build command
* [목차이동](#index)
```
// 이미지 빌드 ( dot 표기 필요 ! )
$ docker build -t {name}:{tag} .
```

<BR>

## <a id="docker-build-command-option"></a>docker build command option
|name(shortcut)|default|description|example|
|-------------|-------------|-------------|-------------|
|--tag(-t)||{name}:{tag} 형식 이름 지정|`$docker build -t ubuntu:14.04 .`|

<BR>

## <a id="docker-image-command"></a>docker image command
* [목차이동](#index)
```
// 도커 이미지 조회
$ docker images


// 도커 특정 이미지 삭제
$ docker rmi {image-id}


// 도커 미사용 이미지 전체 삭제
$ docker image prune


// 도커 REPOSITORY, TAG 가 <none> 값인 것 모두 삭제
$ docker rmi $(docker images -f "dangling=true" -q)
```

<BR>

## <a id="docker-images-command-option"></a>docker images command option
* [목차이동](#index)

|name(shortcut)|description|example|
|-------------|-------------|-------------|
|--filter(-f)|조건을 주고, 필터링해서 출력|`docker images --filter "dangling=true" // UNTAGGED IMAGES 를 출력한다.`|
|--no-trunc|출력을 자르지 않고 전체 다 보여줌|`docker ps (-a) --no-trunc`
|--quiet(-q)|숫자 ID 만 보여줌|`docker images --filter "dangling=true" -q // UNTAGGED IMAGES 에서 IMAGE ID 만 보여준다.`|

<BR>

## <a id="docker-container-command"></a>docker container command
* [목차이동](#index)
```
// 컨테이너 생성 및 실행
// sour-port : 호스트의 포트번호
// dest-port : 컨테이너의 포트번호
$ docker run -p {sour-port}:{dest-port} {image-name}:{tag-name}


// 특정한 컨텐이너 접속
// container-id 입력 시 2~3 자리만 입력하여도 무방, 하지만 다른 동일한 2~3 자리 컨테이너 id 존재 시, 3~4 자리 입력을 권장함
$ docker exec -it {container-id} /bin/bash


// 특정한 컨테이너 정지
$ docker stop {container-name}


// 특정한 컨테이너 삭제
// 컨테이너 삭제 시에는 해당 컨테이너가 정지된 상태여야 한다.
$ docker rm {container-name}


// 특정한 컨테이너 [강제] 삭제
$ docker rm -f {container-name}


// 정지된 컨테이너 모두 삭제
$ docker container prune
```

<BR>

## <a id="docker-container-command-option"></a>docker container command option
* [목차이동](#index)

|name(shortcut)|default|description|example|
|-------------|-------------|-------------|-------------|
|--detach(-d)||백그라운드에서 컨테이너를 실행하고, 컨테이너 ID 를 출력한다.|`docker run -d -p 23340:14480 pasudo123/springboot-docker-basis`|


<BR>

## <a id="docker-ps-command"></a>docker ps
* [목차이동](#index)
```
// 도커 엔진위에 올라간 [실행중인] 컨테이너 목록 출력
$ docker ps


// 도커 엔진위에 올라간 [전체] 컨테이너 목록 출력 (정지된 컨테이너 포함)
$ docker ps -a


// {value} 값을 가진 container 조회
// ex) docker ps -a -f status=exited
$ docker ps -a -f status={value}
```
* CONTAINER ID
    * 컨테이너에게 자동으로 할당된 고유한 ID
    * 전체 CONTAINER ID 확인 시 `$ docker inspect {container-name} | grep Id`
* IMAGE
    * 컨테이너를 생성할 때 사용된 이미지의 이름
* COMMAND
    * 컨테이너가 시작될 때 실행할 명령어
* CREATED
    * 컨테이너가 생성되고 난 뒤 흐른 시간
* STATUS
    * 컨테이너의 상태 (Up : 실행중,  Exited : 종료)
* PORTS
    * 컨테이너가 개방한 포트와 호스트에 연결한 포트를 나열
* NAMES
    * 컨테이너의 고유한 이름 
    * 컨테이너 생성 시, `--name` 옵션으로 이름을 설정하지 않으면 도커엔진이 임의로 이름을 조합한다.

<BR>