# kubectl commands
k8s 명령어 정리

### pod 를 조회한다.
```shell
kubectl get pods

// pod 를 조회한다.
kubectl get pods -o wide

// pod 목록 조회시, 타임스탬프로 정렬해서 조회한다.
kubectl get pods --sort-by=.metadata.creationTimestamp

// -l 옵션을 주어 셀렉터 필터링을 수행한다.
kubectl get pods -l app={label-app-name}

// pod 를 상세하게 조회한다.
kubectl describe pods {pod-name}
```

### deployments 를 조회한다.
```shell
kubectl get deployments
kubectl get deploy
```

### replicasets 을 조회한다.
```shell
kubectl get replicasets
kubectl get rs
```

### 인그레스 명령어
```shell
// 인그레스를 조회한다.
kubectl get ingress

// 인그레스를 상세 조회한다.
kubectl describe ingress {ingress-name}
```

### k8s 클러스터 내 노드를 조회한다.
```shell
// k8s 클러스터 내의 노드 구성을 조회한다.
kubectl get nodes
```

### 특정 오브젝트에 대한 로그를 확인한다.
```shell
kubectl logs {object-name}

// 로그를 실시간으로 보고 싶다면, 아래와 같은 명령어를 입력한다.
kubectl logs -f

// 특정 오브젝트의 앱 레이블에 대한 로그를 보고 싶다면 아래와 같이 입력한다.
kubectl logs -f -l app={app-name}

// 특정 오브젝트의 앱 레이블에 대한 로그를 15 라인만큼 실시간으로 보고 싶다면 아래와 같이 입력한다.
kubectl logs -f --tail=15 -l app={app-name}
```

### 크론잡을 조회한다.
```shell
kubectl get cronjob
```

### k8s 콘텍스트 명령어
context : kubectl 환경을 바꿔가면서 클러스터를 관리할 수 있는 콘텍스트라는 기능이 존재한다.
```shell
// 현재 선택한 콘텍스트 조회
kubectl config current-context

// 콘텍스트를 조회
kubectl config get-contexts

// 특정한 콘텍스트를 선택
kubectl config use-context {context-name}
```

### 서비스를 조회한다.
```shell
// namespace 내 모든 서비스 조회
kubectl get services
```

### 오브젝트 또는 워크로드 리소스 삭제
```shell
// yaml 내용에 선언된 오브젝트 및 리소스 삭제
kubectl delete -f {file-name.yaml or file-name.json}

// pod 삭제
kubectl delete pod {pod-name}
```

### k8s 리소스를 생성 또는 패치
```shell
// filename yaml 파일에 적힌 k8s 리소스를 클러스터 내에서 새롭게 생성하거나 패치한다.
kubectl apply -f {file-name}

// customize 가 포함된 k8s 리소스를 생성하거나 패치한다.
kubectl apply -k {directory}
```

### k8s 서비스 목록을 조회
```shell
kubectl get service
kubectl get svc
```

### 파드에 접근한다.
```shell
kubectl exec

// 특정 파드에 접근하는 명령어
// ${POD_NAME} 에는 조회한 파드의 이름을 넣는다.
// 위의 명령어는 -- 이후에 입력한 명령어를 단 한번 실행하고 종료된다.
kubectl exec ${POD_NAME} -- hostname

// -i 와 -t 그리고 bash 를 사용하여 해당 파드 내에 접속한다.
// 하지만 해당 파드가 bash 또는 zsh 와 같은 쉘을 제공해주어야 한다.
kubectl exec -it ${POD_NAME} -- bash 또는
kubectl exec -it ${POD_NAME} -- /bin/bash
```
