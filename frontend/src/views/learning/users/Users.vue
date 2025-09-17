<template>
  <div class="users-wrapper">
    <div class="card" style="margin-bottom: 24px;">
      <div class="card-body" style="display: flex; justify-content: space-between; align-items: center;">
        <div class="header-info">
          <h2 class="title" style="font-size: 24px; margin: 0 0 4px 0;">사용자 관리</h2>
          <p class="subtitle" style="margin: 0;">{{ users.length }}명의 사용자가 등록되어 있습니다</p>
        </div>
        <div class="header-actions flex" style="align-items: center; gap: 16px;">
          <div class="filter-group">
            <select class="form-input" style="width: 150px;">
              <option>모든 사용자</option>
              <option>활성 사용자</option>
              <option>비활성 사용자</option>
            </select>
          </div>
          <button class="btn btn-primary">
            <i class="fas fa-plus"></i>
            사용자 추가
          </button>
        </div>
      </div>
    </div>

    <div class="users-grid" style="display: grid; grid-template-columns: repeat(auto-fill, minmax(300px, 1fr)); gap: 24px;">
      <div v-for="user in users" :key="user.id" class="card mov03" style="overflow: hidden; transition: all 0.3s ease;">
        <div class="user-card-header" style="position: relative; padding: 24px 24px 0; text-align: center;">
          <img :src="user.avatar" :alt="user.name" class="user-avatar-large" style="width: 80px; height: 80px; border-radius: 50%; object-fit: cover; margin: 0 auto 16px;">
          <div class="user-status" :class="user.status" style="position: absolute; top: 24px; right: 24px; width: 12px; height: 12px; border-radius: 50%; border: 3px solid white;"></div>
        </div>
        <div class="card-body" style="text-align: center;">
          <h4 class="user-name" style="font-size: 18px; font-weight: 600; color: var(--text-primary); margin: 0 0 8px 0;">{{ user.name }}</h4>
          <p class="user-email" style="color: #64748b; margin: 0 0 8px 0;">{{ user.email }}</p>
          <div class="user-role" style="background: #dbeafe; color: #1e40af; padding: 4px 12px; border-radius: 20px; font-size: 12px; font-weight: 500; display: inline-block; margin-bottom: 8px;">{{ user.role }}</div>
          <div class="user-joined" style="color: #94a3b8; font-size: 14px;">가입일: {{ user.joinedAt }}</div>
        </div>
        <div class="card-footer" style="display: flex; gap: 8px; justify-content: center;">
          <button class="btn btn-sm btn-secondary">수정</button>
          <button class="btn btn-sm" style="background: #ef4444; color: white; border: none;">삭제</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { ref } from 'vue'
import '../common.css'

export default {
  name: 'Users',
  setup() {
    // 사용자 목록
    const users = ref([
      {
        id: 1,
        name: '홍길동',
        email: 'hong@example.com',
        role: '관리자',
        joinedAt: '2024-01-15',
        status: 'active',
        avatar: 'https://randomuser.me/api/portraits/men/32.jpg'
      },
      {
        id: 2,
        name: '김철수',
        email: 'kim@example.com',
        role: '강사',
        joinedAt: '2024-02-20',
        status: 'active',
        avatar: 'https://randomuser.me/api/portraits/men/33.jpg'
      },
      {
        id: 3,
        name: '이영희',
        email: 'lee@example.com',
        role: '학생',
        joinedAt: '2024-03-10',
        status: 'inactive',
        avatar: 'https://randomuser.me/api/portraits/women/34.jpg'
      },
      {
        id: 4,
        name: '박민수',
        email: 'park@example.com',
        role: '학생',
        joinedAt: '2024-03-15',
        status: 'active',
        avatar: 'https://randomuser.me/api/portraits/men/35.jpg'
      }
    ])

    return {
      users
    }
  }
}
</script>

<style scoped>
.users-wrapper {
  min-height: 100vh;
  background: var(--bg-primary);
}

/* 사용자 상태 스타일 */
.user-status.active {
  background: #10b981;
}

.user-status.inactive {
  background: #ef4444;
}

/* 카드 호버 효과 */
.card:hover {
  transform: translateY(-2px);
  box-shadow: var(--shadow-lg);
}

/* 반응형 그리드 */
@media (max-width: 768px) {
  .users-grid {
    grid-template-columns: 1fr !important;
  }
}
</style>
