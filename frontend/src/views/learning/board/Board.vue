<template>
  <div class="board-page">
    <!-- 상단 헤더 -->
    <header class="board-header">
      <div class="board-header-container">
        <!-- 검색 영역 -->
        <div class="search-group">
          <select v-model="searchType" class="search-select form-input" style="width: 120px; margin-right: 8px;">
            <option value="all">전체</option>
            <option value="title">제목</option>
            <option value="content">내용</option>
            <option value="writer">작성자</option>
          </select>
          <input 
            type="text" 
            v-model="searchQuery"
            :placeholder="getSearchPlaceholder()"
            class="search-input form-input"
            @keyup.enter="searchBoards"
          >
          <button @click="searchBoards" class="search-btn btn btn-primary">
            <i class="fas fa-search"></i>
          </button>
        </div>
        
        <!-- 액션 버튼들 -->
        <div class="board-header-actions">
          <button @click="goToWrite" class="write-btn btn btn-primary">
            <i class="fas fa-pen"></i>
            글쓰기
          </button>
        </div>
      </div>
    </header>

    <!-- 메인 컨텐츠 -->
    <div class="board-content">
      <!-- 게시글 테이블 -->
      <div class="board-table-wrapper">
          <template v-if="loading">
            <div class="loading-state">
              <i class="fas fa-spinner fa-spin"></i>
              로딩 중...
            </div>
          </template>

          <template v-else-if="boardList.length === 0">
            <div class="empty-state">
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
  </div>
</template>

<script>
import { ref, onMounted, computed } from 'vue'
import { useRouter } from 'vue-router'
import { api } from '/src/util/api.js'
import { getBoardMockData } from '../../mock/boardMockData.js'
import { callApiOrMock } from '/src/util/mockConfig.js'
import '../common.css'

export default {
  name: 'LearningBoard',
  setup() {
    const router = useRouter()

    // 상태
    const boardList = ref([])
    // const boardList = ref([
    //   {
    //     boardId: 1,
    //     title: 'Vue.js 기초 강의에 대한 질문입니다',
    //     content: 'Vue.js 컴포넌트 구조에 대해 궁금한 점이 있습니다.',
    //     writer: '김학생',
    //     viewCount: 15,
    //     createdDttm: '2024-01-15T10:30:00'
    //   },
    //   {
    //     boardId: 2,
    //     title: 'React와 Vue.js의 차이점은 무엇인가요?',
    //     content: '두 프레임워크의 장단점을 비교해보고 싶습니다.',
    //     writer: '이학생',
    //     viewCount: 23,
    //     createdDttm: '2024-01-14T14:20:00'
    //   },
    //   {
    //     boardId: 3,
    //     title: 'JavaScript ES6 문법 정리',
    //     content: 'ES6의 주요 문법들을 정리해보았습니다.',
    //     writer: '박학생',
    //     viewCount: 45,
    //     createdDttm: '2024-01-13T09:15:00'
    //   },
    //   {
    //     boardId: 4,
    //     title: '프로젝트 과제 제출 방법',
    //     content: '프로젝트 과제를 어떻게 제출해야 하는지 궁금합니다.',
    //     writer: '최학생',
    //     viewCount: 8,
    //     createdDttm: '2024-01-12T16:45:00'
    //   },
    //   {
    //     boardId: 5,
    //     title: '코딩 테스트 준비 방법',
    //     content: '취업을 위한 코딩 테스트 준비 방법을 공유합니다.',
    //     writer: '정학생',
    //     viewCount: 67,
    //     createdDttm: '2024-01-11T11:30:00'
    //   }
    // ])
    
    const loading = ref(false)
    const searchQuery = ref('')
    const searchType = ref('all')

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
          () => api.post('/board/getBoardList', requestData),
          // 목업 데이터 호출
          () => getBoardMockData(requestData)
        )
        
        // 응답 데이터 처리
        if (res && res.body) {
          boardList.value = res.body.boardList || []
          totalCount.value = res.body.pagination?.totalElements || 0
          totalPages.value = res.body.pagination?.totalPages || 1
        } else {
          boardList.value = []
          totalCount.value = 0
          totalPages.value = 1
        }
      } catch (e) {
        console.error('게시글 목록 로드 실패:', e)
        boardList.value = []
        totalCount.value = 0
        totalPages.value = 1
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
     * 검색 플레이스홀더 동적 생성
     */
    const getSearchPlaceholder = () => {
      const placeholders = {
        all: '제목, 내용, 작성자로 검색...',
        title: '제목으로 검색...',
        content: '내용으로 검색...',
        writer: '작성자로 검색...'
      }
      return placeholders[searchType.value] || placeholders.all
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
      getBoardList()
    })

    return {
      // 상태
      boardList, loading, searchQuery, searchType,
      currentPage, totalPages, totalCount, pageSize,
      // 계산값
      visiblePages,
      // 메서드
      getBoardList, searchBoards, goToPage, goToDetail, goToWrite, formatDate, getSearchPlaceholder
    }
  }
}
</script>

<style scoped>
/* Board-specific styles only */
.board-header-container {
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 24px;
}

/* 헤더 검색 영역 조정 */
.board-header .search-group {
  max-width: 600px;
  flex: 1;
}

.board-header .search-input {
  font-size: 14px;
  padding: 8px 12px;
}

.board-header .search-btn {
  padding: 8px 12px;
  min-width: 40px;
}

.board-header .search-select {
  font-size: 14px;
  padding: 8px 12px;
  border: 1px solid var(--border-color);
  border-radius: 6px;
  background: var(--bg-white);
  color: var(--text-primary);
  cursor: pointer;
}

/* 헤더 버튼들 조정 */
.board-header .write-btn {
  padding: 8px 16px;
  font-size: 14px;
}

/* 헤더와 테이블 간격 */
.board-content {
  padding: 24px 0;
}

/* 테이블 헤더 색상 변경 - 글쓰기 버튼과 동일한 색상 */
.board-table .table-header {
  background: var(--primary-color) !important;
  border-bottom: none !important;
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

/* 제목 컬럼 제외하고 가운데 정렬 */
.board-table .table-row .table-cell:first-child,
.board-table .table-row .table-cell:nth-child(3),
.board-table .table-row .table-cell:nth-child(4),
.board-table .table-row .table-cell:nth-child(5) {
  justify-content: center !important;
}

/* 버튼 아이콘과 텍스트 간격 조정 */
.btn i {
  margin-right: 6px;
}

.btn i:last-child {
  margin-right: 0;
  margin-left: 6px;
}

/* 아이콘만 있는 버튼은 간격 없음 */
.btn:has(i:only-child) i {
  margin: 0;
}
</style>
