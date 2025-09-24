<template>
  <div class="gamified-learning-layout">
    <!-- 게임화 헤더 -->
    <header class="game-header">
      <div class="header-content">
        <div class="logo-section">
          <div class="logo">
            <i class="fas fa-gamepad"></i>
            <span>SpringWorks</span>
          </div>
          <div class="level-info">
            <div class="level-badge">
              <i class="fas fa-crown"></i>
              <span>Level {{ userLevel }}</span>
            </div>
            <div class="xp-bar">
              <div class="xp-fill" :style="{ width: xpPercentage + '%' }"></div>
              <span class="xp-text">{{ currentXP }}/{{ maxXP }} XP</span>
            </div>
          </div>
        </div>
        
        <div class="game-stats">
          <div class="stat-item">
            <i class="fas fa-coins"></i>
            <span>{{ coins }}</span>
          </div>
          <div class="stat-item">
            <i class="fas fa-gem"></i>
            <span>{{ gems }}</span>
          </div>
          <div class="stat-item">
            <i class="fas fa-trophy"></i>
            <span>{{ streak }}일</span>
          </div>
        </div>
      </div>
    </header>

    <!-- 메인 컨텐츠 -->
    <main class="game-main">
      <!-- 사이드바 -->
      <aside class="game-sidebar">
        <div class="sidebar-header">
          <div class="user-avatar">
            <img src="https://via.placeholder.com/60" alt="아바타" class="avatar-img">
            <div class="avatar-level">{{ userLevel }}</div>
          </div>
          <div class="user-info">
            <h3>김학습</h3>
            <p>게이머</p>
          </div>
        </div>
        
        <nav class="game-nav">
          <a href="#" class="nav-item active">
            <i class="fas fa-home"></i>
            <span>홈</span>
            <div class="nav-badge">3</div>
          </a>
          <a href="#" class="nav-item">
            <i class="fas fa-play-circle"></i>
            <span>퀘스트</span>
            <div class="nav-badge new">NEW</div>
          </a>
          <a href="#" class="nav-item">
            <i class="fas fa-medal"></i>
            <span>업적</span>
            <div class="nav-badge">5</div>
          </a>
          <a href="#" class="nav-item">
            <i class="fas fa-users"></i>
            <span>길드</span>
          </a>
          <a href="#" class="nav-item">
            <i class="fas fa-store"></i>
            <span>상점</span>
          </a>
        </nav>
        
        <div class="achievements-preview">
          <h4>최근 업적</h4>
          <div class="achievement-item">
            <div class="achievement-icon">
              <i class="fas fa-fire"></i>
            </div>
            <div class="achievement-info">
              <h5>학습 연속 7일</h5>
              <p>+50 XP</p>
            </div>
          </div>
        </div>
      </aside>

      <!-- 컨텐츠 영역 -->
      <div class="content-area">
        <!-- 대시보드 -->
        <div class="game-dashboard">
          <!-- 환영 섹션 -->
          <div class="welcome-section">
            <div class="welcome-content">
              <h1>🎮 게임을 시작하세요!</h1>
              <p>오늘의 퀘스트를 완료하고 경험치를 획득하세요</p>
            </div>
            <div class="daily-bonus">
              <button class="bonus-btn" :class="{ claimed: dailyBonusClaimed }">
                <i class="fas fa-gift"></i>
                <span>일일 보너스</span>
                <div class="bonus-amount">+100 XP</div>
              </button>
            </div>
          </div>

          <!-- 오늘의 퀘스트 -->
          <div class="quests-section">
            <h2 class="section-title">
              <i class="fas fa-scroll"></i>
              오늘의 퀘스트
            </h2>
            <div class="quests-grid">
              <div class="quest-card" v-for="quest in dailyQuests" :key="quest.id">
                <div class="quest-header">
                  <div class="quest-icon">
                    <i :class="quest.icon"></i>
                  </div>
                  <div class="quest-info">
                    <h3>{{ quest.title }}</h3>
                    <p>{{ quest.description }}</p>
                  </div>
                  <div class="quest-reward">
                    <i class="fas fa-coins"></i>
                    <span>{{ quest.reward }}</span>
                  </div>
                </div>
                <div class="quest-progress">
                  <div class="progress-bar">
                    <div class="progress-fill" :style="{ width: quest.progress + '%' }"></div>
                  </div>
                  <span class="progress-text">{{ quest.current }}/{{ quest.target }}</span>
                </div>
                <button class="quest-btn" :class="{ completed: quest.completed }">
                  {{ quest.completed ? '완료!' : '진행하기' }}
                </button>
              </div>
            </div>
          </div>

          <!-- 진행중인 강의 -->
          <div class="courses-section">
            <h2 class="section-title">
              <i class="fas fa-book"></i>
              진행중인 강의
            </h2>
            <div class="courses-grid">
              <div class="course-card" v-for="course in courses" :key="course.id">
                <div class="course-image">
                  <img :src="course.thumbnail" :alt="course.title">
                  <div class="course-overlay">
                    <button class="play-btn">
                      <i class="fas fa-play"></i>
                    </button>
                    <div class="course-level">
                      <i class="fas fa-star"></i>
                      <span>{{ course.level }}</span>
                    </div>
                  </div>
                </div>
                <div class="course-content">
                  <h3>{{ course.title }}</h3>
                  <p>{{ course.description }}</p>
                  <div class="course-progress">
                    <div class="progress-bar">
                      <div class="progress-fill" :style="{ width: course.progress + '%' }"></div>
                    </div>
                    <span class="progress-text">{{ course.progress }}% 완료</span>
                  </div>
                  <div class="course-rewards">
                    <div class="reward-item">
                      <i class="fas fa-coins"></i>
                      <span>+{{ course.xpReward }} XP</span>
                    </div>
                    <div class="reward-item">
                      <i class="fas fa-gem"></i>
                      <span>+{{ course.gemReward }}</span>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <!-- 리더보드 -->
          <div class="leaderboard-section">
            <h2 class="section-title">
              <i class="fas fa-trophy"></i>
              이번 주 리더보드
            </h2>
            <div class="leaderboard">
              <div class="leaderboard-item" v-for="(player, index) in leaderboard" :key="player.id">
                <div class="rank">{{ index + 1 }}</div>
                <div class="player-info">
                  <img :src="player.avatar" :alt="player.name" class="player-avatar">
                  <div class="player-details">
                    <h4>{{ player.name }}</h4>
                    <p>Level {{ player.level }}</p>
                  </div>
                </div>
                <div class="player-stats">
                  <div class="stat">
                    <i class="fas fa-coins"></i>
                    <span>{{ player.points }}</span>
                  </div>
                  <div class="stat">
                    <i class="fas fa-fire"></i>
                    <span>{{ player.streak }}일</span>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </main>

    <!-- 레벨업 모달 -->
    <div class="levelup-modal" v-if="showLevelUp">
      <div class="modal-content">
        <div class="levelup-animation">
          <i class="fas fa-star"></i>
        </div>
        <h2>레벨 업! 🎉</h2>
        <p>축하합니다! Level {{ userLevel }}에 도달했습니다!</p>
        <div class="rewards">
          <div class="reward">
            <i class="fas fa-coins"></i>
            <span>+500 코인</span>
          </div>
          <div class="reward">
            <i class="fas fa-gem"></i>
            <span>+10 젬</span>
          </div>
        </div>
        <button class="continue-btn" @click="closeLevelUp">계속하기</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import '/src/styles/admin/main.css'

