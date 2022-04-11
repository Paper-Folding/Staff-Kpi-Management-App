/** Just a template, delete me once project done developing. */
import request from "../../../utils/Ajax";

const state = {
    table: { header: {}, rows: [] },
    total: 0,
    fieldsMapper: {
        id: { hidden: true },
        no: '工号',
        name: '姓名',
        gender: '性别',
        nation: '民族',
        birth: { text: '诞辰', time: "YYYY-MM-DD" },
        enrollTime: { text: '入职时间', time: "YYYY-MM-DD" },
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
        shortPhone: '短号',
        edit: '编辑角色',
        delete: '移除'
    },
    importTemplate: {
        no: '工号',
        name: '姓名',
        gender: '性别',
        nation: '民族',
        birth: '诞辰',
        enrollTime: '入职时间',
        politic: '政治面貌',
        major: '专业',
        level: '学位',
        levelUnit: '学位授予单位',
        levelDate: '学位授予日期',
        jobAlias: '职称',
        researchDirection: '研究方向',
        job: '职务',
        department: '部门',
        idcard: '身份证号',
        phone: '办公电话',
        longPhone: '长号',
        shortPhone: '短号'
    },
    exportsData: {},
    responseStatus: true,
    roleList: [],
    userRoleList: []
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
            commit('staffList', { result: { header: [], rows: [] }, total: 0 });
        }
    },
    async requestImport({ rootState }, params) {
        let res = await request("post", "/staffInfo/import", {
            role: localStorage.getItem("role"),
            list: params.list
        }, true, {}, 100000);
        if (res.status === 200 && res.data.code === 200) {
            rootState.notify("导入成功！", 'success');
        } else {
            rootState.notify(res.data.message);
        }
    },
    async requestExport({ commit, rootState }) {
        let res = await request("post", "/staffInfo/export", {
            role: localStorage.getItem("role")
        }, true, {}, 100000);
        if (res.status === 200 && res.data.code === 200) {
            commit('export', res.data);
        } else {
            rootState.notify(res.data.message);
            commit('export', false);
        }
    },
    async requestDelete({ rootState }, params) {
        let res = await request('delete', "/staffInfo", {
            role: localStorage.getItem("role"),
            id: params.id
        });
        if (res.status === 200 && res.data.code === 200) {
            rootState.notify("已删除", 'success');
        } else {
            rootState.notify(res.data.message);
        }
    },
    async attachRole({ rootState }, params) {
        let res = await request("put", "/staffInfo/attachRole", {
            role: localStorage.getItem("role"),
            id: params.id,
            roles: params.roles
        });
        if (res.status === 200 && res.data.code === 200) {
            rootState.notify("角色Attached！", 'success');
        } else {
            rootState.notify(res.data.message);
        }
    },
    async requestRoleList({ commit, rootState }) {
        let res = await request('post', "/auth/get/role/all");
        if (res.status === 200 && res.data.code === 200) {
            commit('roleList', res.data);
        } else {
            rootState.notify(res.data.message);
            commit('roleList', null);
        }
    },
    async requestUserRoleList({ commit, rootState }, params) {
        let res = await request('post', "/auth/get/role", {
            staffInfoId: params.id
        });
        if (res.status === 200 && res.data.code === 200) {
            commit('userRoleList', res.data);
        } else {
            rootState.notify(res.data.message);
            commit('userRoleList', null);
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
            result.edit = '编辑角色';
            result.delete = '移除';
            state.table.header = result;
        }
        state.table.rows = data.result.rows;
        state.total = data.total;
    },
    export(state, data) {
        if (data === false) {
            state.responseStatus = false;
            return;
        }
        state.responseStatus = true;
        state.exportsData = {
            header: data.result.header.includes('*') ? Object.keys(state.importTemplate) : data.result.header,
            rows: data.result.rows
        }
        for (let row of state.exportsData.rows) {
            if ('id' in row)
                delete row.id;
        }
    },
    roleList(state, data) {
        if (data == null)
            state.roleList = [];
        else
            state.roleList = data.result;
    },
    userRoleList(state, data) {
        if (data == null)
            state.userRoleList = [];
        else
            state.userRoleList = data.result;
    }
}

export default {
    namespaced: true,
    state,
    getters,
    actions,
    mutations
}