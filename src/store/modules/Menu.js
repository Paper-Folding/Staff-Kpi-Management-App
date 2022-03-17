import axios from "axios";
import jsCookie from "js-cookie";

const state = {
    menuContent: [
        {
            header: "业绩管理后台",
            hiddenOnCollapse: true
        },
        { title: "主页", icon: "bi-house", href: "/dashboard" },
        { title: "退出登录", icon: "bi-door-open", logoffBtn: true },
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