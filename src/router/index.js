import Vue from 'vue';
import Router from 'vue-router';
import HomeMain from '../components/HomeMain.vue';
import LoginMain from '../components/LoginMain.vue';
import AdminDashboard from '../components/AdminDashboard.vue';
import TeacherMain from '../components/TeacherMain.vue';
import StudentMain from '../components/StudentMain.vue';

Vue.use(Router);

export default new Router({
    mode: 'history',
    routes: [
        {
            path: '/',
            name: 'HomeMain',
            component: HomeMain
        },
        {
            path: '/login',
            name: 'LoginMain',
            component: LoginMain
        },
        {
            path: '/admin-dashboard',
            name: 'AdminDashboard',
            component: AdminDashboard
        },
        {
            path: '/teacher-main/:teacherId',
            name: 'TeacherMain',
            component: TeacherMain
        },
        {
            path: '/student-main',
            name: 'StudentMain',
            component: StudentMain
        }
    ]
});
