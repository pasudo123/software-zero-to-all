# Gradle 멀티 모듈 프로젝트

## 멀티 모듈 프로젝트 구조
- 루트 디렉토리에 ```settings.gradle``` 을 위치시킨다.
- 루트 디렉토리에 ```build.gradle``` 을 위치시킨다.
- 루트 디렉토리 이외의 하위 디렉토리에는 각각 ```*.gradle``` build 파일이 위치되어 있다. ex)```build.gradle```
- 현재 멀티 모듈 프로젝트의 구조를 확인하려면 ```gradle -q project``` 입력

# reference
- https://woowabros.github.io/study/2019/07/01/multi-module.html
- https://jojoldu.tistory.com/123
- https://docs.gradle.org/current/userguide/multi_project_builds.html#header
- https://docs.gradle.org/current/userguide/intro_multi_project_builds.html#example_listing_the_projects_in_a_build
