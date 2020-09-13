# DockerZeroToALL
* 도커를 정리.

<BR><BR>

## <a id="index"></a>목차
* [도커란](https://pasudo123.tistory.com/398)
* [docker command](#docker-command-text)
    * [docker build command](#docker-build-command)
    * [docker build command option](#docker-build-command-option)
    * [docker image command](#docker-image-command)
    * [docker container command](#docker-container-command)
    * [docker ps](#docker-ps-command)
* [도커 명령어 reference](https://docs.docker.com/engine/reference/commandline/docker/)

<BR><BR>

## <a id="docker-command-text"></a>docker command
* [목차이동](#index)
```
// 도커 버전 확인
$ docker -v
```

<BR><BR>
<!------------------------------------------------------->

## <a id="docker-build-command"></a>docker build command
* [목차이동](#index)
```
// 도커 이미지 조회
$ docker images


// 도커 특정 이미지 삭제
$ docker rmi {image-id}


// 도커 미사용 이미지 전체 삭제
$ docker image prune
```

<BR><BR>
<!------------------------------------------------------->

## <a id="docker-build-command-option"></a>docker build command option
|name(shortcut)|default|description|example|
|-------------|-------------|-------------|-------------|
|--tag(-t)||{name}:{tag} 형식 이름 지정|`$docker build -t ubuntu:14.04`|

<BR><BR>
<!------------------------------------------------------->

## <a id="docker-ps-command"></a>docker container command
* [목차이동](#index)
```
// 컨테이너 생성 및 실행
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

<BR><BR>
<!------------------------------------------------------->

## <a id="docker-ps-command"></a>docker ps
* [목차이동](#index)
```
// 도커 엔진위에 올라간 [실행중인] 컨테이너 목록 출력
$ docker ps


// 도커 엔진위에 올라간 [전체] 컨테이너 목록 출력 (정지된 컨테이너 포함)
$ docker ps -a
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

<BR><BR>
<!------------------------------------------------------->