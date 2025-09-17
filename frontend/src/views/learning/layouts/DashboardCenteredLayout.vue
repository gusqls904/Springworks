<template>
  <div class="dashboard-layout">
    <!-- 상단 헤더 -->
    <header class="dashboard-header">
      <div class="header-left">
        <div class="logo">
          <i class="fas fa-chart-pie"></i>
          <span>Analytics Hub</span>
        </div>
        <nav class="main-nav">
          <a v-for="menu in mainMenus" :key="menu.name" 
             :class="['nav-link', { 'active': selectedMainMenu === menu.name }]"
             @click="selectedMainMenu = menu.name">
            <i :class="menu.icon"></i>
            <span>{{ menu.title }}</span>
          </a>
        </nav>
      </div>
      
      <div class="header-right">
        <div class="search-container">
          <i class="fas fa-search"></i>
          <input type="text" placeholder="검색..." class="search-input">
        </div>
        <div class="header-actions">
          <button class="action-btn notification-btn">
            <i class="fas fa-bell"></i>
            <span class="notification-count">5</span>
          </button>
          <div class="user-profile">
            <img :src="user.avatar" :alt="user.name" class="user-avatar">
            <div class="user-info">
              <div class="user-name">{{ user.name }}</div>
              <div class="user-role">{{ user.role }}</div>
            </div>
          </div>
        </div>
      </div>
    </header>

    <!-- 메인 콘텐츠 -->
    <main class="dashboard-main">
      <!-- 대시보드 위젯들 -->
      <div class="dashboard-grid">
        <!-- KPI 카드들 -->
        <div class="kpi-section">
          <div v-for="kpi in kpiData" :key="kpi.id" class="kpi-card">
            <div class="kpi-header">
              <div class="kpi-icon" :style="{ background: kpi.color }">
                <i :class="kpi.icon"></i>
              </div>
              <div class="kpi-trend" :class="kpi.trend">
                <i :class="kpi.trendIcon"></i>
                <span>{{ kpi.change }}</span>
              </div>
            </div>
            <div class="kpi-content">
              <div class="kpi-value">{{ kpi.value }}</div>
              <div class="kpi-label">{{ kpi.label }}</div>
              <div class="kpi-description">{{ kpi.description }}</div>
            </div>
            <div class="kpi-chart">
              <div class="mini-chart" :style="{ '--progress': kpi.progress }">
                <div class="chart-line" v-for="(point, index) in kpi.chartPoints" :key="index"
                     :style="{ height: point + '%', '--delay': index * 0.1 + 's' }"></div>
              </div>
            </div>
          </div>
        </div>

        <!-- 메인 차트 -->
        <div class="main-chart-section">
          <div class="chart-card large">
            <div class="chart-header">
              <div class="chart-title">
                <h3>사용자 활동 분석</h3>
                <p>최근 30일간의 사용자 활동 패턴</p>
              </div>
              <div class="chart-controls">
                <div class="time-filter">
                  <button v-for="period in timePeriods" :key="period.value"
                          :class="['time-btn', { 'active': selectedPeriod === period.value }]"
                          @click="selectedPeriod = period.value">
                    {{ period.label }}
                  </button>
                </div>
                <div class="chart-actions">
                  <button class="btn btn-outline">
                    <i class="fas fa-download"></i>
                    내보내기
                  </button>
                  <button class="btn btn-primary">
                    <i class="fas fa-expand"></i>
                    전체화면
                  </button>
                </div>
              </div>
            </div>
            <div class="chart-body">
              <div class="chart-container">
                <div class="chart-placeholder">
                  <i class="fas fa-chart-line"></i>
                  <p>사용자 활동 차트</p>
                  <div class="chart-legend">
                    <div class="legend-item">
                      <div class="legend-color" style="background: #3b82f6;"></div>
                      <span>신규 사용자</span>
                    </div>
                    <div class="legend-item">
                      <div class="legend-color" style="background: #10b981;"></div>
                      <span>활성 사용자</span>
                    </div>
                    <div class="legend-item">
                      <div class="legend-color" style="background: #f59e0b;"></div>
                      <span>이탈 사용자</span>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- 사이드 위젯들 -->
        <div class="sidebar-widgets">
          <!-- 실시간 활동 -->
          <div class="widget-card">
            <div class="widget-header">
              <h4>실시간 활동</h4>
              <div class="live-indicator">
                <div class="pulse"></div>
                <span>LIVE</span>
              </div>
            </div>
            <div class="widget-body">
              <div class="activity-stream">
                <div v-for="activity in realtimeActivities" :key="activity.id" 
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

          <!-- 빠른 통계 -->
          <div class="widget-card">
            <div class="widget-header">
              <h4>빠른 통계</h4>
            </div>
            <div class="widget-body">
              <div class="quick-stats">
                <div v-for="stat in quickStats" :key="stat.label" class="quick-stat">
                  <div class="stat-icon">
                    <i :class="stat.icon"></i>
                  </div>
                  <div class="stat-info">
                    <div class="stat-value">{{ stat.value }}</div>
                    <div class="stat-label">{{ stat.label }}</div>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <!-- 알림 센터 -->
          <div class="widget-card">
            <div class="widget-header">
              <h4>알림 센터</h4>
              <button class="btn btn-sm">모두 읽음</button>
            </div>
            <div class="widget-body">
              <div class="notifications">
                <div v-for="notification in notifications" :key="notification.id" 
                     class="notification-item" :class="{ 'unread': !notification.read }">
                  <div class="notification-icon" :class="notification.type">
                    <i :class="notification.icon"></i>
                  </div>
                  <div class="notification-content">
                    <div class="notification-title">{{ notification.title }}</div>
                    <div class="notification-message">{{ notification.message }}</div>
                    <div class="notification-time">{{ notification.time }}</div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- 하단 섹션 -->
        <div class="bottom-section">
          <!-- 최근 사용자 -->
          <div class="data-table-card">
            <div class="card-header">
              <h3>최근 가입 사용자</h3>
              <div class="table-actions">
                <button class="btn btn-outline">필터</button>
                <button class="btn btn-primary">전체 보기</button>
              </div>
            </div>
            <div class="card-body">
              <div class="data-table">
                <div class="table-header">
                  <div class="table-cell">사용자</div>
                  <div class="table-cell">이메일</div>
                  <div class="table-cell">가입일</div>
                  <div class="table-cell">상태</div>
                  <div class="table-cell">작업</div>
                </div>
                <div v-for="user in recentUsers" :key="user.id" class="table-row">
                  <div class="table-cell">
                    <div class="user-info">
                      <img :src="user.avatar" :alt="user.name" class="user-avatar-small">
                      <div class="user-details">
                        <div class="user-name">{{ user.name }}</div>
                        <div class="user-role">{{ user.role }}</div>
                      </div>
                    </div>
                  </div>
                  <div class="table-cell">{{ user.email }}</div>
                  <div class="table-cell">{{ user.joinedAt }}</div>
                  <div class="table-cell">
                    <span class="status-badge" :class="user.status">{{ user.statusText }}</span>
                  </div>
                  <div class="table-cell">
                    <div class="action-buttons">
                      <button class="btn btn-sm btn-outline">보기</button>
                      <button class="btn btn-sm btn-primary">편집</button>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <!-- 성과 지표 -->
          <div class="metrics-card">
            <div class="card-header">
              <h3>성과 지표</h3>
              <div class="metrics-filter">
                <select class="form-select">
                  <option>이번 주</option>
                  <option>이번 달</option>
                  <option>이번 분기</option>
                </select>
              </div>
            </div>
            <div class="card-body">
              <div class="metrics-grid">
                <div v-for="metric in performanceMetrics" :key="metric.id" class="metric-item">
                  <div class="metric-header">
                    <div class="metric-title">{{ metric.title }}</div>
                    <div class="metric-value">{{ metric.value }}</div>
                  </div>
                  <div class="metric-chart">
                    <div class="progress-ring">
                      <svg class="progress-ring-svg" width="60" height="60">
                        <circle class="progress-ring-circle" cx="30" cy="30" r="25" 
                                :stroke-dasharray="metric.circumference" 
                                :stroke-dashoffset="metric.offset"
                                :style="{ '--progress': metric.percentage }"></circle>
                      </svg>
                      <div class="progress-text">{{ metric.percentage }}%</div>
                    </div>
                  </div>
                  <div class="metric-change" :class="metric.trend">
                    <i :class="metric.trendIcon"></i>
                    {{ metric.change }}
                  </div>
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
const selectedMainMenu = ref('dashboard')
const selectedPeriod = ref('30d')

