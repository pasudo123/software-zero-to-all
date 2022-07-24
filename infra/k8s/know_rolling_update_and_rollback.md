## rolling update, rollbacks

## deployment rollout 의 상태를 살핀다.
kubectl rollout status deployment/{deployment-name}

## 현재 리비전을 포함한 deployment 이력확인
kubectl rollout history deployment/{deployment-name}

## deployment strategy
* recreate
    * 애플리케이션이 순간적으로 전체 셧다운된다.
* rolling update
    * deployment resources 의 기본 전략이다.
* > 두 내용은 kubectl describe 명령어를 통해서 보면 차이가 있다.
    * StragetegyType 와 Events 내용이 다르다.

## under the hoods
* k8s 의 deployments 는 새로운 replicaset 을 만든다.
* 그리고 신규로 만들어진 replicaset 에 pod 를 올리고, 구 replicaset 에 있는 pod 를 죽인다.
    * kubectl get rs 를 통해 보면, replicaset 이 두 개 만들어진 것을 확인할 수 있다.

## rollback 을 하고자 한다면,
kubectl rollout undo deployment/{deployment-name} 을 통해서 롤백을 수행

## 관련 학습한 명령어
```shell
// 이미지 변경
& kubectl set image {container-name}={image-name}
```