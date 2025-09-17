<template>
  <div class="social-learning-layout">
    <!-- 상단 헤더 -->
    <header class="social-header">
      <div class="header-content">
        <div class="logo-section">
          <h1 class="logo">LearnTogether</h1>
          <span class="tagline">함께 배우고 성장하세요</span>
        </div>
        <div class="header-actions">
          <div class="search-box">
            <input type="text" placeholder="강의, 스터디, 친구 검색..." class="search-input">
            <button class="search-btn">
              <i class="fas fa-search"></i>
            </button>
          </div>
          <button class="header-btn notification-btn">
            <i class="fas fa-bell"></i>
            <span class="notification-badge">5</span>
          </button>
          <div class="user-profile">
            <img src="https://randomuser.me/api/portraits/men/25.jpg" alt="User" class="user-avatar">
            <div class="user-info">
              <span class="user-name">김학습</span>
              <span class="user-level">Level 12</span>
            </div>
          </div>
        </div>
      </div>
    </header>

    <!-- 메인 콘텐츠 -->
    <main class="social-main">
      <div class="content-container">
        <!-- 학습 통계 -->
        <section class="stats-section">
          <div class="stats-grid">
            <div class="stat-card">
              <div class="stat-icon">
                <i class="fas fa-fire"></i>
              </div>
              <div class="stat-content">
                <div class="stat-value">7일 연속</div>
                <div class="stat-label">학습 스트릭</div>
              </div>
            </div>
            <div class="stat-card">
              <div class="stat-icon">
                <i class="fas fa-users"></i>
              </div>
              <div class="stat-content">
                <div class="stat-value">1,234</div>
                <div class="stat-label">학습 친구</div>
              </div>
            </div>
            <div class="stat-card">
              <div class="stat-icon">
                <i class="fas fa-trophy"></i>
              </div>
              <div class="stat-content">
                <div class="stat-value">15</div>
                <div class="stat-label">획득 배지</div>
              </div>
            </div>
            <div class="stat-card">
              <div class="stat-icon">
                <i class="fas fa-chart-line"></i>
              </div>
              <div class="stat-content">
                <div class="stat-value">85%</div>
                <div class="stat-label">전체 진행률</div>
              </div>
            </div>
          </div>
        </section>

        <!-- 피드 섹션 -->
        <section class="feed-section">
          <div class="feed-header">
            <h2 class="section-title">학습 피드</h2>
            <div class="feed-tabs">
              <button class="tab-btn active">전체</button>
              <button class="tab-btn">팔로잉</button>
              <button class="tab-btn">인기</button>
            </div>
          </div>
          
          <div class="feed-content">
            <div v-for="post in feedPosts" :key="post.id" class="feed-post">
              <div class="post-header">
                <div class="post-author">
                  <img :src="post.author.avatar" :alt="post.author.name" class="author-avatar">
                  <div class="author-info">
                    <span class="author-name">{{ post.author.name }}</span>
                    <span class="post-time">{{ post.time }}</span>
                  </div>
                </div>
                <button class="post-menu">
                  <i class="fas fa-ellipsis-h"></i>
                </button>
              </div>
              
              <div class="post-content">
                <p class="post-text">{{ post.content }}</p>
                <div v-if="post.image" class="post-image">
                  <img :src="post.image" :alt="post.content">
                </div>
                <div v-if="post.course" class="post-course">
                  <div class="course-info">
                    <img :src="post.course.thumbnail" :alt="post.course.title" class="course-thumb">
                    <div class="course-details">
                      <h4 class="course-title">{{ post.course.title }}</h4>
                      <p class="course-progress">{{ post.course.progress }}% 완료</p>
                    </div>
                  </div>
                </div>
              </div>
              
              <div class="post-actions">
                <button class="action-btn like-btn" :class="{ active: post.liked }">
                  <i class="fas fa-heart"></i>
                  <span>{{ post.likes }}</span>
                </button>
                <button class="action-btn comment-btn">
                  <i class="fas fa-comment"></i>
                  <span>{{ post.comments }}</span>
                </button>
                <button class="action-btn share-btn">
                  <i class="fas fa-share"></i>
                  <span>공유</span>
                </button>
              </div>
            </div>
          </div>
        </section>

        <!-- 스터디 그룹 -->
        <section class="study-groups-section">
          <div class="section-header">
            <h2 class="section-title">추천 스터디 그룹</h2>
            <button class="btn btn-primary">모든 그룹 보기</button>
          </div>
          
          <div class="groups-grid">
            <div v-for="group in studyGroups" :key="group.id" class="group-card">
              <div class="group-header">
                <div class="group-avatar">
                  <img :src="group.avatar" :alt="group.name">
                </div>
                <div class="group-info">
                  <h3 class="group-name">{{ group.name }}</h3>
                  <p class="group-description">{{ group.description }}</p>
                </div>
                <div class="group-members">
                  <div class="member-avatars">
                    <img v-for="member in group.members.slice(0, 3)" :key="member.id" 
                         :src="member.avatar" :alt="member.name" class="member-avatar">
                    <span v-if="group.members.length > 3" class="more-members">+{{ group.members.length - 3 }}</span>
                  </div>
                  <span class="member-count">{{ group.members.length }}명</span>
                </div>
              </div>
              <div class="group-tags">
                <span v-for="tag in group.tags" :key="tag" class="group-tag">{{ tag }}</span>
              </div>
              <div class="group-actions">
                <button class="btn btn-secondary">자세히 보기</button>
                <button class="btn btn-primary">참여하기</button>
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
        <i class="fas fa-users"></i>
        <span>스터디</span>
      </a>
      <a href="#" class="nav-item">
        <i class="fas fa-book"></i>
        <span>강의</span>
      </a>
      <a href="#" class="nav-item">
        <i class="fas fa-trophy"></i>
        <span>랭킹</span>
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
  name: 'SocialLearningLayout',
  data() {
    return {
      feedPosts: [
        {
          id: 1,
          author: {
            name: '박개발',
            avatar: 'https://randomuser.me/api/portraits/men/32.jpg'
          },
          time: '2시간 전',
          content: 'Vue.js 강의를 완주했습니다! 정말 유익한 내용이었어요. 다음은 React를 배워볼 예정입니다.',
          image: 'https://picsum.photos/400/300?random=1',
          course: {
            title: 'Vue.js 완전 정복',
            thumbnail: 'https://picsum.photos/100/60?random=1',
            progress: 100
          },
          likes: 24,
          comments: 8,
          liked: true
        },
        {
          id: 2,
          author: {
            name: '이학습',
            avatar: 'https://randomuser.me/api/portraits/women/28.jpg'
          },
          time: '4시간 전',
          content: 'JavaScript 알고리즘 문제를 풀어봤습니다. 어려웠지만 포기하지 않고 끝까지 해냈어요!',
          course: {
            title: 'JavaScript 알고리즘',
            thumbnail: 'https://picsum.photos/100/60?random=2',
            progress: 75
          },
          likes: 18,
          comments: 5,
          liked: false
        },
        {
          id: 3,
          author: {
            name: '김코딩',
            avatar: 'https://randomuser.me/api/portraits/men/45.jpg'
          },
          time: '6시간 전',
          content: '스터디 그룹에서 함께 프로젝트를 진행하고 있습니다. 팀워크가 정말 중요하다는 걸 느꼈어요.',
          image: 'https://picsum.photos/400/300?random=3',
          likes: 31,
          comments: 12,
          liked: true
        }
      ],
      studyGroups: [
        {
          id: 1,
          name: 'Vue.js 마스터',
          description: 'Vue.js를 함께 배우고 실전 프로젝트를 진행하는 그룹입니다.',
          avatar: 'https://picsum.photos/80/80?random=1',
          members: [
            { id: 1, name: '김학습', avatar: 'https://randomuser.me/api/portraits/men/25.jpg' },
            { id: 2, name: '박개발', avatar: 'https://randomuser.me/api/portraits/men/32.jpg' },
            { id: 3, name: '이코딩', avatar: 'https://randomuser.me/api/portraits/women/28.jpg' },
            { id: 4, name: '최프론트', avatar: 'https://randomuser.me/api/portraits/men/35.jpg' }
          ],
          tags: ['Vue.js', '프론트엔드', '초급']
        },
        {
          id: 2,
          name: 'JavaScript 알고리즘',
          description: '알고리즘 문제를 함께 풀고 해결 방법을 공유하는 그룹입니다.',
          avatar: 'https://picsum.photos/80/80?random=2',
          members: [
            { id: 1, name: '김학습', avatar: 'https://randomuser.me/api/portraits/men/25.jpg' },
            { id: 2, name: '박개발', avatar: 'https://randomuser.me/api/portraits/men/32.jpg' }
          ],
          tags: ['JavaScript', '알고리즘', '중급']
        },
        {
          id: 3,
          name: 'React 실전 프로젝트',
          description: 'React로 실제 서비스를 만들어보는 그룹입니다.',
          avatar: 'https://picsum.photos/80/80?random=3',
          members: [
            { id: 1, name: '김학습', avatar: 'https://randomuser.me/api/portraits/men/25.jpg' },
            { id: 2, name: '박개발', avatar: 'https://randomuser.me/api/portraits/men/32.jpg' },
            { id: 3, name: '이코딩', avatar: 'https://randomuser.me/api/portraits/women/28.jpg' }
          ],
          tags: ['React', '프로젝트', '고급']
        }
      ]
    }
  }
}
</script>

