# Gradle 멀티 모듈 프로젝트

## 멀티 모듈 프로젝트 구조
- 루트 디렉토리에 ```settings.gradle``` 을 위치시킨다.
- 루트 디렉토리에 ```build.gradle``` 을 위치시킨다.
- 루트 디렉토리 이외의 하위 디렉토리에는 각각 ```*.gradle``` build 파일이 위치되어 있다. ex)```build.gradle```
- 현재 멀티 모듈 프로젝트의 구조를 확인하려면 ```gradle -q project``` 입력

__ex) gradle -q project 명령어 수헹__
```gradle
$ gradle -q project

------------------------------------------------------------
Root project
------------------------------------------------------------

Root project 'movie-masterpiece-club'
+--- Project ':movie-masterpiece-club-admin'
+--- Project ':movie-masterpiece-club-common'
\--- Project ':movie-masterpiece-club-web'
```

__ex) allprojects__  
- __project API__ 는 현재 프로젝트와 그 하위의 모든 프로젝트를 액세스할 수 있는 ```allprojects``` 속성을 제공.
```gradle
allprojects {
    task hello {
        doLast {
            task -> println "I'm $task.project.name"
        }
    }
}

$ gradle -q hello
I'm movie-masterpiece-club
I'm movie-masterpiece-club-admin
I'm movie-masterpiece-club-common
I'm movie-masterpiece-club-web
```

__ex) subproject__
- __projecet API__ 는 하위 프로젝트만 액세스할 수 있는 ```subprojects``` 속성을 제공.
```gradle
subprojects {
    hello {
        doLast {
            println "- I depend on movie-masterpiece-club"
        }
    }
}

$ gradle -q hello
I'm movie-masterpiece-club
I'm movie-masterpiece-club-admin
- I depend on movie-masterpiece-club
I'm movie-masterpiece-club-common
- I depend on movie-masterpiece-club
I'm movie-masterpiece-club-web
- I depend on movie-masterpiece-club
```


__ex) project__
- __project API__ 는 특정 프로젝트만 액세서할 수 있는 ```project``` 속성을 제공.
```gradle
project(':movie-masterpiece-club-web').hello{
    doLast{
        println "-- I last task in child project"
    }
}

$ gradle -q hello
I'm movie-masterpiece-club
I'm movie-masterpiece-club-admin
- I depend on movie-masterpiece-club
I'm movie-masterpiece-club-common
- I depend on movie-masterpiece-club
I'm movie-masterpiece-club-web
- I depend on movie-masterpiece-club
-- I last task in child project
```

# reference
- https://woowabros.github.io/study/2019/07/01/multi-module.html
- https://jojoldu.tistory.com/123
- https://docs.gradle.org/current/userguide/multi_project_builds.html#header
- https://docs.gradle.org/current/userguide/intro_multi_project_builds.html#example_listing_the_projects_in_a_build
