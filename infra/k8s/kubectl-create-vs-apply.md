# kubectl create vs apply 차이

## kubectl create
* imprative (명령형) command 를 사용할 때, 사용자는 직접적으로 클러스터 내 live object 에 접근할 수 있다.
* 클러스터에 __일회성__ 작업을 시작/실행 하는데 권장되는 방법이다.
* 디플로이트먼 내 파드를 생성한다.
```
$ kubectl create -f nginx.yaml

// deployment 객체를 생성하여 nginx 컨테이너를 띄운다.
$ kubectl create deployment nginx-pod --image=nginx
```

## kubectl apply
* declarative (선언적) command 를 사용할 때, 사용자는 로컬에 저장된 object configuration file 에 작업을 수행한다.
```
kubectl apply -f configs/

// recursive 수행
kubectl apply -R -f configs/
```

## reference
* https://kubernetes.io/docs/concepts/overview/working-with-objects/object-management/#imperative-commands
