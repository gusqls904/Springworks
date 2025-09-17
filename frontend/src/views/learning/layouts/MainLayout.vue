<template>
  <div class="main-layout">
      <!-- 상단 헤더 -->
    <header class="main-header">
      <div class="header-left">
        <button class="menu-toggle" @click="drawer = !drawer">
          <i class="fas fa-bars"></i>
        </button>
        <h1 class="header-title">My Learning System</h1>
      </div>
      
      <div class="header-right">
        <!-- 알림 -->
        <button class="header-btn notification-btn">
          <i class="fas fa-bell"></i>
          <span class="notification-badge">3</span>
        </button>
        
        <!-- 사용자 정보 -->
        <div class="user-info">
          <img src="https://randomuser.me/api/portraits/men/32.jpg" alt="User" class="user-avatar">
          <span class="user-name">{{ user.name }}</span>
        </div>
  
        <!-- 로그아웃 -->
        <button class="header-btn logout-btn" @click="logout">
          <i class="fas fa-sign-out-alt"></i>
        </button>
      </div>
    </header>
  
      <!-- 사이드바 -->
    <aside class="sidebar" :class="{ 'sidebar-collapsed': !drawer }">
      <nav class="sidebar-nav">
        <ul class="nav-list">
          <li 
            v-for="menu in menus"
            :key="menu.name"
            class="nav-item"
            :class="{ 'nav-item-active': selectedMenu.name === menu.name }"
            @click="selectMenu(menu)"
          >
            <i :class="menu.icon"></i>
            <span class="nav-text">{{ menu.title }}</span>
          </li>
        </ul>
      </nav>
    </aside>

    <!-- 메인 콘텐츠 -->
    <main class="main-content">
      <div class="content-header">
        <h2 class="content-title">{{ selectedMenu.title }}</h2>
      </div>
      
      <div class="content-body">
  
          <!-- Dashboard -->
        <div v-if="selectedMenu.name === 'dashboard'" class="dashboard">
          <!-- 통계 카드 -->
          <div class="stats-grid">
            <div v-for="stat in dashboardStats" :key="stat.label" class="stat-card">
              <div class="stat-icon">
                <i :class="stat.icon"></i>
              </div>
              <div class="stat-content">
                <div class="stat-label">{{ stat.label }}</div>
                <div class="stat-value">{{ stat.value }}</div>
              </div>
            </div>
          </div>
  
          <!-- 최근 활동 -->
          <div class="activity-section">
            <div class="activity-grid">
              <!-- 최근 가입 회원 -->
              <div class="activity-card">
                <div class="card-header">
                  <h3 class="card-title">최근 가입 회원</h3>
                </div>
                <div class="card-body">
                  <div class="user-list">
                    <div v-for="user in recentUsers" :key="user.id" class="user-item">
                      <img :src="user.avatar" :alt="user.name" class="user-avatar-small">
                      <div class="user-details">
                        <div class="user-name">{{ user.name }}</div>
                        <div class="user-email">{{ user.email }}</div>
                      </div>
                      <div class="user-time">{{ user.registered }}</div>
                    </div>
                  </div>
                </div>
              </div>
              
              <!-- 최근 등록 강의 -->
              <div class="activity-card">
                <div class="card-header">
                  <h3 class="card-title">최근 등록 강의</h3>
                </div>
                <div class="card-body">
                  <div class="course-list">
                    <div v-for="course in recentCourses" :key="course.title" class="course-item">
                      <div class="course-info">
                        <div class="course-title">{{ course.title }}</div>
                        <div class="course-instructor">{{ course.instructor }}</div>
                      </div>
                      <div class="course-meta">
                        <div class="course-date">{{ course.createdAt }}</div>
                        <div class="course-price">{{ course.price.toLocaleString() }}원</div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
  
          <!-- 사용자 관리 -->
        <div v-if="selectedMenu.name === 'users'" class="users-section">
          <div class="section-header">
            <h3 class="section-title">사용자 관리</h3>
            <button class="btn btn-primary">
              <i class="fas fa-plus"></i>
              새 사용자 추가
            </button>
          </div>
          
          <div class="table-container">
            <table class="data-table">
              <thead>
                <tr>
                  <th v-for="header in userHeaders" :key="header.value" class="table-header">
                    {{ header.text }}
                  </th>
                  <th class="table-header">작업</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="user in users" :key="user.id" class="table-row">
                  <td class="table-cell">{{ user.id }}</td>
                  <td class="table-cell">{{ user.name }}</td>
                  <td class="table-cell">{{ user.email }}</td>
                  <td class="table-cell">
                    <div class="action-buttons">
                      <button class="btn btn-sm btn-secondary">수정</button>
                      <button class="btn btn-sm btn-danger">삭제</button>
                    </div>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
  
          <!-- 강의 관리 -->
        <div v-if="selectedMenu.name === 'courses'" class="courses-section">
          <div class="section-header">
            <h3 class="section-title">강의 관리</h3>
            <button class="btn btn-primary">
              <i class="fas fa-plus"></i>
              새 강의 추가
            </button>
          </div>
          
          <div class="table-container">
            <table class="data-table">
              <thead>
                <tr>
                  <th v-for="header in courseHeaders" :key="header.value" class="table-header">
                    {{ header.text }}
                  </th>
                  <th class="table-header">작업</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="course in courses" :key="course.id" class="table-row">
                  <td class="table-cell">{{ course.id }}</td>
                  <td class="table-cell">{{ course.title }}</td>
                  <td class="table-cell">{{ course.instructor }}</td>
                  <td class="table-cell">
                    <div class="action-buttons">
                      <button class="btn btn-sm btn-secondary">수정</button>
                      <button class="btn btn-sm btn-danger">삭제</button>
                    </div>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>

        <!-- 레이아웃 선택 -->
        <div v-if="selectedMenu.name === 'layouts'" class="layouts-section">
          <div class="section-header">
            <h3 class="section-title">레이아웃 선택</h3>
            <p class="section-description">다양한 디자인의 레이아웃을 선택하여 확인해보세요</p>
          </div>
          
          <div class="layouts-grid">
            <div v-for="layout in availableLayouts" :key="layout.name" class="layout-card">
              <div class="layout-preview">
                <div class="preview-image" :style="{ background: layout.gradient }">
                  <i :class="layout.icon"></i>
                </div>
                <div class="preview-overlay">
                  <button class="btn btn-primary" @click="openLayout(layout)">
                    <i class="fas fa-eye"></i>
                    미리보기
                  </button>
                </div>
              </div>
              <div class="layout-info">
                <h4 class="layout-title">{{ layout.title }}</h4>
                <p class="layout-description">{{ layout.description }}</p>
                <div class="layout-features">
                  <span v-for="feature in layout.features" :key="feature" class="feature-tag">
                    {{ feature }}
                  </span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </main>
  
      <!-- 푸터 -->
    <footer class="main-footer">
      <div class="footer-content">
        <span>&copy; 2025 MyLearningSystem. All rights reserved.</span>
      </div>
    </footer>
  </div>
  </template>
  
  <script setup>
  import { ref } from 'vue'
  import './common.css'
  
  // 사이드바 상태
  const drawer = ref(true)
  
  // 사용자 정보
  const user = ref({
    id: 1,
    name: '홍길동',
    email: 'hong@example.com'
  })
  
  // 메뉴
  const menus = ref([
  { name: 'dashboard', title: '대시보드', icon: 'fas fa-tachometer-alt' },
  { name: 'users', title: '사용자 관리', icon: 'fas fa-users' },
  { name: 'courses', title: '강의 관리', icon: 'fas fa-book' },
  { name: 'layouts', title: '레이아웃 선택', icon: 'fas fa-palette' },
  ])
  
  // 선택 메뉴
  const selectedMenu = ref(menus.value[0])
  const selectMenu = (menu) => (selectedMenu.value = menu)
  
  // 로그아웃
  const logout = () => alert('로그아웃 되었습니다.')
  
  // 대시보드 카드
  const dashboardStats = ref([
  { label: '총 사용자', value: 120, icon: 'fas fa-users' },
  { label: '등록 강의', value: 15, icon: 'fas fa-book' },
  { label: '오늘 로그인', value: 30, icon: 'fas fa-sign-in-alt' },
  { label: '총 매출', value: '1,250,000', icon: 'fas fa-won-sign' },
  ])
  
  // 최근 가입 회원
  const recentUsers = ref([
    { id: 1, name: '김철수', email: 'kim@example.com', registered: '1시간 전', avatar: 'https://randomuser.me/api/portraits/men/33.jpg' },
    { id: 2, name: '이영희', email: 'lee@example.com', registered: '3시간 전', avatar: 'https://randomuser.me/api/portraits/women/34.jpg' },
    { id: 3, name: '박민수', email: 'park@example.com', registered: '5시간 전', avatar: 'https://randomuser.me/api/portraits/men/35.jpg' },
  ])
  
  // 최근 등록 강의
  const recentCourseHeaders = [
    { text: '강의명', value: 'title' },
    { text: '강사', value: 'instructor' },
    { text: '등록일', value: 'createdAt' },
    { text: '가격', value: 'price' },
  ]
  const recentCourses = ref([
    { title: 'Vue 3 마스터 과정', instructor: '김철수', createdAt: '2025-09-14', price: 99000 },
    { title: 'Spring Boot 실무', instructor: '홍길동', createdAt: '2025-09-13', price: 129000 },
    { title: 'Docker와 Kubernetes', instructor: '이영희', createdAt: '2025-09-12', price: 159000 },
  ])
  
  // 사용자 관리 테이블
  const userHeaders = [
    { text: 'ID', value: 'id' },
    { text: '이름', value: 'name' },
    { text: '이메일', value: 'email' },
  ]
  const users = ref([
    { id: 1, name: '홍길동', email: 'hong@example.com' },
    { id: 2, name: '김철수', email: 'kim@example.com' },
    { id: 3, name: '이영희', email: 'lee@example.com' },
  ])
  
  // 강의 관리 테이블
  const courseHeaders = [
    { text: '강의ID', value: 'id' },
    { text: '강의명', value: 'title' },
    { text: '강사', value: 'instructor' },
  ]
  const courses = ref([
    { id: 101, title: 'Vue 3 마스터 과정', instructor: '김철수' },
    { id: 102, title: 'Spring Boot 실무', instructor: '홍길동' },
    { id: 103, title: 'Docker와 Kubernetes', instructor: '이영희' },
  ])

