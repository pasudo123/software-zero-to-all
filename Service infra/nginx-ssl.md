# nginx SSL 설정하기

### 1. 인증서 발급받기
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

`openssl x509 -noout -dates -in /etc/letsencrypt/live/{domain}/cert.pem` 를 입력하면 서버에 설치된 렛츠인스크립트 만료날짜를 확인할 수 있다.
```bash
notBefore=Dec 30 02:04:33 2019 GMT
notAfter=Mar 29 02:04:33 2020 GMT
```
> GMT 기준이기 때문에 우리나라 시간을 9시간 더해서 계산해준다. GMT 는 런던지검의 협정 세계시이다.
