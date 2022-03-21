import request from "../../../utils/Ajax";
import jsCookie from "js-cookie";

const state = {
    fieldsMapper: {
        id: '#',
        name: '角色名',
        description: '描述'
    },
    rows: {}
}

const getters = {
}

const actions = {
    async requestList({ commit }) {
        let res = await request('post', '/role/get/all', {
            page: 1,
            count: 10,
            query: {
                name: "ad",
                description: "江"
            }
        });
        if (res.status === 200 && res.data.code === 200) {
            commit('roleList', res.data.result);
        }
    }
}

const mutations = {
    roleList(state, data) {
        state.rows = data;
    }
}

export default {
    namespaced: true,
    state,
    getters,
    actions,
    mutations
}