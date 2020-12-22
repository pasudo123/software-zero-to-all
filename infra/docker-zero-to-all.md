# docker-zero-to-all
* 도커를 정리.

<BR>

## <a id="index"></a>index
* [docker 기본개념](https://pasudo123.tistory.com/398)
* [docker 컨테이너를 외부에 노출](#how-to-expose-to-external-that-container-of-docker)
* [Dockerfile 작성하기](#how-to-write-way-docker-file)
* [docker command](#docker-command-text)
    * [docker build command](#docker-build-command)
    * [docker build command option](#docker-build-command-option)
    * [docker image command](#docker-image-command)
        * rm, prune
    * [docker images command option](#docker-images-command-option)
    * [docker container command](#docker-container-command)
    * [docker container command option](#docker-container-command-option)
    * [docker run command](#docker-run-command)
    * [docker run command option](#docker-run-command-option)
    * [docker ps](#docker-ps-command)
* [컨테이너 접속 이후 ctrl + P,Q 와 exit 의 차이는 무엇인가](#ctrl-vs-exit)
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

## <a id="how-to-write-way-docker-file"></a>Dockerfile 작성하기
* [목차이동](#index)
* [Dockerfile 작성하는 최선의 방법에 대한 관련 링크](https://docs.docker.com/develop/develop-images/dockerfile_best-practices/)
* context (컨텐스트)
    * Dockerfile 과 같은 디렉토리에 있는 모든 파일들은 컨텍스트라고 한다. (context)
* ADD vs COPY 
    ```shell
    COPY requirements.txt /tmp/
    RUN pip install --requirement /tmp/requirements.txt
    COPY . /tmp/
    ```
    * 둘이 사용방법은 동일하다.
        * `COPY test.html /home/`
        * `COPY ["test.html", "/home/"]`
    * __결론부터 말하면 COPY 를 애용__ 하자.
    * __ADD__
        * 파일을 이미지에 추가한다. 추가하는 파일은 Dockerfile 이 위치한 Context 가 기본경로인 상태에서 추가한다.
        * __외부 URL 및 tar 파일도 추가할 수 있다.__
        * `ADD http://example.com/big.tar.xz /usr/src/things/`
    * __COPY__
        * 로컬 디렉토리에서 읽어들인 Context 로부터 이미지에 파일을 복사하는 역할
        * __로컬 파일만 이미지에 추가할 수 있다.__
    * ADD 를 이용하게 되면 실질적으로 이미지에 추가하는 파일이 어떤 것인지 확신하기 어렵다. 
    * COPY 는 Context 로 부터 직접 파일을 추가하기 때문에 명확하다.
    

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
// 특정 이미지에 대한 컨테이너 생성 및 실행
$ docker create -i -t --name {custom-container-name} {image}
$ docker create -i -t --name mycustom centos:8
$ docker start {container-name}
$ docker start mycustom


// 컨테이너 생성 및 실행
// sour-port : 호스트의 포트번호
// dest-port : 컨테이너의 포트번호
$ docker run -p {sour-port}:{dest-port} {image-name}:{tag-name}


// -d 옵션에 의해 백그라운드로 돌아가는 특정한 컨텐이너 접속 : 배시 셸을 쓸수 있도록 도와준다. (-i 와 -t 옵션이 필요하다.)
// container-id 입력 시 2~3 자리만 입력하여도 무방, 하지만 다른 동일한 2~3 자리 컨테이너 id 존재 시, 3~4 자리 입력을 권장함
$ docker exec -it {container-id | container-name} /bin/bash
$ docker exec -i -t {container-id | container-name} /bin/bash

// 특정한 컨테이너 정지
$ docker stop {container-name}


// 특정한 컨테이너 삭제
$ docker rm {container-name} // 해당 컨테이너가 정지 되어 있지 않다면 삭제 안됨


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
|--interactive(-i)||상호입출력이 가능하도록 한다.||
|--tty(-t)||tty를 활성화 해서 배시(bash) 셸을 사용하도록 한다.||



<BR>

## <a id="docker-run-command"></a>docker run command
* [목차이동](#index)
```shell
// 컨테이너 생성 및 실행
// sour-port : 호스트의 포트번호
// dest-port : 컨테이너의 포트번호
$ docker run -p {sour-port}:{dest-port} {image-name}:{tag-name}

// docker run : 호스트 볼륨 공유
// {host 공유 디렉토리}:{컨테이너 공유 디렉토리}
docker run -d -v /home/wordpress_db:/var/lib/mysql


// 볼륨 컨테이너를 만들고 해당 컨테이너와 볼륨 마운트를 수행
docker run -d --name new_container --volume-from {container-name}
```

<BR>

## <a id="docker-run-command-option"></a>docker run command option
|name(shortcut)|default|description|example|
|-------------|-------------|-------------|-------------|
|--volume(-v)||볼륨 바인드를 수행한다. <br> 이미지에 안에 디렉토리가 존재하는 상태에서 호스트의 볼륨을 공유하면 __이미지 내 기존 디렉토리는 덮어씌어진다.__ |`docker run -d -v /data/etc:/var/etc -p 23340:14480 pasudo123/springboot-docker-basis`|
|--volume-from||지정한 컨테이너와 볼륨 바인드를 수행한다.|`docker run  -d  --name new_container --volume-from {container_name}` |
|--link||타 컨테이너의 내부 ip 를 알 필요없이 컨테이너 alias 만으로도 접근하도록 설정한다. (deprecated 될 예정이고, docker bridge 이용해야한다.)|`docker --link wordpressdb:mysql` (wordpressdb 컨테이너 명칭을 mysql 로 지정하였다. 이렇게하면 wordpressdb 의 ip 를 몰라도 호스트명으로 접근이 가능하다.)|

<BR>

## volume container 구성
<img src="https://github.com/pasudo123/SoftwareZeroToALL/blob/master/Image/2020-09-19_volume-container.png">

<BR>

## <a id="docker-ps-command"></a>docker ps
* [목차이동](#index)
```
// 도커 엔진위에 올라간 [실행중인] 컨테이너 목록 출력
$ docker ps


// 도커 엔진위에 올라간 [전체] 컨테이너 목록 출력 (정지된 컨테이너 포함)
$ docker ps -a


// 전체 도커 컨테이너의 id 값을 출력한다.
// (--quiet) -q 옵션은 container id 만 표시하는 옵션값이다.
$ docker ps -a -q


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

## <a id="ctrl-vs-exit"></a>컨테이너 접속 이후 ctrl + P,Q 와 exit 의 차이는 무엇인가
* [목차이동](#index)   

### ctrl + P,Q
* 백그라운드로 동작하지 않는 컨테이너에 대해 attach 명령어로 접속이 가능하다. 그리고 ctrl + P,Q 명령어를 통해 컨테이너를 빠져나올때 필요하다. 만약 `exit` 명령어를 수행한다면 해당 컨테이너는 빠져나오는 즉시 컨테이너는 종료된다. 내부에 별도의 포그라운드 프로그램이 없는 상태이다.
    * 만약 컨테이너 내부에 프로그램이 실행하지 않은 채 컨테이너 실행 명령어 `docker run -d ~` 을 하게된다면 해당 컨테이너는 생성이후 바로 종료된다.

### exit 
* 컨테이너 내부에 포그라운드로 실행중인 프로그램이 있는 경우에는 `docker exec` 명령어를 통해 접속한다. 이는 `docker run -d ~` 를 통해서 컨테이너를 백그라운드에서 동작하는 애플리케이션으로 실행하였을 때 가능하다. 
    * `exec` 수행 시, `-i` 와 `-t` 옵션이 없으면 컨테이너 내부에 돌아가는 포그라운드 프로그램에 대한 명령어 결과만 반환받는다.
* 결과적으로 포그라운드 프로그램이 컨테이너 내부에서 동작하기 때문에 `exit` 명령어를 이용하더라도 컨테이너는 종료되지 않는다.