# Think. Do. Done. (TDD)

> 심플하게 이용할 수 있는 To Do List 서비스

#### - _해당 프로젝트는 Kotlin, Test-Driven-Development를 공부하기 위한 백엔드 서버 레포지터리 입니다._

---

## 주요 기능

### 회원 관리
- 회원가입 (이메일 / 비밀번호)
- 로그인 / 로그아웃 (JWT)
- 비밀번호 암호화 저장 (BCrypt)

### 투두리스트
- 투두 생성(Create)
- 투두 조회(Read)
- 투두 수정(Update)
- 투두 삭제(Delete)

---

## 기술 스택

### Backend
- Kotlin
- Spring Boot
- Spring Data JPA

### Database
- H2 (file)

### Build
- Gradle (Kotlin DSL)

### 테스트
- JUnit5, Spring Boot Test

### FrontEnd
- 추후, React 도입 예정

---

## 도메인 모델 (초안)

### User

* `id`: Long (PK)
* `email`: String (unique)
* `password`: String (암호화 저장)
* `createdAt`: LocalDateTime

### Todo

* `id`: Long (PK)
* `title`: String
* `description`: String
* `status`: ENUM (THINK, DO, DONE)
* `dueDate`: LocalDate
* `createdAt`: LocalDateTime
* `updatedAt`: LocalDateTime
* `user`: User (Many-to-One)

---

## API 설계 (초안)

### 인증 / 회원

| Method | URL                | 설명   | 비고           |
|--------|--------------------|------|--------------|
| POST   | `/api/auth/signup` | 회원가입 | 이메일 중복 체크    |
| POST   | `/api/auth/login`  | 로그인  | JWT 또는 세션 예정 |
| POST   | `/api/auth/logout` | 로그아웃 | 인증 방식에 따라 처리 |

### 투두

> 모든 투두 API는 인증된 유저만 접근 가능

| Method | URL                      | 설명                    |
|--------|--------------------------|-----------------------|
| GET    | `/api/todos`             | 로그인 유저 투두 목록 조회       |
| POST   | `/api/todos`             | 투두 생성                 |
| GET    | `/api/todos/{id}`        | 단일 투두 상세 조회           |
| PUT    | `/api/todos/{id}`        | 투두 전체 수정              |
| PATCH  | `/api/todos/{id}/status` | 상태만 변경(Think/Do/Done) |
| DELETE | `/api/todos/{id}`        | 투두 삭제                 |

응답 형식은 기본적으로 JSON 사용.

---

## 프로젝트 구조 (초안)

```text
src
 └ main
    ├ kotlin
    │  └ com.example.tdd
    │     ├ config
    │     ├ auth
    │     ├ todo
    │     ├ common
    │     └ TddApplication.kt
    └ resources
       ├ application.yml
       └ data.sql (테스트용 더미 데이터)
```

---

## 로컬 실행 방법

1. 저장소 클론

   ```bash
   git clone <repo-url>
   cd tdd
   ```

2. 서버 실행

   ```bash
   ./gradlew bootRun
   # 또는
   ./gradlew build
   java -jar build/libs/*.jar
   ```

3. 접속
- API: `http://localhost:8080`
- H2 콘솔: `http://localhost:8080/h2-console`
  - JDBC URL, 계정 정보는 `application.properties` 참고
