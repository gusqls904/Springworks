// 공통 fetch 래퍼
async function apiRequest(url, options = {}) {
  const headers = {
    'Content-Type': 'application/json',
    ...options.headers
  }

  // 토큰
  const token = localStorage.getItem('accessToken')
  if (token) headers.Authorization = `Bearer ${token}`

  // URL 구성 (DEV 프록시 ↔ PROD 절대경로)
  const isDev = import.meta.env.DEV
  const fullUrl = url.startsWith('http')
    ? url
    : (isDev ? `/api${url}` : `${window.location.origin}/api${url}`)

  try {
    const response = await fetch(fullUrl, { ...options, headers })

    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`)
    }

    // No Content
    if (response.status === 204) return null

    // JSON 응답만 파싱
    const ct = response.headers.get('content-type') || ''
    if (!ct.includes('application/json')) {
      // 비-JSON 응답: 텍스트로 반환
      const text = await response.text()
      return text || null
    }

    return await response.json()
  } catch (error) {
    console.error('API 요청 에러:', error)
    throw error
  }
}

// 기존 시그니처 유지
export function apiCall(url, data = {}, method = 'POST') {
  const m = method.toUpperCase()
  return apiRequest(url, {
    method: m,
    body: m !== 'GET' ? JSON.stringify(data) : undefined
  })
}

// 메서드별 헬퍼
export const api = {
  get: (url, options = {}) => apiRequest(url, { ...options, method: 'GET' }),
  post: (url, data, options = {}) =>
    apiRequest(url, { ...options, method: 'POST', body: JSON.stringify(data) }),
  put: (url, data, options = {}) =>
    apiRequest(url, { ...options, method: 'PUT', body: JSON.stringify(data) }),
  delete: (url, options = {}) =>
    apiRequest(url, { ...options, method: 'DELETE' })
}

export default api
