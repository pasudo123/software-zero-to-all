## MACBOOK 세팅
맥북 초기세팅

## 목차
<!-- TOC -->
  * [MACBOOK 세팅](#macbook-세팅)
  * [목차](#목차)
  * [처음맥북](#처음맥북)
  * [폰트](#폰트)
  * [생산성 도구](#생산성-도구)
  * [터미널](#터미널)
  * [sdkman 설치](#sdkman-설치)
  * [Intellij Live Templates 설정](#intellij-live-templates-설정)
  * [Intellij Jira 연동](#intellij-jira-연동)
  * [Intellij sync & backup 기능 설정](#intellij-sync--backup-기능-설정)
  * [Intellij plugins](#intellij-plugins)
  * [DataGrip sync & backup 기능 설정](#datagrip-sync--backup-기능-설정)
  * [Datagrip global Datasource 설정](#datagrip-global-datasource-설정)
  * [Medis redis properties export](#medis-redis-properties-export)
  * [http tool](#http-tool-)
  * [k8s openlens 설치](#k8s-openlens-설치)
  * [charles 설치](#charles-설치)
<!-- TOC -->

## 처음맥북
* 문서자체가 어느정도 오래되었기 때문에 모든걸 다 따라할 필요는 없고 참고만 한다.
* https://subicura.com/mac/
* 데스크탑 핫코너
  * 5시 방향 미션컨트롤
  * 7시 방향 잠금화면
* 시간표시 24시간 표기 및 초단위 노출
* 배터리는 `%` 노출
* 한글입력 시 백틱 입력되도록 설정 (https://www.korecmblog.com/backtick-fix/)

## 폰트
[Cascadia-code](https://github.com/microsoft/cascadia-code) 를 기본사용
- cascadia-code 이용
- 13 Font 이용

## 생산성 도구
| 이름       | 설명                     | 링크 |
|----------|------------------------|----|
| RunCat   | 상태바 고양이 노출 (PC 사용량 체크) | https://apps.apple.com/kr/app/runcat/id1429033973?mt=12   |
| Keka     | MacOS 압축프로그램           |  https://www.keka.io/ko/  |
| Maccy    | 클립보드 매니저 프로그램          |  https://maccy.app/  |
| caffeine | 절전모드 실행방지 프로그램         | https://www.caffeine-app.net/ |



## 터미널
기존 터미널을 사용하지 않는다.
- [iterm2](https://iterm2.com/index.html)
  - Settings > General > Settings 에 iTerm2 의 세팅을 export/import 할 수 있다.
- [powerlevel10k](https://github.com/romkatv/powerlevel10k)
  - iterm2 를 특정 테마에 맞게 하는게 아닌 사용자가 직접 커스텀
  - .zshrc 파일의 내용을 그대로 옮겨서 사용할 수 있음
- [하이라이팅 : zsh-syntax-highlighting](https://github.com/zsh-users/zsh-syntax-highlighting)
  - homebrew 설치 이후에 .zshrc 적용 후 source 커맨드
- [자동완성 : zsh-autosuggestions](https://github.com/zsh-users/zsh-autosuggestions)
  - homebrew 설치 이후에 .zshrc 적용 후 source 커맨드
- `~/.zshrc` 파일에 모두 들어갈 수 있도록 한다. 아래 커맨드 이용
  - `source ~/.zshrc`
- `git` 명령어도 포함시킨다.
```text
alias gitlog="git log --all --decorate --oneline --graph"
```
- `kubectl` 명령어도 포함시킨다.
```text
alias k=kubectl
```

그 외 기타 사용방법
```text
## iterm delete 
> 한 줄 지우기
* profiles -> Open Profile -> edit profiles -> keys
* shortcut : cmd + backspace
    * action : send hex codes 
    * send : 0x15

> 한 단어씩 지우기 
* profiles -> Open Profile -> edit profiles -> keys
* shortcut : option + backspace
    * action : send hex codes 
    * send : 0x17

## iterm 단축키 모음 (mac 기준으로 설명한다.)
> CMD + shift + I 
* 동시에 입력할 수 있도록 도와준다.

> CMD + D : iterm 
* 창을 가로로 추가한다.

> CMD + shift + D : iterm 
* 창을 세로로 추가한다.
```


## sdkman 설치
https://sdkman.io/
fx 라고 작성된건 JavaFx 가 설치된 버전 의미. -> 필요없음


## Intellij Live Templates 설정
Settings > Editor > Live Templates 에서 설정가능
* kotlin 에서 `TT` 를 입력
https://velog.io/@max9106/IntelliJ-Live-Template

## Intellij Jira 연동
Tools > Tasks > Servers 에서 지라연결 (사내 id, pwd 입력)

## Intellij sync & backup 기능 설정
* Settings > Backup And Sync Enabled 설정
  * 계정 기준으로 설정하도록 한다.

## Intellij plugins
* CodeGlance Pro
* Key Promoter

## DataGrip sync & backup 기능 설정
* Settings > Backup And Sync Enabled 설정
  * 계정 기준으로 설정하도록 한다.

## Datagrip global Datasource 설정
* DataSource 에서 Global Datasource 를 설정하여 타 PC 에서 datasource 를 들고오도록 한다.

## Medis redis properties export
* Medis redis properties 를 export 하고 import 한다

## http tool 
* rapid API
* httpie
* postman

## k8s openlens 설치

## charles 설치
앱 payload 확인