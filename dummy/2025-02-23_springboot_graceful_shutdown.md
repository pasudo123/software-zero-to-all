## springboot graceful shutdown 
* Spring Boot 2.3 부터 graceful shutdown 설정이 도입되었다.
* Spring Boot 2.x 문서 기준으로 graceful shutdown 을 활성화하려면 `server.shutdown=graceful` 을 설정한다.
* 최신 Spring Boot 문서에서는 graceful shutdown 이 기본 활성화로 설명되며, `server.shutdown=immediate` 로 비활성화할 수 있다.

```yaml
server:
  port: 8080
  shutdown: graceful

## shutdown 수행 시에 타임아웃 시간을 설정 (Spring Boot 2.x 기본값 30초)
spring:
  lifecycle:
    timeout-per-shutdown-phase: "10s"
```

## 테스트는 어떻게?
* intellij 같은 IDE 환경에선 sigkill(9) 가 동작한다.
* pid 기준으로 sigterm(15) 가 동작할 수 있도록 터미널을 통한 종료를 진행하면 테스트 확인할 수 있다. (`직접 테스트 확인`)

8080 포트로 pid 를 확인할 수 있다.
```shell
❯ lsof -i :8080 
```


## k8s 고려사항
* k8s 환경에서 애플리케이션을 구동중이면 pod 의 라이프사이클을 고려할 필요가 있다.
* preStop 과 terminationGracePeriodSeconds 을 이용한다.
    * preStop 은 컨테이너 종료전 hook 프로세스. preStop 이 끝나면 kubelet 이 sigterm(15) 명령어를 메인 프로세스로 전달한다.
    * sigterm 이 끝난 뒤 일정시간 (terminationGracePeriodSeconds) 가 지나면 sigkill(9) 명령어가 수행된다.
* preStop <= terminationGracePeriodSeconds 으로 잡아야한다. (동일하기 보다는 일정초단위 값이 더 크게 잡는게 낫다. +10, +15초)
  * preStop 은 terminationGracePeriodSeconds 에 포함된 시간이다.

## reference
* https://docs.spring.io/spring-boot/reference/web/graceful-shutdown.html
* https://docs.spring.io/spring-boot/docs/2.3.x/reference/html/appendix-application-properties.html
