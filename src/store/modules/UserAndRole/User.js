/** Just a template, delete me once project done developing. */
import request from "../../../utils/Ajax";

const state = {
    table: { header: {}, rows: [] },
    total: 0,
    fieldsMapper: {
        id: { hidden: true },
        no: { text: '工号' },
        name: '姓名',
        gender: '性别',
        nation: '民族',
        birth: { text: '诞辰', time: "YYYY-MM-DD" },
        enrollTime: { text: '入坑时间', time: "YYYY-MM-DD" },
        politic: '政治面貌',
        major: '专业',
        level: '学位',
        levelUnit: '学位授予单位',
        levelDate: { text: '学位授予日期', time: "YYYY-MM-DD" },
        jobAlias: '职称',
        researchDirection: '研究方向',
        job: '职务',
        department: '部门',
        idcard: '身份证号',
        phone: '办公电话',
        longPhone: '长号',
        shortPhone: '短号'
    }
}

const getters = {
    getTotal: state => state.total
}

const actions = {
    async requestStaffList({ commit, rootState }, params) {
        let res = await request("post", "/staffInfo/get/all", {
            role: localStorage.getItem("role"),
            page: params.page,
            count: params.amount,
            query: params.query || null
        });
        if (res.status === 200 && res.data.code === 200) {
            commit('staffList', res.data);
        } else {
            rootState.notify(res.data.message);
            commit('staffList', { result: { header: {}, rows: [] }, total: 0 });
        }
    }
}

const mutations = {
    staffList(state, data) {
        if (data.result.header.includes('*')) {
            state.table.header = state.fieldsMapper;
        } else {
            let result = {};
            for (let head of data.result.header) {
                result[head] = state.fieldsMapper[head];
            }
            state.table.header = result;
        }
        state.table.rows = data.result.rows;
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