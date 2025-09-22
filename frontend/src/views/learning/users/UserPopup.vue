<template>
  <div>
    <BasePopup
      :visible="visible"
      @update:visible="$emit('update:visible', $event)"
      @close="handleClose"
      :title="isEditMode ? '사용자 수정' : '사용자 등록'"
      size="large"
      custom-class="user-popup"
      :close-on-overlay="false"
    >
      <div class="user-form">
        <div class="form-row">
          <div class="form-group">
            <label for="user-userId" class="form-label">사용자 ID <span class="required">*</span></label>
            <div class="input-with-button">
              <input
                type="text"
                id="user-userId"
                v-model="userForm.userId"
                placeholder="아이디를 입력해주세요"
                class="form-input"
                required
                :disabled="isEditMode"
              >
              <button 
                v-if="!isEditMode"
                type="button" 
                class="btn btn-primary" 
                @click="checkUserIdDuplicate"
                :disabled="!userForm.userId.trim() || isChecking"
              >
                {{ isChecking ? '확인중...' : '중복확인' }}
              </button>
            </div>
          </div>
          
          <div class="form-group">
            <label for="user-userName" class="form-label">사용자명 <span class="required">*</span></label>
            <input
              type="text"
              id="user-userName"
              v-model="userForm.userName"
              placeholder="사용자명을 입력해주세요"
              class="form-input"
              required
            >
          </div>
        </div>
        
        <div class="form-row">
          <div class="form-group">
            <label for="user-password" class="form-label">비밀번호 <span class="required" v-if="!isEditMode">*</span></label>
            <input
              type="password"
              id="user-password"
              v-model="userForm.password"
              :placeholder="isEditMode ? '비밀번호를 변경하려면 입력하세요' : '비밀번호를 입력해주세요'"
              class="form-input"
              :required="!isEditMode"
            >
          </div>
          
          <div class="form-group">
            <label for="user-password-confirm" class="form-label">비밀번호 확인 <span class="required" v-if="!isEditMode">*</span></label>
            <input
              type="password"
              id="user-password-confirm"
              v-model="userForm.passwordConfirm"
              :placeholder="isEditMode ? '비밀번호를 변경하려면 다시 입력하세요' : '비밀번호를 다시 입력해주세요'"
              class="form-input"
              :required="!isEditMode"
            >
          </div>
        </div>
        
        <div class="form-row">
          <div class="form-group">
            <label for="user-role" class="form-label">사용자 역할 <span class="required">*</span></label>
            <select
              id="user-role"
              v-model="userForm.roleId"
              class="form-input"
              required
              :disabled="isLoadingRoles"
            >
              <option value="">{{ isLoadingRoles ? '로딩중...' : '역할을 선택해주세요' }}</option>
              <option 
                v-for="role in roleList" 
                :key="role.value" 
                :value="role.value"
              >
                {{ role.label }}
              </option>
            </select>
          </div>
          
          <div class="form-group">
            <label for="user-email" class="form-label">이메일</label>
            <input
              type="email"
              id="user-email"
              v-model="userForm.email"
              placeholder="이메일을 입력해주세요 (선택사항)"
              class="form-input"
            >
          </div>
        </div>

        <div class="form-row" v-if="isEditMode">
          <div class="form-group">
            <label for="user-status" class="form-label">상태 <span class="required">*</span></label>
            <select
              id="user-status"
              v-model="userForm.isActive"
              class="form-input"
              required
            >
              <option value="활성">활성</option>
              <option value="비활성">비활성</option>
            </select>
          </div>
          <div class="form-group">
            <!-- 빈 공간으로 기존 인풋과 동일한 크기 유지 -->
          </div>
        </div>
      </div>
      
      <template #footer>
        <button type="button" class="btn btn-secondary" @click="handleClose">
          취소
        </button>
        <button type="button" class="btn btn-primary" @click="handleSubmit" :disabled="!isFormValid || isLoading">
          {{ isLoading ? '처리중...' : (isEditMode ? '수정' : '등록') }}
        </button>
      </template>
    </BasePopup>
    
  </div>
