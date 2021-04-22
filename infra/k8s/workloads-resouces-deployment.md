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

nginx-deployment-66b6c48dd5   3         3         3       108s
// DESIRED : 
```
