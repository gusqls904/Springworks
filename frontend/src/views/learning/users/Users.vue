<template>
  <div class="users-page">
    <!-- 상단 헤더 -->
    <header class="users-header">
      <div class="users-header-container">
        <!-- 검색 영역 -->
        <div class="search-group">
          <select v-model="searchType" class="search-select form-input" style="width: 120px; margin-right: 8px;">
            <option value="all">전체</option>
            <option value="name">이름</option>
            <option value="email">이메일</option>
            <option value="role">역할</option>
          </select>
          <input 
            type="text" 
            v-model="searchQuery"
            :placeholder="getSearchPlaceholder()"
            class="search-input form-input"
            @keyup.enter="searchUsers"
          >
          <button @click="searchUsers" class="search-btn btn btn-primary">
            <i class="fas fa-search"></i>
          </button>
        </div>
        
        <!-- 액션 버튼들 -->
        <div class="users-header-actions">
          <button @click="goToAddUser" class="add-btn btn btn-primary">
            <i class="fas fa-plus"></i>
            등록
          </button>
        </div>
      </div>
    </header>

    <!-- 메인 컨텐츠 -->
    <div class="users-content">
      <!-- 사용자 테이블 -->
      <div class="users-table-wrapper">
        <template v-if="loading">
          <div class="loading-state">
            <i class="fas fa-spinner fa-spin"></i>
            로딩 중...
          </div>
        </template>

        <template v-else-if="users.length === 0">
          <div class="empty-state">
            <i class="fas fa-users"></i>
            등록된 사용자가 없습니다.
          </div>
        </template>

        <template v-else>
          <div class="board-table">
            <div class="table-header">
              <div class="table-cell">번호</div>
              <div class="table-cell">사용자ID</div>
              <div class="table-cell">이름</div>
              <div class="table-cell">이메일</div>
              <div class="table-cell">역할</div>
              <div class="table-cell">상태</div>
              <div class="table-cell">가입일</div>
              <div class="table-cell">작업</div>
            </div>
            <div 
              v-for="user in users" 
              :key="user.userSeq" 
              class="table-row"
            >
              <div class="table-cell" data-label="번호">{{ user.userSeq }}</div>
              <div class="table-cell" data-label="사용자ID">{{ user.userId }}</div>
              <div class="table-cell" data-label="이름">
                <span class="board-title" :title="user.userName">{{ user.userName }}</span>
              </div>
              <div class="table-cell" data-label="이메일">{{ user.email }}</div>
              <div class="table-cell" data-label="역할">
                <span class="role-badge">{{ user.roleName }}</span>
              </div>
              <div class="table-cell" data-label="상태">
                <span class="status-badge" :class="getStatusClass(user.isActive)">{{ user.isActive }}</span>
              </div>
              <div class="table-cell" data-label="가입일">{{ formatDate(user.createdDttm) }}</div>
              <div class="table-cell" data-label="작업">
                <div class="action-buttons">
                  <button class="btn btn-sm btn-secondary" @click="editUserHandler(user.userSeq)">
                    <i class="fas fa-edit"></i>
                  </button>
                  <button class="btn btn-sm btn-danger" @click="deleteUser(user.userId)">
                    <i class="fas fa-trash"></i>
                  </button>
                </div>
              </div>
            </div>
          </div>
        </template>
      </div>

      <!-- 페이지네이션 -->
      <div class="pagination">
        <button 
          @click="goToPage(currentPage - 1)"
          :disabled="currentPage <= 1"
          class="page-btn"
        >
          <i class="fas fa-chevron-left"></i>
          이전
        </button>
        
        <div class="page-numbers">
          <button 
            v-for="page in visiblePages" 
            :key="page"
            :class="['page-number', { active: page === currentPage }]"
            @click="goToPage(page)"
          >
            {{ page }}
          </button>
        </div>
        
        <button 
          @click="goToPage(currentPage + 1)"
          :disabled="currentPage >= totalPages"
          class="page-btn"
        >
          다음
          <i class="fas fa-chevron-right"></i>
        </button>
      </div>
    </div>

    <!-- 사용자 등록/수정 팝업 -->
    <UserPopup
      :visible="userPopupVisible"
      @update:visible="userPopupVisible = $event"
      @close="userPopupVisible = false"
      @user-saved="handleUserSaved"
      @refresh-list="handleRefreshList"
      :edit-user="editUser"
    />

  </div>
</template>

<script>
import { ref, onMounted, computed } from 'vue'
import { api } from '/src/util/api.js'
import { getUserMockData, deleteUserMockData } from '../../mock/userMockData.js'
import { callApiOrMock } from '/src/util/mockConfig.js'
import UserPopup from './UserPopup.vue'
import Swal from 'sweetalert2'
import '../common.css'

