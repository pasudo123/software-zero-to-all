# nginx-zero-to-all
* nginx 를 정리.

<BR>

## <a id="index"></a>index
* [nginx 관련 살펴볼 것]
    * [nginx : reverse-proxy & web-server](https://pasudo123.tistory.com/403)
    * [advanced nginx config, performance improvements, caching, clustering](https://www.youtube.com/watch?v=WMsqw68DhIg&ab_channel=TechFieldDay)
* nginx
    * [nginx docker hub](https://hub.docker.com/_/nginx)
        * docker 실행 (1)
            * `docker pull nginx:stable` : stable nginx image 풀 한다.
            * `docker run -d -name my-nginx -p 8080:80 {nginx-image-name}` : nginx image 에 대한 container 생성 및 run
                * host 8080 을 container 의 80 포트로 포워딩
        * docker 실행 (2)
            * `./Dockerfile_nginx` 디렉토리를 이용, Dockerfile 을 통해 이미지 생성 : `docker build -t custom-nginx .`
            * custom-nginx image 가 생성된 이후에 `docker run -it -d --name custom-nginx-container -p 8081:80 custom-nginx`
    * [nginx conf 작성시 유의사항](#notice)
    * [nginx-http-core-module](https://nginx.org/en/docs/http/ngx_http_core_module.html)
        * [location](#core-1)
        * [root](#core-2)
        * [alias](#core-3)
    * [nginx-http-proxy-module](https://nginx.org/en/docs/http/ngx_http_proxy_module.html)
        * [proxy_http_version](#proxy-1)
        * [proxy_set_header](#proxy-2)        
        * [proxy_pass](#proxy-3)
        * [proxy_cache](#proxy-4)
        * [proxy_no_cache](#proxy-5)
        * [proxy_cache_bypass](#proxy-6)
        * [proxy_cache_valid](#proxy-7)
        * [proxy_cache_use_stale](#proxy-8)
    * [nginx-http-upstream-module](http://nginx.org/en/docs/http/ngx_http_upstream_module.html)
        * [upstream](#upstream-1)
        * [keepalive](#upstream-2)


## <a id="notice"></a>nginx conf 작성시 유의사항
* 괄호사이는 항상 `띄어쓰기` 를 넣어준다.
```
// 틀린 표현
if($request_method !~ ^(GET|HEAD|POST)$){

}

// 맞는 표현
if ($request_method !~ ^(GET|HEAD|POST)$) {

}
```

### <a id="core-1"></a>directive : location
* 요청온 URI 에 따라 구성할 수 있다.
* 정규식 앞은 `~*` modifier 또는 `~` modifier 를 사용할 수 있다.
    * `~*` 는 case-insensitive 하다. 대소문자 구분을 하지 않는다.
    * `~` 는 case-sensitive 하다. 대소문자를 구분한다.
* location block 은 `중첩` 될 수 있다. 
* modifier 로 `^~` 가 있다면 정규식은 확인하지 않는다. `접두사 일치여부` 만 확인한다.
* modifier `=` 는 정확한 URI 의 정확한 일치를 말한다.
* `만약 location uri 를 정확하게 찾아낸다면 nginx 단의 search 작업은 종료된다.`

```shell
location = / {
    [ configuration A ]
}

location / {
    [ configuration B ]
}

// 두 개 이상의 슬래시를 단일 슬래시로 압축가능
location /documents/ {
    [ configuration C ]
}

location ^~ /images/ {
    [ configuration D ]
}

// 정규식 포함 (확장자에서 많이 쓰인다.)
location ~* \.(gif|jpg|jpeg)$ {
    [ configuration E ]
}
```

<BR> 

### <a id="core-2"></a>directive : root
요청에 대한 루트 디렉토리를 설정한다.

```shell
location /i/ {
    root /data/w3;
}
```
* `/data/w3/i/top.gif` 의 파일은 `/i/top.gif` 에 응답한다.

<BR>

### <a id="core-3"></a>directive : alias
특정 `location` 에 대해서 replacement 를 가능토록 한다.

```shell
location /i/ {
    alias /data/w3/images/;
}
```
* `/i/top.gif` 로 요청이 들어오면, `/data/w3/images/top.gif` 파일이 응답한다.

만약에 location 블록이 마지막 파트만 일치한다고 한다면 오히려 root 를 사용하는 편이 낫다.
```shell
// as-is
location /images/ {
    alias /data/w3/images/;
}

// to-be
location /images/ {
    root /data/w3;
}
```
* `개인적으로` multiple root 를 사용하려고 한다면 root 하나의 alias 여러개로 두어서, 하는 편이 나은듯하다.

<BR>

### <a id="proxy-1"></a>proxy_http_version
프록시 http version 을 설정한다. `디폴트 값`은 `1.0` 을 사용하고 있다. 만약에 `keepalive` 그리고 `NTLM Authentication` 을 사용하려는 경우에 `1.1` 을 사용하여야 한다.
* NLTM 이란
    * NT LanMan 은 Window NT 제품군의 모든 구성원이 사용하는 인증절차이다.
    * NTLM 인증은 네트워크에 사용자 암호를 전송하지 않는다. 대신에 NLTM 인증은 실제 암호가 네트워크를 거치지 않도록 하기위해 `challenge / response` 방식을 사용한다.
* NLTM 절차 ([참고링크](http://itnovice1.blogspot.com/2019/09/ntlm.html))
    * 클라이언트가 서버로 사용자 이름을 보낸다.
    * 서버는 이에 대한 요청값(challenge) 을 생성해 클라이언트로 보낸다.
    * 클라이언트는 이 요청값을 사용자 암호로 암호화하여 서버로 응답(response) 한다.
    * access token 을 발급하고 세션을 맺는다.
```shell
location ^~ /resources/ {
    proxy_http_version 1.1
    ... 
}
```

<BR>

### <a id="proxy-2"></a>proxy_set_header
프록시 서버에 전달된 요청에 헤더 필드를 `재정의` 또는 `추가` 할 수 있다.
```shell
proxy_set_header Host $proxy_host;
proxy_set_header Connection close;
```

<BR>

### <a id="proxy-3"></a>proxy_pass
프록시 서버의 프로토콜 및 주소를 매핑하는 추가적인 location 을 설정한다. 프로토콜은 `http` 또는 `https` 가 될 수 있다. 주소값은 `domain name` 또는 `ip address` 로 정할 수 있다.
```shell
// 일반적인 사용
proxy_pass http://localhost:8000/uri/;

// 변수가 사용되는 경우
location /name/ {
    proxy_pass http://127.0.0.1$request_uri;
}
```
* 일반적으로 web-server 뒷 단에 톰캣 was 가 떠있기 때문에 127.0.0.1:{port} 형태로 설정한다.
* 만약 `domain name` 이 여러개로 구성되어 있다면, `round-robin` 방식으로 각각의 domain name 은 사용된다. 추가적인 address 구성은 [server group](https://nginx.org/en/docs/http/ngx_http_upstream_module.html) 으로 지정할 수 있다.
    * example) `upstream { ... }`

<BR>

### <a id="proxy-4"></a>proxy_cache
캐싱을 위해 사용할 수 있는 공유 메모리존을 정의한다. 동일한 존을 여러 장소에서 사용할 수 있다. 디폴트로는 사용하지 않는 상태인 `proxy_cache off;` 이다. 캐싱을 이용하기 위해선 두 가지가 필요하다. `proxy_cache` 와 `proxy_cache_path` 이다.
```shell
proxy_cache_path /path/to/cache levels=1:2 keys_zone=my_cache:10m max_size=10g 
                 inactive=60m use_temp_path=off;

server {
    # ...
    location / {
        proxy_cache my_cache;
        proxy_pass http://my_upstream;
    }
}
```
* `proxy_cache_path` directive 는 캐시에 대한 경로 및 구성요소를 설정한다. 
* `proxy_cache` 는 `proxy_cache_path` 를 활성화시킨다.
* cache 에 로컬 디스크 디렉토리는 `/path/to/cache` 이다.
* `level` 은 캐시파일을 어떻게 저장할 지 설정한다. `level` 이 설정되지 않으면 모든 파일을 동일한 디렉토리에 저장시킨다. `level=1:2` 는 첫번째 단계의 디렉토리에는 한글자, 두번째 단계의 디렉토리에는 두글자로 명명하라는 의미이다. 결국 /path/to/cache/a/bc 에 캐시파일이 저장된다.
* `key_zone` 캐시를 가리키는 이름을 설정할 수 있다. 메모리에 해당 key 복사본이 있다면 nginx 는 디스크까지 가지않고 HIT or MISS 여부를 신속하게 확인할 수 있다.
* `max-size` 캐시의 최대 사이즈를 설정할 수 있다. (위의 예시에선 10gb 까지 설정되어 있다.) 만약 값이 지정되지 않는다면 선택한 모든 디스크 공간을 사용하도록 캐시가 커지게 된다. 캐시 크기가 한계에 도달한다면 캐시 매니저 프로세스가 가장 최근에 사용된 파일을 제거하게 된다.
* `inactive` 는 얼마나 캐시파일이 접근하지 않은 상태로 유효할 수 있는지 여부를 결정한다. 위의 예시에서 만약 `60m(60분)` 동안 파일에 대한 요청이 없다면 해당 파일은 만료여부에 상관없이 캐시 매니저에 의해 삭제된다. 여기서 디폴트는 `my_cache:10m` 의 `10m` 이다. `cache-control` 이랑은 다른 개념이다. `inactive` 는 일정시간 접근이 되지 않은 파일을 캐시매니저가 삭제되고 다시 액세스가 발생할 시, 오리진서버에서 콘텐츠를 새로고침하며 `inactive timer` 를 리셋한다.
* 파일 시스템간 불필요한 데이터 복사를 방지하려면 해당 매개변수를 `off` 로 설정하는 것이 좋다. `use_temp_path` 는 nginx 가 캐시용 파일을 임시저장경로에 쓰도록 명령하기 때문이다.

<BR>

### <a id="proxy-5"></a>proxy_no_cache
응답이 캐시에 저장되지 않는 조건을 정의한다. 먄약 해당 값이 비어있지 않고 0이 아닌 경우 응답 캐시는 저장되지 않을 것이다.

<BR>

### <a id="proxy-6"></a>proxy_cache_bypass
응답이 캐시에서 가져오지 않는 조건을 정의한다. 만약 해당 값이 비어있지 않고 0이 아닌 경우 응답 캐시는 캐시에서 가져오지 않는다.

<BR>

### <a id="proxy-7"></a>proxy_cache_valid
서로 다른 응답코드에 대한 캐싱시간을 설정한다.

```shell
proxy_cache_valid 200 302 10m;
proxy_cache_valid 404      1m;
proxy_cache_valid 5m;
proxy_cache_valid any      20m;
```
* 200 과 302 응답코드에 대해선 10분동안의 캐싱을 설정한다.
* 404 에 대해선 1분간의 캐싱을 설정한다.
* 응답코드 없이 캐싱시간만 설정할 수 있다. `5m(5분)` 이 설정되어 있다.
* `any` 파라미터를 통해서 다른 응답에 대한 캐싱시간을 설정할 수 있다. 위의 예시에선 `20m` 이 설정되어 있다.

<BR>

### <a id="proxy-8"></a>proxy_cache_use_stale


<BR>

### <a id="upstream-1"></a>upstream
서버의 그룹을 정의한다. 서버는 서로 다른 포트로 `listen` 즉 수신할 수 있다. 추가적으로 TCP 및 UNIX 도메인 소켓에서 수신하는 서버를 혼합할 수 있다.

```shell
upstream backend {
    server backend1.example.com weight=5;
    server 127.0.0.1:8080       max_fails=3 fail_timeout=30s;
    server unix:/tmp/backend3;

    server backup1.example.com  backup;
}
```
* `weighted round-robin` 을 사용하여 서버 간 요청을 분산시켜 전달된다.
    * 위의 예시에서 7개의 요청이 들어온다고 생각했을때, `weight=5` 로 설정된 서버에 5개의 요청이 흘러간다. 그리고 나머지 2개의 요청은 127.0.0.1:8080 과 unix:/tmp/backend3 으로 각각 전달된다.
* 서버와 통신 중 에러가 발생하면, 다음 서버로 해당 요청이 전달된다. (해당 요청은 다음 해당 서버가 작동될때까지 수행된다.) 서버에서 성공적인 응답을 얻을 수 없는 경우에 클라이언트는 마지막 서버와의 결과값을 반환받는다. 

<BR>

### <a id="upstream"></a>keepalive
업스트림 서버(`애플리케이션 서버`) 에 커넥션을 맺기위한 캐시를 활성화한다. syntax 는 `keepalive {connections}` 로 작성하는데 여기서 connections 파라미터는 각각의 워커 프로세스의 캐시에 보존되는 업스트림 서버에 대한 `idle keepalive connections` 의 최대 숫자 값을 설정한다. (재사용하기 위함이다.) 이후 connections 파라미터가 초과되면 가장 최근에 연결된 커넥션이 닫힌다.

keepalive 는 nginx 의 워커프로세스가 열 수 있는 업스트림 서버에 대한 총 연결 수를 제한하지 않는다. conections 파라미터는 새로운 수신 연결도 처리할 수 있도록 `충분하게 작은 수로 설정` 해야 한다.

```shell
upstream http_backend {
    server 127.0.0.1:8080;

    keepalive 16;
}

server {
    ...

    location /http/ {
        proxy_pass http://http_backend;
        proxy_http_version 1.1;
        proxy_set_header Connection "";
        ...
    }
}
```
* http 에서 사용하는 경우에, `proxy_http_version` 을 `1.1` 로 설정함과 동시에 `Connection` 헤더를 비워주어야 한다.
<BR>

## reference
* https://nginx.org/en/docs/http/ngx_http_core_module.html
* https://nginx.org/en/docs/http/ngx_http_proxy_module.html