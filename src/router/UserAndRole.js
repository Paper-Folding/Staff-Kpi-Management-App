export default [
    {
        path: '/usermgr',
        name: 'User',
        meta: { title: '用户管理', permission: 'staff_info' },

        component: () => import('../views/UserAndRole/User.vue'),
    },
    {
        path: '/rolemgr',
        name: 'Role',
        meta: { title: '用户角色管理', permission: 'role' },
        component: () => import('../views/UserAndRole/Role.vue'),
    },
]