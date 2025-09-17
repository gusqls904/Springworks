<template>
  <div class="modern-layout">
    <!-- 상단 네비게이션 -->
    <nav class="modern-nav">
      <div class="nav-container">
        <div class="nav-brand">
          <div class="brand-icon">
            <i class="fas fa-graduation-cap"></i>
          </div>
          <h1 class="brand-title">Learning Hub</h1>
        </div>
        
        <div class="nav-center">
          <div class="search-box">
            <i class="fas fa-search"></i>
            <input type="text" placeholder="강의, 사용자, 콘텐츠 검색..." class="search-input">
          </div>
        </div>
        
        <div class="nav-actions">
          <button class="nav-btn notification-btn">
            <i class="fas fa-bell"></i>
            <span class="notification-dot"></span>
          </button>
          <div class="user-dropdown">
            <img :src="user.avatar" :alt="user.name" class="user-avatar">
            <span class="user-name">{{ user.name }}</span>
            <i class="fas fa-chevron-down"></i>
          </div>
        </div>
      </div>
    </nav>

    <!-- 메인 콘텐츠 -->
    <main class="modern-main">
      <!-- 사이드바 -->
      <aside class="modern-sidebar" :class="{ 'collapsed': sidebarCollapsed }">
        <div class="sidebar-header">
          <button class="sidebar-toggle" @click="sidebarCollapsed = !sidebarCollapsed">
            <i class="fas fa-bars"></i>
          </button>
        </div>
        
        <nav class="sidebar-nav">
          <div v-for="menu in menus" :key="menu.name" 
               class="nav-item" 
               :class="{ 'active': selectedMenu.name === menu.name }"
               @click="selectMenu(menu)">
            <div class="nav-icon">
              <i :class="menu.icon"></i>
            </div>
            <span class="nav-text" v-show="!sidebarCollapsed">{{ menu.title }}</span>
            <div class="nav-badge" v-if="menu.badge && !sidebarCollapsed">{{ menu.badge }}</div>
          </div>
        </nav>
      </aside>

      <!-- 콘텐츠 영역 -->
      <div class="content-area">
        <!-- 페이지 헤더 -->
        <header class="page-header">
          <div class="header-content">
            <div class="page-title">
              <h2>{{ selectedMenu.title }}</h2>
              <p class="page-subtitle">{{ selectedMenu.description }}</p>
            </div>
            <div class="header-actions">
              <button class="btn btn-outline">
                <i class="fas fa-download"></i>
                내보내기
              </button>
              <button class="btn btn-primary">
                <i class="fas fa-plus"></i>
                새로 만들기
              </button>
            </div>
          </div>
        </header>

        <!-- 대시보드 콘텐츠 -->
        <div v-if="selectedMenu.name === 'dashboard'" class="dashboard-content">
          <!-- 통계 카드 그리드 -->
          <div class="stats-section">
            <div class="stats-grid">
              <div v-for="stat in dashboardStats" :key="stat.label" class="stat-card modern-card">
                <div class="stat-header">
                  <div class="stat-icon" :style="{ backgroundColor: stat.color }">
                    <i :class="stat.icon"></i>
                  </div>
                  <div class="stat-trend" :class="stat.trend">
                    <i :class="stat.trendIcon"></i>
                    <span>{{ stat.change }}</span>
                  </div>
                </div>
                <div class="stat-content">
                  <div class="stat-value">{{ stat.value }}</div>
                  <div class="stat-label">{{ stat.label }}</div>
                </div>
                <div class="stat-chart">
                  <div class="mini-chart" :style="{ '--progress': stat.progress }"></div>
                </div>
              </div>
            </div>
          </div>

          <!-- 활동 피드 -->
          <div class="activity-section">
            <div class="section-grid">
              <!-- 최근 활동 -->
              <div class="activity-card modern-card">
                <div class="card-header">
                  <h3>최근 활동</h3>
                  <button class="btn btn-sm btn-outline">전체 보기</button>
                </div>
                <div class="card-body">
                  <div class="activity-list">
                    <div v-for="activity in recentActivities" :key="activity.id" class="activity-item">
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

              <!-- 빠른 작업 -->
              <div class="quick-actions-card modern-card">
                <div class="card-header">
                  <h3>빠른 작업</h3>
                </div>
                <div class="card-body">
                  <div class="quick-actions">
                    <button v-for="action in quickActions" :key="action.name" 
                            class="quick-action-btn" @click="action.handler">
                      <i :class="action.icon"></i>
                      <span>{{ action.name }}</span>
                    </button>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- 사용자 관리 콘텐츠 -->
        <div v-if="selectedMenu.name === 'users'" class="users-content">
          <div class="content-card modern-card">
            <div class="card-header">
              <div class="header-left">
                <h3>사용자 관리</h3>
                <div class="user-count">{{ users.length }}명의 사용자</div>
              </div>
              <div class="header-right">
                <div class="filter-group">
                  <select class="form-select">
                    <option>모든 상태</option>
                    <option>활성</option>
                    <option>비활성</option>
                  </select>
                </div>
                <button class="btn btn-primary">
                  <i class="fas fa-user-plus"></i>
                  사용자 추가
                </button>
              </div>
            </div>
            <div class="card-body">
              <div class="table-responsive">
                <table class="modern-table">
                  <thead>
                    <tr>
                      <th class="checkbox-col">
                        <input type="checkbox" class="form-checkbox">
                      </th>
                      <th>사용자</th>
                      <th>이메일</th>
                      <th>가입일</th>
                      <th>상태</th>
                      <th>작업</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr v-for="user in users" :key="user.id" class="table-row">
                      <td class="checkbox-col">
                        <input type="checkbox" class="form-checkbox">
                      </td>
                      <td>
                        <div class="user-info">
                          <img :src="user.avatar" :alt="user.name" class="user-avatar-small">
                          <div class="user-details">
                            <div class="user-name">{{ user.name }}</div>
                            <div class="user-role">{{ user.role }}</div>
                          </div>
                        </div>
                      </td>
                      <td>{{ user.email }}</td>
                      <td>{{ user.joinedAt }}</td>
                      <td>
                        <span class="status-badge" :class="user.status">{{ user.statusText }}</span>
                      </td>
                      <td>
                        <div class="action-menu">
                          <button class="action-btn">
                            <i class="fas fa-ellipsis-v"></i>
                          </button>
                        </div>
                      </td>
                    </tr>
                  </tbody>
                </table>
              </div>
            </div>
          </div>
        </div>
      </div>
    </main>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import '../common.css'

