<template>
  <BasePopup
    :visible="visible"
    @update:visible="$emit('update:visible', $event)"
    @close="handleClose"
    title="회원가입"
    size="medium"
    custom-class="signup-popup"
    :close-on-overlay="false"
  >
    <form @submit.prevent="handleSignup" class="signup-form">
      <div class="form-group">
        <label for="signup-userId">사용자 ID <span class="required">*</span></label>
        <input
          type="text"
          id="signup-userId"
          v-model="signupForm.userId"
          placeholder="아이디를 입력해주세요"
          required
        >
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
      
      <div class="form-group">
        <label for="signup-email">이메일</label>
        <input
          type="email"
          id="signup-email"
          v-model="signupForm.email"
          placeholder="이메일을 입력해주세요 (선택사항)"
        >
      </div>
      
      <div class="form-group">
        <label for="signup-role">사용자 역할 <span class="required">*</span></label>
        <select
          id="signup-role"
          v-model="signupForm.userRole"
          required
        >
          <option value="">역할을 선택해주세요</option>
          <option value="USER">일반 사용자</option>
          <option value="ADMIN">관리자</option>
          <option value="TEACHER">강사</option>
          <option value="STUDENT">학생</option>
        </select>
      </div>
    </form>
    
    <template #footer>
      <button type="button" class="btn btn-secondary" @click="handleClose">
        취소
      </button>
      <button type="button" class="btn btn-primary" @click="handleSignup" :disabled="!isFormValid || isLoading">
        {{ isLoading ? '처리중...' : '회원가입' }}
      </button>
    </template>
  </BasePopup>
</template>

<script>
import { ref, computed, watch } from 'vue'
import BasePopup from '../../../components/BasePopup.vue'
import { apiCall } from '/src/util/api.js'

export default {
  name: 'SignupPopup',
  components: {
    BasePopup
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
    
    const isFormValid = computed(() => {
      return signupForm.value.userId &&
             signupForm.value.userName &&
             signupForm.value.password &&
             signupForm.value.passwordConfirm &&
             signupForm.value.userRole &&
             signupForm.value.password === signupForm.value.passwordConfirm
    })
    
    // 팝업이 닫힐 때마다 폼 초기화
    watch(() => props.visible, (newVal) => {
      if (!newVal) {
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
    
    const handleSignup = async () => {
      if (!isFormValid.value) {
        alert('모든 필수 필드를 올바르게 입력해주세요.')
        return
      }
      
      if (signupForm.value.password !== signupForm.value.passwordConfirm) {
        alert('비밀번호가 일치하지 않습니다.')
        return
      }
      
      try {
        isLoading.value = true
        
        // DTO에 맞는 데이터 구조로 변환
        const signupData = {
          userId: signupForm.value.userId,
          userName: signupForm.value.userName,
          password: signupForm.value.password,
          email: signupForm.value.email || null, // 빈 문자열이면 null로 전송
          userRole: signupForm.value.userRole,
          isActive: '1'
        }
        
        console.log('회원가입 요청 데이터:', signupData)
        
        // API 직접 호출
        const response = await apiCall('/user/signup', signupData, 'POST')
        
        console.log('회원가입 성공:', response)
        
        // 성공 이벤트 발생
        emit('signup', response)
        
        // 성공 메시지 표시
        alert('회원가입이 완료되었습니다.')
        
        // 팝업 닫기
        handleClose()
        
      } catch (error) {
        console.error('회원가입 실패:', error)
        
        // 에러 메시지 처리
        let errorMessage = '회원가입 중 오류가 발생했습니다.'
        
        if (error.result && error.result.message) {
          errorMessage = error.result.message
        } else if (error.message) {
          errorMessage = error.message
        }
        
        alert(errorMessage)
        
      } finally {
        isLoading.value = false
      }
    }
    
    const handleClose = () => {
      emit('update:visible', false)
      emit('close')
    }
    
    return {
      signupForm,
      isLoading,
      isFormValid,
      handleSignup,
      handleClose
    }
  }
}
</script>

<style scoped>
.signup-form {
  max-width: 100%;
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

.checkbox-group {
  margin-bottom: 16px;
}

.checkbox-wrapper {
  display: flex;
  align-items: flex-start;
  cursor: pointer;
  font-size: 14px;
  line-height: 1.5;
}

.checkbox-wrapper input[type="checkbox"] {
  display: none;
}

.checkmark {
  width: 18px;
  height: 18px;
  border: 2px solid #d1d5db;
  border-radius: 4px;
  margin-right: 12px;
  margin-top: 2px;
  position: relative;
  transition: all 0.2s ease;
  flex-shrink: 0;
}

.checkbox-wrapper input[type="checkbox"]:checked + .checkmark {
  background-color: #3b82f6;
  border-color: #3b82f6;
}

.checkbox-wrapper input[type="checkbox"]:checked + .checkmark::after {
  content: '✓';
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  color: white;
  font-size: 12px;
  font-weight: bold;
}

.checkbox-text {
  color: #6b7280;
}

.terms-link {
  color: #3b82f6;
  text-decoration: none;
}

.terms-link:hover {
  text-decoration: underline;
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

/* 반응형 */
@media (max-width: 768px) {
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
