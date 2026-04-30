# k8s 레이블과 셀렉터
`레이블` 은 파드와 같은 오브젝트에 첨부된 키와 값의 쌍이다.   
레이블은 오브젝트의 특성을 식별하는데 사용되어 사용자에게 중요하지만 코어 시스템에 직접적인 의미는 없다.   
레이블로 오브젝트의 하위 집합을 선택하고, 구성하는데 사용할 수 있다.   
레이블은 오브젝트를 생성할 때에 붙이거나 생성 이후에 붙이거나 언제든지 수정이 가능하다.   
오브젝트마다 키와 값으로 레이블을 정의할 수 있다.   
오브젝트의 `키` 는 고유한 값이어야 한다.

## 구문과 캐릭터 셋
`environment: production` 과 `app: nginx` 2개의 레이블이 있는 구성파일이다.
```yaml
apiVersion: v1
kind: Pod
metadata:
  name: label-demo
  labels: // 레이블이 아래에 적혀져 있다.
    environment: production
    app: nginx
spec:
  containers:
  - name: nginx
    image: nginx:1.14.2
    ports:
    - containerPort: 80
```

```shell
// 위의 yaml 내용을 파일로 만들어서 명령형 오브젝트 수행
// metadata.name 의 이름으로 파드가 생성되었음
❯ kubectl apply -f sample.yaml
pod/label-demo created
```

pod 조회 시, metadata.name 으로 설정된 이름으로 파드 이름으로 설정됨
```shell
❯ kubectl get pods -o wide
NAME         READY   STATUS    RESTARTS   AGE     IP          NODE             NOMINATED NODE   READINESS GATES
label-demo   1/1     Running   0          2m21s   10.1.0.79   docker-desktop   <none>           <none>
```
  
[1] metadata.labels.app 으로 특정 파드를 검색한다.
```
❯ kubectl get pods -l app=nginx
NAME         READY   STATUS    RESTARTS   AGE
label-demo   1/1     Running   0          3m2s
```
  
[2] metadata.labels.environment 으로 특정 파드를 검색한다.
```
❯ kubectl get pods -l environment=production
NAME         READY   STATUS    RESTARTS   AGE
label-demo   1/1     Running   0          3m13s
```

[3] metadata.labels.environment 으로 production labels 가 아닌 파드를 검색한다.
```
❯ kubectl get pods -l environment!=production
No resources found in default namespace.
```

[4] metadata.labels.app 으로 nginx labels 가 아닌 파드를 검색한다.
```
❯ kubectl get pods -l app!=nginx
No resources found in default namespace.
```

## 레이블과 셀렉터
특정 레이블을 가진 파드 조회하는 명령어
```shell
kubectl get pods -l environment=production,tier=frontend
```

## reference
* https://kubernetes.io/ko/docs/concepts/overview/working-with-objects/labels/
