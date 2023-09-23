## curl
* CURL = Cient URL 의 약자
* 네트워크 프로토콜을 이용, 다양한 작업을 수행할 수 있음
   
## curl 의 옵션 (다른것 알아서 검색하자..)
### -o, --output 을 이용, 결과값 파일로 저장
```shell
$ curl -o sample.txt www.naver.com
```
   
### -i, --include 를 이용, 응답헤더를 반환
```shell
$ curl -i www.naver.com
HTTP/2 200
server: NWS
date: Sat, 23 Sep 2023 06:05:02 GMT
content-type: text/html; charset=UTF-8
set-cookie: PM_CK_loc=dfec04b2733dcc066aad62bd9e26472178bd7d6197ffc44d15c4ccc8a08eb0bd; Expires=Sun, 24 Sep 2023 06:05:02 GMT; Path=/; HttpOnly
cache-control: no-cache, no-store, must-revalidate
pragma: no-cache
x-frame-options: DENY
x-xss-protection: 1; mode=block
strict-transport-security: max-age=63072000; includeSubdomains
referrer-policy: unsafe-url
```
   
### -v 를 이용, curl 의 결과 상세 출력
```shell
$ curl -v https://www.naver.com
```
   
### --resolve 를 이용, 로컬호스트 테스트
```shell
$ curl --resolve ${hosts 파일에 설정된 호스트명}:${webserver port}:${local IP} ${요청보낼 호스트명}
$ curl --resolve my.local:80:127.0.0.1 http://myapp.local
```
   
### --resolve 를 이용, 로드밸런서 테스트
```shell
$ curl --resolve ${로드밸런서 호스트명}:${webserver port}:${요청보내고자 하는 server IP} ${로드밸런서 호스트명}
$ curl --resolve myloadbalancer.example.com:443:203.0.113.10 https://myloadbalancer.example.com
```
* VIP 로 묶인 서버를 기준으로 특정 IP 로 요청보낸다.