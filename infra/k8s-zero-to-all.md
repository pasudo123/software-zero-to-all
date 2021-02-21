# k8s-zero-to-all
쿠버네티스 정리

# index
* [설치](#install)
* [개념](#concept)
* [고유특징](#feature)
* [Pod, 파드 : 컨테이너를 다루는 기본단위](#pod)
  * [pod vs docker container](#pod-vs-docker-container)
* [레플리카셋](#replicaset)

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
```
// kubectl apply -f 명령어로 쿠버네티스 생성이 가능하다.
$ kubectl apply -f {yaml-file-name}

```

### <a id="pod-vs-docker-container"></a> 파드와 도커 컨테이너 간의 차이점
* 도커 컨테이너같의 통신을 위한 `네트워크 네임스페이스의 역할` 을 파드가 대신 해주고 있다.
  * 리눅스 네임 스페이스 공유 역할을 파드가 해주기 때문에 따로 컨테이너간 네트워크 설정은 필요하지 않다.
* 하나의 포드는 `하나의 완전한 애플리케이션` 이다.
  * 여기서 포드 내 부가적인 컨테이너를 `사이드카 컨테이너` 라고 부른다.
  * 사이드카 컨테이너는 포드 내의 다른 컨테이너와 네트워크 환경을 공유하게 되는데, 포드에 포함된 컨테이너들은 모두 `같은 워커 노드` 에서 함께 실행된다.
* 단순히 nginx 컨테이너만 있어도 충분하지만 `부가적인 기능` 을 필요로 하는 컨테이너가 같이 실행되어야 하는 경우 `pod` 와 `컨테이너` 의 차이는 분명해진다.

## <a id="replicaset"></a>레플리카셋(replicaset) : 일정개수의 pod 를 유지하는 컨트롤러

### 사용이유
* pod 만 yaml 파일에 정의해서 실행할 경우, 비효율적인 작업으로 이루어질 수 있다.
  * pod 의 삭제/장애 시에 해당 pod 를 다시 생성하지 않는 한 복구가 되지 않는다.
* 이런 `한계점` 을 극복해주는 것이 `레플리카셋` 이다.
  * 정해진 수의 `동일한 파드가 실행될 수 있도록` 관리한다. (중요!!!)
    * 하나가 죽으면 다른 노드에 또 하나의 파드를 생성한다.
    * 하나가 관리되지 않으면 다른 노드에 또 하나의 파드를 생성한다.
  * 노드 장애 등의 이유로 파드를 사용할 수 없다면, `다른 노드` 에서 파드를 다시 생성한다.

### 동작원리
* 레플리카셋은 파드와 연결되어 있지 않다.
  * 둘은 서로 느슨한 결합을 맺고 있다.
  * 둘은 `라벨 셀렉터` 에 의해서 정의되고 있다.
* yaml 파일에서 `레플리카셋 정의` 와 `파드 정의` 를 구분할 수 있다.
* 라벨은 파드 등의 `쿠버네티스 리소스를 분류할 때 유용하게 사용할 수 있는 메타데이터` 이다
  * 쿠버네티스 리소스의 부가적인 정보를 표현할 수 있다.
  * 서로 다른 오브젝트가 서로를 찾아야할 때 사용되기도 한다.
* 레플리카셋은 `selector.matchLabel` 의 항목처럼 두 리소스간의 라벨을 일치시킴으로써 쿠버네티스의 기능을 사용하게 될 것이다.
* 레플리카셋 vs 레플리케이션 컨트롤러 (deprecated) 
  * 레플리카셋이 더 많은 기능을 내장하고 있다. (+ 표현식이 가능하다.)
    * [selector 표현식 참고링크](https://dev-youngjun.tistory.com/31)

## <a id="deployment"></a>디플로이먼트(deployment) : 레플리카셋과 포드의 배포를 관리
디플로이먼트는 레플리카셋과 포드의 상위 `오브젝트` 이다.

### 사용이유
* 디플로이먼트를 이용함으로써, 레플리카셋과 포드의 버저닝할 수 있다.
  * 디플로이먼트를 이용하여 포드의 정보를 업데이트함으로써 이전버전의 레플리카셋을 삭제하지 않고 남겨두고 있다.
  * `--record=true` 옵션을 이용하여, 디플로이먼트를 변경하면 변경사항을 디플로이먼트에 기록함으로써 해당 버전의 레플리카셋을 보존한다.
  * 만약 이전버전의 레플리카셋으로 되돌리려고 하는 경우에 `--to-revision={revision-number}` 을 통하여 리비전 번호를 입력하면 된다.reco

# reference
* 책 : 시작하세요! 도커/쿠버네티스
* https://kubernetes.io/blog/2020/05/21/wsl-docker-kubernetes-on-the-windows-desktop/
