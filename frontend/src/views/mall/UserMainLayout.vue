<template>
  <div class="user-main-layout">
    <!-- 헤더 -->
    <header class="user-header">
      <div class="header-content">
        <div class="logo-section">
          <h1 class="logo">SpringWorks</h1>
          <span class="logo-subtitle">학습 플랫폼</span>
        </div>
        
        <nav class="user-nav">
          <a href="#" class="nav-link" :class="{ active: selectedMenu.name === 'dashboard' }" @click="selectMenu({ name: 'dashboard' })">
            <i class="fas fa-home"></i>
            홈
          </a>
          <a href="#" class="nav-link" :class="{ active: selectedMenu.name === 'courses' }" @click="selectMenu({ name: 'courses' })">
            <i class="fas fa-book"></i>
            강의
          </a>
          <a href="#" class="nav-link" :class="{ active: selectedMenu.name === 'layouts' }" @click="selectMenu({ name: 'layouts' })">
            <i class="fas fa-palette"></i>
            화면 선택
          </a>
          <a href="#" class="nav-link">
            <i class="fas fa-chart-line"></i>
            학습현황
          </a>
          <a href="#" class="nav-link">
            <i class="fas fa-user"></i>
            마이페이지
          </a>
        </nav>
        
        <div class="user-actions">
          <div class="search-box">
            <input type="text" placeholder="강의 검색..." class="search-input">
            <button class="search-btn">
              <i class="fas fa-search"></i>
            </button>
          </div>
          <div class="user-profile">
            <img src="https://via.placeholder.com/40" alt="프로필" class="profile-img">
            <span class="user-name">김학습</span>
          </div>
        </div>
      </div>
    </header>

    <!-- 메인 컨텐츠 -->
    <main class="user-main-content">
      <!-- 사이드바 -->
      <aside class="user-sidebar" :class="{ collapsed: sidebarCollapsed }">
        <div class="sidebar-toggle" @click="toggleSidebar">
          <i class="fas fa-bars"></i>
        </div>
        
        <div class="sidebar-content">
          <div class="sidebar-section">
            <h3 class="sidebar-title">학습 메뉴</h3>
            <ul class="sidebar-menu">
              <li><a href="#" class="menu-link active"><i class="fas fa-play-circle"></i> 진행중인 강의</a></li>
              <li><a href="#" class="menu-link"><i class="fas fa-bookmark"></i> 북마크</a></li>
              <li><a href="#" class="menu-link"><i class="fas fa-certificate"></i> 수료증</a></li>
              <li><a href="#" class="menu-link"><i class="fas fa-download"></i> 다운로드</a></li>
            </ul>
          </div>
          
          <div class="sidebar-section">
            <h3 class="sidebar-title">카테고리</h3>
            <ul class="sidebar-menu">
              <li><a href="#" class="menu-link"><i class="fas fa-code"></i> 프로그래밍</a></li>
              <li><a href="#" class="menu-link"><i class="fas fa-palette"></i> 디자인</a></li>
              <li><a href="#" class="menu-link"><i class="fas fa-chart-bar"></i> 데이터 분석</a></li>
              <li><a href="#" class="menu-link"><i class="fas fa-mobile-alt"></i> 모바일</a></li>
            </ul>
          </div>
        </div>
      </aside>

      <!-- 컨텐츠 영역 -->
      <div class="content-area">
        <!-- 대시보드 섹션 -->
        <div v-if="selectedMenu.name === 'dashboard'" class="dashboard-section">
          <div class="welcome-banner">
            <h2>안녕하세요, 김학습님! 👋</h2>
            <p>오늘도 즐거운 학습을 시작해보세요</p>
          </div>
          
          <div class="stats-grid">
            <div class="stat-card">
              <div class="stat-icon">
                <i class="fas fa-book"></i>
              </div>
              <div class="stat-content">
                <h3>진행중인 강의</h3>
                <span class="stat-number">3</span>
              </div>
            </div>
            <div class="stat-card">
              <div class="stat-icon">
                <i class="fas fa-clock"></i>
              </div>
              <div class="stat-content">
                <h3>학습 시간</h3>
                <span class="stat-number">24시간</span>
              </div>
            </div>
            <div class="stat-card">
              <div class="stat-icon">
                <i class="fas fa-trophy"></i>
              </div>
              <div class="stat-content">
                <h3>완료한 강의</h3>
                <span class="stat-number">12</span>
              </div>
            </div>
            <div class="stat-card">
              <div class="stat-icon">
                <i class="fas fa-star"></i>
              </div>
              <div class="stat-content">
                <h3>학습 점수</h3>
                <span class="stat-number">4.8</span>
              </div>
            </div>
          </div>
          
          <div class="recent-courses">
            <h3 class="section-title">최근 학습 강의</h3>
            <div class="courses-grid">
              <div class="course-card">
                <div class="course-thumbnail">
                  <img src="https://via.placeholder.com/300x200" alt="강의 썸네일">
                  <div class="course-progress">
                    <div class="progress-bar">
                      <div class="progress-fill" style="width: 65%"></div>
                    </div>
                    <span class="progress-text">65%</span>
                  </div>
                </div>
                <div class="course-info">
                  <h4>Vue.js 완벽 가이드</h4>
                  <p>프론트엔드 개발의 모든 것</p>
                  <div class="course-meta">
                    <span class="course-duration"><i class="fas fa-clock"></i> 15시간</span>
                    <span class="course-level">중급</span>
                  </div>
                </div>
              </div>
              
              <div class="course-card">
                <div class="course-thumbnail">
                  <img src="https://via.placeholder.com/300x200" alt="강의 썸네일">
                  <div class="course-progress">
                    <div class="progress-bar">
                      <div class="progress-fill" style="width: 30%"></div>
                    </div>
                    <span class="progress-text">30%</span>
                  </div>
                </div>
                <div class="course-info">
                  <h4>React Native 앱 개발</h4>
                  <p>모바일 앱을 만들어보자</p>
                  <div class="course-meta">
                    <span class="course-duration"><i class="fas fa-clock"></i> 20시간</span>
                    <span class="course-level">고급</span>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- 강의 섹션 -->
        <div v-if="selectedMenu.name === 'courses'" class="courses-section">
          <div class="section-header">
            <h3 class="section-title">전체 강의</h3>
            <div class="filter-controls">
              <select class="filter-select">
                <option>전체 카테고리</option>
                <option>프로그래밍</option>
                <option>디자인</option>
                <option>데이터 분석</option>
              </select>
              <select class="filter-select">
                <option>전체 레벨</option>
                <option>초급</option>
                <option>중급</option>
                <option>고급</option>
              </select>
            </div>
          </div>
          
          <div class="courses-grid">
            <div class="course-card">
              <div class="course-thumbnail">
                <img src="https://via.placeholder.com/300x200" alt="강의 썸네일">
                <div class="course-badge">인기</div>
              </div>
              <div class="course-info">
                <h4>JavaScript 기초부터 실전까지</h4>
                <p>웹 개발의 핵심 언어를 마스터하세요</p>
                <div class="course-meta">
                  <span class="course-rating">
                    <i class="fas fa-star"></i>
                    <i class="fas fa-star"></i>
                    <i class="fas fa-star"></i>
                    <i class="fas fa-star"></i>
                    <i class="fas fa-star"></i>
                    4.9
                  </span>
                  <span class="course-students">1,234명 수강</span>
                </div>
                <div class="course-price">₩89,000</div>
              </div>
            </div>
          </div>
        </div>

        <!-- 레이아웃 선택 섹션 -->
        <div v-if="selectedMenu.name === 'layouts'" class="layouts-section">
          <div class="section-header">
            <h3 class="section-title">학습 화면 선택</h3>
            <p class="section-description">다양한 디자인의 학습 화면을 선택하여 확인해보세요</p>
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
    <footer class="user-footer">
      <div class="footer-content">
        <div class="footer-section">
          <h4>SpringWorks</h4>
          <p>최고의 온라인 학습 플랫폼</p>
        </div>
        <div class="footer-section">
          <h4>고객지원</h4>
          <ul>
            <li><a href="#">자주 묻는 질문</a></li>
            <li><a href="#">문의하기</a></li>
            <li><a href="#">이용약관</a></li>
          </ul>
        </div>
        <div class="footer-section">
          <h4>학습 도움</h4>
          <ul>
            <li><a href="#">학습 가이드</a></li>
            <li><a href="#">기술 지원</a></li>
            <li><a href="#">커뮤니티</a></li>
          </ul>
        </div>
      </div>
      <div class="footer-bottom">
        <p>&copy; 2024 SpringWorks. All rights reserved.</p>
      </div>
    </footer>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import './common.css'

