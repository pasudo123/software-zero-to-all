# cronjob suspend
일시중지   
* `.spec.suspend` 필드를 true 로 설정하면 모든 후속 실행이 일시정지 된다.
* 해당 설정은 이미 시작된 실행에는 적용되지 않는다. 기본값은 false 로 동작한다.

### 주의
만약 스케줄된 시간동안 잡이 일시중지 되었다면, 누락된 잡으로 간주한다.   
`.spec.suspend` 의 값을 true 에서 false 로 변경하면 누락된 잡들은 즉시 스케쥴된다.

## reference
* https://kubernetes.io/ko/docs/tasks/job/automated-tasks-with-cron-jobs/#%EC%9D%BC%EC%8B%9C-%EC%A0%95%EC%A7%80
