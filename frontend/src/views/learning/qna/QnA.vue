<template>
  <div class="qna-page">
    <!-- 상단 헤더 -->
    <header class="qna-header">
      <div class="qna-header-container">
        <!-- 검색 및 필터 영역 -->
        <div class="search-filter-group">
          <!-- 검색 -->
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
              @keyup.enter="searchQnAs"
            >
            <button @click="searchQnAs" class="search-btn btn btn-primary">
              <i class="fas fa-search"></i>
            </button>
          </div>
          
          <!-- 필터 -->
          <div class="filter-group">
            <select v-model="statusFilter" class="filter-select form-input" style="width: 120px; margin-right: 8px;">
              <option value="all">전체 상태</option>
              <option value="pending">답변 대기</option>
              <option value="completed">답변 완료</option>
            </select>
            <select v-model="courseFilter" class="filter-select form-input" style="width: 150px; margin-right: 8px;">
              <option value="all">전체 강의</option>
              <option value="vue">Vue.js 기초</option>
              <option value="react">React 심화</option>
              <option value="js">JavaScript ES6</option>
            </select>
          </div>
        </div>
      </div>
    </header>

    <!-- 메인 컨텐츠 -->
    <div class="qna-content">
      <!-- Q&A 테이블 -->
      <div class="qna-table-wrapper">
        <template v-if="loading">
          <div class="loading-state">
            <i class="fas fa-spinner fa-spin"></i>
            로딩 중...
          </div>
        </template>

        <template v-else-if="qnaList.length === 0">
          <div class="empty-state">
            <i class="fas fa-question-circle"></i>
            등록된 질문이 없습니다.
          </div>
        </template>

        <template v-else>
          <div class="board-table">
            <div class="table-header">
              <div class="table-cell">번호</div>
              <div class="table-cell">질문 제목</div>
              <div class="table-cell">작성자</div>
              <div class="table-cell">강의명</div>
              <div class="table-cell">상태</div>
              <div class="table-cell">작성일</div>
            </div>
            <div 
              v-for="qna in qnaList" 
              :key="qna.qnaId"
              class="qna-item"
            >
              <div 
                class="table-row"
                :class="{ 'selected': selectedQnA?.qnaId === qna.qnaId }"
                @click="selectQnA(qna)"
              >
                <div class="table-cell">{{ qna.qnaId }}</div>
                <div class="table-cell">
                  <span class="board-title" :title="qna.title">{{ qna.title }}</span>
                </div>
                <div class="table-cell">{{ qna.writer }}</div>
                <div class="table-cell">{{ qna.courseName }}</div>
                <div class="table-cell">
                  <span class="status-badge" :class="getStatusClass(qna.status)">
                    {{ getStatusText(qna.status) }}
                  </span>
                </div>
                <div class="table-cell">{{ formatDate(qna.createdDttm) }}</div>
              </div>
              
              <!-- 선택된 Q&A 답변 영역 (게시글 바로 밑) -->
              <div class="qna-detail-section" v-if="selectedQnA?.qnaId === qna.qnaId">
                <div class="answer-card">
                  <!-- 질문 내용 간단 표시 -->
                  <div class="question-summary">
                    <div class="question-header">
                      <div class="question-meta">
                        <span class="writer">{{ selectedQnA.writer }}</span>
                        <span class="date">{{ formatDate(selectedQnA.createdDttm) }}</span>
                      </div>
                      <button @click="closeDetail" class="close-btn">
                        <i class="fas fa-times"></i>
                      </button>
                    </div>
                    <div class="question-text">{{ selectedQnA.content }}</div>
                  </div>

                  <!-- 기존 답변 (있는 경우) -->
                  <div class="existing-answer" v-if="selectedQnA.answer">
                    <div class="answer-label">답변</div>
                    <div class="answer-text">{{ selectedQnA.answer }}</div>
                    <div class="answer-meta">
                      <span>{{ selectedQnA.answerer || '관리자' }} · {{ formatDate(selectedQnA.answeredDttm) }}</span>
                    </div>
                  </div>

                  <!-- 답변 작성 폼 -->
                  <div class="answer-form">
                    <div class="answer-label">답변 작성</div>
                    <textarea 
                      v-model="answerContent"
                      placeholder="답변을 작성해주세요..."
                      class="answer-textarea"
                      rows="4"
                    ></textarea>
                    
                    <div class="answer-actions">
                      <button 
                        @click="saveAnswer" 
                        class="btn btn-sm btn-secondary"
                        :disabled="!answerContent.trim() || isSaving"
                      >
                        {{ isSaving ? '임시저장 중...' : '임시저장' }}
                      </button>
                      <button 
                        @click="markAsCompleted" 
                        class="btn btn-sm btn-primary"
                        :disabled="selectedQnA.status === 'completed' || isSaving"
                      >
                        {{ isSaving ? '등록 중...' : '등록' }}
                      </button>
                    </div>
                  </div>
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

  </div>