const sidebarCollapsed = ref(false)
const selectedMenu = ref({ name: 'dashboard' })

const menus = [
  { name: 'dashboard', label: '대시보드', icon: 'fas fa-home' },
  { name: 'courses', label: '강의', icon: 'fas fa-book' },
  { name: 'layouts', label: '화면 선택', icon: 'fas fa-palette' }
]

const availableLayouts = ref([
  {
    name: 'modern-learning',
    title: '모던 학습 화면',
    description: '깔끔하고 현대적인 학습 인터페이스',
    icon: 'fas fa-graduation-cap',
    gradient: 'linear-gradient(135deg, #667eea 0%, #764ba2 100%)',
    features: ['반응형', '다크모드', '학습진도']
  },
  {
    name: 'mobile-learning',
    title: '모바일 학습 화면',
    description: '모바일에 최적화된 학습 경험',
    icon: 'fas fa-mobile-alt',
    gradient: 'linear-gradient(135deg, #f093fb 0%, #f5576c 100%)',
    features: ['모바일 최적화', '터치 친화적', '오프라인']
  },
  {
    name: 'gamified-learning',
    title: '게임화 학습 화면',
    description: '게임 요소가 가미된 재미있는 학습',
    icon: 'fas fa-gamepad',
    gradient: 'linear-gradient(135deg, #4facfe 0%, #00f2fe 100%)',
    features: ['게임화', '포인트 시스템', '뱃지']
  },
  {
    name: 'minimal-learning',
    title: '미니멀 학습 화면',
    description: '집중할 수 있는 심플한 디자인',
    icon: 'fas fa-book-open',
    gradient: 'linear-gradient(135deg, #a8edea 0%, #fed6e3 100%)',
    features: ['미니멀', '집중도', '깔끔함']
  },
  {
    name: 'social-learning',
    title: '소셜 학습 화면',
    description: '다른 학습자와 소통하며 학습',
    icon: 'fas fa-users',
    gradient: 'linear-gradient(135deg, #ffecd2 0%, #fcb69f 100%)',
    features: ['소셜 기능', '토론', '협업']
  }
])

