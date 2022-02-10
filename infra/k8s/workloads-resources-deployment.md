# 디플로이먼트 (deployment)
디플로이먼트는 파드와 레플리카셋에 대한 선언적 업데이트를 제공한다.   
   
* 디플로이먼트에서 의도하는 상태를 설명
* 디플로이먼트 컨트롤러는 현재 상태에서 의도하는 상태로 비율을 조정하며 변경
* 새 레플리카셋을 생성하는 디플로이먼트를 정의하거나 기존 디플로이먼트를 제거하고, 모든 리소스를 새 디플로이먼트에 적용할 수 있다.

## 유스케이스 : 레플리카셋을 롤아웃할 디플로이먼트
레플리카셋은 백그라운드에서 파드를 생성한다. 롤아웃 상태를 체크해서 성공여부를 확인한다.

```yaml
apiVersion: apps/v1
kind: Deployment
metadata: // 리소스에 대한 부가적인 정보들을 입력한다.
  name: nginx-deployment  // 디플로이먼트 이름을 설정한다.
  labels:
    app: nginx
spec:
  replicas: 3 // spec.replicas 필드에 따라 디플로이먼트는 3개의 레플리카 파드를 생성한다.
  selector: // spec.selector 디플로이먼트가 관리할 파드를 찾는 방법을 정의한다. 여기선 파드 템플릿(파드를 생성하기 위한 명세, spec) 에 정의된 app: nginx 를 선택한다.
    matchLabels:
      app: nginx
  template:
    metadata:
      labels:
        app: nginx   // 파드 템플릿에 명시된 label 이다.
    spec:            // 파드 템플릿의 사양 또는 .template.spec 필드는 파드가 도커허브의 nginx 1.14.2 버전 이미지를 실행하는 nginx 컨테이너 1개를 실행하는 것을 나타낸다.
      containers:
      - name: nginx  // 컨테이너 한 개를 생성하고, .spec.template.spec.container[0].name 필드를 사용하여 nginx 이름을 붙인다.
        image: nginx:1.14.2
        ports:
        - containerPort: 80
```
* 디플로이먼트에 파드 템플릿 레이블과 셀렉터를 `반드시` 명시해주어야 한다. 위의 예제에서 `app: nginx` 가 그러하다 label 을 일치시키는 것이다.

실행시켜본다.   
```shell
❯ kubectl apply -f deployment01.yaml
deployment.apps/nginx-deployment created
```

```shell
❯ kubectl get deployments
NAME               READY   UP-TO-DATE   AVAILABLE   AGE
nginx-deployment   3/3     3            3           12s

// NAME : metadata.name 의 항목이다.
// READY : 사용자가 사용할 수 있는 애플리케이션의 레플리카의 수를 표시 ready/desired (spec.replicas 가 3이라서 3개가 띄어짐)
// AVAILABLE : 사용자가 사용할 수 있는 애플리케이션 레플리카의 수를 표시
// AGE : 애플리케이션의 실행된 시간을 표시 (12초 실행됨)
```

```shell
❯ kubectl get replicasets
NAME                          DESIRED   CURRENT   READY   AGE
nginx-deployment-66b6c48dd5   3         3         3       108s

// NAME : 네임스페이스에 있는 레플리카셋 이름의 목록
// DESIRED : 디플로이먼트의 생성 시, 정의한 의도에 따른 애플리케이션 레플리카의 수를 표시한다. (의도한 상태)
// CURRENT : 현재 실행 중인 레플리카의 수를 표시한다.
// READY : 사용자가 사용할 수 있는 애플리케이션의 레플리카의 수를 표시한다.
// AGE : 애플리케이션이 실행한 시간을 표시한다.
```

각 파드에 자동으로 생성된 레이블을 볼 수 있다.   
만들어진 레플리카셋은 실행 중인 3개의 nginx 파드를 보장한다.
```shell
❯ kubectl get pods --show-labels
NAME                                READY   STATUS    RESTARTS   AGE     LABELS
nginx-deployment-66b6c48dd5-7z2dc   1/1     Running   0          2m14s   app=nginx,pod-template-hash=66b6c48dd5
nginx-deployment-66b6c48dd5-nrv9n   1/1     Running   0          2m14s   app=nginx,pod-template-hash=66b6c48dd5
nginx-deployment-66b6c48dd5-vgvhc   1/1     Running   0          2m14s   app=nginx,pod-template-hash=66b6c48dd5
```
* pod-template-hash 레이블은 디플로이먼트 컨트롤러에 의해서 디플로이먼트가 생성 또는 채택한 모든 레플리카셋에 추가된다.
* 해당 레이블은 디플로이먼트의 자식 레플리카셋이 겹치지 않도록 보장한다. 
   * 레플리카셋의 PodTemplate 을 해싱하고, 해시 결과를 레플리카셋 셀렉터, 파드 템플릿 레이블 및 레플리카셋이 가질 수 있는 기존의 모든 파드에 레이블 값으로 추가해서 사용하도록 생성한다.

### deployment 롤백하기
__디플로이먼트를 이전버전으로 롤백한다__
```
$ kubectl rollout undo deployment/{deployment-name}
```

__디플로이먼트를 특정한 버전으로 롤백한다.__
```
$ kubectl rollout undo deployment/{deployment-name} --to-revision={revision-number}
```

__특정한 버전으로 롤백하기 위한 리비전 번호를 살펴보려고 한다면 아래와 같이 수행__
```
$ kubectl rollout history deployment/{deployment-name}
```

### deployment 재실행하기
__deployment 재실행하기__
```
$ kubectl rollout restart deployment/{deployment-name}
```

### deployment 리소스 스케일링 
__리소스 개수 조정__
```
$ kubectl scale --replicas={변경할 개수} deployment/{deployment-name}
```


### 뒤에는 추가적인 공부가 더 필요하다.

## reference
* https://kubernetes.io/ko/docs/concepts/workloads/controllers/deployment/
* https://kubernetes.io/ko/docs/reference/kubectl/cheatsheet/ : kubectl 자주 사용하는 명령어
