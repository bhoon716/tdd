# Think. Do. Done. (TDD) - Backend

심플하게 이용할 수 있는 To Do List 서비스의 **백엔드 서버**입니다.  
Kotlin과 Test-Driven Development(TDD)를 공부/연습하기 위해 만든 프로젝트입니다.

> 🔗 프론트엔드 Repository: https://github.com/bhoon716/tdd-front

---

## ✨ 주요 기능

### 회원 관리

- 회원가입 (이메일 / 비밀번호)
- 로그인 / 로그아웃 (JWT)
- 비밀번호 암호화 저장 (BCrypt)

### 투두 리스트

- 투두 생성 (Create)
- 투두 조회 (Read)
- 투두 수정 (Update)
- 투두 삭제 (Delete)
- 상태 값: `THINK`, `DO`, `DONE`

---

## 🧱 도메인 모델 (초안)

### User

- `id: Long (PK)`
- `email: String (unique)`
- `password: String` (암호화 저장)
- `createdAt: LocalDateTime`

### Todo

- `id: Long (PK)`
- `title: String`
- `description: String`
- `status: ENUM (THINK, DO, DONE)`
- `dueDate: LocalDate`
- `createdAt: LocalDateTime`
- `updatedAt: LocalDateTime`
- `user: User`

---

## 🌐 API 설계 (초안)

### 인증 / 회원

| Method | URL                | 설명   | 비고           |
|--------|--------------------|------|--------------|
| POST   | `/api/auth/signup` | 회원가입 | 이메일 중복 체크    |
| POST   | `/api/auth/login`  | 로그인  | JWT 발급       |
| POST   | `/api/auth/logout` | 로그아웃 | 인증 방식에 따라 처리 |

### 투두 (인증 필요)

> 모든 투두 API는 **인증된 유저만** 접근 가능합니다.

| Method | URL                      | 설명                     |
|--------|--------------------------|------------------------|
| GET    | `/api/todos`             | 로그인 유저 투두 목록 조회        |
| POST   | `/api/todos`             | 투두 생성                  |
| GET    | `/api/todos/{id}`        | 단일 투두 상세 조회            |
| PUT    | `/api/todos/{id}`        | 투두 전체 수정               |
| PATCH  | `/api/todos/{id}/status` | 상태만 변경 (THINK/DO/DONE) |
| DELETE | `/api/todos/{id}`        | 투두 삭제                  |

- 응답 형식은 기본적으로 **JSON**을 사용합니다.

---

## 🛠 기술 스택

### Backend

- Kotlin
- Spring Boot
- Spring Data JPA

### Database

- H2 (file)

### Build

- Gradle (Kotlin DSL)

### Test

- JUnit 5
- Spring Boot Test

---

## 📂 프로젝트 구조 (초안)

```text
src
└ main
   ├ kotlin
   │  └ com.example.tdd
   │     ├ config        # 설정 (Security, JPA 등)
   │     ├ auth          # 인증/인가, User 도메인
   │     ├ todo          # Todo 도메인, 서비스, 컨트롤러
   │     ├ common        # 공통 유틸, 예외 처리 등
   │     └ TddApplication.kt
   └ resources
      ├ application.yml  # 애플리케이션 및 DB 설정
      └ data.sql         # 테스트용 더미 데이터 (선택)
```
🚀 로컬 실행 방법
0. 사전 준비
- Git
- JDK 21

1. 저장소 클론 (공통)
```bash
git clone https://github.com/bhoon716/tdd.git
cd tdd
```
2. 서버 실행 
- macOS / Linux / Windows (PowerShell)
```bash
# 개발 서버 실행
./gradlew bootRun

# 또는 빌드 후 실행
./gradlew build
java -jar build/libs/*.jar
```

3. 접속
- API: http://localhost:8080
- H2 콘솔: http://localhost:8080/h2-console
- JDBC URL, 계정 정보는 application.properties 참고

### 프론트엔드와 함께 실행하기
전체 플로우를 확인하려면 프론트엔드 레포지토리도 함께 실행합니다.

```bash
# node 21 필요
# 별도 터미널에서
git clone https://github.com/bhoon716/tdd-front.git
cd tdd-front

npm install
npm run dev
```
- 프론트엔드: http://localhost:5173
- 백엔드(http://localhost:8080) 실행
- 프론트엔드(http://localhost:5173) 접속

화면에서 회원가입/로그인 후 Todo 생성/수정/삭제, 상태 변경(Think → Do → Done)을 테스트할 수 있습니다.

🧪 테스트 실행
```bash
./gradlew test
```