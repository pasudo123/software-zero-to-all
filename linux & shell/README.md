# linux command 
* a list of frequently used commands.

## <a id="index"></a>index
* linux
* bash
    * [변수할당](#variable-command)
    * [awk 명령어](#awk-command)
        * [print](#awk-print-command)
* reference
    * [awk 명령어 참고링크](https://recipes4dev.tistory.com/171)

## <a id="variable-command"></a> 변수할당
* 값이나 명령어를 변수에 할당할 시, `variable={value}` 형식으로 equals(=) 에 딱 맞게 붙여준다.
```shell
number=10
echo $number        // 10 

newNumber = 10      // 띄어쓰기가 존재하면 안된다.!!
echo $newNumber     // 10 을 출력하지 못함.

show_list='ls -al'
echo $show_list     // 'ls -al' 명령어 실행
```

<BR>

## <a id="awk-command"></a> awk 명령어 (= 오크 명령어)
* [목차이동](#index)
* 기본 기능은 텍스트 형태로 되어있는 입력데이터를 행과 단어 별로 처리해 출력해주는 것.
* awk 는 명령파일과 입력파일 두 가지 데이터를 받아 실행
    * 명령파일은 awk 가 어떤 데이터 규칙에 따라 처리할 지 지시
* 기본구조
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

<BR>

## <a id="awk-print-command"></a> awk print 명령어
* [목차이동](#index)
```shell
// 특정 이미지의 IMAGE ID 출력
$ docker images | grep {name}:{tag} | awk '{print $3}'
```