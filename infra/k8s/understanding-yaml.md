# k8s yaml 작성 이해하기

## 🟠 이미지
컨테이너 이미지는 애플리케이션과 모든 소프트웨어 의존성을 캡슐화하는 바이너리 데이터를 나타낸다.    
컨테이너 이미지는 독립적으로 실행할 수 있고 런타임 환경에 대해 잘 정의된 가정을 만드는 실행 가능한 소프트웨어 번들이다.   
일반적으로 파드에서 참조하기 전에 애플리케이션의 컨테이너 이미지를 생성해서 레지스트리로 푸시한다.

### 이미지 업데이트
디플로이먼트, 스테이트풀셋, 파드 또는 파드 템플릿은 포함하는 다른 오브젝트를 처음 만들 때,    
특별히 명시하지 않은 경우 기본적으로 해당 파드에 있는 모든 컨테이너의 풀(pull) 정책은 `IfNotPresent` 로 설정된다.
   
`이미지의 강제 풀을 원한다면,`
  * 컨테이너의 `imagePullPolicy` 를 `Always` 로 설정

## 🟠 파드 컨테이너의 리소스 요청 제한
파드의 각 컨테이너는 다음 중 하나 이상을 지정할 수 있다.

* spec.containers[].resources.requests.cpu
* spec.containers[].resources.requests.memory
* ... (그 외)

요청과 제한은 개별 컨테이너에서만 지정할 수 있지만, 파드 리소스 요청 및 제한에 대해 이야기하는 것이 편리하다.   
특정 리소스 타입에 대한 파드 리소스 요청/제한은 파드의 각 컨테이너에 대한 해당 타입의 리소스 요청/제한의 합이다.

