# nginx SSL 설정하기

## 1. 인증서 발급받기
__인증서를 설치할 서버에 접속 이후에 아래의 명령어를 수행<br>__
```bash
# -p 옵션은 하위 디렉토리 미존재시 하위 디렉토리까지 생성한다.
mkdir -p /var/www/letsencrypt/.well-known/acme-challenge
mkdir -p {디렉토리 경로}
```

__webroot 폴더의 경로를 알려주는 letsencrpyt.conf 파일을 생성<br>__
> 해당 파일을 생성할 시, `root` 계정이 아닌 `일반 계정` 으로 하여야 한다.<br>
```bash
touch /etc/nginx/snippets/letsencrypt.conf
```

__letsencrpyt.conf 내용 작성 <br>__
 ```bash
 location ^~ /.well-known/acme-challenge/ {
    default_type "text/plain";
    root /var/www/letsencrypt;
}
 ```
 
__nginx 라우팅 설정에 include 문을 포함 <br>__
 ```bash
# server block 바로 하위에 위치하면 된다.
# include 추가. ssl 설정관련 : location block
include /etc/nginx/snippets/letsencrypt.conf;
 ```
 
__nginx 리로드를 수행하기 이전에 `nginx -t` 를 이용 문법상 오류여부를 확인 <br>__

__인증서를 발급 <br>__
```bash
wget https://dl.eff.org/certbot-auto -P {다운받을 디렉토리}
wget https://dl.eff.org/certbot-auto -P /home/www
```
> `root` 계정이 아닌 일반 사용자 계정을 통해서 다운받는다.<br>
> 다운받은 파일은 Let's Encrypt 의 인증서 발급을 도와주는 프로그램인 certbot-auto이다.<br>
> 다운받고 난 뒤, 실행권한을 주어야 하는데 현재 권한이 없기 때문에 `chmod 755 certbot-auto` 를 수행한다.

__certbot-auto 실행<br>__
```bash
{certbot-auto 경로} certonly --webroot --agree-tos --no-eff-email --email {email} -w /var/www/letsencrypt -d {domain} -d {sub-domain}
```
- __certonly__ :
인증서 획득
- __--webroot__ :
webroot 플러그인 사용
- __--agree-tos__ :
약관 동의
- __--no-eff-email__ :
EFF mailing list 에 참여하지 않음 (인증서 발급기관 CA 로부터 영어 스팸메일을 받고싶다면 해당 옵션 삭제)
- __--email(email)__ :
본인 이메일 작성 (인증서 만료기간이 다가오면 작성된 메일로 내용을 수신할 수 있다.)
- __-w /var/www/letsensrypt__ :
챌린지라는 파일을 생성할 webroot 폴더경로 지정. 앞아서 생성해둠.
- __-d {domain}__ :
인증서를 생성할 도메인 등록. 인증서 하나에 사이트 명은 여러 개가 될 수 있기때문에 계속해서 덧붙이기 가능하다.

<br>

위의 내용을 수행하면 `Congratulations` 문구와 함께 아래의 문구가 일부 나온다.
```shell
Performing the following challenges:
http-01 challenge for gulagbu.com
http-01 challenge for www.gulagbu.com
Using the webroot path /var/www/letsencrypt for all unmatched domains.
Waiting for verification...
Cleaning up challenges

중략...

- Congratulations! Your certificate and chain have been saved at:
   /etc/letsencrypt/live/gulagbu.com/fullchain.pem
   Your key file has been saved at:
   /etc/letsencrypt/live/gulagbu.com/privkey.pem
   Your cert will expire on 2020-03-29. To obtain a new or tweaked
   version of this certificate in the future, simply run certbot-auto
   again. To non-interactively renew *all* of your certificates, run
   "certbot-auto renew"
```
- fullchain.pem 은 인증서 파일이다.
- private.pem 은 개인암호화 키이다.
- 만료날짜는 expire on 2020-03-29 이다.

아래는 `/etc/letsencrypt/live/gulagbu.com` 경로 안의 내용이다.<br>
```
lrwxrwxrwx 1 root root  35 Dec 30 12:04 cert.pem -> ../../archive/gulagbu.com
lrwxrwxrwx 1 root root  36 Dec 30 12:04 chain.pem -> ../../archive/gulagbu.co
lrwxrwxrwx 1 root root  40 Dec 30 12:04 fullchain.pem -> ../../archive/gulagb
lrwxrwxrwx 1 root root  38 Dec 30 12:04 privkey.pem -> ../../archive/gulagbu.
```
4개의 pem 파일을 가지고 있으며 각각 심볼릭 링크가 있다. 오른쪽이 실제 파일이 위치한 경로이다. <br>

