<template>
  <div class="modern-learning-layout">
    <!-- 헤더 -->
    <header class="learning-header">
      <div class="header-content">
        <div class="logo">
          <i class="fas fa-graduation-cap"></i>
          <span>SpringWorks</span>
        </div>
        <div class="header-actions">
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
    <main class="learning-main">
      <!-- 사이드바 -->
      <aside class="learning-sidebar">
        <nav class="sidebar-nav">
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
        </nav>
      </aside>

      <!-- 컨텐츠 영역 -->
      <div class="content-area">
        <!-- 대시보드 -->
        <div class="dashboard">
          <div class="welcome-section">
            <h1>안녕하세요, 김학습님! 👋</h1>
            <p>오늘도 즐거운 학습을 시작해보세요</p>
          </div>

          <!-- 학습 통계 -->
          <div class="stats-section">
            <div class="stat-card">
              <div class="stat-icon">
                <i class="fas fa-book"></i>
              </div>
              <div class="stat-content">
                <h3>진행중인 강의</h3>
                <span class="stat-number">3</span>
                <span class="stat-label">강의</span>
              </div>
            </div>
            <div class="stat-card">
              <div class="stat-icon">
                <i class="fas fa-clock"></i>
              </div>
              <div class="stat-content">
                <h3>학습 시간</h3>
                <span class="stat-number">24</span>
                <span class="stat-label">시간</span>
              </div>
            </div>
            <div class="stat-card">
              <div class="stat-icon">
                <i class="fas fa-trophy"></i>
              </div>
              <div class="stat-content">
                <h3>완료한 강의</h3>
                <span class="stat-number">12</span>
                <span class="stat-label">강의</span>
              </div>
            </div>
            <div class="stat-card">
              <div class="stat-icon">
                <i class="fas fa-star"></i>
              </div>
              <div class="stat-content">
                <h3>학습 점수</h3>
                <span class="stat-number">4.8</span>
                <span class="stat-label">점</span>
              </div>
            </div>
          </div>

          <!-- 최근 강의 -->
          <div class="recent-courses">
            <h2 class="section-title">최근 학습 강의</h2>
            <div class="courses-grid">
              <div class="course-card">
                <div class="course-image">
                  <img src="https://via.placeholder.com/300x200" alt="강의 썸네일">
                  <div class="course-overlay">
                    <button class="play-btn">
                      <i class="fas fa-play"></i>
                    </button>
                  </div>
                </div>
                <div class="course-content">
                  <div class="course-header">
                    <h3>Vue.js 완벽 가이드</h3>
                    <div class="course-rating">
                      <i class="fas fa-star"></i>
                      <span>4.9</span>
                    </div>
                  </div>
                  <p class="course-description">프론트엔드 개발의 모든 것</p>
                  <div class="course-progress">
                    <div class="progress-bar">
                      <div class="progress-fill" style="width: 65%"></div>
                    </div>
                    <span class="progress-text">65% 완료</span>
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
                  <div class="course-overlay">
                    <button class="play-btn">
                      <i class="fas fa-play"></i>
                    </button>
                  </div>
                </div>
                <div class="course-content">
                  <div class="course-header">
                    <h3>React Native 앱 개발</h3>
                    <div class="course-rating">
                      <i class="fas fa-star"></i>
                      <span>4.8</span>
                    </div>
                  </div>
                  <p class="course-description">모바일 앱을 만들어보자</p>
                  <div class="course-progress">
                    <div class="progress-bar">
                      <div class="progress-fill" style="width: 30%"></div>
                    </div>
                    <span class="progress-text">30% 완료</span>
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
          </div>

          <!-- 추천 강의 -->
          <div class="recommended-courses">
            <h2 class="section-title">추천 강의</h2>
            <div class="courses-list">
              <div class="course-item">
                <img src="https://via.placeholder.com/80x60" alt="강의 썸네일" class="course-thumb">
                <div class="course-info">
                  <h4>JavaScript 기초부터 실전까지</h4>
                  <p>웹 개발의 핵심 언어를 마스터하세요</p>
                  <div class="course-stats">
                    <span class="rating">
                      <i class="fas fa-star"></i>
                      4.9
                    </span>
                    <span class="students">1,234명 수강</span>
                    <span class="price">₩89,000</span>
                  </div>
                </div>
                <button class="enroll-btn">수강신청</button>
              </div>

              <div class="course-item">
                <img src="https://via.placeholder.com/80x60" alt="강의 썸네일" class="course-thumb">
                <div class="course-info">
                  <h4>Python 데이터 분석</h4>
                  <p>데이터 사이언스의 첫 걸음</p>
                  <div class="course-stats">
                    <span class="rating">
                      <i class="fas fa-star"></i>
                      4.8
                    </span>
                    <span class="students">856명 수강</span>
                    <span class="price">₩79,000</span>
                  </div>
                </div>
                <button class="enroll-btn">수강신청</button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </main>
  </div>
