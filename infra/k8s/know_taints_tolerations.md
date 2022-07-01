## taints & tolerations 
taints 는 특정 노드에 설정할 수 있다. 뭔가 찜? 해놓는 느낌이 든다.    
이렇게 해놓은 상태에서 파드는 해당 taints 된 노드에 스케줄링 되지 못한다.    
taints 된 노드가 거부한다. 해당 노드에 팟이 스케줄링 되기 위해선,    
팟을 tolerations 설정을 해야 한다. 그래야 해당 팟은 taints 된 노드에 스케줄링 될 수 있다.

## master node 에는 taint 설정이 있음.
* kubectl describe nodes {master-node-name} 을 통해서 조회할 수 있다.

## node 에 taints 설정
* kubectl `taint` nodes {node-name} key=value:{NoSchedule | NoExecute | PreferNoSchedule }

## node 에 taints 제거
* kubectl `taint` nodes {node-name} key=value:{NoSchedule | NoExecute | PreferNoSchedule }`-`


## pod configuration 파일에 tolerations 설정
* pod configuration file 에서 아래 네개의 값을 채운다.
  * spec.tolerations[].key
  * spec.tolerations[].operator
  * spec.tolerations[].value
  * spec.tolerations[].effect
  * 위 값들을 쌍따옴표로 감쌀 수 있어야 한다.