// 사용 가능한 레이아웃들
const availableLayouts = ref([
  {
    name: 'modern-card',
    title: '현대적 카드 레이아웃',
    description: '글래스모피즘 디자인과 그라데이션을 활용한 현대적인 UI',
    icon: 'fas fa-layer-group',
    gradient: 'linear-gradient(135deg, #667eea 0%, #764ba2 100%)',
    features: ['글래스모피즘', '그라데이션', '카드 기반', '현대적'],
    component: 'ModernCardLayout'
  },
  {
    name: 'minimal-sidebar',
    title: '미니멀 사이드바 레이아웃',
    description: '접을 수 있는 사이드바와 깔끔한 디자인의 실용적 UI',
    icon: 'fas fa-bars',
    gradient: 'linear-gradient(135deg, #4facfe 0%, #00f2fe 100%)',
    features: ['미니멀', '접는 사이드바', '깔끔한 UI', '실용적'],
    component: 'MinimalSidebarLayout'
  },
  {
    name: 'dark-theme',
    title: '다크 테마 레이아웃',
    description: '다크 모드와 네온 색상을 활용한 고급스러운 디자인',
    icon: 'fas fa-moon',
    gradient: 'linear-gradient(135deg, #2c3e50 0%, #34495e 100%)',
    features: ['다크 모드', '네온 효과', '고급스러운', '야간 모드'],
    component: 'DarkThemeLayout'
  },
  {
    name: 'dashboard-centered',
    title: '대시보드 중심 레이아웃',
    description: '데이터와 KPI 중심의 비즈니스 인텔리전스 UI',
    icon: 'fas fa-chart-pie',
    gradient: 'linear-gradient(135deg, #f093fb 0%, #f5576c 100%)',
    features: ['데이터 중심', 'KPI 카드', '실시간 위젯', '분석'],
    component: 'DashboardCenteredLayout'
  },
  {
    name: 'mobile-first',
    title: '모바일 우선 레이아웃',
    description: '모바일 최적화와 터치 친화적인 반응형 디자인',
    icon: 'fas fa-mobile-alt',
    gradient: 'linear-gradient(135deg, #43e97b 0%, #38f9d7 100%)',
    features: ['모바일 최적화', '터치 친화적', '반응형', '하단 네비게이션'],
    component: 'MobileFirstLayout'
  }
])

