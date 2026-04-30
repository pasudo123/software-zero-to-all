# AGENTS.md

## 목적

이 문서는 `software-zero-to-all` 저장소에서 문서를 추가하거나 정리할 때 따르는 프로젝트별 작업 가이드다. 이 저장소는 애플리케이션 코드가 아니라 소프트웨어 학습, 실무 메모, 명령어 사용법, 인프라/DB/백엔드 개념을 누적하는 Markdown 기반 지식 저장소다.

## 구조

루트 `README.md`는 전체 문서의 인덱스 역할을 한다. 새 문서를 추가했을 때 여러 번 다시 볼 만한 내용이면 `README.md`에도 링크를 추가한다.

주제별 문서는 가장 가까운 디렉터리에 둔다.

- `database/`: MySQL, 트랜잭션, 인덱스, 락, 파티션 등 DB 정리
- `command/`: curl, jq, awk, grep, lsof 같은 CLI 사용법
- `infra/`: AWS, Docker, Kubernetes, Kafka 등 인프라 메모
- `dummy/`: 아직 정식 분류 전이거나 단편적인 실무/학습 메모
- `book/`, `Inflearn/`, `Udemy/`: 책과 강의 기반 정리
- `personal/`: 개인 관점의 설계, 개발 습관, 개념 정리
- `Image/`: 문서에서 참조하는 이미지와 다이어그램

## 컨벤션

문서는 짧은 개요에서 시작하고, 이후 원인, 동작 방식, 예시, 해결 방법, 참고 순서로 정리한다. 기존 문서처럼 `## 개요`, `## 원인`, `## 해소방법`, `## reference` 같은 실용적인 제목을 우선 사용한다.

코드 예시는 fenced code block으로 작성하고 가능한 언어를 명시한다.

```bash
curl -v https://example.com
```

```kotlin
CoroutineScope(Dispatchers.IO + SupervisorJob())
```

Mermaid 다이어그램은 필요한 경우에만 사용하고, 복잡한 설명을 보완하는 용도로 둔다. 링크는 가능하면 상대 경로를 사용한다. 파일명은 기존 관례처럼 한글과 영어를 허용하되, 검색 가능한 핵심 키워드를 포함한다.

## 내용 및 PR

새 글은 하나의 주제를 중심으로 작성한다. 문제 상황, 핵심 개념, 실제 사용 예시, 주의사항을 함께 남기면 재사용성이 높다. 명령어 문서는 옵션별 예시를 포함하고, Spring/Kotlin/DB 문서는 동작 조건과 실패 케이스를 함께 적는다.

커밋 메시지는 최근 히스토리와 맞춰 짧게 작성한다.

- `docs : probe`
- `docs : redis setnx + expire + uuid`
- `docs 정리 : 짧게 기억 정리.`

PR에는 변경한 문서 경로, 추가/수정 이유, 확인한 내용만 간단히 적는다. 실제 토큰, 비밀번호, 개인 서버 주소, 내부 URL은 문서에 남기지 않고 `<HOST>`, `<DB_PASSWORD>` 같은 placeholder를 사용한다.
