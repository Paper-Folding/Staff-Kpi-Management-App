import request from "../../utils/Ajax";

const state = {
    contest: {},
    staff: {}
}

const getters = {
    getContest: state => state.contest,
    getStaff: state => state.staff
}

const actions = {
    async requestContestStatistics({ commit }) {
        let res = await request('get', '/statistics/contest');
        if (res.status === 200 && res.data.code === 200) {
            commit('contest', res.data);
        } else {
            commit('contest', {});
        }
    },
    async requestStaffStatistics({ commit }) {
        let res = await request('get', '/statistics/staff');
        if (res.status === 200 && res.data.code === 200) {
            commit('staff', res.data);
        } else {
            commit('staff', {});
        }
    }
}

const mutations = {
    contest(state, data) {
        state.contest = 'result' in data ? data.result : {};
    },
    staff(state, data) {
        state.staff = 'result' in data ? data.result : {};
    }
}

export default {
    namespaced: true,
    state,
    getters,
    actions,
    mutations
}