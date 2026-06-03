## ripgrep

`ripgrep` 은 `rg` 명령으로 사용하는 빠른 텍스트 검색 도구다.
코드나 Markdown 문서에서 문자열을 찾을 때 `grep` 보다 더 편한 기본값을 제공한다.

## 언제 쓰는가

- 코드베이스에서 문자열을 빠르게 찾고 싶을 때
- 특정 디렉터리나 파일 타입만 대상으로 검색하고 싶을 때
- `.gitignore` 에 걸리는 파일은 제외하고 검색하고 싶을 때
- 검색 결과를 `fzf` 로 좁혀 보고 싶을 때

## 기본 사용법

```bash
# 현재 디렉터리 아래에서 TODO 검색
rg "TODO"

# command 디렉터리 안에서 jq 검색
rg "jq" command

# 대소문자 구분 없이 검색
rg -i "spring"
```

기본 출력은 `파일:라인:내용` 형태다.
파일명과 라인 번호가 같이 보여서 바로 위치를 찾아가기 좋다.

## 파일 타입과 glob

```bash
# Markdown 파일에서만 검색
rg --glob "*.md" "Brewfile"

# Kotlin 파일에서만 검색
rg --glob "*.kt" "suspend"

# 특정 디렉터리 제외
rg "TODO" --glob "!build/**"
```

glob 은 포함 조건과 제외 조건을 모두 표현할 수 있다.
제외 조건은 `!` 로 시작한다.

## ignore 와 숨김 파일

```bash
# 숨김 파일 포함
rg --hidden "TOKEN"

# ignore 규칙 무시
rg --no-ignore "TOKEN"

# 숨김 파일 포함 + ignore 규칙 무시
rg --hidden --no-ignore "TOKEN"
```

`rg` 는 기본적으로 `.gitignore` 를 따른다.
설정 파일이나 숨김 디렉터리까지 확인해야 할 때만 위 옵션을 사용한다.

## 결과 형태 바꾸기

```bash
# 파일명만 출력
rg -l "jq"

# 매칭되지 않는 파일명만 출력
rg -L "jq"

# 매칭 전후 문맥 2줄 출력
rg -C 2 "transaction"

# 라인 번호 포함
rg -n "httpie"
```

`-l` 은 “어떤 파일에 있는지”만 필요할 때 유용하다.
`-C` 는 앞뒤 문맥까지 봐야 의미가 잡히는 문서 검색에서 편하다.

## fzf 와 조합하기

```bash
# 검색 결과 중 하나 선택
rg -n "TODO" | fzf

# Markdown 문서에서 reference 가 들어간 위치 선택
rg -n --glob "*.md" "reference" | fzf
```

결과가 많으면 `rg` 로 찾고 `fzf` 로 고르는 흐름이 좋다.

## 주의사항

- 정규표현식 문법을 사용하므로 특수문자를 찾을 때는 escape 가 필요할 수 있다.
- ignore 규칙 때문에 검색되지 않는 파일이 있을 수 있다.
- 바이너리 파일이나 매우 큰 로그까지 대상으로 삼으면 결과가 많아지므로 검색 범위를 먼저 좁히는 편이 좋다.

## reference

- https://github.com/BurntSushi/ripgrep
