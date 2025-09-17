<template>
  <div class="minimal-learning-layout">
    <!-- 상단 헤더 -->
    <header class="minimal-header">
      <div class="header-content">
        <div class="logo-section">
          <h1 class="logo">Learn</h1>
        </div>
        <div class="header-actions">
          <button class="header-btn">
            <i class="fas fa-search"></i>
          </button>
          <button class="header-btn">
            <i class="fas fa-bell"></i>
          </button>
          <div class="user-profile">
            <img src="https://randomuser.me/api/portraits/women/44.jpg" alt="User" class="user-avatar">
          </div>
        </div>
      </div>
    </header>

    <!-- 메인 콘텐츠 -->
    <main class="minimal-main">
      <div class="content-container">
        <!-- 학습 진행률 -->
        <section class="progress-section">
          <div class="progress-card">
            <h2 class="section-title">학습 진행률</h2>
            <div class="progress-stats">
              <div class="progress-item">
                <div class="progress-circle">
                  <div class="progress-value">75%</div>
                </div>
                <div class="progress-label">전체 진행률</div>
              </div>
              <div class="progress-item">
                <div class="progress-circle">
                  <div class="progress-value">3/5</div>
                </div>
                <div class="progress-label">완료된 강의</div>
              </div>
            </div>
          </div>
        </section>

        <!-- 최근 학습 -->
        <section class="recent-section">
          <h2 class="section-title">최근 학습</h2>
          <div class="course-list">
            <div v-for="course in recentCourses" :key="course.id" class="course-item">
              <div class="course-thumbnail">
                <img :src="course.thumbnail" :alt="course.title">
              </div>
              <div class="course-info">
                <h3 class="course-title">{{ course.title }}</h3>
                <p class="course-description">{{ course.description }}</p>
                <div class="course-meta">
                  <span class="course-duration">{{ course.duration }}</span>
                  <span class="course-progress">{{ course.progress }}%</span>
                </div>
              </div>
              <div class="course-actions">
                <button class="btn btn-primary">계속하기</button>
              </div>
            </div>
          </div>
        </section>

        <!-- 추천 강의 -->
        <section class="recommended-section">
          <h2 class="section-title">추천 강의</h2>
          <div class="course-grid">
            <div v-for="course in recommendedCourses" :key="course.id" class="course-card">
              <div class="course-thumbnail">
                <img :src="course.thumbnail" :alt="course.title">
                <div class="course-overlay">
                  <button class="btn btn-primary">시작하기</button>
                </div>
              </div>
              <div class="course-info">
                <h3 class="course-title">{{ course.title }}</h3>
                <p class="course-instructor">{{ course.instructor }}</p>
                <div class="course-rating">
                  <div class="stars">
                    <i v-for="star in 5" :key="star" class="fas fa-star" :class="{ 'filled': star <= course.rating }"></i>
                  </div>
                  <span class="rating-text">{{ course.rating }}/5</span>
                </div>
              </div>
            </div>
          </div>
        </section>
      </div>
    </main>

    <!-- 하단 네비게이션 -->
    <nav class="bottom-nav">
      <a href="#" class="nav-item active">
        <i class="fas fa-home"></i>
        <span>홈</span>
      </a>
      <a href="#" class="nav-item">
        <i class="fas fa-book"></i>
        <span>강의</span>
      </a>
      <a href="#" class="nav-item">
        <i class="fas fa-chart-line"></i>
        <span>진행률</span>
      </a>
      <a href="#" class="nav-item">
        <i class="fas fa-user"></i>
        <span>프로필</span>
      </a>
    </nav>
  </div>
</template>

<script>
export default {
  name: 'MinimalLearningLayout',
  data() {
    return {
      recentCourses: [
        {
          id: 1,
          title: 'Vue.js 기초부터 실전까지',
          description: 'Vue.js의 핵심 개념을 배우고 실전 프로젝트를 만들어보세요',
          thumbnail: 'https://picsum.photos/300/200?random=1',
          duration: '2시간 30분',
          progress: 75
        },
        {
          id: 2,
          title: 'JavaScript ES6+ 완벽 가이드',
          description: '최신 JavaScript 문법과 기능들을 마스터하세요',
          thumbnail: 'https://picsum.photos/300/200?random=2',
          duration: '3시간 15분',
          progress: 45
        }
      ],
      recommendedCourses: [
        {
          id: 3,
          title: 'React 완전 정복',
          description: 'React의 모든 것을 배우고 전문가가 되세요',
          thumbnail: 'https://picsum.photos/300/200?random=3',
          instructor: '김개발',
          rating: 4.8
        },
        {
          id: 4,
          title: 'Node.js 백엔드 개발',
          description: '서버 사이드 개발의 모든 것을 배우세요',
          thumbnail: 'https://picsum.photos/300/200?random=4',
          instructor: '박서버',
          rating: 4.9
        },
        {
          id: 5,
          title: 'Python 데이터 분석',
          description: '데이터 사이언스의 첫 걸음을 시작하세요',
          thumbnail: 'https://picsum.photos/300/200?random=5',
          instructor: '이데이터',
          rating: 4.7
        }
      ]
    }
  }
}
</script>

