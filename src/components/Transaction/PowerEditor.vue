<!-- 权限编辑器 -->
<template>
    <CCS v-model="processedInitialList"></CCS>
</template>

<script>
import CCS from "../CollapsibleChecks/Container.vue"
export default {
    data() {
        return {
            processedInitialList: []
        }
    },
    props: {
        // used for pass in intialize data
        initial: [Array, null]
    },
    created() {
        this.template = [
            {
                text: '员工基本信息', name: 'staff_info',
                children: [
                    {
                        text: '查询/搜索', name: 'select', dbid: 1, children: [
                            { text: '工号(no)', name: 'no' },
                            { text: '姓名', name: 'name' },
                            { text: '性别', name: 'gender' },
                            { text: '民族', name: 'nation' },
                            { text: '出生年月日', name: 'birth' },
                            { text: '参加工作时间', name: 'enroll_time' },
                            { text: '政治面貌', name: 'politic' },
                            { text: '专业', name: 'major' },
                            { text: '学历学位', name: 'level' },
                            { text: '学历学位授予单位', name: 'level_unit' },
                            { text: '学历学位授予日期', name: 'level_date' },
                            { text: '职称', name: 'job_alias' },
                            { text: '主要研究方向', name: 'research_direction' },
                            { text: '职务', name: 'job' },
                            { text: '部门', name: 'department' },
                            { text: '身份证号', name: 'idcard' },
                            { text: '办公电话', name: 'phone' },
                            { text: '长号', name: 'long_phone' },
                            { text: '短号', name: 'short_phone' },
                        ],
                    },
                    { text: '插入/添加', name: 'insert', dbid: 2 },
                    { text: '删除', name: 'delete', dbid: 3 },
                    { text: '修改', name: 'update', dbid: 4 },
                    { text: '导出查询权限允许的字段数据', name: 'export', dbid: 5 },
                    { text: '导入数据/批量插入', name: 'import', dbid: 6 },
                ]
            }
        ];
        if (this.initial == null || this.initial.length === 0) {
            this.processedInitialList = this.template;
        } else {
            this.inTakeInitialList(this.removeRedundant());
        }
    },
    methods: {
        collect() {

        },
        inTakeInitialList(list) {
            return list;
        },
        // sometimes * and common fields both exist, remove common fields when * exists
        removeRedundant(list) {
            let result = [];
            let asterItems = list.filter(ele => ele.columnName === '*');
            for (let item of asterItems) {
                result = [...list.filter(ele => ele.tableName !== item.tableName), item];
            }
            return result;
        }

    },
    watch: {
        initial(newVal) {
            this.processedInitialList = this.inTakeInitialList(this.removeRedundant(newVal));
        }
    },
    components: {
        CCS
    }
}
</script>

<style lang="scss" scoped>
</style>