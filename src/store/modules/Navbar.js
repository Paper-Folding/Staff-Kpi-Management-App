import request from "../../utils/Ajax.js";
import Auth from "../../utils/Auth.js";

const state = {
    roleList: [],
    role: null
}

const getters = {

}

const actions = {
    // get current logged user role details
    async requestUserRoleDetails({ commit, rootState }) {
        if (Auth.isUserLogined()) {
            const user = Auth.getLoggedUser();
            let res = await request("post", "/auth/get/role", {
                authenticationId: user.id
            });
            if (res.status === 200 && res.data.code === 200) {
                commit('requestDone', res.data);
            } else {
                rootState.notify("server error");
            }
        }
    },
    async requestChangePassword({ commit, rootState }, params) {
        let res = await request('put', '/me/changePassword', {
            password: params.password
        });
        if (res.status === 200 && res.data.code === 200) {
            rootState.notify('密码已成功修改为 ' + params.password + '! (对了我是故意的！)', 'success');
        } else {
            rootState.notify("server error");
        }
    }
}

const mutations = {
    requestDone(state, res) {
        state.roleList = res.result;
        state.role = res.result.filter(ele => ele.name === Auth.getCurrentRole())?.[0];
    }
}

export default {
    namespaced: true,
    state,
    getters,
    actions,
    mutations
}