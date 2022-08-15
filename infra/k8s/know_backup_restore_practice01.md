## k8s cluster 에서 구동중인 etcd 버전 확인하기
```shell
$ kubectl get pods -A
$ kubectl describe pods etcd-controlplane -n kube-system
```
* Containers 항목에 있는 etcd docker image version 을 확인

## control plane 노드에서 etcd cluster 로 도달할 수 있는 주소값은 무엇인지 확인하기
```shell
$ kubectl describe pods etcd-controlplane -n kube-system

# 아래 내용에서 --listen-client-url 을 확인
Command:
      etcd
      --advertise-client-urls=https://10.14.174.3:2379
      --cert-file=/etc/kubernetes/pki/etcd/server.crt
      --client-cert-auth=true
      --data-dir=/var/lib/etcd
      --initial-advertise-peer-urls=https://10.14.174.3:2380
      --initial-cluster=controlplane=https://10.14.174.3:2380
      --key-file=/etc/kubernetes/pki/etcd/server.key
      --listen-client-urls=https://127.0.0.1:2379,https://10.14.174.3:2379
      --listen-metrics-urls=http://127.0.0.1:2381
      --listen-peer-urls=https://10.14.174.3:2380
      --name=controlplane
      --peer-cert-file=/etc/kubernetes/pki/etcd/peer.crt
      --peer-client-cert-auth=true
      --peer-key-file=/etc/kubernetes/pki/etcd/peer.key
      --peer-trusted-ca-file=/etc/kubernetes/pki/etcd/ca.crt
      --snapshot-count=10000
      --trusted-ca-file=/etc/kubernetes/pki/etcd/ca.crt
```

## etcd cerificated file 은 어디에 위치했는지 확인하기
```shell
$ kubectl describe pods etcd-controlplane -n kube-system

# 아래 내용에서 --cert-file 을 확인
Command:
      etcd
      --advertise-client-urls=https://10.14.174.3:2379
      --cert-file=/etc/kubernetes/pki/etcd/server.crt
      --client-cert-auth=true
      --data-dir=/var/lib/etcd
      --initial-advertise-peer-urls=https://10.14.174.3:2380
      --initial-cluster=controlplane=https://10.14.174.3:2380
      --key-file=/etc/kubernetes/pki/etcd/server.key
      --listen-client-urls=https://127.0.0.1:2379,https://10.14.174.3:2379
      --listen-metrics-urls=http://127.0.0.1:2381
      --listen-peer-urls=https://10.14.174.3:2380
      --name=controlplane
      --peer-cert-file=/etc/kubernetes/pki/etcd/peer.crt
      --peer-client-cert-auth=true
      --peer-key-file=/etc/kubernetes/pki/etcd/peer.key
      --peer-trusted-ca-file=/etc/kubernetes/pki/etcd/ca.crt
      --snapshot-count=10000
      --trusted-ca-file=/etc/kubernetes/pki/etcd/ca.crt
```

## etcd database 스냅샷을 뜬다. : 위치는 /opt/snapshot-pre-boot.db
```shell

$ export ETCDCTL_API=3

# 현재 설정된 etcd 의 속성을 보고 확인 
$ etcdctl snapshot save --endpoints=127.0.0.1:2379 \
> --cacert=/etc/kubernetes/pki/etcd/ca.crt \
> --cert=/etc/kubernetes/pki/etcd/server.crt \
> --key=/etc/kubernetes/pki/etcd/server.key \
> /opt/snapshot-pre-boot.db
Snapshot saved at /opt/snapshot-pre-boot.db
```

## etcd database 를 복구한다. (해당 부분이 좀 어렵다...)
```shell

$ export ETCDCTL_API=3
$ etcdctl snapshot restore --data-dir /var/lib/etcd-from-backup /opt/snapshot-pre-boot.db
# etcd.yaml 파일을 수정한다. : hostPath 로 설정된 부분을 변경한다.
$ vim /etc/kubernetes/manifests/etcd.yaml
```