// 사용자 정보
const user = ref({
  id: 1,
  name: '홍길동',
  role: '관리자',
  avatar: 'https://randomuser.me/api/portraits/men/32.jpg'
})

// 메인 메뉴
const mainMenus = ref([
  { name: 'dashboard', title: '대시보드', icon: 'fas fa-tachometer-alt' },
  { name: 'analytics', title: '분석', icon: 'fas fa-chart-line' },
  { name: 'users', title: '사용자', icon: 'fas fa-users' },
  { name: 'courses', title: '강의', icon: 'fas fa-book' },
  { name: 'reports', title: '보고서', icon: 'fas fa-file-alt' }
])

// 시간 필터
const timePeriods = ref([
  { label: '7일', value: '7d' },
  { label: '30일', value: '30d' },
  { label: '90일', value: '90d' },
  { label: '1년', value: '1y' }
])

// KPI 데이터
const kpiData = ref([
  {
    id: 1,
    label: '총 사용자',
    value: '12,345',
    description: '전체 등록 사용자 수',
    icon: 'fas fa-users',
    color: 'linear-gradient(135deg, #667eea 0%, #764ba2 100%)',
    trend: 'up',
    trendIcon: 'fas fa-arrow-up',
    change: '+12.5%',
    progress: '75%',
    chartPoints: [65, 70, 75, 80, 85, 90, 88, 92, 89, 95]
  },
  {
    id: 2,
    label: '활성 사용자',
    value: '8,234',
    description: '최근 30일 활동 사용자',
    icon: 'fas fa-user-check',
    color: 'linear-gradient(135deg, #f093fb 0%, #f5576c 100%)',
    trend: 'up',
    trendIcon: 'fas fa-arrow-up',
    change: '+8.3%',
    progress: '68%',
    chartPoints: [55, 60, 65, 70, 75, 80, 78, 82, 79, 85]
  },
  {
    id: 3,
    label: '수강 완료율',
    value: '87.2%',
    description: '강의 수강 완료 비율',
    icon: 'fas fa-graduation-cap',
    color: 'linear-gradient(135deg, #4facfe 0%, #00f2fe 100%)',
    trend: 'up',
    trendIcon: 'fas fa-arrow-up',
    change: '+5.1%',
    progress: '87%',
    chartPoints: [70, 75, 80, 85, 90, 88, 92, 89, 95, 87]
  },
  {
    id: 4,
    label: '월간 수익',
    value: '₩45.2M',
    description: '이번 달 총 수익',
    icon: 'fas fa-won-sign',
    color: 'linear-gradient(135deg, #43e97b 0%, #38f9d7 100%)',
    trend: 'down',
    trendIcon: 'fas fa-arrow-down',
    change: '-2.1%',
    progress: '92%',
    chartPoints: [85, 90, 88, 92, 89, 95, 87, 93, 90, 88]
  }
])

