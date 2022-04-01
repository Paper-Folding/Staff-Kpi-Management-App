import { createRouter, createWebHistory } from 'vue-router';
import auth from "../utils/Auth.js";
import UserAndRole from './UserAndRole.js';

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
        meta: { requiresAuth: true },
        component: () => import('../views/Dashboard.vue'),
        children: [
            {
                path: '',
                name: 'Home',
                meta: { title: '欢迎您' },
                component: () => import('../views/Home.vue'),
            },
            ...UserAndRole,
            {
                path: '/me',
                name: 'Me',
                meta: { title: '我的个人信息' },
                component: () => import('../views/Me.vue')
            }
        ]
    },
    { path: '/:pathMatch(.*)*', meta: { title: 'Something Went Wrong' }, component: () => import('../views/FourZeroFour.vue') }
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