`openssl x509 -noout -dates -in /etc/letsencrypt/live/{domain}/cert.pem` 를 입력하면 서버에 설치된 렛츠인스크립트 만료날짜를 확인할 수 있다.
```bash
notBefore=Dec 30 02:04:33 2019 GMT
notAfter=Mar 29 02:04:33 2020 GMT
```
> GMT 기준이기 때문에 우리나라 시간을 9시간 더해서 계산해준다. GMT 는 런던지검의 협정 세계시이다.

## 2. 웹서버 SSL 설정 및 HTTPS 라우팅 설정
__nginx 의 SSL 보안설정__<br>
(1) ssl.conf 파일을 생성하고 해당 파일 내에 보안코드를 작성<br>
(2) ssl.conf 파일을 라이퉁 설정 파일에 `include` 수행<br>
> 위의 내용처럼 할 필욘 없지만 보안 코드가 라우팅 설정파일에 작성되어야 한다는 것은 매우 중요하다.<br>

__ssl.conf 에 사용할 dhparam.pem 암호화 파일 생성__<br>
/etc/nginx/ 폴더 아래에 `dhparam.pem` 파일을 생성하여야 한다. 아래의 명령어를 입력<br>
암호화 비트의 수는 2048 또는 4096 의 2의 제곱승 형태로 쓸 수 있고 해당 값이 높으면 높을수록 암호화가 강력하다. (대신 HTTPS 성능이 느려진다.)<br>
`openssl dhparam -out /{저장할경로}/dhparam{파일 명}.pem {암호화 비트의 수}` 수행 <br>
`openssl dhparam -out /etc/nginx/dhparam.pem 2048`<br>
> 시간이 좀 걸린다.

__nginx ssl.conf 파일 생성__<br>

```bash
sudo touch /etc/nginx/snippets/ssl.conf
sudo chmod 777 /etc/nginx/snippets/ssl.conf
sudo ls -l /etc/nginx/snippets/ssl.conf
```

ssl.conf 파일을 생성하였으면 아래의 내용을 넣어준다. <br>
아래의 내용을 넣었으면 권한을 `644` 로 변경하여 사용자 권한을 막는다 <br>

```
ssl_session_timeout 1d;
ssl_session_cache shared:SSL:50m;
ssl_session_tickets off;

ssl_protocols TLSv1 TLSv1.1 TLSv1.2;
ssl_dhparam /etc/nginx/dhparam.pem; ###파일을 생성하려면 CLI에서 명령어를 다음과 같이: sudo openssl dhparam -out /etc/nginx/dhparam.pem 2048
ssl_ciphers ECDHE-RSA-AES256-GCM-SHA512:DHE-RSA-AES256-GCM-SHA512:ECDHE-RSA-AES256-GCM-SHA384:DHE-RSA-AES256-GCM-SHA384:ECDHE-RSA-AES256-SHA384; ##강력한 알고리즘 사용
ssl_ecdh_curve secp384r1;

ssl_prefer_server_ciphers on;
ssl_stapling on;
ssl_stapling_verify on;

resolver 8.8.8.8 8.8.4.4; ## 구글이 제공하는 네임서버로 인증서 발급업체와 통신
add_header Strict-Transport-Security "max-age=15768000; includeSubdomains; preload";
add_header X-Content-Type-Options nosniff;

############# iframe 사용 옵션. 참고: http://bit.ly/x-frames #############
add_header X-Frame-Options "SAMEORIGIN"; ####같은 도메인에서만 iframe 허용
####add_header X-Frame-Options DENY; ####모든 도메인에서 iframe 허용
####add_header X-Frame-Options "ALLOW-FROM https://example.com"; #####특정 도메인에서만 허용
```
- __ssl_protocol__ : ssl 프로토콜에는 보안등급과 버전이 존재
- __ssl_dhparam__ : dhparam 의 경로가 어디있는지 설정
- __add_header X-Frame-Options__ : 개인 웹사이트 내용이 html의 iframe 태그를 통해서 그대로 다른 웹페이지에 임베딩되는 것을 제한하기 위한 옵션
 - 나머지 옵션에는 `DENY` 와 `ALLOW-FROM {domain}` 이 있다.
 - X-Frame-Options 은 브라우저별로 호환이 다르다.
 
__nginx 라우팅 HTTPS 설정 및 www 리다이렉트 설정__<br>
WWW 서브도메인을 메인도메인으로 리다이렉팅 또는 그 반대로 해주는 부분은 DNS 설정의 cname 설정으로도 할 수 있다.<br>
하지만 DNS 설정상에서 상세한 컨트롤이 안되고 HTTPS 리다이렉팅은 불가하기 때문에 웹서버에서 설정하는 것이 좋다.<br>

아래와 같이 수행한다.<br>
> http://www.gulagbu.com 👉 https://www.gulagbu.com<br>
> https://www.gulagbu.com 👉 https://gulagbu.com<br>

