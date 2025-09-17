<template>
  <div class="mobile-layout">
    <!-- 모바일 헤더 -->
    <header class="mobile-header">
      <div class="header-top">
        <button class="menu-toggle" @click="sidebarOpen = !sidebarOpen">
          <i class="fas fa-bars"></i>
        </button>
        <div class="logo">
          <i class="fas fa-mobile-alt"></i>
          <span>Mobile Learning</span>
        </div>
        <div class="header-actions">
          <button class="action-btn notification-btn">
            <i class="fas fa-bell"></i>
            <span class="notification-badge" v-if="notifications.length > 0">{{ notifications.length }}</span>
          </button>
        </div>
      </div>
      
      <!-- 검색바 -->
      <div class="search-section">
        <div class="search-box">
          <i class="fas fa-search"></i>
          <input type="text" placeholder="검색..." class="search-input">
        </div>
      </div>
    </header>

    <!-- 모바일 사이드바 오버레이 -->
    <div class="sidebar-overlay" :class="{ 'active': sidebarOpen }" @click="sidebarOpen = false"></div>
    
    <!-- 모바일 사이드바 -->
    <aside class="mobile-sidebar" :class="{ 'open': sidebarOpen }">
      <div class="sidebar-header">
        <div class="user-profile">
          <img :src="user.avatar" :alt="user.name" class="user-avatar">
          <div class="user-info">
            <div class="user-name">{{ user.name }}</div>
            <div class="user-role">{{ user.role }}</div>
          </div>
        </div>
        <button class="close-btn" @click="sidebarOpen = false">
          <i class="fas fa-times"></i>
        </button>
      </div>
      
      <nav class="sidebar-nav">
        <div v-for="menu in menus" :key="menu.name" 
             class="nav-item" 
             :class="{ 'active': selectedMenu.name === menu.name }"
             @click="selectMenu(menu); sidebarOpen = false">
          <i :class="menu.icon"></i>
          <span>{{ menu.title }}</span>
          <div class="nav-badge" v-if="menu.badge">{{ menu.badge }}</div>
        </div>
      </nav>
      
      <div class="sidebar-footer">
        <button class="btn btn-outline">
          <i class="fas fa-cog"></i>
          설정
        </button>
        <button class="btn btn-primary">
          <i class="fas fa-sign-out-alt"></i>
          로그아웃
        </button>
      </div>
    </aside>

    <!-- 메인 콘텐츠 -->
    <main class="mobile-main">
      <!-- 페이지 헤더 -->
      <div class="page-header">
        <h1 class="page-title">{{ selectedMenu.title }}</h1>
        <p class="page-subtitle">{{ selectedMenu.description }}</p>
      </div>

      <!-- 대시보드 콘텐츠 -->
      <div v-if="selectedMenu.name === 'dashboard'" class="dashboard-content">
        <!-- 통계 카드 -->
        <div class="stats-section">
          <div class="section-title">
            <h3>오늘의 현황</h3>
            <button class="btn btn-sm btn-outline">전체 보기</button>
          </div>
          <div class="stats-grid">
            <div v-for="stat in dashboardStats" :key="stat.label" class="stat-card">
              <div class="stat-icon" :style="{ background: stat.color }">
                <i :class="stat.icon"></i>
              </div>
              <div class="stat-content">
                <div class="stat-value">{{ stat.value }}</div>
                <div class="stat-label">{{ stat.label }}</div>
                <div class="stat-change" :class="stat.trend">
                  <i :class="stat.trendIcon"></i>
                  {{ stat.change }}
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- 빠른 액션 -->
        <div class="quick-actions-section">
          <h3>빠른 작업</h3>
          <div class="quick-actions-grid">
            <button v-for="action in quickActions" :key="action.name" 
                    class="quick-action-btn" @click="action.handler">
              <i :class="action.icon"></i>
              <span>{{ action.name }}</span>
            </button>
          </div>
        </div>

        <!-- 최근 활동 -->
        <div class="recent-activity-section">
          <div class="section-header">
            <h3>최근 활동</h3>
            <button class="btn btn-sm btn-outline">전체 보기</button>
          </div>
          <div class="activity-list">
            <div v-for="activity in recentActivities" :key="activity.id" 
                 class="activity-item">
              <div class="activity-avatar">
                <img :src="activity.avatar" :alt="activity.user">
              </div>
              <div class="activity-content">
                <div class="activity-text">
                  <strong>{{ activity.user }}</strong> {{ activity.action }}
                </div>
                <div class="activity-time">{{ activity.time }}</div>
              </div>
              <div class="activity-status" :class="activity.status"></div>
            </div>
          </div>
        </div>
      </div>

      <!-- 사용자 관리 콘텐츠 -->
      <div v-if="selectedMenu.name === 'users'" class="users-content">
        <div class="content-header">
          <div class="header-info">
            <h2>사용자 관리</h2>
            <p>{{ users.length }}명의 사용자</p>
          </div>
          <button class="btn btn-primary">
            <i class="fas fa-plus"></i>
            추가
          </button>
        </div>

        <!-- 필터 바 -->
        <div class="filter-bar">
          <div class="filter-group">
            <select class="form-select">
              <option>모든 사용자</option>
              <option>활성 사용자</option>
              <option>비활성 사용자</option>
            </select>
          </div>
          <div class="search-group">
            <i class="fas fa-search"></i>
            <input type="text" placeholder="사용자 검색..." class="search-input">
          </div>
        </div>

        <!-- 사용자 카드 리스트 -->
        <div class="users-list">
          <div v-for="user in users" :key="user.id" class="user-card">
            <div class="user-card-header">
              <img :src="user.avatar" :alt="user.name" class="user-avatar-large">
              <div class="user-status" :class="user.status"></div>
            </div>
            <div class="user-card-body">
              <h4 class="user-name">{{ user.name }}</h4>
              <p class="user-email">{{ user.email }}</p>
              <div class="user-meta">
                <span class="user-role">{{ user.roleText }}</span>
                <span class="user-joined">{{ user.joinedAt }}</span>
              </div>
            </div>
            <div class="user-card-actions">
              <button class="btn btn-sm btn-outline">보기</button>
              <button class="btn btn-sm btn-primary">편집</button>
            </div>
          </div>
        </div>
      </div>

      <!-- 강의 관리 콘텐츠 -->
      <div v-if="selectedMenu.name === 'courses'" class="courses-content">
        <div class="content-header">
          <div class="header-info">
            <h2>강의 관리</h2>
            <p>{{ courses.length }}개의 강의</p>
          </div>
          <button class="btn btn-primary">
            <i class="fas fa-plus"></i>
            추가
          </button>
        </div>

        <!-- 강의 카드 리스트 -->
        <div class="courses-list">
          <div v-for="course in courses" :key="course.id" class="course-card">
            <div class="course-image">
              <img :src="course.image" :alt="course.title">
              <div class="course-overlay">
                <button class="btn btn-sm btn-primary">보기</button>
              </div>
            </div>
            <div class="course-content">
              <div class="course-header">
                <h4 class="course-title">{{ course.title }}</h4>
                <div class="course-rating">
                  <i class="fas fa-star"></i>
                  <span>{{ course.rating }}</span>
                </div>
              </div>
              <p class="course-description">{{ course.description }}</p>
              <div class="course-meta">
                <div class="course-instructor">
                  <i class="fas fa-user"></i>
                  {{ course.instructor }}
                </div>
                <div class="course-students">
                  <i class="fas fa-users"></i>
                  {{ course.students }}명
                </div>
              </div>
              <div class="course-footer">
                <div class="course-price">₩{{ course.price.toLocaleString() }}</div>
                <div class="course-actions">
                  <button class="btn btn-sm btn-outline">수정</button>
                  <button class="btn btn-sm btn-danger">삭제</button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- 하단 네비게이션 -->
      <div class="bottom-nav">
        <button v-for="menu in bottomMenus" :key="menu.name" 
                :class="['nav-btn', { 'active': selectedMenu.name === menu.name }]"
                @click="selectMenu(menu)">
          <i :class="menu.icon"></i>
          <span>{{ menu.title }}</span>
        </button>
      </div>
    </main>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import '../common.css'

