<template>
  <div class="learning-board-page">
    <!-- 상단 헤더 -->
    <header class="learning-board_header">
      <div class="learning-header_container">
        <div class="learning-header_left">
          <h1 class="learning-header_title eng">강의 게시판</h1>
          <p class="learning-header_subtitle">
            강의 관련 질문과 답변을 공유하는 공간입니다.
          </p>
        </div>
        <div class="learning-header_right">
          <button @click="goBack" class="learning-back_btn">
            <i class="fas fa-arrow-left"></i>
            메인으로
          </button>
        </div>
      </div>
    </header>

    <!-- 메인 컨텐츠 -->
    <main class="learning-board_main">
      <div class="learning-board_container">
        <!-- 검색 및 필터 -->
        <div class="learning-board_search">
          <div class="learning-search_group">
            <input 
              type="text" 
              v-model="searchQuery"
              placeholder="제목, 내용, 작성자로 검색..."
              class="learning-search_input form-input"
              @keyup.enter="searchBoards"
            >
            <button @click="searchBoards" class="learning-search_btn btn btn-primary">
              <i class="fas fa-search"></i>
            </button>
          </div>
          <button @click="goToWrite" class="learning-write_btn btn btn-primary">
            <i class="fas fa-pen"></i>
            글쓰기
          </button>
        </div>

        <!-- 게시글 테이블 (Element Plus) -->
        <div class="learning-board_table">
          <template v-if="loading">
            <div class="learning-loading">
              <i class="fas fa-spinner fa-spin"></i>
              로딩 중...
            </div>
          </template>

          <template v-else-if="boardList.length === 0">
            <div class="learning-empty">
              <i class="fas fa-inbox"></i>
              등록된 게시글이 없습니다.
            </div>
          </template>

          <template v-else>
            <div class="board-table">
              <div class="table-header">
                <div class="table-cell">번호</div>
                <div class="table-cell">제목</div>
                <div class="table-cell">작성자</div>
                <div class="table-cell">조회</div>
                <div class="table-cell">작성일</div>
              </div>
              <div 
                v-for="board in boardList" 
                :key="board.boardId" 
                class="table-row"
                @click="goToDetail(board.boardId)"
              >
                <div class="table-cell">{{ board.boardId }}</div>
                <div class="table-cell">
                  <span class="board-title" :title="board.title">{{ board.title }}</span>
                </div>
                <div class="table-cell">{{ board.writer }}</div>
                <div class="table-cell">{{ board.viewCount }}</div>
                <div class="table-cell">{{ formatDate(board.createdDttm) }}</div>
              </div>
            </div>
          </template>
        </div>

        <!-- 페이지네이션 -->
        <div class="learning-pagination">
          <button 
            @click="goToPage(currentPage - 1)"
            :disabled="currentPage <= 1"
            class="learning-page_btn"
          >
            <i class="fas fa-chevron-left"></i>
            이전
          </button>
          
          <div class="learning-page_numbers">
            <button 
              v-for="page in visiblePages" 
              :key="page"
              :class="['learning-page_number', { active: page === currentPage }]"
              @click="goToPage(page)"
            >
              {{ page }}
            </button>
          </div>
          
          <button 
            @click="goToPage(currentPage + 1)"
            :disabled="currentPage >= totalPages"
            class="learning-page_btn"
          >
            다음
            <i class="fas fa-chevron-right"></i>
          </button>
        </div>
      </div>
    </main>
  </div>
</template>

<script>
import { ref, onMounted, computed } from 'vue'
import { useRouter } from 'vue-router'
import { api } from '/src/util/api.js'
import '../common.css'

