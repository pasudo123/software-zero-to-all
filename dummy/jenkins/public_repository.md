## github public repository 에서 젠킨스 연동

### 젠킨스 설치
* 젠킨스 설치는 도커파일을 이용해서 설치를 수행한다. 
* `docker pull jenkins/jenkins:lts-jdk11` 를 수행하였다. ([참고](https://hub.docker.com/r/jenkins/jenkins))
* 그 이외의 디테일한 절차들은 생략한다.

### 아이템, 파이프라인 등록
<kbd><img src="20230814_pipeline_enroll.png" width=500 /></kbd><br>

### 파이프라인 구성 설정 : General
<kbd><img src="20230814_pipeline_general.png" width=500 /></kbd><br>
* General 항목에서 깃헙 프로젝트 URL 을 작성한다.

<kbd><img src="20230814_pipeline_general_trigger.png" width=500 /></kbd><br>
* 빌드 트리거 항목에서 `GitHub hook trigger for GITScm polling` 를 선택한다.
    * 해당항목을 통해서 레파지토리 푸시 시, 트리거되어 Jenkinsfile 내 작성된 그루비 스크립트가 수행된다.

### 파이프라인 구성 설정 : Pipeline
<kbd><img src="20230814_pipeline_pipeline.png" width=500 /></kbd><br>
* Definition 항목에 `Pipeline script for SCM` 을 설정한다.
* `Repositories` 는 깃헙 레파지토리 URL 을 설정하고 `public` 으로 노출된 레파지토리이기 때문에 별도의 `Credential` 을 설정하지 않는다.
* `Branches to build` 는 어떤 브랜치에 trigger 를 설정할지 정할 수 있다.

<kbd><img src="20230814_pipeline_scriptpath.png" width=500 /></kbd><br>
* `Script Path` 는 Jenkinsfile 프로젝트내 어디에 있는지 프로젝트를 루트 디렉토리로 하여 경로를 명시해준다.

### github personal access token 권한 주기
<kbd><img src="20230814_configuration_github_help.png" width=500 /></kbd><br>
* Dashboard > Jenkins 관리 > System 에서 Github 메뉴의 help 를 누르면 위처럼 뜬다.
* Github 플러그인은 크레덴셜을 추가하지 않는한 작동하지 않는다.
* Hook 을 받기 위해선 앞서 진행했던 `Pipeline script for SCM` 을 설정이 필요하다.
* 크레덴셜 기본 _SCOPE_ 에는 admin:repo_hook 제공해야 한다.

<kbd><img src="20230814_github_token_1.png" width=500 /></kbd><br>
<kbd><img src="20230814_github_token_2.png" width=500 /></kbd><br>

### github 웹훅 설정 (젠킨스 연동)
* 깃헙 레파지토리에 Settings 탭에서 Webhooks 메뉴를 클릭한다.
* Payload URL 을 필수로 등록해줘야 하는데, 아래와 같이 입력한다.
    * https://{Jenkins-Server-URL}/github-webhook/
    * `github-webhook` 은 필수로 입력이 필요하다. 그리고 마지막 postfix 로 `/` 를 붙여줘야 한다.
        * /github-webhook  (X) : 동작
        * /github-webhook/ (O) : 미동작

<kbd><img src="20230814_github_webhook_jenkins.png" width=500 /></kbd><br>
* 위의 이미지에선 ngrok 를 이용해서 로컬에 띄어둔 젠킨스 서버를 외부로 노출했다. 
* `만약` github-webhook path 를 변경하고 싶다면 jenkins 서버에서 수정을 해야한다.
    * Dashboard > Jenkins 관리 > System
    * Github 메뉴에서 `고급` 을 클릭하면 `webhook url 을 오버라이드 할 수 있다.`

<kbd><img src="20230814_pipeline_override_url.png" width=500 /></kbd><br>
* 위의 메뉴에서 url 을 변경하면, 깃헙 레파지토레 세팅에 웹훅 URL 을 같이 변경해줘야 한다.

### 결과
<kbd><img src="20230814_pipeline_result.png" width=500 /></kbd><br>
* 위 작업들을 수행하고 난 뒤, Jenkinsfile 이 프로젝트 위치하고 푸시를 수행하면 젠킨스 파이프라인이 기동되는 것을 확인할 수 있다.
