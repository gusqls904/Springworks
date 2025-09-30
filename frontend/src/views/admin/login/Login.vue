<template>
  <div class="login-page">
    <div class="login-container">
      <div class="login-bg">
        <img src="/img/login_06.jpg" alt="Background" class="bg-image">
      </div>
      <div class="login-card">
        <div class="login-content">
          <h2 class="title eng text-center">강의 시스템 로그인</h2>
          <p class="subtitle text-center">
            아이디와 비밀번호로 로그인하거나<br>
            회원가입을 진행해주세요.
          </p>

          <form class="login-form" @submit.prevent="handleLogin">
            <div class="form-group">
              <label for="userId" class="form-label">아이디</label>
              <input 
                type="text" 
                id="userId" 
                v-model="loginForm.userId"
                placeholder="아이디을 입력해주세요"
                class="form-input"
                required
              >
            </div>
            
            <div class="form-group">
              <label for="password" class="form-label">비밀번호</label>
              <input 
                type="password" 
                id="password" 
                v-model="loginForm.password" 
                placeholder="비밀번호를 입력해주세요"
                class="form-input"
                required
              >
            </div>

            <div class="form-options">
              <label class="checkbox-wrapper">
                <input type="checkbox" v-model="rememberMe">
                <span class="checkmark"></span>
                로그인 상태 유지
              </label>
            </div>

            <button type="submit" class="btn btn-primary btn-lg mov03" style="width: 100%; margin-bottom: 15px; color: white;" :disabled="isLoading">
              <i v-if="isLoading" class="fas fa-spinner fa-spin" style="margin-right: 8px;"></i>
              {{ isLoading ? '로그인 중...' : '로그인' }}
            </button>
            
            <div class="form-links-bottom">
              <div class="form-links-left">
                <a class="action-link" @click="openSignupPopup">회원가입</a>
              </div>
              <div class="form-links-right">
                <a class="action-link" @click="goBack">아이디 찾기</a>
                <a class="action-link" @click="goBack">비밀번호 찾기</a>
              </div>
            </div>
          </form>
        </div>
      </div>
    </div>
    
    <!-- 회원가입 팝업 -->
    <SignupPopup
      :visible="showSignupPopup"
      @update:visible="showSignupPopup = $event"
      @close="closeSignupPopup"
      @signup="handleSignup"
    />
  </div>
</template>

<script>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '/src/util/userStore.js'
import SignupPopup from '/src/views/admin/login/SignupPopup.vue'
import Swal from 'sweetalert2'
import api from '/src/util/api.js'
import '/src/styles/admin/main.css'

