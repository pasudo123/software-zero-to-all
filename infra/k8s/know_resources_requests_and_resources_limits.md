## resources.requests & resources.limits
* 노드내에서 pods 의 리소스를 최소 보장할 cpu/memory 를 설정할 수 있다.
* 노드내에서 pods 의 리소스를 최대 제한할 cpu/memory 를 설정할 수 있다.
* yaml 설정
    * spec.containers.resources.requests.memory
    * spec.containers.resources.limits.memory
    * spec.containers.resources.requests.cpu
    * spec.containers.resources.limits.cpu

## pod 가 node 에 스케줄링될 때,
* pod 의 resources.requests 만큼의 여유가 있는 노드에 스케줄링된다.
* pod 를 할당할 때, 사용되는 자원 할당기준은 `resources.requests` 가 된다. 
* pod 가 `pending` 상태로 되는 경우
    * node 에 가용가능한 memory 와 pod 의 `resources.requests.memory` 를 비교, pod 의 `요청 메모리` 가 크다면 pod 의 상태는 `pending` 이 된다.
    * node 에 가용가능한 cpu 와 pod 의 `resources.requests.cpu` 를 비교, pod 의 `요청 cpu` 가 크다면 pod 의 상태는 `pending` 이 된다.

## limit 을 초과하면?
* requests.cpu 가 limits.cpu 를 초과하는 경우 k8s throttle 은 limit 만큼 request 가 초과되지 않도록 조절한다.
    * 앱의 퍼포먼스는 떨어질 수 있다. 
    * liveness health check 를 통해서 문제 발생 시, k8s 가 자체적으로 pod 을 restart 할지 결정한다.
* requests.memory 이 limits.cpu 를 초과하는 경우 pod 은 종료될 수 있다. -> kubelet 은 해당 pod 을 restart 시킨다.
* pod status OOMKilled 가 나온다.
* https://kubernetes.io/docs/tasks/configure-pod-container/assign-memory-resource/#exceed-a-container-s-memory-limit

## best practice
* [구글공식링크](https://cloud.google.com/blog/products/containers-kubernetes/kubernetes-best-practices-resource-requests-and-limits)
   
애플리케이션이 다중코어를 쓰지 않는다면 cpu 는 1000m 으로 잡고 스케일아웃하는 편이 좋다.
```shell
Unless your app is specifically designed to take advantage of multiple cores 
(scientific computing and some databases come to mind), 
it is usually a best practice to keep the CPU request at ‘1’ or below, 
and run more replicas to scale it out. This gives the system more flexibility and reliability.
```