// 상태 관리
const sidebarOpen = ref(false)

// 사용자 정보
const user = ref({
  id: 1,
  name: '홍길동',
  role: '관리자',
  avatar: 'https://randomuser.me/api/portraits/men/32.jpg'
})

// 메뉴
const menus = ref([
  { 
    name: 'dashboard', 
    title: '대시보드', 
    icon: 'fas fa-tachometer-alt',
    description: '전체 현황을 확인하세요',
    badge: '3'
  },
  { 
    name: 'users', 
    title: '사용자 관리', 
    icon: 'fas fa-users',
    description: '사용자를 관리하세요'
  },
  { 
    name: 'courses', 
    title: '강의 관리', 
    icon: 'fas fa-book',
    description: '강의를 관리하세요'
  },
  { 
    name: 'analytics', 
    title: '분석', 
    icon: 'fas fa-chart-line',
    description: '데이터를 분석하세요'
  },
  { 
    name: 'settings', 
    title: '설정', 
    icon: 'fas fa-cog',
    description: '시스템을 설정하세요'
  }
])

// 하단 네비게이션 메뉴
const bottomMenus = ref([
  { name: 'dashboard', title: '홈', icon: 'fas fa-home' },
  { name: 'users', title: '사용자', icon: 'fas fa-users' },
  { name: 'courses', title: '강의', icon: 'fas fa-book' },
  { name: 'analytics', title: '분석', icon: 'fas fa-chart-line' }
])

