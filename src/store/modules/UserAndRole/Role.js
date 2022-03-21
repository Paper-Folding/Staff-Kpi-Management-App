import request from "../../../utils/Ajax";
import jsCookie from "js-cookie";

const state = {
    fieldsMapper: {
        id: '#',
        name: '角色名',
        description: '描述'
    },
    rows: {},
    total: 0
}

const getters = {
    total: state => state.total,
}

const actions = {
    async requestList({ commit }, params) {
        let res = await request('post', '/role/get/all', {
            page: params.page,
            count: params.amount,
            // query: {
            //     name: "add",
            //     description: "江"
            // }
        });
        if (res.status === 200 && res.data.code === 200) {
            commit('roleList', res.data);
        }
    }
}

const mutations = {
    roleList(state, data) {
        state.rows = data.result;
        state.total = data.total;
    }
}

export default {
    namespaced: true,
    state,
    getters,
    actions,
    mutations
}