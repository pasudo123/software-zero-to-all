# 쿠버네티스 파드 이해하기

## pod (파드)
* `pod` 는 쿠버네티스에서 생성하고 관리할 수 있는 배포 가능한 가장 작은 컴퓨팅 단위이다.   
* `pod` 는 하나 이상의 컨테이너 그룹이다.
  * 해당 그룹은 스토리지 및 네트워크를 공유하고, 해당 컨테이너를 구동하는 방식에 대한 명세를 갖는다.
   * 파드 내 컨테이너들은 스토리지 및 네트워크를 공유하기 때문에 파드 내 컨테이너 간에 별도의 네트워크 설정을 필요하지 않다. 
   * 리눅스 네임 스페이스 역할을 파드가 해주고 있다.
  * 파드의 콘텐츠는 항상 함께 배치되고, 함께 스케줄되며 공유 콘텍스트에서 실행된다.
  * 파드는 애플리케이션 별 `논리 호스트` 를 모델링한다. 클라우드가 아닌 콘텍스트에서 동일한 물리 또는 가상 머신에서 실행되는 애플리케이션은 동일한 논리 호스트에서 실행되는 클라우드 애플리케이션과 비슷하다.

## pod 란 무엇인가?

## pod 의 사용
* 일반적으로 싱글톤 파드를 포함하여 파드를 직접적으로 만들 필요는 없다.
* deployment 또는 job 과 같은 워크로드(쿠버네티스에서 구동되는 애플리케이션) 리소스를 사용하여 생성한다.
  * 워크로드가 아니다. `워크로드 리소스` 이다.
* 파드가 상태를 추적해야 한다면, 스테이트풀셋(StatefulSet) 리소스를 고려한다.
  * 스테이트풀셋은 애플리케이션의 스테이트풀을 관리하는데 사용하는 워크로드 API 오브젝트이다.
  * 스테이트풀셋은 파드 집합의 디플로이먼트와 스케일링을 관리하며, 파드들의 순서 및 고유성을 보장한다.
* k8s 클러스터의 파드는 두가지 주요한 방식으로 사용된다.
  * `단일 컨테이너를 실행하는 파드`
    *  파드 당 하나의 컨테이너 모델은 가장 일반적인 쿠버네티스 유스케이스이다. 이 경우, 파드를 단일 컨테이너를 둘러싼 wrapper 라고 생각할 수 있다. 
    *  k8s 는 컨테이너를 직접 관리하는 대신 파드를 관리한다.
  * `함께 작동해야 하는 여러 컨테이너를 실행하는 파드` 
    * 파드는 밀접하게 결합되어 있고, 리소스를 공유해야하는 여러 개의 컨테이너로 구성된 애플리케이션을 캡슐화할 수 있다.
    * 이렇게 배치된 컨테이너는 하나의 결합된 서비스 단위를 형성한다. 예를 들어 하나의 컨테이너는 공유 볼륨에 저장된 데이터를 퍼블릭에 제공하는 반면에, 별도의 사이드카 컨테이너 (포드 내 부가적인 컨테이너를 사이드카 컨테이너라고 부른다.) 는 해당 파일을 새로 고치거나 업데이트 한다.
    * 파드는 이러한 `컨테이너`, `스토리지 리소스`, `임시 네트워크 ID` 를 단일 단위로 함께 래핑한다.

> 각 파드는 특정 애플리케이션의 단일 인스턴스를 실행하기 위한 것이다.   
> 더 많은 인스턴스를 실행하여 더 많은 전체 리소스를 제공하기 위해 애플리케이션을 수평적으로 확장하려면, 각 인스턴스에 각각의 여러 파드를 사용해야할 것이다.
> k8s 에서 이를 일반적으로 `레플리케이션` 이라고 부른다. 복제된 pod 는 일반적으로 워크로드 리소스와 해당 `컨트롤러`(API 서버를 통해 클러스터의 공유된 상태를 감시하고, 현재 상태를 원하는 상태로 이행시키는 컨트롤 루프) 에 의해 그룹으로 생성되고 관리된다. 

