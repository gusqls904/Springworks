<template>
  <div class="mobile-learning-layout">
    <!-- 모바일 헤더 -->
    <header class="mobile-header">
      <div class="header-top">
        <button class="menu-btn" @click="toggleMenu">
          <i class="fas fa-bars"></i>
        </button>
        <div class="logo">
          <i class="fas fa-mobile-alt"></i>
          <span>SpringWorks</span>
        </div>
        <div class="header-actions">
          <button class="search-btn">
            <i class="fas fa-search"></i>
          </button>
          <div class="profile-btn">
            <img src="https://via.placeholder.com/32" alt="프로필" class="profile-img">
          </div>
        </div>
      </div>
      
      <!-- 검색바 -->
      <div class="search-bar" v-if="showSearch">
        <input type="text" placeholder="강의 검색..." class="search-input">
        <button class="search-submit">
          <i class="fas fa-search"></i>
        </button>
      </div>
    </header>

    <!-- 모바일 네비게이션 -->
    <nav class="mobile-nav" :class="{ active: menuOpen }">
      <div class="nav-content">
        <div class="nav-header">
          <h3>메뉴</h3>
          <button class="close-btn" @click="toggleMenu">
            <i class="fas fa-times"></i>
          </button>
        </div>
        
        <div class="nav-items">
          <a href="#" class="nav-item active">
            <i class="fas fa-home"></i>
            <span>홈</span>
          </a>
          <a href="#" class="nav-item">
            <i class="fas fa-play-circle"></i>
            <span>진행중인 강의</span>
          </a>
          <a href="#" class="nav-item">
            <i class="fas fa-bookmark"></i>
            <span>북마크</span>
          </a>
          <a href="#" class="nav-item">
            <i class="fas fa-chart-line"></i>
            <span>학습현황</span>
          </a>
          <a href="#" class="nav-item">
            <i class="fas fa-user"></i>
            <span>마이페이지</span>
          </a>
        </div>
      </div>
    </nav>

    <!-- 오버레이 -->
    <div class="overlay" :class="{ active: menuOpen }" @click="toggleMenu"></div>

    <!-- 메인 컨텐츠 -->
    <main class="mobile-main">
      <!-- 환영 섹션 -->
      <section class="welcome-section">
        <div class="welcome-content">
          <h1>안녕하세요! 👋</h1>
          <p>김학습님, 오늘도 즐거운 학습을 시작해보세요</p>
        </div>
        <div class="welcome-actions">
          <button class="action-btn primary">
            <i class="fas fa-play"></i>
            학습 시작
          </button>
          <button class="action-btn secondary">
            <i class="fas fa-search"></i>
            강의 찾기
          </button>
        </div>
      </section>

      <!-- 학습 통계 -->
      <section class="stats-section">
        <h2 class="section-title">학습 현황</h2>
        <div class="stats-grid">
          <div class="stat-card">
            <div class="stat-icon">
              <i class="fas fa-book"></i>
            </div>
            <div class="stat-content">
              <span class="stat-number">3</span>
              <span class="stat-label">진행중</span>
            </div>
          </div>
          <div class="stat-card">
            <div class="stat-icon">
              <i class="fas fa-clock"></i>
            </div>
            <div class="stat-content">
              <span class="stat-number">24</span>
              <span class="stat-label">시간</span>
            </div>
          </div>
          <div class="stat-card">
            <div class="stat-icon">
              <i class="fas fa-trophy"></i>
            </div>
            <div class="stat-content">
              <span class="stat-number">12</span>
              <span class="stat-label">완료</span>
            </div>
          </div>
          <div class="stat-card">
            <div class="stat-icon">
              <i class="fas fa-star"></i>
            </div>
            <div class="stat-content">
              <span class="stat-number">4.8</span>
              <span class="stat-label">점수</span>
            </div>
          </div>
        </div>
      </section>

      <!-- 진행중인 강의 -->
      <section class="current-courses">
        <div class="section-header">
          <h2 class="section-title">진행중인 강의</h2>
          <a href="#" class="see-all">전체보기</a>
        </div>
        
        <div class="courses-list">
          <div class="course-card">
            <div class="course-image">
              <img src="https://via.placeholder.com/300x200" alt="강의 썸네일">
              <div class="play-overlay">
                <button class="play-btn">
                  <i class="fas fa-play"></i>
                </button>
              </div>
            </div>
            <div class="course-content">
              <h3>Vue.js 완벽 가이드</h3>
              <p>프론트엔드 개발의 모든 것</p>
              <div class="course-progress">
                <div class="progress-info">
                  <span class="progress-text">65% 완료</span>
                  <span class="progress-time">남은 시간: 5시간</span>
                </div>
                <div class="progress-bar">
                  <div class="progress-fill" style="width: 65%"></div>
                </div>
              </div>
              <div class="course-meta">
                <span class="course-duration">
                  <i class="fas fa-clock"></i>
                  15시간
                </span>
                <span class="course-level">중급</span>
              </div>
            </div>
          </div>

          <div class="course-card">
            <div class="course-image">
              <img src="https://via.placeholder.com/300x200" alt="강의 썸네일">
              <div class="play-overlay">
                <button class="play-btn">
                  <i class="fas fa-play"></i>
                </button>
              </div>
            </div>
            <div class="course-content">
              <h3>React Native 앱 개발</h3>
              <p>모바일 앱을 만들어보자</p>
              <div class="course-progress">
                <div class="progress-info">
                  <span class="progress-text">30% 완료</span>
                  <span class="progress-time">남은 시간: 14시간</span>
                </div>
                <div class="progress-bar">
                  <div class="progress-fill" style="width: 30%"></div>
                </div>
              </div>
              <div class="course-meta">
                <span class="course-duration">
                  <i class="fas fa-clock"></i>
                  20시간
                </span>
                <span class="course-level">고급</span>
              </div>
            </div>
          </div>
        </div>
      </section>

      <!-- 추천 강의 -->
      <section class="recommended-courses">
        <div class="section-header">
          <h2 class="section-title">추천 강의</h2>
          <a href="#" class="see-all">전체보기</a>
        </div>
        
        <div class="courses-horizontal">
          <div class="course-item">
            <img src="https://via.placeholder.com/120x80" alt="강의 썸네일" class="course-thumb">
            <div class="course-info">
              <h4>JavaScript 기초</h4>
              <div class="course-rating">
                <i class="fas fa-star"></i>
                <span>4.9</span>
              </div>
              <div class="course-price">₩89,000</div>
            </div>
          </div>

          <div class="course-item">
            <img src="https://via.placeholder.com/120x80" alt="강의 썸네일" class="course-thumb">
            <div class="course-info">
              <h4>Python 데이터 분석</h4>
              <div class="course-rating">
                <i class="fas fa-star"></i>
                <span>4.8</span>
              </div>
              <div class="course-price">₩79,000</div>
            </div>
          </div>

          <div class="course-item">
            <img src="https://via.placeholder.com/120x80" alt="강의 썸네일" class="course-thumb">
            <div class="course-info">
              <h4>UI/UX 디자인</h4>
              <div class="course-rating">
                <i class="fas fa-star"></i>
                <span>4.7</span>
              </div>
              <div class="course-price">₩99,000</div>
            </div>
          </div>
        </div>
      </section>

      <!-- 하단 네비게이션 -->
      <nav class="bottom-nav">
        <a href="#" class="nav-item active">
          <i class="fas fa-home"></i>
          <span>홈</span>
        </a>
        <a href="#" class="nav-item">
          <i class="fas fa-play-circle"></i>
          <span>학습</span>
        </a>
        <a href="#" class="nav-item">
          <i class="fas fa-bookmark"></i>
          <span>북마크</span>
        </a>
        <a href="#" class="nav-item">
          <i class="fas fa-chart-line"></i>
          <span>현황</span>
        </a>
        <a href="#" class="nav-item">
          <i class="fas fa-user"></i>
          <span>마이</span>
        </a>
      </nav>
    </main>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import '../common.css'

