## dev/null 👀
* dev/null 파일은 비어있고, 해당 경로로 전송된 데이터는 버려진다.
* 출력할 필요가 없을 때 사용하는 명령어다.
```shell
❯ echo "Hello World"
Hello World

~                                                                                                                                  
❯ echo "Hello World" &> /dev/null
```
