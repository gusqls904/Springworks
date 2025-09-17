<template>
  <div class="dark-layout">
    <!-- 상단 네비게이션 -->
    <nav class="dark-nav">
      <div class="nav-container">
        <div class="nav-brand">
          <div class="brand-icon">
            <i class="fas fa-moon"></i>
          </div>
          <h1 class="brand-title">Dark Learning</h1>
        </div>
        
        <div class="nav-center">
          <div class="search-box">
            <i class="fas fa-search"></i>
            <input type="text" placeholder="검색..." class="search-input">
          </div>
        </div>
        
        <div class="nav-actions">
          <button class="nav-btn">
            <i class="fas fa-bell"></i>
            <span class="notification-dot"></span>
          </button>
          <button class="nav-btn">
            <i class="fas fa-cog"></i>
          </button>
          <div class="user-menu">
            <img :src="user.avatar" :alt="user.name" class="user-avatar">
            <span class="user-name">{{ user.name }}</span>
          </div>
        </div>
      </div>
    </nav>

    <!-- 메인 콘텐츠 -->
    <div class="dark-main">
      <!-- 사이드바 -->
      <aside class="dark-sidebar">
        <div class="sidebar-header">
          <h3>메뉴</h3>
        </div>
        
        <nav class="sidebar-nav">
          <div v-for="menu in menus" :key="menu.name" 
               class="nav-item" 
               :class="{ 'active': selectedMenu.name === menu.name }"
               @click="selectMenu(menu)">
            <div class="nav-icon">
              <i :class="menu.icon"></i>
            </div>
            <span class="nav-text">{{ menu.title }}</span>
            <div class="nav-badge" v-if="menu.badge">{{ menu.badge }}</div>
          </div>
        </nav>
      </aside>

      <!-- 콘텐츠 영역 -->
      <main class="content-area">
        <!-- 페이지 헤더 -->
        <header class="page-header">
          <div class="header-content">
            <div class="page-info">
              <h2>{{ selectedMenu.title }}</h2>
              <p>{{ selectedMenu.description }}</p>
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
          <!-- 통계 카드 -->
          <div class="stats-grid">
            <div v-for="stat in dashboardStats" :key="stat.label" class="stat-card">
              <div class="stat-header">
                <div class="stat-icon" :style="{ background: stat.gradient }">
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
                <div class="chart-bar" v-for="(bar, index) in stat.chartData" :key="index"
                     :style="{ height: bar + '%', '--delay': index * 0.1 + 's' }"></div>
              </div>
            </div>
          </div>

          <!-- 차트 섹션 -->
          <div class="charts-section">
            <div class="chart-card">
              <div class="card-header">
                <h3>사용자 활동</h3>
                <div class="chart-controls">
                  <button class="btn btn-sm active">주간</button>
                  <button class="btn btn-sm">월간</button>
                  <button class="btn btn-sm">연간</button>
                </div>
              </div>
              <div class="card-body">
                <div class="chart-container">
                  <div class="chart-placeholder">
                    <i class="fas fa-chart-area"></i>
                    <p>사용자 활동 차트</p>
                  </div>
                </div>
              </div>
            </div>
            
            <div class="chart-card">
              <div class="card-header">
                <h3>인기 강의</h3>
              </div>
              <div class="card-body">
                <div class="course-ranking">
                  <div v-for="(course, index) in popularCourses" :key="course.id" 
                       class="ranking-item">
                    <div class="rank-number">{{ index + 1 }}</div>
                    <div class="course-info">
                      <div class="course-name">{{ course.name }}</div>
                      <div class="course-stats">{{ course.students }}명 수강</div>
                    </div>
                    <div class="course-progress">
                      <div class="progress-bar">
                        <div class="progress-fill" :style="{ width: course.progress + '%' }"></div>
                      </div>
                      <span class="progress-text">{{ course.progress }}%</span>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <!-- 활동 피드 -->
          <div class="activity-feed">
            <div class="feed-header">
              <h3>최근 활동</h3>
              <button class="btn btn-sm btn-outline">전체 보기</button>
            </div>
            <div class="feed-content">
              <div v-for="activity in recentActivities" :key="activity.id" 
                   class="activity-item">
                <div class="activity-avatar">
                  <img :src="activity.avatar" :alt="activity.user">
                </div>
                <div class="activity-content">
                  <div class="activity-text">
                    <strong>{{ activity.user }}</strong> {{ activity.action }}
                  </div>
                  <div class="activity-meta">
                    <span class="activity-time">{{ activity.time }}</span>
                    <span class="activity-type" :class="activity.type">{{ activity.typeText }}</span>
                  </div>
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
              <p>{{ users.length }}명의 사용자가 등록되어 있습니다</p>
            </div>
            <div class="header-controls">
              <div class="filter-group">
                <select class="form-select">
                  <option>모든 사용자</option>
                  <option>활성 사용자</option>
                  <option>비활성 사용자</option>
                </select>
              </div>
              <button class="btn btn-primary">
                <i class="fas fa-user-plus"></i>
                사용자 추가
              </button>
            </div>
          </div>

          <div class="users-table">
            <div class="table-header">
              <div class="table-cell checkbox">
                <input type="checkbox" class="form-checkbox">
              </div>
              <div class="table-cell">사용자</div>
              <div class="table-cell">이메일</div>
              <div class="table-cell">역할</div>
              <div class="table-cell">상태</div>
              <div class="table-cell">가입일</div>
              <div class="table-cell">작업</div>
            </div>
            <div v-for="user in users" :key="user.id" class="table-row">
              <div class="table-cell checkbox">
                <input type="checkbox" class="form-checkbox">
              </div>
              <div class="table-cell">
                <div class="user-info">
                  <img :src="user.avatar" :alt="user.name" class="user-avatar-small">
                  <div class="user-details">
                    <div class="user-name">{{ user.name }}</div>
                    <div class="user-id">ID: {{ user.id }}</div>
                  </div>
                </div>
              </div>
              <div class="table-cell">{{ user.email }}</div>
              <div class="table-cell">
                <span class="role-badge" :class="user.role">{{ user.roleText }}</span>
              </div>
              <div class="table-cell">
                <span class="status-badge" :class="user.status">{{ user.statusText }}</span>
              </div>
              <div class="table-cell">{{ user.joinedAt }}</div>
              <div class="table-cell">
                <div class="action-menu">
                  <button class="action-btn">
                    <i class="fas fa-ellipsis-v"></i>
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- 강의 관리 콘텐츠 -->
        <div v-if="selectedMenu.name === 'courses'" class="courses-content">
          <div class="content-header">
            <div class="header-info">
              <h2>강의 관리</h2>
              <p>{{ courses.length }}개의 강의가 등록되어 있습니다</p>
            </div>
            <div class="header-controls">
              <button class="btn btn-primary">
                <i class="fas fa-plus"></i>
                강의 추가
              </button>
            </div>
          </div>

          <div class="courses-grid">
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
      </main>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import '../common.css'

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

