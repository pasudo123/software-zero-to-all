## service type (NodePort, ClusterIP, LoadBalancer)

## NodePort
* spec.ports[].nodePort : 노드 외부에 노출하는 포트 사용자가 실제 맞닥트리는 포트
    * node ip 가 192.0.0.1 이면 사용자는 192.0.0.1:{nodePort} 로 요청을 보내야 한다.
* spec.ports[].port : 노드 내부에 들어온 다음, nodePort 에 매핑된 port
* spec.ports[].targetPort : 노드 내부에 들어온 포트가 특정 pod 에 연결되기 위해 타겟이 되는 포트를 선정하는데 그 해당이 되는 포트
* service 가 어느 파드와 연결될지 결정하기 위해선, spec.selector 의 값이 중요하다.