const toggleSidebar = () => {
  sidebarCollapsed.value = !sidebarCollapsed.value
}

const selectMenu = (menu) => {
  selectedMenu.value = menu
}

const openLayout = (layout) => {
  const layoutUrl = `/learning/user-layouts/${layout.name}`
  window.open(layoutUrl, '_blank')
}
</script>

<style scoped>
.user-main-layout {
  min-height: 100vh;
  background: #f8fafc;
  display: flex;
  flex-direction: column;
}

/* 헤더 스타일 */
.user-header {
  background: white;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
  position: sticky;
  top: 0;
  z-index: 100;
}

.header-content {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 20px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 70px;
}

.logo-section {
  display: flex;
  align-items: center;
  gap: 10px;
}

.logo {
  font-size: 24px;
  font-weight: bold;
  color: var(--primary-color);
  margin: 0;
}

.logo-subtitle {
  color: #64748b;
  font-size: 14px;
}

.user-nav {
  display: flex;
  gap: 30px;
}

.nav-link {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 10px 15px;
  border-radius: 8px;
  text-decoration: none;
  color: #64748b;
  font-weight: 500;
  transition: all 0.3s ease;
}

.nav-link:hover,
.nav-link.active {
  background: var(--primary-color);
  color: white;
}

.user-actions {
  display: flex;
  align-items: center;
  gap: 20px;
}

.search-box {
  display: flex;
  align-items: center;
  background: #f1f5f9;
  border-radius: 25px;
  padding: 8px 15px;
}

