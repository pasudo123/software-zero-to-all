## shell variable 👀
* 값이나 명령어를 변수에 할당할 시, `variable={value}` 형식으로 equals(=) 에 딱 맞게 붙여준다.
```shell
number=10
echo $number        // 10 

newNumber = 10      // 띄어쓰기가 존재하면 안된다.!!
echo $newNumber     // 10 을 출력하지 못함.

show_list='ls -al'
echo $show_list     // 'ls -al' 명령어 실행
```