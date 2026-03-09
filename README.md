# CMS 프로젝트

## 프로젝트 개요
Spring Boot 기반의 간단한 CMS(Content Management System) 프로젝트입니다.  
사용자는 회원가입 및 로그인 후 콘텐츠를 생성, 조회, 수정, 삭제할 수 있으며, 관리자 또는 본인이 작성한 콘텐츠만 수정/삭제가 가능합니다.  

---

## 기술 스택
- **백엔드**: Java 21, Spring Boot, Spring Data JPA, Spring Security, JWT
- **DB**: H2 Database (개발용)
- **API 문서화**: Springdoc OpenAPI / Swagger UI
- **빌드 도구**: Gradle
- **테스트 및 검증 도구**: Postman, Swagger UI

---

## 로그인 방식
1. **회원가입**: `/user/signup`  
   - `username`, `password`를 입력해 회원 생성
2. **로그인**: `/user/login`  
   - `username`, `password`를 입력하여 로그인
   - 성공 시 JWT 토큰을 발급받음
   - JWT는 Authorization 헤더에 `Bearer <token>` 형태로 전달
3. **인증 적용 API**:  
   - 콘텐츠 생성, 수정, 삭제 시 JWT 토큰 필요
   - 조회 API는 공개 (인증 없이 조회 가능)
4. **Swagger UI에서 인증 테스트**  
   - Swagger 우측 상단 **Authorize** 버튼 클릭 후 `Bearer <JWT>` 입력

---

## 프로젝트 실행 방법
1. 저장소 클론

```bash
git clone <repository_url>
cd <project_directory>
```

2. Gradle build 및 실행

```bash
./gradlew bootRun
```

3. H2 콘솔 접속 (개발용, DB 확인용)

```bash
http://localhost:8080/h2-console
```

4. Swagger UI 접속 (API 문서 확인 및 테스트)

```bash
http://localhost:8080/swagger-ui/index.html
```

---

## 사용한 AI 도구
- **Chatgpt**: 코드 작성 검증, 수정, Swagger 및 JWT 연동 가이드 제공, 개발 생산성 향상에 활용

---
