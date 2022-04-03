/** Just a template, delete me once project done developing. */
import request from "../../utils/Ajax";

const state = {
    meInfoTemplate: {
        no: '',
        name: '',
        gender: '',
        nation: '',
        birth: '',
        enrollTime: '',
        politic: '',
        major: '',
        level: '',
        levelUnit: '',
        levelDate: '',
        jobAlias: '',
        researchDirection: '',
        job: '',
        department: '',
        idcard: '',
        phone: '',
        longPhone: '',
        shortPhone: ''
    },
    meInfo: {}
}

const getters = {
}

const actions = {
    async requestUpdateAvatar({ rootState }, newAvatarFile) {
        let formData = new FormData();
        formData.append('avatar', newAvatarFile);
        let res = await request('post', '/me/updateAvatar', formData, true, {
            'Content-Type': 'multipart/form-data'
        });
        if (res.status === 200 && res.data.code === 200) {
            rootState.notify('头像已更改', 'success');
        } else {
            rootState.notify('头像未成功更改');
        }
    },
    async requestMeInfo({ commit, rootState }) {
        let res = await request('get', '/me', null);
        if (res.status === 200 && res.data.code === 200) {
            commit('meInfo', res.data);
        } else {
            commit('meInfo', null);
            rootState.notify(res.data.message);
        }
    },
    async requestUpdateMeInfo({ rootState }, params) {
        let res = await request('put', '/me', params);
        if (res.status === 200 && res.data.code === 200) {
            rootState.notify('相应字段已更改为' + params[Object.keys(params)[0]] + ', 您可能需要重新登录以获取视图更改。', 'success');
        } else {
            rootState.notify(res.data.message);
        }
    }
}

const mutations = {
    meInfo(state, data) {
        if (data == null)
            state.meInfo = state.meInfoTemplate;
        else
            state.meInfo = Object.assign({}, state.meInfoTemplate, data.result);
    }
}

export default {
    namespaced: true,
    state,
    getters,
    actions,
    mutations
}