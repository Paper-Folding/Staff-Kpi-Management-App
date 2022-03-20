export default [
    {
        path: '/usermgr',
        name: 'User',
        meta: { title: '用户管理' },
        component: () => import('../views/UserAndRole/User.vue'),
    },
    {
        path: '/rolemgr',
        name: 'Role',
        meta: { title: '用户角色管理' },
        component: () => import('../views/UserAndRole/Role.vue'),
    },
]