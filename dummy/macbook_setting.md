## MACBOOK 세팅
맥북 초기세팅

## 🔖 목차
- [기본 설정](#기본-설정)
- [폰트](#폰트)
- [생산성 도구](#생산성-도구)
- [터미널](#터미널)
- [Alias 명령어](#alias-명령어)
- [개발 도구](#개발-도구)
- [추가 도구](#추가-도구)

---

## 🛠️ 기본 설정
| 항목 | 설명 |
|------|------|
| 초기 설정 참고 | [Subicura 맥북 설정 참고](https://subicura.com/mac/) |
| 핫코너 설정 | 오른쪽 하단(미션 컨트롤), 왼쪽 하단(잠금 화면) |
| 시간 표시 설정 | 24시간 표기, 초단위 표시 |
| 배터리 표시 | `%` 노출 |
| 백틱(`) 입력 설정 | [한글 입력 시 백틱 입력 설정](https://www.korecmblog.com/backtick-fix/) |

---

## 🔤 폰트
| 항목 | 설정 |
|------|------|
| 기본 폰트 | [Cascadia Code](https://github.com/microsoft/cascadia-code) |
| 폰트 크기 | 13 |

---

## 📌 생산성 도구
| 이름 | 설명 | 링크 |
|------|------|------|
| RunCat | 상태바에서 PC 사용량 체크 | [링크](https://apps.apple.com/kr/app/runcat/id1429033973?mt=12) |
| Keka | 압축 프로그램 | [링크](https://www.keka.io/ko/) |
| Maccy | 클립보드 매니저 | [링크](https://maccy.app/) |
| caffeine | 절전모드 방지 | [링크](https://www.caffeine-app.net/) |

---

## 💻 터미널
| 항목 | 설정 및 설명 | 링크 |
|------|------|------|
| 터미널 | iTerm2 (세팅 export/import 가능) | [iTerm2](https://iterm2.com/index.html) |
| 테마 관리 | powerlevel10k (사용자 커스터마이징 가능) | [powerlevel10k](https://github.com/romkatv/powerlevel10k) |
| 하이라이팅 | 명령어 문법 강조 플러그인 | [zsh-syntax-highlighting](https://github.com/zsh-users/zsh-syntax-highlighting) |
| 자동완성 | 자동 완성 기능 플러그인 | [zsh-autosuggestions](https://github.com/zsh-users/zsh-autosuggestions) |

### 🔑 iTerm 주요 단축키
| 단축키 | 기능 |
|--------|------|
| Cmd + Backspace | 한 줄 지우기 (hex: 0x15) |
| Option + Backspace | 한 단어씩 지우기 (hex: 0x17) |
| Cmd + Shift + I | 여러 창 동시 입력 |
| Cmd + D | 창 가로 추가 |
| Cmd + Shift + D | 창 세로 추가 |

---

## 🔗 Alias 명령어
| 명령어 | 설명 |
|--------|------|
| `gitlog` | Git 로그를 깔끔하게 그래픽 표시 |
| `k` | `kubectl` 명령어 단축 |

**설정 적용 방법**: `.zshrc`에 추가 후 `source ~/.zshrc` 실행

---

## 🧑‍💻 개발 도구
| 항목 | 세부 내용 | 링크 |
|------|------|------|
| sdkman | JDK/SDK 관리 툴 | [sdkman](https://sdkman.io/) |
| IntelliJ Live Templates | Kotlin 템플릿 설정 (`TT`) | [Live Template 설정법](https://velog.io/@max9106/IntelliJ-Live-Template) |
| IntelliJ Jira 연동 | Jira 서버 연동 (계정 정보 필요) | - |
| IntelliJ Sync & Backup | 계정 기준 활성화 | - |
| IntelliJ Plugins | CodeGlance Pro(코드 미니맵), Key Promoter(단축키 추천) | - |
| DataGrip Sync & Backup | 계정 기준 활성화 | - |
| DataGrip Global Datasource | 타 PC와 datasource 공유 | - |

---

## 🔧 추가 도구
| 도구명 | 설명 | 링크 |
|------|------|------|
| Medis Redis | Redis properties export/import | - |
| Rapid API | API 마켓플레이스 플랫폼 | - |
| HTTPie | CLI 기반 HTTP 클라이언트 | - |
| Postman | GUI 기반 API 테스트 클라이언트 | - |
| OpenLens | K8s GUI 관리 도구 | - |
| Charles | HTTP 요청/응답 캡처 및 분석 도구 | - |