<style scoped>
/* ============================================
   SOCIAL LEARNING LAYOUT - ESSENTIAL STYLES ONLY
   ============================================ */

/* Layout Structure */
.social-learning-layout {
  min-height: 100vh;
  background: #f8fafc;
  display: flex;
  flex-direction: column;
}

/* Header */
.social-header {
  background: white;
  border-bottom: 1px solid #e2e8f0;
  padding: 0 20px;
  position: sticky;
  top: 0;
  z-index: 100;
}

.header-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 70px;
  max-width: 1200px;
  margin: 0 auto;
}

.logo-section .logo {
  font-size: 24px;
  font-weight: 700;
  color: #1e293b;
  margin: 0;
}

.logo-section .tagline {
  font-size: 12px;
  color: #64748b;
  margin-left: 10px;
}

.header-actions {
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
  min-width: 300px;
}

.search-input {
  border: none;
  background: none;
  outline: none;
  flex: 1;
  font-size: 14px;
  color: #334155;
}

.search-input::placeholder {
  color: #94a3b8;
}

.search-btn {
  background: none;
  border: none;
  color: #64748b;
  cursor: pointer;
  padding: 5px;
}

.notification-btn {
  position: relative;
  background: none;
  border: none;
  color: #64748b;
  font-size: 18px;
  cursor: pointer;
  padding: 8px;
  border-radius: 50%;
  transition: all 0.2s ease;
}

