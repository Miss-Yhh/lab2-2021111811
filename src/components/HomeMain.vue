<template>
  <div class="home-container">
    <h1>教 师 信 息 管 理 系 统</h1>
    <button class="login-button" @click="goToLogin">登录</button>
  </div>
</template>

<script>
export default {
  name: 'HomeMain',
  methods: {
    goToLogin() {
      this.$router.push({name: 'LoginMain'});
    }
  }
};
</script>

<style scoped>
.home-container {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-size: cover;
  background-position: center;
  text-align: center;
}

h1 {
  font-size: 60px; /* 增大字体 */
  color: black; /* 设置字体颜色为黑色 */
  margin-bottom: 50px;
  font-family: 'KaiTi', serif; /* 楷体 */
  letter-spacing: 15px; /* 增加字间距 */
}

.login-button {
  padding: 15px 60px; /* 增大按钮 */
  background-color: #00aaff;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-size: 25px; /* 增大按钮文字 */
}

.login-button:hover {
  background-color: #0088cc;
}
</style>