</template>

<script>
import { ref, onMounted, computed } from 'vue'
import { useRouter } from 'vue-router'
import { api } from '/src/util/api.js'
import { getQnAMockData } from '../../mock/qnaMockData.js'
import { callApiOrMock } from '/src/util/mockConfig.js'
import Swal from 'sweetalert2'
import '../common.css'

export default {
  name: 'QnA',
  components: {
  },
  setup() {
    const router = useRouter()

    // 상태
    const qnaList = ref([])
    const loading = ref(false)
    const searchQuery = ref('')
    const searchType = ref('all')
    const statusFilter = ref('all')
    const courseFilter = ref('all')
    
    // 선택된 Q&A
    const selectedQnA = ref(null)
    const answerContent = ref('')
    const isSaving = ref(false)


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

    // 알러트 표시 함수 (SweetAlert2)
    const showAlert = async (message, type = 'info', title = '알림', callback = null) => {
      const result = await Swal.fire({
        title: title,
        text: message,
        icon: type,
        confirmButtonText: '확인',
        allowOutsideClick: false,
        allowEscapeKey: true
      })
      
      if (callback && typeof callback === 'function') {
        callback(result)
      }
    }



    /**
     * Q&A 목록 로드
     */
    const getQnAList = async () => {
      try {
        loading.value = true
        
        // API 요청 파라미터 구성
        const requestData = {
          page: currentPage.value,
          size: pageSize.value,
          searchType: searchType.value,
          searchQuery: searchQuery.value,
          statusFilter: statusFilter.value,
          courseFilter: courseFilter.value
        }
        
        // API 호출 또는 목업 데이터 사용
        const res = await callApiOrMock(
          // 실제 API 호출
          () => api.post('/qna/getQnAList', requestData),
          // 목업 데이터 호출
          () => getQnAMockData(requestData)
        )
        
        // 응답 데이터 처리
        if (res && res.body) {
          qnaList.value = res.body.qnaList || []
          totalCount.value = res.body.pagination?.totalElements || 0
          totalPages.value = res.body.pagination?.totalPages || 1
        } else {
          qnaList.value = []
          totalCount.value = 0
          totalPages.value = 1
        }
      } catch (e) {
        console.error('Q&A 목록 로드 실패:', e)
        qnaList.value = []
        totalCount.value = 0
        totalPages.value = 1
      } finally {
        loading.value = false
      }
    }

    /**
     * 검색
     */
    const searchQnAs = () => {
      currentPage.value = 1
      getQnAList()
    }

    /**
     * 페이지 이동
     */
    const goToPage = (page) => {
      if (page < 1 || page > totalPages.value) return
      currentPage.value = page
      getQnAList()
    }

    /**
     * Q&A 선택 (토글 기능)
     */
    const selectQnA = (qna) => {
      // 이미 선택된 Q&A를 다시 클릭하면 닫기
      if (selectedQnA.value?.qnaId === qna.qnaId) {
        closeDetail()
        return
      }
      
      // 새로운 Q&A 선택
      selectedQnA.value = qna
      answerContent.value = qna.answer || ''
    }

    /**
     * 상세 패널 닫기
     */
    const closeDetail = () => {
      selectedQnA.value = null
      answerContent.value = ''
    }

    /**
     * 답변 임시저장
     */
    const saveAnswer = async () => {
      if (!answerContent.value.trim() || !selectedQnA.value) return
      
      try {
        isSaving.value = true
        
        // 답변 임시저장 API 호출
        const response = await callApiOrMock(
          () => api.post('/qna/saveAnswer', {
            qnaId: selectedQnA.value.qnaId,
            answer: answerContent.value
          }),
          () => Promise.resolve({ success: true })
        )
        
        if (response.success) {
          // 로컬 상태 업데이트
          selectedQnA.value.answer = answerContent.value
          selectedQnA.value.answerer = '관리자'
          selectedQnA.value.answeredDttm = new Date().toISOString()
          
          // 목록에서도 업데이트
          const index = qnaList.value.findIndex(q => q.qnaId === selectedQnA.value.qnaId)
          if (index !== -1) {
            qnaList.value[index] = { ...selectedQnA.value }
          }
          
          showAlert('답변이 임시저장되었습니다.', 'success', '임시저장 완료')
        }
      } catch (error) {
        console.error('답변 임시저장 실패:', error)
        showAlert('답변 임시저장에 실패했습니다.', 'error', '임시저장 실패')
      } finally {
        isSaving.value = false
      }
    }

    /**
     * 답변 등록
     */
    const markAsCompleted = async () => {
      if (!selectedQnA.value) return
      
      try {
        isSaving.value = true
        
        const response = await callApiOrMock(
          () => api.post('/qna/markCompleted', {
            qnaId: selectedQnA.value.qnaId
          }),
          () => Promise.resolve({ success: true })
        )
        
        if (response.success) {
          selectedQnA.value.status = 'completed'
          
          // 목록에서도 업데이트
          const index = qnaList.value.findIndex(q => q.qnaId === selectedQnA.value.qnaId)
          if (index !== -1) {
            qnaList.value[index].status = 'completed'
          }
          
          showAlert('답변이 등록되었습니다.', 'success', '등록 완료')
        }
      } catch (error) {
        console.error('답변 등록 실패:', error)
        showAlert('답변 등록에 실패했습니다.', 'error', '등록 실패')
      } finally {
        isSaving.value = false
      }
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
     * 상태 클래스 매핑
     */
    const getStatusClass = (status) => {
      return status === 'completed' ? 'completed' : 'pending'
    }

    /**
     * 상태 텍스트 매핑
     */
    const getStatusText = (status) => {
      return status === 'completed' ? '답변 완료' : '답변 대기'
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
      getQnAList()
    })

    return {
      // 상태
      qnaList, loading, searchQuery, searchType, statusFilter, courseFilter,
      selectedQnA, answerContent, isSaving,
      currentPage, totalPages, totalCount, pageSize,
      // 알러트 팝업 상태
      // 계산값
      visiblePages,
      // 메서드
      getQnAList, searchQnAs, goToPage, selectQnA, closeDetail, saveAnswer, markAsCompleted, formatDate, getSearchPlaceholder, getStatusClass, getStatusText,
      // 알러트 메서드
      showAlert
    }
  }
}
</script>