// 실시간 활동
const realtimeActivities = ref([
  {
    id: 1,
    user: '김철수',
    action: '새 강의를 등록했습니다',
    time: '방금 전',
    avatar: 'https://randomuser.me/api/portraits/men/33.jpg',
    status: 'success'
  },
  {
    id: 2,
    user: '이영희',
    action: '강의를 완료했습니다',
    time: '1분 전',
    avatar: 'https://randomuser.me/api/portraits/women/34.jpg',
    status: 'info'
  },
  {
    id: 3,
    user: '박민수',
    action: '새 계정을 생성했습니다',
    time: '3분 전',
    avatar: 'https://randomuser.me/api/portraits/men/35.jpg',
    status: 'success'
  }
])

// 빠른 통계
const quickStats = ref([
  { label: '오늘 방문자', value: '1,234', icon: 'fas fa-eye' },
  { label: '신규 가입', value: '45', icon: 'fas fa-user-plus' },
  { label: '강의 완료', value: '89', icon: 'fas fa-check-circle' },
  { label: '수익', value: '₩2.1M', icon: 'fas fa-won-sign' }
])

// 알림
const notifications = ref([
  {
    id: 1,
    title: '새로운 사용자 가입',
    message: '김철수님이 새로 가입했습니다.',
    time: '5분 전',
    type: 'success',
    icon: 'fas fa-user-plus',
    read: false
  },
  {
    id: 2,
    title: '시스템 업데이트',
    message: '새로운 기능이 추가되었습니다.',
    time: '1시간 전',
    type: 'info',
    icon: 'fas fa-info-circle',
    read: false
  },
  {
    id: 3,
    title: '백업 완료',
    message: '데이터 백업이 성공적으로 완료되었습니다.',
    time: '3시간 전',
    type: 'success',
    icon: 'fas fa-database',
    read: true
  }
])

