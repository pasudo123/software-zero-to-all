# http
## keep-alive
클라이언트 요청
```shell
GET /index.html HTTP/1.1
Host: www.example.com
Connection: keep-alive
```
- 서버에 연결을 유지하라는 요청

<BR>  

서버 응답
```shell
HTTP/1.1 200 OK
Content-Type: text/html
Connection: keep-alive
Keep-Alive: timeout=5, max=100
```
- 클라이언트와 연결을 유지함. 최대 100개의 요청 및 타임아웃 5초가 설정됨.
- timeout : 유휴연결이 계속 열려 있어야 하는 최소한의 시간(초단위)을 가리킨다. (한번 연결되면 최소 timeout 만큼 연결을 유지한다.)
    - keep-alive TCP 메시지가 trasnport layer 에도 설정되어야 한다. 위 부분은 http 1.1 의 application layer 에서만 설정된 상태라서 http 의 keep alive 는 무시될 수 있다.
- max : 연결이 닫히기 전에 전송될 수 있는 최대 요청 수를 가리킨다.
    - 여러 요청을 한번의 파이프라이닝으로 처리할 수 있다.

<BR>

## keep-alive 커넥션 제한과 규칙
- keep-alive 는 http/1.0 에서 기본으로 사용되지 않는다. 클라이언트는 keep-alive 커넥션을 사용하기 위해 Connection: Keep-Alive 요청 헤더를 보내야 한다.
    - 커넥션을 유지하려면 모든 메시지에 Connection: Keep-Alive 를 보내야 한다.
    - 만약 클라이언트가 Connection: Keep-Alive 헤더를 보내지 않으면 서버는 요청을 처리한 후 커넥션을 끊는다.
- keep-alive 와 프록시 설정
    - 프록시 설정이 잘못되면 연결을 끊으려고 해도 연결이 끊어지지 않는 Hang 현상이 발생한다.
- http/1.1 에서는 keep-alive 커넥션을 지원하지 않는 대신에, __지속 커넥션을 지원한다.__ (지속커넥션은 활성화)
    - 다음 커넥션을 끊으려면 Connection: Close 헤더를 명시해야 한다.
    - http/1.1 클라이언트는 응답에 Connection: Close 헤더가 없으면 응답 후에도 http/1.1 커넥션을 유지하는 것으로 추정한다.
    - 

<BR>

## 참고링크
- https://developer.mozilla.org/ko/docs/Web/HTTP/Headers/Keep-Alive
- http 완벽 가이드