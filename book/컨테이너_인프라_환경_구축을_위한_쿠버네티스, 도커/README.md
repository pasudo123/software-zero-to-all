# 컨테이너 인프라 환경 구축을 위한 쿠버네티스/도커

### 사용 커맨드

```shell
$ kubectl get pods 

$ kubectl delete pods {nginx-pod : pod-ame}

$ kubectl delete deployment {deployment-name}

$ kubectl apply -f {file-name}.yaml

// https://kubernetes.io/ko/docs/reference/kubectl/cheatsheet/#%EB%A6%AC%EC%86%8C%EC%8A%A4-%EC%8A%A4%EC%BC%80%EC%9D%BC%EB%A7%81
// 리소스 스케일링 방식
$ kubectl scale deployment {deployment-name} --replicas=9

// 배포된 파드의 특정 값 확인
$ kubectl get pods\
-o=custom-columns=NAME:.metadata.name,IP:.status.podIP,STATUS:.status.phase,NODE:.spec.nodeName

// -o yaml 옵션을 주어서 pod.yaml 파일에 저장
$ kubectl get pods {pod-name} -o yaml > pod.yaml

$ kubectl scale deployment {deployment-name} --replicas=3

// node 를 스케줄 되지 않는 상태로 설정/해제 : 파드가 더 이상 해당 노드에 배포되지 않는다.
$ kubectl cordon {node-name}
$ kubectl uncordon {node-name}
$ kubectl cordon w1-k8s
$ kubectl uncordon w1-k8s

$ kubectl get nodes

// node 를 스케줄 되지 않은 상태로 변경시키는 것과 동일
$ kubectl drain {node-name}
$ kubectl drain w3-k8s
$ kubectl drain w3-k8s --ignore-daemonsets (daemonsets 을 무시하고 drain 을 수행한다.)
$ kubectl uncordon w3-k8s (스케줄 비활성화를 활성화로 변경)

// --record 는 배포한 정보의 히스토리를 기록한다.
$ kubectl apply -f {file-name}.yaml --record

// --record 옵션으로 기록된 히스토리는 rollout history 명령을 통해 확인이 가능하다.
$ kubectl `rollout history` deployment {deployment-name} 

// nginx 컨테이너 버전을 1.16.0 업데이트 수행. --record 명령을 통해서 기록
$ kubectl set image deployment {deployment-name} nginx=nginx:1.16.0 --record

// `rollout status` 를 통해 deployment 상태 확인 : nginx 컨테이너가 1.16.0 으로 모두 업데이트되었는지 확인
$ kubectl rollout status deployment rollout-nginx

// curl -I : 헤더정보만 보여주는 옵션
$ curl -I --slient 172.16.103.143

// 마지막 단계의 revision3 에서 전 단계 revision2 로 상태를 되돌린다.
$ kubectl rollout undo deployment {deployment-name}

// deployment pod 생성, image 는 sysnet4admin/echo-home 으로 설정
$ kubectl create deployment np-pods --images=sysnet4admin/echo-home

$ kubectl get services

$ kubectl get nodes -o wide

// {deployment-name} 에 해당하는 파드의 개수를 3개로 설정한다.
$ kubectl scale deployment {deployment-name} --replicas=3

// {deployment-name} 에 해당하는 디플로이먼트는 서비스 svc-v2 와 연결되어있다.
// 서비스 svc-v2 는 파드로 보내줄 연결포트를 80으로 설정한다.
$ kubectl expose deployment {deployment-name} --type=NodePort --name=svc-v2 --port=80

$ kubectl get services
$ kubectl delete deployment {deployment-name}
$ kubectl delete services {service-name}

$ kubectl create deployment {deployment-name} --image={image-name}
$ kubectl apply -f {file-name}.yaml

// ingress-nginx namespace 에서 파드 조회
$ kubectl get pods -n ingress-nginx

$ kubectl get ingress

// 인그레스에 요청한 내용이 모두 적용되었는지 여부 확인 : yaml 형식으로 출력해서 적용된 내용을 확인할 수 있도록 해준다.
$ kubectl get ingress -o yaml

$ kubectl get services -n ingress-nginx

$ kubectl expose deployment ex-lb --type=LoadBalancer --name=ex-svc
service/ex-svc exposed

$ kubectl get services ex-svc

// pod 의 부하를 확인한다.
$ kubectl top pods

// deployment 를 수정한다.
$ kubectl edit deployment {deployment-name}

// deployment 를 autoscale 하여 특정조건이 만족되는 경우에 자동으로 scale 되도록 한다.
// min 은 최소 파드의 개수, max 는 최대 파드의 개수, cpu-percent 는 cpu 사용량이 50% 를 넘으면 autoscale 하겠다는 의미다.
$ kubectl autoscale deployment {deployment-name} --min=1 --max=30 --cpu-percent=50

// -w 는 watch 의 약어로 오브젝트 상태에 변화가 감지되면 해당 변화를 출력해준다.
$ kubectl get pods -n metallb-system -o wide -w
```

# 개념
### cordon
* 특정한 노드에 파드를 더 이상 할당하지 않게 한다.
* node 의 상태는 `SchedulingDisabled` 가 된다. 

### drain
* 지정한 노드 내 파드를 전부 다른 노드로 이동시킨다.
* 이동의 개념보단 지정된 노드 내 파드를 삭제하고, 다른 노드 내에 파드를 생성한다.
* drain 된 node 의 상태는 `SchedulingDisabled` 가 된다. 

### 쿠버네티스 연결을 담당하는 service
* 외부의 요청이 k8s 클러스터에 접속하는 방법을 의미
