import request from "../../../utils/Ajax";

const state = {
    fieldsMapper: {
        id: { text: '#', width: '10%' },
        name: { text: '角色名', width: '30%' },
        description: { text: '描述', width: '45%' },
        edit: '',
        delete: ''
    },
    rows: {},
    total: 0,
    singleRole: null, // used for see single role detail, edit or others
    responseStatus: true, // used for identify if some request is successful
}

const getters = {
    total: state => state.total,
}

const actions = {
    async requestList({ commit, rootState }, params) {
        let res = await request('post', '/role/get/all', {
            role: localStorage.getItem('role'),
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
    },
    async requestRole({ commit }, params) {
        let res = await request('post', '/role/get', {
            role: localStorage.getItem('role'),
            id: params.id
        });
        if (res.status === 200 && res.data.code === 200) {
            commit('role', res.data);
        } else {
            rootState.notify(res.data.message);
            commit('role', null);
        }
    },
    async requestDeleteRole({ rootState }, params) {
        let res = await request("delete", '/role', {
            role: localStorage.getItem('role'),
            id: params.id,
            name: params.name
        })
        if (res.status === 200 && res.data.code === 200) {
            rootState.notify('角色' + params.name + '已删除', 'success');
        } else {
            rootState.notify(res.data.message);
        }
    },
    async requestAddRole({ rootState }, params) {
        let res = await request("post", '/role', {
            role: localStorage.getItem('role'),
            id: params.id,
            name: params.name,
            description: params.description,
            roleScopes: params.roleScopes
        });
        if (res.status === 200 && res.data.code === 200) {
            rootState.notify('角色' + params.name + '已添加', 'success');
        } else {
            rootState.notify(res.data.message);

        }
    },
    async requestUpdateRole({ commit, rootState }, params) {
        let res = await request("put", '/role', {
            role: localStorage.getItem('role'),
            id: params.id,
            name: params.name,
            description: params.description,
            roleScopes: params.roleScopes
        });
        if (res.status === 200 && res.data.code === 200) {
            rootState.notify('角色' + params.name + '已更改', 'success');
            commit('updateRole', true);
        } else {
            rootState.notify(res.data.message);
            commit('updateRole', false);
        }
    },
}

const mutations = {
    roleList(state, data) {
        state.rows = data.result;
        state.total = data.total;
    },
    role(state, data) {
        state.singleRole = data?.result;
    },
    updateRole(state, isSuccess) {
        state.responseStatus = isSuccess;
    }
}

export default {
    namespaced: true,
    state,
    getters,
    actions,
    mutations
}