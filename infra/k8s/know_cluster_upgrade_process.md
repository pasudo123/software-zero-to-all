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

## 문제관련
