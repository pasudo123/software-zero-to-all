## httpie

`httpie` 는 터미널에서 HTTP API 를 호출하기 위한 CLI 도구다.
`curl` 보다 사람이 읽기 쉬운 문법을 제공하고, JSON 요청/응답을 다룰 때 기본 출력이 편하다.

## 언제 쓰는가

- REST API 를 빠르게 호출해보고 싶을 때
- JSON body 를 간단한 문법으로 보내고 싶을 때
- 응답 header, status code, body 를 터미널에서 확인하고 싶을 때
- `jq` 와 조합해서 API 응답의 일부만 추출하고 싶을 때

## 기본 사용법

```bash
# GET 요청
http GET https://api.github.com/repos/pasudo123/dotfiles

# method 를 생략하면 기본적으로 GET 요청
http https://api.github.com/repos/pasudo123/dotfiles

# POST 요청
http POST https://httpbin.org/post name=pasudo role=dev
```

`key=value` 형태는 JSON body 로 전송된다.
API 테스트를 처음 할 때 `curl -H ... -d ...` 보다 짧게 쓸 수 있다.

## query parameter

query parameter 는 `==` 로 표현한다.

```bash
http GET https://api.github.com/search/repositories q==dotfiles per_page==5
```

`q==dotfiles` 는 URL 의 `?q=dotfiles` 와 같은 의미다.
반대로 `q=dotfiles` 는 JSON body 필드로 해석된다.

## header 보내기

header 는 `Header-Name:value` 형태로 쓴다.

```bash
http GET https://api.github.com/repos/pasudo123/dotfiles \
  Accept:application/vnd.github+json
```

인증 토큰처럼 민감한 값은 문서에 실제 값을 남기지 않는다.

```bash
http GET https://api.github.com/user \
  Authorization:"Bearer <TOKEN>"
```

## JSON body

```bash
# 문자열 값
http POST https://httpbin.org/post name=pasudo role=dev

# 숫자/boolean/null 은 := 를 사용하면 JSON 타입으로 보낸다.
http POST https://httpbin.org/post id:=1 active:=true deleted:=null

# 배열이나 객체도 := 로 보낼 수 있다.
http POST https://httpbin.org/post tags:='["cli", "http"]' meta:='{"source":"httpie"}'
```

`=` 는 문자열로 보내고, `:=` 는 JSON 타입으로 보낸다고 생각하면 된다.

## 응답 확인 옵션

```bash
# header 와 body 를 함께 보기
http --print=hb GET https://api.github.com/repos/pasudo123/dotfiles

# status/header/body 전체 보기
http --print=Hhb GET https://api.github.com/repos/pasudo123/dotfiles

# body 만 출력
http --body GET https://api.github.com/repos/pasudo123/dotfiles
```

옵션 이름을 매번 외우기 어렵다면 처음에는 기본 출력으로 확인하고, 필요한 경우에만 `--print` 를 붙인다.

## jq 와 조합하기

```bash
http --body GET https://api.github.com/repos/pasudo123/dotfiles \
  | jq '{name: .name, default_branch: .default_branch, clone_url: .clone_url}'
```

`httpie` 는 응답을 보기 좋게 출력하지만, 원하는 필드만 뽑아낼 때는 `jq` 와 조합하면 좋다.

## 주의사항

- `=` 와 `==` 는 의미가 다르다. body 필드는 `=`, query parameter 는 `==` 를 쓴다.
- 숫자나 boolean 을 JSON 타입으로 보내려면 `:=` 를 쓴다.
- token, password, cookie 같은 값은 명령어 history 나 문서에 남기지 않도록 주의한다.
- 복잡한 재시도, 타임아웃, 다운로드 작업은 `curl` 이 더 익숙한 선택일 수 있다.

## reference

- https://httpie.io/docs/cli