// 선택된 메뉴
const selectedMenu = ref(menus.value[0])
const selectMenu = (menu) => (selectedMenu.value = menu)

// 대시보드 통계
const dashboardStats = ref([
  {
    label: '총 사용자',
    value: '1,234',
    icon: 'fas fa-users',
    color: 'linear-gradient(135deg, #667eea 0%, #764ba2 100%)',
    trend: 'up',
    trendIcon: 'fas fa-arrow-up',
    change: '+12%'
  },
  {
    label: '활성 강의',
    value: '45',
    icon: 'fas fa-book',
    color: 'linear-gradient(135deg, #f093fb 0%, #f5576c 100%)',
    trend: 'up',
    trendIcon: 'fas fa-arrow-up',
    change: '+8%'
  },
  {
    label: '완료된 수강',
    value: '892',
    icon: 'fas fa-graduation-cap',
    color: 'linear-gradient(135deg, #4facfe 0%, #00f2fe 100%)',
    trend: 'up',
    trendIcon: 'fas fa-arrow-up',
    change: '+15%'
  },
  {
    label: '월간 수익',
    value: '₩12.5M',
    icon: 'fas fa-won-sign',
    color: 'linear-gradient(135deg, #43e97b 0%, #38f9d7 100%)',
    trend: 'down',
    trendIcon: 'fas fa-arrow-down',
    change: '-3%'
  }
])

// 빠른 작업
const quickActions = ref([
  { name: '사용자 추가', icon: 'fas fa-user-plus', handler: () => console.log('사용자 추가') },
  { name: '강의 생성', icon: 'fas fa-plus-circle', handler: () => console.log('강의 생성') },
  { name: '데이터 내보내기', icon: 'fas fa-download', handler: () => console.log('데이터 내보내기') },
  { name: '시스템 설정', icon: 'fas fa-cog', handler: () => console.log('시스템 설정') }
])

// 최근 활동
const recentActivities = ref([
  {
    id: 1,
    user: '김철수',
    action: '새 강의를 등록했습니다',
    time: '5분 전',
    avatar: 'https://randomuser.me/api/portraits/men/33.jpg',
    status: 'success'
  },
  {
    id: 2,
    user: '이영희',
    action: '강의를 완료했습니다',
    time: '1시간 전',
    avatar: 'https://randomuser.me/api/portraits/women/34.jpg',
    status: 'info'
  },
  {
    id: 3,
    user: '박민수',
    action: '새 계정을 생성했습니다',
    time: '3시간 전',
    avatar: 'https://randomuser.me/api/portraits/men/35.jpg',
    status: 'success'
  }
])

