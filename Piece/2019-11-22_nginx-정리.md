# nginx 정리.

### nginx 설치
- VPS 코노하를 기준으로 설명
- conoha : CentOS 7.x 이고, yum 으로 설치
```
> yum install nginx
> yum remove nginx
```

### nginx 시작, 중지, 재시작 명령어
```
> nginx -s {signal}
> nginx -s stop     //  즉시 종료한다.
> nginx -s quit     //  마지막 요청까지 처리한 뒤 종료한다.
> nginx -s reload   //  설정파일을 다시 리로드하고 재시작한다.
```

### nginx 설정 위치
1. /usr/local/nginx/conf
2. /etc/nginx
3. /use/local/etc/nginx
> nginx 설정은 nginx.conf.d 라는 파일을 건드려 설정하여야 한다.   
> 위의 1 ~ 3 안에 설정파일이 존재한다.

### nginx 설정파일 내 지시문 (Directives)
- __user__
  -  ```user nginx```
  - nginx 를 기동하면 master process & worker process & cache process 이렇게 총 3개의 프로세스를 기동한다.
  - 위의 세개의 프로세스에서 nginx 는 worker process 가 실질적인 동작을 수행한다.
    - 만약 ```user root``` 로 되어있다면 worker process 는 root 권한으로 동작한다. 악의적 사용자가 이를 제어하게 된다면 위험하다.
    - 이런 위험 때문에 일반유저의 권한으로 접속할 수 없는 유저를 만들어야 한다. (쉘 접속 차단)
    - ```www-data```, ```www```, ```nginx``` 와 같은 이름으로 유저를 만든다.
  - ```nginx 가 웹서버 프로세스를 실행할 사용자를 정의```
- __worker_process__
  - ```worker_process auto;```
  - nginx 가 싱글스레드 기반이기 때문에 core 의 개수를 맞추어 설정하기 위함이다. core 의 수보다 높은 숫자를 지정해도 아무 문제 없다. ```auto``` 로 지정하는 경우에는 nginx 가 알아서 지정해준다.
    - ```cat /proc/cpuinfo | grep "physical id"``` 를 리눅스 명령어로 치면, 해당 서버 내의 물리적 CPU 의 개수를 파악할 수 있다. 서로 번호가 달리 나와야 한다.
- __events__
  - 네트워크의 동작방법과 관련된 설정값을 가진다. (nginx 특징인 ```비동기 이벤트``` 처리방식에 대한 옵션을 설정)
  - 이벤트 블록의 지시어들은 이벤트 블록에서만 사용할 수 있다.
  - ```worker_connection``` 은 하나의 프로세스가 처리할 수 있는 커넥션의 수를 의미
- __http__
  - ```server```, ```location``` 의 루트 블록이라고 말할 수 있다.
  - 특정 도메인 또는 IP 주소에 대한 요청들을 컨트롤한다.
  - http 블록에서 설정된 값을 하위블록들은 상속한다. 
  - http 블록은 여러 개를 사용할 수 있지만, 관리상의 이유로 한번만 사용하는 것을 권장한다.
  - __server__
    - 하나의 웹사이트를 선언하는데 사용된다.
    - 가상 호스팅의 개념이다. (Virtual Host)
      - 예를들어 하나의 서버로 http://A.com 과 http://B.com 을 동시에 운영하고 싶다면 사용할 수 있는 방법이다.
  - __location__
    - ```server``` 블록 안에 특정 URL 을 처리하는 방법을 정의한다.
      - 예를들어 http://A.com/category 와 http://A.com/search 에 접근하는 요청을 다르게 하고 싶을 때 사용한다.

### nginx 퍼포먼스 관련.
- ```worker_process```
  - 프로세스 몇개를 생성할 것인지 지정하는 지시어.
- ```worker_connections```
  - 이벤트 안에서 사용하는 지시어로 몇 개의 접속을 동시에 처리할 것인지 지정하는 지시어.

> worker_process X worker_connection 을 조합하여 하나의 머신이 처리할 수 있는 커넥션의 양을 산출할 수 있다. worker_process = 4 이고 worker_connection = 1024 이면 4 X 1024 = 4096 개의 커넥션을 맺을 수 있다. 이러한 값을 설정하는 공식은 따로 없다. 
- REF
  - https://opentutorials.org/module/351/3334

### Load Balance
트래픽을 서버 그룹 내에서 로드밸런싱 하기 위한 설정
```
http {

  upstream {groupName} {
    server {server-name}
    server backend1.example.com
    server backend2.example.com
    server 192.0.0.1:8080
  }
}
```

위에서 설정한 서버 그룹을 로드밸런싱 하기위해 ```server``` 블록을 이용한다.
```
http {

  server {
    location / {
       proxy_pass http://{groupName}
    }
  }
}
```

```upstream``` 블록에 로드 밸런싱 알고리즘이 설정되어 있지 않았다. nginx 는 기본적으로 ```ROUND ROBIN``` 방식으로 로드밸런싱 한다.

### Load Balance Method 선택하기
1. Round Robin : ```default```
2. Least Connection : ```least_conn```
3. IP Hash : ```ip_hash```
- 클라이언트 IP 를 해시해서 특정 클라이언트는 특정 서버로 연결하도록 설정. (세션 클러스터링이 구성되지 않은 경우 유용 : 하나의 서버로만 가서 세션을 다른 서버와 동기화 할 필요가 없기 때문.)

> __세션 클러스터링이란 ?__    
> 클러스터링은 여러 대의 서버가 한가지 업무를 수행하는 것을 의미한다. 물리적으로는 분리되어 있어도 논리적으로는 하나인 것이다. 세션 클러스터링이란 WAS 가 두 대 이상 설치되어 있을 경우 세션으로 세션관리하는 것을 의미한다. 


## REF
- https://whatisthenext.tistory.com/123
- https://opentutorials.org/module/384/4526