.notification-btn:hover {
  background: #f1f5f9;
  color: #334155;
}

.notification-badge {
  position: absolute;
  top: 0;
  right: 0;
  background: #ef4444;
  color: white;
  font-size: 10px;
  padding: 2px 6px;
  border-radius: 10px;
  min-width: 18px;
  text-align: center;
}

.user-profile {
  display: flex;
  align-items: center;
  gap: 10px;
  cursor: pointer;
  padding: 5px;
  border-radius: 8px;
  transition: background 0.2s ease;
}

.user-profile:hover {
  background: #f1f5f9;
}

.user-avatar {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  object-fit: cover;
}

.user-info {
  display: flex;
  flex-direction: column;
}

.user-name {
  font-size: 14px;
  font-weight: 600;
  color: #1e293b;
}

.user-level {
  font-size: 12px;
  color: #64748b;
}

/* Main Content */
.social-main {
  flex: 1;
  padding: 30px 20px;
}

.content-container {
  max-width: 1200px;
  margin: 0 auto;
}

/* Stats Section */
.stats-section {
  margin-bottom: 40px;
}

.stats-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 20px;
}

.stat-card {
  background: white;
  border-radius: 16px;
  padding: 25px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.05);
  display: flex;
  align-items: center;
  gap: 15px;
  transition: transform 0.2s ease;
}

.stat-card:hover {
  transform: translateY(-2px);
}

.stat-icon {
  width: 50px;
  height: 50px;
  border-radius: 12px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
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
  font-size: 14px;
  color: #64748b;
}

/* Feed Section */
.feed-section {
  margin-bottom: 40px;
}

.feed-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 30px;
}

.feed-tabs {
  display: flex;
  gap: 10px;
}

.tab-btn {
  background: none;
  border: none;
  padding: 8px 16px;
  border-radius: 20px;
  color: #64748b;
  cursor: pointer;
  transition: all 0.2s ease;
}

.tab-btn.active {
  background: #667eea;
  color: white;
}

.feed-content {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.feed-post {
  background: white;
  border-radius: 16px;
  padding: 25px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.05);
}

.post-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 15px;
}

.post-author {
  display: flex;
  align-items: center;
  gap: 12px;
}

