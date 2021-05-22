# matchLabels & labels & selectors 이해하기
reference 를 해석해서 작성되었음

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
* 따라서 `spec.selector.matchLabels.app` 와 `spec.template.metadata.labels.app` 라벨이 일치되어야 한다. 그래야 k8s 기능을 온전히 사용할 수 있다.
  * `spec.selector.matchLabels` 는 replicatSet/Pod 를 의미한다.
  * `spec.template.metadata.labels` 은 replicatSet/Pod 을 만들 때, 해당 레이블에 할당함을 의미한다.
  * 여튼 두 값이 일치되어야 한다.

## reference
* https://medium.com/@zwhitchcox/matchlabels-labels-and-selectors-explained-in-detail-for-beginners-d421bdd05362
