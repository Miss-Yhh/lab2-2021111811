<template>
  <div class="student-main">
    <aside class="sidebar">
      <ul>
        <li @click="showSection('availability')">查询教师空闲时间</li>
        <li @click="showSection('appointmentList')">查询预约记录</li>
        <li @click="logout">退出登录</li>
      </ul>
    </aside>
    <main class="main-content">
      <div v-if="currentSection === 'availability'">
        <h1>查找教师空闲时间</h1>
        <form @submit.prevent="fetchAvailability">
          <div class="form-group">
            <label for="teacherId">教师ID</label>
            <input type="text" id="teacherId" v-model="teacherId" required>
          </div>
          <button type="submit" class="btn">查找</button>
        </form>

        <div v-if="availabilities.length > 0">
          <h2>空闲时间列表</h2>
          <table>
            <thead>
            <tr>
              <th>序号</th>
              <th>开始时间</th>
              <th>结束时间</th>
              <th>状态</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="availability in availabilities" :key="availability.slotId">
              <td>{{ availability.slotId }}</td>
              <td>{{ formatDate(availability.startTime) }}</td>
              <td>{{ formatDate(availability.endTime) }}</td>
              <td>{{ availability.status }}</td>
              <td>
                <button @click="requestAppointment(availability.slotId)">预约</button>
              </td>
            </tr>
            </tbody>
          </table>
        </div>
      </div>

      <div v-if="currentSection === 'appointmentList'">
        <h1>预约记录列表</h1>
        <div v-if="appointments.length > 0">
          <table>
            <thead>
            <tr>
              <th>预约ID</th>
              <th>时间段ID</th>
              <th>预约状态</th>
              <th>创建时间</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="appointment in appointments" :key="appointment.appointmentId">
              <td>{{ appointment.appointmentId }}</td>
              <td>{{ appointment.slotId }}</td>
              <td>{{ appointment.status }}</td>
              <td>{{ formatDate(appointment.createdAt) }}</td>
            </tr>
            </tbody>
          </table>
        </div>
      </div>
    </main>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'StudentMain',
  data() {
    return {
      teacherId: '',
      studentId: '',
      availabilities: [],
      appointments: [],
      currentSection: 'availability'
    };
  },
  created() {
    this.studentId = localStorage.getItem('studentId');
  },
  methods: {
    showSection(section) {
      this.currentSection = section;
      if (section === 'appointmentList') {
        this.fetchAppointmentList();
      }
    },
    fetchAvailability() {
      axios.get(`http://localhost:8088/availabilitySlots/available/${this.teacherId}`)
          .then(response => {
            this.availabilities = response.data;
          })
          .catch(error => {
            console.error('Failed to fetch availabilities:', error);
            alert('获取空闲时间失败，请重试。');
          });
    },
    requestAppointment(slotId) {
      const appointmentData = {
        teacherId: this.teacherId,
        studentId: this.studentId,
        slotId: slotId
      };

      axios.post('http://localhost:8088/appointments/request', JSON.stringify(appointmentData), {
        headers: {
          'Content-Type': 'application/json'
        }
      })
          .then(response => {
            console.log('Appointment response:', response.data);
            alert('预约成功！');
          })
          .catch(error => {
            console.error('Failed to request appointment:', error);
            alert('预约失败，请重试。');
          });
    },
    fetchAppointmentList() {
      axios.get(`http://localhost:8088/appointments/requestlist/${this.studentId}`)
          .then(response => {
            this.appointments = response.data;
          })
          .catch(error => {
            console.error('Failed to fetch appointments:', error);
            alert('获取预约记录失败，请重试。');
          });
    },
    logout() {
      this.$router.push({ name: 'LoginMain' });
    },
    formatDate(date) {
      if (!date) return '';
      const d = new Date(date);
      return d.toLocaleString();
    }
  }
};
</script>

<style scoped>
.student-main {
  display: flex;
  height: 100vh;
}

.sidebar {
  width: 200px;
  background-color: #f8f9fa;
  padding: 20px;
  box-shadow: 2px 0 5px rgba(0, 0, 0, 0.1);
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

table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 20px;
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
</style>
