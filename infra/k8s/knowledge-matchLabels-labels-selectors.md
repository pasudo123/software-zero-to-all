# matchLabels & labels & selectors 이해하기
reference + 강의 들은 것 기반하여 작성


## matchLabels : deployment 리소스에게 적용될 pod 가 무엇인지 알려준다.
아래의 yaml 파일을 기준으로 설명한다.
```yaml
kind: Deployment
...
metadata:
  name: nginx
  labels:
    app: nginx
    tier: backend
spec:
  selector:
    matchLabels:
      app: nginx
  template:
    metadata:
      labels:
        app: nginx
        tier: backend
...
```

첫번째 `metadata` 는 deployment 리소스 그 자체에 대한 정보이다.
```yaml
kind: Deployment
...
metadata:
  name: nginx
  labels:               ## deployment 의 레이블 정보이다.
    app: nginx
    tier: backend
...
```

두번째 `selector` 는 pod 를 deployment 에 적용하기 위한 deployment 리소스 정보이다.
```yaml
spec:
  selector:
    matchLabels:
      app: nginx
  template:             ## template 은 podTemplate 을 의미한다. 
    metadata:     
      labels:           ## pod 의 레이블 정보이다. : deployment 가 배포하는 pod 의 레이블 정보이다.
        app: nginx      
        tier: backend
```
* deployment 에게 현재 배포하는 pod 가 무엇인지 알려줄 필요가 있다.
* 따라서 `spec.selector.matchLabels.app` 와 `spec.template.metadata.labels.app` `라벨이 일치` 되어야 한다. 그래야 k8s 기능을 온전히 사용할 수 있다.
  * `spec.selector.matchLabels` 는 replicatSet 이 관리하는 Pod 를 의미한다.
  * `spec.template.metadata.labels` 은 replicatSet 이 관리하는 Pod 을 만들 때, 해당 레이블에 할당함을 의미한다.
  * 여튼 두 값이 일치되어야 한다.

```
레플리카셋은 파드와 연결되어 있지 않다.
둘은 서로 느슨한 결합을 맺고 있다.
둘은 라벨 셀렉터 에 의해서 정의되고 있다.

위의 내용에서는 레플리카셋 정의과 파드템플릿 정의를 따로 구분해서 사용하고 있다.
```


## service 와 pod 가 연결안되는 경우가 있다.
* 실무에서 겪었음.
* 가령 service 리소스의 yml 파일에 `spec.selector.app` 항목이 있다.
  * 해당 항목은 deployment.yml 파일의 `spec.template.metadata.labels.app` 과 일치시켜주어야 한다.
  * service 와 deployment 를 연결하는 것이 아닌 service 와 pod 이 연결되어야 한다. 그래서 deployment 쪽의 상위 metadata.labels.app 와는 구분해서 이해하고 있어야 한다.


## labels 관련 명령어
* kubectl get pods --selector=app=App1
  * App1 이라는 app Labels 를 가진 팟 조회
* kubectl get all --selector=env=prod
  * 쿠버네티스 클러스터 내 모든 리소스에서 labels 가 env=prod 로 설정된 것 조회
* kubectl get all --selector=env=prod,tier=frontend 
  * 쿠버네티스 클러스터 내 모든 리소스에서 labels 가 env=prod & tier=frontend 로 설정된 것 조회


## reference
* https://medium.com/@zwhitchcox/matchlabels-labels-and-selectors-explained-in-detail-for-beginners-d421bdd05362
