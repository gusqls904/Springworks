<template>
  <div class="mall-login-page elegant">
    <div class="mall-login_container">
        <img src="/img/login_05.jpg">
      <div class="mall-login_left">
        <div class="mall-login_content">
          <h2 class="mall-login_title eng">쇼핑몰 로그인</h2>
          <p class="mall-login_subtitle">
            아이디와 비밀번호로 로그인하거나<br>
            회원가입을 진행해주세요.
          </p>

          <form class="mall-login_form" @submit.prevent="handleLogin">
            <div class="mall-form_group">
              <label for="id">아이디</label>
              <input 
                type="id" 
                id="id" 
                v-model="loginForm.id"
                placeholder="아이디을 입력해주세요"
                required
              >
            </div>
            
            <div class="mall-form_group">
              <label for="password">비밀번호</label>
              <input 
                type="password" 
                id="password" 
                v-model="loginForm.password" 
                placeholder="비밀번호를 입력해주세요"
                required
              >
            </div>

            <div class="mall-form_options">
              <label class="mall-checkbox_wrapper">
                <input type="checkbox" v-model="rememberMe">
                <span class="mall-checkmark"></span>
                로그인 상태 유지
              </label>
              <a class="mall-forgot_password" @click="goBack">비밀번호를 잊으셨나요?</a>
            </div>

            <button type="submit" class="mall-login_btn mall-primary mov03">
              로그인
            </button>
          </form>

          <div class="mall-divider">
            <span>또는</span>
          </div>

          <div class="mall-login_social">
            <div class="mall-login_kakao">
              <a class="mall-login_btn mall-kakao mov03" @click="handleKakaoLogin">
                <img src="/img/sns_login_04.jpg">
                카카오로 로그인
              </a>
            </div>
            <a class="mall-login_btn mall-naver mov03" @click="handleNaverLogin">
              <img src="/img/sns_login_01.jpg">
              네이버로 로그인
            </a>
            <a class="mall-login_btn mall-google mov03" @click="handleGoogleLogin">
              <img src="/img/sns_login_03.jpg">
              구글로 로그인
            </a>
          </div>

          <div class="mall-signup_link">
            <p>아직 계정이 없으신가요? <a @click="goToSignup">회원가입</a></p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import './MallLogin.css'

export default {
  name: 'MallLogin',
  setup() {
    const router = useRouter()
    
    const loginForm = ref({
      id: '',
      password: ''
    })
    const rememberMe = ref(false)

    const goBack = () => {
      router.push('/')
    }

    const handleLogin = () => {
      console.log('이메일 로그인:', loginForm.value)
    }

    const handleKakaoLogin = () => {
      console.log('카카오 로그인')
    }

    const handleNaverLogin = () => {
      console.log('네이버 로그인')
    }

    const handleGoogleLogin = () => {
      console.log('구글 로그인')
    }

    const goToSignup = () => {
      console.log('회원가입 페이지로 이동')
    }

    return {
      loginForm,
      rememberMe,
      goBack,
      handleLogin,
      handleKakaoLogin,
      handleNaverLogin,
      handleGoogleLogin,
      goToSignup
    }
  }
}
</script>