// 레이아웃 열기 함수
const openLayout = (layout) => {
  // 새 탭에서 레이아웃 컴포넌트 열기
  const layoutUrl = `/learning/layouts/${layout.name}`
  window.open(layoutUrl, '_blank')
}
  </script>
  
  <style scoped>
/* ============================================
   MAIN LAYOUT STYLES
   ============================================ */

.main-layout {
  display: grid;
  grid-template-areas: 
    "header header"
    "sidebar content"
    "footer footer";
  grid-template-columns: 250px 1fr;
  grid-template-rows: 60px 1fr 50px;
  min-height: 100vh;
  background-color: var(--bg-primary);
}

/* ============================================
   HEADER STYLES
   ============================================ */
.main-header {
  grid-area: header;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 20px;
  background: linear-gradient(135deg, var(--primary-color) 0%, var(--primary-light) 100%);
  color: white;
  box-shadow: var(--shadow-md);
  z-index: 1000;
}

.header-left {
  display: flex;
  align-items: center;
  gap: 15px;
}

.menu-toggle {
  background: none;
  border: none;
  color: white;
  font-size: 18px;
  cursor: pointer;
  padding: 8px;
  border-radius: 4px;
  transition: background-color 0.3s ease;
}

.menu-toggle:hover {
  background-color: rgba(255, 255, 255, 0.1);
}