// 상태 관리
const sidebarCollapsed = ref(false)

// 사용자 정보
const user = ref({
  id: 1,
  name: '홍길동',
  email: 'hong@example.com',
  avatar: 'https://randomuser.me/api/portraits/men/32.jpg'
})

// 메뉴
const menus = ref([
  { 
    name: 'dashboard', 
    title: '대시보드', 
    icon: 'fas fa-tachometer-alt',
    description: '전체 현황을 한눈에 확인하세요',
    badge: '3'
  },
  { 
    name: 'users', 
    title: '사용자 관리', 
    icon: 'fas fa-users',
    description: '사용자 계정을 관리하세요'
  },
  { 
    name: 'courses', 
    title: '강의 관리', 
    icon: 'fas fa-book',
    description: '강의 콘텐츠를 관리하세요'
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
    description: '시스템 설정을 관리하세요'
  }
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
    color: '#3b82f6',
    trend: 'up',
    trendIcon: 'fas fa-arrow-up',
    change: '+12%',
    progress: '75%'
  },
  {
    label: '활성 강의',
    value: '45',
    icon: 'fas fa-book',
    color: '#10b981',
    trend: 'up',
    trendIcon: 'fas fa-arrow-up',
    change: '+8%',
    progress: '60%'
  },
  {
    label: '완료된 수강',
    value: '892',
    icon: 'fas fa-graduation-cap',
    color: '#f59e0b',
    trend: 'up',
    trendIcon: 'fas fa-arrow-up',
    change: '+15%',
    progress: '85%'
  },
  {
    label: '월간 수익',
    value: '₩12.5M',
    icon: 'fas fa-won-sign',
    color: '#8b5cf6',
    trend: 'down',
    trendIcon: 'fas fa-arrow-down',
    change: '-3%',
    progress: '45%'
  }
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
    action: '사용자 정보를 수정했습니다',
    time: '15분 전',
    avatar: 'https://randomuser.me/api/portraits/women/34.jpg',
    status: 'info'
  },
  {
    id: 3,
    user: '박민수',
    action: '강의를 삭제했습니다',
    time: '1시간 전',
    avatar: 'https://randomuser.me/api/portraits/men/35.jpg',
    status: 'warning'
  }
])

