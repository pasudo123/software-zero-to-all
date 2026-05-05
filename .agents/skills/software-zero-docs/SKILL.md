---
name: software-zero-docs
description: Use when adding, reorganizing, reviewing, or preparing PRs for Markdown learning notes in software-zero-to-all. Do not use for application code changes.
---

# Software Zero Docs

이 skill은 `software-zero-to-all` 저장소에서 Markdown 문서를 추가하거나 정리할 때 사용한다.
저장소의 변하지 않는 기준은 `AGENTS.md`를 따르고, 여기에는 실제 작업 순서만 둔다.

## 흐름

1. `AGENTS.md`, 루트 `README.md`, 관련 주제 디렉터리의 `README.md`를 확인한다.
2. 요청의 목표, 제약조건, 완료조건을 짧게 정리한다.
3. 기존 문서와 겹치는지 `rg`로 검색한다.
4. 새 문서가 필요한지, 기존 문서를 보강하면 되는지 판단한다.
5. 주제와 가장 가까운 디렉터리와 파일명을 고른다.
6. 문서와 필요한 인덱스만 수정한다.
7. 검증 명령을 실행하고 결과를 요약한다.

## 작성할 때

- 첫 문단에서 이 글이 무엇을 다루는지 바로 말한다.
- 필요한 경우 `## 개요`, `## 원인`, `## 동작 방식`, `## 예시`, `## 주의사항`, `## reference` 같은 실용적인 제목을 쓴다.
- 코드와 명령어는 fenced code block을 사용하고 가능한 언어를 명시한다.
- 표는 비교가 필요할 때만 사용하고, 표만으로 부족하면 짧은 해설을 붙인다.
- `AGENTS.md`의 문서 위치, 작성 기준, 보안 기준을 반복해서 쓰지 말고 적용만 한다.

## README 반영

- 새 문서가 재사용 가능한 정리라면 루트 `README.md`의 해당 섹션에 링크를 추가한다.
- 주제 디렉터리에 `README.md`가 있으면 그 인덱스도 함께 갱신한다.
- 같은 항목 안에서는 작성일 순서를 유지한다.
- 단편 메모나 임시 문서는 무리해서 루트 인덱스에 올리지 않는다.
- README 파일을 새로 만들기 전에 기존 인덱스로 충분한지 먼저 본다.

## PR 준비

- Git/PR 정책은 `AGENTS.md`를 따른다.
- 커밋 메시지는 최근 히스토리처럼 짧게 쓴다.
- PR 제목과 본문은 기본적으로 한글로 작성한다.
- worktree를 쓰지 못했다면 PR 본문에 이유를 짧게 남긴다.

## 확인 명령 예시

```bash
git diff --check
```

```bash
rg -n "token|password|secret|localhost|http://|https://|내부 URL|개인 서버" <수정한 파일>
```

```bash
rg -n "이전-파일명|이전-디렉터리명" .
```