<style scoped>
/* QnA-specific styles */
.qna-page {
  min-height: 100vh;
  background: var(--bg-primary);
}

/* QnA Header */
.qna-header {
  background: var(--bg-white);
  border-bottom: 1px solid var(--border-light);
  padding: 24px 32px;
}

.qna-header-container {
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 24px;
}

/* 검색 및 필터 영역 */
.search-filter-group {
  display: flex;
  flex-direction: column;
  gap: 12px;
  flex: 1;
}

.search-group {
  display: flex;
  align-items: center;
  gap: 8px;
}

.filter-group {
  display: flex;
  align-items: center;
  gap: 8px;
}

.qna-header .search-group {
  max-width: 600px;
}

.qna-header .search-input {
  font-size: 14px;
  padding: 8px 12px;
}

.qna-header .search-btn {
  padding: 8px 12px;
  min-width: 40px;
}

.qna-header .search-select,
.qna-header .filter-select {
  font-size: 14px;
  padding: 8px 12px;
  border: 1px solid var(--border-color);
  border-radius: 6px;
  background: var(--bg-white);
  color: var(--text-primary);
  cursor: pointer;
}


/* 메인 레이아웃 */
.qna-content {
  padding: 24px 0;
}

.qna-table-wrapper {
  margin-bottom: 24px;
}

/* QnA 아이템 컨테이너 */
.qna-item {
  border-bottom: 1px solid var(--border-light);
}

.qna-item:last-child {
  border-bottom: none;
}

/* QnA 테이블 - 게시판과 동일한 구조 */
.board-table .table-header {
  display: grid;
  grid-template-columns: minmax(80px, 100px) 2fr minmax(120px, 150px) minmax(120px, 150px) minmax(100px, 120px) minmax(120px, 140px);
  background: var(--primary-color) !important;
  border-bottom: none !important;
}

.board-table .table-row {
  display: grid;
  grid-template-columns: minmax(80px, 100px) 2fr minmax(120px, 150px) minmax(120px, 150px) minmax(100px, 120px) minmax(120px, 140px);
  cursor: pointer;
  transition: all 0.2s ease;
}

.board-table .table-row:hover {
  background: var(--bg-light);
}