<style scoped>
/* ============================================
   MINIMAL LEARNING LAYOUT - ESSENTIAL STYLES ONLY
   ============================================ */

/* Layout Structure */
.minimal-learning-layout {
  min-height: 100vh;
  background: #fafafa;
  display: flex;
  flex-direction: column;
}

/* Header */
.minimal-header {
  background: white;
  border-bottom: 1px solid #e0e0e0;
  padding: 0 20px;
  position: sticky;
  top: 0;
  z-index: 100;
}

.header-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 60px;
  max-width: 1200px;
  margin: 0 auto;
}

.logo-section .logo {
  font-size: 24px;
  font-weight: 700;
  color: #333;
  margin: 0;
}

.header-actions {
  display: flex;
  align-items: center;
  gap: 15px;
}

.header-btn {
  background: none;
  border: none;
  color: #666;
  font-size: 18px;
  cursor: pointer;
  padding: 8px;
  border-radius: 50%;
  transition: all 0.2s ease;
}

.header-btn:hover {
  background: #f5f5f5;
  color: #333;
}

.user-avatar {
  width: 32px;
  height: 32px;
  border-radius: 50%;
  object-fit: cover;
}

/* Main Content */
.minimal-main {
  flex: 1;
  padding: 30px 20px;
}

.content-container {
  max-width: 1200px;
  margin: 0 auto;
}

/* Progress Section */
.progress-section {
  margin-bottom: 40px;
}

.progress-card {
  background: white;
  border-radius: 12px;
  padding: 30px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05);
}

.progress-stats {
  display: flex;
  gap: 40px;
  margin-top: 20px;
}

.progress-item {
  text-align: center;
}

.progress-circle {
  width: 80px;
  height: 80px;
  border-radius: 50%;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  display: flex;
  align-items: center;
  justify-content: center;
  margin: 0 auto 10px;
}

.progress-value {
  color: white;
  font-size: 18px;
  font-weight: 700;
}

.progress-label {
  color: #666;
  font-size: 14px;
}

/* Recent Section */
.recent-section {
  margin-bottom: 40px;
}

.course-list {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.course-item {
  background: white;
  border-radius: 12px;
  padding: 20px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05);
  display: flex;
  gap: 20px;
  align-items: center;
}

.course-thumbnail {
  width: 120px;
  height: 80px;
  border-radius: 8px;
  overflow: hidden;
  flex-shrink: 0;
}

.course-thumbnail img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.course-info {
  flex: 1;
}

.course-title {
  font-size: 18px;
  font-weight: 600;
  color: #333;
  margin: 0 0 8px 0;
}

.course-description {
  color: #666;
  font-size: 14px;
  margin: 0 0 12px 0;
  line-height: 1.4;
}

.course-meta {
  display: flex;
  gap: 20px;
  font-size: 12px;
  color: #999;
}

.course-actions {
  flex-shrink: 0;
}

/* Recommended Section */
.recommended-section {
  margin-bottom: 40px;
}

.course-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
  gap: 20px;
}

.course-card {
  background: white;
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05);
  transition: transform 0.2s ease, box-shadow 0.2s ease;
}

.course-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
}

.course-card .course-thumbnail {
  position: relative;
  width: 100%;
  height: 160px;
}

.course-overlay {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
  opacity: 0;
  transition: opacity 0.2s ease;
}

.course-card:hover .course-overlay {
  opacity: 1;
}

.course-card .course-info {
  padding: 20px;
}

.course-instructor {
  color: #666;
  font-size: 14px;
  margin: 8px 0;
}

.course-rating {
  display: flex;
  align-items: center;
  gap: 8px;
  margin-top: 12px;
}

.stars {
  display: flex;
  gap: 2px;
}

.stars i {
  color: #ddd;
  font-size: 12px;
}

.stars i.filled {
  color: #ffc107;
}

.rating-text {
  color: #666;
  font-size: 12px;
}

/* Bottom Navigation */
.bottom-nav {
  background: white;
  border-top: 1px solid #e0e0e0;
  display: flex;
  justify-content: space-around;
  padding: 10px 0;
  position: sticky;
  bottom: 0;
  z-index: 100;
}

.nav-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 4px;
  text-decoration: none;
  color: #666;
  font-size: 12px;
  padding: 8px;
  border-radius: 8px;
  transition: all 0.2s ease;
}

.nav-item:hover,
.nav-item.active {
  color: #667eea;
  background: #f0f2ff;
}

.nav-item i {
  font-size: 18px;
}

/* Responsive */
@media (max-width: 768px) {
  .minimal-main {
    padding: 20px 15px;
  }
  
  .progress-stats {
    flex-direction: column;
    gap: 20px;
  }
  
  .course-item {
    flex-direction: column;
    text-align: center;
  }
  
  .course-thumbnail {
    width: 100%;
    height: 120px;
  }
  
  .course-grid {
    grid-template-columns: 1fr;
  }
}
</style>