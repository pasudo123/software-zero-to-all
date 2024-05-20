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


## 참고링크
- https://developer.mozilla.org/ko/docs/Web/HTTP/Headers/Keep-Alive