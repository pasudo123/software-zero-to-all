# 서비스 (service)
파드(`클러스터 내에서 실행중인 컨테이너의 집합`) 집합 중에서 실행중인 애플리케이션을 네트워크 서비스로 노출시키는 추상화된 방법   
k8s 를 사용하면, 익숙하지 않은 서비스 디스커버리 메커니즘을 애플리케이션을 수정할 필요가 없다.   
쿠버네티스는 파드에게 고유한 ip 주소와 파드 집합에 대한 단일 dns 명을 부여하고 그것들 간의 로드밸런싱을 수행할 수 있다. 

## 동기 : 디플로이먼트에 의해 백엔드 파드집합의 정보가 수시로 변경되는데 어떻게 이를 파악할 수 있는가?
k8s 파드는 클러스터의 상태와 일치되도록 생성 및 삭제가 된다. 파드는 `비영구적인` 리소스이다.   
만약 앱 실행을 위하여 Deployment 를 사용한다면, 동적으로 파드를 생성 및 제거할 수 있다.   
   
각각의 파드는 고유한 IP 를 가지고 있지만, 디플로이먼트에서는 한 시점에 실행되는 파드의 집합이 잠시 후 실행되는 파드의 집합과 `다를 수 있다.`   
   
> 위의 경우 클러스터 내 백엔드의 파드집합이, 클러스터 내 프론트엔드 파드집합에 의해서 사용이 되는 경우
> 어떻게 백엔드 파드집합의 정보를 프론트엔드 파드집합에게 전달할 수 있는가? 라고 의문이 제기될 수 있다.

## 서비스 리소스
k8s 에서 서비스는 클러스터 내 파드의 논리적 집합과 그것들에 대해 접근할 수 있는 정책을 정의하는 추상적인 개념이다.   
서비스가 대상하는 파드의 집합은 셀렉터에 의해서 결졍된다.

> 파드는 클러스터 내 프론트엔드와 백엔드 사이의 디커플링을 가능케 한다.

## 서비스 정의
```yaml
apiVersion: v1
kind: Service             # serivce 오브젝트 정의
metadata:       
  name: my-service        # serivce 오브젝트의 `dns 네임` 은 my-service 로 한다.
spec:
  selector:
    app: MyApp            # service 와 연결할 `파드집합의 레이블은 MyApp` 이다. [[service] -> [pods ...]] : service 와 pod 집합을 연결한다.
  ports:
    - protocol: TCP       # service 오브젝트는 TCP 의 파드 9376 포트를 대상으로 한다.
      port: 80            # service 의 80 포트는 파드집합 9376 포트와 연결된다.
      targetPort: 9376
```

> 서비스는 모든 수신 port 를 targetPort 와 매핑할 수 있다.
> 그리고 일반적으로 편의상 port 와 targetPort 의 값은 동일하게 설정된다.


## 가상 IP 와 서비스 프록시
쿠버네티스 모든 노드는 `kube-proxy` 를 실행한다.   
`kube-proxy` 는 ExternalName 이외의 유형의 `서비스` 에 대한 가상 ip 형식을 구현한다.   

서비스 오브젝트의 ExternalName 유형
```yaml
apiVersion: v1
kind: Service
metadata:
  name: my-service
  namespace: prod
spec:
  type: ExternalName  # 서비스 오브젝트의 ExternalName 유형이다.
  externalName: my.database.example.com
```
   
reference 를 참고한다.


## 서비스 퍼블리싱 (ServiceType)
애플리케이션 중 일부 (프론트엔드) 는 서비스를 클러스터 밖에 위치한 외부 IP 에 노출시키고 싶은 경우가 있을 수 있다.   
기본값은 `ClusterIP` 이다.   
* ClusterIP
  * 서비스를 클러스터 내부 ip 에 노출시킨다. 해당 값을 선택하면 클러스터 내에서만 서비스에 도달할 수 있다. 해당 값은 ServiceTypes 의 기본값이다. 

reference 를 참고한다.

## reference
* https://kubernetes.io/ko/docs/concepts/services-networking/service/
* [service 의 port 와 targetPort 의 차이점은 무엇인가?](https://stackoverflow.com/questions/49981601/difference-between-targetport-and-port-in-kubernetes-service-definition)