</template>

<script setup>
import '../common.css'
</script>

<style scoped>
.modern-learning-layout {
  min-height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  font-family: 'Inter', -apple-system, BlinkMacSystemFont, sans-serif;
}

/* 헤더 */
.learning-header {
  background: rgba(255, 255, 255, 0.95);
  backdrop-filter: blur(10px);
  border-bottom: 1px solid rgba(255, 255, 255, 0.2);
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

.logo {
  display: flex;
  align-items: center;
  gap: 10px;
  font-size: 24px;
  font-weight: bold;
  color: var(--primary-color);
}

.logo i {
  font-size: 28px;
}

.header-actions {
  display: flex;
  align-items: center;
  gap: 20px;
}

.search-box {
  display: flex;
  align-items: center;
  background: #f8fafc;
  border-radius: 25px;
  padding: 8px 15px;
  border: 2px solid transparent;
  transition: all 0.3s ease;
}

.search-box:focus-within {
  border-color: var(--primary-color);
  box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.1);
}

.search-input {
  border: none;
  background: none;
  outline: none;
  padding: 5px 10px;
  width: 250px;
  font-size: 14px;
}

.search-btn {
  background: none;
  border: none;
  color: #64748b;
  cursor: pointer;
  padding: 5px;
}

.user-profile {
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 8px 15px;
  background: #f8fafc;
  border-radius: 25px;
  cursor: pointer;
  transition: all 0.3s ease;
}

.user-profile:hover {
  background: #e2e8f0;
}

.profile-img {
  width: 35px;
  height: 35px;
  border-radius: 50%;
  object-fit: cover;
}

.user-name {
  font-weight: 500;
  color: #1e293b;
}

/* 메인 컨텐츠 */
.learning-main {
  display: flex;
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
  gap: 20px;
}

/* 사이드바 */
.learning-sidebar {
  width: 250px;
  background: rgba(255, 255, 255, 0.95);
  backdrop-filter: blur(10px);
  border-radius: 16px;
  padding: 25px;
  height: fit-content;
  position: sticky;
  top: 90px;
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.1);
}

.sidebar-nav {
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
  position: relative;
}

.nav-item:hover {
  background: #f1f5f9;
  color: var(--primary-color);
}

.nav-item.active {
  background: var(--primary-color);
  color: white;
  box-shadow: 0 4px 12px rgba(102, 126, 234, 0.3);
}

.nav-item i {
  font-size: 18px;
  width: 20px;
  text-align: center;
}

/* 컨텐츠 영역 */
.content-area {
  flex: 1;
  background: rgba(255, 255, 255, 0.95);
  backdrop-filter: blur(10px);
  border-radius: 16px;
  padding: 30px;
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.1);
}

/* 대시보드 */
.dashboard {
  display: flex;
  flex-direction: column;
  gap: 30px;
}

.welcome-section {
  text-align: center;
  padding: 40px 20px;
  background: linear-gradient(135deg, var(--primary-color) 0%, #667eea 100%);
  border-radius: 16px;
  color: white;
}

.welcome-section h1 {
  margin: 0 0 10px 0;
  font-size: 32px;
  font-weight: 700;
}

.welcome-section p {
  margin: 0;
  font-size: 18px;
  opacity: 0.9;
}

/* 통계 섹션 */
.stats-section {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 20px;
}

.stat-card {
  background: white;
  border: 1px solid #e2e8f0;
  border-radius: 16px;
  padding: 25px;
  display: flex;
  align-items: center;
  gap: 20px;
  transition: all 0.3s ease;
  position: relative;
  overflow: hidden;
}

.stat-card::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  height: 4px;
  background: linear-gradient(90deg, var(--primary-color), #667eea);
}

.stat-card:hover {
  transform: translateY(-4px);
  box-shadow: 0 12px 30px rgba(0, 0, 0, 0.15);
}

.stat-icon {
  width: 60px;
  height: 60px;
  background: linear-gradient(135deg, var(--primary-color), #667eea);
  border-radius: 16px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  font-size: 24px;
}

.stat-content h3 {
  margin: 0 0 5px 0;
  font-size: 14px;
  color: #64748b;
  font-weight: 500;
}

.stat-number {
  font-size: 28px;
  font-weight: 700;
  color: #1e293b;
  display: block;
}

.stat-label {
  font-size: 12px;
  color: #64748b;
  font-weight: 500;
}

/* 강의 섹션 */
.section-title {
  margin: 0 0 20px 0;
  font-size: 24px;
  font-weight: 700;
  color: #1e293b;
}

.courses-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(350px, 1fr));
  gap: 25px;
}