const menuOpen = ref(false)
const showSearch = ref(false)

const toggleMenu = () => {
  menuOpen.value = !menuOpen.value
}
</script>

<style scoped>
.mobile-learning-layout {
  min-height: 100vh;
  background: #f8fafc;
  font-family: 'Inter', -apple-system, BlinkMacSystemFont, sans-serif;
  position: relative;
}

/* 모바일 헤더 */
.mobile-header {
  background: white;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  position: sticky;
  top: 0;
  z-index: 100;
}

.header-top {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 15px 20px;
  height: 60px;
}

.menu-btn {
  background: none;
  border: none;
  font-size: 20px;
  color: #64748b;
  cursor: pointer;
  padding: 8px;
}

.logo {
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 20px;
  font-weight: bold;
  color: var(--primary-color);
}

.logo i {
  font-size: 22px;
}

.header-actions {
  display: flex;
  align-items: center;
  gap: 15px;
}

.search-btn {
  background: none;
  border: none;
  font-size: 18px;
  color: #64748b;
  cursor: pointer;
  padding: 8px;
}

.profile-btn {
  cursor: pointer;
}

.profile-img {
  width: 32px;
  height: 32px;
  border-radius: 50%;
  object-fit: cover;
}

.search-bar {
  display: flex;
  align-items: center;
  padding: 10px 20px;
  background: #f8fafc;
  border-top: 1px solid #e2e8f0;
}

