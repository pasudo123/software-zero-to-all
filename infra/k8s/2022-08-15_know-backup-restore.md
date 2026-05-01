## backup & restore methods

## backup resource configs
```
$ kubectl get all --all-namespaces -o yaml > all-deploy-services.yaml
```
* backup all pods & deployment & service

## backup etcd
* etcd cluster 는 클러스터의 상태를 저장하는 클러스터이다.
    * 노드와 클러스터에서 생성한 리소스들의 정보를 저장하고 있다.
* etcd cluster 는 마스터노드에 호스팅되어있다.
* save comment 를 통해서 스냅샷을 저장할 수 있다. 
* cluster 정보를 백업하기 위해선
    * (1) kube-apiserver 를 멈춰야 한다. => `service kube-apiserver stop`
    * (2) 이후에 restore 커맨드를 통해서 백업파일을 어느 특정한 곳에 위치시킨다.

```shell
$ ETCDCTL_API=3 etcdctl snapshot save snapshot.db
$ ls 
$ service kube-apiserver stop
$ ETCDCTL_API=3 etcdctl snapshot restore snapshot.db --data-dir /var/lib/etcd-from-backup
$ systemctl daemon-reload
$ service etcd restart
$ service kube-apiserver start
```

## etcd
* https://tech.kakao.com/2021/12/20/kubernetes-etcd/ 를 참고해서 작성
* etcd 는 key/value 형태의 데이터를 저장하는 저장소. etcd 가 다운되어버린다면 k8s 클러스터의 기능을 사용할 수 없음
* etcd 는 RSM (replicated state machine) 으로 분산 컴퓨팅 환경에서 몇 개의 서버가 다운되더라도 정상적으로 동작하기 위해서 선택하는 빙법이다.
    * 똑같은 데이터를 여러서버에서 복제하고 이 방법을 사용하는 머신은 RSM 이라고 한다.
* control plane(컨트롤 플레인) components 중 일부가 etcd 컴포넌트이다.
    * 컨트롤 플레인은 클러스터 주요 구성요소들을 배포하고 관리한다.
    * control plane components 들은 kube-system ns 에 생성되어있다.

## etcd 스냅샷 뜨고 싶다면?
```shell
$ etcdctl snapshot save -h
```

## etcd 복구하고 싶다면?
```shell
$ etcd snapshot restore -h
```