## static pod
* 스태틱 파드는 특정 노드 내의 kubelet 에 얽매인 파드이다.
    * kubelet 이 독립적으로 관리하는 pod 이다.
    * kube-api server 의 도움이 필요없다.
* 별도 k8s 아키텍처가 static pod 를 만들 때 필요없다.
    * kube-api server
    * kube-controller-manager
    * kube-scheduler
    * etcd cluster
    * master node

## node 가 자체적으로 어떻게 파드를 생성할 수 있는가? (kube-api-server 없이) 
* node 내에는 /etc/kubenertes/manifests 디렉토리 내에 pod yml 파일을 위치시키면된다.
    * 특정 node 에 접속해서 그냥 yml 파일을 만들면 된다.
* node 내의 kubelet 은 해당 파일을 읽어들여 pod 을 생성한다.
    * kubelet 은 지속적으로 해당 파일을 체크한다.
    * 노드 내 컨테이너를 만든다. 문제가 생기면 리스타트한다.
* 명세된 pod 의 내용이 변경되면 kubelet 은 변경된 내용을 수정한다.
* 명세된 pod 의 내용이 삭제되면, kubelet 은 해당 pod 를 삭제한다.
* `오직 pod 만이 이렇게 생성할 수 있다.`

## staticPodPath 는 어디서 찾는가?
* /var/lib/kubelet 디렉토리에 config.yaml 파일이 있다. 여기에 staticPodPath 의 경로가 적혀있다.

## static pod 와 daemonset 의 차이
* staticPod 는 kubelet 에 의해서 만들어진다.
* daemonSet 은 kube-api server 에 의해서 만들어진다.
* kube-scheduler 에 의해서 무시된다.
