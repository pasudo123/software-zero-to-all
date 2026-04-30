## nodeAffinity
특정 팟에 노드를 기존 nodeSelector 보다 섬세하게 설정할 수 있다.

## nodeSelector vs nodeAffinity 와 비교
nodeSelctor
```yaml
-- 위에는 생략
spec:
    nodeSelector:
        size: Large
```

nodeAffinity
```yaml
-- 위에는 생략
spec:
    affinity:
        nodeAffinity:
            requiredDuringSchedulingIgnoredDuringExecution:
                nodeSelectorTerms:
                    - matchExpressions:
                        - key: size
                          operator: In
                          values:
                          - Large
```

## Node Affinity Types
#### requiredDuringSchedulingIgnoredDuringExecution
* 팟이 특정 레이블을 가진 노드에만 스케줄링 될 것임을 명시한다. 만약 특정한 레이블을 가진 파드가 없는 경우에는 팟은 스케줄링 되지 못한다.
* `IgnoreDuringExecution` 의 의미는 해당 팟이 특정 레이블링된 노드에 스케줄링 되어 동작하는 경우, 해당 노드의 레이블링이 사라져도 무시하고 그대로 실행함을 의미한다.

#### preferredDuringSchedulingIgnoredDuringExecution
* 팟이 특정 레이블을 가지 노드에만 스케줄링 될 것임을 명시한다. `다만 해당 레이블을 가진 파드가 없는 경우에는 아무노드에서 스케줄링 된다.`
* `IgnoreDuringExecution` 의 의미는 해당 팟이 특정 레이블링된 노드에 스케줄링 되어 동작하는 경우, 해당 노드의 레이블링이 사라져도 무시하고 그대로 실행함을 의미한다.

#### requiredDuringSchedulingRequiredDuringExecution
* requiredDuringSchedulingIgnoredDuringExecution 와 유사하지만, 해당 팟이 떠있는 노드의 레이블이 사라지면 해당 팟은 축출/종료가 된다.

## operator 종류 (내가 알고 있는 것?)
* In
* Exists

## 참고문서
* [노드 어피니티](https://kubernetes.io/ko/docs/concepts/scheduling-eviction/assign-pod-node/#%EB%85%B8%EB%93%9C-%EC%96%B4%ED%94%BC%EB%8B%88%ED%8B%B0)
* [노드 어피니티를 활용해 노드에 파드 할당하기](https://kubernetes.io/ko/docs/tasks/configure-pod-container/assign-pods-nodes-using-node-affinity/)