<template>
  <div class="settings-wrapper">
    <div class="card" style="margin-bottom: 24px;">
      <div class="card-body">
        <h2 class="title" style="font-size: 24px; margin: 0 0 4px 0;">시스템 설정</h2>
        <p class="subtitle" style="margin: 0;">시스템을 설정하세요</p>
      </div>
    </div>

    <div class="settings-grid" style="display: grid; grid-template-columns: repeat(auto-fit, minmax(400px, 1fr)); gap: 24px;">
      <!-- 일반 설정 -->
      <div class="card" style="padding: 24px;">
        <h3 style="font-size: 18px; font-weight: 600; color: var(--text-primary); margin: 0 0 20px 0;">
          <i class="fas fa-cog" style="margin-right: 8px;"></i>
          일반 설정
        </h3>
        <div class="setting-item" style="display: flex; justify-content: space-between; align-items: center; padding: 16px 0; border-bottom: 1px solid var(--border-light);">
          <div>
            <div style="font-weight: 500; color: var(--text-primary); margin-bottom: 4px;">다크 모드</div>
            <div style="font-size: 14px; color: #64748b;">어두운 테마를 사용합니다</div>
          </div>
          <label class="toggle-switch">
            <input type="checkbox" v-model="settings.darkMode">
            <span class="slider"></span>
          </label>
        </div>
        <div class="setting-item" style="display: flex; justify-content: space-between; align-items: center; padding: 16px 0; border-bottom: 1px solid var(--border-light);">
          <div>
            <div style="font-weight: 500; color: var(--text-primary); margin-bottom: 4px;">알림</div>
            <div style="font-size: 14px; color: #64748b;">이메일 알림을 받습니다</div>
          </div>
          <label class="toggle-switch">
            <input type="checkbox" v-model="settings.notifications">
            <span class="slider"></span>
          </label>
        </div>
        <div class="setting-item" style="display: flex; justify-content: space-between; align-items: center; padding: 16px 0;">
          <div>
            <div style="font-weight: 500; color: var(--text-primary); margin-bottom: 4px;">자동 저장</div>
            <div style="font-size: 14px; color: #64748b;">작업을 자동으로 저장합니다</div>
          </div>
          <label class="toggle-switch">
            <input type="checkbox" v-model="settings.autoSave">
            <span class="slider"></span>
          </label>
        </div>
      </div>

      <!-- 보안 설정 -->
      <div class="card" style="padding: 24px;">
        <h3 style="font-size: 18px; font-weight: 600; color: var(--text-primary); margin: 0 0 20px 0;">
          <i class="fas fa-shield-alt" style="margin-right: 8px;"></i>
          보안 설정
        </h3>
        <div class="setting-item" style="display: flex; justify-content: space-between; align-items: center; padding: 16px 0; border-bottom: 1px solid var(--border-light);">
          <div>
            <div style="font-weight: 500; color: var(--text-primary); margin-bottom: 4px;">2단계 인증</div>
            <div style="font-size: 14px; color: #64748b;">추가 보안을 위해 활성화</div>
          </div>
          <label class="toggle-switch">
            <input type="checkbox" v-model="settings.twoFactor">
            <span class="slider"></span>
          </label>
        </div>
        <div class="setting-item" style="display: flex; justify-content: space-between; align-items: center; padding: 16px 0; border-bottom: 1px solid var(--border-light);">
          <div>
            <div style="font-weight: 500; color: var(--text-primary); margin-bottom: 4px;">세션 만료</div>
            <div style="font-size: 14px; color: #64748b;">30분 후 자동 로그아웃</div>
          </div>
          <label class="toggle-switch">
            <input type="checkbox" v-model="settings.sessionTimeout">
            <span class="slider"></span>
          </label>
        </div>
        <div class="setting-item" style="display: flex; justify-content: space-between; align-items: center; padding: 16px 0;">
          <div>
            <div style="font-weight: 500; color: var(--text-primary); margin-bottom: 4px;">로그인 알림</div>
            <div style="font-size: 14px; color: #64748b;">새로운 로그인 시 알림</div>
          </div>
          <label class="toggle-switch">
            <input type="checkbox" v-model="settings.loginAlerts">
            <span class="slider"></span>
          </label>
        </div>
      </div>

      <!-- 계정 설정 -->
      <div class="card" style="padding: 24px;">
        <h3 style="font-size: 18px; font-weight: 600; color: var(--text-primary); margin: 0 0 20px 0;">
          <i class="fas fa-user-cog" style="margin-right: 8px;"></i>
          계정 설정
        </h3>
        <div class="form-group" style="margin-bottom: 16px;">
          <label style="display: block; font-weight: 500; color: var(--text-primary); margin-bottom: 8px;">이름</label>
          <input type="text" v-model="settings.name" class="form-input" style="width: 100%;">
        </div>
        <div class="form-group" style="margin-bottom: 16px;">
          <label style="display: block; font-weight: 500; color: var(--text-primary); margin-bottom: 8px;">이메일</label>
          <input type="email" v-model="settings.email" class="form-input" style="width: 100%;">
        </div>
        <div class="form-group" style="margin-bottom: 20px;">
          <label style="display: block; font-weight: 500; color: var(--text-primary); margin-bottom: 8px;">역할</label>
          <select v-model="settings.role" class="form-input" style="width: 100%;">
            <option value="admin">관리자</option>
            <option value="teacher">강사</option>
            <option value="student">학생</option>
          </select>
        </div>
        <button class="btn btn-primary" style="width: 100%;">
          <i class="fas fa-save"></i>
          설정 저장
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import { ref } from 'vue'
import '../common.css'

export default {
  name: 'Settings',
  setup() {
    // 설정 데이터
    const settings = ref({
      darkMode: false,
      notifications: true,
      autoSave: true,
      twoFactor: false,
      sessionTimeout: true,
      loginAlerts: true,
      name: '홍길동',
      email: 'hong@example.com',
      role: 'admin'
    })

    return {
      settings
    }
  }
}
</script>

<style scoped>
.settings-wrapper {
  min-height: 100vh;
  background: var(--bg-primary);
}

/* 토글 스위치 스타일 */
.toggle-switch {
  position: relative;
  display: inline-block;
  width: 50px;
  height: 24px;
}

.toggle-switch input {
  opacity: 0;
  width: 0;
  height: 0;
}

.slider {
  position: absolute;
  cursor: pointer;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: #ccc;
  transition: .4s;
  border-radius: 24px;
}

.slider:before {
  position: absolute;
  content: "";
  height: 18px;
  width: 18px;
  left: 3px;
  bottom: 3px;
  background-color: white;
  transition: .4s;
  border-radius: 50%;
}

input:checked + .slider {
  background-color: var(--primary-color);
}

input:checked + .slider:before {
  transform: translateX(26px);
}

/* 반응형 그리드 */
@media (max-width: 768px) {
  .settings-grid {
    grid-template-columns: 1fr !important;
  }
}
</style>