const userLevel = ref(15)
const currentXP = ref(1250)
const maxXP = ref(1500)
const coins = ref(2450)
const gems = ref(85)
const streak = ref(7)
const dailyBonusClaimed = ref(false)
const showLevelUp = ref(false)

const xpPercentage = computed(() => (currentXP.value / maxXP.value) * 100)

const dailyQuests = ref([
  {
    id: 1,
    title: '학습 시간 30분',
    description: '오늘 30분 이상 학습하세요',
    icon: 'fas fa-clock',
    current: 20,
    target: 30,
    progress: 67,
    reward: 100,
    completed: false
  },
  {
    id: 2,
    title: '강의 1개 완료',
    description: '강의를 하나 완료하세요',
    icon: 'fas fa-play-circle',
    current: 0,
    target: 1,
    progress: 0,
    reward: 200,
    completed: false
  },
  {
    id: 3,
    title: '연속 학습 7일',
    description: '7일 연속으로 학습하세요',
    icon: 'fas fa-fire',
    current: 7,
    target: 7,
    progress: 100,
    reward: 500,
    completed: true
  }
])

const courses = ref([
  {
    id: 1,
    title: 'Vue.js 완벽 가이드',
    description: '프론트엔드 개발의 모든 것',
    thumbnail: 'https://via.placeholder.com/300x200',
    progress: 65,
    level: '중급',
    xpReward: 150,
    gemReward: 5
  },
  {
    id: 2,
    title: 'React Native 앱 개발',
    description: '모바일 앱을 만들어보자',
    thumbnail: 'https://via.placeholder.com/300x200',
    progress: 30,
    level: '고급',
    xpReward: 200,
    gemReward: 8
  }
])