.search-input {
  flex: 1;
  border: none;
  background: none;
  outline: none;
  padding: 8px 12px;
  font-size: 16px;
}

.search-submit {
  background: var(--primary-color);
  color: white;
  border: none;
  padding: 8px 12px;
  border-radius: 6px;
  cursor: pointer;
}

/* 모바일 네비게이션 */
.mobile-nav {
  position: fixed;
  top: 0;
  left: -300px;
  width: 300px;
  height: 100vh;
  background: white;
  z-index: 200;
  transition: left 0.3s ease;
  box-shadow: 2px 0 10px rgba(0, 0, 0, 0.1);
}

.mobile-nav.active {
  left: 0;
}

.nav-content {
  padding: 20px;
  height: 100%;
  display: flex;
  flex-direction: column;
}

.nav-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 30px;
  padding-bottom: 20px;
  border-bottom: 1px solid #e2e8f0;
}

.nav-header h3 {
  margin: 0;
  font-size: 20px;
  color: #1e293b;
}

.close-btn {
  background: none;
  border: none;
  font-size: 20px;
  color: #64748b;
  cursor: pointer;
  padding: 8px;
}

.nav-items {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.nav-item {
  display: flex;
  align-items: center;
  gap: 15px;
  padding: 15px 20px;
  border-radius: 12px;
  text-decoration: none;
  color: #64748b;
  font-weight: 500;
  transition: all 0.3s ease;
}

.nav-item:hover,
.nav-item.active {
  background: var(--primary-color);
  color: white;
}

.nav-item i {
  font-size: 18px;
  width: 20px;
  text-align: center;
}

/* 오버레이 */
.overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.5);
  z-index: 150;
  opacity: 0;
  visibility: hidden;
  transition: all 0.3s ease;
}

.overlay.active {
  opacity: 1;
  visibility: visible;
}

/* 메인 컨텐츠 */
.mobile-main {
  padding: 20px;
  padding-bottom: 80px;
}

/* 환영 섹션 */
.welcome-section {
  background: linear-gradient(135deg, var(--primary-color) 0%, #667eea 100%);
  border-radius: 16px;
  padding: 25px;
  margin-bottom: 25px;
  color: white;
  text-align: center;
}

.welcome-content h1 {
  margin: 0 0 10px 0;
  font-size: 24px;
  font-weight: 700;
}

.welcome-content p {
  margin: 0 0 20px 0;
  font-size: 16px;
  opacity: 0.9;
}

.welcome-actions {
  display: flex;
  gap: 10px;
  justify-content: center;
}

.action-btn {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 12px 20px;
  border-radius: 25px;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.3s ease;
  border: none;
}

.action-btn.primary {
  background: white;
  color: var(--primary-color);
}

.action-btn.secondary {
  background: rgba(255, 255, 255, 0.2);
  color: white;
  border: 1px solid rgba(255, 255, 255, 0.3);
}

.action-btn:hover {
  transform: translateY(-2px);
}

/* 통계 섹션 */
.section-title {
  margin: 0 0 20px 0;
  font-size: 20px;
  font-weight: 700;
  color: #1e293b;
}

.stats-section {
  margin-bottom: 30px;
}

.stats-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 15px;
}

.stat-card {
  background: white;
  border: 1px solid #e2e8f0;
  border-radius: 12px;
  padding: 20px;
  display: flex;
  align-items: center;
  gap: 15px;
  transition: all 0.3s ease;
}

.stat-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 8px 25px rgba(0, 0, 0, 0.1);
}

.stat-icon {
  width: 50px;
  height: 50px;
  background: var(--primary-color);
  border-radius: 12px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  font-size: 20px;
}

.stat-content {
  display: flex;
  flex-direction: column;
  gap: 5px;
}

.stat-number {
  font-size: 24px;
  font-weight: 700;
  color: #1e293b;
}