.author-avatar {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  object-fit: cover;
}

.author-name {
  font-size: 16px;
  font-weight: 600;
  color: #1e293b;
}

.post-time {
  font-size: 12px;
  color: #64748b;
}

.post-menu {
  background: none;
  border: none;
  color: #64748b;
  cursor: pointer;
  padding: 5px;
  border-radius: 4px;
}

.post-content {
  margin-bottom: 20px;
}

.post-text {
  font-size: 16px;
  color: #334155;
  line-height: 1.6;
  margin-bottom: 15px;
}

.post-image {
  border-radius: 12px;
  overflow: hidden;
  margin-bottom: 15px;
}

.post-image img {
  width: 100%;
  height: auto;
  display: block;
}

.post-course {
  background: #f8fafc;
  border-radius: 12px;
  padding: 15px;
  border: 1px solid #e2e8f0;
}

.course-info {
  display: flex;
  align-items: center;
  gap: 12px;
}

.course-thumb {
  width: 60px;
  height: 40px;
  border-radius: 8px;
  object-fit: cover;
}

.course-title {
  font-size: 14px;
  font-weight: 600;
  color: #1e293b;
  margin: 0 0 4px 0;
}

.course-progress {
  font-size: 12px;
  color: #64748b;
  margin: 0;
}

.post-actions {
  display: flex;
  gap: 20px;
  padding-top: 15px;
  border-top: 1px solid #e2e8f0;
}

.action-btn {
  background: none;
  border: none;
  color: #64748b;
  cursor: pointer;
  display: flex;
  align-items: center;
  gap: 6px;
  padding: 8px 12px;
  border-radius: 8px;
  transition: all 0.2s ease;
}

.action-btn:hover {
  background: #f1f5f9;
  color: #334155;
}

.action-btn.active {
  color: #ef4444;
}

/* Study Groups Section */
.study-groups-section {
  margin-bottom: 40px;
}

.section-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 30px;
}

.groups-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(350px, 1fr));
  gap: 20px;
}

.group-card {
  background: white;
  border-radius: 16px;
  padding: 25px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.05);
  transition: transform 0.2s ease;
}

.group-card:hover {
  transform: translateY(-2px);
}

.group-header {
  display: flex;
  align-items: flex-start;
  gap: 15px;
  margin-bottom: 15px;
}

.group-avatar {
  width: 50px;
  height: 50px;
  border-radius: 12px;
  overflow: hidden;
  flex-shrink: 0;
}

.group-avatar img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.group-info {
  flex: 1;
}

.group-name {
  font-size: 18px;
  font-weight: 600;
  color: #1e293b;
  margin: 0 0 8px 0;
}

.group-description {
  font-size: 14px;
  color: #64748b;
  line-height: 1.5;
  margin: 0;
}

.group-members {
  display: flex;
  flex-direction: column;
  align-items: flex-end;
  gap: 8px;
}

.member-avatars {
  display: flex;
  align-items: center;
  gap: -5px;
}

.member-avatar {
  width: 24px;
  height: 24px;
  border-radius: 50%;
  object-fit: cover;
  border: 2px solid white;
  margin-left: -5px;
}

.member-avatar:first-child {
  margin-left: 0;
}

.more-members {
  font-size: 12px;
  color: #64748b;
  margin-left: 5px;
}

.member-count {
  font-size: 12px;
  color: #64748b;
}

.group-tags {
  display: flex;
  gap: 8px;
  margin-bottom: 20px;
  flex-wrap: wrap;
}

.group-tag {
  background: #e0f2fe;
  color: #0369a1;
  font-size: 12px;
  padding: 4px 8px;
  border-radius: 12px;
}

.group-actions {
  display: flex;
  gap: 10px;
  justify-content: flex-end;
}

/* Bottom Navigation */
.bottom-nav {
  background: white;
  border-top: 1px solid #e2e8f0;
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
  color: #64748b;
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
  .social-main {
    padding: 20px 15px;
  }
  
  .header-actions {
    gap: 10px;
  }
  
  .search-box {
    min-width: 200px;
  }
  
  .user-info {
    display: none;
  }
  
  .stats-grid {
    grid-template-columns: repeat(2, 1fr);
  }
  
  .groups-grid {
    grid-template-columns: 1fr;
  }
  
  .group-header {
    flex-direction: column;
    align-items: flex-start;
  }
  
  .group-members {
    align-items: flex-start;
  }
}
</style>