// 알림
const notifications = ref([
  { id: 1, title: '새로운 사용자 가입', message: '김철수님이 새로 가입했습니다.' },
  { id: 2, title: '시스템 업데이트', message: '새로운 기능이 추가되었습니다.' }
])

// 사용자 목록
const users = ref([
  {
    id: 1,
    name: '홍길동',
    email: 'hong@example.com',
    role: 'admin',
    roleText: '관리자',
    joinedAt: '2024-01-15',
    status: 'active',
    avatar: 'https://randomuser.me/api/portraits/men/32.jpg'
  },
  {
    id: 2,
    name: '김철수',
    email: 'kim@example.com',
    role: 'instructor',
    roleText: '강사',
    joinedAt: '2024-02-20',
    status: 'active',
    avatar: 'https://randomuser.me/api/portraits/men/33.jpg'
  },
  {
    id: 3,
    name: '이영희',
    email: 'lee@example.com',
    role: 'student',
    roleText: '학생',
    joinedAt: '2024-03-10',
    status: 'inactive',
    avatar: 'https://randomuser.me/api/portraits/women/34.jpg'
  }
])

// 강의 목록
const courses = ref([
  {
    id: 1,
    title: 'Vue.js 완벽 가이드',
    description: 'Vue.js의 모든 것을 배우는 완벽한 강의입니다.',
    instructor: '홍길동',
    students: 156,
    rating: 4.8,
    price: 99000,
    image: 'https://images.unsplash.com/photo-1555066931-4365d14bab8c?w=400&h=250&fit=crop'
  },
  {
    id: 2,
    title: 'React 실무 프로젝트',
    description: '실제 프로젝트로 배우는 React 개발',
    instructor: '김철수',
    students: 134,
    rating: 4.9,
    price: 129000,
    image: 'https://images.unsplash.com/photo-1633356122544-f134324a6cee?w=400&h=250&fit=crop'
  },
  {
    id: 3,
    title: 'Node.js 백엔드 개발',
    description: '서버 사이드 개발의 모든 것',
    instructor: '이영희',
    students: 98,
    rating: 4.7,
    price: 159000,
    image: 'https://images.unsplash.com/photo-1627398242454-45a1465c2479?w=400&h=250&fit=crop'
  }
])
</script>

<style scoped>
/* ============================================
   MOBILE FIRST LAYOUT STYLES
   ============================================ */

.mobile-layout {
  min-height: 100vh;
  background: #f8fafc;
  padding-bottom: 80px; /* 하단 네비게이션 공간 */
}

/* ============================================
   MOBILE HEADER STYLES
   ============================================ */
.mobile-header {
  background: white;
  border-bottom: 1px solid #e2e8f0;
  position: sticky;
  top: 0;
  z-index: 1000;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
}

.header-top {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 12px 16px;
  height: 56px;
}

.menu-toggle {
  width: 40px;
  height: 40px;
  border: none;
  background: #f1f5f9;
  border-radius: 8px;
  color: #64748b;
  cursor: pointer;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  justify-content: center;
}

.menu-toggle:hover {
  background: #e2e8f0;
  color: #475569;
}

.logo {
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 18px;
  font-weight: 700;
  color: #1e293b;
}

.logo i {
  color: #3b82f6;
  font-size: 20px;
}

.header-actions {
  display: flex;
  align-items: center;
  gap: 8px;
}

.action-btn {
  position: relative;
  width: 40px;
  height: 40px;
  border: none;
  background: #f1f5f9;
  border-radius: 8px;
  color: #64748b;
  cursor: pointer;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  justify-content: center;
}

.action-btn:hover {
  background: #e2e8f0;
  color: #475569;
}

.notification-badge {
  position: absolute;
  top: -4px;
  right: -4px;
  background: #ef4444;
  color: white;
  font-size: 10px;
  padding: 2px 6px;
  border-radius: 10px;
  min-width: 16px;
  text-align: center;
}

.search-section {
  padding: 0 16px 16px;
}

.search-box {
  position: relative;
  display: flex;
  align-items: center;
}