.course-card {
  background: white;
  border: 1px solid #e2e8f0;
  border-radius: 16px;
  overflow: hidden;
  transition: all 0.3s ease;
  position: relative;
}

.course-card:hover {
  transform: translateY(-6px);
  box-shadow: 0 20px 40px rgba(0, 0, 0, 0.15);
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
  transition: transform 0.3s ease;
}

.course-card:hover .course-image img {
  transform: scale(1.05);
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

.play-btn {
  width: 60px;
  height: 60px;
  background: white;
  border: none;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  color: var(--primary-color);
  font-size: 20px;
  cursor: pointer;
  transition: all 0.3s ease;
}

.play-btn:hover {
  transform: scale(1.1);
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.3);
}

.course-content {
  padding: 25px;
}

.course-header {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  margin-bottom: 10px;
}

.course-header h3 {
  margin: 0;
  font-size: 18px;
  font-weight: 600;
  color: #1e293b;
  flex: 1;
}

.course-rating {
  display: flex;
  align-items: center;
  gap: 5px;
  color: #f59e0b;
  font-size: 14px;
  font-weight: 500;
}

.course-description {
  margin: 0 0 20px 0;
  color: #64748b;
  font-size: 14px;
  line-height: 1.5;
}

.course-progress {
  margin-bottom: 20px;
}

.progress-bar {
  height: 6px;
  background: #e2e8f0;
  border-radius: 3px;
  overflow: hidden;
  margin-bottom: 8px;
}

.progress-fill {
  height: 100%;
  background: linear-gradient(90deg, var(--primary-color), #10b981);
  transition: width 0.3s ease;
}

.progress-text {
  font-size: 12px;
  color: #64748b;
  font-weight: 500;
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
.courses-list {
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.course-item {
  display: flex;
  align-items: center;
  gap: 20px;
  padding: 20px;
  background: white;
  border: 1px solid #e2e8f0;
  border-radius: 12px;
  transition: all 0.3s ease;
}

.course-item:hover {
  transform: translateX(5px);
  box-shadow: 0 8px 25px rgba(0, 0, 0, 0.1);
}

.course-thumb {
  width: 80px;
  height: 60px;
  border-radius: 8px;
  object-fit: cover;
}

.course-info {
  flex: 1;
}

.course-info h4 {
  margin: 0 0 5px 0;
  font-size: 16px;
  font-weight: 600;
  color: #1e293b;
}

.course-info p {
  margin: 0 0 10px 0;
  color: #64748b;
  font-size: 14px;
}

.course-stats {
  display: flex;
  align-items: center;
  gap: 15px;
  font-size: 12px;
}

.rating {
  display: flex;
  align-items: center;
  gap: 3px;
  color: #f59e0b;
  font-weight: 500;
}

.students {
  color: #64748b;
}

.price {
  color: var(--primary-color);
  font-weight: 600;
  font-size: 14px;
}

.enroll-btn {
  background: var(--primary-color);
  color: white;
  border: none;
  padding: 10px 20px;
  border-radius: 8px;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.3s ease;
}

.enroll-btn:hover {
  background: #5a67d8;
  transform: translateY(-2px);
}

/* 반응형 */
@media (max-width: 768px) {
  .learning-main {
    flex-direction: column;
    padding: 15px;
  }
  
  .learning-sidebar {
    width: 100%;
    position: static;
  }
  
  .sidebar-nav {
    flex-direction: row;
    overflow-x: auto;
    gap: 10px;
  }
  
  .nav-item {
    white-space: nowrap;
    min-width: fit-content;
  }
  
  .stats-section {
    grid-template-columns: 1fr;
  }
  
  .courses-grid {
    grid-template-columns: 1fr;
  }
  
  .course-item {
    flex-direction: column;
    text-align: center;
  }
  
  .course-stats {
    justify-content: center;
  }
}
</style>

