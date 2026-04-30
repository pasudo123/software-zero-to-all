## service type (NodePort, ClusterIP, LoadBalancer)

## NodePort
* spec.ports[].nodePort : 노드 외부에 노출하는 포트 사용자가 실제 맞닥트리는 포트
    * node ip 가 192.0.0.1 이면 사용자는 192.0.0.1:{nodePort} 로 요청을 보내야 한다.
* spec.ports[].port : 노드 내부에 들어온 다음, nodePort 에 매핑된 port
* spec.ports[].targetPort : 노드 내부에 들어온 포트가 특정 pod 에 연결되기 위해 타겟이 되는 포트를 선정하는데 그 해당이 되는 포트
* service 가 어느 파드와 연결될지 결정하기 위해선, spec.selector 의 값이 중요하다.

## ClusterIP
* k8s 클러스터 내부에서 팟을 접근할 수 있다.
* service 의 기본타입
* spec.ports[].targetPort : 접근대상이 되는 팟들이 내부적으로 사용하는 포트
* spec.ports[].port : 서비스 IP 에 접근할 때 사용하는 포트
* spec.selector