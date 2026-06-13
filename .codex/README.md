# Codex Global Policy Backup

이 디렉터리는 PC 전역 Codex 설정을 새 환경에서도 복원할 수 있도록 보관한다.

## 파일

- `AGENTS.md`: 로컬 PC 의 `~/.codex/AGENTS.md` 로 사용하는 글로벌 Codex 정책

## 복원 방법

새 PC 에서 이 저장소를 받은 뒤 아래 위치로 복사한다.

```bash
mkdir -p ~/.codex
cp .codex/AGENTS.md ~/.codex/AGENTS.md
```

프로젝트 루트에 별도 `AGENTS.md` 가 있으면 프로젝트 지침이 우선 적용된다.
