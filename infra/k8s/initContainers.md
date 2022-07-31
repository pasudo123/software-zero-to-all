# 초기화 컨테이너 (initContainers)

## 개요
* 파드가 띄어지기 이전에, 가장 먼저 실행되는 컨테이너의 한 종류. (특수한 컨테이너)
* 파드스펙에 나란히 명시할 수 있다. 
  * `spec.initContainers` 
  * `spec.containers`

## 초기화 컨테이너의 특징
* 항상 완료를 목적으로 실행된다.
* 각 초기화 컨테이너는 다음에 있을 초기화 컨테이너가 실행되기 이전에 성공적으로 완료되어야 한다.
* 초기화 컨테이너는 `lifecycle`, `readinessProbe` 는 존재하지 않는다.
* 만약 파드의 초기화 컨테이너가 실패하면, kubelet 은 초기화 컨테이너가 성공할 때까지, 반복적으로 재시작을 수행한다.
  * 그러나, pod 의 `restartPolicy` 가 `Never` 로 설정되어 있는 경우 해당 파드의 초기화 컨테이너가 실패하면 쿠버네티스는 전체 파드가 실패했다고 간주한다.

## 형태
```yaml
apiVersion: v1
kind: Pod
metadata:
  name: myapp-pod
  labels:
    app: myapp
spec:
  containers:
  - name: myapp-container
    image: busybox:1.28
    command: ['sh', '-c', 'echo The app is running! && sleep 3600']
  initContainers:
  - name: init-myservice
    image: busybox
    command: ['sh', '-c', 'git clone <some-repository-that-will-be-used-by-application> ; done;']
```

# reference
* https://kubernetes.io/ko/docs/concepts/workloads/pods/init-containers/
