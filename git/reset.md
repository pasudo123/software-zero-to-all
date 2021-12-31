# git reset

### git reset --soft HEAD
```shell
// [1] HEAD 를 이전 다음 커밋할 스냅샷으로 옮긴다. : index 트리로 돌린다. : git status 하면 녹색으로 뜸
$ git reset --soft HEAD~
```

### git reset --mixed HEAD
```shell
// [1] HEAD 를 이전 다음 커밋할 스냅샷으로 옮긴다. : index 트리로 돌린다.
// [2] index 를 현재 HEAD 가 가리키는 스냅샷으로 변경한다. : staged 에서 unstaged 로 되돌림, git status 하면 빨갛게 뜸
$ git reset --mixed HEAD~
```

### git reset --hard HEAD
```shell
// ** 쓰면 위험하다.
// [1] HEAD 를 이전 다음 커밋할 스냅샷으로 옮긴다. : index 트리로 돌린다.
// [2] index 를 현재 HEAD 가 가리키는 스냅샷으로 변경한다.
// [3] working directory 트리를 마지막 커밋한 시점으로 되돌린다. : 로컬에서 작업한 결과물이 사라짐.
$ git reset --hard HEAD~
```

# reference
* https://git-scm.com/book/ko/v2/Git-%EB%8F%84%EA%B5%AC-Reset-%EB%AA%85%ED%99%95%ED%9E%88-%EC%95%8C%EA%B3%A0-%EA%B0%80%EA%B8%B0