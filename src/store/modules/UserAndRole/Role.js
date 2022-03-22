import request from "../../../utils/Ajax";
import jsCookie from "js-cookie";

const state = {
    fieldsMapper: {
        id: { text: '#', width: '20%' },
        name: { text: '角色名', width: '40%' },
        description: '描述'
    },
    rows: {},
    total: 0
}

const getters = {
    total: state => state.total,
}

const actions = {
    async requestList({ commit, rootState }, params) {
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
        } else {
            rootState.notify(res.data.message);
            commit('roleList', { result: [], total: 0 });
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