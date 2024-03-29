# k8s 오브젝트 관리
`kubectl` 커맨드라인 툴은 쿠버네티스 오브젝트를 생성하고 관리하기 위한 몇가지 상이한 방법을 지원한다.   
`오브젝트` 란 추상화된 집합이며, 쿠버네티스 시스템에서 영속성을 갖는다. (클러스터의 상태를 나타낼 수 있다.)

## 관리기법
1. 명령형 커맨드 (Imperative commands)
2. 명령형 오브젝트 구성 (Imperative object configuration)
3. 선언형 오브젝트 구성 (Declarative object configuration)

## 1. 명령형 커맨드 : Imperative commands
> `개발환경에서 수행하는 것을 권장`   
> 명령형 커맨드를 사용할 경우, 사용자는 클러스터 내 활성 오브젝트를 대상으로 직접 동작시킨다.   
> 사용자는 실행할 작업을 인수 또는 플래그로 `kubectl` 커맨드에 지정한다.   
> 이것은 클러스터에서 `일회성 작업` 을 개시시키거나 동작시키기 위한 추천방법이다.   
> 해당기법은 활성 오브젝트 대상으로 직접적인 영향을 미치기 때문에, 이전 구성에 대한 이력을 제공하지 않는다.

아래는 디플로이먼트 오브젝트를 생성하여 nginx 컨테이너 인스턴스를 구동시킨다.
```terminal
❯ kubectl create deployment nginx --image nginx
deployment.apps/nginx created

❯ kubectl get pods
NAME                     READY   STATUS    RESTARTS   AGE
nginx-6799fc88d8-d6bm5   1/1     Running   0          2m24s

// 이렇게 삭제해도 레플리카셋 때문에 다시 pod이 뜨기 때문에 deployment 오브젝트를 지워주어야 한다.
❯ kubectl delete pods nginx-6799fc88d8-d6bm5
pod "nginx-6799fc88d8-d6bm5" deleted

❯ kubectl delete deployments nginx
deployment.apps "nginx" deleted
```
* 장점
  * 커맨드는 하나의 동작을 나타내는 단어로 표현된다.
  * 커맨드는 클러스터를 수정하기 위해 단 하나의 단계만을 필요로 한다.
* 단점
  * 변경에 대한 감시 추적 (audit trail) 을 제공하지 않는다.
  * 새로운 오브젝트 생성을 위한 템플릿을 제공하지 않는다.

## 2. 명령형 오브젝트 구성 : Imperative object configuration
> `개발환경 및 프로덕션환경에서 수행하는 것을 권장`   
> 명령형 오브젝트 구성에서는 kubectl 커맨드로 작업, 선택적 플래그, 최소 하나의 파일 이름 지정을 한다.   
> 그 파일은 yaml 또는 json 형식으로 오브젝트의 `완전한 정의` 를 포함해야 한다.   

적당한 위치에 nginx.yaml 파일을 아래와 같이 만들어준다. 디플로이먼트 오브젝트를 만들기 위한 구성파일이다.
```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: nginx-deployment
spec:
  selector:
    matchLabels:
      app: nginx
  replicas: 2 # tells deployment to run 2 pods matching the template
  template:
    metadata:
      labels:
        app: nginx
    spec:
      containers:
      - name: nginx
        image: nginx:1.14.2
        ports:
        - containerPort: 80
```

명령을 아래와 같이 실행한다.
```shell
❯ kubectl create -f nginx.yaml
deployment.apps/nginx-deployment created

❯ kubectl get pod
NAME                                READY   STATUS    RESTARTS   AGE
nginx-deployment-66b6c48dd5-d4w46   1/1     Running   0          6m34s
nginx-deployment-66b6c48dd5-scd6r   1/1     Running   0          6m34s

// nginx.yaml 에 지정된 명세를 이용하여 오브젝트를 삭제한다.
❯ kubectl delete -f nginx.yaml
deployment.apps "nginx-deployment" deleted

❯ kubectl get pod
NAME                                READY   STATUS        RESTARTS   AGE
nginx-deployment-66b6c48dd5-d4w46   0/1     Terminating   0          15m
nginx-deployment-66b6c48dd5-scd6r   0/1     Terminating   0          15m
```
* 장점
  * 오브젝트 구성은 git 과 같은 소스 컨트롤 시스템에 보관할 수 있다.
  * 오브젝트 구성은 푸시와 감시 추적(audit trail) 전에 변경사항을 검토하는 것과 같은 프로세스들과 통합할 수 있다.
  * 오브젝트 구성은 새로운 오브젝트 생성을 위한 템플릿을 제공한다. 

## 3. 선언형 오브젝트 구성 (Declarative object configuration)
선언형 오브젝트 구성을 사용할 경우, 사용자는 `로컬에 있는 오브젝트 구성 파일` 을 구동시키지만 사용자는 `파일에서 수행할 작업을 정의` 하지 않는다.   
(내가 이해하기론, 디렉토리 대상으로 실행하기 때문에 그런듯)      
`생성`, `업데이트`, `삭제` 작업은 `kubectl` 에 의해서 오브젝트마다 자동으로 감지된다. 
   
아래와 같은 명령어로 실행한다.
```shell
// configs/ 디렉토리내 모든 구성파일들을 생성 또는 패치를 수행한다.
// 어떤 변경이 일어날지 모르는 경우에는 우선적으로 diff 를 하고나서 적용할 수 있다.
kubectl diff -f configs/
kubectl apply -f configs/
```

아래와 같은 명령어는 디렉토리를 재귀적으로 실행한다.
```shell
kubectl diff -R -f configs/
kubectl apply -R -f configs/
```

## reference
* [쿠버네티스 오브젝트 관리](https://kubernetes.io/ko/docs/concepts/overview/working-with-objects/object-management/)
