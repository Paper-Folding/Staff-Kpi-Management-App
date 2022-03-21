import request from "../../utils/Ajax";
import jsCookie from "js-cookie";
import auth from "../../utils/Auth.js";

const state = {
    logined: auth.isUserLogined(),
}

const getters = {

}

const actions = {
    async postLogin({ commit, rootState }, postData) {
        try {
            const result = await request('post', '/auth/login', {
                username: postData.username,
                password: postData.password
            }, false);
            if (result.status === 200 && result.data.code === 200)
                commit('loginSuccess', result.data);
            else
                commit('loginFailure');
        }
        catch (err) {
            rootState.notify(err);
        }
    },
    logoff({ commit }) {
        commit("logoff");
    }
}

const mutations = {
    loginSuccess(state, response) {
        jsCookie.set('loggedUser', JSON.stringify({ ...response, token: response.type + ' ' + response.token, code: undefined, type: undefined }), { expires: 10000 }); // 1/48 = 30min to expire
        state.logined = true;
    },
    loginFailure(state) {
        jsCookie.remove('loggedUser');
        state.logined = false;
    },
    logoff(state) {
        jsCookie.remove('loggedUser');
        state.logined = false;
    }
}

export default {
    namespaced: true,
    state,
    getters,
    actions,
    mutations
}