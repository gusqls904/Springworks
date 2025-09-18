<template>
  <div class="dashboard-wrapper">
    <!-- 통계 카드 -->
    <div class="stats-container" style="display: grid; grid-template-columns: repeat(auto-fit, minmax(250px, 1fr)); gap: 24px; margin-bottom: 32px;">
      <div v-for="stat in dashboardStats" :key="stat.label" class="card mov03" style="padding: 24px; display: flex; align-items: center; gap: 16px; transition: all 0.3s ease;">
        <div class="stat-icon" style="width: 48px; height: 48px; background: linear-gradient(135deg, var(--primary-color) 0%, var(--primary-light) 100%); border-radius: 12px; display: flex; align-items: center; justify-content: center; color: white; font-size: 20px;">
          <i :class="stat.icon"></i>
        </div>
        <div class="stat-content" style="flex: 1;">
          <div class="stat-value" style="font-size: 24px; font-weight: 700; color: var(--text-primary); margin-bottom: 4px;">{{ stat.value }}</div>
          <div class="stat-label" style="color: #64748b; font-size: 14px; margin-bottom: 8px;">{{ stat.label }}</div>
          <div class="stat-change flex" :class="stat.trend" style="align-items: center; gap: 4px; font-size: 12px; font-weight: 600;">
            <i :class="stat.trendIcon"></i>
            {{ stat.change }}
          </div>
        </div>
      </div>
    </div>

    <!-- 차트 섹션 -->
    <div class="charts-section" style="display: grid; grid-template-columns: 2fr 1fr; gap: 24px; margin-bottom: 32px;">
      <div class="card" style="border-radius: 12px; overflow: hidden;">
        <div class="card-header" style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 0;">
          <h3 style="font-size: 18px; font-weight: 600; color: var(--text-primary); margin: 0;">사용자 증가 추이</h3>
          <div class="chart-actions flex" style="gap: 8px;">
            <button class="btn btn-sm">7일</button>
            <button class="btn btn-sm btn-primary">30일</button>
            <button class="btn btn-sm">90일</button>
          </div>
        </div>
        <div class="card-body">
          <div class="chart-placeholder" style="height: 200px; display: flex; flex-direction: column; align-items: center; justify-content: center; color: #94a3b8; background: var(--bg-light); border-radius: 8px;">
            <i class="fas fa-chart-line" style="font-size: 48px; margin-bottom: 16px;"></i>
            <p>차트 데이터</p>
          </div>
        </div>
      </div>
      
      <div class="card" style="border-radius: 12px; overflow: hidden;">
        <div class="card-header" style="margin-bottom: 0;">
          <h3 style="font-size: 18px; font-weight: 600; color: var(--text-primary); margin: 0;">강의별 수강률</h3>
        </div>
        <div class="card-body">
          <div class="progress-list" style="display: flex; flex-direction: column; gap: 16px;">
            <div v-for="course in courseProgress" :key="course.name" class="progress-item" style="display: flex; flex-direction: column; gap: 8px;">
              <div class="progress-info flex" style="justify-content: space-between; align-items: center;">
                <span class="course-name" style="font-weight: 500; color: var(--text-primary);">{{ course.name }}</span>
                <span class="progress-percent" style="font-weight: 600; color: var(--primary-color);">{{ course.percent }}%</span>
              </div>
              <div class="progress-bar" style="height: 6px; background: #e2e8f0; border-radius: 3px; overflow: hidden;">
                <div class="progress-fill" :style="{ width: course.percent + '%' }" style="height: 100%; background: linear-gradient(90deg, var(--primary-color) 0%, var(--primary-light) 100%); border-radius: 3px; transition: width 0.3s ease;"></div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 최근 활동 -->
    <div class="card" style="border-radius: 12px; overflow: hidden;">
      <div class="card-header" style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 0;">
        <h3 style="font-size: 18px; font-weight: 600; color: var(--text-primary); margin: 0;">최근 활동</h3>
        <button class="btn btn-secondary">전체 보기</button>
      </div>
      <div class="card-body">
        <div class="activity-list" style="display: flex; flex-direction: column; gap: 16px;">
          <div v-for="activity in recentActivities" :key="activity.id" class="activity-item flex" style="align-items: center; gap: 16px; padding: 16px 0; border-bottom: 1px solid var(--bg-light);">
            <div class="activity-icon" style="width: 40px; height: 40px; background: #f1f5f9; border-radius: 8px; display: flex; align-items: center; justify-content: center; color: #64748b;">
              <i :class="activity.icon"></i>
            </div>
            <div class="activity-content" style="flex: 1;">
              <div class="activity-text" style="color: var(--text-primary); font-weight: 500; margin-bottom: 4px;">{{ activity.text }}</div>
              <div class="activity-time" style="color: #94a3b8; font-size: 14px;">{{ activity.time }}</div>
            </div>
            <div class="activity-status" :class="activity.status" style="width: 8px; height: 8px; border-radius: 50%;"></div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { ref } from 'vue'
import '../common.css'

export default {
  name: 'Dashboard',
  setup() {
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

    return {
      dashboardStats,
      courseProgress,
      recentActivities
    }
  }
}
</script>

<style scoped>
/* Dashboard-specific styles */
.dashboard-wrapper {
  min-height: 100vh;
  background: var(--bg-primary);
}

/* 통계 카드 호버 효과 */
.card:hover {
  transform: translateY(-2px);
  box-shadow: var(--shadow-lg);
}

/* 차트 섹션 반응형 */
@media (max-width: 1024px) {
  .charts-section {
    grid-template-columns: 1fr !important;
  }
  
  .stats-container {
    grid-template-columns: repeat(auto-fit, minmax(200px, 1fr)) !important;
  }
}
</style>
