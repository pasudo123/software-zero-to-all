## scheduling
스케줄링은 kube-scheduling 리소스에 제어를 받는듯 하다.?   
해당 리소스 위치는 namespace kube-system 의 kube-scheduling 이란 이름의 pod 으로 띄어져 있다.
```shell
❯ kubectl get pods -n kube-system
NAME                                     READY   STATUS                   RESTARTS        AGE
kube-scheduler-docker-desktop            1/1     Running                  236 (12h ago)   121d
```

## nodeName
* 팟을 spec.nodeName 을 통해 특정한 팟에 스케줄링 할 수 있다.
* [링크](https://kubernetes.io/ko/docs/concepts/scheduling-eviction/assign-pod-node/#nodename)