const leaderboard = ref([
  {
    id: 1,
    name: '김학습',
    avatar: 'https://via.placeholder.com/40',
    level: 15,
    points: 2450,
    streak: 7
  },
  {
    id: 2,
    name: '박개발',
    avatar: 'https://via.placeholder.com/40',
    level: 14,
    points: 2200,
    streak: 5
  },
  {
    id: 3,
    name: '이코딩',
    avatar: 'https://via.placeholder.com/40',
    level: 13,
    points: 1950,
    streak: 3
  }
])

const closeLevelUp = () => {
  showLevelUp.value = false
}
</script>

<style scoped>
.gamified-learning-layout {
  min-height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  font-family: 'Inter', -apple-system, BlinkMacSystemFont, sans-serif;
}

/* 게임화 헤더 */
.game-header {
  background: rgba(0, 0, 0, 0.8);
  backdrop-filter: blur(10px);
  border-bottom: 2px solid #ffd700;
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
  height: 80px;
}

.logo-section {
  display: flex;
  align-items: center;
  gap: 30px;
}

.logo {
  display: flex;
  align-items: center;
  gap: 10px;
  font-size: 24px;
  font-weight: bold;
  color: #ffd700;
}

.logo i {
  font-size: 28px;
}

.level-info {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.level-badge {
  display: flex;
  align-items: center;
  gap: 8px;
  background: linear-gradient(45deg, #ffd700, #ffed4e);
  color: #1a1a1a;
  padding: 6px 12px;
  border-radius: 20px;
  font-weight: bold;
  font-size: 14px;
}

.xp-bar {
  position: relative;
  width: 200px;
  height: 8px;
  background: rgba(255, 255, 255, 0.2);
  border-radius: 4px;
  overflow: hidden;
}

.xp-fill {
  height: 100%;
  background: linear-gradient(90deg, #ffd700, #ffed4e);
  transition: width 0.3s ease;
}

.xp-text {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  font-size: 10px;
  font-weight: bold;
  color: white;
  text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.5);
}

.game-stats {
  display: flex;
  gap: 20px;
}

.stat-item {
  display: flex;
  align-items: center;
  gap: 8px;
  background: rgba(255, 255, 255, 0.1);
  padding: 8px 15px;
  border-radius: 20px;
  color: white;
  font-weight: bold;
}

.stat-item i {
  font-size: 16px;
}

/* 메인 컨텐츠 */
.game-main {
  display: flex;
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
  gap: 20px;
}

/* 게임 사이드바 */
.game-sidebar {
  width: 280px;
  background: rgba(0, 0, 0, 0.8);
  backdrop-filter: blur(10px);
  border-radius: 16px;
  padding: 25px;
  height: fit-content;
  position: sticky;
  top: 100px;
  border: 2px solid #ffd700;
}

.sidebar-header {
  display: flex;
  align-items: center;
  gap: 15px;
  margin-bottom: 30px;
  padding-bottom: 20px;
  border-bottom: 1px solid rgba(255, 215, 0, 0.3);
}

.user-avatar {
  position: relative;
}

.avatar-img {
  width: 60px;
  height: 60px;
  border-radius: 50%;
  object-fit: cover;
  border: 3px solid #ffd700;
}

.avatar-level {
  position: absolute;
  bottom: -5px;
  right: -5px;
  background: #ffd700;
  color: #1a1a1a;
  width: 24px;
  height: 24px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 12px;
  font-weight: bold;
}

.user-info h3 {
  margin: 0 0 5px 0;
  color: white;
  font-size: 18px;
}

.user-info p {
  margin: 0;
  color: #ffd700;
  font-size: 14px;
}

.game-nav {
  display: flex;
  flex-direction: column;
  gap: 8px;
  margin-bottom: 30px;
}

.nav-item {
  display: flex;
  align-items: center;
  gap: 15px;
  padding: 15px 20px;
  border-radius: 12px;
  text-decoration: none;
  color: rgba(255, 255, 255, 0.7);
  font-weight: 500;
  transition: all 0.3s ease;
  position: relative;
}

.nav-item:hover,
.nav-item.active {
  background: rgba(255, 215, 0, 0.2);
  color: #ffd700;
}

.nav-item i {
  font-size: 18px;
  width: 20px;
  text-align: center;
}

.nav-badge {
  position: absolute;
  right: 15px;
  background: #ff6b6b;
  color: white;
  padding: 2px 6px;
  border-radius: 10px;
  font-size: 10px;
  font-weight: bold;
}

.nav-badge.new {
  background: #4ecdc4;
}

.achievements-preview {
  background: rgba(255, 215, 0, 0.1);
  border-radius: 12px;
  padding: 20px;
  border: 1px solid rgba(255, 215, 0, 0.3);
}

.achievements-preview h4 {
  margin: 0 0 15px 0;
  color: #ffd700;
  font-size: 16px;
}

.achievement-item {
  display: flex;
  align-items: center;
  gap: 12px;
}

.achievement-icon {
  width: 40px;
  height: 40px;
  background: #ffd700;
  border-radius: 8px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #1a1a1a;
  font-size: 16px;
}

.achievement-info h5 {
  margin: 0 0 5px 0;
  color: white;
  font-size: 14px;
}

.achievement-info p {
  margin: 0;
  color: #ffd700;
  font-size: 12px;
  font-weight: bold;
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
.game-dashboard {
  display: flex;
  flex-direction: column;
  gap: 30px;
}

/* 환영 섹션 */
.welcome-section {
  background: linear-gradient(135deg, #ffd700 0%, #ffed4e 100%);
  border-radius: 16px;
  padding: 30px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  color: #1a1a1a;
}

.welcome-content h1 {
  margin: 0 0 10px 0;
  font-size: 28px;
  font-weight: 700;
}

.welcome-content p {
  margin: 0;
  font-size: 16px;
  opacity: 0.8;
}

.daily-bonus {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 10px;
}

.bonus-btn {
  background: rgba(255, 255, 255, 0.9);
  border: 2px solid #1a1a1a;
  padding: 15px 25px;
  border-radius: 12px;
  cursor: pointer;
  transition: all 0.3s ease;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 5px;
  font-weight: bold;
}

.bonus-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.2);
}

.bonus-btn.claimed {
  background: #4ecdc4;
  color: white;
}

.bonus-amount {
  font-size: 14px;
  color: #ff6b6b;
  font-weight: bold;
}

/* 퀘스트 섹션 */
.section-title {
  display: flex;
  align-items: center;
  gap: 10px;
  margin: 0 0 20px 0;
  font-size: 24px;
  font-weight: 700;
  color: #1e293b;
}

.quests-section {
  background: white;
  border: 2px solid #e2e8f0;
  border-radius: 16px;
  padding: 25px;
}

.quests-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: 20px;
}

.quest-card {
  background: #f8fafc;
  border: 2px solid #e2e8f0;
  border-radius: 12px;
  padding: 20px;
  transition: all 0.3s ease;
}

.quest-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 8px 25px rgba(0, 0, 0, 0.1);
}