.board-table .table-row.selected {
  background: #dbeafe;
  border-left: 4px solid var(--primary-color);
}

.board-table .table-header .table-cell {
  color: white !important;
  font-weight: 600 !important;
  justify-content: center !important;
}

.board-table .table-row .table-cell:first-child,
.board-table .table-row .table-cell:nth-child(3),
.board-table .table-row .table-cell:nth-child(4),
.board-table .table-row .table-cell:nth-child(5),
.board-table .table-row .table-cell:nth-child(6) {
  justify-content: center !important;
}

/* 테이블 셀 구분선 */
.board-table .table-header .table-cell:not(:last-child) {
  border-right: 1px solid rgba(255, 255, 255, 0.2) !important;
}

.board-table .table-row .table-cell:not(:last-child) {
  border-right: 1px solid var(--border-light) !important;
}

/* 상태 배지 */
.status-badge {
  padding: 4px 8px;
  border-radius: 12px;
  font-size: 12px;
  font-weight: 500;
}

.status-badge.pending {
  background: #fef3c7;
  color: #92400e;
}

.status-badge.completed {
  background: #dcfce7;
  color: #166534;
}

/* 게시글 바로 밑 답변 영역 - 간단하고 작게 */
.qna-detail-section {
  margin: 0;
  padding: 0 16px 16px 16px;
  background: #f8fafc;
}

.answer-card {
  background: var(--bg-white);
  border-radius: 6px;
  border: 1px solid var(--border-light);
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
  overflow: hidden;
}

/* 질문 요약 */
.question-summary {
  padding: 12px 16px;
  border-bottom: 1px solid var(--border-light);
  background: #f8fafc;
}

.question-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 8px;
}

.question-meta {
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 12px;
  color: var(--text-secondary);
}

.writer {
  font-weight: 500;
  color: var(--text-primary);
}

.date {
  color: var(--text-secondary);
}

.close-btn {
  padding: 4px 6px;
  min-width: auto;
  font-size: 12px;
  background: none;
  border: none;
  color: var(--text-secondary);
  cursor: pointer;
  border-radius: 4px;
  transition: all 0.2s ease;
  display: flex;
  align-items: center;
  justify-content: center;
  width: 20px;
  height: 20px;
}

.close-btn:hover {
  background: var(--bg-light);
  color: var(--text-primary);
}

.question-text {
  font-size: 14px;
  line-height: 1.5;
  color: var(--text-primary);
  max-height: 60px;
  overflow: hidden;
  display: -webkit-box;
  -webkit-line-clamp: 3;
  line-clamp: 3;
  -webkit-box-orient: vertical;
}

/* 기존 답변 */
.existing-answer {
  padding: 12px 16px;
  border-bottom: 1px solid var(--border-light);
  background: #f0f9ff;
}

.answer-label {
  font-size: 12px;
  font-weight: 600;
  color: var(--primary-color);
  margin-bottom: 6px;
  text-transform: uppercase;
  letter-spacing: 0.5px;
}

.answer-text {
  font-size: 14px;
  line-height: 1.5;
  color: var(--text-primary);
  margin-bottom: 6px;
  max-height: 80px;
  overflow: hidden;
  display: -webkit-box;
  -webkit-line-clamp: 4;
  line-clamp: 4;
  -webkit-box-orient: vertical;
}

.answer-meta {
  font-size: 11px;
  color: var(--text-secondary);
}

/* 답변 작성 폼 */
.answer-form {
  padding: 12px 16px;
}

.answer-textarea {
  width: 100%;
  resize: vertical;
  margin-bottom: 8px;
  min-height: 80px;
  font-size: 14px;
  padding: 8px 12px;
  border: 1px solid var(--border-color);
  border-radius: 4px;
  background: var(--bg-white);
}

.answer-actions {
  display: flex;
  gap: 8px;
  justify-content: flex-end;
}

.answer-actions .btn {
  padding: 6px 12px;
  font-size: 12px;
  min-width: auto;
}

/* 반응형 */
@media (max-width: 768px) {
  .qna-header-container {
    flex-direction: column;
    align-items: stretch;
    gap: 16px;
  }
  
  .search-filter-group {
    gap: 8px;
  }
  
  .search-group,
  .filter-group {
    flex-wrap: wrap;
  }
  
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
  
  .info-grid {
    grid-template-columns: 1fr;
    gap: 12px;
  }
  
  .answer-actions {
    flex-direction: column;
  }
  
  .answer-actions .btn {
    width: 100%;
  }
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
