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