.search-input {
  border: none;
  background: none;
  outline: none;
  padding: 5px 10px;
  width: 200px;
}

.search-btn {
  background: none;
  border: none;
  color: #64748b;
  cursor: pointer;
}

.user-profile {
  display: flex;
  align-items: center;
  gap: 10px;
}

.profile-img {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  object-fit: cover;
}

.user-name {
  font-weight: 500;
  color: #1e293b;
}

/* 메인 컨텐츠 */
.user-main-content {
  flex: 1;
  display: flex;
  max-width: 1200px;
  margin: 0 auto;
  width: 100%;
  padding: 20px;
  gap: 20px;
}

/* 사이드바 */
.user-sidebar {
  width: 280px;
  background: white;
  border-radius: 12px;
  padding: 20px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
  height: fit-content;
  position: sticky;
  top: 90px;
  transition: all 0.3s ease;
}

.user-sidebar.collapsed {
  width: 60px;
  padding: 20px 10px;
}

.sidebar-toggle {
  display: none;
  position: absolute;
  top: 10px;
  right: 10px;
  background: var(--primary-color);
  color: white;
  border: none;
  border-radius: 4px;
  padding: 8px;
  cursor: pointer;
}

.sidebar-content {
  display: flex;
  flex-direction: column;
  gap: 30px;
}

.sidebar-section {
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.sidebar-title {
  font-size: 14px;
  font-weight: 600;
  color: #64748b;
  text-transform: uppercase;
  letter-spacing: 0.5px;
  margin: 0;
}

.sidebar-menu {
  list-style: none;
  padding: 0;
  margin: 0;
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.menu-link {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 12px 15px;
  border-radius: 8px;
  text-decoration: none;
  color: #64748b;
  font-weight: 500;
  transition: all 0.3s ease;
}

.menu-link:hover,
.menu-link.active {
  background: var(--primary-color);
  color: white;
}

/* 컨텐츠 영역 */
.content-area {
  flex: 1;
  background: white;
  border-radius: 12px;
  padding: 30px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
}

/* 대시보드 섹션 */
.welcome-banner {
  background: linear-gradient(135deg, var(--primary-color) 0%, #667eea 100%);
  color: white;
  padding: 30px;
  border-radius: 12px;
  margin-bottom: 30px;
  text-align: center;
}

.welcome-banner h2 {
  margin: 0 0 10px 0;
  font-size: 28px;
}

.welcome-banner p {
  margin: 0;
  opacity: 0.9;
  font-size: 16px;
}

.stats-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 20px;
  margin-bottom: 40px;
}

.stat-card {
  background: white;
  border: 1px solid #e2e8f0;
  border-radius: 12px;
  padding: 25px;
  display: flex;
  align-items: center;
  gap: 20px;
  transition: all 0.3s ease;
}

.stat-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 8px 25px rgba(0,0,0,0.1);
}

.stat-icon {
  width: 60px;
  height: 60px;
  background: var(--primary-color);
  border-radius: 12px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  font-size: 24px;
}

.stat-content h3 {
  margin: 0 0 8px 0;
  font-size: 14px;
  color: #64748b;
  font-weight: 500;
}

.stat-number {
  font-size: 32px;
  font-weight: bold;
  color: #1e293b;
}

/* 강의 카드 */
.courses-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 25px;
}

.course-card {
  background: white;
  border: 1px solid #e2e8f0;
  border-radius: 12px;
  overflow: hidden;
  transition: all 0.3s ease;
}

.course-card:hover {
  transform: translateY(-4px);
  box-shadow: 0 12px 30px rgba(0,0,0,0.15);
}

.course-thumbnail {
  position: relative;
  height: 200px;
  overflow: hidden;
}

.course-thumbnail img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.course-progress {
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
  background: rgba(0,0,0,0.7);
  padding: 10px 15px;
  display: flex;
  align-items: center;
  gap: 10px;
}

.progress-bar {
  flex: 1;
  height: 4px;
  background: rgba(255,255,255,0.3);
  border-radius: 2px;
  overflow: hidden;
}

.progress-fill {
  height: 100%;
  background: #10b981;
  transition: width 0.3s ease;
}

