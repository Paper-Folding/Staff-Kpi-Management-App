import { createRouter, createWebHistory } from 'vue-router';

const routes = [
    {
        path: '/',
        name: 'Login',
        meta: { title: '登录' },
        alias: '/login',
        component: () => import('../views/Login.vue')
    },
    {
        path: '/dashboard',
        name: 'Dashboard',
        meta: { title: '你好鸭' },
        component: () => import('../views/Dashboard.vue')
    }
];

const router = createRouter({
    history: createWebHistory('./'),
    routes
});

export default router;