// 선택된 메뉴
const selectedMenu = ref(menus.value[0])
const selectMenu = (menu) => (selectedMenu.value = menu)

// 대시보드 통계
const dashboardStats = ref([
  {
    label: '총 사용자',
    value: '1,234',
    icon: 'fas fa-users',
    gradient: 'linear-gradient(135deg, #667eea 0%, #764ba2 100%)',
    trend: 'up',
    trendIcon: 'fas fa-arrow-up',
    change: '+12%',
    chartData: [65, 70, 75, 80, 85, 90, 88]
  },
  {
    label: '활성 강의',
    value: '45',
    icon: 'fas fa-book',
    gradient: 'linear-gradient(135deg, #f093fb 0%, #f5576c 100%)',
    trend: 'up',
    trendIcon: 'fas fa-arrow-up',
    change: '+8%',
    chartData: [40, 45, 50, 55, 60, 65, 62]
  },
  {
    label: '완료된 수강',
    value: '892',
    icon: 'fas fa-graduation-cap',
    gradient: 'linear-gradient(135deg, #4facfe 0%, #00f2fe 100%)',
    trend: 'up',
    trendIcon: 'fas fa-arrow-up',
    change: '+15%',
    chartData: [70, 75, 80, 85, 90, 95, 92]
  },
  {
    label: '월간 수익',
    value: '₩12.5M',
    icon: 'fas fa-won-sign',
    gradient: 'linear-gradient(135deg, #43e97b 0%, #38f9d7 100%)',
    trend: 'down',
    trendIcon: 'fas fa-arrow-down',
    change: '-3%',
    chartData: [50, 55, 60, 58, 65, 70, 68]
  }
])

