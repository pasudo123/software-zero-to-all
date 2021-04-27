# 잡 (job)
잡에서 하나 이상의 파드를 생성하고, 지정된 수의 파드가 성공적으로 종료될 때까지 `계속해서 파드의 실행을 재시도` 한다.   
파드가 성공적으로 완료되면, 성공적으로 완료된 job을 추적한다. `지정된 수` 의 성공 완료에 도달하면 작업 (즉, 잡) 이 완료된다.   
잡을 삭제하면 잡이 생성한 파드가 정리된다.

예시 잡
```yaml
apiVersion: batch/v1
kind: Job
metadata:
  name: pi
spec:
  template:
    spec:
      containers:
      - name: pi
        image: perl
        command: ["perl",  "-Mbignum=bpi", "-wle", "print bpi(2000)"]
      restartPolicy: Never
  backoffLimit: 4
```

실행
```shell
❯ kubectl apply -f job.yaml
job.batch/pi created
```

## 파드와 컨테이너 장애 처리하기
파드 내 컨테이너의 프로세스가 0이 아닌 종료코드로 종료되었거나 혹은 컨테이너 메모리 제한을 초과해서 죽는 등의 여러가지 이유로 실패할 수 있다.   
만약 이런 일이 발생하고, 
* `.spec.template.spec.restartPolicy = "OnFailure"` 라면 파드는 노드에 그대로 유지되지만 `컨테이너는 다시 실행` 된다.

### 파드 백오프(backoff) 실패정책


## reference
* https://kubernetes.io/ko/docs/concepts/workloads/controllers/job/
