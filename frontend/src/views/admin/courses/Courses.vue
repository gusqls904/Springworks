<template>
  <div class="courses-wrapper">
    <div class="card" style="margin-bottom: 24px;">
      <div class="card-body" style="display: flex; justify-content: space-between; align-items: center;">
        <div class="header-info">
          <h2 class="title" style="font-size: 24px; margin: 0 0 4px 0;">강의 관리</h2>
          <p class="subtitle" style="margin: 0;">{{ courses.length }}개의 강의가 등록되어 있습니다</p>
        </div>
        <div class="header-actions">
          <button class="btn btn-primary">
            <i class="fas fa-plus"></i>
            강의 추가
          </button>
        </div>
      </div>
    </div>

    <div class="card" style="border-radius: 12px; overflow: hidden;">
      <div class="table-header" style="display: grid; grid-template-columns: 2fr 1fr 1fr 1fr 1fr; background: var(--bg-light); border-bottom: 1px solid var(--border-light);">
        <div class="table-cell" style="padding: 16px 20px; display: flex; align-items: center; color: var(--text-primary); font-weight: 600;">강의명</div>
        <div class="table-cell" style="padding: 16px 20px; display: flex; align-items: center; color: var(--text-primary); font-weight: 600;">강사</div>
        <div class="table-cell" style="padding: 16px 20px; display: flex; align-items: center; color: var(--text-primary); font-weight: 600;">수강생</div>
        <div class="table-cell" style="padding: 16px 20px; display: flex; align-items: center; color: var(--text-primary); font-weight: 600;">상태</div>
        <div class="table-cell" style="padding: 16px 20px; display: flex; align-items: center; color: var(--text-primary); font-weight: 600;">작업</div>
      </div>
      <div v-for="course in courses" :key="course.id" class="table-row" style="display: grid; grid-template-columns: 2fr 1fr 1fr 1fr 1fr; border-bottom: 1px solid var(--bg-light); transition: background-color 0.3s ease;">
        <div class="table-cell" style="padding: 16px 20px; display: flex; align-items: center; color: var(--text-primary);">
          <div class="course-info" style="display: flex; flex-direction: column; gap: 4px;">
            <div class="course-title" style="font-weight: 500; color: var(--text-primary);">{{ course.title }}</div>
            <div class="course-description" style="font-size: 14px; color: #64748b;">{{ course.description }}</div>
          </div>
        </div>
        <div class="table-cell" style="padding: 16px 20px; display: flex; align-items: center; color: var(--text-primary);">{{ course.instructor }}</div>
        <div class="table-cell" style="padding: 16px 20px; display: flex; align-items: center; color: var(--text-primary);">{{ course.students }}명</div>
        <div class="table-cell" style="padding: 16px 20px; display: flex; align-items: center; color: var(--text-primary);">
          <span class="status-badge" :class="course.status" style="padding: 4px 8px; border-radius: 6px; font-size: 12px; font-weight: 500;">{{ course.statusText }}</span>
        </div>
        <div class="table-cell" style="padding: 16px 20px; display: flex; align-items: center; color: var(--text-primary);">
          <div class="action-buttons flex" style="gap: 8px;">
            <button class="btn btn-sm btn-secondary">수정</button>
            <button class="btn btn-sm" style="background: #ef4444; color: white; border: none;">삭제</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { ref } from 'vue'
import '/src/styles/admin/main.css'

export default {
  name: 'Courses',
  setup() {
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

    return {
      courses
    }
  }
}
</script>

<style scoped>
.courses-wrapper {
  min-height: 100vh;
  background: var(--bg-primary);
}

/* 상태 배지 스타일 */
.status-badge.active {
  background: #dcfce7;
  color: #166534;
}

.status-badge.completed {
  background: #dbeafe;
  color: #1e40af;
}

/* 테이블 행 호버 효과 */
.table-row:hover {
  background: var(--bg-light);
}

/* 반응형 테이블 */
@media (max-width: 768px) {
  .table-header,
  .table-row {
    grid-template-columns: 1fr !important;
    gap: 8px;
  }
  
  .table-cell {
    padding: 8px 0;
  }
}
</style>
