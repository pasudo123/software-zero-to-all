# k8s 
* [kubenetes-zero-to-all](./2021-02-07_k8s-zero-to-all.md)

## 오브젝트
* [what-does-mean-k8s-object : 쿠버네티스 오브젝트란](./2021-04-18_what-does-mean-k8s-object.md)
* [k8s-object-management : 쿠버네티스 오브젝트 관리](./2021-04-18_k8s-object-management.md)
* [k8s-label-and-selector : 레이블과 셀렉터](./2021-05-05_k8s-label-and-selector.md)

## 파드
* [what-does-mean-k8s-pod : 쿠버네티스 파드란](./2021-04-18_what-does-mean-k8s-pod.md)
* [pod-life-cycle : 파드의 라이프사이클](./2021-04-20_pod-life-cycle.md)
* [initContainers : 초기화 컨테이너](2021-07-08_initcontainers.md)

## 워크로드 리소스
* [workloads-resources-deployment : 디플로이먼트](./2021-04-22_workloads-resources-deployment.md)
* [workloads-resources-replicaset : 레플리카셋](./2021-04-23_workloads-resources-replicaset.md)
* [workloads-resources-job : 잡](./2021-04-27_workloads-resources-job.md)
* [workloads-resources-configmap : 컨피그맵](./2021-10-28_workloads-resources-configmap.md)
* [workloads-resources-daemonSet : 데몬셋](2021-10-28_workloads-resources-daemonset.md)

## 서비스, 로드밸런싱, 네트워킹
* [networking-service : 서비스(service)](./2021-05-23_networking-service.md)
* [networking-ingress : 인그레스(ingress)](./2021-05-21_networking-ingress.md)

## kubectl 명령어
* [kubectl-commands : kubectl command](./2021-05-05_kubectl-commands.md)

## yml 파일 작성
* [understanding-yaml : yaml 파일에 있는 내용](./2021-04-23_understanding-yaml.md)

## k8s 토막지식 + udemy 들은 거 기반 정리
* [kubectl run, kubectl creates, kubectl apply 차이 : imprative & declarative](./2021-03-14_kubectl-run-vs-create-vs-apply.md)
* [knowledge-spec-suspend : k8s 대시보드에서 크론잡을 잠시 일시중지 시키려면](./2021-05-10_knowledge-spec-suspend.md)
* [knowledge-matchLabels-labels-selectors : matchLabels & labels & selectors 이해하기](2021-05-22_knowledge-matchlabels-labels-selectors.md)
* [config & context](2022-06-25_know-config-context.md)
* [--dry-run=client 옵션](2022-06-27_know-dry-run.md)
* [service type, nodePort & clusterIP & LoadBalancer](2022-06-27_know-service-type.md)
* [namespace](2022-06-28_know-namespace.md)
* [scheduling](2022-06-30_know-scheduling.md)
* [scheduling : taints & tolerations](2022-07-01_know-taints-tolerations.md)
* [scheduling : NodeSelector](2022-07-03_know-node-selector.md)
* [scheduling : NodeAffinity](2022-07-03_know-node-affinity.md)
* [scheduling : Taints & Tolerations vs NodeSelector/NodeAffinity](2022-07-03_know-versus-taints-tolerations-and-node-selector-node-affinity.md)
* [scheduling : resources.requests & resources.limits](2022-07-06_know-resources-requests-and-resources-limits.md)
* [scheduling : daemonset : 기존에 작성해둔거 참고](#)
* [scheduling : static pod](2022-07-10_know-static-pod.md)
* [scheduling : multiple scheduler](2022-07-14_know-multiple-scheduler.md)
* [logging & monitoring](2022-07-21_know-logging-monitoring.md)
* [rolling update & rollback](2022-07-24_know-rolling-update-and-rollback.md)
* [configure : arguments](2022-07-24_know-configure-arguments.md)
* [configure : env variable](2022-07-28_know-configure-env-variable.md)
* [configure : secret](2022-07-29_know-configure-secret.md)
* [initContainer 기존에 작성한 부분 참고](2021-07-08_initcontainers.md)
* [liveness probe & readness probe](https://kubernetes.io/docs/tasks/configure-pod-container/configure-liveness-readiness-startup-probes/)
* [os upgrade : drain, corbon, uncorbon](2022-07-31_know-os-upgrade.md)
* [os upgrade : cluster upgrade process](2022-08-07_know-cluster-upgrade-process.md)
* [backup & restore : resources, etcdctl](2022-08-15_know-backup-restore.md)
    * [practice01](2022-08-15_know-backup-restore-practice01.md)
* [security](2022-08-27_know-security.md)
* [local cache patterns : 다중 파드에서 로컬 캐시 운영 패턴](2026-03-24_k8s-local-cache-patterns.md)

## CKA 참고자료
* [CKA udemy 강의](https://www.udemy.com/course/certified-kubernetes-administrator-with-practice-tests/)
* [CKA udemy 강의 자료](https://github.com/kodekloudhub/certified-kubernetes-administrator-course)
