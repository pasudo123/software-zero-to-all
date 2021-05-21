# ingress
클러스터 내의 서비스에 대한 외부접근을 관리하는 API 오브젝트이며, 일반적으로 http 를 관리함.   
인그레스는 부하분산, SSL 종료, 명칭기반의 가상호스팅을 제공한다.

## 용어
* 노드 (Node)
  * 클러스터의 일부이며, 쿠버네티스에 속한 워커머신
* 클러스터 (Cluster)
  * 노드들의 집합, 노드집합은 쿠버네티스에서 관리되는 컨테이너화된 애플리케이션을 실행하는 노드집합을 의미한다.

## 인그레스란?
* 클러스터 외부에서 클러스터의 내부 [서비스](https://kubernetes.io/ko/docs/concepts/services-networking/service/) 로 http/https 경로를 노출한다.   
* 트래픽 `라우팅` 은 인그레스 리소스에 정의된 규칙에 의해서 컨트롤된다.   
* 인그레스 리소스가 작동하려면, 클러스터는 실행중인 [`인그레스 컨트롤러`](https://kubernetes.io/ko/docs/concepts/services-networking/ingress-controllers/) 가 반드시 필요하다.
<img src="../../images/20210522_ingress_diagram.png" />

## reference
* https://kubernetes.io/ko/docs/concepts/services-networking/ingress/