// 인기 강의
const popularCourses = ref([
  { id: 1, name: 'Vue.js 완벽 가이드', students: 156, progress: 85 },
  { id: 2, name: 'React 실무 프로젝트', students: 134, progress: 72 },
  { id: 3, name: 'Node.js 백엔드 개발', students: 98, progress: 90 },
  { id: 4, name: 'Python 데이터분석', students: 87, progress: 68 }
])

// 최근 활동
const recentActivities = ref([
  {
    id: 1,
    user: '김철수',
    action: '새 강의를 등록했습니다',
    time: '5분 전',
    avatar: 'https://randomuser.me/api/portraits/men/33.jpg',
    status: 'success',
    type: 'course',
    typeText: '강의'
  },
  {
    id: 2,
    user: '이영희',
    action: '사용자 정보를 수정했습니다',
    time: '15분 전',
    avatar: 'https://randomuser.me/api/portraits/women/34.jpg',
    status: 'info',
    type: 'user',
    typeText: '사용자'
  },
  {
    id: 3,
    user: '박민수',
    action: '강의를 삭제했습니다',
    time: '1시간 전',
    avatar: 'https://randomuser.me/api/portraits/men/35.jpg',
    status: 'warning',
    type: 'course',
    typeText: '강의'
  }
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
    statusText: '활성',
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
    statusText: '활성',
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
    statusText: '비활성',
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
   DARK THEME LAYOUT STYLES
   ============================================ */

.dark-layout {
  min-height: 100vh;
  background: #0f172a;
  color: #e2e8f0;
}

/* ============================================
   NAVIGATION STYLES
   ============================================ */
.dark-nav {
  background: #1e293b;
  border-bottom: 1px solid #334155;
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
  color: #f1f5f9;
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
  color: #64748b;
  font-size: 16px;
}

.search-input {
  width: 100%;
  padding: 12px 16px 12px 48px;
  border: 1px solid #334155;
  border-radius: 12px;
  font-size: 14px;
  background: #0f172a;
  color: #e2e8f0;
  transition: all 0.3s ease;
}

.search-input:focus {
  outline: none;
  border-color: #667eea;
  box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.1);
}

.search-input::placeholder {
  color: #64748b;
}

.nav-actions {
  display: flex;
  align-items: center;
  gap: 16px;
}

.nav-btn {
  position: relative;
  width: 44px;
  height: 44px;
  border: none;
  background: #334155;
  border-radius: 12px;
  color: #94a3b8;
  cursor: pointer;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  justify-content: center;
}

.nav-btn:hover {
  background: #475569;
  color: #e2e8f0;
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

.user-menu {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 8px 16px;
  background: #334155;
  border-radius: 12px;
  cursor: pointer;
  transition: all 0.3s ease;
}

.user-menu:hover {
  background: #475569;
}

.user-avatar {
  width: 32px;
  height: 32px;
  border-radius: 50%;
  object-fit: cover;
}

.user-name {
  font-weight: 500;
  color: #e2e8f0;
}

/* ============================================
   MAIN LAYOUT STYLES
   ============================================ */
.dark-main {
  display: flex;
  max-width: 1400px;
  margin: 0 auto;
  min-height: calc(100vh - 70px);
}

.dark-sidebar {
  width: 280px;
  background: #1e293b;
  border-right: 1px solid #334155;
  padding: 20px 0;
}

.sidebar-header {
  padding: 0 20px 20px;
  border-bottom: 1px solid #334155;
  margin-bottom: 20px;
}

.sidebar-header h3 {
  font-size: 16px;
  font-weight: 600;
  color: #94a3b8;
  margin: 0;
  text-transform: uppercase;
  letter-spacing: 0.5px;
}

.sidebar-nav {
  padding: 0 10px;
}

.nav-item {
  position: relative;
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 12px 16px;
  margin: 4px 0;
  border-radius: 8px;
  cursor: pointer;
  transition: all 0.3s ease;
  color: #94a3b8;
}

.nav-item:hover {
  background: #334155;
  color: #e2e8f0;
}

.nav-item.active {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
}

.nav-icon {
  width: 20px;
  height: 20px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 16px;
}

.nav-text {
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

/* ============================================
   CONTENT AREA STYLES
   ============================================ */
.content-area {
  flex: 1;
  background: #0f172a;
}

.page-header {
  padding: 30px;
  border-bottom: 1px solid #334155;
  background: linear-gradient(135deg, #1e293b 0%, #334155 100%);
}

.header-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.page-info h2 {
  font-size: 28px;
  font-weight: 700;
  color: #f1f5f9;
  margin: 0 0 8px 0;
}

.page-info p {
  color: #94a3b8;
  margin: 0;
  font-size: 16px;
}

.header-actions {
  display: flex;
  gap: 12px;
}

.btn-outline {
  background: transparent;
  border: 2px solid #475569;
  color: #94a3b8;
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
  background: #334155;
  border-color: #64748b;
  color: #e2e8f0;
}

.btn-primary {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border: none;
  color: white;
  padding: 10px 20px;
  border-radius: 10px;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  gap: 8px;
}

.btn-primary:hover {
  transform: translateY(-1px);
  box-shadow: 0 4px 15px rgba(102, 126, 234, 0.3);
}

/* ============================================
   DASHBOARD STYLES
   ============================================ */
.dashboard-content {
  padding: 30px;
}

.stats-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
  gap: 20px;
  margin-bottom: 30px;
}

.stat-card {
  background: #1e293b;
  border: 1px solid #334155;
  border-radius: 16px;
  padding: 24px;
  transition: all 0.3s ease;
}

.stat-card:hover {
  transform: translateY(-2px);
  border-color: #475569;
  box-shadow: 0 8px 25px rgba(0, 0, 0, 0.3);
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
  color: #f1f5f9;
  margin-bottom: 4px;
}

.stat-label {
  color: #94a3b8;
  font-size: 14px;
}

.stat-chart {
  height: 40px;
  display: flex;
  align-items: end;
  gap: 2px;
}

.chart-bar {
  flex: 1;
  background: linear-gradient(180deg, #667eea 0%, #764ba2 100%);
  border-radius: 2px;
  animation: growUp 0.6s ease-out var(--delay);
}

@keyframes growUp {
  from { height: 0; }
  to { height: 100%; }
}

.charts-section {
  display: grid;
  grid-template-columns: 2fr 1fr;
  gap: 20px;
  margin-bottom: 30px;
}

.chart-card {
  background: #1e293b;
  border: 1px solid #334155;
  border-radius: 16px;
  overflow: hidden;
}

.card-header {
  padding: 20px 24px;
  border-bottom: 1px solid #334155;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.card-header h3 {
  font-size: 18px;
  font-weight: 600;
  color: #f1f5f9;
  margin: 0;
}

.chart-controls {
  display: flex;
  gap: 8px;
}

.btn-sm {
  padding: 6px 12px;
  font-size: 12px;
  border-radius: 6px;
  border: 1px solid #475569;
  background: transparent;
  color: #94a3b8;
  cursor: pointer;
  transition: all 0.3s ease;
}

.btn-sm:hover,
.btn-sm.active {
  background: #667eea;
  border-color: #667eea;
  color: white;
}

.card-body {
  padding: 24px;
}

.chart-container {
  height: 200px;
}

.chart-placeholder {
  height: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  color: #64748b;
  background: #0f172a;
  border-radius: 8px;
  border: 2px dashed #334155;
}

.chart-placeholder i {
  font-size: 48px;
  margin-bottom: 16px;
  color: #475569;
}

.course-ranking {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.ranking-item {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 12px;
  background: #0f172a;
  border-radius: 8px;
  border: 1px solid #334155;
}

.rank-number {
  width: 24px;
  height: 24px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  font-weight: 600;
  font-size: 12px;
}

.course-info {
  flex: 1;
}

.course-name {
  font-weight: 500;
  color: #f1f5f9;
  margin-bottom: 4px;
}

.course-stats {
  font-size: 12px;
  color: #94a3b8;
}

.course-progress {
  display: flex;
  flex-direction: column;
  gap: 4px;
  align-items: flex-end;
}

.progress-bar {
  width: 80px;
  height: 4px;
  background: #334155;
  border-radius: 2px;
  overflow: hidden;
}

.progress-fill {
  height: 100%;
  background: linear-gradient(90deg, #667eea 0%, #764ba2 100%);
  border-radius: 2px;
  transition: width 0.3s ease;
}

.progress-text {
  font-size: 12px;
  color: #94a3b8;
  font-weight: 500;
}

.activity-feed {
  background: #1e293b;
  border: 1px solid #334155;
  border-radius: 16px;
  overflow: hidden;
}

.feed-header {
  padding: 20px 24px;
  border-bottom: 1px solid #334155;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.feed-header h3 {
  font-size: 18px;
  font-weight: 600;
  color: #f1f5f9;
  margin: 0;
}

.feed-content {
  padding: 24px;
}

.activity-item {
  display: flex;
  align-items: center;
  gap: 16px;
  padding: 16px 0;
  border-bottom: 1px solid #334155;
}

.activity-item:last-child {
  border-bottom: none;
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
  color: #e2e8f0;
  margin-bottom: 4px;
}

.activity-meta {
  display: flex;
  gap: 12px;
  font-size: 12px;
}

.activity-time {
  color: #94a3b8;
}

.activity-type {
  padding: 2px 6px;
  border-radius: 4px;
  font-weight: 500;
}

.activity-type.course {
  background: #1e40af;
  color: #dbeafe;
}

.activity-type.user {
  background: #059669;
  color: #d1fae5;
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

/* ============================================
   USERS STYLES
   ============================================ */
.users-content {
  padding: 30px;
}

.content-header {
  background: #1e293b;
  border: 1px solid #334155;
  border-radius: 16px;
  padding: 24px;
  margin-bottom: 24px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.header-info h2 {
  font-size: 24px;
  font-weight: 700;
  color: #f1f5f9;
  margin: 0 0 4px 0;
}

.header-info p {
  color: #94a3b8;
  margin: 0;
}

.header-controls {
  display: flex;
  align-items: center;
  gap: 16px;
}

.form-select {
  padding: 8px 12px;
  border: 1px solid #475569;
  border-radius: 8px;
  background: #0f172a;
  color: #e2e8f0;
  font-size: 14px;
  cursor: pointer;
}

.users-table {
  background: #1e293b;
  border: 1px solid #334155;
  border-radius: 16px;
  overflow: hidden;
}

.table-header {
  display: grid;
  grid-template-columns: 40px 2fr 1fr 1fr 1fr 1fr 80px;
  background: #334155;
  border-bottom: 1px solid #475569;
}

.table-row {
  display: grid;
  grid-template-columns: 40px 2fr 1fr 1fr 1fr 1fr 80px;
  border-bottom: 1px solid #334155;
  transition: background-color 0.3s ease;
}

.table-row:hover {
  background: #334155;
}

.table-cell {
  padding: 16px 20px;
  display: flex;
  align-items: center;
  color: #e2e8f0;
}

.table-header .table-cell {
  font-weight: 600;
  color: #f1f5f9;
  font-size: 14px;
}

.checkbox {
  justify-content: center;
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
  color: #f1f5f9;
  margin-bottom: 2px;
}

.user-id {
  font-size: 12px;
  color: #94a3b8;
}

.role-badge {
  padding: 4px 8px;
  border-radius: 6px;
  font-size: 12px;
  font-weight: 500;
}

.role-badge.admin {
  background: #dc2626;
  color: #fef2f2;
}

.role-badge.instructor {
  background: #1e40af;
  color: #dbeafe;
}

.role-badge.student {
  background: #059669;
  color: #d1fae5;
}

.status-badge {
  padding: 4px 8px;
  border-radius: 6px;
  font-size: 12px;
  font-weight: 500;
}

.status-badge.active {
  background: #059669;
  color: #d1fae5;
}

.status-badge.inactive {
  background: #dc2626;
  color: #fef2f2;
}

.action-menu {
  position: relative;
}

.action-btn {
  width: 32px;
  height: 32px;
  border: none;
  background: #475569;
  border-radius: 6px;
  color: #94a3b8;
  cursor: pointer;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  justify-content: center;
}

.action-btn:hover {
  background: #64748b;
  color: #e2e8f0;
}

/* ============================================
   COURSES STYLES
   ============================================ */
.courses-content {
  padding: 30px;
}

.courses-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(350px, 1fr));
  gap: 24px;
}

.course-card {
  background: #1e293b;
  border: 1px solid #334155;
  border-radius: 16px;
  overflow: hidden;
  transition: all 0.3s ease;
}

.course-card:hover {
  transform: translateY(-4px);
  border-color: #475569;
  box-shadow: 0 8px 25px rgba(0, 0, 0, 0.3);
}

.course-image {
  position: relative;
  height: 200px;
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
  padding: 20px;
}

.course-header {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  margin-bottom: 12px;
}

.course-title {
  font-size: 18px;
  font-weight: 600;
  color: #f1f5f9;
  margin: 0;
  flex: 1;
}

.course-rating {
  display: flex;
  align-items: center;
  gap: 4px;
  color: #fbbf24;
  font-size: 14px;
  font-weight: 500;
}

.course-description {
  color: #94a3b8;
  font-size: 14px;
  line-height: 1.5;
  margin-bottom: 16px;
}

.course-meta {
  display: flex;
  gap: 16px;
  margin-bottom: 16px;
  font-size: 14px;
  color: #64748b;
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
  font-size: 18px;
  font-weight: 700;
  color: #f1f5f9;
}

.course-actions {
  display: flex;
  gap: 8px;
}

.btn-danger {
  background: #dc2626;
  border: none;
  color: white;
  padding: 6px 12px;
  border-radius: 6px;
  font-size: 12px;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.3s ease;
}

.btn-danger:hover {
  background: #b91c1c;
}

/* ============================================
   RESPONSIVE DESIGN
   ============================================ */
@media (max-width: 1024px) {
  .charts-section {
    grid-template-columns: 1fr;
  }
  
  .stats-grid {
    grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  }
  
  .courses-grid {
    grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  }
}

@media (max-width: 768px) {
  .dark-main {
    flex-direction: column;
  }
  
  .dark-sidebar {
    width: 100%;
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
  
  .content-header {
    flex-direction: column;
    align-items: flex-start;
    gap: 20px;
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

