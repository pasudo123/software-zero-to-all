# k8s yaml 작성 이해하기

## 이미지
컨테이너 이미지는 애플리케이션과 모든 소프트웨어 의존성을 캡슐화하는 바이너리 데이터를 나타낸다.    
컨테이너 이미지는 독립적으로 실행할 수 있고 런타임 환경에 대해 잘 정의된 가정을 만드는 실행 가능한 소프트웨어 번들이다.   
일반적으로 파드에서 참조하기 전에 애플리케이션의 컨테이너 이미지를 생성해서 레지스트리로 푸시한다.

### 이미지 업데이트
디플로이먼트, 스테이트풀셋, 파드 또는 파드 템플릿은 포함하는 다른 오브젝트를 처음 만들 때,    
특별히 명시하지 않은 경우 기본적으로 해당 파드에 있는 모든 컨테이너의 풀(pull) 정책은 `IfNotPresent` 로 설정된다.
   
이미지의 강제 풀을 원한다면,
  * 컨테이너의 `imagePullPolicy` 를 `Always` 로 설정

## 파드 컨테이너의 리소스 요청 제한
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



## reference
* https://kubernetes.io/ko/docs/concepts/containers/images/
* https://kubernetes.io/ko/docs/concepts/configuration/manage-resources-containers/
* https://www.mirantis.com/blog/introduction-to-yaml-creating-a-kubernetes-deployment/
