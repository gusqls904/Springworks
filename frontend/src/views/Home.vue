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

export default {
  name: 'Login',
  setup() {
    const router = useRouter()
    const returnUrl = ref('')
    const displayNomember = ref(false)

    const goToMallLogin = () => {
      // 쇼핑몰 로그인 이동
      router.push('/mall/login')
    }

    const goToLearningLogin = () => {
      // 강의 로그인 이동
      router.push('/admin/login')
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

<style scoped>
.login {
  position: absolute;
  top: 30px;
  left: 30px;
  z-index: 10;
}

.login_container {
  background: white;
  border-radius: 0;
  box-shadow: 0 0 0;
  overflow: hidden;
  display: flex;
  width: 100vw;
  height: 100vh;
}

.login_left {
  flex: 0.35;
  padding: 100px 80px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  background: white;
  position: relative;
}

.login_right {
  flex: 0.65;
  background: linear-gradient(135deg, #f4e4c1 0%, #e8d5b7 50%, #d4c4a8 100%);
  position: relative;
  display: flex;
  align-items: center;
  justify-content: center;
}

.login_right::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: url('data:image/svg+xml,<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 100 100"><defs><pattern id="texture" width="20" height="20" patternUnits="userSpaceOnUse"><circle cx="10" cy="10" r="0.5" fill="rgba(255,255,255,0.1)"/></pattern></defs><rect width="100" height="100" fill="url(%23texture)"/></svg>');
  opacity: 0.3;
}

.login_vase {
  position: relative;
  z-index: 2;
  display: flex;
  align-items: center;
  justify-content: space-around;
  width: 100%;
  height: 100%;
}

.login_vase img {
  width: 100%;
  height: 100%;
  object-position: center;
  position: relative;
  z-index: 3;
}

.login_content {
  width: 100%;
  max-width: 450px;
  margin: 0 auto;
}

.login_title {
  font-size: 34px;
  font-weight: 300;
  letter-spacing: -0.06em;
  margin-bottom: 30px;
  line-height: 1.2;
  color: #333;
  text-align: center;
}

.login_subtitle {
  font-size: 17px;
  font-weight: 300;
  line-height: 1.7;
  color: #888;
  margin-bottom: 60px;
  text-align: center;
}

.login_social {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.login_btn {
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 18px;
  border: 2px solid #e1e5e9;
  border-radius: 10px;
  text-decoration: none;
  color: #333;
  font-weight: 400;
  transition: all 0.3s ease;
  background: white;
  font-size: 15px;
  letter-spacing: -0.5px;
  cursor: pointer;
}

.login_btn:hover {
  border-color: #333;
  transform: translateY(-2px);
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
  text-decoration: none;
}

.login_btn img {
  width: 24px;
  height: 24px;
  margin-right: 12px;
}

.login_btn.kakao {
  background: #FEE500;
  border-color: #FEE500;
  color: #3c1e1e;
}

.login_btn.kakao:hover {
  background: #FDD835;
  border-color: #FDD835;
}

.login_btn.naver {
  background: #26c826;
  border-color: #26c826;
  color: #fff;
}

.login_btn.naver:hover {
  background: #22b022;
  border-color: #22b022;
}

.login_btn.facebook {
  background: #4267b2;
  border-color: #4267b2;
  color: #fff;
}

.login_btn.facebook:hover {
  background: #3b5a9a;
  border-color: #3b5a9a;
}

.login_btn.google {
  background: #f4511e;
  border-color: #f4511e;
  color: #fff;
}

.login_btn.google:hover {
  background: #e04a1b;
  border-color: #e04a1b;
}

.login_btn.line {
  background: #26c826;
  border-color: #26c826;
  color: #fff;
}

.login_btn.line:hover {
  background: #22b022;
  border-color: #22b022;
}

.login_btn.apple {
  background: #222;
  border-color: #222;
  color: #fff;
}

.login_btn.apple:hover {
  background: #000;
  border-color: #000;
}

.login_btn.naver span {
  background-image: url('/_idio/img/sns_login_01.png');
}

.login_btn.facebook span {
  background-image: url('/_idio/img/sns_login_02.png');
}

.login_btn.google span {
  background-image: url('/_idio/img/sns_login_03.png');
}

.login_btn.kakao span {
  background-image: url('/_idio/img/sns_login_04.png');
}

.login_btn.line span {
  background-image: url('/_idio/img/sns_login_05.png');
}

.login_btn.apple span {
  background-image: url('/_idio/img/sns_login_06.png');
}

.login_btn span {
  position: absolute;
  top: 0;
  left: 0;
  display: block;
  width: 100%;
  height: 100%;
  background-size: 50%;
  background-position: center center;
  background-repeat: no-repeat;
}

.login_kakao {
  position: relative;
}


@media (max-width: 768px) {
  .login_container {
    flex-direction: column;
    height: 100vh;
  }

  .login_left,
  .login_right {
    flex: 1;
    padding: 40px 30px;
  }

  .login_title {
    font-size: 30px;
  }

  .login {
    position: relative;
    top: auto;
    left: auto;
    margin-bottom: 20px;
    text-align: center;
  }
}

/* 로그인 페이지 전체 스타일 */
.login-page {
  background: #f8f9fa;
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
}
</style>
