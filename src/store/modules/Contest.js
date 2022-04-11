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
    // records fields that are allowed by current user selected role
    allowedFields: [],
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
        certificate: "{}",
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
    staffList: [], // for select
    uploadedCertInfo: {}
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
            commit('contestList', { result: { header: [], rows: [] }, total: 0 });
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
    },
    async requestUpdateCert({ rootState }, params) {
        let formData = new FormData();
        formData.append('cert', params.cert);
        formData.append('role', localStorage.getItem("role"));
        formData.append('contestId', params.id);
        let res = await request('post', '/contest/updateCert', formData, true, {
            'Content-Type': 'multipart/form-data'
        });
        if (res.status === 200 && res.data.code === 200) {
            rootState.notify('证书已更新', 'success');
        } else {
            rootState.notify(res.data.message);
        }
    },
    async requestStaffList({ commit }) {
        let res = await request('post', '/contest/get/staffList', {
            role: localStorage.getItem("role")
        });
        if (res.status === 200 && res.data.code === 200) {
            commit('staffList', res.data);
        }
    },
    async requestUpdate({ rootState }, params) {
        if (params == null || Object.keys(params).length === 0)
            return;
        let res = await request('put', '/contest', {
            role: localStorage.getItem("role"),
            ...params
        });
        if (res.status === 200 && res.data.code === 200) {
            rootState.notify('修改居然成功了....', 'success');
        } else {
            rootState.notify(res.data.message);
        }
    },
    async requestUploadCert({ rootState, commit }, params) {
        let formData = new FormData();
        formData.append('cert', params.cert);
        formData.append('role', localStorage.getItem("role"));
        let res = await request("post", "/contest/uploadCert", formData, true, {
            'Content-Type': 'multipart/form-data'
        });
        if (res.status === 200 && res.data.code === 200) {
            rootState.notify('证书已上传', 'success');
            commit('uploadCert', res.data);
        } else {
            rootState.notify(res.data.message);
            commit('uploadCert', null);
        }
    },
    async requestAdd({ rootState, state }, params) {
        params.students = JSON.stringify(typeof params.students !== 'string' ? params.students : []);
        let res = await request("post", "/contest", {
            role: localStorage.getItem("role"),
            ...params
        });
        if (res.status === 200 && res.data.code === 200) {
            state.responseStatus = true;
            rootState.notify('已添加！', 'success');
        } else {
            state.responseStatus = false;
            rootState.notify(res.data.message);
        }
    },
    async requestDelete({ rootState }, params) {
        let res = await request('delete', "/contest", {
            role: localStorage.getItem("role"),
            id: params.id
        });
        if (res.status === 200 && res.data.code === 200) {
            rootState.notify('删除了,现在你满意了吧~', 'success');
        } else {
            rootState.notify(res.data.message);
        }
    }
}

const mutations = {
    contestList(state, data) {
        if (data.result.header.includes('*')) {
            state.table.header = state.fieldsMapper;
            state.allowedFields = Object.keys(state.contestTemplate);
        } else {
            let result = {};
            for (let head of data.result.header) {
                if (head in state.fieldsMapper) {
                    result[head] = state.fieldsMapper[head];
                }
            }
            state.allowedFields = data.result.header;
            result.edit = '编辑';
            result.delete = '移除';
            state.table.header = result;
        }
        state.table.rows = data.result.rows;
        state.total = data.total;
    },
    contest(state, data) {
        state.contest = Object.assign({}, state.contestTemplate, { ...data.result, students: JSON.parse(data.result.students || '[]') });
    },
    staffList(state, data) {
        state.staffList = data.result;
    },
    uploadCert(state, data) {
        if (data == null)
            state.responseStatus = false;
        else {
            state.responseStatus = true;
            state.uploadedCertInfo = JSON.stringify(data.result);
        }
    }
}

export default {
    namespaced: true,
    state,
    getters,
    actions,
    mutations
}