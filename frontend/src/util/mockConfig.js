/**
 * 목업 데이터 설정 관리
 * true/false 설정으로 목업 데이터 사용 여부를 제어
 */

// 목업 모드 설정 (기본값: true)
let useMockData = false

/**
 * 목업 데이터 사용 여부 설정
 * @param {boolean} enabled - 목업 데이터 사용 여부
 */
export const setMockMode = (enabled) => {
  useMockData = enabled
  localStorage.setItem('useMockData', enabled.toString())
  console.log(`🔧 목업 모드: ${enabled ? '활성화' : '비활성화'}`)
}

/**
 * 목업 데이터 사용 여부 확인
 * @returns {boolean} 목업 데이터 사용 여부
 */
export const isMockMode = () => {
  // localStorage에서 설정 확인 (없으면 기본값 true)
  const saved = localStorage.getItem('useMockData')
  if (saved !== null) {
    useMockData = saved === 'true'
  }
  return useMockData
}

/**
 * 목업 모드 토글
 */
export const toggleMockMode = () => {
  const newMode = !isMockMode()
  setMockMode(newMode)
  console.log(`🔧 목업 모드: ${newMode ? '활성화' : '비활성화'}`)
  return newMode
}

/**
 * API 호출 시 목업 데이터 사용 여부에 따라 적절한 함수 실행
 * @param {Function} apiFunction - 실제 API 호출 함수
 * @param {Function} mockFunction - 목업 데이터 호출 함수
 * @returns {Promise} API 또는 목업 데이터 결과
 */
export const callApiOrMock = async (apiFunction, mockFunction) => {
  if (isMockMode()) {
    console.log('🔧 목업 데이터 사용')
    return await mockFunction()
  } else {
    console.log('🌐 실제 API 호출')
    return await apiFunction()
  }
}

// 초기화 시 localStorage에서 설정 로드
const savedSetting = localStorage.getItem('useMockData')
if (savedSetting !== null) {
  useMockData = savedSetting === 'true'
}

// 개발자 도구용 전역 함수
window.toggleMockMode = () => {
  const newMode = !isMockMode()
  setMockMode(newMode)
  console.log(`목업 모드가 ${newMode ? '활성화' : '비활성화'}되었습니다.`)
  console.log('페이지를 새로고침하여 변경사항을 적용하세요.')
}

window.getMockStatus = () => {
  return {
    useMockData: isMockMode(),
    localStorage: localStorage.getItem('useMockData')
  }
}

console.log('🚀 목업 설정 시스템 초기화됨')
console.log('📊 현재 상태:', window.getMockStatus())
console.log('💡 사용법: toggleMockMode() - 목업 모드 전환')
