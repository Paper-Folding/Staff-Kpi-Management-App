import { createRouter, createWebHistory } from 'vue-router';

const routes = [
    {
        path: '/',
        name: 'Login',
        meta: { title: '登录' },
        alias: '/login',
        component: () => import('../views/Login.vue')
    },
];

const router = createRouter({
    history: createWebHistory('./'),
    routes
});

export default router;