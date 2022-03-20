import axios from "axios";
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
    async getRoleList({ commit }) {
        let res = await axios.get(import.meta.env.VITE_API_URL + "/role/all");
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