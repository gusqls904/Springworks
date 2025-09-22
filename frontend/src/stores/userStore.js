import { defineStore } from 'pinia'
import { ref, computed } from 'vue'

export const useUserStore = defineStore('user', () => {
  // 상태
  const userId = ref('')
  const userName = ref('')
  const roleId = ref('')
  const roleName = ref('')
  const isLoggedIn = ref(false)

  // 계산된 속성
  const userInfo = computed(() => ({
    userId: userId.value,
    userName: userName.value,
    roleId: roleId.value,
    roleName: roleName.value
  }))

  const isAdmin = computed(() => roleId.value === 'ADMIN' || roleName.value === '관리자')
  const isInstructor = computed(() => roleId.value === 'INSTRUCTOR' || roleName.value === '강사')
  const isStudent = computed(() => roleId.value === 'STUDENT' || roleName.value === '학생')

  // 액션
  const setUser = (userData) => {
    userId.value = userData.userId || ''
    userName.value = userData.userName || ''
    roleId.value = userData.roleId || ''
    roleName.value = userData.roleName || ''
    isLoggedIn.value = true

    // localStorage에도 저장 (새로고침 시 복원용)
    localStorage.setItem('userId', userId.value)
    localStorage.setItem('userName', userName.value)
    localStorage.setItem('roleId', roleId.value)
    localStorage.setItem('roleName', roleName.value)
    localStorage.setItem('isLoggedIn', 'true')
  }

  const clearUser = () => {
    userId.value = ''
    userName.value = ''
    roleId.value = ''
    roleName.value = ''
    isLoggedIn.value = false

    // localStorage에서도 제거
    localStorage.removeItem('userId')
    localStorage.removeItem('userName')
    localStorage.removeItem('roleId')
    localStorage.removeItem('roleName')
    localStorage.removeItem('isLoggedIn')
  }

  const loadUserFromStorage = () => {
    const storedUserId = localStorage.getItem('userId')
    const storedUserName = localStorage.getItem('userName')
    const storedRoleId = localStorage.getItem('roleId')
    const storedRoleName = localStorage.getItem('roleName')
    const storedIsLoggedIn = localStorage.getItem('isLoggedIn')

    if (storedIsLoggedIn === 'true' && storedUserId) {
      userId.value = storedUserId
      userName.value = storedUserName || ''
      roleId.value = storedRoleId || ''
      roleName.value = storedRoleName || ''
      isLoggedIn.value = true
    }
  }

  const updateUserRole = (newRoleId, newRoleName) => {
    roleId.value = newRoleId
    roleName.value = newRoleName
    localStorage.setItem('roleId', newRoleId)
    localStorage.setItem('roleName', newRoleName)
  }

  return {
    // 상태
    userId,
    userName,
    roleId,
    roleName,
    isLoggedIn,
    // 계산된 속성
    userInfo,
    isAdmin,
    isInstructor,
    isStudent,
    // 액션
    setUser,
    clearUser,
    loadUserFromStorage,
    updateUserRole
  }
})
