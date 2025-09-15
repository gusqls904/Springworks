# VueWorks

Vue 3 + Vite로 구성된 현대적인 웹 애플리케이션 프로젝트입니다.

## 🚀 기술 스택

- **Vue 3** - Composition API를 사용한 반응형 프레임워크
- **Vite** - 빠른 개발 서버와 빌드 도구
- **ES6+** - 최신 JavaScript 문법 지원

## 📦 설치 및 실행

### 의존성 설치
```bash
npm install
```

### 개발 서버 실행
```bash
npm run dev
```

### 프로덕션 빌드
```bash
npm run build
```

### 빌드 미리보기
```bash
npm run preview
```

## 🎯 주요 기능

- ⚡ Vite의 빠른 HMR (Hot Module Replacement)
- 🎨 현대적인 UI/UX 디자인
- 📱 반응형 웹 디자인
- 🔧 Vue 3 Composition API 사용
- 🎪 인터랙티브 카운터 컴포넌트

## 📁 프로젝트 구조

```
vueworks/
├── public/
│   └── vite.svg          # Vite 아이콘
├── src/
│   ├── App.vue           # 메인 Vue 컴포넌트
│   ├── main.js           # 애플리케이션 진입점
│   └── style.css         # 전역 스타일
├── index.html            # HTML 템플릿
├── package.json          # 프로젝트 설정 및 의존성
├── vite.config.js        # Vite 설정
└── README.md             # 프로젝트 문서
```

## 🛠️ 개발 가이드

### 컴포넌트 개발
- Vue 3 Composition API를 사용하여 컴포넌트를 작성합니다
- `<script setup>` 문법을 활용할 수 있습니다
- 반응형 데이터는 `ref()` 또는 `reactive()`를 사용합니다

### 스타일링
- 전역 스타일은 `src/style.css`에 정의합니다
- 컴포넌트별 스타일은 `<style scoped>`를 사용합니다
- CSS Grid와 Flexbox를 활용한 반응형 레이아웃을 지원합니다

## 📄 라이선스

MIT License