__nginx 라우팅 설정 파일 수정__<br>
아래와 같이 한다. 
> ssl 명령을 추가한 부분을 살펴본다. pem 파일 위치설정과 함께 ssl.conf include 한 부분도 살펴본다.
> 추가적으로 `location` block 에서 redirect 를 수행하는 구문이 있다.
```javascript

user nginx;
worker_processes auto;
error_log /var/log/nginx/error.log;
pid /run/nginx.pid;

# Load dynamic modules. See /usr/share/doc/nginx/README.dynamic.
include /usr/share/nginx/modules/*.conf;

events {
    worker_connections 1024;
}

http {
    log_format  main  '$remote_addr - $remote_user [$time_local] "$request" '
                      '$status $body_bytes_sent "$http_referer" '
                      '"$http_user_agent" "$http_x_forwarded_for"';

    access_log  /var/log/nginx/access.log  main;

    sendfile            on;
    tcp_nopush          on;
    tcp_nodelay         on;
    keepalive_timeout   65;
    types_hash_max_size 2048;

    include             /etc/nginx/mime.types;
    default_type        application/octet-stream;

    # Load modular configuration files from the /etc/nginx/conf.d directory.
    # See http://nginx.org/en/docs/ngx_core_module.html#include
    # for more information.
    include /etc/nginx/conf.d/*.conf;

    server {
        listen       80 default_server;
        listen       [::]:80 default_server;
        server_name  gulagbu.com www.gulagbu.com;
        # root         /usr/share/nginx/html;
        root /home;

        # Load configuration files for the default server block.
        include /etc/nginx/default.d/*.conf;

        # include 추가. ssl 설정관련 : location block
        include /etc/nginx/snippets/letsencrypt.conf;

        location / {
            # http --> https redirect
            return 301 https://gulagbu.com$request_uri;
        }

        location ~/web-resources/\.(png)$ {
            # http --> https redirect
            return 301 https://gulagbu.com$request_uri;
        }

        error_page 404 /404.html;
            location = /40x.html {
        }

        error_page 500 502 503 504 /50x.html;
            location = /50x.html {
        }
    }

##############################################
# Settings for a TLS enabled server. (HTTPS) #
##############################################

    server {
        listen          443 ssl http2;
        listen          [::]:443 ssl http2;
        server_name     www.gulagbu.com;
        root            /home;

        ######################################################################
        # pem 파일 위치 설정.
        ######################################################################
        ssl_certificate /etc/letsencrypt/live/gulagbu.com/cert.pem;
        ssl_certificate_key /etc/letsencrypt/live/gulagbu.com/privkey.pem;
        ssl_trusted_certificate /etc/letsencrypt/live/gulagbu.com/fullchain.pem;

        ###############################
        # SSL 설정 내용을 include 한다.
        ###############################
        include /etc/nginx/snippets/ssl.conf;

        location / {
            # http --> https redirect
            return 301 https://gulagbu.com$request_uri;
        }

        location ~/web-resources/\.(png)$ {
            return 301 https://gulagbu.com$request_uri;
        }

    }

    ######################################################
    # nginx reverse proxy 관련
    ######################################################
    upstream tomcat {
        ip_hash;                        # Load Balance methods 설정.
        server 127.0.0.1:8081;          # 8081 포트를 80 포트로 서비스할 수 있도록 설정.
    }

    server {
        listen       443 ssl http2;
        listen       [::]:443 ssl http2;
        server_name  gulagbu.com;
        client_max_body_size 20M;
#        root         /usr/share/nginx/html;
        root /home;
        
        # static resources 관련 try_files

        # SSL 인증서 관련 설정
        ssl_certificate /etc/letsencrypt/live/gulagbu.com/cert.pem;
        ssl_certificate_key /etc/letsencrypt/live/gulagbu.com/privkey.pem;
        ssl_trusted_certificate /etc/letsencrypt/live/gulagbu.com/fullchain.pem;

        # SSL 설정 내용을 include 한다.
        include /etc/nginx/snippets/ssl.conf;

        # reverse proxy 설정
        location / {
            try_files $uri $uri/ @proxy;
        }

        location @proxy {
            proxy_set_header    HOST $http_host;
            proxy_set_header    X-Real-IP $remote_addr;
            proxy_set_header    X-Forwarded-For $proxy_add_x_forwarded_for;
            proxy_set_header    X-Forwarded-Proto $scheme;
            proxy_set_header    X-NginX-Proxy true;
            proxy_pass          http://tomcat;
            proxy_redirect      off;
            charset             utf-8;
        }

        # 웹서버에 저장된 이미지 파일 경로
        location ~/web-resources/\.(png)$ {
            expires 3d;
            alias /web-resources;
        }

#        error_page 404 /404.html;
#            location = /40x.html {
#        }

#        error_page 500 502 503 504 /50x.html;
#            location = /50x.html {
#        }
    }
}
```
