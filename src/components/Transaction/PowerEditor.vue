<!-- 权限编辑器 -->
<template>
    <CCS v-model="processedInitialList"></CCS>
</template>

<script>
import CCS from "../CollapsibleChecks/Container.vue";
import tool from "../CollapsibleChecks/tool";
export default {
    data() {
        return {
            processedInitialList: [],
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
            },
            {
                text: '研究项目(含科研与教研)', name: 'research',
                children: [
                    {
                        text: '查询/搜索', name: 'select', dbid: 1, children: [
                            { text: '项目编号(no)', name: 'no' },
                            { text: '项目名称', name: 'name' },
                            { text: '项目级别', name: 'level' },
                            { text: '经费额度', name: 'budget' },
                            { text: '负责人', name: 'dutier_staff_info_id' },
                            { text: '负责单位', name: 'department' },
                            { text: '立项时间', name: 'found_time' },
                            { text: '截止时间', name: 'deadline' },
                            { text: '是否结题', name: 'is_finish' },
                            { text: '结题时间', name: 'finish_time' },
                            { text: '登记时间', name: 'add_time' },
                            { text: '登记人', name: 'add_by_staff_info_id' }
                        ],
                    },
                    { text: '插入/添加', name: 'insert', dbid: 2 },
                    { text: '删除', name: 'delete', dbid: 3 },
                    { text: '修改', name: 'update', dbid: 4 },
                    { text: '导出查询权限允许的字段数据', name: 'export', dbid: 5 },
                    { text: '导入数据/批量插入', name: 'import', dbid: 6 },
                ]
            }
        ]
        if (this.initial == null || this.initial.length === 0) {
            this.processedInitialList = JSON.parse(JSON.stringify(this.template)); // deep clone
        }
    },
    methods: {
        // collect data
        collect() {
            let result = [];
            for (let table of this.processedInitialList) {
                // skip digging deeper if table is unchecked
                if (table.checked === 0 || table.checked == null)
                    continue;
                for (let operation of table.children) {
                    // skip digging deeper if operation is unchecked
                    if (operation.checked === 0 || operation.checked == null)
                        continue;
                    if (tool.hasChildren(operation)) {
                        // if opration with selecting all, collect all its sub items with '*'
                        if (operation.checked === 2) {
                            result.push({
                                operationId: operation.dbid,
                                tableName: table.name,
                                fieldName: '*'
                            });
                        } else {
                            for (let field of operation.children) {
                                if (field.checked === 2) {
                                    // collect !
                                    result.push({
                                        operationId: operation.dbid,
                                        tableName: table.name,
                                        fieldName: field.name
                                    });
                                }
                            }
                        }
                    } else {
                        result.push({
                            operationId: operation.dbid,
                            tableName: table.name,
                            fieldName: '*'
                        });
                    }
                }
            }
            return result;
        },
        // intake template with initial list
        inTakeInitialList(list) {
            let result = JSON.parse(JSON.stringify(this.template)); // deep clone
            for (let item of list) {
                for (let table of result) {
                    if (table.name === item.tableName) {
                        for (let operation of table.children) {
                            if (operation.dbid === item.operationId) {
                                if (item.columnName === '*') {
                                    if (tool.hasChildren(operation)) {
                                        operation.children.forEach(ele => ele.checked = 2);
                                    } else {
                                        operation.checked = 2;
                                    }
                                } else {
                                    for (let field of operation.children) {
                                        if (field.name === item.columnName) {
                                            field.checked = 2;
                                            break;
                                        }
                                    }
                                }
                                break;
                            }
                        }
                        break;
                    }
                }
            }
            return result;
        },
        // sometimes * and common fields both exist, remove common fields when * exists
        removeRedundant(list) {
            let result = list;
            let asterItems = result.filter(ele => ele.columnName === '*');
            if (asterItems.length === 0)
                return result;
            for (let item of asterItems) {
                result = [...result.filter(ele => ele.tableName !== item.tableName || ele.operationId !== item.operationId), item];
            }
            return result;
        }
    },
    watch: {
        initial(newVal) {
            let temp = this.inTakeInitialList(this.removeRedundant(newVal));
            tool.tagUniqueId(temp);
            tool.initializeCheckStatus(temp);
            this.processedInitialList = temp;
        }
    },
    components: {
        CCS
    }
}
</script>

<style lang="scss" scoped>
</style>