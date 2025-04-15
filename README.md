## 🧾 커밋 메시지 컨벤션 가이드 (Commit Message Convention)

우리는 [Conventional Commits](https://www.conventionalcommits.org/en/v1.0.0/) 스타일을 기반으로 커밋 메시지를 작성합니다.

### ✅ 기본 형식

> 예시: `feat(chat): 웹소켓 연결 기능 추가`

---

### 🎯 커밋 타입(Type)

| 타입 | 설명 | 예시 |
|------|------|------|
| `feat` | 새로운 기능 추가 | `feat(chat): 메세지 전송 기능 추가` |
| `fix` | 버그 수정 | `fix(chat): 이모지 전송 시 오류 해결` |
| `docs` | 문서 수정 | `docs: README에 CORS 설정 방법 추가` |
| `style` | 코드 스타일 (세미콜론, 공백 등) 변경 | `style: 줄바꿈 정리 및 포맷 적용` |
| `refactor` | 리팩터링 (기능 변화 없이 코드 개선) | `refactor(chat): 핸들러 로직 분리` |
| `test` | 테스트 코드 추가 또는 수정 | `test: WebSocket 기능 테스트 추가` |
| `chore` | 빌드 설정, 패키지 관리 등 기타 작업 | `chore: .gitignore에 .env 추가` |
| `perf` | 성능 개선 | `perf: 메시지 렌더링 속도 향상` |

--
## 🚦 Git 사용 규칙

1. **로컬 본인의 브랜치에서 작업 (fe, hoon, jiin)**  
2. **작업 전:** `git pull origin main`  
3. **작업 후:** `git push origin <브랜치명>`

### ⚠ pull이 안 될 때

```bash
git add .
git commit -m "작업 커밋 메시지"

git fetch origin
git merge origin/main  # 자동 또는 수동 병합
---

### 🛠 예시 커밋 메시지

```bash
git commit -m "feat(chat): 채팅방 입장 시 알림 메시지 추가"
git commit -m "fix(chat): 메시지 중복 전송 버그 수정"
git commit -m "docs: 프로젝트 실행 방법 추가"

