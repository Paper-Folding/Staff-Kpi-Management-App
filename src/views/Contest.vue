<!-- 竞赛管理 -->
<template>
    <div class="mt-3 mb-2 top">
        <div class="d-flex gap-3">
            <outline-button color="blue" @click="downloadTemplate">下载导入模板</outline-button>
            <excel-importer v-model="importingTable" @confirm-import="importIt"></excel-importer>
            <outline-button color="green" @click="exportIt">导出</outline-button>
        </div>
        <search-input v-model="query" placeholder="键入以搜索"></search-input>
    </div>
    <paper-table
        :status="currentStatus"
        key-column="id"
        v-model="table.rows"
        :header="table.header"
        @row-click="seeDetail"
    ></paper-table>
    <pagination v-model="curPage" v-bind="{ total, per, showAmount: 10 }"></pagination>
    <paper-modal ref="modal" size="lg">
        <template #title>{{ modal.title }}</template>
        <template #body>
            <reversed-table leftWidth="25%">
                <RTRow :row="{ left: '编号' }">
                    <label-input
                        v-model="modal.row.no"
                        @on-confirm="requestUpdate({ no: modal.row.no })"
                        disabled
                    ></label-input>
                </RTRow>
                <RTRow :row="{ left: '类型' }">
                    <label-select
                        :disabled="modal.disabled"
                        v-model="modal.row.type"
                        :list="[{ text: '学生竞赛', value: '学生竞赛' }, { text: '教师获奖', value: '教师获奖' }]"
                        @on-confirm="requestUpdate({ type: modal.row.type })"
                    ></label-select>
                </RTRow>
                <RTRow :row="{ left: '名称' }">
                    <label-input
                        v-model="modal.row.name"
                        @on-confirm="requestUpdate({ name: modal.row.name })"
                        :disabled="modal.disabled"
                    ></label-input>
                </RTRow>
                <RTRow :row="{ left: '参赛学生' }">
                    <paper-table
                        :header="Maid.keys(modal.row.students)"
                        :modelValue="modal.row.students"
                        :status="0"
                    ></paper-table>
                    <excel-importer
                        v-model="modal.importingStudents"
                        @confirm-import="modal.row.students = modal.importingStudents.rows"
                    ></excel-importer>
                </RTRow>
            </reversed-table>
        </template>
        <template #footer>
            <outline-button color="blue" data-bs-dismiss="modal" icon="none">关闭</outline-button>
            <outline-button
                v-if="modal.mode === 'edit'"
                @click="attachIt"
                color="red"
                icon="check-lg"
            >完成修改</outline-button>
        </template>
    </paper-modal>
</template>

<script>
import { mapActions, mapGetters } from 'vuex';
import ExcelImporter from '../components/Transaction/ExcelImporter.vue';
import excelHelper from '../components/Excel/ExcelHelper.js';
import OutlineButton from '../components/OutlineButton.vue';
import SearchInput from '../components/SearchInput.vue';
import PaperTable from "../components/PaperTable/PaperTable.vue";
import PaperModal from '../components/PaperModal.vue';
import state from "../components/PaperTable/Constants";

import ReversedTable from '../components/reversed-table/DataTable.vue';
import RTRow from "../components/reversed-table/Row.vue";
import LabelInput from '../components/LabelInput.vue';
import LabelSelect from '../components/LabelSelect.vue';
import LabelDatePicker from '../components/LabelDatePicker.vue';

import Pagination from "../components/Pagination.vue";
import Maid from '../utils/Maid';
import { debounce } from "lodash";
import Auth from '../utils/Auth';
export default {
    data() {
        return {
            table: { header: {}, rows: [] },
            currentStatus: state.LOADING,
            curPage: 1,
            per: 10,
            query: "",
            importingTable: {
                header: [],
                rows: []
            },
            modal: {
                title: '',
                mode: '',
                disabled: false,
                row: this.$store.state.Contest.contestTemplate,
                importingStudents: { header: [], rows: [] }, // students list to import
            }
        }
    },
    created() {
        this.Maid = Maid;
    },
    async mounted() {
        await this.requestList({ page: this.curPage, amount: this.per });
        this.table.header = this.$store.state.Contest.table.header;
        this.table.rows = this.sanitizeRows(this.$store.state.Contest.table.rows);
        if (this.table.rows.length === 0)
            this.currentStatus = state.NOTHING_LOADED;
        else
            this.currentStatus = state.NORMAL;
    },
    computed: {
        ...mapGetters({ total: "Contest/getTotal" })
    },
    watch: {
        async curPage(turnTo) {
            this.currentStatus = state.LOADING;
            await this.requestList({ page: turnTo, amount: this.per, query: this.query });
            this.table.header = this.$store.state.Contest.table.header;
            this.table.rows = this.sanitizeRows(this.$store.state.Contest.table.rows);
            this.currentStatus = state.NORMAL;
        },
        query: debounce(async function (newVal) {
            this.currentStatus = state.LOADING;
            await this.requestList({ page: this.curPage, amount: this.per, query: newVal });
            this.table.header = this.$store.state.Contest.table.header;
            this.table.rows = this.sanitizeRows(this.$store.state.Contest.table.rows);
            if (this.table.rows.length === 0)
                this.currentStatus = state.NOTHING_FOUND;
            else
                this.currentStatus = state.NORMAL;
        }, 800)
    },
    methods: {
        ...mapActions({ requestList: "Contest/requestList", requestOne: "Contest/requestOne" }),
        downloadTemplate() {
            const template = [excelHelper.formatTableJsonToXlsxJson(this.$store.state.Contest.importTemplate)[1]];
            excelHelper.saveBlobAs(excelHelper.convertWorkbookToBlob(excelHelper.createNewWorkbook({ Author: Auth.getLoggedUser().realName }, template)), "template-contest.xlsx");
        },
        async seeDetail({ id }) {
            await this.requestOne({ id });
            this.modal.title = '查看详情';
            this.modal.disabled = true;
            this.modal.row = this.$store.state.Contest.contest;
            this.$refs.modal.open();
        },
        sanitizeRows(requestedRows) {
            let result = [];
            for (let row of requestedRows) {
                result.push({
                    ...row,
                    edit: { type: 'btn', title: '编辑', icon: 'bi-pencil-square', color: 'var(--bs-primary)', click: this.callAttach },
                    delete: { type: 'btn', title: '删除', icon: 'bi-trash', color: 'var(--bs-danger)', click: this.callDelete }
                });
            }
            return result;
        },
    },
    components: {
        ExcelImporter,
        OutlineButton,
        SearchInput,
        PaperTable,
        PaperModal,
        Pagination,
        ReversedTable,
        RTRow,
        LabelInput,
        LabelSelect,
        LabelDatePicker
    }
}
</script>

<style lang="scss" scoped>
.top {
    display: flex;
    justify-content: space-between;

    @media (max-width: 768px) {
        flex-direction: column;
        gap: 0.75rem;

        .search {
            margin-left: 1rem;
        }
    }
}
</style>