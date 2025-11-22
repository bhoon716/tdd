# Think. Do. Done. (TDD) ✅

> 심플하게 이용할 수 있는 To Do List 백엔드 서버

해당 프로젝트는 **Kotlin**과 **Test-Driven Development(TDD)**을 학습하기 위한 Todo List 백엔드 레포지토리입니다.

---

## 주요 기능 ✨

### 1. 회원 / 인증 🔐

- 회원가입 (이메일 / 비밀번호)
- 로그인 (JWT Access Token 발급)
- 비밀번호 암호화 저장 (BCryptPasswordEncoder)

### 2. 투두리스트 📝

- 투두 생성(Create)
- 투두 단건 조회(Read)
- 투두 목록 조회 (현재는 전체 조회, 추후 페이징/필터링 확장 예정)
- 투두 수정(Update)
- 투두 삭제(Delete)
- 로그인한 사용자 기준으로 자신의 Todo만 접근 가능

---

## 기술 스택 🛠

### Backend

- Kotlin 1.9
- Spring Boot 3.5.x
- Spring Web (REST API)
- Spring Data JPA
- Spring Security
- Jackson (jackson-module-kotlin)

### Database

- H2 (in-memory, `jdbc:h2:mem:ampm`)

### Build

- Gradle (Kotlin DSL)
- Java 21 (Toolchain)

### 테스트 ✅

- JUnit 5
- Spring Boot Test
- Spring Security Test
- MockK

---

## 도메인 모델 📦

### User (`open_mission.tdd.auth.entity.User`)

* `id`: Long? (PK, `user_id` 컬럼)
* `email`: String (unique, not null)
* `password`: String (암호화 저장, not null)
* `createdAt` / `updatedAt`: LocalDateTime (공통 `BaseEntity` 상속, JPA Auditing)

### Todo (`open_mission.tdd.todo.entity.Todo`)

* `id`: Long? (PK, `todo_id` 컬럼)
* `user`: User (Many-to-One, LAZY, `user_id` FK, not null)
* `title`: String (not null)
* `content`: String (not null, 최대 1000자)
* `status`: `TodoStatus` (ENUM: `THINK`, `DO`, `DONE`, 기본값 `THINK`)
* `createdAt` / `updatedAt`: LocalDateTime (공통 `BaseEntity` 상속, JPA Auditing)

`TodoStatus`

* `THINK`
* `DO`
* `DONE`

---

## 주요 API 📡

### 인증 / 회원 (`/api/auth`)

| Method | URL                | 설명          |
|--------|--------------------|-------------|
| POST   | `/api/auth/signup` | 회원가입        |
| POST   | `/api/auth/login`  | 로그인(JWT 발급) |

요청/응답 DTO (예시)

- `SignupRequest(email, password)` / `SignupResponse`
- `LoginRequest(email, password)` / `LoginResponse(accessToken, ...)`

---

### 투두 (`/api/todos`)

> 모든 투두 API는 인증된 유저만 접근 가능하며, `@AuthenticationPrincipal`로 주입된 `userId: Long` 기준으로 자신의 Todo만 조회/수정/삭제할 수 있습니다.

| Method | URL               | 설명              | 요청 바디                                       | 응답 바디                |
|--------|-------------------|-----------------|---------------------------------------------|----------------------|
| POST   | `/api/todos`      | 투두 생성           | `CreateTodoRequest(title, content)`         | `TodoResponse`       |
| GET    | `/api/todos`      | 로그인 유저 투두 목록 조회 | 없음                                          | `List<TodoResponse>` |
| GET    | `/api/todos/{id}` | 단일 투두 상세 조회     | 없음                                          | `TodoResponse`       |
| PUT    | `/api/todos/{id}` | 투두 전체 수정        | `UpdateTodoRequest(title, content, status)` | `TodoResponse`       |
| DELETE | `/api/todos/{id}` | 투두 삭제           | 없음                                          | `Unit`               |

`TodoResponse`

* `id`: Long
* `title`: String
* `content`: String
* `status`: TodoStatus

요청 DTO

- `CreateTodoRequest(title: String, content: String)`
- `UpdateTodoRequest(title: String, content: String, status: TodoStatus)`

응답은 공통 래퍼 `ApiResponse<T>`로 감싸서 반환합니다.

---

## 프로젝트 구조 (실제 기준) 🗂

```text
src
 └ main
    ├ kotlin
    │  └ open_mission.tdd
    │     ├ auth        # 인증/인가, 회원가입/로그인, JWT 발급
    │     ├ common      # 공통 베이스 엔티티, 에러, 공통 응답 등
    │     ├ config      # 스프링/JPA/시큐리티 설정
    │     ├ security    # 보안 설정 및 JWT 필터, 토큰 관련 컴포넌트
    │     ├ todo        # Todo 도메인, 서비스, 컨트롤러, DTO
    │     └ TddApplication.kt
    └ resources
       ├ application.properties
       └ static/, templates/ (추후 프론트엔드/뷰 연동용)
```

---

## 로컬 실행 방법 ▶️

1. 저장소 클론

   ```bash
   git clone https://github.com/bhoon716/tdd
   cd tdd
   ```

2. 테스트 실행

   ```bash
   ./gradlew test
   ```

3. 애플리케이션 실행

   ```bash
   ./gradlew bootRun
   # 또는
   ./gradlew build
   java -jar build/libs/*.jar
   ```

4. 접속

- API: `http://localhost:8080`
- H2 콘솔: `http://localhost:8080/h2-console`
  - JDBC URL, 계정 정보는 `application.properties` 참고

---

## 향후 계획 🔍

- 통합 테스트 추가
- 작은 기능들 꾸준히 추가해보면서 TDD 사이클 계속 연습하기
- 테스트 커버리지 꾸준히 늘리기
