const state = {
    menuContent: [
        { header: "业绩管理后台", hiddenOnCollapse: true },
        { title: "主页", icon: "bi-house", href: "/dashboard" },
        {
            title: "用户与权限", icon: "bi-people-fill", child: [
                { title: "用户", icon: "bi-file-person", href: '/usermgr', badge: { element: 'i', class: 'bi-question-circle', help: '轻松管理全站用户信息' } },
                { title: "角色与权限", icon: "bi-key", href: '/rolemgr', badge: { element: 'i', class: 'bi-question-circle', help: '定义并管理全站角色及所属权限' } },
            ]
        },
        { title: "竞赛管理", icon: "bi-journal-bookmark-fill", href: '/contest', badge: { element: 'i', class: 'bi-question-circle', help: '典型示例管理模块: 竞赛与获奖管理' } },
        { title: "登出", icon: "bi-door-open", logoffBtn: true },
    ]
}

const getters = {
    getMenuContent: state => state.menuContent
}

const actions = {

}

const mutations = {

}

export default {
    namespaced: true,
    state,
    getters,
    actions,
    mutations
}