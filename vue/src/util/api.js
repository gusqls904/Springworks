// fetch 기반 API 호출 함수
async function apiRequest(url, options = {}) {
  const headers = {
    'Content-Type': 'application/json',
    ...options.headers
  }
  
  // 토큰 추가
  const token = localStorage.getItem('accessToken')
  if (token) {
    headers.Authorization = `Bearer ${token}`
  }
  
  // 개발 환경에서는 프록시 사용, 프로덕션에서는 절대 URL 사용
  const isDev = import.meta.env.DEV
  const fullUrl = url.startsWith('http') 
    ? url 
    : isDev 
      ? `/api${url}`  // 개발: 프록시 사용
      : `${window.location.origin}/api${url}`  // 프로덕션: 절대 URL 사용
  
  try {
    const response = await fetch(fullUrl, {
      ...options,
      headers
    })
    
    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`)
    }
    
    // 응답이 비어있는지 확인
    const text = await response.text()
    if (!text) {
      return null // 빈 응답인 경우 null 반환
    }
    
    // JSON 파싱 시도
    try {
      return JSON.parse(text)
    } catch (parseError) {
      console.error('JSON 파싱 에러:', parseError)
      console.error('응답 내용:', text)
      throw new Error('서버에서 잘못된 응답을 반환했습니다.')
    }
  } catch (error) {
    console.error('API 요청 에러:', error)
    throw error
  }
}

// 기존 apiCall 함수 호환성 유지
export function apiCall(url, data = {}, method = 'POST') {
  return apiRequest(url, {
    method: method.toUpperCase(),
    body: method.toUpperCase() !== 'GET' ? JSON.stringify(data) : undefined
  })
}

// HTTP 메서드별 함수들
export const api = {
  get: (url, options = {}) => apiRequest(url, { ...options, method: 'GET' }),
  post: (url, data, options = {}) => apiRequest(url, { 
    ...options, 
    method: 'POST', 
    body: JSON.stringify(data) 
  }),
  put: (url, data, options = {}) => apiRequest(url, { 
    ...options, 
    method: 'PUT', 
    body: JSON.stringify(data) 
  }),
  delete: (url, options = {}) => apiRequest(url, { ...options, method: 'DELETE' })
}

export default api
