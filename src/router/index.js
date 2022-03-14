import { createRouter, createWebHistory } from 'vue-router';
import auth from "../utils/Auth.js";

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
        meta: { title: '你好鸭', requiresAuth: true },
        component: () => import('../views/Dashboard.vue')
    }
];

const router = createRouter({
    history: createWebHistory('./'),
    routes
});

router.beforeEach((to, from, next) => {
    if (to.fullPath === '/' && auth.isUserLogined()) {
        next('/dashboard');
        return;
    }
    document.title = to.meta.title;
    if (to.meta.requiresAuth) {
        if (auth.isUserLogined())
            next();
        else
            next({ path: "/", query: { redirect: to.fullPath, msg: 'expired' } });
    } else
        next();
});

export default router;