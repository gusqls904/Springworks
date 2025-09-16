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
              class="learning-search_input"
              @keyup.enter="searchBoards"
            >
            <button @click="searchBoards" class="learning-search_btn">
              <i class="fas fa-search"></i>
            </button>
          </div>
          <button @click="goToWrite" class="learning-write_btn">
            <i class="fas fa-pen"></i>
            글쓰기
          </button>
        </div>

        <!-- 게시글 테이블 -->
        <div class="learning-board_table">
          <div class="learning-table_header">
            <div class="learning-col_no">번호</div>
            <div class="learning-col_title">제목</div>
            <div class="learning-col_writer">작성자</div>
            <div class="learning-col_date">작성일</div>
            <div class="learning-col_views">조회</div>
          </div>
          
          <div v-if="loading" class="learning-loading">
            <i class="fas fa-spinner fa-spin"></i>
            로딩 중...
          </div>
          
          <div v-else-if="boardList.length === 0" class="learning-empty">
            <i class="fas fa-inbox"></i>
            등록된 게시글이 없습니다.
          </div>
          
          <div 
            v-else
            v-for="board in boardList" 
            :key="board.boardId"
            class="learning-table_row"
            @click="goToDetail(board.boardId)"
          >
            <div class="learning-col_no">{{ board.boardId }}</div>
            <div class="learning-col_title">
              {{ board.title }}
              <span v-if="board.commentCount > 0" class="learning-comment_count">
                [{{ board.commentCount }}]
              </span>
            </div>
            <div class="learning-col_writer">{{ board.writer }}</div>
            <div class="learning-col_date">{{ formatDate(board.createdAt) }}</div>
            <div class="learning-col_views">{{ board.viewCount }}</div>
          </div>
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
import './LearningBoard.css'

export default {
  name: 'LearningBoard',
  setup() {
    const router = useRouter()
    
    // 게시글 목록
    const boardList = ref([])
    const loading = ref(false)
    
    // 검색
    const searchQuery = ref('')
    
    // 페이지네이션
    const currentPage = ref(1)
    const totalPages = ref(1)
    const totalCount = ref(0)
    const pageSize = ref(10)
    
    const visiblePages = computed(() => {
      const start = Math.max(1, currentPage.value - 2)
      const end = Math.min(totalPages.value, start + 4)
      return Array.from({ length: end - start + 1 }, (_, i) => start + i)
    })
    
    // 게시글 목록 로드
    const loadBoardList = async () => {
      try {
        loading.value = true
        // 임시 데이터
        boardList.value = [
          {
            boardId: 1,
            title: 'Vue.js 강의 질문입니다',
            writer: '김학생',
            createdAt: '2024-01-15',
            viewCount: 15,
            commentCount: 3
          },
          {
            boardId: 2,
            title: 'React vs Vue 비교',
            writer: '이학생',
            createdAt: '2024-01-14',
            viewCount: 23,
            commentCount: 1
          }
        ]
        totalPages.value = 1
        totalCount.value = 2
        
      } catch (error) {
        console.error('게시글 목록 로드 실패:', error)
        alert('게시글을 불러오는데 실패했습니다.')
      } finally {
        loading.value = false
      }
    }
    
    // 검색
    const searchBoards = () => {
      currentPage.value = 1
      loadBoardList()
    }
    
    // 페이지 이동
    const goToPage = (page) => {
      if (page >= 1 && page <= totalPages.value) {
        currentPage.value = page
        loadBoardList()
      }
    }
    
    // 게시글 상세보기
    const goToDetail = (boardId) => {
      router.push(`/learning/board/${boardId}`)
    }
    
    // 글쓰기 페이지
    const goToWrite = () => {
      router.push('/learning/board/write')
    }
    
    // 메인으로 돌아가기
    const goBack = () => {
      router.push('/')
    }
    
    // 날짜 포맷팅
    const formatDate = (dateString) => {
      const date = new Date(dateString)
      return date.toLocaleDateString('ko-KR', {
        year: 'numeric',
        month: '2-digit',
        day: '2-digit'
      })
    }
    
    onMounted(() => {
      loadBoardList()
    })
    
    return {
      boardList,
      loading,
      searchQuery,
      currentPage,
      totalPages,
      totalCount,
      pageSize,
      visiblePages,
      loadBoardList,
      searchBoards,
      goToPage,
      goToDetail,
      goToWrite,
      goBack,
      formatDate
    }
  }
}
</script>
