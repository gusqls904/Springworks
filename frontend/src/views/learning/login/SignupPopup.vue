<template>
  <div>
    <BasePopup
      :visible="visible"
      @update:visible="$emit('update:visible', $event)"
      @close="handleClose"
      title="회원가입"
      size="large"
      custom-class="signup-popup"
      :close-on-overlay="false"
    >
      <div class="signup-form">
        <div class="form-row">
          <div class="form-group">
            <label for="signup-userId">사용자 ID <span class="required">*</span></label>
            <div class="input-with-button">
              <input
                type="text"
                id="signup-userId"
                v-model="signupForm.userId"
                placeholder="아이디를 입력해주세요"
                required
              >
              <button 
                type="button" 
                class="btn-check-duplicate" 
                @click="checkUserIdDuplicate"
                :disabled="!signupForm.userId.trim() || isChecking"
              >
                {{ isChecking ? '확인중...' : '중복확인' }}
              </button>
            </div>
          </div>
          
          <div class="form-group">
            <label for="signup-userName">사용자명 <span class="required">*</span></label>
            <input
              type="text"
              id="signup-userName"
              v-model="signupForm.userName"
              placeholder="사용자명을 입력해주세요"
              required
            >
          </div>
        </div>
        
        <div class="form-row">
          <div class="form-group">
            <label for="signup-password">비밀번호 <span class="required">*</span></label>
            <input
              type="password"
              id="signup-password"
              v-model="signupForm.password"
              placeholder="비밀번호를 입력해주세요"
              required
            >
          </div>
          
          <div class="form-group">
            <label for="signup-password-confirm">비밀번호 확인 <span class="required">*</span></label>
            <input
              type="password"
              id="signup-password-confirm"
              v-model="signupForm.passwordConfirm"
              placeholder="비밀번호를 다시 입력해주세요"
              required
            >
          </div>
        </div>
        
        <div class="form-row">
          <div class="form-group">
            <label for="signup-role">사용자 역할 <span class="required">*</span></label>
            <select
              id="signup-role"
              v-model="signupForm.userRole"
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
            <label for="signup-email">이메일</label>
            <input
              type="email"
              id="signup-email"
              v-model="signupForm.email"
              placeholder="이메일을 입력해주세요 (선택사항)"
            >
          </div>
        </div>
      </div>
      
      <template #footer>
        <button type="button" class="btn btn-secondary" @click="handleClose">
          취소
        </button>
        <button type="button" class="btn btn-primary" @click="handleSignup" :disabled="!isFormValid || isLoading">
          {{ isLoading ? '처리중...' : '회원가입' }}
        </button>
      </template>
    </BasePopup>
    
    <AlertPopup
      :visible="alertVisible"
      @update:visible="alertVisible = $event"
      :title="alertTitle"
      :message="alertMessage"
      :type="alertType"
    />
  </div>
</template>

<script>
import { ref, computed, watch, onMounted } from 'vue'
import BasePopup from '../../../components/BasePopup.vue'
import AlertPopup from '../../../components/AlertPopup.vue'
import { apiCall } from '/src/util/api.js'

