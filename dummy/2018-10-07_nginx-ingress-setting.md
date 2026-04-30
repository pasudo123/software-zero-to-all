## ingress nginx annotation
ingress nginx controller 의 애노테이션 중에서 타임아웃을 설정하는 애노테이션이 존재.   
이 애노테이션을 활용하면 파드에 요청을 쓰고 읽고 그리고 커넥션 맺는 시간까지 조절할 수 있다.   

<!-- TOC -->
  * [ingress nginx annotation](#ingress-nginx-annotation)
  * [annotation](#annotation)
  * [proxy-read-timeout](#proxy-read-timeout)
  * [proxy-send-timeout](#proxy-send-timeout)
  * [proxy-connect-timeout](#proxy-connect-timeout)
  * [참고](#참고)
<!-- TOC -->

## annotation
```yaml
nginx.ingress.kubernetes.io/proxy-read-timeout: "180"
nginx.ingress.kubernetes.io/proxy-send-timeout: "180"
nginx.ingress.kubernetes.io/proxy-connect-timeout: "10"
```
* 위 값들의 단위는 초단위

## proxy-read-timeout
* nginx 가 백엔드로 응답 데이터를 읽는(read) 동안 다음 데이터가 도착하지 않고 지연되는 경우의 최대 대기 시간 (응답이 중간에 끊기는 것 까지 포함)
* 예시상황
  * DB 에서 대용량 데이터를 읽고 가공하는 API (느린 응답)
  * long-polling 방식의 http API
  * 리포트 생성, 파일 다운로드 등의 무거운 작업인 경우
* 해당 값을 늘리는 경우에 클라이언트 쪽에서도 read-timeout 이 있기 때문에 맞물려 동작되어야 한다.

## proxy-send-timeout
* nginx 가 백엔드로 요청 body를 전송(write) 하는 중에, 응답 없이 지연될 수 있는 최대 대기 시간
* 예시상황 (by chatGPT. 나도 해보진 않음. 2025-07-09)
  * 요청 바디가 큰 경우 (대형 JSON, 파입업로드)
  * 네트워크 지연으로 데이터 전송이 지체되는 경우

## proxy-connect-timeout
* nginx 가 백엔드 (was : e.g. undertow, tomcat) 와 TCP 연결을 시도할 때 최대 대기시간
* 기본값은 60초.
* 예시상황 (by chatGPT. 나도 해보진 않음. 2025-07-09)
  * pod가 부하로 느리게 뜨는 경우
  * 서비스가 비정상적이거나 readiness probe 가 통과되지 않아 느려지는 경우

## 참고
* https://kubernetes.github.io/ingress-nginx/user-guide/nginx-configuration/annotations/#custom-timeouts
  * 위 사이트에 관련 내용들을 참고.
* chatGPT