.header-title {
  font-size: 20px;
  font-weight: 600;
  margin: 0;
}

.header-right {
  display: flex;
  align-items: center;
  gap: 20px;
}

.header-btn {
  background: none;
  border: none;
  color: white;
  font-size: 16px;
  cursor: pointer;
  padding: 8px;
  border-radius: 4px;
  position: relative;
  transition: background-color 0.3s ease;
}

.header-btn:hover {
  background-color: rgba(255, 255, 255, 0.1);
}

.notification-badge {
  position: absolute;
  top: 2px;
  right: 2px;
  background: #ef4444;
  color: white;
  font-size: 10px;
  padding: 2px 6px;
  border-radius: 10px;
  min-width: 16px;
  text-align: center;
}

.user-info {
  display: flex;
  align-items: center;
  gap: 10px;
}

.user-avatar {
  width: 32px;
  height: 32px;
  border-radius: 50%;
  object-fit: cover;
}

.user-name {
  font-weight: 500;
}

/* ============================================
   SIDEBAR STYLES
   ============================================ */
.sidebar {
  grid-area: sidebar;
  background: white;
  border-right: 1px solid var(--border-color);
  transition: all 0.3s ease;
  overflow-y: auto;
}

.sidebar-collapsed {
  grid-template-columns: 60px 1fr;
}

.sidebar-nav {
  padding: 20px 0;
}

.nav-list {
  list-style: none;
  margin: 0;
  padding: 0;
}

.nav-item {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 12px 20px;
    cursor: pointer;
  transition: all 0.3s ease;
  border-left: 3px solid transparent;
}

.nav-item:hover {
  background-color: var(--bg-light);
}

.nav-item-active {
  background-color: var(--primary-bg);
  border-left-color: var(--primary-color);
  color: var(--primary-color);
}

.nav-item i {
  font-size: 16px;
  width: 20px;
  text-align: center;
}

.nav-text {
  font-weight: 500;
}

/* ============================================
   MAIN CONTENT STYLES
   ============================================ */
.main-content {
  grid-area: content;
  padding: 20px;
  overflow-y: auto;
}

.content-header {
  margin-bottom: 30px;
}

.content-title {
  font-size: 24px;
  font-weight: 600;
  color: var(--text-primary);
  margin: 0;
}

/* ============================================
   DASHBOARD STYLES
   ============================================ */
.stats-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 20px;
  margin-bottom: 30px;
}

.stat-card {
  background: white;
  padding: 24px;
  border-radius: 12px;
  box-shadow: var(--shadow-sm);
  display: flex;
  align-items: center;
  gap: 16px;
  transition: all 0.3s ease;
}

.stat-card:hover {
  transform: translateY(-2px);
  box-shadow: var(--shadow-md);
}

.stat-icon {
  width: 50px;
  height: 50px;
  background: var(--primary-bg);
  border-radius: 12px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: var(--primary-color);
  font-size: 24px;
}

.stat-content {
  flex: 1;
}

.stat-label {
  font-size: 14px;
  color: var(--text-secondary);
  margin-bottom: 4px;
}

.stat-value {
  font-size: 24px;
  font-weight: 700;
  color: var(--text-primary);
}

.activity-section {
  margin-top: 30px;
}

.activity-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(400px, 1fr));
  gap: 20px;
}

.activity-card {
  background: white;
  border-radius: 12px;
  box-shadow: var(--shadow-sm);
  overflow: hidden;
}

.card-header {
  padding: 20px 24px;
  border-bottom: 1px solid var(--border-light);
  background: var(--bg-light);
}

.card-title {
  font-size: 18px;
  font-weight: 600;
  color: var(--text-primary);
  margin: 0;
}

.card-body {
  padding: 20px 24px;
}

