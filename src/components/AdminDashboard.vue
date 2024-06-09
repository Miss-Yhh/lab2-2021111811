<template>
  <div class="admin-dashboard">
    <aside class="sidebar">
      <ul>
        <li @click="showSection('teachers')">教师信息</li>
        <li @click="showSection('addTeacher')">添加教师</li>
        <li @click="showSection('deleteTeacher')">删除教师</li>
        <li @click="logout">退出登录</li>
      </ul>
    </aside>
    <main class="main-content">
      <div v-if="currentSection === 'teachers'" class="table-container">
        <h2>教师信息列表</h2>
        <table>
          <thead>
          <tr>
            <th>教师ID</th>
            <th>姓名</th>
            <th>科目</th>
            <th>邮箱</th>
            <th>电话号码</th>
            <th>地址</th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="teacher in teachers" :key="teacher.teacherId">
            <td>{{ teacher.teacherId }}</td>
            <td>{{ teacher.teacherName }}</td>
            <td>{{ teacher.subject }}</td>
            <td>{{ teacher.email }}</td>
            <td>{{ teacher.phoneNumber }}</td>
            <td>{{ teacher.address }}</td>
          </tr>
          </tbody>
        </table>
      </div>
      <div v-if="currentSection === 'addTeacher'" class="form-container">
        <h2>添加教师信息</h2>
        <form @submit.prevent="addTeacher">
          <div class="form-group">
            <label for="teacherId">教师ID</label>
            <input type="number" v-model="newTeacher.teacherId" required />
          </div>
          <div class="form-group">
            <label for="teacherName">姓名</label>
            <input type="text" v-model="newTeacher.teacherName" required />
          </div>
          <div class="form-group">
            <label for="subject">科目</label>
            <input type="text" v-model="newTeacher.subject" required />
          </div>
          <div class="form-group">
            <label for="email">邮箱</label>
            <input type="email" v-model="newTeacher.email" required />
          </div>
          <div class="form-group">
            <label for="phoneNumber">电话号码</label>
            <input type="text" v-model="newTeacher.phoneNumber" required />
          </div>
          <div class="form-group">
            <label for="address">地址</label>
            <input type="text" v-model="newTeacher.address" required />
          </div>
          <div class="form-group">
            <label for="password">登录密码</label>
            <input type="password" v-model="newTeacher.password" required />
          </div>
          <button type="submit">添加教师</button>
        </form>
      </div>
      <div v-if="currentSection === 'deleteTeacher'" class="delete-container">
        <h2>删除教师信息</h2>
        <form @submit.prevent="deleteTeacher">
          <div class="form-group">
            <label for="deleteTeacherId">教师ID</label>
            <input type="number" v-model="deleteTeacherId" required />
          </div>
          <button type="submit">删除教师</button>
        </form>
      </div>
    </main>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'AdminDashboard',
  props: ['username'],
  data() {
    return {
      currentSection: 'teachers',
      teachers: [],
      newTeacher: {
        teacherId: '',
        teacherName: '',
        subject: '',
        email: '',
        phoneNumber: '',
        address: '',
        password: ''
      },
      deleteTeacherId: ''
    };
  },
  created() {
    this.fetchTeachers();
  },
  methods: {
    async fetchTeachers() {
      try {
        const response = await axios.get('http://localhost:8088/admin/showteachers');
        this.teachers = response.data;
      } catch (error) {
        console.error('Failed to fetch teachers:', error);
      }
    },
    async addTeacher() {
      try {
        const response = await axios.post('http://localhost:8088/admin/teacher/add', this.newTeacher);
        if (response.status === 200) {
          alert('教师添加成功！');
          this.fetchTeachers(); // 更新教师列表
          this.resetForm();
        } else {
          alert('教师添加失败！');
        }
      } catch (error) {
        console.error('Failed to add teacher:', error);
        alert('教师添加失败！');
      }
    },
    async deleteTeacher() {
      try {
        const response = await axios.get(`http://localhost:8088/admin/teacher/delete/${this.deleteTeacherId}`);
        if (response.status === 200) {
          alert('教师删除成功！');
          this.fetchTeachers(); // 更新教师列表
          this.deleteTeacherId = '';
        } else {
          alert('教师删除失败！');
        }
      } catch (error) {
        console.error('Failed to delete teacher:', error);
        alert('教师删除失败！');
      }
    },
    resetForm() {
      this.newTeacher = {
        teacherId: '',
        teacherName: '',
        subject: '',
        email: '',
        phoneNumber: '',
        address: '',
        password: ''
      };
    },
    showSection(section) {
      this.currentSection = section;
    },
    logout() {
      this.$router.push({ name: 'LoginMain' });
    }
  }
};
</script>

<style scoped>
.admin-dashboard {
  display: flex;
  height: 100vh;
}

.sidebar {
  width: 200px;
  background-color: #f8f9fa;
  padding: 20px;
  box-shadow: 2px 0 5px rgba(0, 0, 0, 0.1);
}

.sidebar h2 {
  font-size: 20px;
  margin-bottom: 20px;
  text-align: center;
}

.sidebar ul {
  list-style: none;
  padding: 0;
}

.sidebar li {
  padding: 10px 20px;
  cursor: pointer;
  border-radius: 4px;
  transition: background-color 0.3s;
}

.sidebar li:hover {
  background-color: #007bff;
  color: white;
}

.main-content {
  flex-grow: 1;
  padding: 20px;
}

.table-container, .form-container, .delete-container {
  max-width: 800px;
  margin: 0 auto;
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  padding: 20px;
}

h2 {
  text-align: center;
  margin-bottom: 20px;
}

table {
  width: 100%;
  border-collapse: collapse;
}

th, td {
  border: 1px solid #ccc;
  padding: 8px;
  text-align: left;
}

th {
  background-color: #f4f4f4;
}

tbody tr:nth-child(odd) {
  background-color: #f9f9f9;
}

.form-group {
  margin-bottom: 15px;
}

label {
  display: block;
  margin-bottom: 5px;
  font-weight: bold;
}

input {
  width: calc(100% - 20px);
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 4px;
  box-sizing: border-box;
}

button {
  padding: 10px 20px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

button:hover {
  background-color: #0056b3;
}
</style>