.quest-header {
  display: flex;
  align-items: center;
  gap: 15px;
  margin-bottom: 15px;
}

.quest-icon {
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

.quest-info {
  flex: 1;
}

.quest-info h3 {
  margin: 0 0 5px 0;
  font-size: 16px;
  color: #1e293b;
  font-weight: 600;
}

.quest-info p {
  margin: 0;
  color: #64748b;
  font-size: 14px;
}

.quest-reward {
  display: flex;
  align-items: center;
  gap: 5px;
  color: #f59e0b;
  font-weight: bold;
  font-size: 14px;
}

.quest-progress {
  margin-bottom: 15px;
}

.progress-bar {
  height: 8px;
  background: #e2e8f0;
  border-radius: 4px;
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

.quest-btn {
  width: 100%;
  background: var(--primary-color);
  color: white;
  border: none;
  padding: 12px;
  border-radius: 8px;
  font-weight: bold;
  cursor: pointer;
  transition: all 0.3s ease;
}

.quest-btn:hover {
  background: #5a67d8;
  transform: translateY(-2px);
}

.quest-btn.completed {
  background: #10b981;
}

/* 강의 섹션 */
.courses-section {
  background: white;
  border: 2px solid #e2e8f0;
  border-radius: 16px;
  padding: 25px;
}

.courses-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(350px, 1fr));
  gap: 25px;
}

.course-card {
  background: white;
  border: 2px solid #e2e8f0;
  border-radius: 16px;
  overflow: hidden;
  transition: all 0.3s ease;
}

