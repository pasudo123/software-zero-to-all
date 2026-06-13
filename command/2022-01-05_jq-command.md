## jq

`jq` 는 JSON 을 터미널에서 읽기 좋게 출력하거나 원하는 필드만 뽑아낼 때 사용하는 커맨드라인 JSON processor 다.
API 응답을 확인하거나, 로그/설정 파일처럼 JSON 으로 떨어지는 데이터를 빠르게 살펴볼 때 자주 쓴다.

## 언제 쓰는가

- JSON 응답을 보기 좋게 pretty print 하고 싶을 때
- 특정 필드만 추출하고 싶을 때
- 배열을 필터링하거나 새로운 객체 형태로 재구성하고 싶을 때
- `curl`, `httpie`, `gh`, `kubectl` 같은 CLI 출력과 조합하고 싶을 때

## 기본 사용법

```bash
# JSON 전체를 보기 좋게 출력
cat response.json | jq '.'

# 특정 필드 추출
cat response.json | jq '.name'

# 문자열 따옴표 없이 출력
cat response.json | jq -r '.name'
```

`jq` 의 필터는 작은따옴표로 감싸는 편이 안전하다.
셸에서 큰따옴표를 쓰면 `$`, `\` 같은 문자가 먼저 해석될 수 있다.

## 객체 필드 추출

예시 JSON 이 아래와 같다고 가정한다.

```json
{
  "name": "jq",
  "tap": "homebrew/core",
  "desc": "Lightweight and flexible command-line JSON processor",
  "versions": {
    "stable": "1.7.1"
  }
}
```

```bash
# 단일 필드
cat formula.json | jq '.name'

# 중첩 필드
cat formula.json | jq '.versions.stable'

# 여러 필드를 새 객체로 재구성
cat formula.json | jq '{name: .name, version: .versions.stable, desc: .desc}'
```

## 배열 다루기

```json
{
  "items": [
    { "name": "jq", "type": "json" },
    { "name": "ripgrep", "type": "search" },
    { "name": "fd", "type": "search" }
  ]
}
```

```bash
# 배열 전체
cat tools.json | jq '.items'

# 첫 번째 요소
cat tools.json | jq '.items[0]'

# 배열 안의 name 만 추출
cat tools.json | jq '.items[].name'

# type 이 search 인 항목만 추출
cat tools.json | jq '.items[] | select(.type == "search")'
```

## raw output

문자열 값을 다른 명령어에 넘길 때는 `-r` 옵션을 자주 쓴다.
따옴표가 포함된 JSON 문자열이 아니라 순수 문자열만 출력한다.

```bash
cat tools.json | jq -r '.items[].name'
```

출력 예시는 아래와 같다.

```text
jq
ripgrep
fd
```

## API 응답과 조합하기

```bash
# curl 응답을 jq 로 보기 좋게 출력
curl -s https://api.github.com/repos/pasudo123/dotfiles | jq '.'

# 필요한 필드만 추출
curl -s https://api.github.com/repos/pasudo123/dotfiles \
  | jq '{name: .name, default_branch: .default_branch, stars: .stargazers_count}'

# httpie 는 기본적으로 JSON 을 보기 좋게 출력하지만, jq 로 추가 필터링할 수 있다.
http GET https://api.github.com/repos/pasudo123/dotfiles \
  | jq -r '.clone_url'
```

## CSV 로 변환하기

`@csv` 를 사용하면 배열을 CSV 형태로 만들 수 있다.
실제 문자열만 필요하면 `-r` 옵션을 같이 쓴다.

```bash
cat formula.json | jq -r '[.name, .tap, .desc] | @csv'
```

## 주의사항

- 없는 필드를 조회하면 `null` 이 출력된다.
- `-r` 옵션은 문자열을 후속 명령어에 넘길 때 좋지만, JSON 구조를 유지해야 할 때는 쓰지 않는다.
- 큰 JSON 을 다룰 때는 먼저 `jq 'keys'`, `jq '.items[0]'` 처럼 구조를 좁혀가며 확인하는 편이 좋다.
- 민감한 응답을 문서나 로그에 남길 때는 token, password 같은 값을 마스킹한다.

## reference

- https://jqlang.org/
- https://formulae.brew.sh/formula/jq
