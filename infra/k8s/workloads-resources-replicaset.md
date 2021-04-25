# 레플리카셋
레플리카셋의 목적은 레플리카 파드 집합의 실행을 항상 안정적으로 유지하는 것이다.   
이처럼 에플리카셋은 보통 명시된 `동일 파드 개수에 대한 가용성을 보장` 하는데 사용한다.

## 레플리카셋 동작방식
* 레플리카셋을 정의하는 필드
  * 획득가능한 파드를 식별하는 방법이 명시된 셀렉터
  * 유지해야 하는 파드 개수를 명시하는 레플리카 개수
  * 레플리카 수 유지를 위해 생성하는 신규 파드에 대한 데이터를 명시하는 파드 템플릿을 포함

> 그러면 레플리카셋은 필드에 지정된 설정을 충족하기 위해 필요한 만큼의 파드를 만들고 삭제한다.
> 레플리카셋이 새로운 파드를 생성해야할 경우, 명시된 파드 템플릿을 이용한다.

## 레플리카셋을 사용하는 시기
* 레플리카셋은 `항상 지정된 개수의 파드가 실행되도록 보장` 한다.
  * 그러나 디플로이먼트는 레플리카셋을 관리하고 다른 유용한 기능과 함께 파드에 대한 선언적 업데이트를 제공하는 상위 개념이다.
  * 따라서 우리가 사용자 지정 오케스트레이션이 필요하거나 업데이트가 전혀 필요하지 않은 경우라면 레플리카셋을 직접적으로 사용하기보다는 `디플로이먼트를 사용하는 것을 권장` 한다.
* `결론적으로` 우리가 레플리카셋 오브젝트를 직접적으로 조작할 필요가 없음을 뜻한다. 대신 디플로이먼트를 이용하고 사양부분에서 애플리케이션을 정의하면 된다.

```yaml
apiVersion: apps/v1
kind: ReplicaSet
metadata:
  name: frontend
  labels:
    app: guestbook
    tier: frontend
spec:
  # 케이스에 따라 레플리카를 수정한다.
  # 파드의 개수를 3개로 설정한다.
  replicas: 3
  selector:
    matchLabels:
      tier: frontend
  template:
    metadata:
      labels:
        tier: frontend
    spec:
      containers:
      - name: php-redis
        image: gcr.io/google_samples/gb-frontend:v3
```

### kubectl 을 통한 레플리카셋 생성, 조회, 상태확인
#### 생성
```shell
❯ kubectl apply -f frontend.yaml
replicaset.apps/frontend created
```

#### 레플리카셋 조회
```shell
❯ kubectl get rs
NAME       DESIRED   CURRENT   READY   AGE
frontend   3         3         3       35s
```

#### 상태확인
```shell
❯ kubectl describe rs/frontend
Name:         frontend
Namespace:    default
Selector:     tier=frontend
Labels:       app=guestbook
              tier=frontend
Annotations:  <none>
Replicas:     3 current / 3 desired
Pods Status:  3 Running / 0 Waiting / 0 Succeeded / 0 Failed
Pod Template:
  Labels:  tier=frontend
  Containers:
   php-redis:
    Image:        gcr.io/google_samples/gb-frontend:v3
    Port:         <none>
    Host Port:    <none>
    Environment:  <none>
    Mounts:       <none>
  Volumes:        <none>
Events:
  Type    Reason            Age    From                   Message
  ----    ------            ----   ----                   -------
  Normal  SuccessfulCreate  2m34s  replicaset-controller  Created pod: frontend-sbzqr
  Normal  SuccessfulCreate  2m34s  replicaset-controller  Created pod: frontend-k9jhp
  Normal  SuccessfulCreate  2m34s  replicaset-controller  Created pod: frontend-gx2jl
```

#### 파드 조회
```shell
❯ kubectl get pods
NAME             READY   STATUS    RESTARTS   AGE
frontend-gx2jl   1/1     Running   0          26s
frontend-k9jhp   1/1     Running   0          26s
frontend-sbzqr   1/1     Running   0          26s  
```

## 레플리카셋 매니페스트 작성하기
여기서, 매니페스트란 k8s 리소스를 정의하는 파일을 뜻한다.   
레플리카셋은 모든 쿠버네티스 api 오브젝트와 마찬가지로 apiVersion, kind, metadata 필드가 필요하다.   
레플리카셋에 대한 kind 필드의 값은 항상 레플리카셋이다. `쿠버네티스 1.9` 에서 레플리카셋의 kind 에 있는 API version `apps/v1` 은 현재버전이며, 기본으로 활성화 되어있다.
`apps/v1beta2` 

## reference
* https://kubernetes.io/ko/docs/concepts/workloads/controllers/replicaset/
