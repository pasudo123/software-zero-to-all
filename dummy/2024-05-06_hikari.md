# hikari connection pool

## 필수 Configuration
- connectionTimeout
    - 클라이언트가 pool 에서 연결을 기다리는 최대 밀리초
    - 시간초과 시, SQLException 이 발생
- maximumPoolSize
    - 풀의 최대크기
- minimumIdle
    - 풀의 최소크기

## MySQL Configuration
Hikari CP 에서 MySQL 성능향상을 위해 추천하는 옵션값. 하나씩 살펴보기.
- prepStmtCacheSize
    - MySQL 드라이버가 캐시할 명령어 수 정의. 기본값은 25. 250 ~ 500 사이로 설정할 것을 권유.
    - 준비된 명령어는 디비에서 자주 사용되는 SQL 을 컴파일하여, 그 명령어 수행 시 재사용을 하게 해주어 성능에 이점을 가져옴.
- prepStmtCacheSqlLimit
- cachePrepStmts
- useServerPrepStmts


## 참고자료
* [게임 서버 시스템을 위한 HikariCP 옵션 및 권장 설정](https://netmarble.engineering/hikaricp-options-optimization-for-game-server/)
* [게임 서버 시스템을 위한 JDBC와 Timeout 이해하기](https://netmarble.engineering/jdbc-timeout-for-game-server/)
* [hikari cp](https://github.com/brettwooldridge/HikariCP#gear-configuration-knobs-baby)
* [hikari cp : MySQL Configuration](https://github.com/brettwooldridge/HikariCP/wiki/MySQL-Configuration)