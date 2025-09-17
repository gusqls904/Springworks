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
              <label for="id" class="form-label">아이디</label>
              <input 
                type="text" 
                id="id" 
                v-model="loginForm.id"
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

            <button type="submit" class="btn btn-primary btn-lg mov03" style="width: 100%; margin-bottom: 15px; color: white;">
              로그인
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
import SignupPopup from '/src/views/learning/login/SignupPopup.vue'
import '../common.css'

export default {
  name: 'LearningLogin',
  components: {
    SignupPopup
  },
  setup() {
    const router = useRouter()
    
    const loginForm = ref({
      id: '',
      password: ''
    })
    const rememberMe = ref(false)
    const showSignupPopup = ref(false)

    const goBack = () => {
      router.push('/')
    }

    const handleLogin = () => {
      router.push('/learning/main')
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
