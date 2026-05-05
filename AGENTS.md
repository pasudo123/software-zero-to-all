# AGENTS.md

## 목적

`software-zero-to-all`은 애플리케이션 코드가 아니라 소프트웨어 학습, 실무 메모, 명령어 사용법, 인프라/DB/백엔드/AI 개념을 누적하는 Markdown 지식 저장소다.

이 파일에는 Codex가 항상 알아야 하는 저장소의 기준만 둔다. 문서 작성, 정리, PR 준비처럼 반복되는 절차는 `.agents/skills/software-zero-docs/SKILL.md`를 사용한다.

## 문서 위치

루트 `README.md`는 전체 인덱스다. 새 문서가 나중에 다시 볼 만한 내용이면 해당 주제 인덱스와 루트 `README.md`에 필요한 링크만 추가한다.

| 디렉터리 | 역할 |
| --- | --- |
| `ai/` | LLM, Codex, AI 도구, 모델 비교, AI 활용 메모 |
| `spring/` | Spring, Spring Boot, JPA, QueryDSL, Hibernate |
| `database/` | MySQL, 트랜잭션, 인덱스, 락, 파티션 |
| `command/` | curl, jq, awk, grep, lsof 같은 CLI 사용법 |
| `infra/` | AWS, Docker, Kubernetes, Kafka 등 인프라 메모 |
| `backend/` | Spring 범주에 들어가지 않는 백엔드 일반 주제 |
| `development/` | 설계, 아키텍처, 개발 방법론, 실습형 정리 |
| `personal/`, `piece/`, `dummy/` | 개인 메모, 짧은 단편, 아직 분류 전인 글 |
| `book/`, `inflearn/`, `udemy/` | 책과 강의 기반 정리 |
| `image/` | 문서에서 참조하는 이미지와 다이어그램 |

새 학습/실무 메모는 `yyyy-MM-dd_topic-kebab.md` 형식을 우선 사용한다. 예: `2026-04-30_redis-lock.md`
분류가 애매하면 임시로 `dummy/`에 두고, 주제가 명확해지면 이동한다.

## 작성 기준

- 한글 설명을 기본으로 하고, 기술 용어가 더 자연스러운 경우 영어를 함께 쓴다.
- AI가 쓴 티가 강한 문장, 과한 요약, 홍보 문구, 근거 없는 단정은 줄인다.
- 개인 학습 메모의 말투와 뉘앙스는 가능한 유지하고, 틀린 표현이나 오래된 정보는 바로잡는다.
- 복잡한 흐름이나 관계를 설명할 때는 다이어그램도 필요하면 추가하고, Markdown 문서 안에서는 Mermaid를 우선 사용한다.
- 링크는 가능하면 상대 경로를 사용한다.

## 작업 정책

- 모든 변경은 작업 브랜치와 `main` 대상 PR을 통해 반영한다.
- 머지는 GitHub의 `Create a merge commit` 방식으로 처리한다. Git 기준으로는 `git merge --no-ff`처럼 merge commit을 남기는 방식이며, fast-forward, squash merge, rebase merge는 사용하지 않는다.
- 가능하면 `git worktree`로 작업 공간을 분리한다.
- 검색은 `rg`, 파일 탐색은 `fd`, JSON 확인은 `jq`, GitHub 연동은 `gh`를 우선 사용한다.
- PR 전에는 `git diff --check`를 실행하고, Markdown 내부 링크와 이미지 경로를 확인한다.
- 실제 토큰, 비밀번호, 개인 서버 주소, 내부 URL은 문서에 남기지 않는다. `<HOST>`, `<DB_PASSWORD>` 같은 placeholder를 사용한다.