export default {
  name: 'LearningBoard',
  setup() {
    const router = useRouter()

    // 상태
    const boardList = ref([
      {
        boardId: 1,
        title: 'Vue.js 기초 강의에 대한 질문입니다',
        content: 'Vue.js 컴포넌트 구조에 대해 궁금한 점이 있습니다.',
        writer: '김학생',
        viewCount: 15,
        createdDttm: '2024-01-15T10:30:00'
      },
      {
        boardId: 2,
        title: 'React와 Vue.js의 차이점은 무엇인가요?',
        content: '두 프레임워크의 장단점을 비교해보고 싶습니다.',
        writer: '이학생',
        viewCount: 23,
        createdDttm: '2024-01-14T14:20:00'
      },
      {
        boardId: 3,
        title: 'JavaScript ES6 문법 정리',
        content: 'ES6의 주요 문법들을 정리해보았습니다.',
        writer: '박학생',
        viewCount: 45,
        createdDttm: '2024-01-13T09:15:00'
      },
      {
        boardId: 4,
        title: '프로젝트 과제 제출 방법',
        content: '프로젝트 과제를 어떻게 제출해야 하는지 궁금합니다.',
        writer: '최학생',
        viewCount: 8,
        createdDttm: '2024-01-12T16:45:00'
      },
      {
        boardId: 5,
        title: '코딩 테스트 준비 방법',
        content: '취업을 위한 코딩 테스트 준비 방법을 공유합니다.',
        writer: '정학생',
        viewCount: 67,
        createdDttm: '2024-01-11T11:30:00'
      }
    ])
    const loading = ref(false)
    const searchQuery = ref('')

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
     * 게시글 목록 로드
     */
    const getBoardList = async () => {
      try {
        loading.value = true
        const res = await api.post('/board/getBoardList', {
          page: currentPage.value,
          size: pageSize.value,
          query: searchQuery.value
        })

        const list =
          Array.isArray(res) ? res :
          Array.isArray(res?.body) ? res.body :
          []

        boardList.value = list
      } catch (e) {
        console.error('게시글 목록 로드 실패:', e)
        alert('게시글을 불러오는데 실패했습니다.')
      } finally {
        loading.value = false
      }
    }

    /**
     * 검색
     */
    const searchBoards = () => {
      currentPage.value = 1
      getBoardList()
    }

    /**
     * 페이지 이동
     */
    const goToPage = (page) => {
      if (page < 1 || page > totalPages.value) return
      currentPage.value = page
      getBoardList()
    }

    /**
     * 상세보기 이동
     */
    const goToDetail = (boardId) => {
      router.push(`/learning/board/${boardId}`)
    }

    /**
     * 글쓰기 이동
     */
    const goToWrite = () => {
      router.push('/learning/board/write')
    }

    /**
     * 메인으로
     */
    const goBack = () => {
      router.push('/')
    }

    /**
     * 날짜 포맷
     */
    const formatDate = (dateString) => {
      const date = new Date(dateString)
      return date.toLocaleDateString('ko-KR', { year: 'numeric', month: '2-digit', day: '2-digit' })
    }

    onMounted(() => {
      getBoardList()
    })

    return {
      // 상태
      boardList, loading, searchQuery,
      currentPage, totalPages, totalCount, pageSize,
      // 계산값
      visiblePages,
      // 메서드
      getBoardList, searchBoards, goToPage, goToDetail, goToWrite, goBack, formatDate
    }
  }
}
</script>

<style scoped>
/* ============================================
   LEARNING BOARD STYLES
   ============================================ */

.learning-board-page {
  min-height: 100vh;
  background: var(--bg-primary);
}

/* Header Styles */
.learning-board_header {
  background: var(--bg-white);
  border-bottom: 1px solid var(--border-light);
  padding: 24px 32px;
}

