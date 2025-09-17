<template>
  <div class="minimal-layout">
    <!-- 미니멀 사이드바 -->
    <aside class="minimal-sidebar" :class="{ 'expanded': sidebarExpanded }">
      <div class="sidebar-header">
        <div class="logo" v-show="sidebarExpanded">
          <i class="fas fa-graduation-cap"></i>
          <span>Learning</span>
        </div>
        <button class="sidebar-toggle" @click="sidebarExpanded = !sidebarExpanded">
          <i class="fas fa-bars"></i>
        </button>
      </div>
      
      <nav class="sidebar-nav">
        <div v-for="menu in menus" :key="menu.name" 
             class="nav-item" 
             :class="{ 'active': selectedMenu.name === menu.name }"
             @click="selectMenu(menu)"
             :title="menu.title">
          <i :class="menu.icon"></i>
          <span v-show="sidebarExpanded">{{ menu.title }}</span>
          <div class="nav-indicator" v-if="selectedMenu.name === menu.name"></div>
        </div>
      </nav>
      
      <div class="sidebar-footer" v-show="sidebarExpanded">
        <div class="user-profile">
          <img :src="user.avatar" :alt="user.name" class="user-avatar">
          <div class="user-info">
            <div class="user-name">{{ user.name }}</div>
            <div class="user-role">{{ user.role }}</div>
          </div>
        </div>
      </div>
    </aside>

    <!-- 메인 콘텐츠 -->
    <main class="minimal-main">
      <!-- 상단 헤더 -->
      <header class="minimal-header">
        <div class="header-left">
          <h1 class="page-title">{{ selectedMenu.title }}</h1>
          <p class="page-description">{{ selectedMenu.description }}</p>
        </div>
        
        <div class="header-right">
          <div class="search-container">
            <i class="fas fa-search"></i>
            <input type="text" placeholder="검색..." class="search-input">
          </div>
          
          <div class="header-actions">
            <button class="action-btn notification-btn">
              <i class="fas fa-bell"></i>
              <span class="notification-count">3</span>
            </button>
            
            <div class="user-menu">
              <img :src="user.avatar" :alt="user.name" class="user-avatar-small">
              <i class="fas fa-chevron-down"></i>
            </div>
          </div>
        </div>
      </header>

      <!-- 콘텐츠 영역 -->
      <div class="content-wrapper">
        <!-- 대시보드 콘텐츠 -->
        <div v-if="selectedMenu.name === 'dashboard'" class="dashboard-wrapper">
          <!-- 통계 카드 -->
          <div class="stats-container">
            <div v-for="stat in dashboardStats" :key="stat.label" class="stat-card">
              <div class="stat-icon">
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

          <!-- 차트 섹션 -->
          <div class="charts-section">
            <div class="chart-card">
              <div class="chart-header">
                <h3>사용자 증가 추이</h3>
                <div class="chart-actions">
                  <button class="btn btn-sm">7일</button>
                  <button class="btn btn-sm btn-primary">30일</button>
                  <button class="btn btn-sm">90일</button>
                </div>
              </div>
              <div class="chart-content">
                <div class="chart-placeholder">
                  <i class="fas fa-chart-line"></i>
                  <p>차트 데이터</p>
                </div>
              </div>
            </div>
            
            <div class="chart-card">
              <div class="chart-header">
                <h3>강의별 수강률</h3>
              </div>
              <div class="chart-content">
                <div class="progress-list">
                  <div v-for="course in courseProgress" :key="course.name" class="progress-item">
                    <div class="progress-info">
                      <span class="course-name">{{ course.name }}</span>
                      <span class="progress-percent">{{ course.percent }}%</span>
                    </div>
                    <div class="progress-bar">
                      <div class="progress-fill" :style="{ width: course.percent + '%' }"></div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <!-- 최근 활동 -->
          <div class="activity-section">
            <div class="section-header">
              <h3>최근 활동</h3>
              <button class="btn btn-outline">전체 보기</button>
            </div>
            <div class="activity-list">
              <div v-for="activity in recentActivities" :key="activity.id" class="activity-item">
                <div class="activity-icon">
                  <i :class="activity.icon"></i>
                </div>
                <div class="activity-content">
                  <div class="activity-text">{{ activity.text }}</div>
                  <div class="activity-time">{{ activity.time }}</div>
                </div>
                <div class="activity-status" :class="activity.status"></div>
              </div>
            </div>
          </div>
        </div>

        <!-- 사용자 관리 콘텐츠 -->
        <div v-if="selectedMenu.name === 'users'" class="users-wrapper">
          <div class="section-header">
            <div class="header-info">
              <h2>사용자 관리</h2>
              <p>{{ users.length }}명의 사용자가 등록되어 있습니다</p>
            </div>
            <div class="header-actions">
              <div class="filter-group">
                <select class="form-select">
                  <option>모든 사용자</option>
                  <option>활성 사용자</option>
                  <option>비활성 사용자</option>
                </select>
              </div>
              <button class="btn btn-primary">
                <i class="fas fa-plus"></i>
                사용자 추가
              </button>
            </div>
          </div>

          <div class="users-grid">
            <div v-for="user in users" :key="user.id" class="user-card">
              <div class="user-card-header">
                <img :src="user.avatar" :alt="user.name" class="user-avatar-large">
                <div class="user-status" :class="user.status"></div>
              </div>
              <div class="user-card-body">
                <h4 class="user-name">{{ user.name }}</h4>
                <p class="user-email">{{ user.email }}</p>
                <div class="user-role">{{ user.role }}</div>
                <div class="user-joined">가입일: {{ user.joinedAt }}</div>
              </div>
              <div class="user-card-footer">
                <button class="btn btn-sm btn-outline">수정</button>
                <button class="btn btn-sm btn-danger">삭제</button>
              </div>
            </div>
          </div>
        </div>

        <!-- 강의 관리 콘텐츠 -->
        <div v-if="selectedMenu.name === 'courses'" class="courses-wrapper">
          <div class="section-header">
            <div class="header-info">
              <h2>강의 관리</h2>
              <p>{{ courses.length }}개의 강의가 등록되어 있습니다</p>
            </div>
            <div class="header-actions">
              <button class="btn btn-primary">
                <i class="fas fa-plus"></i>
                강의 추가
              </button>
            </div>
          </div>

          <div class="courses-table">
            <div class="table-header">
              <div class="table-cell">강의명</div>
              <div class="table-cell">강사</div>
              <div class="table-cell">수강생</div>
              <div class="table-cell">상태</div>
              <div class="table-cell">작업</div>
            </div>
            <div v-for="course in courses" :key="course.id" class="table-row">
              <div class="table-cell">
                <div class="course-info">
                  <div class="course-title">{{ course.title }}</div>
                  <div class="course-description">{{ course.description }}</div>
                </div>
              </div>
              <div class="table-cell">{{ course.instructor }}</div>
              <div class="table-cell">{{ course.students }}명</div>
              <div class="table-cell">
                <span class="status-badge" :class="course.status">{{ course.statusText }}</span>
              </div>
              <div class="table-cell">
                <div class="action-buttons">
                  <button class="btn btn-sm btn-outline">수정</button>
                  <button class="btn btn-sm btn-danger">삭제</button>
                </div>
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
const sidebarExpanded = ref(true)

