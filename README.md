# Springworks 프로젝트

Spring Boot 3.5.5 + Vue.js 3 기반의 풀스택 웹 애플리케이션 프로젝트입니다.

## 📋 프로젝트 개요

| 항목             | 값                           |
|-----------------|-----------------------------|
| **Backend**     | Spring Boot 3.5.5 + Java 21 |
| **Frontend**    | Vue.js 3 + Vite 5.0         |
| **Database**    | MariaDB 3.3.3               |
| **ORM**         | MyBatis 3.0.3               |
| **Security**    | Spring Security + JWT        |
| **API Docs**    | Swagger (OpenAPI) 2.8.13    |
| **Build Tool**  | Maven (Backend), npm (Frontend) |

## 🏗️ 프로젝트 구조

```
Springworks/
├── backend/                    # Spring Boot 백엔드
│   ├── src/main/java/
│   │   └── com/study/work/
│   │       ├── config/        # 설정 클래스들
│   │       │   ├── SecurityConfig.java
│   │       │   ├── SwaggerConfig.java
│   │       │   └── WebConfig.java
│   │       ├── learning/      # 학습 관련 모듈
│   │       │   └── user/      # 사용자 관리
│   │       │       ├── ctl/   # 컨트롤러
│   │       │       ├── dao/   # 데이터 액세스
│   │       │       ├── dto/   # 데이터 전송 객체
│   │       │       └── svc/   # 서비스
│   │       ├── ServletInitializer.java
│   │       └── SpringworksApplication.java
│   ├── src/main/resources/
│   │   ├── application.yml     # 애플리케이션 설정
│   │   ├── mapper/            # MyBatis 매퍼
│   │   └── static/            # 정적 리소스 (프론트엔드 빌드 결과물)
│   └── pom.xml
└── frontend/                   # Vue.js 프론트엔드
    ├── src/
    │   ├── components/        # Vue 컴포넌트
    │   ├── views/            # 페이지 뷰
    │   │   ├── common/       # 공통 페이지
    │   │   ├── learning/     # 학습 관련 페이지
    │   │   └── mall/         # 쇼핑몰 관련 페이지
    │   └── util/             # 유틸리티
    ├── public/               # 정적 리소스
    ├── package.json
    └── vite.config.js        # Vite 설정 (빌드 결과물을 backend/static으로)
```

## 🚀 실행 방법

### 백엔드 실행
```bash
cd backend
./mvnw spring-boot:run
```
또는
```bash
cd backend
mvn spring-boot:run
```

### 프론트엔드 개발 서버 실행
```bash
cd frontend
npm install
npm run dev
```

### 프론트엔드 빌드
```bash
cd frontend
npm run build
```
빌드 결과물은 자동으로 `backend/src/main/resources/static` 폴더에 생성됩니다.

## ⚡ 주요 기능

### 백엔드 (Spring Boot)
- **사용자 관리**: 회원가입, 로그인, 사용자 정보 관리
- **보안**: Spring Security + JWT 토큰 기반 인증
- **API 문서화**: Swagger UI를 통한 API 문서 자동 생성
- **데이터베이스**: MariaDB + MyBatis ORM
- **CORS 설정**: 프론트엔드와의 통신을 위한 CORS 설정

### 프론트엔드 (Vue.js)
- **SPA**: Vue Router를 사용한 단일 페이지 애플리케이션
- **컴포넌트**: 재사용 가능한 Vue 컴포넌트 구조
- **스타일링**: CSS 모듈화 및 반응형 디자인
- **API 통신**: Axios를 사용한 백엔드 API 통신
- **빌드 최적화**: Vite를 사용한 빠른 빌드 및 개발 서버

## 🌐 접속 정보

- **백엔드 서버**: http://localhost:8090
- **프론트엔드 개발 서버**: http://localhost:3000
- **API 문서 (Swagger)**: http://localhost:8090/swagger-ui.html

## 📦 주요 의존성

### Backend (Maven)
- Spring Boot 3.5.5
- Spring Security
- Spring Data JPA
- MyBatis 3.0.3
- MariaDB Driver 3.3.3
- JWT (jjwt) 0.11.5
- Swagger (OpenAPI) 2.8.13
- Lombok
- Spring Boot DevTools

### Frontend (npm)
- Vue.js 3.4.0
- Vue Router 4.5.1
- Vite 5.0.0
- Axios 1.11.0
- FontAwesome 7.0.1

## 🔄 개발 워크플로우

1. **개발 환경 설정**
   ```bash
   # 백엔드 의존성 설치 (Maven)
   cd backend
   ./mvnw clean install
   
   # 프론트엔드 의존성 설치
   cd frontend
   npm install
   ```

2. **개발 서버 실행**
   ```bash
   # 터미널 1: 백엔드 실행
   cd backend && ./mvnw spring-boot:run
   
   # 터미널 2: 프론트엔드 개발 서버 실행
   cd frontend && npm run dev
   ```

3. **프로덕션 빌드**
   ```bash
   # 프론트엔드 빌드 (자동으로 backend/static에 복사)
   cd frontend && npm run build
   
   # 백엔드 실행 (프론트엔드 포함)
   cd backend && ./mvnw spring-boot:run
   ```

## 📝 추가 정보

- **Java 버전**: 21
- **Node.js 버전**: 권장 18.x 이상
- **데이터베이스**: MariaDB (설정은 application.yml 참조)
- **빌드 결과물**: WAR 파일로 패키징되어 배포 가능