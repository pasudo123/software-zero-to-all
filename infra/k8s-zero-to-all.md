# k8s-zero-to-all
쿠버네티스 정리

# index
* [설치](#install)
* [개념](#concept)

## <a id="install"></a>설치
Docker for windows 에서 setting 메뉴에서 `enable kubernetes` 선택 및 `apply & restart` 수행.
<img src="../Image/20210207_k8s01.png" width="500" />
<img src="../Image/20210207_k8s02.png" width="500" />

설치가 완료된 뒤 아래와 같이 수행되어야 한다.
```
$ kubectl version --short
Client Version: v1.19.3
Server Version: v1.19.3
```
* k8s 클라이언트/서버 버전이 출력되어야 한다.
* `kubectl` 은 쿠버네티스에 접근해 api 를 사용하기 위한 명령어이다. 도커에서 사용한 `docker` 와 비슷한 역할이라고 생각하면 된다.

### 기본설정을 이용하여 버추얼 박스로 minikube 설치
* `wsl` 로 `apt-get install virtualbox` 를 입력한다.
  * wsl 에 `No network connection` 이란 문구가 나오는데 [여기서](https://github.com/microsoft/WSL/issues/5336) 해결하였다.
* `책을 따라서` 여기까지 수행하다가 바로 https://kubernetes.io/blog/2020/05/21/wsl-docker-kubernetes-on-the-windows-desktop/ 여기 링크로 넘어가서 따로 진행했다.

## <a id="concept"></a>개념
* 컨테이너 오케스트레이션 툴
* 여러 서버의 자원을 클러스터링하여 컨테이너를 배치하는 것이 쿠버네티스의 핵심이다.


# reference
* 책 : 시작하세요! 도커/쿠버네티스
* https://kubernetes.io/blog/2020/05/21/wsl-docker-kubernetes-on-the-windows-desktop/
