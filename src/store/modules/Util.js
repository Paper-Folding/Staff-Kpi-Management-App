/** Just a template, delete me once project done developing. */
import request from "../../utils/Ajax";

const state = {
    permissionList: []
}

const getters = {

}

const actions = {
    async requestPermissionList({ commit }, params) {
        if (params.roleName === 'officer' || params.roleName === 'admin'){
            commit('permissionList', '*');
            return;
        }
        let res = await request('post', '/auth/get/permission', {
            "authenticationId": params.authenticationId ?? null,
            "username": params.username ?? null,
            "roleId": params.roleId ?? null,
            "roleName": params.roleName ?? null,
            "tableName": params.tableName
        });
        if (res.status === 200 && res.data.code === 200)
            commit('permissionList', res.data.result);
        else
            commit('permissionList', []);

    }
}

const mutations = {
    permissionList(state, data) {
        state.permissionList = data;
    }
}

export default {
    namespaced: true,
    state,
    getters,
    actions,
    mutations
}