</template>

<script>
import { ref, computed, watch } from 'vue'
import BasePopup from '../../../components/BasePopup.vue'
import { apiCall } from '/src/util/api.js'
import { updateUserMockData, createUserMockData, roleListMockData } from '../../mock/userMockData.js'
import { callApiOrMock } from '/src/util/mockConfig.js'
import Swal from 'sweetalert2'
import '../common.css'

export default {
  name: 'UserPopup',
  components: {
    BasePopup
  },
  props: {
    visible: {
      type: Boolean,
      default: false
    },
    editUser: {
      type: Object,
      default: null
    }
  },
  emits: ['update:visible', 'close', 'user-saved', 'refresh-list'],
  setup(props, { emit }) {
    
    const isLoading = ref(false)
    const isLoadingRoles = ref(false)
    const roleList = ref([])
    const isChecking = ref(false)
    const userIdChecked = ref(false)

    // 편집 모드 확인
    const isEditMode = computed(() => !!props.editUser)

    const userForm = ref({
      userId: '',
      userName: '',
      password: '',
      passwordConfirm: '',
      email: '',
      roleId: '',
      roleName: '',
      isActive: '활성'
    })

    const isFormValid = computed(() => {
      const baseValid = userForm.value.userId &&
                       userForm.value.userName &&
                       userForm.value.roleId
      
      if (isEditMode.value) {
        // 수정 모드: 비밀번호는 선택사항
        return baseValid
      } else {
        // 등록 모드: 비밀번호 필수 + 중복확인 완료
        return baseValid &&
               userForm.value.password &&
               userForm.value.passwordConfirm &&
               userForm.value.password === userForm.value.passwordConfirm &&
               userIdChecked.value
      }
    })
    
    const resetForm = () => {
      userForm.value = {
        userId: '',
        userName: '',
        password: '',
        passwordConfirm: '',
        email: '',
        roleId: '',
        roleName: '',
        isActive: '활성'
      }
      userIdChecked.value = false
      isLoading.value = false
    }

    const loadUserData = () => {
      if (props.editUser) {
        userForm.value = {
          userId: props.editUser.userId || '',
          userName: props.editUser.userName || '',
          password: '',
          passwordConfirm: '',
          email: props.editUser.email || '',
          roleId: props.editUser.roleId || '',
          roleName: props.editUser.roleName || '',
          isActive: props.editUser.isActive || '활성'
        }
        userIdChecked.value = true // 수정 모드에서는 중복확인 완료로 간주
      }
    }
    
    /**
     * 팝업이 열릴 때
     */
    watch(() => props.visible, (newVal) => {
      if (newVal) {
        getRoleList()
        if (isEditMode.value) {
          loadUserData()
        } else {
          resetForm()
        }
      }
    })

    /**
     * 편집 사용자 데이터가 변경될 때
     */
    watch(() => props.editUser, () => {
      if (props.visible && isEditMode.value) {
        loadUserData()
      }
    })

    /**
     * 역할 선택 시 roleId와 roleName 동기화
     */
    watch(() => userForm.value.roleId, (newRoleId) => {
      if (newRoleId) {
        const selectedRole = roleList.value.find(role => role.value === newRoleId)
        if (selectedRole) {
          userForm.value.roleName = selectedRole.label
        }
      }
    })
    
    /**
     * 역할 목록 로드
     */
    const getRoleList = async () => {
      try {
        isLoadingRoles.value = true
        
        const res = await callApiOrMock(
          // 실제 API 호출
          () => apiCall('/user/getRoleList', {}, 'POST'),
          // 목업 데이터 호출
          () => Promise.resolve(roleListMockData)
        )

        roleList.value = res?.body.map(role => ({
          value: role.roleId,
          label: role.roleName
        })) || []
      } catch (error) {
        showAlert('오류가 발생했습니다.', 'error')
      } finally {
        isLoadingRoles.value = false
      }
    }
    
    /**
     * 사용자 ID 중복확인
     */
    const checkUserIdDuplicate = async () => {
      const userId = userForm.value.userId.trim()
      if (!userId) {
        showAlert('아이디를 입력해주세요.', 'warning')
        return
      }
      
      if (!/^[a-zA-Z0-9_]{4,20}$/.test(userId)) {
        showAlert('아이디는 영문/숫자/밑줄로 4~20자여야 합니다.', 'warning')
        return
      }

      try {
        isChecking.value = true
        userIdChecked.value = false

        const res = await apiCall('/user/checkUserId', { userId }, 'POST')
        
        const duplicate = res?.body.duplicate
        if (duplicate) {
          showAlert('이미 사용 중인 아이디입니다.', 'warning')
          userIdChecked.value = false
        } else {
          showAlert('사용 가능한 아이디입니다.', 'success')
          userIdChecked.value = true
        }
      } catch (e) {
        showAlert('오류가 발생했습니다.', 'error')
      } finally {
        isChecking.value = false
      }
    }
    
    /**
     * 사용자 등록/수정 처리
     */
    const handleSubmit = async () => {
      if (!isFormValid.value) {
        showAlert('모든 필수 필드를 올바르게 입력해주세요.', 'warning')
        return
      }
      
      if (userForm.value.password && userForm.value.password !== userForm.value.passwordConfirm) {
        showAlert('비밀번호가 일치하지 않습니다.', 'warning')
        return
      }
      
      try {
        isLoading.value = true
        
        const userData = {
          userId: userForm.value.userId,
          userName: userForm.value.userName,
          email: userForm.value.email || null,
          roleId: userForm.value.roleId,
          roleName: userForm.value.roleName, // 표시용
          isActive: userForm.value.isActive === '활성' ? 1 : 0  // DB 저장 형태로 변환
        }
        
        // 수정 모드인 경우 userSeq 추가
        if (isEditMode.value) {
          userData.userSeq = props.editUser.userSeq
        }
        
        // 비밀번호가 입력된 경우에만 포함
        if (userForm.value.password) {
          userData.password = userForm.value.password
        }
        
        // API 호출 또는 목업 데이터 사용
        const response = await callApiOrMock(
          // 실제 API 호출
          async () => {
            const endpoint = isEditMode.value ? '/user/updateUser' : '/user/createUser'
            return await apiCall(endpoint, userData, 'POST')
          },
          // 목업 데이터 호출
          async () => {
            if (isEditMode.value) {
              return updateUserMockData(userData)
            } else {
              return createUserMockData(userData)
            }
          }
        )
        
        emit('user-saved', response)
        showAlert(isEditMode.value ? '사용자가 수정되었습니다.' : '사용자가 등록되었습니다.', 'success')
        
        // 목록 재조회를 위한 이벤트 발생
        emit('refresh-list')
        handleClose()
        
      } catch (error) {
        showAlert('오류가 발생했습니다.', 'error')
      } finally {
        isLoading.value = false
      }
    }
    
    /**
     * 팝업 닫기
     */
    const handleClose = () => {
      emit('update:visible', false)
      emit('close')
    }
    
    /**
     * Alert 표시 함수 (SweetAlert2)
     */
    const showAlert = async (message, type = 'info', title = '알림') => {
      await Swal.fire({
        title: title,
        text: message,
        icon: type,
        confirmButtonText: '확인',
        allowOutsideClick: false,
        allowEscapeKey: true
      })
    }
    
    return {
      userForm,
      isLoading,
      isLoadingRoles,
      roleList,
      isFormValid,
      handleSubmit,
      handleClose,
      showAlert,
      isChecking,
      userIdChecked,
      checkUserIdDuplicate,
      isEditMode
    }
  }
}
</script>

<style scoped>
/* ============================================
   USER POPUP - SPECIFIC STYLES
   ============================================ */
.user-form {
  max-width: 100%;
}

.input-with-button {
  display: flex;
  gap: 8px;
  align-items: flex-end;
}

.input-with-button .form-input {
  flex: 1;
}

.input-with-button .btn {
  white-space: nowrap;
  min-width: 80px;
}
</style>
