import axios from "axios";
import jsCookie from "js-cookie";
import auth from "../../utils/Auth.js";

const state = {
    logined: auth.isUserLogined(),
}

const getters = {

}

const actions = {
    async postLogin({ commit }, postData) {
        const result = await axios.post(import.meta.env.VITE_API_URL + "/auth/login", {
            username: postData.username,
            password: postData.password
        });
        if (result.status === 200 && result.data.code === 200)
            commit('loginSuccess', result.data);
        else
            commit('loginFailure', result.data);
    },
    logOff({ commit }) {
        commit("logOff");
    }
}

const mutations = {
    loginSuccess(state, response) {
        jsCookie.set('loggedUser', JSON.stringify({ ...response, token: response.type + ' ' + response.token, code: undefined, type: undefined }), { expires: 1 / 48 }); // 30min to expire
        state.logined = true;
    },
    loginFailure(state) {
        jsCookie.remove('loggedUser');
        state.logined = false;
    },
    logOff(state) {
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