// 사용자 정보
const user = ref({
  id: 1,
  name: '홍길동',
  email: 'hong@example.com',
  role: '관리자',
  avatar: 'https://randomuser.me/api/portraits/men/32.jpg'
})

// 메뉴
const menus = ref([
  { 
    name: 'dashboard', 
    title: '대시보드', 
    icon: 'fas fa-tachometer-alt',
    description: '전체 현황을 확인하세요'
  },
  { 
    name: 'users', 
    title: '사용자', 
    icon: 'fas fa-users',
    description: '사용자를 관리하세요'
  },
  { 
    name: 'courses', 
    title: '강의', 
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

// 선택된 메뉴
const selectedMenu = ref(menus.value[0])
const selectMenu = (menu) => (selectedMenu.value = menu)

// 대시보드 통계
const dashboardStats = ref([
  {
    label: '총 사용자',
    value: '1,234',
    icon: 'fas fa-users',
    trend: 'up',
    trendIcon: 'fas fa-arrow-up',
    change: '+12%'
  },
  {
    label: '활성 강의',
    value: '45',
    icon: 'fas fa-book',
    trend: 'up',
    trendIcon: 'fas fa-arrow-up',
    change: '+8%'
  },
  {
    label: '완료된 수강',
    value: '892',
    icon: 'fas fa-graduation-cap',
    trend: 'up',
    trendIcon: 'fas fa-arrow-up',
    change: '+15%'
  },
  {
    label: '월간 수익',
    value: '₩12.5M',
    icon: 'fas fa-won-sign',
    trend: 'down',
    trendIcon: 'fas fa-arrow-down',
    change: '-3%'
  }
])

// 강의 진행률
const courseProgress = ref([
  { name: 'Vue.js 기초', percent: 85 },
  { name: 'React 심화', percent: 72 },
  { name: 'Node.js 실무', percent: 90 },
  { name: 'Python 데이터분석', percent: 68 }
])

// 최근 활동
const recentActivities = ref([
  {
    id: 1,
    text: '새로운 사용자가 가입했습니다',
    time: '5분 전',
    icon: 'fas fa-user-plus',
    status: 'success'
  },
  {
    id: 2,
    text: '강의가 업데이트되었습니다',
    time: '1시간 전',
    icon: 'fas fa-book',
    status: 'info'
  },
  {
    id: 3,
    text: '시스템 백업이 완료되었습니다',
    time: '3시간 전',
    icon: 'fas fa-database',
    status: 'success'
  }
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
    avatar: 'https://randomuser.me/api/portraits/men/32.jpg'
  },
  {
    id: 2,
    name: '김철수',
    email: 'kim@example.com',
    role: '강사',
    joinedAt: '2024-02-20',
    status: 'active',
    avatar: 'https://randomuser.me/api/portraits/men/33.jpg'
  },
  {
    id: 3,
    name: '이영희',
    email: 'lee@example.com',
    role: '학생',
    joinedAt: '2024-03-10',
    status: 'inactive',
    avatar: 'https://randomuser.me/api/portraits/women/34.jpg'
  },
  {
    id: 4,
    name: '박민수',
    email: 'park@example.com',
    role: '학생',
    joinedAt: '2024-03-15',
    status: 'active',
    avatar: 'https://randomuser.me/api/portraits/men/35.jpg'
  }
])

// 강의 목록
const courses = ref([
  {
    id: 1,
    title: 'Vue.js 완벽 가이드',
    description: 'Vue.js의 모든 것을 배우는 완벽한 강의',
    instructor: '홍길동',
    students: 156,
    status: 'active',
    statusText: '진행중'
  },
  {
    id: 2,
    title: 'React 실무 프로젝트',
    description: '실제 프로젝트로 배우는 React',
    instructor: '김철수',
    students: 89,
    status: 'active',
    statusText: '진행중'
  },
  {
    id: 3,
    title: 'Node.js 백엔드 개발',
    description: '서버 사이드 개발의 모든 것',
    instructor: '이영희',
    students: 234,
    status: 'completed',
    statusText: '완료'
  }
])
</script>

<style scoped>
/* ============================================
   MINIMAL SIDEBAR LAYOUT STYLES
   ============================================ */

.minimal-layout {
  display: flex;
  min-height: 100vh;
  background: #f8fafc;
}

/* ============================================
   SIDEBAR STYLES
   ============================================ */
.minimal-sidebar {
  width: 80px;
  background: white;
  border-right: 1px solid #e2e8f0;
  transition: all 0.3s ease;
  display: flex;
  flex-direction: column;
  position: fixed;
  height: 100vh;
  z-index: 1000;
}

.minimal-sidebar.expanded {
  width: 280px;
}

.sidebar-header {
  padding: 20px;
  border-bottom: 1px solid #e2e8f0;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.logo {
  display: flex;
  align-items: center;
  gap: 12px;
  font-size: 20px;
  font-weight: 700;
  color: #1e293b;
}

.logo i {
  color: #3b82f6;
  font-size: 24px;
}

.sidebar-toggle {
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

.sidebar-toggle:hover {
  background: #e2e8f0;
  color: #475569;
}

.sidebar-nav {
  flex: 1;
  padding: 20px 0;
  overflow-y: auto;
}

.nav-item {
  position: relative;
  display: flex;
  align-items: center;
  gap: 16px;
  padding: 12px 20px;
  margin: 4px 12px;
  border-radius: 8px;
  cursor: pointer;
  transition: all 0.3s ease;
  color: #64748b;
}

.nav-item:hover {
  background: #f1f5f9;
  color: #475569;
}

.nav-item.active {
  background: #dbeafe;
  color: #3b82f6;
}

.nav-item i {
  font-size: 18px;
  width: 20px;
  text-align: center;
}

.nav-item span {
  font-weight: 500;
  white-space: nowrap;
  opacity: 0;
  transition: opacity 0.3s ease;
}

.minimal-sidebar.expanded .nav-item span {
  opacity: 1;
}

.nav-indicator {
  position: absolute;
  right: 8px;
  top: 50%;
  transform: translateY(-50%);
  width: 4px;
  height: 20px;
  background: #3b82f6;
  border-radius: 2px;
}

.sidebar-footer {
  padding: 20px;
  border-top: 1px solid #e2e8f0;
}

.user-profile {
  display: flex;
  align-items: center;
  gap: 12px;
}

.user-avatar {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  object-fit: cover;
}

.user-info {
  opacity: 0;
  transition: opacity 0.3s ease;
}

.minimal-sidebar.expanded .user-info {
  opacity: 1;
}

.user-name {
  font-weight: 600;
  color: #1e293b;
  font-size: 14px;
  margin-bottom: 2px;
}

.user-role {
  font-size: 12px;
  color: #64748b;
}

/* ============================================
   MAIN CONTENT STYLES
   ============================================ */
.minimal-main {
  flex: 1;
  margin-left: 80px;
  transition: margin-left 0.3s ease;
}

.minimal-sidebar.expanded + .minimal-main {
  margin-left: 280px;
}

.minimal-header {
  background: white;
  border-bottom: 1px solid #e2e8f0;
  padding: 24px 32px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.header-left .page-title {
  font-size: 28px;
  font-weight: 700;
  color: #1e293b;
  margin: 0 0 4px 0;
}

.page-description {
  color: #64748b;
  margin: 0;
  font-size: 16px;
}

.header-right {
  display: flex;
  align-items: center;
  gap: 24px;
}

.search-container {
  position: relative;
  display: flex;
  align-items: center;
}

.search-container i {
  position: absolute;
  left: 16px;
  color: #94a3b8;
  font-size: 16px;
}

.search-input {
  padding: 10px 16px 10px 44px;
  border: 1px solid #d1d5db;
  border-radius: 8px;
  font-size: 14px;
  width: 300px;
  background: #f8fafc;
  transition: all 0.3s ease;
}

.search-input:focus {
  outline: none;
  border-color: #3b82f6;
  background: white;
  box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.1);
}

.header-actions {
  display: flex;
  align-items: center;
  gap: 16px;
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

.notification-count {
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

.user-menu {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 8px 12px;
  background: #f1f5f9;
  border-radius: 8px;
  cursor: pointer;
  transition: all 0.3s ease;
}

.user-menu:hover {
  background: #e2e8f0;
}

.user-avatar-small {
  width: 32px;
  height: 32px;
  border-radius: 50%;
  object-fit: cover;
}

/* ============================================
   CONTENT WRAPPER STYLES
   ============================================ */
.content-wrapper {
  padding: 32px;
}

/* ============================================
   DASHBOARD STYLES
   ============================================ */
.stats-container {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 24px;
  margin-bottom: 32px;
}

.stat-card {
  background: white;
  padding: 24px;
  border-radius: 12px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
  display: flex;
  align-items: center;
  gap: 16px;
  transition: all 0.3s ease;
}

.stat-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
}

.stat-icon {
  width: 48px;
  height: 48px;
  background: linear-gradient(135deg, #3b82f6 0%, #1d4ed8 100%);
  border-radius: 12px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  font-size: 20px;
}

.stat-content {
  flex: 1;
}

.stat-value {
  font-size: 24px;
  font-weight: 700;
  color: #1e293b;
  margin-bottom: 4px;
}

.stat-label {
  color: #64748b;
  font-size: 14px;
  margin-bottom: 8px;
}

.stat-change {
  display: flex;
  align-items: center;
  gap: 4px;
  font-size: 12px;
  font-weight: 600;
}

.stat-change.up {
  color: #059669;
}

.stat-change.down {
  color: #dc2626;
}

.charts-section {
  display: grid;
  grid-template-columns: 2fr 1fr;
  gap: 24px;
  margin-bottom: 32px;
}

.chart-card {
  background: white;
  border-radius: 12px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
  overflow: hidden;
}

.chart-header {
  padding: 20px 24px;
  border-bottom: 1px solid #e2e8f0;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.chart-header h3 {
  font-size: 18px;
  font-weight: 600;
  color: #1e293b;
  margin: 0;
}

.chart-actions {
  display: flex;
  gap: 8px;
}

.chart-content {
  padding: 24px;
}

.chart-placeholder {
  height: 200px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  color: #94a3b8;
  background: #f8fafc;
  border-radius: 8px;
}

.chart-placeholder i {
  font-size: 48px;
  margin-bottom: 16px;
}

.progress-list {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.progress-item {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.progress-info {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.course-name {
  font-weight: 500;
  color: #1e293b;
}

.progress-percent {
  font-weight: 600;
  color: #3b82f6;
}

.progress-bar {
  height: 6px;
  background: #e2e8f0;
  border-radius: 3px;
  overflow: hidden;
}

.progress-fill {
  height: 100%;
  background: linear-gradient(90deg, #3b82f6 0%, #1d4ed8 100%);
  border-radius: 3px;
  transition: width 0.3s ease;
}

.activity-section {
  background: white;
  border-radius: 12px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
  overflow: hidden;
}

.section-header {
  padding: 20px 24px;
  border-bottom: 1px solid #e2e8f0;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.section-header h3 {
  font-size: 18px;
  font-weight: 600;
  color: #1e293b;
  margin: 0;
}

.activity-list {
  padding: 24px;
}

.activity-item {
  display: flex;
  align-items: center;
  gap: 16px;
  padding: 16px 0;
  border-bottom: 1px solid #f1f5f9;
}

.activity-item:last-child {
  border-bottom: none;
}

.activity-icon {
  width: 40px;
  height: 40px;
  background: #f1f5f9;
  border-radius: 8px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #64748b;
}

.activity-content {
  flex: 1;
}

.activity-text {
  color: #1e293b;
  font-weight: 500;
  margin-bottom: 4px;
}

.activity-time {
  color: #94a3b8;
  font-size: 14px;
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
   USERS STYLES
   ============================================ */
.users-wrapper .section-header {
  background: white;
  border-radius: 12px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
  margin-bottom: 24px;
}

.header-info h2 {
  font-size: 24px;
  font-weight: 700;
  color: #1e293b;
  margin: 0 0 4px 0;
}

.header-info p {
  color: #64748b;
  margin: 0;
}

.header-actions {
  display: flex;
  align-items: center;
  gap: 16px;
}

.users-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 24px;
}

.user-card {
  background: white;
  border-radius: 12px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
  overflow: hidden;
  transition: all 0.3s ease;
}

.user-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
}

.user-card-header {
  position: relative;
  padding: 24px 24px 0;
  text-align: center;
}

.user-avatar-large {
  width: 80px;
  height: 80px;
  border-radius: 50%;
  object-fit: cover;
  margin: 0 auto 16px;
}

.user-status {
  position: absolute;
  top: 24px;
  right: 24px;
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
  padding: 0 24px 24px;
  text-align: center;
}

.user-name {
  font-size: 18px;
  font-weight: 600;
  color: #1e293b;
  margin: 0 0 8px 0;
}

.user-email {
  color: #64748b;
  margin: 0 0 8px 0;
}

.user-role {
  background: #dbeafe;
  color: #1e40af;
  padding: 4px 12px;
  border-radius: 20px;
  font-size: 12px;
  font-weight: 500;
  display: inline-block;
  margin-bottom: 8px;
}

.user-joined {
  color: #94a3b8;
  font-size: 14px;
}

.user-card-footer {
  padding: 16px 24px;
  background: #f8fafc;
  display: flex;
  gap: 8px;
  justify-content: center;
}

/* ============================================
   COURSES STYLES
   ============================================ */
.courses-wrapper .section-header {
  background: white;
  border-radius: 12px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
  margin-bottom: 24px;
}

.courses-table {
  background: white;
  border-radius: 12px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
  overflow: hidden;
}

.table-header {
  display: grid;
  grid-template-columns: 2fr 1fr 1fr 1fr 1fr;
  background: #f8fafc;
  border-bottom: 1px solid #e2e8f0;
}

.table-row {
  display: grid;
  grid-template-columns: 2fr 1fr 1fr 1fr 1fr;
  border-bottom: 1px solid #f1f5f9;
  transition: background-color 0.3s ease;
}

.table-row:hover {
  background: #f8fafc;
}

.table-cell {
  padding: 16px 20px;
  display: flex;
  align-items: center;
  color: #1e293b;
}

.table-header .table-cell {
  font-weight: 600;
  color: #475569;
}

.course-info {
  display: flex;
  flex-direction: column;
  gap: 4px;
}

.course-title {
  font-weight: 500;
  color: #1e293b;
}

.course-description {
  font-size: 14px;
  color: #64748b;
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

.status-badge.completed {
  background: #dbeafe;
  color: #1e40af;
}

.action-buttons {
  display: flex;
  gap: 8px;
}

/* ============================================
   RESPONSIVE DESIGN
   ============================================ */
@media (max-width: 1024px) {
  .charts-section {
    grid-template-columns: 1fr;
  }
  
  .stats-container {
    grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  }
}

@media (max-width: 768px) {
  .minimal-sidebar {
    transform: translateX(-100%);
  }
  
  .minimal-sidebar.expanded {
    transform: translateX(0);
  }
  
  .minimal-main {
    margin-left: 0;
  }
  
  .minimal-header {
    flex-direction: column;
    align-items: flex-start;
    gap: 16px;
  }
  
  .header-right {
    width: 100%;
    justify-content: space-between;
  }
  
  .search-input {
    width: 200px;
  }
  
  .content-wrapper {
    padding: 16px;
  }
  
  .users-grid {
    grid-template-columns: 1fr;
  }
  
  .table-header,
  .table-row {
    grid-template-columns: 1fr;
    gap: 8px;
  }
  
  .table-cell {
    padding: 8px 0;
  }
}
</style>

