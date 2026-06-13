## fzf

`fzf` 는 터미널에서 목록을 퍼지 검색(fuzzy finder)으로 고르는 도구다.
파일, 명령어 history, git branch, 검색 결과처럼 줄 단위로 출력되는 목록을 빠르게 좁혀서 선택할 수 있다.

## 언제 쓰는가

- 이전에 실행한 명령어를 다시 찾고 싶을 때
- 파일 목록 중 하나를 골라 열고 싶을 때
- git branch, process, 로그 검색 결과처럼 후보가 많을 때
- `fd`, `rg`, `git` 같은 명령의 출력 결과를 대화식으로 선택하고 싶을 때

## 기본 사용법

```bash
# 입력 목록에서 하나 선택
printf "apple\nbanana\norange\n" | fzf

# 파일 목록 중 하나 선택
fd . | fzf

# 이전 명령어 목록에서 검색
history | fzf
```

`fzf` 는 표준 입력으로 들어온 줄 목록을 대상으로 검색한다.
선택한 결과는 표준 출력으로 나오기 때문에 다른 명령어와 조합하기 좋다.

## 파일 선택

```bash
# 현재 디렉터리 아래 파일 선택
fd --type f | fzf

# Markdown 파일만 선택
fd --type f --extension md | fzf

# 선택한 파일을 vim 으로 열기
vim "$(fd --type f | fzf)"
```

파일명이 공백을 포함할 수 있으면 `"` 로 감싸는 편이 안전하다.

## git branch 선택

```bash
# 브랜치 목록에서 하나 선택
git branch --format='%(refname:short)' | fzf

# 선택한 브랜치로 이동
git switch "$(git branch --format='%(refname:short)' | fzf)"
```

## rg 결과와 조합하기

```bash
# TODO 가 들어간 라인 중 하나 선택
rg -n "TODO" | fzf

# command 문서 안에서 jq 라는 단어가 들어간 위치 선택
rg -n "jq" command | fzf
```

`rg -n` 은 `파일:라인:내용` 형태로 출력하므로, 검색 결과가 많을 때 `fzf` 로 좁혀보기 좋다.

## 자주 쓰는 키

```text
CTRL-R: shell history 검색
CTRL-T: 파일 검색
ALT-C : 디렉터리 이동
```

위 키 바인딩은 shell integration 이 설정되어 있어야 동작한다.
Homebrew 로 설치했다면 shell 설정 파일에서 fzf 초기화 스크립트를 로드해야 한다.

## 주의사항

- `fzf` 는 입력 목록을 고르는 도구라서, 어떤 목록을 넘길지 먼저 정하는 것이 중요하다.
- 파일 검색은 `find` 보다 `fd` 와 조합하면 보통 더 편하다.
- 문자열 검색 결과는 `grep` 보다 `rg` 와 조합하는 경우가 많다.

## reference

- https://github.com/junegunn/fzf
