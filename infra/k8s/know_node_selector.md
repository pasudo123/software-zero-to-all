## nodeSelector
특정 노드에 팟을 스케줄링 할 수 있다.

## pod configuration 파일에 nodeSelector 설정하기
* spec.nodeSelector 에 key/value 쌍으로 설정할 수 있다.
```yaml
~~
spec:
  nodeSelector:
    size: Large
```

## node 에 label 을 설정하기
* kubectl label nodes {node-name} {label-key}={label-value}
    * ex) kubectl label nodes node01 size=Large

## nodeSelector 의 한계
* 단일 노드에 대해 팟을 스케줄링 할 수 있도록 한다.
* 멀티 노드에 대해 팟을 스케줄링 하기 위해선 어떻게 해야하는가?
    * size Large or Medium
    * `Node Affinity` 가 대안이 될 수 있다.
* 특정 노드만 제외하고 스케줄링 하기 위해선 어떻게 해야하는가?
    * `Node Affinity` 가 대안이 될 수 있다.
