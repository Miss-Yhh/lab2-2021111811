<template>
  <div class="container">
    <div class="login-wrapper">
      <h2>教师管理系统</h2>
      <form @submit.prevent="login">
        <div class="input-group">
          <i class="el-icon-user"></i>
          <input type="text" class="input-item" v-model="username" placeholder="请输入用户名" required>
        </div>
        <div class="input-group">
          <i class="el-icon-lock"></i>
          <input type="password" class="input-item" v-model="password" placeholder="请输入密码" required>
        </div>
        <div v-if="role === 'admin'" class="input-group">
          <i class="el-icon-s-custom"></i>
          <input type="text" class="input-item" v-model="adminId" placeholder="请输入管理员号" required>
        </div>
        <div v-if="role === 'student'" class="input-group">
          <i class="el-icon-s-custom"></i>
          <input type="text" class="input-item" v-model="studentId" placeholder="请输入学生号" required>
        </div>
        <div v-if="role === 'teacher'" class="input-group">
          <i class="el-icon-s-custom"></i>
          <input type="text" class="input-item" v-model="teacherId" placeholder="请输入教师号" required>
        </div>
        <div class="role-group">
          <div class="roles">
            <input type="radio" id="admin" value="admin" v-model="role" required>
            <label for="admin">管理员</label>
            <input type="radio" id="student" value="student" v-model="role" required>
            <label for="student">学生</label>
            <input type="radio" id="teacher" value="teacher" v-model="role" required>
            <label for="teacher">教师</label>
          </div>
        </div>
        <button type="submit" class="btn">登录</button>
      </form>
    </div>
  </div>
</template>

<script>
import qs from 'qs';

export default {
  name: 'LoginMain',
  data() {
    return {
      username: '',
      password: '',
      role: '',
      adminId: '',
      studentId: '',
      teacherId: ''
    };
  },
  methods: {
    login() {
      const payload = {
        username: this.username,
        password: this.password,
        role: this.role
      };

      this.$axios.post('http://localhost:8088/login', qs.stringify(payload))
          .then(response => {
            if (response.data === 'OK') {
              if (this.role === 'admin') {
                this.$router.push({ name: 'AdminDashboard', params: { adminId: this.adminId } });
              } else if (this.role === 'teacher') {
                this.$router.push({ name: 'TeacherMain', params: { teacherId: this.teacherId } });
              } else if (this.role === 'student') {
                localStorage.setItem('studentId', this.studentId); // 保存学生号
                this.$router.push({ name: 'StudentMain', params: { studentId: this.studentId } });
              } else {
                alert('角色未定义！');
              }
            } else {
              alert('密码错误！');
            }
          })
          .catch(error => {
            console.error('Failed to login:', error);
            alert('登录失败，请检查您的输入。');
          });
    }
  }
};
</script>

<style scoped>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

html, body {
  height: 100%;
}

body {
  background-image: url('../assets/background.png');
  background-size: cover;
  background-position: center;
  display: flex;
  justify-content: center;
  align-items: center;
}

.container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  width: 100%;
}

.login-wrapper {
  background-color: rgba(255, 255, 255, 0.8);
  width: 400px;
  padding: 30px;
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  text-align: center;
}

h2 {
  font-size: 24px;
  color: #333;
  margin-bottom: 20px;
  font-family: 'KaiTi', serif;
  letter-spacing: 5px;
}

.input-group {
  display: flex;
  align-items: center;
  margin-bottom: 15px;
  border: 1px solid #ddd;
  border-radius: 5px;
  padding: 5px 10px;
}

.input-group i {
  font-size: 18px;
  color: #888;
  margin-right: 10px;
}

.input-item {
  width: 100%;
  border: none;
  outline: none;
  padding: 10px;
  font-size: 14px;
}

.role-group {
  text-align: left;
  margin-top: 20px;
  margin-bottom: 20px;
}

.roles {
  display: flex;
  justify-content: space-around;
  gap: 15px;
}

.role-group input[type="radio"] {
  margin-right: 5px;
}

.btn {
  width: 100%;
  padding: 10px;
  background-color: #00aaff;
  color: #fff;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-size: 16px;
}

.btn:hover {
  background-color: #0088cc;
}
</style>
