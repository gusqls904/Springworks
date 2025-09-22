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
            <label for="signup-userId" class="form-label">사용자 ID <span class="required">*</span></label>
            <div class="input-with-button">
              <input
                type="text"
                id="signup-userId"
                v-model="signupForm.userId"
                placeholder="아이디를 입력해주세요"
                class="form-input"
                required
              >
              <button 
                type="button" 
                class="btn btn-primary" 
                @click="checkUserIdDuplicate"
                :disabled="!signupForm.userId.trim() || isChecking"
              >
                {{ isChecking ? '확인중...' : '중복확인' }}
              </button>
            </div>
          </div>
          
          <div class="form-group">
            <label for="signup-userName" class="form-label">사용자명 <span class="required">*</span></label>
            <input
              type="text"
              id="signup-userName"
              v-model="signupForm.userName"
              placeholder="사용자명을 입력해주세요"
              class="form-input"
              required
            >
          </div>
        </div>
        
        <div class="form-row">
          <div class="form-group">
            <label for="signup-password" class="form-label">비밀번호 <span class="required">*</span></label>
            <input
              type="password"
              id="signup-password"
              v-model="signupForm.password"
              placeholder="비밀번호를 입력해주세요"
              class="form-input"
              required
            >
          </div>
          
          <div class="form-group">
            <label for="signup-password-confirm" class="form-label">비밀번호 확인 <span class="required">*</span></label>
            <input
              type="password"
              id="signup-password-confirm"
              v-model="signupForm.passwordConfirm"
              placeholder="비밀번호를 다시 입력해주세요"
              class="form-input"
              required
            >
          </div>
        </div>
        
        <div class="form-row">
          <div class="form-group">
            <label for="signup-role" class="form-label">사용자 역할 <span class="required">*</span></label>
            <select
              id="signup-role"
              v-model="signupForm.roleId"
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
            <label for="signup-email" class="form-label">이메일</label>
            <input
              type="email"
              id="signup-email"
              v-model="signupForm.email"
              placeholder="이메일을 입력해주세요 (선택사항)"
              class="form-input"
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
    
  </div>
</template>

<script>
import { ref, computed, watch } from 'vue'
import BasePopup from '../../../components/BasePopup.vue'
import { apiCall } from '/src/util/api.js'
import Swal from 'sweetalert2'
import '../common.css'

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

    
    const isLoading = ref(false)
    const isLoadingRoles = ref(false)
    const roleList = ref([])
    const isChecking = ref(false)
    const userIdChecked = ref(false)

    const signupForm = ref({
      userId: '',
      userName: '',
      password: '',
      passwordConfirm: '',
      email: '',
      roleId: ''
    })

    const isFormValid = computed(() => {
      return signupForm.value.userId &&
             signupForm.value.userName &&
             signupForm.value.password &&
             signupForm.value.passwordConfirm &&
             signupForm.value.roleId &&
             signupForm.value.password === signupForm.value.passwordConfirm
    })
    
    const resetForm = () => {
      signupForm.value = {
        userId: '',
        userName: '',
        password: '',
        passwordConfirm: '',
        email: '',
        roleId: ''
      }
      isLoading.value = false
    }
    
    /**
     * 팝업이 열릴 때
     */
    watch(() => props.visible, (newVal) => {
      if (newVal) {
        getRoleList()
      } else {
        resetForm()
      }
    })
    
    /**
     * 역할 목록 로드
     */
    const getRoleList = async () => {
      try {
        isLoadingRoles.value = true
        const res = await apiCall('/user/getRoleList', {}, 'POST')

          roleList.value = res?.body.map(role => ({
            value: role.roleId,
            label: role.roleName
          }))
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
      const userId = signupForm.value.userId.trim()
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
     * 회원가입 처리
     */
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
        
        const signupData = {
          userId: signupForm.value.userId,
          userName: signupForm.value.userName,
          password: signupForm.value.password,
          email: signupForm.value.email || null,
          roleId: signupForm.value.roleId,
          isActive: '1'
        }
        
        const response = await apiCall('/user/signup', signupData, 'POST')
        
        emit('signup', response)
        showAlert('회원가입이 완료되었습니다.', 'success')
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
      signupForm,
      isLoading,
      isLoadingRoles,
      roleList,
      isFormValid,
      handleSignup,
      handleClose,
      showAlert,
      isChecking,
      userIdChecked,
      checkUserIdDuplicate
    }
  }
}
</script>

<style scoped>
/* ============================================
   SIGNUP POPUP - SPECIFIC STYLES
   ============================================ */
.signup-form {
  max-width: 100%;
}
</style>