.progress-text {
  color: white;
  font-size: 12px;
  font-weight: 500;
}

.course-badge {
  position: absolute;
  top: 10px;
  right: 10px;
  background: #ef4444;
  color: white;
  padding: 4px 8px;
  border-radius: 4px;
  font-size: 12px;
  font-weight: 500;
}

.course-info {
  padding: 20px;
}

.course-info h4 {
  margin: 0 0 8px 0;
  font-size: 18px;
  color: #1e293b;
  font-weight: 600;
}

.course-info p {
  margin: 0 0 15px 0;
  color: #64748b;
  font-size: 14px;
  line-height: 1.5;
}

.course-meta {
  display: flex;
  align-items: center;
  gap: 15px;
  margin-bottom: 15px;
}

.course-duration,
.course-level {
  display: flex;
  align-items: center;
  gap: 5px;
  font-size: 12px;
  color: #64748b;
}

.course-level {
  background: #f1f5f9;
  padding: 4px 8px;
  border-radius: 4px;
}

.course-rating {
  display: flex;
  align-items: center;
  gap: 5px;
  font-size: 14px;
  color: #f59e0b;
}

.course-students {
  font-size: 12px;
  color: #64748b;
}

.course-price {
  font-size: 20px;
  font-weight: bold;
  color: var(--primary-color);
}

/* 레이아웃 섹션 */
.layouts-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 25px;
}

.layout-card {
  background: white;
  border: 1px solid #e2e8f0;
  border-radius: 12px;
  overflow: hidden;
  transition: all 0.3s ease;
}

.layout-card:hover {
  transform: translateY(-4px);
  box-shadow: 0 12px 30px rgba(0,0,0,0.15);
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
  font-size: 48px;
  color: white;
}

.preview-overlay {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0,0,0,0.7);
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
  padding: 20px;
}

.layout-title {
  margin: 0 0 8px 0;
  font-size: 18px;
  color: #1e293b;
  font-weight: 600;
}

.layout-description {
  margin: 0 0 15px 0;
  color: #64748b;
  font-size: 14px;
  line-height: 1.5;
}

.layout-features {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
}

.feature-tag {
  background: #f1f5f9;
  color: #64748b;
  padding: 4px 8px;
  border-radius: 4px;
  font-size: 12px;
  font-weight: 500;
}

/* 푸터 */
.user-footer {
  background: #1e293b;
  color: white;
  margin-top: auto;
}

.footer-content {
  max-width: 1200px;
  margin: 0 auto;
  padding: 40px 20px;
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 40px;
}

.footer-section h4 {
  margin: 0 0 15px 0;
  font-size: 18px;
  font-weight: 600;
}

.footer-section p {
  margin: 0 0 15px 0;
  color: #94a3b8;
  line-height: 1.6;
}

.footer-section ul {
  list-style: none;
  padding: 0;
  margin: 0;
}

.footer-section ul li {
  margin-bottom: 8px;
}

.footer-section ul li a {
  color: #94a3b8;
  text-decoration: none;
  transition: color 0.3s ease;
}

.footer-section ul li a:hover {
  color: white;
}

.footer-bottom {
  border-top: 1px solid #334155;
  padding: 20px;
  text-align: center;
  color: #94a3b8;
}

/* 반응형 */
@media (max-width: 768px) {
  .header-content {
    flex-direction: column;
    height: auto;
    padding: 15px 20px;
    gap: 15px;
  }
  
  .user-nav {
    order: 3;
    width: 100%;
    justify-content: center;
    gap: 15px;
  }
  
  .user-actions {
    order: 2;
    width: 100%;
    justify-content: space-between;
  }
  
  .search-box {
    flex: 1;
    max-width: 300px;
  }
  
  .user-main-content {
    flex-direction: column;
    padding: 15px;
  }
  
  .user-sidebar {
    width: 100%;
    position: static;
  }
  
  .sidebar-toggle {
    display: block;
  }
  
  .stats-grid {
    grid-template-columns: 1fr;
  }
  
  .courses-grid {
    grid-template-columns: 1fr;
  }
  
  .layouts-grid {
    grid-template-columns: 1fr;
  }
}
</style>

