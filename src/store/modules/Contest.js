import request from "../../utils/Ajax";

const state = {
    table: { header: {}, rows: [] },
    total: 0,
    fieldsMapper: {
        id: { hidden: true },
        no: '编号',
        type: '类型',
        name: '名称',
        tutorName: '指导/获奖教师',
        prize: '奖项',
        level: '级别',
        awardTime: { text: '获奖时间', time: 'YYYY-MM-DD' },
        addTime: { text: '登记时间', time: 'YYYY-MM-DD hh:mm' },
        adderName: '登记人',
        edit: '编辑',
        delete: '移除'
    },
    // used for response of requesting one contest
    contest: {},
    contestTemplate: {
        id: -1,
        no: "",
        type: "",
        name: "",
        students: [],
        tutorStaffInfoId: -1,
        tutorNo: "",
        tutorName: "",
        prize: "",
        level: "",
        awardTime: "",
        certificate: "",
        addTime: "",
        addStaffInfoId: -1,
        adderNo: "",
        adderName: ""
    },
    // used for download template
    importTemplate: {
        type: '类型',
        no: '编号',
        name: '名称',
        tutorNo: '指导/获奖教师编号',
        prize: '奖项',
        level: '级别',
        awardTime: '获奖时间',
        addTime: '登记时间',
        adderNo: '登记人编号'
    },
    responseStatus: true,
}

const getters = {
    getTotal: state => state.total
}

const actions = {
    async requestList({ commit, rootState }, params) {
        let res = await request("post", "/contest/get/all", {
            role: localStorage.getItem("role"),
            page: params.page,
            count: params.amount,
            query: params.query || null
        });
        if (res.status === 200 && res.data.code === 200) {
            commit('contestList', res.data);
        } else {
            rootState.notify(res.data.message);
            commit('contestList', { result: { header: {}, rows: [] }, total: 0 });
        }
    },
    async requestOne({ commit, rootState }, params) {
        let res = await request('post', "/contest/get", {
            role: localStorage.getItem("role"),
            id: params.id
        });
        if (res.status === 200 && res.data.code === 200) {
            commit('contest', res.data);
        } else {
            rootState.notify(res.data.message);
            commit('contest', {});
        }
    }
}

const mutations = {
    contestList(state, data) {
        if (data.result.header.includes('*')) {
            state.table.header = state.fieldsMapper;
        } else {
            let result = {};
            for (let head of data.result.header) {
                result[head] = state.fieldsMapper[head];
            }
            result.edit = '编辑';
            result.delete = '移除';
            state.table.header = result;
        }
        state.table.rows = data.result.rows;
        state.total = data.total;
    },
    contest(state, data) {
        state.contest = Object.assign(state.contestTemplate, { ...data.result, students: JSON.parse(data.result.students) });
    }
}

export default {
    namespaced: true,
    state,
    getters,
    actions,
    mutations
}