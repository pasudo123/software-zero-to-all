# 데몬셋 (daemonSet)
* `노드 하나당 파드 한 개 만을 생성한다.` 만약 노드가 클러스터에 추가되면 파드도 해당 노드에 하나 추가된다.
* 노드가 클러스터에서 제거되면, 해당 파드는 가비지로 수집된다.
* 데몬셋을 삭제하면 데몬셋이 생성한 파드들은 정리된다.

## 데몬셋을 언제 사용하는가?
* 노드를 관리하는 파드라면 데몬셋으로 만드는게 효과적이다.
  * 모든 노드에서 클러스터 스토리지 데몬을 실행하기 위함
  * 모든 노드에서 로그 수집 데몬을 실행하기 위함
  * 모든 노드에서 노드 모니터링 데몬을 실행하기 위함

## 생성은?
* replicaset 생성과 유사하다. 파일 내용도 유사
* Kind 내용이 `DaemonSet` 으로 설정된다.

## 명령어는?
* kubectl create -f {daemonset-config-file}.yaml
* kubectl get daemonsets
* kubectl describe daemonsets {name}
* 생성하려면, kubectl create 형태로 deployment 를 만들고 내용을 바꿔는 형태로 가면 된다...

## 어떻게 동작하는가?
* k8s 1.12 버전부터는 default schedule & node affinity 를 통해 노드에 스케줄링 된다.

## reference
* https://kubernetes.io/ko/docs/concepts/workloads/controllers/daemonset/
* 컨테이너 인프라 환경 구축을 위한 쿠버네티스/도커