export default {
  name: 'LearningLogin',
  components: {
    SignupPopup
  },
  setup() {
    const router = useRouter()
    const userStore = useUserStore()
    
    const loginForm = ref({
      userId: '',
      password: ''
    })
    const rememberMe = ref(false)
    const showSignupPopup = ref(false)
    const isLoading = ref(false)

    const goBack = () => {
      router.push('/')
    }

    const handleLogin = async () => {
      if (!loginForm.value.userId.trim() || !loginForm.value.password.trim()) {
        Swal.fire({
          title: '입력 오류',
          text: '아이디와 비밀번호를 입력해주세요.',
          icon: 'warning',
          confirmButtonText: '확인'
        })
        return
      }

      // 로그인 방식 선택 컨펌 팝업
      const result = await Swal.fire({
        title: '로그인 방식 선택',
        text: '어떤 방식으로 로그인하시겠습니까?',
        icon: 'question',
        showCancelButton: true,
        confirmButtonText: '실제 API 통신',
        cancelButtonText: '목업 데이터 사용',
        reverseButtons: true,
        allowOutsideClick: false,
        allowEscapeKey: true
      })

      if (result.dismiss === Swal.DismissReason.cancel) {
        // 목업 데이터 사용
        await loginWithMockData()
      } else if (result.isConfirmed) {
        // 실제 API 통신
        await loginWithApi()
      }
    }

    // 목업 데이터로 로그인
    const loginWithMockData = async () => {
      try {
        isLoading.value = true
        
        // 목업 로그인 데이터
        const mockUserData = {
          userId: loginForm.value.userId,
          userName: '홍길동',
          roleId: 'ADMIN',
          roleName: '관리자'
        }
        
        // Pinia store에 사용자 정보 저장
        userStore.setUser(mockUserData)
        
        // 성공 알림
        Swal.fire({
          title: '로그인 성공 (목업)',
          text: '목업 데이터로 로그인되었습니다!',
          icon: 'success',
          timer: 1500,
          showConfirmButton: false
        })
        
        // 메인 페이지로 이동
        setTimeout(() => {
          router.push('/admin/main')
        }, 1500)
        
      } catch (error) {
        console.error('목업 로그인 오류:', error)
        Swal.fire({
          title: '로그인 실패',
          text: '목업 로그인 중 오류가 발생했습니다.',
          icon: 'error',
          confirmButtonText: '확인'
        })
      } finally {
        isLoading.value = false
      }
    }

    // 실제 API로 로그인
    const loginWithApi = async () => {
      try {
        isLoading.value = true
        
        // 실제 로그인 API 호출
        const response = await api.post('/auth/login', loginForm.value)
        
        if (response?.body) {
          // API 응답에서 사용자 정보 추출
          const userData = {
            userId: response.body.userId || loginForm.value.userId,
            userName: response.body.userName,
            roleId: response.body.roleId,
            roleName: response.body.roleName
          }
          
          // Pinia store에 사용자 정보 저장
          userStore.setUser(userData)
          
          // 성공 알림
          Swal.fire({
            title: '로그인 성공 (API)',
            text: 'API 통신으로 로그인되었습니다!',
            icon: 'success',
            timer: 1500,
            showConfirmButton: false
          })
          
          // 메인 페이지로 이동
          setTimeout(() => {
            router.push('/admin/main')
          }, 1500)
        } else {
          throw new Error('API 응답이 올바르지 않습니다.')
        }
        
      } catch (error) {
        console.error('API 로그인 오류:', error)
        Swal.fire({
          title: '로그인 실패',
          text: error.message,
          icon: 'error',
          confirmButtonText: '확인'
        })
      } finally {
        isLoading.value = false
      }
    }

    const openSignupPopup = () => {
      showSignupPopup.value = true
    }

    const closeSignupPopup = () => {
      showSignupPopup.value = false
    }

    const handleSignup = (signupData) => {
      console.log('회원가입 데이터:', signupData)
    }

    return {
      loginForm,
      rememberMe,
      showSignupPopup,
      isLoading,
      goBack,
      handleLogin,
      openSignupPopup,
      closeSignupPopup,
      handleSignup
    }
  }
}
</script>

<style scoped>
/* ============================================
   LEARNING LOGIN PAGE - ESSENTIAL STYLES ONLY
   ============================================ */

/* CSS Variables */
:root {
  --login-overlay: rgba(0, 0, 0, 0.3);
}

/* Page Layout */
.login-page {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
}

.login-container {
  position: relative;
  overflow: hidden;
  display: flex;
  width: 100vw;
  height: 100vh;
  justify-content: center;
  align-items: center;
  background-color: #e8f4fd;
}

.login-bg {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: 0;
}

.bg-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
  object-position: center;
}

.login-container::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: var(--login-overlay);
  z-index: 0;
}

.login-card {
  position: relative;
  z-index: 1;
  width: 100%;
  max-width: 500px;
  padding: 60px 50px;
  background: rgba(255, 255, 255, 0.95);
  backdrop-filter: blur(10px);
  border: 1px solid rgba(74, 144, 164, 0.2);
  box-shadow: 0 15px 35px rgba(74, 144, 164, 0.1);
  border-radius: 20px;
  margin: 0 20px;
}

.login-content {
  width: 100%;
  max-width: 450px;
  margin: 0 auto;
}

/* Form Customization */
.login-form .form-input {
  padding: 16px 20px;
  border: 1px solid rgba(74, 144, 164, 0.3);
  border-radius: 10px;
  background: rgba(255, 255, 255, 0.9);
}

.login-form .form-input:focus {
  box-shadow: 0 0 0 3px rgba(74, 144, 164, 0.1);
}

.login-form .form-input::placeholder {
  color: #7a9ca8;
}

.login-form .form-label {
  color: var(--primary-color);
}

/* Responsive */
@media (max-width: 768px) {
  .login-card {
    padding: 40px 30px;
    margin: 0 10px;
    border-radius: 15px;
  }
}

@media (max-width: 480px) {
  .login-card {
    padding: 30px 20px;
    margin: 0 5px;
  }
}
</style>
