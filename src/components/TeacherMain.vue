<template>
  <div class="teacher-main">
    <aside class="sidebar">
      <ul>
        <li @click="showSection('appointments')">预约申请列表</li>
        <li @click="showSection('addAvailability')">增加空闲时间</li>
        <li @click="showSection('updateStatus')">状态修改</li>
        <li @click="showSection('viewAvailability')">查看空闲时间</li>
        <li @click="logout">退出登录</li>
      </ul>
    </aside>
    <main class="main-content">
      <div v-if="currentSection === 'appointments'" class="table-container">
        <h2>预约申请列表</h2>
        <table>
          <thead>
          <tr>
            <th>预约ID</th>
            <th>学生ID</th>
            <th>时间段ID</th>
            <th>预约状态</th>
            <th>创建时间</th>
            <th>操作</th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="appointment in appointments" :key="appointment.appointmentId">
            <td>{{ appointment.appointmentId }}</td>
            <td>{{ appointment.studentId }}</td>
            <td>{{ appointment.slotId }}</td>
            <td>{{ appointment.status }}</td>
            <td>{{ formatDate(appointment.createdAt) }}</td>
            <td>
              <div class="action-buttons">
                <button @click="acceptAppointment(appointment.appointmentId)">接受预约</button>
                <button @click="refuseAppointment(appointment.appointmentId)">拒绝预约</button>
              </div>
            </td>
          </tr>
          </tbody>
        </table>
      </div>
      <div v-if="currentSection === 'addAvailability'" class="form-container">
        <h2>增加空闲时间</h2>
        <form @submit.prevent="addAvailabilitySlot">
          <div class="form-group">
            <label for="teacherId">教师号</label>
            <input type="text" id="teacherId" v-model="newSlot.teacherId" required />
          </div>
          <div class="form-group">
            <label for="startTime">开始时间</label>
            <input type="datetime-local" id="startTime" v-model="newSlot.startTime" required />
          </div>
          <div class="form-group">
            <label for="endTime">结束时间</label>
            <input type="datetime-local" id="endTime" v-model="newSlot.endTime" required />
          </div>
          <div class="form-group">
            <label for="status">状态</label>
            <input type="text" id="status" v-model="newSlot.status" value="AVAILABLE" readonly />
          </div>
          <button type="submit" class="btn">增加</button>
        </form>
      </div>
      <div v-if="currentSection === 'updateStatus'" class="form-container">
        <h2>状态修改</h2>
        <form @submit.prevent="updateStatus">
          <div class="form-group">
            <label for="slotId">空闲时间序号</label>
            <input type="number" id="slotId" v-model="statusUpdate.slotId" required />
          </div>
          <div class="form-group">
            <label for="newStatus">状态</label>
            <select id="newStatus" v-model="statusUpdate.newStatus" required>
              <option value="RESERVED">RESERVED</option>
              <option value="DENIED">DENIED</option>
              <option value="AVAILABLE">AVAILABLE</option>
            </select>
          </div>
          <button type="submit" class="btn">更新状态</button>
        </form>
      </div>
      <div v-if="currentSection === 'viewAvailability'" class="table-container">
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
          <tr v-for="slot in availabilities" :key="slot.slotId">
            <td>{{ slot.slotId }}</td>
            <td>{{ formatDate(slot.startTime) }}</td>
            <td>{{ formatDate(slot.endTime) }}</td>
            <td>{{ slot.status }}</td>
          </tr>
          </tbody>
        </table>
      </div>
    </main>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'TeacherMain',
  data() {
    return {
      currentSection: 'appointments',
      appointments: [],
      availabilities: [],
      newSlot: {
        teacherId: '',
        startTime: '',
        endTime: '',
        status: 'AVAILABLE',
        createdAt: '2023-03-15T09:00:00',
        updatedAt: '2023-03-15T09:00:00'
      },
      statusUpdate: {
        slotId: '',
        newStatus: 'AVAILABLE'
      },
      response: null
    };
  },
  created() {
    this.fetchAppointments();
    this.fetchAvailabilities();
  },
  methods: {
    fetchAppointments() {
      const teacherId = this.$route.params.teacherId;
      axios
          .get(`http://localhost:8088/appointments/teacher/${teacherId}`)
          .then(response => {
            this.appointments = response.data;
          })
          .catch(error => {
            console.error('Failed to fetch appointments:', error);
            alert('获取预约信息失败，请重试。');
          });
    },
    fetchAvailabilities() {
      const teacherId = this.$route.params.teacherId;
      axios
          .get(`http://localhost:8088/availabilitySlots/available/${teacherId}`)
          .then(response => {
            this.availabilities = response.data;
          })
          .catch(error => {
            console.error('Failed to fetch availabilities:', error);
            alert('获取空闲时间失败，请重试。');
          });
    },
    addAvailabilitySlot() {
      const payload = {
        teacherId: this.newSlot.teacherId,
        startTime: this.newSlot.startTime,
        endTime: this.newSlot.endTime,
        status: this.newSlot.status,
        createdAt: this.newSlot.createdAt,
        updatedAt: this.newSlot.updatedAt
      };

      axios
          .post('http://localhost:8088/availabilitySlots/create', payload, {
            headers: { 'Content-Type': 'application/json' }
          })
          .then(response => {
            this.response = response.data;
            console.log('Response:', response.data);
            alert('空闲时间增加成功！');
            this.resetForm();
            this.fetchAvailabilities(); // 更新空闲时间列表
          })
          .catch(error => {
            console.error('Failed to add availability slot:', error);
            alert('增加空闲时间失败，请重试。');
          });
    },
    acceptAppointment(appointmentId) {
      axios
          .put(`http://localhost:8088/appointments/accept/${appointmentId}`)
          .then(response => {
            console.log('Response:', response.data);
            alert('预约接受成功！');
            this.fetchAppointments();
          })
          .catch(error => {
            console.error('Failed to accept appointment:', error);
            alert('接受预约失败，请重试。');
          });
    },
    refuseAppointment(appointmentId) {
      axios
          .put(`http://localhost:8088/appointments/refuse/${appointmentId}`)
          .then(response => {
            console.log('Response:', response.data);
            alert('预约拒绝成功！');
            this.fetchAppointments();
          })
          .catch(error => {
            console.error('Failed to refuse appointment:', error);
            alert('拒绝预约失败，请重试。');
          });
    },
    updateStatus() {
      const slotId = this.statusUpdate.slotId;
      const newStatus = this.statusUpdate.newStatus;
      const payload = { status: newStatus };

      axios
          .put(
              `http://localhost:8088/availabilitySlots/updateStatus/${slotId}/${newStatus}`,
              payload,
              {
                headers: { 'Content-Type': 'application/json' }
              }
          )
          .then(response => {
            this.response = response.data;
            console.log('Response:', response.data);
            alert('状态更新成功！');
            this.resetStatusForm();
            this.fetchAvailabilities(); // 更新空闲时间列表
          })
          .catch(error => {
            console.error('Failed to update status:', error);
            alert('状态更新失败，请重试。');
          });
    },
    resetForm() {
      this.newSlot.teacherId = '';
      this.newSlot.startTime = '';
      this.newSlot.endTime = '';
    },
    resetStatusForm() {
      this.statusUpdate.slotId = '';
      this.statusUpdate.newStatus = 'AVAILABLE';
    },
    formatDate(date) {
      if (!date) return '';
      const d = new Date(date);
      return d.toLocaleString();
    },
    showSection(section) {
      this.currentSection = section;
      if (section === 'viewAvailability') {
        this.fetchAvailabilities(); // 切换到查看空闲时间页面时更新列表
      }
    },
    logout() {
      this.$router.push({ name: 'LoginMain' });
    }
  }
};
</script>

<style scoped>
.teacher-main {
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

.table-container,
.form-container,
.delete-container {
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

th,
td {
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

input,
select {
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

.action-buttons {
  display: flex;
  gap: 10px;
}
</style>
