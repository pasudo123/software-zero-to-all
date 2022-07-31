# `kubectl run` vs `kubectl create` vs `kubectl apply`

## kubectl run 
* 쉽게 파드를 생성할 수 있는 방법
* 단일 파드 1개가 생성되고 작동 및 관리된다.
```shell
$ kubectl run nginx-pod --image=nginx
```

## kubectl create
* imprative (명령형) command 를 사용할 때, 사용자는 직접적으로 클러스터 내 live object 에 접근할 수 있다.
* 클러스터에 __일회성__ 작업을 시작/실행 하는데 권장되는 방법이다.
* 동일한 오브젝트가 해당 네임스페이스에 존재하는 경우에는 에러가 발생한다. (해당 오브젝트가 미존재 하는 경우에만 생성할 수 있다.)
```shell
❯ kubectl create deployment mydeployment --image=nginx
deployment.apps/mydeployment created
 
❯ kubectl create deployment mydeployment --image=nginx
error: failed to create deployment: deployments.apps "mydeployment" already exists
```

* deployment 안에 파드를 생성한다.
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

## imprative 명령형
쿠버네티스 클러스터에게 일일히 어떻게 할 것인지에 대한 설명을 나열한다.

### create objects
* kubectl run --image=nginx nginx
* kubectl create deployment --image=nginx nginx
* kubectl expose deployment nginx --port 80
* kubectl expose service --name {name} --port=80 
  * default 로 svc 타입은 cluserip 로 세팅된다.

### update objects
* kubectl edit deployment nginx
  * 쿠버네티크 클러스터 내의 live object 와 configuration file 간의 내용 차이가 있을 수 있다.
  * 따라서 파일을 변경하고 replace -f nginx.yml 명령어를 쓰는게 좋을 수 있다.
* kubectl scale deployment nginx --replicas=5
* kubectl set image deployment nginx nginx=nginx:1

### etc
* kubectl create -f nginx.yml
* kubectl replace -f nginx.yml
* kubectl replace --force -f {file}.yaml
  * 리소스 강제 업데이트 : 리소스를 삭제하고, 재생성한다. 해당 명령어는 서비스를 중단시킨다.
* kubectl delete -f nginx.yml
* configuration 파일이 변경되지 않은 상태에서 동일한 명령어를 쓰게되면 에러를 만난다.

## declarative 선언형
configuration 파일을 읽어들여 쿠버네티스 클러스터 내에서 리소스가 적용되도록 한다.
* kubectl apply -f nginx.yml
* 동일하게 명령을 수행하더라도 에러를 만나지 않는다.
* 쿠버네티스 클러스터 내에서 live object configuration 내용을 살피면 `metadata.annotations.kubectl.kubernetes.io/last-applied-confiugarion` 내용이 있다. 해당 내용에 따라서 local file 의 configuraion 이 변경되었을 때 live object 도 적용/반영된다.


## reference
* https://kubernetes.io/docs/concepts/overview/working-with-objects/object-management/#imperative-commands
