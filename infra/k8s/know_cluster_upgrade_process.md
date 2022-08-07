## cluster upgrade process
* kube api server 는 주요한 컴포넌트
* `어떤한 컴포넌트들도 kube api server 컴포넌트의 버전보다 더 높을 수 없다.`
    * kube api server 의 버전이 v1.10 라고 가정
    * controller-manager & kube-scheduler 는 kube api server 와 같거나(v1.10) 또는 -0.1 낮다.(v1.09)
    * kubelet & kube-proxy 는 kube api server 와 같거나 -0.1 또는 -0.2 낮다. (v1.10, v1.09, v1.08)
    * kubectl (kube control) 의 경우에는 kube api server 의 버전보다 같거나 높다.
* k8s 는 업그레이드를 한번에 못한다. 마이너 버전부터 한단게씩 올려야 한다.
* k8s 업그레이드는 두가지 스텝이 존재한다.
    * master node 업그레이드
    * worker node 업그레이드
    * 마스터노드 업그레이드
        * 마스터 노드가 다운되더라도 사용자들은 애플리케이션에 접근할 수 있다. (영향을 끼치지 않음)
        * 대신에 kubectl, k8s api 에는 접근할 수 없다.
        * 애플리케이션에 deploy, delete, modify 를 수행할 수 없다.
    * 워커노드를 업그레이드하기 위한 세가지 전략이 존재한다.
        * (1) 워커노드를 모드 업그레이드 한다. -> 사용자는 애플리케이션에 접근할 수 없다. (다운타임이 필연적으로 발생한다.)
        * (2) 워커노드를 한대씩 순차적으로 업그레이드 한다. 업그레이드 되는 파드는 현재 구동중인 워커노드에 할당된다.
        * (3) 신규 버전의 워커노드를 그냥 추가한다.
* 업그레이드 어떻게?
    * `kubeadm upgrade plan` 를 사용하면 여러 업그레이드를 위한 정보를 제공해준다.
    * 업그레이드의 계획을 살펴보고, 업그레이드 할 수 있는 버전을 알려준다.

## 문제관련
### how many nodes can host workloads in this clusters ?
```shell
# 모든 노드에 대해서 taints 를 확인한다.
kubectl describe node | grep Taints
```

### what is the latest stable version available for upgrade ?
```shell
# 
kubeadm upgrade plan
```

### upgrade the controlplane components to exact version v1.20.0
* 1.20.0 으로 controlplane 컴포넌트를 업그레이드 해줘야함.
* 우선적으로 k8s 홈페이지 접근해서, 관련 링크를 살핀다.
    * [v1-20 upgrade k8s version](https://v1-20.docs.kubernetes.io/ko/docs/tasks/administer-cluster/kubeadm/kubeadm-upgrade/)
* [control plane 노드 업그레이드](https://v1-20.docs.kubernetes.io/ko/docs/tasks/administer-cluster/kubeadm/kubeadm-upgrade/#%EC%BB%A8%ED%8A%B8%EB%A1%A4-%ED%94%8C%EB%A0%88%EC%9D%B8-%EB%85%B8%EB%93%9C-%EC%97%85%EA%B7%B8%EB%A0%88%EC%9D%B4%EB%93%9C)
* [kubelet 과 kubectl 업그레이드](https://v1-20.docs.kubernetes.io/ko/docs/tasks/administer-cluster/kubeadm/kubeadm-upgrade/#kubelet%EA%B3%BC-kubectl-%EC%97%85%EA%B7%B8%EB%A0%88%EC%9D%B4%EB%93%9C)

```shell
# OS 확인
$ cat /etc/*release*

# 업그레이드할 버전 결정 : 최신 안정화된 버전을 찾는다.
$ apt update
$ apt-cache madison kubeadm

# 특정 버전 명시 : 1.20.0-00
$ apt-mark unhold kubeadm && \
apt-get update && apt-get install -y kubeadm=1.20.0-00 && \
apt-mark hold kubeadm

# kubelet & kubectl 버전 명시 업그레이드 : 1.20.0-00
$ apt-get update && \
apt-get install -y --allow-change-held-packages kubelet=1.20.0-00 kubectl=1.20.0-0

# 재시작
$ sudo systemctl daemon-reload
$ sudo systemctl restart kubelet
```

### upgrade the worker node to the exact version v1.20.0
* 아까 마스터노드를 업그레이드 한 것과 유사한 방식으로 업그레이드를 진행할 수 있도록 한다.
* 해당 노드에 `ssh` 접속하고 유사하게 진행