export default {
  name: 'Users',
  components: {
    UserPopup
  },
  setup() {
    // 상태
    const users = ref([])
    const loading = ref(false)
    const searchQuery = ref('')
    const searchType = ref('all')
    
    // 팝업 상태
    const userPopupVisible = ref(false)
    const editUser = ref(null)
    

    // 페이지네이션
    const currentPage = ref(1)
    const totalPages = ref(1)
    const totalCount = ref(0)
    const pageSize = ref(10)

    // 계산값
    const visiblePages = computed(() => {
      const start = Math.max(1, currentPage.value - 2)
      const end = Math.min(totalPages.value, start + 4)
      return Array.from({ length: end - start + 1 }, (_, i) => start + i)
    })

    /**
     * 사용자 목록 로드
     */
    const getUsers = async () => {
      try {
        loading.value = true
        
        // API 요청 파라미터 구성
        const requestData = {
          page: currentPage.value,
          size: pageSize.value,
          searchType: searchType.value,
          searchQuery: searchQuery.value
        }
        
        // API 호출 또는 목업 데이터 사용
        const res = await callApiOrMock(
          // 실제 API 호출
          () => api.post('/api/user/getUserList', requestData),
          // 목업 데이터 호출
          () => getUserMockData(requestData)
        )
        
        // 응답 데이터 처리
        if (res && res.body) {
          users.value = res.body.userList || []
          totalCount.value = res.body.pagination?.totalElements || 0
          totalPages.value = res.body.pagination?.totalPages || 1
        } else {
          users.value = []
          totalCount.value = 0
          totalPages.value = 1
        }
      } catch (e) {
        console.error('사용자 목록 로드 실패:', e)
        users.value = []
        totalCount.value = 0
        totalPages.value = 1
      } finally {
        loading.value = false
      }
    }

    /**
     * 검색
     */
    const searchUsers = () => {
      currentPage.value = 1
      getUsers()
    }

    /**
     * 페이지 이동
     */
    const goToPage = (page) => {
      if (page < 1 || page > totalPages.value) return
      currentPage.value = page
      getUsers()
    }

    /**
     * 사용자 등록
     */
    const goToAddUser = () => {
      editUser.value = null
      userPopupVisible.value = true
    }

    /**
     * 사용자 수정
     */
    const editUserHandler = (userSeq) => {
      const user = users.value.find(u => u.userSeq === userSeq)
      if (user) {
        // 사용자 데이터를 복사하여 새로운 객체 생성
        editUser.value = { ...user }
        userPopupVisible.value = true
      }
    }

    /**
     * 사용자 삭제 (SweetAlert2 컨펌 팝업 표시)
     */
    const deleteUser = async (userId) => {
      const result = await Swal.fire({
        title: '사용자 삭제',
        text: '정말로 이 사용자를 삭제하시겠습니까?',
        icon: 'question',
        showCancelButton: true,
        confirmButtonText: '삭제',
        cancelButtonText: '취소',
        allowOutsideClick: false,
        allowEscapeKey: true
      })
      
      if (result.isConfirmed) {
        await confirmDelete(userId)
      }
    }

    /**
     * 사용자 저장 후 처리
     */
    const handleUserSaved = () => {
      getUsers() // 목록 새로고침
    }

    /**
     * 목록 재조회 처리
     */
    const handleRefreshList = () => {
      getUsers() // 목록 새로고침
    }

    /**
     * 삭제 확인 처리
     */
    const confirmDelete = async (userId) => {
      try {
        loading.value = true
        
        // API 호출 또는 목업 데이터 사용
        await callApiOrMock(
          // 실제 API 호출
          async () => {
            return await api.post('/api/user/deleteUser', { userId: userId })
          },
          // 목업 데이터 호출
          async () => {
            return deleteUserMockData(userId)
          }
        )
        
        // 삭제 성공 시 목록 재조회
        await getUsers()
        
        // 성공 토스트 표시
        Swal.fire({
          title: '삭제 완료',
          text: '사용자가 성공적으로 삭제되었습니다.',
          icon: 'success',
          timer: 2000,
          showConfirmButton: false
        })
        
      } catch (error) {
        console.error('사용자 삭제 오류:', error)
        Swal.fire({
          title: '삭제 실패',
          text: '사용자 삭제 중 오류가 발생했습니다.',
          icon: 'error'
        })
      } finally {
        loading.value = false
      }
    }


    /**
     * 검색 플레이스홀더 동적 생성
     */
    const getSearchPlaceholder = () => {
      const placeholders = {
        all: '이름, 이메일, 역할로 검색...',
        name: '이름으로 검색...',
        email: '이메일로 검색...',
        role: '역할로 검색...'
      }
      return placeholders[searchType.value] || placeholders.all
    }

    /**
     * 상태 클래스 매핑
     */
    const getStatusClass = (isActive) => {
      return isActive === '활성' ? 'active' : 'inactive'
    }

    /**
     * 날짜 포맷
     */
    const formatDate = (dateString) => {
      const date = new Date(dateString)
      const year = date.getFullYear()
      const month = String(date.getMonth() + 1).padStart(2, '0')
      const day = String(date.getDate()).padStart(2, '0')
      return `${year}-${month}-${day}`
    }

    onMounted(() => {
      getUsers()
    })

    return {
      // 상태
      users, loading, searchQuery, searchType,
      currentPage, totalPages, totalCount, pageSize,
      userPopupVisible, editUser,
      // 계산값
      visiblePages,
      // 메서드
      getUsers, searchUsers, goToPage, goToAddUser, editUserHandler, deleteUser, formatDate, getSearchPlaceholder, getStatusClass, handleUserSaved, handleRefreshList, confirmDelete
    }
  }
}
</script>