// 빠른 작업
const quickActions = ref([
  { name: '새 사용자 추가', icon: 'fas fa-user-plus', handler: () => console.log('사용자 추가') },
  { name: '강의 생성', icon: 'fas fa-plus-circle', handler: () => console.log('강의 생성') },
  { name: '데이터 내보내기', icon: 'fas fa-download', handler: () => console.log('데이터 내보내기') },
  { name: '시스템 설정', icon: 'fas fa-cog', handler: () => console.log('시스템 설정') }
])

// 사용자 목록
const users = ref([
  {
    id: 1,
    name: '홍길동',
    email: 'hong@example.com',
    role: '관리자',
    joinedAt: '2024-01-15',
    status: 'active',
    statusText: '활성',
    avatar: 'https://randomuser.me/api/portraits/men/32.jpg'
  },
  {
    id: 2,
    name: '김철수',
    email: 'kim@example.com',
    role: '강사',
    joinedAt: '2024-02-20',
    status: 'active',
    statusText: '활성',
    avatar: 'https://randomuser.me/api/portraits/men/33.jpg'
  },
  {
    id: 3,
    name: '이영희',
    email: 'lee@example.com',
    role: '학생',
    joinedAt: '2024-03-10',
    status: 'inactive',
    statusText: '비활성',
    avatar: 'https://randomuser.me/api/portraits/women/34.jpg'
  }
])
</script>

<style scoped>
/* ============================================
   MODERN CARD LAYOUT STYLES
   ============================================ */

.modern-layout {
  min-height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  background-attachment: fixed;
}

/* ============================================
   NAVIGATION STYLES
   ============================================ */
.modern-nav {
  background: rgba(255, 255, 255, 0.95);
  backdrop-filter: blur(20px);
  border-bottom: 1px solid rgba(255, 255, 255, 0.2);
  position: sticky;
  top: 0;
  z-index: 1000;
}

.nav-container {
  max-width: 1400px;
  margin: 0 auto;
  padding: 0 20px;
  display: flex;
  align-items: center;
  height: 70px;
  gap: 30px;
}

.nav-brand {
  display: flex;
  align-items: center;
  gap: 12px;
}

.brand-icon {
  width: 40px;
  height: 40px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border-radius: 12px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  font-size: 20px;
}

.brand-title {
  font-size: 24px;
  font-weight: 700;
  color: #1f2937;
  margin: 0;
}

.nav-center {
  flex: 1;
  max-width: 500px;
}

.search-box {
  position: relative;
  width: 100%;
}

.search-box i {
  position: absolute;
  left: 16px;
  top: 50%;
  transform: translateY(-50%);
  color: #9ca3af;
  font-size: 16px;
}

.search-input {
  width: 100%;
  padding: 12px 16px 12px 48px;
  border: 2px solid #e5e7eb;
  border-radius: 12px;
  font-size: 14px;
  background: #f9fafb;
  transition: all 0.3s ease;
}

.search-input:focus {
  outline: none;
  border-color: #667eea;
  background: white;
  box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.1);
}

.nav-actions {
  display: flex;
  align-items: center;
  gap: 20px;
}

.nav-btn {
  position: relative;
  width: 44px;
  height: 44px;
  border: none;
  background: #f3f4f6;
  border-radius: 12px;
  color: #6b7280;
  cursor: pointer;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  justify-content: center;
}

.nav-btn:hover {
  background: #e5e7eb;
  color: #374151;
}

.notification-dot {
  position: absolute;
  top: 8px;
  right: 8px;
  width: 8px;
  height: 8px;
  background: #ef4444;
  border-radius: 50%;
}

.user-dropdown {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 8px 16px;
  background: #f9fafb;
  border-radius: 12px;
  cursor: pointer;
  transition: all 0.3s ease;
}

.user-dropdown:hover {
  background: #f3f4f6;
}

.user-avatar {
  width: 32px;
  height: 32px;
  border-radius: 50%;
  object-fit: cover;
}

.user-name {
  font-weight: 500;
  color: #374151;
}

/* ============================================
   MAIN LAYOUT STYLES
   ============================================ */
.modern-main {
  display: flex;
  max-width: 1400px;
  margin: 0 auto;
  padding: 20px;
  gap: 20px;
  min-height: calc(100vh - 70px);
}

.modern-sidebar {
  width: 280px;
  background: rgba(255, 255, 255, 0.95);
  backdrop-filter: blur(20px);
  border-radius: 20px;
  padding: 20px 0;
  transition: all 0.3s ease;
  height: fit-content;
  position: sticky;
  top: 90px;
}

