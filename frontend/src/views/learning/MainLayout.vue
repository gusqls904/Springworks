<template>
  <div class="minimal-layout flex">
    <!-- 미니멀 사이드바 -->
    <aside class="minimal-sidebar" :class="{ 'expanded': sidebarExpanded }">
      <div class="sidebar-header flex" style="justify-content: space-between; align-items: center;">
        <div class="logo flex" :style="{ 
          alignItems: 'center', 
          gap: sidebarExpanded ? '12px' : '0',
          justifyContent: sidebarExpanded ? 'flex-start' : 'center'
        }">
          <i v-show="sidebarExpanded" class="fas fa-graduation-cap col1" :style="{ 
            fontSize: '24px',
            transition: 'all 0.3s ease'
          }"></i>
          <span v-show="sidebarExpanded" :style="{ 
            fontSize: '20px', 
            fontWeight: '700', 
            color: 'var(--text-primary)',
            transition: 'all 0.3s ease'
          }">Learning</span>
        </div>
        <button class="btn btn-sm" @click="sidebarExpanded = !sidebarExpanded" style="width: 40px; height: 40px; padding: 0;">
          <i class="fas fa-bars"></i>
        </button>
      </div>
      
      <nav class="sidebar-nav">
        <div v-for="menu in menus" :key="menu.name" 
             class="nav-item flex" 
             :class="{ 'active': selectedMenu.name === menu.name }"
             @click="selectMenu(menu)"
             :title="sidebarExpanded ? menu.title : menu.title"
             :style="{ 
               alignItems: 'center', 
               gap: sidebarExpanded ? '16px' : '0', 
               padding: sidebarExpanded ? '12px 20px' : '12px', 
               margin: '4px 12px', 
               borderRadius: '8px', 
               cursor: 'pointer', 
               transition: 'all 0.3s ease', 
               color: '#64748b',
               justifyContent: sidebarExpanded ? 'flex-start' : 'center'
             }">
          <i :class="menu.icon" :style="{ 
            fontSize: '18px', 
            width: '20px', 
            textAlign: 'center',
            transition: 'all 0.3s ease'
          }"></i>
          <span v-show="sidebarExpanded" :style="{ 
            fontWeight: '500', 
            whiteSpace: 'nowrap',
            transition: 'all 0.3s ease'
          }">{{ menu.title }}</span>
          <div class="nav-indicator" v-if="selectedMenu.name === menu.name && sidebarExpanded" style="position: absolute; right: 8px; top: 50%; transform: translateY(-50%); width: 4px; height: 20px; background: var(--primary-color); border-radius: 2px;"></div>
        </div>
      </nav>
      
      <!-- 사이드바 프로필 -->
      <div class="sidebar-profile" :style="{ 
        padding: sidebarExpanded ? '20px' : '12px',
        borderTop: '1px solid var(--border-light)',
        marginTop: 'auto'
      }">
        <div class="profile-container flex" :style="{ 
          alignItems: 'center', 
          gap: sidebarExpanded ? '12px' : '0',
          justifyContent: sidebarExpanded ? 'flex-start' : 'center',
          cursor: 'pointer',
          padding: '8px',
          borderRadius: '8px',
          transition: 'all 0.3s ease'
        }" @click="toggleSidebarProfileMenu">
          <div class="profile-avatar" :style="{ 
            width: sidebarExpanded ? '40px' : '32px',
            height: sidebarExpanded ? '40px' : '32px',
            borderRadius: '50%',
            backgroundColor: 'var(--primary-color)',
            display: 'flex',
            alignItems: 'center',
            justifyContent: 'center',
            color: 'white',
            fontSize: sidebarExpanded ? '16px' : '14px',
            fontWeight: '600',
            transition: 'all 0.3s ease'
          }">
            <i class="fas fa-user"></i>
          </div>
          <div v-show="sidebarExpanded" class="profile-details" :style="{ 
            flex: 1,
            transition: 'all 0.3s ease'
          }">
            <div :style="{ 
              fontSize: '14px', 
              fontWeight: '600', 
              color: 'var(--text-primary)',
              marginBottom: '2px'
            }">{{ user.name }}</div>
            <div :style="{ 
              fontSize: '12px', 
              color: '#64748b'
            }">{{ user.role }}</div>
          </div>
          <i v-show="sidebarExpanded" class="fas fa-chevron-down" :style="{ 
            fontSize: '12px', 
            color: '#64748b',
            transition: 'all 0.3s ease',
            transform: sidebarProfileMenuOpen ? 'rotate(180deg)' : 'rotate(0deg)'
          }"></i>
        </div>
      </div>
      
    </aside>

    <!-- 메인 콘텐츠 -->
    <main class="minimal-main">
      <!-- 상단 헤더 -->
      <header class="minimal-header flex" style="justify-content: space-between; align-items: center;">
        <div class="header-left">
          <h1 class="title" style="margin: 0 0 4px 0;">{{ selectedMenu.title }}</h1>
          <p class="subtitle" style="margin: 0;">{{ selectedMenu.description }}</p>
        </div>
        
        <div class="header-right flex" style="align-items: center; gap: 16px;">
          <button class="btn btn-sm" style="position: relative; width: 40px; height: 40px; padding: 0;">
            <i class="fas fa-bell"></i>
            <span class="notification-count" style="position: absolute; top: -4px; right: -4px; background: #ef4444; color: white; font-size: 10px; padding: 2px 6px; border-radius: 10px; min-width: 16px; text-align: center;">3</span>
          </button>
          
          <button class="btn btn-sm" @click="handleLogout" style="width: 40px; height: 40px; padding: 0;">
            <i class="fas fa-sign-out-alt"></i>
          </button>
        </div>
      </header>

      <!-- 콘텐츠 영역 -->
      <div class="content-wrapper">
        <!-- 동적 컴포넌트 렌더링 -->
        <component 
          :is="currentComponent" 
          v-if="currentComponent"
          :class="`${selectedMenu.name}-wrapper`"
        />

      </div>
    </main>
  </div>
  </template>
  
  <script setup>
  import { ref, computed } from 'vue'
  import Board from './board/Board.vue'
  import Dashboard from './dashboard/dashboard.vue'
  import Analytics from './analytics/Analytics.vue'
  import Users from './users/Users.vue'
  import Courses from './courses/Courses.vue'
  import Settings from './settings/Settings.vue'
  import './common.css'
  
