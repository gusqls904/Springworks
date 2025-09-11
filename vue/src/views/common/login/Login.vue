<template>
  <div class="login-page">
    <div class="login_container">
      <div class="login_left">
        <div class="login_content">
          <h2 class="login_title eng">Welcome to Project.</h2>
          <p class="login_subtitle">
            멀티 서비스 웹 플랫폼<br>
            온라인쇼핑몰 / 강의시스템 개발 프로젝트!
          </p>

          <div class="login_social">
            <div class="login_kakao">
              <a class="login_btn kakao mov03" @click="goToMallLogin">
                온라인 쇼핑몰
              </a>
            </div>

            <a class="login_btn mov03" @click="goToLearningLogin">
              강의 시스템
            </a>
          </div>
        </div>
      </div>

      <div class="login_right">
        <div class="login_vase">
          <img src="/img/login_03.jpg">
        </div>
      </div>

    </div>
  </div>
</template>

<script>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import '/src/views/common/login/Login.css'

export default {
  name: 'Login',
  setup() {
    const router = useRouter()
    const returnUrl = ref('')
    const displayNomember = ref(false)

    const goToMallLogin = () => {
      // 쇼핑몰 로그인 이동
      router.push('/mall-login')
    }

    const goToLearningLogin = () => {
      // 강의 로그인 이동
      router.push('/learning-login')
    }

    return {
      returnUrl,
      displayNomember,
      goToMallLogin,
      goToLearningLogin
    }
  }
}
</script>