.user-list, .course-list {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.user-item, .course-item {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 12px;
    border-radius: 8px;
  transition: background-color 0.3s ease;
}

.user-item:hover, .course-item:hover {
  background-color: var(--bg-light);
}

.user-avatar-small {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  object-fit: cover;
}

.user-details, .course-info {
  flex: 1;
}

.user-name, .course-title {
  font-weight: 600;
  color: var(--text-primary);
  margin-bottom: 4px;
}

.user-email, .course-instructor {
  font-size: 14px;
  color: var(--text-secondary);
}

.user-time, .course-meta {
  text-align: right;
  font-size: 12px;
  color: var(--text-muted);
}

.course-price {
  font-weight: 600;
  color: var(--primary-color);
}

/* ============================================
   TABLE STYLES
   ============================================ */
.section-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.section-title {
  font-size: 20px;
  font-weight: 600;
  color: var(--text-primary);
  margin: 0;
}

.table-container {
  background: white;
    border-radius: 12px;
  box-shadow: var(--shadow-sm);
  overflow: hidden;
}

.data-table {
  width: 100%;
  border-collapse: collapse;
}

.table-header {
  background: var(--bg-light);
  padding: 16px 20px;
  text-align: left;
  font-weight: 600;
  color: var(--text-primary);
  border-bottom: 1px solid var(--border-color);
}

.table-row {
  border-bottom: 1px solid var(--border-light);
  transition: background-color 0.3s ease;
}

.table-row:hover {
  background-color: var(--bg-light);
}

.table-cell {
  padding: 16px 20px;
  color: var(--text-primary);
}

.action-buttons {
  display: flex;
  gap: 8px;
}

/* ============================================
   FOOTER STYLES
   ============================================ */
.main-footer {
  grid-area: footer;
  background: white;
  border-top: 1px solid var(--border-color);
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 0 20px;
}

.footer-content {
  color: var(--text-secondary);
  font-size: 14px;
}

/* ============================================
   RESPONSIVE DESIGN
   ============================================ */
@media (max-width: 768px) {
  .main-layout {
    grid-template-columns: 1fr;
    grid-template-areas: 
      "header"
      "content"
      "footer";
  }
  
  .sidebar {
    position: fixed;
    top: 60px;
    left: -250px;
    width: 250px;
    height: calc(100vh - 60px);
    z-index: 999;
    transition: left 0.3s ease;
  }
  
  .sidebar:not(.sidebar-collapsed) {
    left: 0;
  }
  
  .stats-grid {
    grid-template-columns: 1fr;
  }
  
  .activity-grid {
    grid-template-columns: 1fr;
  }
  
  .table-container {
    overflow-x: auto;
  }
}

/* ============================================
   LAYOUTS SECTION STYLES
   ============================================ */
.layouts-section {
  padding: 20px 0;
}

.section-description {
  color: var(--text-secondary);
  margin: 8px 0 0 0;
  font-size: 16px;
}

.layouts-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(350px, 1fr));
  gap: 24px;
  margin-top: 24px;
}

.layout-card {
  background: white;
  border-radius: 16px;
  box-shadow: var(--shadow-sm);
  overflow: hidden;
  transition: all 0.3s ease;
  border: 1px solid var(--border-light);
}

.layout-card:hover {
    transform: translateY(-4px);
  box-shadow: var(--shadow-lg);
  border-color: var(--primary-color);
}

.layout-preview {
  position: relative;
  height: 200px;
  overflow: hidden;
}

.preview-image {
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  font-size: 48px;
  position: relative;
}

.preview-overlay {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.7);
  display: flex;
  align-items: center;
  justify-content: center;
  opacity: 0;
  transition: opacity 0.3s ease;
}

.layout-card:hover .preview-overlay {
  opacity: 1;
}

.layout-info {
  padding: 24px;
}

.layout-title {
  font-size: 20px;
  font-weight: 600;
  color: var(--text-primary);
  margin: 0 0 8px 0;
}

.layout-description {
  color: var(--text-secondary);
  font-size: 14px;
  line-height: 1.5;
  margin: 0 0 16px 0;
}

.layout-features {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
}

.feature-tag {
  background: var(--primary-bg);
  color: var(--primary-color);
  padding: 4px 8px;
  border-radius: 12px;
  font-size: 12px;
  font-weight: 500;
}

/* 레이아웃 카드 반응형 */
@media (max-width: 768px) {
  .layouts-grid {
    grid-template-columns: 1fr;
    gap: 16px;
  }
  
  .layout-preview {
    height: 160px;
  }
  
  .preview-image {
    font-size: 36px;
  }
  
  .layout-info {
    padding: 16px;
  }
  }
  </style>
  