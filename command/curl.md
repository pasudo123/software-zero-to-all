# curl
* CURL = Cient URL 의 약자
* 네트워크 프로토콜을 이용, 다양한 작업을 수행할 수 있음
   
<BR>

## -o, --output 을 이용, 결과값 파일로 저장
```shell
$ curl -o sample.txt www.naver.com
```
   
<BR>

## -i, --include 를 이용, 응답헤더를 반환
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

<BR>

## -v 를 이용, curl 의 결과 상세 출력
```shell
$ curl -v https://www.naver.com
```

<BR>

## --resolve 를 이용, 로컬호스트 테스트
```shell
$ curl --resolve ${host}:${port}:${local IP} ${domain}
$ curl --resolve my.local:80:127.0.0.1 http://myapp.local
```

<BR>

## --resolve 를 이용, 로드밸런서 테스트
강제로 DNS 리졸브를 가능하게한다.
```shell
$ curl --resolve ${host}:${port}:${IP} ${domain}

## https 
$ curl --resolve myloadbalancer.example.com:443:203.0.113.10 https://myloadbalancer.example.com

## http
$ curl --resolve myloadbalancer.example.com:80:203.0.113.10 http://myloadbalancer.example.com
```
* VIP 로 묶인 서버를 기준으로 특정 IP 로 요청보낸다.
* VIP 로 묶인 서버를 기준으로 인증서 갱신 개별 확인할때 편하다.

<BR>

## -x 를 이용, 프록시 설정 테스트
```shell
// curl -x proxy.example.com:10050 -X POST www.naver.com
$ curl -x ${proxy-host} -X POST ${host}
$ curl --proxy ${proxy-host} -X POST ${host}
```
- 아웃바운드로 나갈 시, 프록시를 거칠때 위와같은 설정을 한다.