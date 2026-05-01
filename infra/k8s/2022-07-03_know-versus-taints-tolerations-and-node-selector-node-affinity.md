## Taints & Tolerations vs NodeAffinity
* Taints & Tolerations 의 경우에는 노드에 taint 설정을 하고, 팟에 tolerations 설정을 하더라도, 해당 팟이 항상 특정한 노드에 스케줄링 되는 것을 보장하지 않는다.
* NodeSelector 혹은 NodeAffinity 의 경우에는 특정 노드에 특정 팟을 할당할 수 있지만, 특정 노드에 설정되지 않은 다른 팟들도 할당될 수 있다.
* 위 두 스케줄링 방식을 결합해서 사용하면 더 효과적으로 특정 노드에 특정 팟이 스케줄링 되고, 다른 팟들은 설정한 팟에 스케줄링 되지 않도록 할 수 있다.