export default {
  name: 'SignupPopup',
  components: {
    BasePopup,
    AlertPopup
  },
  props: {
    visible: {
      type: Boolean,
      default: false
    }
  },
  emits: ['update:visible', 'close', 'signup'],
  setup(props, { emit }) {
    const signupForm = ref({
      userId: '',
      userName: '',
      password: '',
      passwordConfirm: '',
      email: '',
      userRole: ''
    })
    
    const isLoading = ref(false)
    const isLoadingRoles = ref(false)
    const roleList = ref([])
    
    const isFormValid = computed(() => {
      return signupForm.value.userId &&
             signupForm.value.userName &&
             signupForm.value.password &&
             signupForm.value.passwordConfirm &&
             signupForm.value.userRole &&
             signupForm.value.password === signupForm.value.passwordConfirm
    })
    

    /**
     * 팝업이 열릴 때
     */
    watch(() => props.visible, (newVal) => {
      if (newVal) {
        loadRoleList()
      } else {
        resetForm()
      }
    })
    
    const resetForm = () => {
      signupForm.value = {
        userId: '',
        userName: '',
        password: '',
        passwordConfirm: '',
        email: '',
        userRole: ''
      }
      isLoading.value = false
    }
    
    /**
     * 역할 목록 로드
     */ 
    const loadRoleList = async () => {
      try {
        isLoadingRoles.value = true
        const response = await apiCall('/user/getRoleList', {}, 'POST')
        
        // API 응답에 따라 역할 목록 설정
        if (response && Array.isArray(response)) {
          roleList.value = response.map(role => ({
            value: role.roleId,
            label: role.roleName
          }))
        } else if (response && response.roleList) {
          roleList.value = response.roleList.map(role => ({
            value: role.roleId,
            label: role.roleName
          }))
        } else {
          
        }
      } catch (error) {
        console.error('역할 목록 로드 실패:', error)

      } finally {
        isLoadingRoles.value = false
      }
    }
    
    // Alert 상태
    const alertVisible = ref(false)
    const alertTitle = ref('알림')
    const alertMessage = ref('')
    const alertType = ref('info')
    
    const isChecking = ref(false)
    const userIdChecked = ref(false)
    
    // Alert 표시 함수
    const showAlert = (message, type = 'info', title = '알림') => {
      alertMessage.value = message
      alertType.value = type
      alertTitle.value = title
      alertVisible.value = true
    }
    
    const handleSignup = async () => {
      if (!isFormValid.value) {
        showAlert('모든 필수 필드를 올바르게 입력해주세요.', 'warning')
        return
      }
      
      if (signupForm.value.password !== signupForm.value.passwordConfirm) {
        showAlert('비밀번호가 일치하지 않습니다.', 'warning')
        return
      }
      
      try {
        isLoading.value = true
        
        // DTO에 맞는 데이터 구조로 변환
        const signupData = {
          userId: signupForm.value.userId,
          userName: signupForm.value.userName,
          password: signupForm.value.password,
          email: signupForm.value.email || null,
          userRole: signupForm.value.userRole,
          isActive: '1'
        }
        
        const response = await apiCall('/user/signup', signupData, 'POST')
        
        // 성공 이벤트 발생
        emit('signup', response)
        
        // 성공 메시지 표시
        showAlert('회원가입이 완료되었습니다.', 'success')
        
        // 팝업 닫기
        handleClose()
        
      } catch (error) {
        // 에러 메시지 처리
        let errorMessage = '회원가입 중 오류가 발생했습니다.'
        
        if (error.result && error.result.message) {
          errorMessage = error.result.message
        } else if (error.message) {
          errorMessage = error.message
        }
        
        showAlert(errorMessage, 'error')
        
      } finally {
        isLoading.value = false
      }
    }
    
    const handleClose = () => {
      emit('update:visible', false)
      emit('close')
    }
    
    /**
     * 사용자 ID 중복확인
     */ 
    const checkUserIdDuplicate = async () => {
      const userId = signupForm.value.userId.trim()
      if (!userId) {
        showAlert('아이디를 입력해주세요.', 'warning')
        return
      }
      // 간단한 형식 검증 (영문/숫자/밑줄, 4~20자)
      if (!/^[a-zA-Z0-9_]{4,20}$/.test(userId)) {
        showAlert('아이디는 영문/숫자/밑줄로 4~20자여야 합니다.', 'warning')
        return
      }

      try {
        isChecking.value = true
        userIdChecked.value = false

        // API 호출 (응답 구조에 맞게 duplicate/available 둘 다 처리)
        const res = await apiCall('/user/checkUserId', { userId }, 'POST')
        const duplicate = res?.duplicate ?? (res?.available === false)
        if (duplicate) {
          showAlert('이미 사용 중인 아이디입니다.', 'warning')
          userIdChecked.value = false
        } else {
          showAlert('사용 가능한 아이디입니다.', 'success')
          userIdChecked.value = true
        }
      } catch (e) {
        showAlert('중복확인 중 오류가 발생했습니다.', 'error')
      } finally {
        isChecking.value = false
      }
    }
    
    return {
      signupForm,
      isLoading,
      isLoadingRoles,
      roleList,
      isFormValid,
      handleSignup,
      handleClose,
      alertVisible,
      alertTitle,
      alertMessage,
      alertType,
      showAlert,
      isChecking,
      userIdChecked,
      checkUserIdDuplicate
    }
    
  }
}
</script>

<style scoped>
.signup-form {
  max-width: 100%;
}

.form-row {
  display: flex;
  gap: 20px;
  margin-bottom: 20px;
}

.form-row .form-group {
  flex: 1;
  margin-bottom: 0;
}

.form-group {
  margin-bottom: 20px;
}

.form-group label {
  display: block;
  margin-bottom: 8px;
  font-size: 14px;
  font-weight: 500;
  color: #374151;
}

.required {
  color: #ef4444;
  font-weight: bold;
}

.form-group input,
.form-group select {
  width: 100%;
  padding: 12px 16px;
  border: 1px solid #d1d5db;
  border-radius: 8px;
  font-size: 14px;
  transition: border-color 0.2s ease;
}

.form-group input:focus,
.form-group select:focus {
  outline: none;
  border-color: #3b82f6;
  box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.1);
}

.form-group select {
  cursor: pointer;
  background-color: white;
}

.btn {
  padding: 12px 24px;
  border-radius: 8px;
  font-size: 14px;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.2s ease;
  border: none;
}

.btn-secondary {
  background: #f3f4f6;
  color: #374151;
  margin-right: 12px;
}

.btn-secondary:hover {
  background: #e5e7eb;
}

.btn-primary {
  background: #3b82f6;
  color: white;
}

.btn-primary:hover:not(:disabled) {
  background: #2563eb;
}

.btn-primary:disabled {
  background: #9ca3af;
  cursor: not-allowed;
}

.input-with-button {
  display: flex;
  align-items: center;
  gap: 10px;
}

.input-with-button input {
  flex-grow: 1;
}

.btn-check-duplicate {
  padding: 12px 16px;
  background-color: #3b82f6;
  color: white;
  border: none;
  border-radius: 8px;
  font-size: 14px;
  font-weight: 500;
  cursor: pointer;
  transition: background-color 0.2s ease;
  white-space: nowrap;
}

.btn-check-duplicate:hover:not(:disabled) {
  background-color: #2563eb;
}

.btn-check-duplicate:disabled {
  background-color: #9ca3af;
  cursor: not-allowed;
  opacity: 0.6;
}

/* 반응형 */
@media (max-width: 768px) {
  .form-row {
    flex-direction: column;
    gap: 0;
  }
  
  .form-row .form-group {
    margin-bottom: 20px;
  }
  
  .form-group input,
  .form-group select {
    padding: 10px 14px;
    font-size: 16px; /* iOS 줌 방지 */
  }
  
  .btn {
    padding: 10px 20px;
    font-size: 16px;
  }
}
</style>