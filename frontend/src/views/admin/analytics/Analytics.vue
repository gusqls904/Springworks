<template>
  <div class="analytics-wrapper">
    <div class="card" style="margin-bottom: 24px;">
      <div class="card-body">
        <h2 class="title" style="font-size: 24px; margin: 0 0 4px 0;">분석</h2>
        <p class="subtitle" style="margin: 0;">데이터를 분석하세요</p>
      </div>
    </div>

    <div class="analytics-grid" style="display: grid; grid-template-columns: repeat(auto-fit, minmax(300px, 1fr)); gap: 24px;">
      <div class="card" style="padding: 24px;">
        <h3 style="font-size: 18px; font-weight: 600; color: var(--text-primary); margin: 0 0 16px 0;">사용자 분석</h3>
        <div class="chart-placeholder" style="height: 200px; display: flex; flex-direction: column; align-items: center; justify-content: center; color: #94a3b8; background: var(--bg-light); border-radius: 8px;">
          <i class="fas fa-chart-pie" style="font-size: 48px; margin-bottom: 16px;"></i>
          <p>사용자 분석 차트</p>
        </div>
      </div>

      <div class="card" style="padding: 24px;">
        <h3 style="font-size: 18px; font-weight: 600; color: var(--text-primary); margin: 0 0 16px 0;">성과 분석</h3>
        <div class="chart-placeholder" style="height: 200px; display: flex; flex-direction: column; align-items: center; justify-content: center; color: #94a3b8; background: var(--bg-light); border-radius: 8px;">
          <i class="fas fa-chart-bar" style="font-size: 48px; margin-bottom: 16px;"></i>
          <p>성과 분석 차트</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import '/src/styles/admin/main.css'

export default {
  name: 'Analytics',
  setup() {
    return {}
  }
}
</script>

<style scoped>
.analytics-wrapper {
  min-height: 100vh;
  background: var(--bg-primary);
}
</style>
