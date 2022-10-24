## xargs command 👀
* 표준입력 데이터스트림을 읽고 명령줄을 처리한다.
* 특정 명령을 파이프라인을 통해 인자로 받아 실행하고자 할 떄 같이 쓸 수 있다.

## 사용방식
k8s 잡을 삭제할 일이 있어서 일괄적으로 삭제처리할 떄 유용하게 쓸 수 있었다. (회사 동료분이 알려줬다.)

```shell
$ kubectl get jobs | awk '{print $1}' | xargs kubectl delete job 
```