// 상태 관리
const sidebarExpanded = ref(true)
const sidebarProfileMenuOpen = ref(false)

// 컴포넌트 매핑
const components = {
  Dashboard,
  Board,
  Analytics,
  Users,
  Courses,
  Settings
}
  
  // 사용자 정보
  const user = ref({
    id: 1,
    name: '홍길동',
    email: 'hong@example.com',
    role: '관리자'
  })
  
  // 메뉴
  const menus = ref([
    { 
      name: 'dashboard', 
      title: '대시보드', 
      icon: 'fas fa-tachometer-alt',
      description: '내부에 무슨 기능 추가 할지 생각 중',
      component: 'Dashboard'
    },
    { 
      name: 'board', 
      title: '게시판', 
      icon: 'fas fa-clipboard-list',
      description: '나중에 사용자 질문 관리 할지 생각 중',
      component: 'Board'
    },
    { 
      name: 'users', 
      title: '사용자', 
      icon: 'fas fa-users',
      description: '사용자를 관리하세요',
      component: 'Users'
    },
    { 
      name: 'courses', 
      title: '강의', 
      icon: 'fas fa-book',
      description: '강의를 관리하세요',
      component: 'Courses'
    },
    { 
      name: 'analytics', 
      title: '분석', 
      icon: 'fas fa-chart-line',
      description: '데이터를 분석하세요',
      component: 'Analytics'
    },
    { 
      name: 'settings', 
      title: '설정', 
      icon: 'fas fa-cog',
      description: '시스템을 설정하세요',
      component: 'Settings'
    }
  ])

