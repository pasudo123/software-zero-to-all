## awk 👀
* `오크` 리고 부른다.
* 기본 기능은 텍스트 형태로 되어있는 입력데이터를 행과 단어 별로 처리해 출력해주는 것.
* awk 는 명령파일과 입력파일 두 가지 데이터를 받아 실행
    * 명령파일은 awk 가 어떤 데이터 규칙에 따라 처리할 지 지시
* 사용방법
```shell
$ awk '{function-name}'
$ awk '{print $1}'              // 첫번째 필드 출력
$ awk '{print}' ./sample.txt    // sample.txt 의 모든 레코드 출력

sample.txt 문서

|----|----|----|----|
| $1 | $2 | $3 | $4 |  ==>  ... $n 필드
|----|----|----|----|
|  A | 10 | 20 | 30 |  ==>      $0 레코드
|  B | 40 | 50 | 60 |
|  C | 70 | 80 | 90 |
| .. | .. | .. | .. |
|----|----|----|----|
```
* awk print 이용
```shell
// 도커의 특정한 이미지의 IMAGE ID 출력
$ docker images | grep {name}:{tag} | awk '{print $3}'

// 특정 프로세스의 pid 출력
$ ps -ef | grep java | awk '{print $2}'
```

# <a id="reference"></a> reference 🚀
* [awk 명령어 참고링크](https://recipes4dev.tistory.com/171)