## pod 가 여러 컨테이너를 관리하는 방법 (링크를 직접 보는게 나을듯)
* 파드는 응집력있는 서비스 단위를 형성하는 여러 협력 프로세스(컨테이너)를 지원하도록 설계되었다.   
* 파드의 컨테이너는 클러스터의 동일한 물리 또는 가상머신에서 자동으로 같은 위치에 배치되고 스케줄된다.
* 컨테이너는 리소스 의존성을 공유하고 서로 통신하고 종료 시기와 방법을 조정할 수 있다.

## pod 작업
* 사용자가 k8s 에서 직접 개별 파드를 만드는 경우는 거의 없다. 싱글톤 파드도 마찬가지이다.
* 파드가 상대적으로 일시적인, 일회용 엔티티로 설계되었기 때문이다. 파드가 생성될 때 (사용자가 직접 또는 컨트롤러가 간접적으로), 새 파드는 클러스터의 `노드`(k8s 의 워커머신) 에서 실행되도록 스케줄된다.
* 파드는 파드 실행이 완료 혹은 파드 오브젝트가 삭제 또는 리소스 부족으로 파드가 축출되거나 노드가 실패할때까지 해당 노드에 남아있다.
> 파드에서 컨테이너를 다시 시작하는 것과 파드를 다시 시작하는 것을 혼동하면 안된다.   
> 파드는 프로세스가 아니라 `컨테이너를 실행하기 위한 환경` 이다. 파드는 삭제될 때까지 유지된다.

파드 오브젝트에 대한 매니페스트를 만들 때, 지정된 이름이 유효한 `DNS 서브도메인 이름` 인지 확인한다.
* `매니페스트` 란, k8s 의 여러가지 리소스를 정의하는 파일을 매니페스트 파일이라고 한다.
* `DNS 서브도메인` 이란 클라이언트에서 k8s 클러스터 내 리소스를 인식하는 이름이다.

## pod 와 controller
* 워크로드 리소스를 사용하면, 여러 파드를 관리할 수 있다.
* 리소스에 대한 컨트롤러는 파드 장애 시, 복제 및 롤아웃과 자동복구를 처리한다.
* 예를 들어, 노드가 실패했다면 컨트롤러는 해당 노드와 파드가 작동을 중지했음을 인식하고 대체 파드를 생성한다.
  * 스케줄러는 이런 대체 파드를 정상 노드에 배치한다.

## 하나 이상의 파드를 관리하는 워크로드 리소스
* [디플로이먼트](https://kubernetes.io/ko/docs/concepts/workloads/controllers/deployment/)
* [스테이트풀셋](https://kubernetes.io/ko/docs/concepts/workloads/controllers/statefulset/)
* [데몬셋 (DaemonSet)](https://kubernetes.io/ko/docs/concepts/workloads/controllers/daemonset/)

## 파드 템플릿
* 파드템플릿은 파드를 생성하기 위한 명세(spec) 이며, 디플로이먼트, 잡, 데몬셋과 같은 워크로드 리소스에 포함된다.
* 워크로드 리소스의 각 컨트롤러는 `워크로드 오브젝트 내부의 PodTemplate 을 사용하여 실제 파드를 생성` 한다.
* PodTemplate 은 앱을 실행하는데 사용되는 워크로드 리소스가 무엇이든지 원하는 상태의 일부이다.

아래는 하나의 컨테이너를 시작하는 `template` 이 있는 간단한 잡의 매니페스트(k8s 의 여러가지 리소스를 정의하는 파일) 이다.   
해당 파드의 컨테이너는 메시지를 출력한 다음 일시중지한다.
```yaml
apiVersion: batch/v1
kind: Job
metadata:
  name: hello
spec:
  template:
    # ==== 여기서부터 파드 템플릿이다
    spec:
      containers:
      - name: hello
        image: busybox
        command: ['sh', '-c', 'echo "Hello, Kubernetes!" && sleep 3600']
      restartPolicy: OnFailure
    # ==== 여기까지 파드 템플릿이다
```
* 파드템플릿을 수정하거나 새로운 파드 템플릿을 바꾸어도 `이미 존재하는 파드에는 직접적인 영향을 끼치지 않는다.`
* 워크로드 리소스의 파드 템플릿을 변경하는 경우에, 해당 리소스는 수정된 템플릿을 사용하는 대체 파드를 생성하여야 한다.

## 추가 공부가 필요

## reference
* [파드](https://kubernetes.io/ko/docs/concepts/workloads/pods/)
