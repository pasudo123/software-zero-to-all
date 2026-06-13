## fd

`fd` 는 파일과 디렉터리 이름을 빠르게 찾는 도구다.
기본적으로 사용법이 단순하고, 색상 출력과 ignore 규칙을 지원해서 `find` 보다 일상적인 검색에 쓰기 편하다.

## 언제 쓰는가

- 파일명이나 디렉터리명을 빠르게 찾고 싶을 때
- 특정 확장자의 파일만 보고 싶을 때
- `.gitignore` 에 걸리는 파일은 제외하고 검색하고 싶을 때
- 검색 결과를 `fzf`, `xargs`, editor 와 조합하고 싶을 때

## 기본 사용법

```bash
# 이름에 README 가 들어간 파일/디렉터리 찾기
fd README

# 현재 디렉터리 아래 모든 항목 보기
fd .

# command 디렉터리 안에서 command 가 들어간 파일 찾기
fd command command
```

`fd <패턴> <검색경로>` 형태로 자주 사용한다.
패턴을 생략하기보다 `.` 을 넣으면 전체 목록을 보기 쉽다.

## 타입과 확장자 필터

```bash
# 파일만 검색
fd --type f

# 디렉터리만 검색
fd --type d

# Markdown 파일만 검색
fd --extension md

# command 디렉터리 아래 Markdown 파일만 검색
fd --type f --extension md command
```

짧은 옵션도 사용할 수 있다.

```bash
fd -t f -e md command
```

## 숨김 파일과 ignore 처리

```bash
# 숨김 파일 포함
fd --hidden

# .gitignore 규칙 무시
fd --no-ignore

# 숨김 파일 포함 + ignore 규칙 무시
fd --hidden --no-ignore
```

기본적으로 `fd` 는 숨김 파일을 제외하고, `.gitignore` 같은 ignore 규칙을 따른다.
일반적인 프로젝트 탐색에는 이 기본값이 편하지만, 설정 파일까지 찾아야 한다면 `--hidden` 을 같이 쓴다.

## 제외 패턴

```bash
# node_modules 제외
fd --exclude node_modules

# build 디렉터리 제외
fd --exclude build
```

여러 제외 조건이 필요하면 `--exclude` 를 반복해서 사용한다.

## fzf 와 조합하기

```bash
# 파일 하나를 선택
fd --type f | fzf

# 선택한 Markdown 파일 열기
vim "$(fd --type f --extension md | fzf)"
```

`fd` 로 후보를 만들고 `fzf` 로 고르는 흐름은 파일을 빠르게 찾을 때 자주 쓴다.

## find 와의 차이

`find` 는 POSIX 계열 시스템에 기본으로 있고 더 세밀한 조건 표현이 가능하다.
반면 `fd` 는 일상적인 파일명 검색에 필요한 옵션이 단순하고, ignore 규칙을 자연스럽게 반영한다.
복잡한 파일 속성 조건이 필요하면 `find`, 이름 기반 검색은 `fd` 를 먼저 떠올리면 된다.

## reference

- https://github.com/sharkdp/fd