### 쿠버네티스의 리소스 단위
#### CPU 의미
* CPU 리소스에 대한 제한 및 요청은 cpu 단위로 측정된다. 쿠버네티스의 CPU 1개는 클라우드 공급자 vCPU/Core 1개와 베어메탈 인텔 프로세서에서의 1개 [하이퍼스레드](https://www.intel.co.kr/content/www/kr/ko/gaming/resources/hyper-threading.html)에 해당한다.
* 분수 요청이 허용된다. 0.5 의 `spec.containers[].resources.requests.cpu` 요청을 가진 컨테이너는 CPU 1개를 요구하는 컨테이너 절반만큼 CPU 를 보장한다.
  * 0.1 이라는 표현은 백 밀리 CPU 로 읽을 수 있는 100m 표현과 동일하다.

#### 메모리 의미
* `memory` 에 대한 제한 및 요청은 `바이트단위` 로 측정된다.
* E, P, T, G, M, K 와 같은 접미사 중 하나를 사용하여 메모리를 일반 정수 또는 고정 소수점 숫자로 표현할 수 있다.
  * Ei, Pi, Ti, Gi, Mi, Ki 와 같은 2의 거듭제곱을 사용할 수도 있다.

다음은 동일한 값의 메모리를 나타낸다.
> 128974848, 129e6, 129M, 123Mi

## 🟠 파드를 클러스터에 노출
* ports[].containerPort 를 사용을 하는걸로 보인다.

## 🟠 컨테이너 재시작 정책
* 파드의 `spec` 에는 `restartPolicy` 필드가 있다.
   * 사용가능한 값은 `Always`, `OnFailure`, `Never` 이고 기본값은 `Always` 이다.
   * `restartPolicy` 는 모든 컨테이너에 적용된다.
   * `restartPolicy` 는 동일한 노드에서 kubelet 에 의한 컨테이너 재시작만을 의마한다.
      * 파드의 컨테이너가 종료된 후, kubelet 은 5분으로 제한되는 지수 백오프 지연(10초, 20초, 40초, ...) 으로 컨테이너를 재시작한다.
      * 컨테이너가 10분동안 아무런 문제없이 실행되면, kubelet 은 해당 컨테이너의 재시작 백오프 타이머를 재설정한다.

## 🟠 컨테이너가 요청을 처리할 준비가 되었는지 확인 (http prove)
* 프로브는 컨테이너에서 kubelet 에 의해 주기적으로 수행되는 진단이다. 
   * 진단을 수행하기 위해서는 kubelet 은 컨테이너에 의해서 구현된 핸들러를 호출한다. (핸들러에는 `3가지 타입` 이 존재한다.)
      * ExecAction
      * TCPSocketAction
      * HTTPGetAction
         * 지정한 포트 및 경로에서 컨테이너 IP 주소에 대한 GET 요청을 수행한다.
         * 응답의 상태 코드가 200 이상 400 미만이면 성공한 것으로 간주한다.
* `readinessProve`
   * 프로브가 성공한 경우에만 파드에 트래픽 전송을 시작하려는 경우 이용
   * 파드가 트래픽을 받지 않는 상태에서 시작되고 이후에 프로브가 성공하기 시작한 이후에만 트래픽을 받는다는 것을 명시한다.
   * 만약 컨테이너가 대량의 데이터, 설정파일들 또는 시동중인 마이그레이션을 처리해야 한다면 준비성 프로브를 지정하여야 한다.
```yaml
readinessProbe:
 failureThreshold: 2    # 2번의 프로브 실패를 설정한다. (periodSeconds 가 2초니깐 4초동안 프로브가 2번 실패하면 더 이상의 트래픽을 보내지 않음을 말한다.)
 httpGet:
   path: /health        # http get 경로
   port: 3000
   scheme: HTTP
 initialDelaySeconds: 2 # 첫번째 프로브이전에 컨테이너가 작성된 후 2초를 대기한다. 이후 periodSeconds 초 만큼 확인한다.
 periodSeconds: 2       # 2초 주기로 진단함을 의미.
 successThreshold: 1    # 실패 후 프로브가 성공해야 하는 횟수를 의미한다.
 timeoutSeconds: 1      # 프로브 제한시간을 의미한다.
```

### failureThreshold 개인적인 궁금증
probe 가 `failureThreshold` 설정한만큼 실패가 된다면 이후에는 어떻게 될 것인가?   
프로브 정책에 따라 달라질 수 있다.

* `livenessProbe` : 활성화 프로브
   *  컨테이너가 동작 중인지 여부를 나타낸다. 만약 활성화 프로브에 실패한다면, kubelet 은 `컨테이너를 죽이고`, 해당 컨테이너는 재시작 정책의 대상이 된다.
   *  만약 컨테이너가 활성프로브를 제공하지 않는 상태라고 한다면, 기본 상태는 `Success` 가 된다.
* `readinessProbe` : 준비성 프로브
   * 컨테이너가 요청을 처리할 준비가 되었는지 여부를 나타낸다.
   * 만약 준비성 프로브가 실패한다면, 엔드포인트 컨트롤러는 파드에 연관된 모든 서비스들의 엔드포인트에서 파드의 IP 주소를 제거한다.
   * 준비성 프로브의 초기지연 이전의 기본상태는 `Failure` 이다.
* `startupProbe` : 스타트업 프로브
   * 컨테이너 내의 애플리케이션이 시작되었는지를 나타낸다.
   * 스타트업 프로브가 주어진 경우, 성공할 때까지 다른 나머지 프로브는 활성화되지 않는다.
   * 만약 스타트업 프로브가 실패하면, kubelet 이 컨테이너를 죽이고, 컨테이너는 재시작 정책에 따라 처리된다.

## strategy 관련

deployments yaml 명세
```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  labels:
    app: helloworld
  name: helloworld
spec:
  replicas: 3
  minReadySeconds: 0
  selector:
    matchLabels:
      app: helloworld
  strategy:
    rollingUpdate:
      maxSurge: 25%
      maxUnavailable: 1
    type: RollingUpdate
```

## reference
* https://kubernetes.io/ko/docs/concepts/containers/images/
* https://kubernetes.io/ko/docs/concepts/configuration/manage-resources-containers/
* https://www.mirantis.com/blog/introduction-to-yaml-creating-a-kubernetes-deployment/
* [k8s pod 를 진단하는 서비스](https://medium.com/finda-tech/kubernetes-pod%EC%9D%98-%EC%A7%84%EB%8B%A8%EC%9D%84-%EB%8B%B4%EB%8B%B9%ED%95%98%EB%8A%94-%EC%84%9C%EB%B9%84%EC%8A%A4-probe-7872cec9e568)
* [kubernetes-graceful-shutdown-example](https://github.com/RisingStack/kubernetes-graceful-shutdown-example/blob/master/kubernetes/deployment.yaml)
* [재시작 정책](https://kubernetes.io/ko/docs/concepts/workloads/pods/pod-lifecycle/#restart-policy)
