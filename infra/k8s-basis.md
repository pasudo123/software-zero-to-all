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
$ kubectl describe pods my-nginx-pod
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

