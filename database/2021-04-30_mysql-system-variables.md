# mysql 시스템 변수
mysql 서버는 기동하면서, 설정 파일의 내용을 읽어 메모리나 작동방식을 초기화하고, 접속된 사용자를 제어하기 위해 이러한 값을 별도로 저장한다.   
mysql 서버에서는 이렇게 저장된 값을 변수(variable) 이라고 표현하며, 더 정확하게는 **시스템 변수** 라고 칭한다.

시스템 변수의 확인
```mysql
$ SHOW VARIABLES

$ SHOW GLOBAL VARIABLES
```
* 시스템 변수 값이 어떻게 mysql 서버와 클라이언트에 영향을 미치는지 판단하기 위해선 각 변수는 두 개로 구분시켜야 한다.
* 시스템 변수는 적용 범위에 따라 아래와 같이 나뉜다.
  * global variable (글로별 변수)
  * session variable (세션 변수)

## global variable
* mysql 인스턴스에서 전체적으로 영향을 미치는 시스템 변수를 의미한다.
* mysql 서버 자체의 설정일 때가 많다.

## session variable
* mysql 클라이언트가 mysql 서버에 **접속할 때**, 기본적으로 부여하는 옵션의 기본값을 제어하는데 사용된다.

# mysql 시스템 변수들
### max_connection
* mysql 서버가 최대한 허용할 수 있는 클라이언트의 연결 수를 제한하는 설정이다.
* max_connection 값을 늘리는 경우에, 무거운 쿼리 실행 시 mysql 응답은 자연히 느려지고 더 많은 커넥션을 생성하게 된다.
  * max_connection 을 적당히 늘려야 한다.
* 애플리케이션 단의 connection 과 mysql max_connection 설정이 중요하다.

### thread_cache_size
* mysql 연결 시에, 커넥션을 맺고나서 작업요청을 처리하는 주체이다. (커넥션 == 스레드라 생각해도 되지만, 약간 다르다.)
* mysql 서버가 스레드를 캐시하려면 메모리를 사용하기 때문에, 앱단에서 커넥션 풀을 가지고 있는게 좋다.

> mysql `connection` 도 tcp 기반이다.

### wait_timeout (초 단위)
* mysql 서버가 연결된 클라이언트가 wait_timeout 에 지정된 시간동안 아무런 요청이 없이 대기하는 경우 mysql 서버는 해당 커넥션을 강제로 종료해버린다.
* 이 설정값의 시간단위는 초이며, 28800초 (8시간) 가 기본값으로 설정되어 있다.
* 사실 앞 단의 많은 네트워크를 거치기 때문에 커넥션이 예상시간보다 빨리 끊어지는 문제는 각 서버 운영체제의 `idle-timeout` 등의 문제인 경우가 많다.

# reference
* real mysql
