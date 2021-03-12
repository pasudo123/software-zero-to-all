# Revert 
원격에 올라간 커밋을 되돌린다.

## 상황
* code review 를 수행하는 회사가 있다고 가정
* git repository 에 여러 명의 사람이 같이 코드를 푸시하고, `main` 브랜치로 pr 을 날린다.
* `feature/board` 브랜치로 작업을 하고 pr 날리고 `merge` 까지 수행되었다.
* `feature/board` 브랜치에 잘못된 사항이 있어서 다시 날려야 하는 상황이 생겻다. !!!
* 기존에 `merge` 된 pr 건에 대해서 `revert` 를 수행한다.
* 그리고 `revert` 된 pr 은 새롭게 `main` 브랜치로 pr 을 날리게 된다.
  * 해당 건은 일단 `merge` 가 되어야 한다. 왜냐하면 `main` 브랜치에서 작업한 건은 사라져야 하기 때문!!
* 그리고 로컬에서 작업한 `feature/board` 브랜치를 수정하고 push 이후에 다시 pr 을 날리면 된다 !! 
  * 이후 작업은 코드리뷰 확인하고 `sqush merge` 를 수행하면 끝~
