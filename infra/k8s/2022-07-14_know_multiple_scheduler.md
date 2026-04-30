## multiple scheduler

## kube-scheduler
도커 데스크탑에 설치된 kube-scheduler 를 확인하면, 별도 conf 파일이 존재하는 것을 확인할 수 있다.
`kube-scheduler` 는 기본적으로 네임스페이스 `kube-system` 에 있다.

```
$ kubectl get pods kube-scheduler-docker-desktop -n kube-system -o yaml

apiVersion: v1
kind: Pod
metadata:
// 생략 생략 생략
spec:
  containers:
  - command:
    - kube-scheduler
    - --authentication-kubeconfig=/etc/kubernetes/scheduler.conf
    - --authorization-kubeconfig=/etc/kubernetes/scheduler.conf
    - --bind-address=127.0.0.1
    - --kubeconfig=/etc/kubernetes/scheduler.conf
    - --leader-elect=true
    image: k8s.gcr.io/kube-scheduler:v1.24.1
    imagePullPolicy: IfNotPresent
```
* --leader-elect
    * 커스텀 스케줄러는 리더로 선출하지 않을 것이기 때문에 해당 값을 false 로 주어야 한다.
* --lock-object-name
    * 해당 값은 대체되었다. (https://kubernetes.io/docs/reference/command-line-tools-reference/kube-scheduler/)

## scheduler 에 의해서 발생한 이벤트를 확인할 수 있다.
로컬에서 띄었을 때 확인할 수 있다.   
```
$ kubectl get events

LAST SEEN   TYPE      REASON                    OBJECT                MESSAGE
148m        Normal    NodeHasSufficientMemory   node/docker-desktop   Node docker-desktop status is now: NodeHasSufficientMemory
148m        Normal    NodeHasNoDiskPressure     node/docker-desktop   Node docker-desktop status is now: NodeHasNoDiskPressure
148m        Normal    NodeHasSufficientPID      node/docker-desktop   Node docker-desktop status is now: NodeHasSufficientPID
148m        Normal    RegisteredNode            node/docker-desktop   Node docker-desktop event: Registered Node docker-desktop in Controller
148m        Normal    Starting                  node/docker-desktop
56m         Normal    Starting                  node/docker-desktop   Starting kubelet.
// 생략 생략 생략 생략
11m         Normal    NodeHasNoDiskPressure     node/docker-desktop   Node docker-desktop status is now: NodeHasNoDiskPressure
11m         Normal    NodeHasSufficientPID      node/docker-desktop   Node docker-desktop status is now: NodeHasSufficientPID
11m         Normal    NodeAllocatableEnforced   node/docker-desktop   Updated Node Allocatable limit across pods
11m         Normal    Starting                  node/docker-desktop
11m         Normal    RegisteredNode            node/docker-desktop   Node docker-desktop event: Registered Node docker-desktop in Controller
```

## 특정 팟이 특정한 스케줄러에 의해 스케줄링 되고자 하는 경우
* Kind: Pod 로 설정된 yaml 파일에 대해서, spec.schedulerName 에 특정한 스케줄러 이름을 기입하면 된다.

## 언제 쓰는가?

## 참고
