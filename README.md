# SPRING ADVANCED

## 📌 구현 기능

### Lv 0. 프로젝트 세팅
- `application.properties` 누락으로 인한 JWT 시크릿 키 설정 오류 해결
- `openssl rand -base64 32` 명령어로 올바른 Base64 형식의 JWT 시크릿 키 생성

### Lv 1. ArgumentResolver
- `AuthUserArgumentResolver`에 `@Component` 추가
- `WebMvcConfig` 클래스 새로 생성하여 `AuthUserArgumentResolver`를 Spring MVC에 등록

### Lv 2. 코드 개선
- **Early Return** : `signup()` 메서드에서 이메일 중복 체크를 먼저 수행하여 불필요한 `passwordEncoder.encode()` 호출 방지
- **if-else 제거** : `WeatherClient`의 `getTodayWeather()`에서 불필요한 `else` 블록 제거
- **Validation** : `UserChangePasswordRequest` DTO에 `@Size`, `@Pattern` 어노테이션 추가하여 비밀번호 유효성 검사를 서비스 레이어에서 DTO로 이동

### Lv 3. N+1 문제
- `TodoRepository`에서 `fetch join` 대신 `@EntityGraph` 사용으로 변경
- 페이징과 함께 안전하게 연관 데이터를 조회할 수 있도록 개선

### Lv 4. 테스트 코드
- `PasswordEncoderTest` : `matches()` 메서드 인자 순서 오류 수정
- `ManagerServiceTest` : 예외 타입 및 메서드명 수정, 서비스 로직 개선
- `CommentServiceTest` : 예외 타입 불일치 수정

### Lv 5. AOP 로깅
- `AdminApiLoggingAspect` 클래스 생성
- `CommentAdminController`, `UserAdminController` 메서드 실행 전후 요청/응답 로깅 구현
- 요청한 사용자 ID, 요청 시각, URL, 요청/응답 본문 기록