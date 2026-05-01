## namespace
리소스를 논리적으로 구분한다.
네임스페이스의 리소스들은 논리적으로 구분되었을 뿐, 실제 물리적으로 구분되어있진 않다.

## namespace 를 만드는 방법
* (1) 단순 namespace.yaml 파일을 통해 apiVersion, kind, metadata 를 선언해서 kubectl apply -f 형태로 하는 방법
* (2) 특정 클러스터 내에 진입해서 kubectl create ns {namespace-name} 을 통해 바로 생성하는 방법

## namespace 의 서비스에 접근하고 싶다면?
기본적으로 같은 네임스페이스 내의 서비스에 접근을 할 수 있지만,   
다른 네임스페이스에 존재하는 서비스에 접근하고자 하는 경우가 있을 수 있다.   
아래와 같이 작성하면 접근할 수 있다.
{service-name}.{namespace-name}.svc.cluster.local 형태로 접근할 수 있다.  
> 만약 dev namespace 에 db-service 라는 svc 가 있다면 
> dev.db-service.svc.cluster.local 로 접근하면 된다.

## 관련 명령어
* kubectl get pods --namespace={namespace-name}
* kubectl get pods -n {namespace-name}
* kubectl get pods --all-namespaces
* kubectl get ns
* kubectl config set-context $(kubectl config current-context) --namespace={ns name}
    * 현재 쉘 환경을 특정 네임스페이스로 변경하기 위함 : 원래는 default 변경해서 pod/svc/deploy/... 등을 조회할 수 있다. 