/**
 * ============================================
 * API 통신 모듈
 * ============================================
 * 
 * 📋 기능:
 * - HTTP 요청 메서드: GET, POST, PUT, DELETE
 * - 자동 토큰 인증 (로그인 전/후 헤더 자동 설정)
 * - 환경별 URL 자동 구성 (DEV/PROD)
 * - 응답 타입별 자동 처리 (JSON/Text/NoContent)
 * - 통합 에러 처리 (Body 유무에 따른 구분 처리)
 * 
 * 🔧 사용법:
 * - api.get('/users')           // GET 요청
 * - api.post('/users', data)    // POST 요청
 * - api.put('/users/1', data)   // PUT 요청
 * - api.delete('/users/1')      // DELETE 요청
 * ============================================
 */

import Swal from 'sweetalert2'

// ============================================
// 📡 1. 공통 API 요청 함수
// ============================================

/**
 * 공통 fetch 래퍼 함수
 * @param {string} url - 요청 URL
 * @param {Object} options - fetch 옵션
 * @returns {Promise<any>} - 응답 데이터
 */
async function apiRequest(url, options = {}) {
  // ============================================
  // 🔐 2. 헤더 설정 (로그인 전/후 자동 처리)
  // ============================================
  const headers = {
    'Content-Type': 'application/json',
    ...options.headers
  }

  // 로그인 후: Authorization 헤더 자동 추가
  const token = localStorage.getItem('accessToken')
  if (token) {
    headers.Authorization = `Bearer ${token}`
  }

  // ============================================
  // 🌐 3. URL 구성 (환경별 자동 처리)
  // ============================================
  const isDev = import.meta.env.DEV
  const fullUrl = url.startsWith('http')
    ? url  // 절대 URL인 경우 그대로 사용
    : (isDev 
        ? `/api${url}`  // 개발환경: 프록시 사용
        : `${window.location.origin}/api${url}`  // 운영환경: 절대경로 사용
      )

  // ============================================
  // 📤 4. HTTP 요청 실행
  // ============================================
  try {
    const response = await fetch(fullUrl, { 
      ...options, 
      headers 
    })

    // ============================================
    // ❌ 5. HTTP 오류 처리 (Body 유무에 따른 구분)
    // ============================================
    if (!response.ok) {
      
      // let errorMessage = `HTTP error! status: ${response.status}`
      // let errorType = 'UNKNOWN_ERROR'
      
      // try {
        // 응답 body가 있는지 확인
        const responseText = await response.text()
        
        if (responseText) {
          const errorData = JSON.parse(responseText)
          
          throw errorData
      //   } else {
      //     // 응답 body가 없음 = WAS에 도달하지 못함 (필터/인터셉터) 또는 서버 오류
      //     // 일단 모든 에러를 Vue 컴포넌트로 throw (나중에 특정 status만 예외 처리)
      //     const error = new Error(`HTTP error! status: ${response.status}`)
      //     error.type = 'HTTP_ERROR'
      //     error.status = response.status
      //     throw error
      //   }
      // } catch (responseError) {
      //   // 응답 자체를 읽을 수 없음 = 네트워크 오류
      //   // 일단 모든 에러를 Vue 컴포넌트로 throw (나중에 특정 status만 예외 처리)
      //   const error = new Error('네트워크 연결을 확인해주세요.')
      //   error.type = 'NETWORK_ERROR'
      //   error.status = 0
      //   throw error
      }
    }

    // ============================================
    // 📥 6. 응답 처리 (타입별 자동 처리)
    // ============================================
    
    // No Content (204) 응답
    if (response.status === 204) {
      return null
    }

    // Content-Type 확인
    const contentType = response.headers.get('content-type') || ''
    
    // JSON 응답 처리
    if (contentType.includes('application/json')) {
      return await response.json()
    }
    
    // 비-JSON 응답 처리 (텍스트, HTML 등)
    const text = await response.text()
    return text || null

  } catch (error) {
    // ============================================
    // 🚨 7. 에러 로깅 및 재throw
    // ============================================
    // console.error('API 요청 에러:', {
    //   url: fullUrl,
    //   method: options.method || 'GET',
    //   error: error.message,
    //   timestamp: new Date().toISOString()
    // })
    throw error
  }
}

// ============================================
// 🛠️ 8. HTTP 메서드별 헬퍼 함수들
// ============================================

/**
 * API 메서드 객체
 * - GET: 데이터 조회
 * - POST: 데이터 생성/전송
 * - PUT: 데이터 수정
 * - DELETE: 데이터 삭제
 */
export const api = {
  /**
   * GET 요청 - 데이터 조회
   * @param {string} url - 요청 URL
   * @param {Object} options - 추가 옵션
   * @returns {Promise<any>} - 응답 데이터
   */
  get: (url, options = {}) => 
    apiRequest(url, { ...options, method: 'GET' }),

  /**
   * POST 요청 - 데이터 생성/전송
   * @param {string} url - 요청 URL
   * @param {Object} data - 전송할 데이터
   * @param {Object} options - 추가 옵션
   * @returns {Promise<any>} - 응답 데이터
   */
  post: (url, data, options = {}) =>
    apiRequest(url, { 
      ...options, 
      method: 'POST', 
      body: JSON.stringify(data) 
    }),

  /**
   * PUT 요청 - 데이터 수정
   * @param {string} url - 요청 URL
   * @param {Object} data - 수정할 데이터
   * @param {Object} options - 추가 옵션
   * @returns {Promise<any>} - 응답 데이터
   */
  put: (url, data, options = {}) =>
    apiRequest(url, { 
      ...options, 
      method: 'PUT', 
      body: JSON.stringify(data) 
    }),

  /**
   * DELETE 요청 - 데이터 삭제
   * @param {string} url - 요청 URL
   * @param {Object} options - 추가 옵션
   * @returns {Promise<any>} - 응답 데이터
   */
  delete: (url, options = {}) =>
    apiRequest(url, { ...options, method: 'DELETE' })
}

// ============================================
// 📤 9. 기본 내보내기
// ============================================
export default api