.learning-header_container {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.learning-header_title {
  font-size: 28px;
  font-weight: 300;
  color: var(--text-primary);
  margin: 0 0 8px 0;
}

.learning-header_subtitle {
  font-size: 14px;
  color: var(--text-secondary);
  margin: 0;
}

.learning-back_btn {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 10px 16px;
  background: var(--primary-color);
  color: white;
  border: none;
  border-radius: 6px;
  cursor: pointer;
  transition: all 0.3s ease;
}

.learning-back_btn:hover {
  background: var(--primary-hover);
}

/* Main Content */
.learning-board_main {
  padding: 32px;
}

.learning-board_container {
  max-width: 1200px;
  margin: 0 auto;
}

/* Search Section */
.learning-board_search {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 24px;
  gap: 16px;
}

.learning-search_group {
  display: flex;
  align-items: center;
  gap: 8px;
  flex: 1;
  max-width: 400px;
}

.learning-search_input {
  flex: 1;
  padding: 10px 16px;
}

.learning-search_btn {
  padding: 10px 16px;
  min-width: 50px;
}

.learning-write_btn {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 10px 20px;
  white-space: nowrap;
}

/* Board Table */
.board-table {
  background: var(--bg-white);
  border-radius: 8px;
  overflow: hidden;
  box-shadow: var(--shadow-sm);
}

.table-header {
  display: grid;
  grid-template-columns: 80px 1fr 120px 80px 140px;
  background: var(--bg-light);
  border-bottom: 1px solid var(--border-light);
}

.table-row {
  display: grid;
  grid-template-columns: 80px 1fr 120px 80px 140px;
  border-bottom: 1px solid var(--bg-light);
  transition: background-color 0.3s ease;
  cursor: pointer;
}

.table-row:hover {
  background: var(--bg-light);
}

.table-cell {
  padding: 16px 20px;
  display: flex;
  align-items: center;
  color: var(--text-primary);
}

.table-header .table-cell {
  font-weight: 600;
  color: var(--text-primary);
}

.board-title {
  font-weight: 500;
  color: var(--text-primary);
  text-overflow: ellipsis;
  overflow: hidden;
  white-space: nowrap;
}

/* Loading & Empty States */
.learning-loading,
.learning-empty {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 60px 20px;
  color: var(--text-secondary);
  background: var(--bg-white);
  border-radius: 8px;
}

.learning-loading i,
.learning-empty i {
  font-size: 48px;
  margin-bottom: 16px;
  color: var(--primary-color);
}

.learning-loading i {
  animation: spin 1s linear infinite;
}

@keyframes spin {
  from { transform: rotate(0deg); }
  to { transform: rotate(360deg); }
}

/* Pagination */
.learning-pagination {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 8px;
  margin-top: 24px;
}

.learning-page_btn {
  display: flex;
  align-items: center;
  gap: 4px;
  padding: 8px 16px;
  background: var(--bg-white);
  border: 1px solid var(--border-color);
  border-radius: 6px;
  color: var(--text-primary);
  cursor: pointer;
  transition: all 0.3s ease;
}

.learning-page_btn:hover:not(:disabled) {
  background: var(--primary-color);
  color: white;
  border-color: var(--primary-color);
}

.learning-page_btn:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}

.learning-page_numbers {
  display: flex;
  gap: 4px;
}

.learning-page_number {
  width: 40px;
  height: 40px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: var(--bg-white);
  border: 1px solid var(--border-color);
  border-radius: 6px;
  color: var(--text-primary);
  cursor: pointer;
  transition: all 0.3s ease;
}

.learning-page_number:hover {
  background: var(--primary-color);
  color: white;
  border-color: var(--primary-color);
}

.learning-page_number.active {
  background: var(--primary-color);
  color: white;
  border-color: var(--primary-color);
}

/* Responsive */
@media (max-width: 768px) {
  .learning-board_main {
    padding: 16px;
  }
  
  .learning-board_search {
    flex-direction: column;
    align-items: stretch;
  }
  
  .learning-search_group {
    max-width: none;
  }
  
  .table-header,
  .table-row {
    grid-template-columns: 60px 1fr 80px 60px 100px;
  }
  
  .table-cell {
    padding: 12px 8px;
    font-size: 14px;
  }
  
  .learning-pagination {
    flex-wrap: wrap;
  }
}
</style>