.stat-label {
  font-size: 12px;
  color: #64748b;
  font-weight: 500;
}

/* 강의 섹션 */
.section-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.see-all {
  color: var(--primary-color);
  text-decoration: none;
  font-size: 14px;
  font-weight: 500;
}

.current-courses,
.recommended-courses {
  margin-bottom: 30px;
}

.courses-list {
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.course-card {
  background: white;
  border: 1px solid #e2e8f0;
  border-radius: 12px;
  overflow: hidden;
  transition: all 0.3s ease;
}

.course-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 8px 25px rgba(0, 0, 0, 0.1);
}

.course-image {
  position: relative;
  height: 150px;
  overflow: hidden;
}

.course-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.play-overlay {
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

.course-card:hover .play-overlay {
  opacity: 1;
}

.play-btn {
  width: 50px;
  height: 50px;
  background: white;
  border: none;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  color: var(--primary-color);
  font-size: 18px;
  cursor: pointer;
  transition: all 0.3s ease;
}

.play-btn:hover {
  transform: scale(1.1);
}

.course-content {
  padding: 20px;
}

.course-content h3 {
  margin: 0 0 8px 0;
  font-size: 16px;
  font-weight: 600;
  color: #1e293b;
}

.course-content p {
  margin: 0 0 15px 0;
  color: #64748b;
  font-size: 14px;
}

.course-progress {
  margin-bottom: 15px;
}

.progress-info {
  display: flex;
  justify-content: space-between;
  margin-bottom: 8px;
  font-size: 12px;
}

.progress-text {
  color: #1e293b;
  font-weight: 500;
}

.progress-time {
  color: #64748b;
}

.progress-bar {
  height: 4px;
  background: #e2e8f0;
  border-radius: 2px;
  overflow: hidden;
}

.progress-fill {
  height: 100%;
  background: linear-gradient(90deg, var(--primary-color), #10b981);
  transition: width 0.3s ease;
}

.course-meta {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.course-duration {
  display: flex;
  align-items: center;
  gap: 5px;
  font-size: 12px;
  color: #64748b;
}

.course-level {
  background: #f1f5f9;
  color: #64748b;
  padding: 4px 8px;
  border-radius: 4px;
  font-size: 12px;
  font-weight: 500;
}

/* 추천 강의 */
.courses-horizontal {
  display: flex;
  gap: 15px;
  overflow-x: auto;
  padding-bottom: 10px;
}

.course-item {
  min-width: 140px;
  background: white;
  border: 1px solid #e2e8f0;
  border-radius: 12px;
  overflow: hidden;
  transition: all 0.3s ease;
}

.course-item:hover {
  transform: translateY(-2px);
  box-shadow: 0 8px 25px rgba(0, 0, 0, 0.1);
}

.course-thumb {
  width: 100%;
  height: 80px;
  object-fit: cover;
}

.course-info {
  padding: 12px;
}

.course-info h4 {
  margin: 0 0 8px 0;
  font-size: 14px;
  font-weight: 600;
  color: #1e293b;
  line-height: 1.3;
}

.course-rating {
  display: flex;
  align-items: center;
  gap: 3px;
  margin-bottom: 8px;
  font-size: 12px;
  color: #f59e0b;
}

.course-price {
  font-size: 14px;
  font-weight: 600;
  color: var(--primary-color);
}

/* 하단 네비게이션 */
.bottom-nav {
  position: fixed;
  bottom: 0;
  left: 0;
  right: 0;
  background: white;
  border-top: 1px solid #e2e8f0;
  display: flex;
  justify-content: space-around;
  padding: 10px 0;
  z-index: 100;
}

.bottom-nav .nav-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 4px;
  padding: 8px 12px;
  text-decoration: none;
  color: #64748b;
  font-size: 12px;
  font-weight: 500;
  transition: all 0.3s ease;
  border-radius: 8px;
}

.bottom-nav .nav-item:hover,
.bottom-nav .nav-item.active {
  color: var(--primary-color);
  background: rgba(102, 126, 234, 0.1);
}

.bottom-nav .nav-item i {
  font-size: 18px;
}

/* 반응형 */
@media (max-width: 480px) {
  .mobile-main {
    padding: 15px;
  }
  
  .welcome-actions {
    flex-direction: column;
  }
  
  .stats-grid {
    grid-template-columns: 1fr;
  }
  
  .courses-horizontal {
    gap: 10px;
  }
  
  .course-item {
    min-width: 120px;
  }
}
</style>

