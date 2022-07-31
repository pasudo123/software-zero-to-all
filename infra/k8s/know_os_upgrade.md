## os upgrade

## os 업그레이드를 위해 여려 명령어가 필요할 수 있다.
* kubectl drain {node-name}
    * 특정 노드에 있는 파드들은 다른 노드들로 옮김
    * 추가로 해당 노드를 스케줄링 제외시킨다. -> cordon 명령어를 같이 쓰는 효과가 있다.
* kubectl cordon {node-name}
    * 해당 노드에 새로운 파드들이 더 이상 스케줄링 되지 않도록 schedule-disabled 시킨다.
* kubectl uncordon {node-name}
    * 해당 노드에 파드들이 스케줄이 될 수 있도록 활성화 한다.

## 데몬셋으로 실행되고 있는 pod 들을 drain 수행하기
* 아래의 명령어 중에서 하나를 선택하여 실행한다.
    * kubectl drain node01 --ignore-daemonsets=true
    * kubectl drain node01 --ignore-daemonsets --force
    * 데몬셋의 경우에는 해당 노드에 계속 띄어지려고 하기 때문에 단순 drain 으로 할 수 없고 별도의 옵션을 주어야 한다.
* ReplicationController || Replicaset 등에 의해서 제어되는 팟들은 drain 이 되지만, 그렇지 않은 팟들은 drain 을 손쉽게 허락하지 않는다.

## reference
* [kubectl userguide : kubectl drain](https://jamesdefabia.github.io/docs/user-guide/kubectl/kubectl_drain/)

