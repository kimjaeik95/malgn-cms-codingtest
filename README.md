# malgn-cms-codingtest
## 요구사항 정의서 
### (Notion 설계서) : https://www.notion.so/31b4f9a27b4a80bda4e8f580039c0280 
--- 
## 프로젝트 실행 방법 
### 개발 환경
- Java 25
- Spring Boot 4
- Spring Security
- JPA
- H2 Database
- Lombok
- validation
- Swagger
- flyway
### 실행
1. git clone https://github.com/kimjaeik95/malgn-cms-codingtest.git
2. cd malgn-cms-codingtest
3. ./gradlew bootRun
4. Swagger api 테스트
- 토큰 입력 시 Swagger 상단 Authorize 버튼 클릭 후 
Value 입력창에 JWT Bearer 접두사를 제거하고 순수 토큰 값만 입력해 주세요!

## 구현 내용 / 추가 구현 기능 
### 1. 콘텐츠 (CMS)
- 콘텐츠 CRUD: 콘텐츠 추가, 목록 조회(페이징), 상세 조회, 수정, 삭제 기능 구현
- 조회수 증가: 상세 조회 시 viewCount가 자동으로 증가하도록 도메인 로직 설계
- 동시성 제어: 다수의 사용자가 동시에 상세 조회 시 조회수 데이터가 누락되지 않도록 비관적 락 적용
- 일반 사용자: 본인이 작성한 콘텐츠만 수정, 삭제 가능
- 관리자(ADMIN): 모든 콘텐츠에 대한 수정, 삭제 가능
### 2. Security 인증 / 인가
- JWT 기반 로그인: Spring Security와 JWT를 연동하여 서버 무상태 인증
- USER, ADMIN 권한을 구분하여 API 접근 제어
- CORS 설정
- AuthenticationPrincipal을 로그인 인증된 사용자 객체 저장
### 추가 구현
- JUnit5 Mockito: Service 레이어의 핵심 비즈니스 로직에 대한 유닛 테스트 수행
- Swagger API 문서화: 모든 API에 @Operation 및 @Schema를 적용하여 보기 쉬운 API 문서 제공
- Flyway DB 형상 관리: 데이터베이스 스키마 버전을 관리하여 추 후 DBMS 도입 또는 장기 관리시 편의성 향상
- validation 데이터 무결성을 위해 DTO 레벨에서 입력값 검증 추가
## 사용한 AI 도구 또는 참고 자료 
### 사용 AI 도구 : ChatGpt, Gemini 사용
### 참고 자료 :
- 벨리데이션
https://jakarta.ee/specifications/bean-validation/3.0/apidocs/jakarta/validation/constraints/package-summary.html
- 스웨거 https://swagger.io/docs/specification/v3_0/basic-structure/
- 시큐리티 https://docs.spring.io/spring-security/reference/servlet/authorization/authorize-http-requests.html#match-requests
- JWT + 이전 프로젝트 참고 https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/filter/OncePerRequestFilter.html

(JwtFilter) https://github.com/jwtk/jjwt?tab=readme-ov-file#creating-a-jwt
## REST API 문서 및 테스트 
### (Notion API 명세서) : https://rumbling-whip-45b.notion.site/API-05e4f9a27b4a83f9bc210181acba6640?source=copy_link

### (Swagger) :  http://localhost:8080/swagger-ui/index.html
