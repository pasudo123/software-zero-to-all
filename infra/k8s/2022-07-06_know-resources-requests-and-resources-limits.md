## resources.requests & resources.limits
* `requests` 는 pod 의 리소스를 최소 보장할 cpu/memory 를 설정한다. 스케줄러가 노드를 고를 때 사용한다.
* `limits` 는 pod 의 리소스를 최대 제한할 cpu/memory 를 설정한다. kubelet 과 컨테이너 런타임이 커널 기능으로 강제한다.
* yaml 설정
    * spec.containers[].resources.requests.memory
    * spec.containers[].resources.limits.memory
    * spec.containers[].resources.requests.cpu
    * spec.containers[].resources.limits.cpu

## pod 가 node 에 스케줄링될 때,
* pod 의 resources.requests 만큼의 여유가 있는 노드에 스케줄링된다.
* pod 를 할당할 때, 사용되는 자원 할당기준은 `resources.requests` 가 된다. 
* pod 가 `pending` 상태로 되는 경우
    * node 에 가용가능한 memory 와 pod 의 `resources.requests.memory` 를 비교, pod 의 `요청 메모리` 가 크다면 pod 의 상태는 `pending` 이 된다.
    * node 에 가용가능한 cpu 와 pod 의 `resources.requests.cpu` 를 비교, pod 의 `요청 cpu` 가 크다면 pod 의 상태는 `pending` 이 된다.

## limit 을 초과하면?
* 컨테이너가 `limits.cpu` 에 가까워지면 CPU throttling 이 발생한다.
    * 앱의 퍼포먼스는 떨어질 수 있다. 
    * CPU limit 은 hard limit 이며, 컨테이너가 limit 보다 많은 CPU 시간을 계속 사용할 수 없도록 제한된다.
* 컨테이너가 `limits.memory` 를 초과해서 사용하면 커널의 OOM kill 대상이 될 수 있다.
    * 메모리 limit 은 반응적으로 적용되므로 초과 즉시 항상 종료되는 것은 아니지만, 메모리 압박이 감지되면 종료될 수 있다.
    * 종료된 컨테이너는 restartPolicy 에 따라 재시작될 수 있다.
* 이 경우 `lastState.terminated.reason` 또는 이벤트에서 `OOMKilled` 를 확인할 수 있다.
* https://kubernetes.io/docs/tasks/configure-pod-container/assign-memory-resource/#exceed-a-container-s-memory-limit

## 상태를 보려면,
* `kubectl describe pods {pod-name}` 으로 lastState 값을 확인하면 된다.

## best practice
* [구글공식링크](https://cloud.google.com/blog/products/containers-kubernetes/kubernetes-best-practices-resource-requests-and-limits)
   
애플리케이션이 다중코어를 쓰지 않는다면 cpu 는 1000m 으로 잡고 스케일아웃하는 편이 좋다.
```shell
Unless your app is specifically designed to take advantage of multiple cores 
(scientific computing and some databases come to mind), 
it is usually a best practice to keep the CPU request at ‘1’ or below, 
and run more replicas to scale it out. This gives the system more flexibility and reliability.
```

## reference
* [Kubernetes docs - Resource Management for Pods and Containers](https://kubernetes.io/docs/concepts/configuration/manage-resources-containers/)
