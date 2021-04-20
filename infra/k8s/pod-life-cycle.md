# pod 의 라이프사이클
* 파드는 정의된 라이프사이클을 따른다.
* `Pending` 단계에서 시작하여 기본 컨테이너 중 적어도 하나 이상 OK 로 시작하면 `Running` 단계를 통과한다.
* 이후에 파드의 컨테이너가 실패로 종료되었는지 여부에 따라 `Succeeded` 또는 `Failed` 단계로 이동한다.
* 파드가 실행되는 동안에, `kubelet`(클러스터의 각 노드에서 실행되는 에이전트) 은 일종의 오류를 처리하기 위해 컨테이너를 다시 시작할 수 있다.
* 파드 내에서 쿠버네티스는 다양한 컨테이너 상태를 추적하고 파드를 다시 정상 상태로 만들기 위하여 취할 조치를 결정한다.



## reference
* [파드 라이프사이클](https://kubernetes.io/ko/docs/concepts/workloads/pods/pod-lifecycle/#%ED%8C%8C%EB%93%9C%EC%9D%98-%EB%8B%A8%EA%B3%84)
