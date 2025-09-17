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
        <!-- 대시보드 콘텐츠 -->
        <div v-if="selectedMenu.name === 'dashboard'" class="dashboard-wrapper">
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

        <!-- 게시판 콘텐츠 -->
        <div v-if="selectedMenu.name === 'board'" class="board-wrapper">
          <Board />
        </div>

        <!-- 사용자 관리 콘텐츠 -->
        <div v-if="selectedMenu.name === 'users'" class="users-wrapper">
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

        <!-- 강의 관리 콘텐츠 -->
        <div v-if="selectedMenu.name === 'courses'" class="courses-wrapper">
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
      </div>
    </main>
  </div>
  </template>
  
  <script setup>
  import { ref } from 'vue'
  import Board from './board/Board.vue'
  import './common.css'
  
// 상태 관리
const sidebarExpanded = ref(true)
const sidebarProfileMenuOpen = ref(false)
  
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
      description: '전체 현황을 확인하세요'
    },
    { 
      name: 'board', 
      title: '게시판', 
      icon: 'fas fa-clipboard-list',
      description: '게시판을 관리하세요'
    },
    // { 
    //   name: 'users', 
    //   title: '사용자', 
    //   icon: 'fas fa-users',
    //   description: '사용자를 관리하세요'
    // },
    // { 
    //   name: 'courses', 
    //   title: '강의', 
    //   icon: 'fas fa-book',
    //   description: '강의를 관리하세요'
    // },
    // { 
    //   name: 'analytics', 
    //   title: '분석', 
    //   icon: 'fas fa-chart-line',
    //   description: '데이터를 분석하세요'
    // },
    // { 
    //   name: 'settings', 
    //   title: '설정', 
    //   icon: 'fas fa-cog',
    //   description: '시스템을 설정하세요'
    // }
  ])

// 선택된 메뉴
  const selectedMenu = ref(menus.value[0])
  const selectMenu = (menu) => (selectedMenu.value = menu)
  
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
.user-status.active {
  background: #10b981;
}

.user-status.inactive {
  background: #ef4444;
}

.stat-change.up {
  color: #059669;
}

.stat-change.down {
  color: #dc2626;
}

.activity-status.success {
  background: #10b981;
}

.activity-status.info {
  background: var(--primary-color);
}

.status-badge.active {
  background: #dcfce7;
  color: #166534;
}

.status-badge.completed {
  background: #dbeafe;
  color: #1e40af;
}

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
  .charts-section {
    grid-template-columns: 1fr !important;
  }
  
  .stats-container {
    grid-template-columns: repeat(auto-fit, minmax(200px, 1fr)) !important;
  }
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
  
  .users-grid {
    grid-template-columns: 1fr !important;
  }
  
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
  
  