# 스프링 부트 Gradle Build 

### dependecies
- compile 
  - compile 시점에 필요한 디펜던시 라이브러리들을 컴파일로 정의
- runtime
  - 런타임 시에 참조할 라이브러리르 정의, 기본적으로 컴파일 라이브러리를 포함
- compileOnly
  - 컴파일 시점에만 사용하고 런타임에는 필요없는 라이브러리를 정의
- testCompile
  - 프로젝트의 테스트를 위한 디펜던시 라이브러리를 정의. 기본적으로 컴파일된 클래스와 컴파일 라이브러리를 포함
