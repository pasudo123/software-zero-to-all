# k8s-zero-to-all
쿠버네티스 정리

# index
* [설치](#install)
* [개념](#concept)
* [고유특징](#feature)
* [Pod, 파드 : 컨테이너를 다루는 기본단위](#pod)
  * [pod vs docker container](#pod-vs-docker-container)

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

## <a id="feature"></a>고유특징
* 🦾 모든 리소스는 오브젝트 형태로 관리된다.
  * 오브젝트란 추상화된 집합이다.
  * `kubectl api-resources` 를 통해서 오브젝트를 조회할 수 있다.
  * `kubectl explain {object-name}` 을 통해서 특정한 오브젝트의 설명을 볼 수 있다.

```
// 오브젝트 조회
$ kubectl api-resources
NAME                              SHORTNAMES   APIGROUP                       NAMESPACED   KIND
bindings                                                                      true         Binding
componentstatuses                 cs                                          false        ComponentStatus
configmaps                        cm                                          true         ConfigMap
endpoints                         ep                                          true         Endpoints
... (훨씬 많음)

// 특정한 
$ kubectl explain pod
KIND:     Pod
VERSION:  v1

DESCRIPTION:
     Pod is a collection of containers that can run on a host. This resource is
     created by clients and scheduled onto hosts.
```

* 🦾 쿠버네티스는 명령어 `kubectl` 을 사용할 수 있지만, 반면에 yaml 파일을 더 많이 사용한다.
  * `쿠버네티스를 잘 사용하는 방법` = `yaml 파일을 잘 작성하는법` 이라해도 과언이 아니다.
* 🦾 쿠버네티스는 여러 개의 컴포넌트로 구성되어있다.
  * k8s 는 `master`, `worker` 로 나뉘어져있다.
  * `master` 는 클러스터를 관리하는 역할을 담당하고 있다.
  * `worker` 는 애플리케이션 컨테이너가 생성된다.
  * 클러스터 구성을 위해서 `kubelet` 이라는 에이전트가 모든 노드에서 실행된다. 해당 노드는 컨테이너의 생성/삭제를 담당하는 것뿐만 아니라 마스터와 워커노드간의 통신역할을 함께 담당하고 있는 중요한 에이전트이다. `kubelet` 이 정상적으로 동작하지 않으면 해당 노드는 쿠버네티스와 제대로 연결되지 않을 가능성이 크다.

## <a id="pod"></a>Pod, 파드 : 컨테이너를 다루는 기본단위
* `파드` 는 1개이상의 컨테이너로 구성된 컨테이너의 집합이다.
* k8s 에서는 컨테이너 애플리케이션을 배포하기위한 기본단위로 `파드` 라는 개념을 사용한다.
* 1개의 `파드` 에는 1개의 `컨테이너` 가 존재할 숴 있고, 여러 개의 `컨테이너` 가 존재할 수 있다.
  * 가령, nginx 컨테이너가 하나의 파드안에 있다고 가정했을 때, nginx 컨테이너를 여러 개 띄우려고 한다면 nginx 컨테이너를 포함하는 여러 개의 파드를 생성하여야 한다.
* 결국 `파드` 는 컨테이너 애플리케이션을 나타내기위한 기본적인 구성요소가 된다.

### <a id="pod-vs-docker-container"></a> 파드와 도커 컨테이너 간의 차이점
* 도커 컨테이너같의 통신을 위한 `네트워크 네임스페이스의 역할` 을 파드가 대신 해주고 있다.
  * 리눅스 네임 스페이스 공유 역할을 파드가 해주기 때문에 따로 컨테이너간 네트워크 설정은 필요하지 않다.
* 좀 더 상세한 내용은 추가할 예정.

# reference
* 책 : 시작하세요! 도커/쿠버네티스
* https://kubernetes.io/blog/2020/05/21/wsl-docker-kubernetes-on-the-windows-desktop/