.modern-sidebar.collapsed {
  width: 80px;
}

.sidebar-header {
  padding: 0 20px 20px;
  border-bottom: 1px solid #e5e7eb;
  margin-bottom: 20px;
}

.sidebar-toggle {
  width: 40px;
  height: 40px;
  border: none;
  background: #f3f4f6;
  border-radius: 10px;
  color: #6b7280;
  cursor: pointer;
  transition: all 0.3s ease;
}

.sidebar-toggle:hover {
  background: #e5e7eb;
  color: #374151;
}

.sidebar-nav {
  padding: 0 10px;
}

.nav-item {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 12px 16px;
  margin-bottom: 4px;
  border-radius: 12px;
  cursor: pointer;
  transition: all 0.3s ease;
  position: relative;
}

.nav-item:hover {
  background: #f3f4f6;
}

.nav-item.active {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
}

.nav-item.active .nav-icon {
  color: white;
}

.nav-icon {
  width: 20px;
  height: 20px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #6b7280;
  font-size: 16px;
}

.nav-text {
  font-weight: 500;
  color: #374151;
  transition: opacity 0.3s ease;
}

.nav-badge {
  background: #ef4444;
  color: white;
  font-size: 10px;
  padding: 2px 6px;
  border-radius: 10px;
  margin-left: auto;
}

.content-area {
  flex: 1;
  background: rgba(255, 255, 255, 0.95);
  backdrop-filter: blur(20px);
  border-radius: 20px;
  overflow: hidden;
}

/* ============================================
   PAGE HEADER STYLES
   ============================================ */
.page-header {
  padding: 30px;
  border-bottom: 1px solid #e5e7eb;
  background: linear-gradient(135deg, #f8fafc 0%, #f1f5f9 100%);
}

.header-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.page-title h2 {
  font-size: 28px;
  font-weight: 700;
  color: #1f2937;
  margin: 0 0 8px 0;
}

.page-subtitle {
  color: #6b7280;
  margin: 0;
  font-size: 16px;
}

.header-actions {
  display: flex;
  gap: 12px;
}

.btn-outline {
  background: transparent;
  border: 2px solid #d1d5db;
  color: #374151;
  padding: 10px 20px;
  border-radius: 10px;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  gap: 8px;
}

.btn-outline:hover {
  background: #f9fafb;
  border-color: #9ca3af;
}

/* ============================================
   DASHBOARD STYLES
   ============================================ */
.dashboard-content {
  padding: 30px;
}

.stats-section {
  margin-bottom: 40px;
}

.stats-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
  gap: 20px;
}

.modern-card {
  background: white;
  border-radius: 16px;
  box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1);
  overflow: hidden;
  transition: all 0.3s ease;
}

.modern-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 10px 25px -3px rgba(0, 0, 0, 0.1);
}

.stat-card {
  padding: 24px;
}

.stat-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 16px;
}

.stat-icon {
  width: 48px;
  height: 48px;
  border-radius: 12px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  font-size: 20px;
}

.stat-trend {
  display: flex;
  align-items: center;
  gap: 4px;
  font-size: 12px;
  font-weight: 600;
}

.stat-trend.up {
  color: #10b981;
}

.stat-trend.down {
  color: #ef4444;
}

.stat-content {
  margin-bottom: 16px;
}

.stat-value {
  font-size: 32px;
  font-weight: 700;
  color: #1f2937;
  margin-bottom: 4px;
}

.stat-label {
  color: #6b7280;
  font-size: 14px;
}

.stat-chart {
  height: 4px;
  background: #f3f4f6;
  border-radius: 2px;
  overflow: hidden;
}

.mini-chart {
  height: 100%;
  background: linear-gradient(90deg, #667eea 0%, #764ba2 100%);
  width: var(--progress);
  transition: width 0.3s ease;
}

/* ============================================
   ACTIVITY SECTION STYLES
   ============================================ */
.activity-section {
  margin-top: 40px;
}

.section-grid {
  display: grid;
  grid-template-columns: 2fr 1fr;
  gap: 20px;
}

.activity-card, .quick-actions-card {
  background: white;
  border-radius: 16px;
  box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1);
}

.card-header {
  padding: 20px 24px;
  border-bottom: 1px solid #e5e7eb;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.card-header h3 {
  font-size: 18px;
  font-weight: 600;
  color: #1f2937;
  margin: 0;
}

.card-body {
  padding: 24px;
}