// 선택된 메뉴
  const selectedMenu = ref(menus.value[0])
  const selectMenu = (menu) => (selectedMenu.value = menu)
  
  // 현재 컴포넌트 계산
  const currentComponent = computed(() => {
    return components[selectedMenu.value.component] || null
  })
  
  // 사이드바 프로필 메뉴 토글
  const toggleSidebarProfileMenu = () => {
    sidebarProfileMenuOpen.value = !sidebarProfileMenuOpen.value
  }
  
  // 로그아웃 처리
  const handleLogout = () => {
    if (confirm('정말 로그아웃 하시겠습니까?')) {
      // 로그아웃 로직 구현
      console.log('로그아웃 처리')
      // 예: 토큰 제거, 로그인 페이지로 리다이렉트 등
      // router.push('/login')
    }
  }
  

  </script>
  
  <style scoped>
/* ============================================
   MINIMAL SIDEBAR LAYOUT STYLES
   ============================================ */

.minimal-layout {
  min-height: 100vh;
  background: var(--bg-primary);
}

/* ============================================
   SIDEBAR STYLES
   ============================================ */
.minimal-sidebar {
  width: 80px;
  background: var(--bg-white);
  border-right: 1px solid var(--border-light);
  transition: width 0.3s ease;
  display: flex;
  flex-direction: column;
  position: fixed;
  height: 100vh;
  z-index: 1000;
  left: 0;
  top: 0;
}

.minimal-sidebar.expanded {
  width: 280px;
}

.sidebar-header {
  padding: 20px;
  border-bottom: 1px solid var(--border-light);
  min-height: 80px;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.sidebar-nav {
  flex: 1;
  padding: 20px 0;
  overflow-y: auto;
}

.nav-item {
  position: relative;
  margin: 4px 12px;
  cursor: pointer;
  transition: all 0.3s ease;
  color: #64748b;
    border-radius: 8px;
  }
  
.nav-item:hover {
  background: #f1f5f9;
  color: #475569;
}

.nav-item.active {
  background: #dbeafe;
  color: var(--primary-color);
}

/* 사이드바 확장 시 텍스트 표시 - Vue의 동적 스타일로 처리하므로 제거 */

/* ============================================
   MAIN CONTENT STYLES
   ============================================ */
.minimal-main {
  flex: 1;
  margin-left: 80px;
  transition: margin-left 0.3s ease;
  min-height: 100vh;
}

.minimal-sidebar.expanded ~ .minimal-main {
  margin-left: 280px;
}

.minimal-header {
  background: var(--bg-white);
  border-bottom: 1px solid var(--border-light);
  padding: 24px 32px;
}

.content-wrapper {
  padding: 32px;
}

/* ============================================
   STATUS STYLES
   ============================================ */
/* Status styles moved to individual components */

/* ============================================
   HOVER EFFECTS
   ============================================ */
.card:hover {
  transform: translateY(-2px);
  box-shadow: var(--shadow-lg);
}

.table-row:hover {
  background: var(--bg-light);
}

.sidebar-profile .profile-container:hover {
  background: var(--bg-light);
}

/* ============================================
   RESPONSIVE DESIGN
   ============================================ */
@media (max-width: 1024px) {
  /* Responsive styles for other components */
}

@media (max-width: 768px) {
  .minimal-sidebar {
    transform: translateX(-100%);
    width: 280px !important;
  }
  
  .minimal-sidebar.expanded {
    transform: translateX(0);
  }
  
  .minimal-main {
    margin-left: 0 !important;
  }
  
  .minimal-sidebar.expanded ~ .minimal-main {
    margin-left: 0 !important;
  }
  
  .minimal-header {
    flex-direction: column;
    align-items: flex-start;
    gap: 16px;
  }
  
  .header-right {
    width: 100%;
    justify-content: space-between;
  }
  
  .content-wrapper {
    padding: 16px;
  }
  
  /* Responsive styles for individual components */
  }
  </style>
  
  