.course-card:hover {
  transform: translateY(-4px);
  box-shadow: 0 12px 30px rgba(0, 0, 0, 0.15);
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

.play-btn {
  width: 60px;
  height: 60px;
  background: #ffd700;
  border: none;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #1a1a1a;
  font-size: 20px;
  cursor: pointer;
  transition: all 0.3s ease;
}

.play-btn:hover {
  transform: scale(1.1);
}

.course-level {
  position: absolute;
  top: 15px;
  right: 15px;
  background: rgba(0, 0, 0, 0.8);
  color: #ffd700;
  padding: 6px 12px;
  border-radius: 20px;
  font-size: 12px;
  font-weight: bold;
  display: flex;
  align-items: center;
  gap: 5px;
}

.course-content {
  padding: 25px;
}

.course-content h3 {
  margin: 0 0 10px 0;
  font-size: 18px;
  font-weight: 600;
  color: #1e293b;
}

.course-content p {
  margin: 0 0 20px 0;
  color: #64748b;
  font-size: 14px;
}

.course-progress {
  margin-bottom: 20px;
}

.course-rewards {
  display: flex;
  gap: 15px;
}

.reward-item {
  display: flex;
  align-items: center;
  gap: 5px;
  font-size: 12px;
  font-weight: bold;
}

.reward-item:first-child {
  color: #f59e0b;
}

.reward-item:last-child {
  color: #8b5cf6;
}

/* 리더보드 */
.leaderboard-section {
  background: white;
  border: 2px solid #e2e8f0;
  border-radius: 16px;
  padding: 25px;
}

.leaderboard {
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.leaderboard-item {
  display: flex;
  align-items: center;
  gap: 20px;
  padding: 20px;
  background: #f8fafc;
  border-radius: 12px;
  transition: all 0.3s ease;
}

.leaderboard-item:hover {
  transform: translateX(5px);
  box-shadow: 0 8px 25px rgba(0, 0, 0, 0.1);
}

.rank {
  width: 40px;
  height: 40px;
  background: #ffd700;
  color: #1a1a1a;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-weight: bold;
  font-size: 18px;
}

.player-info {
  display: flex;
  align-items: center;
  gap: 15px;
  flex: 1;
}

.player-avatar {
  width: 50px;
  height: 50px;
  border-radius: 50%;
  object-fit: cover;
  border: 2px solid #ffd700;
}

.player-details h4 {
  margin: 0 0 5px 0;
  color: #1e293b;
  font-size: 16px;
}

.player-details p {
  margin: 0;
  color: #64748b;
  font-size: 14px;
}

.player-stats {
  display: flex;
  gap: 20px;
}

.stat {
  display: flex;
  align-items: center;
  gap: 5px;
  font-size: 14px;
  font-weight: bold;
}

.stat:first-child {
  color: #f59e0b;
}

.stat:last-child {
  color: #ef4444;
}

/* 레벨업 모달 */
.levelup-modal {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.8);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
}

.modal-content {
  background: white;
  border-radius: 20px;
  padding: 40px;
  text-align: center;
  max-width: 400px;
  width: 90%;
  box-shadow: 0 20px 60px rgba(0, 0, 0, 0.3);
}

.levelup-animation {
  font-size: 60px;
  color: #ffd700;
  margin-bottom: 20px;
  animation: bounce 1s infinite;
}

@keyframes bounce {
  0%, 20%, 50%, 80%, 100% {
    transform: translateY(0);
  }
  40% {
    transform: translateY(-10px);
  }
  60% {
    transform: translateY(-5px);
  }
}

.modal-content h2 {
  margin: 0 0 10px 0;
  color: #1e293b;
  font-size: 28px;
}

.modal-content p {
  margin: 0 0 30px 0;
  color: #64748b;
  font-size: 16px;
}

.rewards {
  display: flex;
  justify-content: center;
  gap: 30px;
  margin-bottom: 30px;
}

.reward {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 8px;
  font-weight: bold;
}

.reward i {
  font-size: 24px;
  color: #f59e0b;
}

.continue-btn {
  background: var(--primary-color);
  color: white;
  border: none;
  padding: 15px 30px;
  border-radius: 25px;
  font-weight: bold;
  cursor: pointer;
  transition: all 0.3s ease;
}

.continue-btn:hover {
  background: #5a67d8;
  transform: translateY(-2px);
}

/* 반응형 */
@media (max-width: 768px) {
  .game-main {
    flex-direction: column;
    padding: 15px;
  }
  
  .game-sidebar {
    width: 100%;
    position: static;
  }
  
  .header-content {
    flex-direction: column;
    height: auto;
    padding: 15px 20px;
    gap: 15px;
  }
  
  .level-info {
    align-items: center;
  }
  
  .xp-bar {
    width: 150px;
  }
  
  .quests-grid {
    grid-template-columns: 1fr;
  }
  
  .courses-grid {
    grid-template-columns: 1fr;
  }
  
  .welcome-section {
    flex-direction: column;
    text-align: center;
    gap: 20px;
  }
}
</style>

