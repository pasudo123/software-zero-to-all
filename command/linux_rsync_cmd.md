## rsync command 👀
* 파일 또는 디렉토리 전체를 복사하는 명령어
* 원격서버에 있는 것도 복사할 수 있다.
* 중간에 파일이 변경되는 경우에는 변경된 파일을 그대로 덮어쓴다.

## rsync -r
rsync `-r` 옵션 (recursive)
```shell
// dir1 에 있는 파일 전체를 dir2 로 모두 옮긴다.
$ rsync -r dir1/ dir2
```

아래와 같이 디렉토리로 표시하고, -r 옵션이 없으면 에러가 난다.
```shell
// skipping directory /dir1/.
$ rsync dir1/ dir2
```

## rsync -a
아카이브 옵션, 심볼릭 링크, groups, owners, 파일생성시간 까지 모두 유지된다.   
일반적인 사용에 추천되는 플래그값이다.
```shell
$ rsync -a dir1/ dir2
```

## rsync -v
verbose 옵션이다. 결과에 대한 내용을 장황하게 풀어낸다. 
```shell
$ rsync -v dir1/ dir2
```

결과내용 (대략적으로)
```shell
building file list ... done
test100.txt
test101.txt
test102.txt
test103.txt
test104.txt
...생략...
test196.txt
test197.txt
test198.txt
test199.txt
test200.txt

sent 5583 bytes  received 2242 bytes  15650.00 bytes/sec
```

## rsync --delete
--delete 가 있음으로써, sour 쪽에 삭제된 파일이 있는 경우에 dest 에 존재하는 경우 삭제해버린다.
```shell
$ rsync --delete dir1/ dir2
building file list ... done
deleting test2.txt
./
...생략...
sent 304 bytes  received 70 bytes  748.00 bytes/sec
total size is 5  speedup is 0.01
```

## source directory 쪽에 `/` 를 붙이는 이유
아래 둘의 차이
```shell
// dir1 디렉토리 자체가 dir2 로 들어간다.
$ rsync -a dir1 dir2

// dir1 내 파일이 모두 dir2 로 들어간다.
$ rsync -a dir1/ dir2
```



## <a id="reference"></a> reference 🚀
* https://www.digitalocean.com/community/tutorials/how-to-use-rsync-to-sync-local-and-remote-directories