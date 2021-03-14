# k8s 실습

## nginx pod 를 생성하기 위한 yml 파일 작성
```yml
// nginx-pod.yaml
apiVersion: v1
kind: Pod
metadata:
  name: my-nginx-pod
spec:
  containers:
    - name: my-nginx-container
      image: nginx:latest
      ports:
        - containerPort: 80
          protocol: TCP
```
* `appVersion`
  * yaml 파일에서 정의한 오브젝트의 api version 을 나타낸다. 오브젝트의 종류 및 개발의 성숙도에 따라서 apiVersion 의 설정값이 달라질 수 있다.
* `kind`
  * 리소스의 종류를 나타낸다.
  * 위의 구문에서는 생성하려는 것이 pod 이기 때문에 pod 로 입력되었다.
* `metadata`
  * 라벨, 주석, 이름 등과 같은 리로스의 부가정보들을 입력한다.
  * 위의 경우에서는 name 을 my-nginx-pod 로 설정해두었다.
* `spec`
  * 리소스를 생성하기 위한 자세한 정보를 입력한다.
  * 위의 경우에서는 포드에서 실핼될 컨테이너 정보를 정의하는 containers 항목을 만든 뒤, 하위 항목인 image 에서 사용할 도커 이미지를 지정했다. name 항목에는 컨테이너의 이름을, ports 항목에서는 nginx 컨테이너가 사용할 포트인 80 을 입력했다.

## nginx pod 생성 명령어 작성
```
$ kubectl apply -f nginx-pod.yaml
pod/my-nginx-pod created
```

## pod 의 목록확인
```
// kubectl get {object-name} 을 통하여 특정한 오브젝트 목록을 확인
// 아래의 명령어는 현재 k8s 에 존재하는 파드의 목록을 출력한다.
$ kubectl get pods
NAME           READY   STATUS    RESTARTS   AGE
my-nginx-pod   1/1     Running   0          8m32s
```

## 생성된 nginx pod 에 대한 자세한 리소스 정보를 확인
```
// kubectl describe pods {pod-name}
$ kubectl describe pods my-nginx-pod
Name:         my-nginx-pod
Namespace:    default
Priority:     0
Node:         docker-desktop/192.168.65.3
Start Time:   Sun, 07 Feb 2021 20:16:13 +0900
Labels:       <none>
Annotations:  <none>
Status:       Running
IP:           10.1.0.20
IPs:
  IP:  10.1.0.20
Containers:
  my-nginx-container:
    Container ID:   docker://fb86402dd614dbbdce400b9b25ddb60da12e680990979b3c25499af9cf8e1bae
    Image:          nginx:latest
    Image ID:       docker-pullable://nginx@sha256:10b8cc432d56da8b61b070f4c7d2543a9ed17c2b23010b43af434fd40e2ca4aa
    Port:           80/TCP
    Host Port:      0/TCP
    State:          Running
      Started:      Sun, 07 Feb 2021 20:16:26 +0900
    Ready:          True
    Restart Count:  0
    Environment:    <none>
    Mounts:
      /var/run/secrets/kubernetes.io/serviceaccount from default-token-8rqh4 (ro)
Conditions:
  Type              Status
  Initialized       True
  Ready             True
  ContainersReady   True
  PodScheduled      True
Volumes:
  default-token-8rqh4:
    Type:        Secret (a volume populated by a Secret)
    SecretName:  default-token-8rqh4
    Optional:    false
QoS Class:       BestEffort
Node-Selectors:  <none>
Tolerations:     node.kubernetes.io/not-ready:NoExecute op=Exists for 300s
                 node.kubernetes.io/unreachable:NoExecute op=Exists for 300s
Events:
  Type    Reason     Age   From               Message
  ----    ------     ----  ----               -------
  Normal  Scheduled  10m   default-scheduler  Successfully assigned default/my-nginx-pod to docker-desktop
  Normal  Pulling    10m   kubelet            Pulling image "nginx:latest"
  Normal  Pulled     10m   kubelet            Successfully pulled image "nginx:latest" in 12.0751933s
  Normal  Created    10m   kubelet            Created container my-nginx-container
  Normal  Started    10m   kubelet            Started container my-nginx-container

```

## pod 컨테이너 내부 접근
https://kubernetes.io/docs/tasks/debug-application-cluster/get-shell-running-container/ 참고.
```
$ kubectl exec -it my-nginx-pod -- /bin/bash
root@my-nginx-pod:/#
```

## nginx pod 오브젝트 삭제
```
$ kubectl delete -f nginx-pod.yaml
pod "my-nginx-pod" deleted
```

## nginx replicaset 을 생성하기 위한 yaml 파일 생성
```
apiVersion: apps/v1
kind: ReplicaSet
metadata:
  name: replicaset-nginx
spec:                                 ^
  replica: 3                          |
  selector:                           | 레플리카 셋을 정의한다.
    matchLabels:                      |
      app: my-nginx-pods-label        |
                                      --------------------------------------
  template:                           |
    metadata:                         |
      name: my-nginx-pod              |
      labels:                         |
        app: my-nginx-pods-label      |
    spec:                             | 포드를 정의한다.
      containers:                     |
        - name: nginx                 |
          image: nginx:latest         |
          ports:                      |
            - containerPort: 80       v
```
* `spec.replicas`
  * 동일한 포드를 몇 개 유지시킬 것인지 설정한다.
  * 위의 예시에서는 3개의 포드를 유지시킨다.
* `spec.template` 아래의 내용들
  * 포드를 생성할 때, 사용할 템플릿을 정의한다.
  * 이전에 작성했던 pod 의 spec 과 동일하다.
