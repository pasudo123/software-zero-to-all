# k8s object (쿠버네티스 오브젝트)
* 쿠버네티스의 모든 리소스는 오브젝트 형태로 관리된다.
* 오브젝트란 추상화된 집합인데, 쿠버네티스 시스템에서 `영속성` 을 가진다.
* 쿠버네티스는 클러스터의 상태를 나타내기 위해 `오브젝트` 를 이용한다.
  * 어떤 컨테이너화된 애플리케이션이 동작 중인지 `오브젝트` 로 나타낼 수 있다.
  * 그 애플리케이션이 이용할 수 있는 리소스가 무엇인지 `오브젝트` 로 나타낼 수 있다.
  * 그 애플이케이션이 어떻게 재구동 정책, 업그레이드, 내고장성과 같은 것에 동작해야 하는지에 대한 정책을 결정하는 것을 `오브젝트` 로 나타낼 수 있다.
* 쿠버네티스에서 `오브젝트` 란 하나의 의도를 담은 레코드이다.
  * 오브젝트를 생성하게 되면, 쿠버네티스 시스템은 그 오브젝트 생성을 보장하기 위해 지속적으로 작동할 것이다.
  * 오브젝트를 생성함으로써, 클러스터의 `워크로드` 를 어떤 형태로 보이고자 하는지 효과적으로 쿠버네티스 시스템에 전달한다.
    * `워크로드` 란, 쿠버네티스에서 구동되는 애플리케이션이다.
    * 워크로드가 단일 컴포넌트이거나 함께 작동하는 여러 컴포넌트이든 관계없이, `쿠버네티스에서는 워크로드를 일련의 파드 집합 내에서 실행한다.`
> 결과적으로 오브젝트는 클러스터에 대해 우리가 의도한 상태를 만들어낼 수 있다.

## 오브젝트 명세 및 상태 (spec & status)
* 모든 쿠버네티스 오브젝트는 오브젝트의 구성을 결정해주는 두 개의 중첩된 오브젝트 필드를 포함한다.
  * spec object
  * status object
* spec object
  * 오브젝트를 생성할 때, 리소스에 원하는 특징(의도한 상태) 에 대한 설명을 쿠버네티스 시스템에 전달한다.
* status object
  * 쿠버네티스 시스템과 컴포넌트에 의해 제공되고 업데이트된 오브젝트의 현재 상태를 설명한다.
  * 쿠버네티스 [컨트롤 플레인](https://kubernetes.io/ko/docs/reference/glossary/?all=true#term-control-plane)은 모든 오브젝트의 실제 상태를 사용자가 의도한 상태와 일치시키기 위해 지속적으로 능동 관리한다.
    * `컨트롤 플레인` 이란 컨테이너 라이프사이클을 정의/배포/관리 하기 위한 API와 인터페이스들을 노출하는 컨테이너 오케스트레이션 레이어이다.  
> 예를 들어, k8s 디플로이먼트는 클러스터에서 동작하는 애플리케이션을 표현해줄 수 있는 오브젝트이다. 디플로이먼트를 생성할 때, 디플로이먼트 spec 에 3개의 애플리케이션 레플리카가 동작되도록 설정할 수 있다. 쿠버네티스 시스템은 그 디플로이먼트 spec을 읽어 spec 에 일치되도록 상태를 업데이트하여 3개의 의도한 애플리케이션 인스턴스를 구동시킨다. 만약 그 인스턴스들 중 어느 하나가 어떤 문제로 인해 멈춘다면 k8s 시스템은 보정을 통해 spec 과 status 차이에 대응한다.

## 쿠버네티스 오브젝트 기술하기
* `오브젝트` 를 생성하기 위해 (직접적 또는 kubectl을 통해서든) k8s API 를 이용할 때, API 요청은 요청 내용안에 JSON 형식으로 정보를 포함시켜 주어야 한다.
  * 대부분의 경우 정보는 .yaml 파일에 kubectl 에 제공한다.
  * kubectl 은 API 요청이 이루어질 때 JSON 형식으로 정보를 변환시켜 준다.
  * `kubectl` 은 쿠버네티스 시스템을 제어하기 위한 커맨드라인 도구이다.
    * k8s 시스템에 접근하여 api 를 사용할 수 있다.
  * `kubectl 을 통해 .yaml 파일을 읽고, .yaml 파일을 JSON 형식으로 변환해서 정보를 k8s api 로 보낸다.` 라고 이해하면 될까??

## yaml 파일로 제공되는 오브젝트 기술 내용
```yaml
apiVersion: apps/v1     # 해당 오브젝트를 생성하기 위해 사용하고 있는 쿠버네티스 API 버전 명시
kind: Deployment        # 어떤 종류의 오브젝트를 생성하고자 하는지
metadata:               # 이름 문자열, UID, 선택적인 네임스페이스를 포함하여 오브젝트를 유일하게 구분지어 줄 데이터
  name: nginx-deployment
spec:                   # 오브젝트에 대해 어떤 상태를 의도하는지 기술 ***
  selector:
    matchLabels:
      app: nginx
  replicas: 2 # tells deployment to run 2 pods matching the template
  template:
    metadata:
      labels:
        app: nginx
    spec:
      containers:
      - name: nginx
        image: nginx:1.14.2
        ports:
        - containerPort: 80
```
* 오브젝트 `spec` 에 대한 정확한 포맷은 모든 쿠버네티스 오브젝트마다 다르고, 그 오브젝트 특유의 중첩된 필드를 포함한다.

# reference
* [쿠버네티스 오브젝트 이해하기](https://kubernetes.io/ko/docs/concepts/overview/working-with-objects/kubernetes-objects/)
* [워크로드](https://kubernetes.io/ko/docs/concepts/workloads/)
* [리소스 관리](https://kubernetes.io/ko/docs/concepts/cluster-administration/manage-deployment/)