.search-box i {
  position: absolute;
  left: 16px;
  color: #94a3b8;
  font-size: 16px;
}

.search-input {
  width: 100%;
  padding: 12px 16px 12px 48px;
  border: 1px solid #d1d5db;
  border-radius: 12px;
  font-size: 16px;
  background: #f8fafc;
  transition: all 0.3s ease;
}

.search-input:focus {
  outline: none;
  border-color: #3b82f6;
  background: white;
  box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.1);
}

/* ============================================
   SIDEBAR OVERLAY STYLES
   ============================================ */
.sidebar-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.5);
  z-index: 999;
  opacity: 0;
  visibility: hidden;
  transition: all 0.3s ease;
}

.sidebar-overlay.active {
  opacity: 1;
  visibility: visible;
}

/* ============================================
   MOBILE SIDEBAR STYLES
   ============================================ */
.mobile-sidebar {
  position: fixed;
  top: 0;
  left: -280px;
  width: 280px;
  height: 100vh;
  background: white;
  z-index: 1000;
  transition: left 0.3s ease;
  display: flex;
  flex-direction: column;
  box-shadow: 2px 0 10px rgba(0, 0, 0, 0.1);
}

.mobile-sidebar.open {
  left: 0;
}

.sidebar-header {
  padding: 20px;
  border-bottom: 1px solid #e2e8f0;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.user-profile {
  display: flex;
  align-items: center;
  gap: 12px;
}

.user-avatar {
  width: 48px;
  height: 48px;
  border-radius: 50%;
  object-fit: cover;
}

.user-info .user-name {
  font-weight: 600;
  color: #1e293b;
  margin-bottom: 2px;
}

.user-role {
  font-size: 14px;
  color: #64748b;
}

.close-btn {
  width: 32px;
  height: 32px;
  border: none;
  background: #f1f5f9;
  border-radius: 6px;
  color: #64748b;
  cursor: pointer;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  justify-content: center;
}

.close-btn:hover {
  background: #e2e8f0;
  color: #475569;
}

.sidebar-nav {
  flex: 1;
  padding: 20px 0;
  overflow-y: auto;
}

.nav-item {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 16px 20px;
  margin: 4px 0;
  cursor: pointer;
  transition: all 0.3s ease;
  color: #64748b;
  position: relative;
}

.nav-item:hover {
  background: #f1f5f9;
  color: #475569;
}

.nav-item.active {
  background: #dbeafe;
  color: #1e40af;
}

.nav-item i {
  font-size: 18px;
  width: 20px;
  text-align: center;
}

.nav-item span {
  font-weight: 500;
  flex: 1;
}

.nav-badge {
  background: #ef4444;
  color: white;
  font-size: 10px;
  padding: 2px 6px;
  border-radius: 10px;
  font-weight: 600;
}

.sidebar-footer {
  padding: 20px;
  border-top: 1px solid #e2e8f0;
  display: flex;
  flex-direction: column;
  gap: 12px;
}

/* ============================================
   MAIN CONTENT STYLES
   ============================================ */
.mobile-main {
  padding: 16px;
}

.page-header {
  margin-bottom: 24px;
}

.page-title {
  font-size: 24px;
  font-weight: 700;
  color: #1e293b;
  margin: 0 0 8px 0;
}

.page-subtitle {
  color: #64748b;
  margin: 0;
  font-size: 16px;
}

/* ============================================
   DASHBOARD STYLES
   ============================================ */
.dashboard-content {
  display: flex;
  flex-direction: column;
  gap: 24px;
}

.stats-section {
  background: white;
  border-radius: 16px;
  padding: 20px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
}

.section-title {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 16px;
}

.section-title h3 {
  font-size: 18px;
  font-weight: 600;
  color: #1e293b;
  margin: 0;
}

.stats-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 16px;
}

.stat-card {
  background: #f8fafc;
  border-radius: 12px;
  padding: 16px;
  display: flex;
  align-items: center;
  gap: 12px;
  transition: all 0.3s ease;
}

