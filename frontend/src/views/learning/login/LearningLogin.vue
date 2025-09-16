<template>
  <div class="learning-login-page elegant">
    <div class="learning-login_container">
        <img src="/img/login_06.jpg">
      <div class="learning-login_left">
        <div class="learning-login_content">
          <h2 class="learning-login_title eng">강의 시스템 로그인</h2>
          <p class="learning-login_subtitle">
            아이디와 비밀번호로 로그인하거나<br>
            회원가입을 진행해주세요.
          </p>

          <form class="learning-login_form" @submit.prevent="handleLogin">
            <div class="learning-form_group">
              <label for="id">아이디</label>
              <input 
                type="id" 
                id="id" 
                v-model="loginForm.id"
                placeholder="아이디을 입력해주세요"
                required
              >
            </div>
            
            <div class="learning-form_group">
              <label for="password">비밀번호</label>
              <input 
                type="password" 
                id="password" 
                v-model="loginForm.password" 
                placeholder="비밀번호를 입력해주세요"
                required
              >
            </div>

            <div class="learning-form_options">
              <label class="learning-checkbox_wrapper">
                <input type="checkbox" v-model="rememberMe">
                <span class="learning-checkmark"></span>
                로그인 상태 유지
              </label>
              <div class="learning-form_links">
                <a class="learning-forgot_password" @click="goBack">비밀번호 찾기</a>
                <a class="learning-signup_link_inline" @click="openSignupPopup">회원가입</a>
              </div>
            </div>

            <button type="submit" class="learning-login_btn learning-primary mov03">
              로그인
            </button>
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
import './LearningLogin.css'
import SignupPopup from '/src/views/learning/login/SignupPopup.vue'

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
      console.log('이메일 로그인:', loginForm.value)
      // 로그인 성공 후 게시판으로 이동
      router.push('/learning/board')  // '/board' → '/learning/board'
    }

    const openSignupPopup = () => {
      showSignupPopup.value = true
    }

    const closeSignupPopup = () => {
      showSignupPopup.value = false
    }

    const handleSignup = (signupData) => {
      console.log('회원가입 데이터:', signupData)
      // 회원가입 API 호출 로직
      // alert('회원가입이 완료되었습니다.')
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