.activity-list {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.activity-item {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 12px;
  border-radius: 12px;
  transition: background-color 0.3s ease;
}

.activity-item:hover {
  background: #f9fafb;
}

.activity-avatar {
  width: 40px;
  height: 40px;
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
  color: #374151;
  font-size: 14px;
  margin-bottom: 4px;
}

.activity-time {
  color: #9ca3af;
  font-size: 12px;
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

.activity-status.warning {
  background: #f59e0b;
}

.quick-actions {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 12px;
}

.quick-action-btn {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 8px;
  padding: 20px 12px;
  background: #f9fafb;
  border: none;
  border-radius: 12px;
  cursor: pointer;
  transition: all 0.3s ease;
}

.quick-action-btn:hover {
  background: #f3f4f6;
  transform: translateY(-1px);
}

.quick-action-btn i {
  font-size: 20px;
  color: #667eea;
}

.quick-action-btn span {
  font-size: 12px;
  font-weight: 500;
  color: #374151;
}

/* ============================================
   USERS CONTENT STYLES
   ============================================ */
.users-content {
  padding: 30px;
}

.content-card {
  background: white;
  border-radius: 16px;
  box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1);
  overflow: hidden;
}

.card-header {
  padding: 24px 30px;
  border-bottom: 1px solid #e5e7eb;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.header-left h3 {
  font-size: 20px;
  font-weight: 600;
  color: #1f2937;
  margin: 0 0 4px 0;
}

.user-count {
  color: #6b7280;
  font-size: 14px;
}

.header-right {
  display: flex;
  align-items: center;
  gap: 16px;
}

.filter-group {
  position: relative;
}

.form-select {
  padding: 8px 12px;
  border: 1px solid #d1d5db;
  border-radius: 8px;
  background: white;
  font-size: 14px;
  color: #374151;
  cursor: pointer;
}

.table-responsive {
  overflow-x: auto;
}

.modern-table {
  width: 100%;
  border-collapse: collapse;
}

.modern-table th {
  background: #f9fafb;
  padding: 16px 20px;
  text-align: left;
  font-weight: 600;
  color: #374151;
  font-size: 14px;
  border-bottom: 1px solid #e5e7eb;
}

.modern-table td {
  padding: 16px 20px;
  border-bottom: 1px solid #f3f4f6;
  color: #374151;
}

.table-row:hover {
  background: #f9fafb;
}

.checkbox-col {
  width: 40px;
}

.form-checkbox {
  width: 16px;
  height: 16px;
  accent-color: #667eea;
}

.user-info {
  display: flex;
  align-items: center;
  gap: 12px;
}

.user-avatar-small {
  width: 32px;
  height: 32px;
  border-radius: 50%;
  object-fit: cover;
}

.user-details .user-name {
  font-weight: 500;
  color: #1f2937;
  margin-bottom: 2px;
}

.user-role {
  font-size: 12px;
  color: #6b7280;
}

.status-badge {
  padding: 4px 8px;
  border-radius: 6px;
  font-size: 12px;
  font-weight: 500;
}

.status-badge.active {
  background: #dcfce7;
  color: #166534;
}

.status-badge.inactive {
  background: #fef2f2;
  color: #dc2626;
}

.action-menu {
  position: relative;
}

.action-btn {
  width: 32px;
  height: 32px;
  border: none;
  background: #f3f4f6;
  border-radius: 6px;
  color: #6b7280;
  cursor: pointer;
  transition: all 0.3s ease;
}

.action-btn:hover {
  background: #e5e7eb;
  color: #374151;
}

/* ============================================
   RESPONSIVE DESIGN
   ============================================ */
@media (max-width: 1024px) {
  .section-grid {
    grid-template-columns: 1fr;
  }
  
  .stats-grid {
    grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  }
}

@media (max-width: 768px) {
  .modern-main {
    flex-direction: column;
    padding: 10px;
  }
  
  .modern-sidebar {
    width: 100%;
    position: static;
    order: 2;
  }
  
  .content-area {
    order: 1;
  }
  
  .nav-container {
    flex-direction: column;
    height: auto;
    padding: 15px 20px;
    gap: 15px;
  }
  
  .nav-center {
    order: 3;
    max-width: 100%;
  }
  
  .header-content {
    flex-direction: column;
    align-items: flex-start;
    gap: 20px;
  }
  
  .header-actions {
    width: 100%;
    justify-content: flex-end;
  }
}
</style>