.stat-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.stat-icon {
  width: 40px;
  height: 40px;
  border-radius: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  font-size: 18px;
}

.stat-content {
  flex: 1;
}

.stat-value {
  font-size: 20px;
  font-weight: 700;
  color: #1e293b;
  margin-bottom: 2px;
}

.stat-label {
  font-size: 12px;
  color: #64748b;
  margin-bottom: 4px;
}

.stat-change {
  display: flex;
  align-items: center;
  gap: 4px;
  font-size: 11px;
  font-weight: 600;
}

.stat-change.up {
  color: #059669;
}

.stat-change.down {
  color: #dc2626;
}

.quick-actions-section {
  background: white;
  border-radius: 16px;
  padding: 20px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
}

.quick-actions-section h3 {
  font-size: 18px;
  font-weight: 600;
  color: #1e293b;
  margin: 0 0 16px 0;
}

.quick-actions-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 12px;
}

.quick-action-btn {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 8px;
  padding: 16px 12px;
  background: #f8fafc;
  border: none;
  border-radius: 12px;
  cursor: pointer;
  transition: all 0.3s ease;
}

.quick-action-btn:hover {
  background: #f1f5f9;
  transform: translateY(-1px);
}

.quick-action-btn i {
  font-size: 20px;
  color: #3b82f6;
}

.quick-action-btn span {
  font-size: 12px;
  font-weight: 500;
  color: #374151;
}

.recent-activity-section {
  background: white;
  border-radius: 16px;
  padding: 20px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
}

.section-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 16px;
}

.section-header h3 {
  font-size: 18px;
  font-weight: 600;
  color: #1e293b;
  margin: 0;
}

