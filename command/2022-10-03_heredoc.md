## heredoc
커맨드라인을 여러줄에 걸쳐서 작성할 수 있게 도와준다.
```shell
<< [구분자]
//
// 명령어들
//
[구분자]
```

## 기본 사용법
```shell
$ vim ./heredoc.sh
$ chmod 755 ./heredoc.sh
```

작성내용
```shell
#!/bin/bash

current_date=$(date +%F)

cat << EOF
HELLO : $current_date
HELLO : $(pwd)
HELLO : $USER
EOF
```

출력내용
```shell
$ ./heredoc.sh
HELLO : 2022-10-03
HELLO : /Users/pasudo123/pasudo-repo
HELLO : pasudo123
```
* 'EOF' 를 다른 구분자로 작성해도 된다.

## 구분자를 큰 따옴표로 감싼 경우 : 변수를 사용할 수 없다.
```shell
#!/bin/bash

current_date=$(date +%F)

cat << "EOF"
HELLO : $current_date
HELLO : $(pwd)
HELLO : $USER
EOF
```

출력내용
```shell
❯ ./heredoc.sh
HELLO : $current_date
HELLO : $(pwd)
HELLO : $USER
```

## ssh 명령어와 같이 쓸 수 있음 : 리모트 서버에 접속 후 명령어 수행 시
```shell
#!/bin/bash

current_date=$(date +%F)

ssh -o StrictHostKeyChecking=no {account}@{host} << EOF
HELLO 1
HELLO 2
HELLO 3
EOF
```

## 참고
* https://linux.die.net/man/5/ssh_config