<style scoped>
/* Users-specific styles only */
.users-page {
  min-height: 100vh;
  background: var(--bg-primary);
}

/* Users Header */
.users-header {
  background: var(--bg-white);
  border-bottom: 1px solid var(--border-light);
  padding: 24px 32px;
}

.users-header-container {
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 24px;
}

/* 헤더 검색 영역 조정 */
.users-header .search-group {
  max-width: 600px;
  flex: 1;
}

.users-header .search-input {
  font-size: 14px;
  padding: 8px 12px;
}

.users-header .search-btn {
  padding: 8px 12px;
  min-width: 40px;
}

.users-header .search-select {
  font-size: 14px;
  padding: 8px 12px;
  border: 1px solid var(--border-color);
  border-radius: 6px;
  background: var(--bg-white);
  color: var(--text-primary);
  cursor: pointer;
}

/* 헤더 버튼들 조정 */
.users-header .add-btn {
  padding: 8px 16px;
  font-size: 14px;
}

/* 헤더와 테이블 간격 */
.users-content {
  padding: 24px 0;
}

/* 사용자 테이블 그리드 컬럼 조정 */
.board-table .table-header {
  display: grid;
  grid-template-columns: minmax(80px, 100px) minmax(200px, 250px) minmax(160px, 180px) 2fr minmax(120px, 150px) minmax(100px, 120px) minmax(140px, 160px) minmax(120px, 140px);
  background: var(--primary-color) !important;
  border-bottom: none !important;
}

.board-table .table-row {
  display: grid;
  grid-template-columns: minmax(80px, 100px) minmax(200px, 250px) minmax(160px, 180px) 2fr minmax(120px, 150px) minmax(100px, 120px) minmax(140px, 160px) minmax(120px, 140px);
}

.board-table .table-header .table-cell {
  color: white !important;
  font-weight: 600 !important;
  justify-content: center !important;
}

/* 테이블 셀 구분선 */
.board-table .table-header .table-cell:not(:last-child) {
  border-right: 1px solid rgba(255, 255, 255, 0.2) !important;
}

.board-table .table-row .table-cell:not(:last-child) {
  border-right: 1px solid var(--border-light) !important;
}

.board-table .table-row .table-cell:first-child,
.board-table .table-row .table-cell:nth-child(2),
.board-table .table-row .table-cell:nth-child(3),
.board-table .table-row .table-cell:nth-child(5),
.board-table .table-row .table-cell:nth-child(6),
.board-table .table-row .table-cell:nth-child(7),
.board-table .table-row .table-cell:nth-child(8) {
  justify-content: center !important;
}

/* 상태 배지 스타일 */
.status-badge {
  padding: 4px 8px;
  border-radius: 12px;
  font-size: 12px;
  font-weight: 500;
}

.status-badge.active {
  background: #dcfce7;
  color: #166534;
}

.status-badge.inactive {
  background: #fee2e2;
  color: #dc2626;
}

/* 액션 버튼 스타일 */
.action-buttons {
  display: flex;
  gap: 4px;
  justify-content: center;
}

.btn-danger {
  background: #ef4444;
  color: white;
  border: none;
}

.btn-danger:hover {
  background: #dc2626;
}

/* 반응형 테이블 */
@media (max-width: 1200px) {
  .board-table .table-header,
  .board-table .table-row {
    grid-template-columns: minmax(70px, 90px) minmax(180px, 220px) minmax(120px, 150px) 2fr minmax(100px, 130px) minmax(80px, 100px) minmax(120px, 140px) minmax(100px, 120px);
  }
}

@media (max-width: 768px) {
  .board-table .table-header,
  .board-table .table-row {
    grid-template-columns: 1fr;
    gap: 8px;
  }
  
  .board-table .table-cell {
    padding: 8px 0;
    border-right: none !important;
    border-bottom: 1px solid var(--border-light);
  }
  
  .board-table .table-cell:before {
    content: attr(data-label) ": ";
    font-weight: 600;
    color: var(--text-primary);
    margin-right: 8px;
  }
  
  .board-table .table-header {
    display: none;
  }
}
</style>