// 최근 사용자
const recentUsers = ref([
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

// 성과 지표
const performanceMetrics = ref([
  {
    id: 1,
    title: '사용자 만족도',
    value: '4.8/5.0',
    percentage: 96,
    circumference: 157,
    offset: 6.28,
    trend: 'up',
    trendIcon: 'fas fa-arrow-up',
    change: '+2.1%'
  },
  {
    id: 2,
    title: '강의 완료율',
    value: '87.2%',
    percentage: 87,
    circumference: 157,
    offset: 20.4,
    trend: 'up',
    trendIcon: 'fas fa-arrow-up',
    change: '+5.3%'
  },
  {
    id: 3,
    title: '시스템 안정성',
    value: '99.9%',
    percentage: 99,
    circumference: 157,
    offset: 1.57,
    trend: 'up',
    trendIcon: 'fas fa-arrow-up',
    change: '+0.1%'
  },
  {
    id: 4,
    title: '고객 지원',
    value: '4.6/5.0',
    percentage: 92,
    circumference: 157,
    offset: 12.56,
    trend: 'down',
    trendIcon: 'fas fa-arrow-down',
    change: '-1.2%'
  }
])
</script>

<style scoped>
/* ============================================
   DASHBOARD CENTERED LAYOUT STYLES
   ============================================ */

.dashboard-layout {
  min-height: 100vh;
  background: #f8fafc;
}

/* ============================================
   HEADER STYLES
   ============================================ */
.dashboard-header {
  background: white;
  border-bottom: 1px solid #e2e8f0;
  padding: 0 24px;
  height: 70px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  position: sticky;
  top: 0;
  z-index: 1000;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
}

.header-left {
  display: flex;
  align-items: center;
  gap: 40px;
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

.main-nav {
  display: flex;
  gap: 8px;
}

.nav-link {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 8px 16px;
  border-radius: 8px;
  color: #64748b;
  text-decoration: none;
  font-weight: 500;
  transition: all 0.3s ease;
  cursor: pointer;
}

.nav-link:hover {
  background: #f1f5f9;
  color: #475569;
}

.nav-link.active {
  background: #dbeafe;
  color: #1e40af;
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

.user-profile {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 8px 16px;
  background: #f8fafc;
  border-radius: 8px;
  cursor: pointer;
  transition: all 0.3s ease;
}

.user-profile:hover {
  background: #f1f5f9;
}

.user-avatar {
  width: 32px;
  height: 32px;
  border-radius: 50%;
  object-fit: cover;
}

.user-info .user-name {
  font-weight: 500;
  color: #1e293b;
  font-size: 14px;
}

.user-role {
  font-size: 12px;
  color: #64748b;
}

/* ============================================
   MAIN CONTENT STYLES
   ============================================ */
.dashboard-main {
  padding: 24px;
  max-width: 1400px;
  margin: 0 auto;
}

.dashboard-grid {
  display: grid;
  grid-template-areas: 
    "kpi kpi kpi kpi"
    "main-chart main-chart sidebar sidebar"
    "bottom bottom bottom bottom";
  grid-template-columns: repeat(4, 1fr);
  gap: 24px;
}

.kpi-section {
  grid-area: kpi;
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 20px;
}

.kpi-card {
  background: white;
  border-radius: 16px;
  padding: 24px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
  border: 1px solid #e2e8f0;
  transition: all 0.3s ease;
  position: relative;
  overflow: hidden;
}

.kpi-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
}

.kpi-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 16px;
}

.kpi-icon {
  width: 48px;
  height: 48px;
  border-radius: 12px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  font-size: 20px;
}

.kpi-trend {
  display: flex;
  align-items: center;
  gap: 4px;
  font-size: 12px;
  font-weight: 600;
}

.kpi-trend.up {
  color: #059669;
}

.kpi-trend.down {
  color: #dc2626;
}

.kpi-content {
  margin-bottom: 16px;
}

.kpi-value {
  font-size: 28px;
  font-weight: 700;
  color: #1e293b;
  margin-bottom: 4px;
}

.kpi-label {
  font-size: 14px;
  font-weight: 600;
  color: #475569;
  margin-bottom: 4px;
}

.kpi-description {
  font-size: 12px;
  color: #64748b;
}

.kpi-chart {
  height: 40px;
  display: flex;
  align-items: end;
  gap: 2px;
}

.mini-chart {
  display: flex;
  align-items: end;
  gap: 2px;
  height: 100%;
  width: 100%;
}

.chart-line {
  flex: 1;
  background: linear-gradient(180deg, #3b82f6 0%, #1d4ed8 100%);
  border-radius: 1px;
  animation: growUp 0.6s ease-out var(--delay);
  min-height: 4px;
}

@keyframes growUp {
  from { height: 0; }
  to { height: 100%; }
}

.main-chart-section {
  grid-area: main-chart;
}

.chart-card.large {
  background: white;
  border-radius: 16px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
  border: 1px solid #e2e8f0;
  overflow: hidden;
}

.chart-header {
  padding: 24px;
  border-bottom: 1px solid #e2e8f0;
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
}

.chart-title h3 {
  font-size: 20px;
  font-weight: 700;
  color: #1e293b;
  margin: 0 0 4px 0;
}

.chart-title p {
  color: #64748b;
  margin: 0;
  font-size: 14px;
}

.chart-controls {
  display: flex;
  flex-direction: column;
  gap: 12px;
  align-items: flex-end;
}

.time-filter {
  display: flex;
  gap: 4px;
  background: #f1f5f9;
  border-radius: 8px;
  padding: 4px;
}

.time-btn {
  padding: 6px 12px;
  border: none;
  background: transparent;
  color: #64748b;
  font-size: 12px;
  font-weight: 500;
  border-radius: 6px;
  cursor: pointer;
  transition: all 0.3s ease;
}

.time-btn:hover {
  background: #e2e8f0;
  color: #475569;
}

.time-btn.active {
  background: #3b82f6;
  color: white;
}

.chart-actions {
  display: flex;
  gap: 8px;
}

.chart-body {
  padding: 24px;
}

.chart-container {
  height: 300px;
}

.chart-placeholder {
  height: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  color: #64748b;
  background: #f8fafc;
  border-radius: 8px;
  border: 2px dashed #d1d5db;
}

.chart-placeholder i {
  font-size: 48px;
  margin-bottom: 16px;
  color: #94a3b8;
}

.chart-legend {
  display: flex;
  gap: 16px;
  margin-top: 16px;
}

.legend-item {
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 12px;
  color: #64748b;
}

.legend-color {
  width: 12px;
  height: 12px;
  border-radius: 2px;
}

.sidebar-widgets {
  grid-area: sidebar;
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.widget-card {
  background: white;
  border-radius: 16px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
  border: 1px solid #e2e8f0;
  overflow: hidden;
}

.widget-header {
  padding: 16px 20px;
  border-bottom: 1px solid #e2e8f0;
  display: flex;
  justify-content: space-between;
  align-items: center;
  background: #f8fafc;
}

.widget-header h4 {
  font-size: 16px;
  font-weight: 600;
  color: #1e293b;
  margin: 0;
}

.live-indicator {
  display: flex;
  align-items: center;
  gap: 6px;
  font-size: 10px;
  font-weight: 600;
  color: #dc2626;
}

.pulse {
  width: 8px;
  height: 8px;
  background: #dc2626;
  border-radius: 50%;
  animation: pulse 2s infinite;
}

@keyframes pulse {
  0% { opacity: 1; }
  50% { opacity: 0.5; }
  100% { opacity: 1; }
}

.widget-body {
  padding: 20px;
}

.activity-stream {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.activity-item {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 8px;
  border-radius: 8px;
  transition: background-color 0.3s ease;
}

.activity-item:hover {
  background: #f8fafc;
}

.activity-avatar {
  width: 32px;
  height: 32px;
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

.quick-stats {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.quick-stat {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 12px;
  background: #f8fafc;
  border-radius: 8px;
}

.stat-icon {
  width: 32px;
  height: 32px;
  background: #dbeafe;
  border-radius: 8px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #1e40af;
  font-size: 14px;
}

.stat-info {
  flex: 1;
}

.stat-value {
  font-size: 16px;
  font-weight: 600;
  color: #1e293b;
  margin-bottom: 2px;
}

.stat-label {
  font-size: 12px;
  color: #64748b;
}

.notifications {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.notification-item {
  display: flex;
  align-items: flex-start;
  gap: 12px;
  padding: 12px;
  border-radius: 8px;
  transition: background-color 0.3s ease;
}

.notification-item:hover {
  background: #f8fafc;
}

.notification-item.unread {
  background: #fef3c7;
  border-left: 3px solid #f59e0b;
}

.notification-icon {
  width: 32px;
  height: 32px;
  border-radius: 8px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 14px;
}

.notification-icon.success {
  background: #d1fae5;
  color: #059669;
}

.notification-icon.info {
  background: #dbeafe;
  color: #1e40af;
}

.notification-content {
  flex: 1;
}

.notification-title {
  font-size: 14px;
  font-weight: 600;
  color: #1e293b;
  margin-bottom: 2px;
}

.notification-message {
  font-size: 12px;
  color: #64748b;
  margin-bottom: 4px;
}

.notification-time {
  font-size: 11px;
  color: #9ca3af;
}

.bottom-section {
  grid-area: bottom;
  display: grid;
  grid-template-columns: 2fr 1fr;
  gap: 24px;
}

.data-table-card,
.metrics-card {
  background: white;
  border-radius: 16px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
  border: 1px solid #e2e8f0;
  overflow: hidden;
}

.card-header {
  padding: 20px 24px;
  border-bottom: 1px solid #e2e8f0;
  display: flex;
  justify-content: space-between;
  align-items: center;
  background: #f8fafc;
}

.card-header h3 {
  font-size: 18px;
  font-weight: 600;
  color: #1e293b;
  margin: 0;
}

.table-actions,
.metrics-filter {
  display: flex;
  gap: 8px;
}

.card-body {
  padding: 24px;
}

.data-table {
  display: flex;
  flex-direction: column;
}

.table-header {
  display: grid;
  grid-template-columns: 2fr 1fr 1fr 1fr 1fr;
  background: #f8fafc;
  border-radius: 8px;
  margin-bottom: 8px;
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
  padding: 12px 16px;
  display: flex;
  align-items: center;
  color: #374151;
}

.table-header .table-cell {
  font-weight: 600;
  color: #475569;
  font-size: 14px;
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
  color: #1e293b;
  margin-bottom: 2px;
}

.user-role {
  font-size: 12px;
  color: #64748b;
}

.status-badge {
  padding: 4px 8px;
  border-radius: 6px;
  font-size: 12px;
  font-weight: 500;
}

.status-badge.active {
  background: #d1fae5;
  color: #059669;
}

.status-badge.inactive {
  background: #fef2f2;
  color: #dc2626;
}

.action-buttons {
  display: flex;
  gap: 4px;
}

.metrics-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 20px;
}

.metric-item {
  text-align: center;
  padding: 20px;
  background: #f8fafc;
  border-radius: 12px;
  border: 1px solid #e2e8f0;
}

.metric-header {
  margin-bottom: 16px;
}

.metric-title {
  font-size: 14px;
  font-weight: 500;
  color: #64748b;
  margin-bottom: 4px;
}

.metric-value {
  font-size: 20px;
  font-weight: 700;
  color: #1e293b;
}

.metric-chart {
  position: relative;
  display: flex;
  justify-content: center;
  margin-bottom: 12px;
}

.progress-ring {
  position: relative;
  width: 60px;
  height: 60px;
}

.progress-ring-svg {
  transform: rotate(-90deg);
}

.progress-ring-circle {
  fill: none;
  stroke: #e2e8f0;
  stroke-width: 4;
  stroke-linecap: round;
  transition: stroke-dashoffset 0.5s ease;
}

.progress-ring-circle:nth-child(2) {
  stroke: #3b82f6;
  stroke-dasharray: 157;
  stroke-dashoffset: calc(157 - (157 * var(--progress)) / 100);
}

.progress-text {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  font-size: 12px;
  font-weight: 600;
  color: #1e293b;
}

.metric-change {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 4px;
  font-size: 12px;
  font-weight: 600;
}

.metric-change.up {
  color: #059669;
}

.metric-change.down {
  color: #dc2626;
}

/* ============================================
   RESPONSIVE DESIGN
   ============================================ */
@media (max-width: 1200px) {
  .dashboard-grid {
    grid-template-areas: 
      "kpi kpi kpi kpi"
      "main-chart main-chart main-chart main-chart"
      "sidebar sidebar sidebar sidebar"
      "bottom bottom bottom bottom";
  }
  
  .kpi-section {
    grid-template-columns: repeat(2, 1fr);
  }
}

@media (max-width: 768px) {
  .dashboard-header {
    flex-direction: column;
    height: auto;
    padding: 16px;
    gap: 16px;
  }
  
  .header-left {
    flex-direction: column;
    gap: 16px;
  }
  
  .main-nav {
    flex-wrap: wrap;
  }
  
  .header-right {
    width: 100%;
    justify-content: space-between;
  }
  
  .search-input {
    width: 200px;
  }
  
  .dashboard-main {
    padding: 16px;
  }
  
  .kpi-section {
    grid-template-columns: 1fr;
  }
  
  .bottom-section {
    grid-template-columns: 1fr;
  }
  
  .metrics-grid {
    grid-template-columns: 1fr;
  }
  
  .table-header,
  .table-row {
    grid-template-columns: 1fr;
    gap: 8px;
  }
}
</style>