.activity-list {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.activity-item {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 12px;
  background: #f8fafc;
  border-radius: 12px;
  transition: background-color 0.3s ease;
}

.activity-item:hover {
  background: #f1f5f9;
}

.activity-avatar {
  width: 36px;
  height: 36px;
  border-radius: 50%;
  overflow: hidden;
}

.activity-avatar img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.activity-content {
  flex: 1;
}

.activity-text {
  font-size: 14px;
  color: #374151;
  margin-bottom: 2px;
}

.activity-time {
  font-size: 12px;
  color: #9ca3af;
}

.activity-status {
  width: 8px;
  height: 8px;
  border-radius: 50%;
}

.activity-status.success {
  background: #10b981;
}

.activity-status.info {
  background: #3b82f6;
}

/* ============================================
   USERS CONTENT STYLES
   ============================================ */
.users-content {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.content-header {
  background: white;
  border-radius: 16px;
  padding: 20px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.header-info h2 {
  font-size: 20px;
  font-weight: 700;
  color: #1e293b;
  margin: 0 0 4px 0;
}

.header-info p {
  color: #64748b;
  margin: 0;
  font-size: 14px;
}

.filter-bar {
  background: white;
  border-radius: 16px;
  padding: 16px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.filter-group {
  flex: 1;
}

.form-select {
  width: 100%;
  padding: 12px 16px;
  border: 1px solid #d1d5db;
  border-radius: 8px;
  background: white;
  font-size: 14px;
  color: #374151;
  cursor: pointer;
}

.search-group {
  position: relative;
  flex: 1;
}

.search-group i {
  position: absolute;
  left: 16px;
  top: 50%;
  transform: translateY(-50%);
  color: #94a3b8;
  font-size: 16px;
}

.search-group .search-input {
  width: 100%;
  padding: 12px 16px 12px 48px;
  border: 1px solid #d1d5db;
  border-radius: 8px;
  font-size: 14px;
  background: white;
}

.users-list {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.user-card {
  background: white;
  border-radius: 16px;
  padding: 20px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease;
}

.user-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
}

.user-card-header {
  position: relative;
  text-align: center;
  margin-bottom: 16px;
}

.user-avatar-large {
  width: 60px;
  height: 60px;
  border-radius: 50%;
  object-fit: cover;
  margin: 0 auto 8px;
}

.user-status {
  position: absolute;
  top: 0;
  right: calc(50% - 30px);
  width: 12px;
  height: 12px;
  border-radius: 50%;
  border: 3px solid white;
}

.user-status.active {
  background: #10b981;
}

.user-status.inactive {
  background: #ef4444;
}

.user-card-body {
  text-align: center;
  margin-bottom: 16px;
}

.user-name {
  font-size: 18px;
  font-weight: 600;
  color: #1e293b;
  margin: 0 0 4px 0;
}

.user-email {
  color: #64748b;
  margin: 0 0 8px 0;
  font-size: 14px;
}

.user-meta {
  display: flex;
  justify-content: center;
  gap: 12px;
  font-size: 12px;
}

.user-role {
  background: #dbeafe;
  color: #1e40af;
  padding: 4px 8px;
  border-radius: 12px;
  font-weight: 500;
}

.user-joined {
  color: #9ca3af;
}

.user-card-actions {
  display: flex;
  gap: 8px;
  justify-content: center;
}

/* ============================================
   COURSES CONTENT STYLES
   ============================================ */
.courses-content {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.courses-list {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.course-card {
  background: white;
  border-radius: 16px;
  overflow: hidden;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease;
}

.course-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
}

.course-image {
  position: relative;
  height: 160px;
  overflow: hidden;
}

.course-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.course-overlay {
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

.course-card:hover .course-overlay {
  opacity: 1;
}

.course-content {
  padding: 16px;
}

.course-header {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  margin-bottom: 8px;
}

.course-title {
  font-size: 16px;
  font-weight: 600;
  color: #1e293b;
  margin: 0;
  flex: 1;
}

.course-rating {
  display: flex;
  align-items: center;
  gap: 4px;
  color: #fbbf24;
  font-size: 12px;
  font-weight: 500;
}

.course-description {
  color: #64748b;
  font-size: 14px;
  line-height: 1.4;
  margin-bottom: 12px;
}

.course-meta {
  display: flex;
  gap: 12px;
  margin-bottom: 12px;
  font-size: 12px;
  color: #9ca3af;
}

.course-meta i {
  margin-right: 4px;
}

.course-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.course-price {
  font-size: 16px;
  font-weight: 700;
  color: #1e293b;
}

.course-actions {
  display: flex;
  gap: 4px;
}

/* ============================================
   BOTTOM NAVIGATION STYLES
   ============================================ */
.bottom-nav {
  position: fixed;
  bottom: 0;
  left: 0;
  right: 0;
  background: white;
  border-top: 1px solid #e2e8f0;
  display: flex;
  z-index: 1000;
  box-shadow: 0 -2px 10px rgba(0, 0, 0, 0.1);
}

.nav-btn {
  flex: 1;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 4px;
  padding: 8px 4px;
  border: none;
  background: none;
  color: #9ca3af;
  cursor: pointer;
  transition: all 0.3s ease;
  font-size: 10px;
  font-weight: 500;
}

.nav-btn:hover {
  color: #64748b;
}

.nav-btn.active {
  color: #3b82f6;
}

.nav-btn i {
  font-size: 18px;
}

/* ============================================
   RESPONSIVE DESIGN
   ============================================ */
@media (min-width: 768px) {
  .mobile-layout {
    padding-bottom: 0;
  }
  
  .bottom-nav {
    display: none;
  }
  
  .stats-grid {
    grid-template-columns: repeat(4, 1fr);
  }
  
  .quick-actions-grid {
    grid-template-columns: repeat(4, 1fr);
  }
  
  .users-list {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
    gap: 20px;
  }
  
  .courses-list {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
    gap: 20px;
  }
  
  .filter-bar {
    flex-direction: row;
    gap: 16px;
  }
}

@media (min-width: 1024px) {
  .mobile-sidebar {
    position: static;
    left: 0;
    width: 280px;
    height: auto;
    box-shadow: none;
    border-right: 1px solid #e2e8f0;
  }
  
  .mobile-layout {
    display: flex;
    min-height: 100vh;
  }
  
  .mobile-main {
    flex: 1;
    padding: 24px;
  }
  
  .sidebar-overlay {
    display: none;
  }
  
  .menu-toggle {
    display: none;
  }
}
</style>

