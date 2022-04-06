import { createRouter, createWebHistory } from 'vue-router';
import auth from "../utils/Auth.js";
import store from '../store';
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
                meta: { title: '关于我' },
                component: () => import('../views/Me.vue')
            },
            {
                path: '/contest',
                name: 'Contest',
                meta: { title: '竞赛管理', permission: 'contest' },
                component: () => import('../views/Contest.vue')
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

router.afterEach((to, from) => {
    if ('permission' in to.meta) {
        store.dispatch('Util/requestPermissionList', {
            username: auth.getLoggedUser().username,
            roleName: localStorage.getItem('role'),
            tableName: to.meta.permission
        });
    }
})

export default router;