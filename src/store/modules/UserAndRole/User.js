/** Just a template, delete me once project done developing. */
import axios from "axios";
import jsCookie from "js-cookie";

const state = {
    fieldsMapper: {
        id: '#',
        no: '工号',
        name: '姓名',
        gender: '性别',
        nation: '民族',
        birth: '诞辰',
        "enroll_time": '入坑时间',
        politic: '政治面貌',
        major: '专业',
        level: '学位',
        "level_unit": '学位授予单位',
        "level_date": '学位授予日期',
        "job_alias": '职称',
        "research_direction": '研究方向',
        job: '职务',
        department: '部门',
        idcard: '身份证号',
        phone: '办公电话',
        "long_phone": '长号',
        "short_phone": '短号'
    }
}

const getters = {

}

const actions = {

}

const mutations = {

}

export default {
    namespaced: true,
    state,
    getters,
    actions,
    mutations
}