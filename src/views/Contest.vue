<!-- 竞赛管理 -->
<template>
    <div class="mt-3 mb-2 top">
        <div class="d-flex gap-3">
            <outline-button color="green" @click="callAdd">手动添加</outline-button>
            <outline-button icon="download" color="blue" @click="downloadTemplate">下载导入模板</outline-button>
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
    <paper-modal ref="modal" size="lg" @onClose="refreshTable">
        <template #title>{{ modal.title }}</template>
        <template #body>
            <reversed-table leftWidth="25%">
                <RTRow :row="{ left: '编号' }">
                    <label-input
                        v-model="modal.row.no"
                        :hideButton="modal.mode === 'add'"
                        :disabled="modal.mode !== 'add'"
                    ></label-input>
                </RTRow>
                <RTRow :row="{ left: '类型' }">
                    <label-select
                        v-model="modal.row.type"
                        :list="[{ text: '学生竞赛', value: '学生竞赛' }, { text: '教师获奖', value: '教师获奖' }]"
                        :hideButton="modal.mode === 'add'"
                        :disabled="modal.mode === 'view'"
                        @on-confirm="requestUpdate({ id: modal.row.id, type: modal.row.type })"
                    ></label-select>
                </RTRow>
                <RTRow :row="{ left: '名称' }">
                    <label-input
                        v-model="modal.row.name"
                        @on-confirm="requestUpdate({ id: modal.row.id, name: modal.row.name })"
                        :hideButton="modal.mode === 'add'"
                        :disabled="modal.mode === 'view'"
                    ></label-input>
                </RTRow>
                <RTRow :row="{ left: '参赛学生' }">
                    <paper-table
                        :header="modal.stuTable.header"
                        :modelValue="modal.stuTable.rows"
                        :status="modal.studentsTableStatus"
                    ></paper-table>
                    <excel-importer
                        v-if="modal.mode !== 'view'"
                        v-model="modal.importingStudents"
                        @confirm-import="onStudentsConfirmImport"
                        text="导入学生列表"
                        style="transform:scale(0.9)"
                    ></excel-importer>
                </RTRow>
                <RTRow :row="{ left: '指导/获奖教师' }">
                    <label-select
                        v-model="modal.selectedTutor.item"
                        :list="modal.selectedTutor.list"
                        :hideButton="modal.mode === 'add'"
                        :disabled="modal.mode === 'view'"
                        @on-confirm="requestUpdate({ id: modal.row.id, tutorNo: modal.selectedTutor.item })"
                    ></label-select>
                </RTRow>
                <RTRow :row="{ left: '奖项' }">
                    <label-input
                        v-model="modal.row.prize"
                        :hideButton="modal.mode === 'add'"
                        :disabled="modal.mode === 'view'"
                        @on-confirm="requestUpdate({ id: modal.row.id, prize: modal.row.prize })"
                    ></label-input>
                </RTRow>
                <RTRow :row="{ left: '级别' }">
                    <label-select
                        v-model="modal.row.level"
                        :list="[{ text: '国家级', value: '国家级' }, { text: '省部级', value: '省部级' }, { text: '市厅级', value: '市厅级' }, { text: '局级', value: '局级' }, { text: '校级', value: '校级' }, { text: '自导自演级', value: '自导自演级' }]"
                        :hideButton="modal.mode === 'add'"
                        :disabled="modal.mode === 'view'"
                        @on-confirm="requestUpdate({ id: modal.row.id, level: modal.row.level })"
                    ></label-select>
                </RTRow>
                <RTRow :row="{ left: '得奖时间' }">
                    <label-date-picker
                        type="date"
                        v-model="modal.row.awardTime"
                        :hideButton="modal.mode === 'add'"
                        :disabled="modal.mode === 'view'"
                        @on-confirm="requestUpdate({ id: modal.row.id, 'award_time': Maid.formatDate(modal.row.awardTime, 'YYYY-MM-DD') })"
                    ></label-date-picker>
                </RTRow>
                <RTRow :row="{ left: '获奖证书' }">
                    <file-uploader
                        ref="certUploader"
                        :contestId="modal.row.id"
                        :certificate="modal.row.certificate"
                        :disabled="modal.mode === 'view'"
                        @confirmed="uploadCert"
                        :instantEmit="modal.mode === 'add'"
                        @selected="storeCertTemporarily"
                    ></file-uploader>
                </RTRow>
                <RTRow v-if="modal.mode !== 'add'" :row="{ left: '登记时间' }">
                    <label-input
                        :modelValue="Maid.formatDate(modal.row.addTime, 'YYYY-MM-DD')"
                        disabled
                    ></label-input>
                </RTRow>
                <RTRow v-if="modal.mode !== 'add'" :row="{ left: '登记人' }">
                    <label-input :modelValue="modal.row.adderName" disabled></label-input>
                </RTRow>
            </reversed-table>
        </template>
        <template #footer>
            <outline-button color="blue" data-bs-dismiss="modal" icon="none">关闭</outline-button>
            <outline-button
                color="green"
                @click="confirmAdd"
                v-if="modal.mode === 'add'"
                icon="check-lg"
            >确认添加</outline-button>
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
import state from "../components/PaperTable/Constants";
import PaperModal from '../components/PaperModal.vue';
import FileUploader from '../components/Transaction/FileUploader.vue';

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
                row: this.$store.state.Contest.contestTemplate,
                importingStudents: { header: [], rows: [] }, // students list to import
                stuTable: {
                    header: [],
                    rows: [],
                    status: state.LOADING
                },
                selectedTutor: {
                    item: '',
                    list: [],
                },
                certSrc: '',
                certFile: null
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
        ...mapActions({ requestList: "Contest/requestList", requestOne: "Contest/requestOne", requestUpdateCert: "Contest/requestUpdateCert", requestStaffList: "Contest/requestStaffList", requestUpdate: "Contest/requestUpdate", requestUploadCert: "Contest/requestUploadCert", requestAdd: "Contest/requestAdd", requestDelete: "Contest/requestDelete" }),
        downloadTemplate() {
            const template = [excelHelper.formatTableJsonToXlsxJson(this.$store.state.Contest.importTemplate)[1]];
            excelHelper.saveBlobAs(excelHelper.convertWorkbookToBlob(excelHelper.createNewWorkbook({ Author: Auth.getLoggedUser().realName }, template)), "template-contest.xlsx");
        },
        async seeDetail({ id }) {
            this.modal.stuTable.header = [];
            this.modal.stuTable.rows = [];
            this.modal.stuTable.status = state.LOADING;

            this.modal.row = this.$store.state.Contest.contestTemplate;
            await this.requestOne({ id });
            this.modal.title = '查看详情';
            this.modal.mode = 'view';
            this.modal.row = this.$store.state.Contest.contest;

            this.modal.stuTable.header = Maid.keys(this.modal.row.students);
            this.modal.stuTable.rows = this.modal.row.students;
            this.modal.stuTable.status = state.NORMAL;

            this.modal.selectedTutor.list = [{ value: this.modal.row.tutorNo, text: this.modal.row.tutorNo + ' - ' + this.modal.row.tutorName }];
            this.modal.selectedTutor.item = this.modal.row.tutorNo;
            this.modal.certSrc = import.meta.env.VITE_API_URL + '/contest/cert/' + this.modal.row.id;
            this.$refs.modal.open();
        },
        async onStudentsConfirmImport() {
            this.modal.stuTable.header = [];
            this.modal.stuTable.rows = [];
            this.modal.stuTable.status = state.LOADING;

            if (this.modal.mode === 'edit')
                await this.requestUpdate({ id: this.modal.row.id, students: JSON.stringify(this.modal.importingStudents.rows) });
            else
                await setTimeout(() => { }, 100);

            this.modal.stuTable.header = Maid.keys(this.modal.importingStudents.rows);
            this.modal.stuTable.rows = this.modal.importingStudents.rows;
            this.modal.stuTable.status = state.NORMAL;
        },
        async callEdit({ id }) {
            this.modal.stuTable.header = [];
            this.modal.stuTable.rows = [];
            this.modal.stuTable.status = state.LOADING;

            this.modal.row = this.$store.state.Contest.contestTemplate;
            await this.requestOne({ id });
            this.modal.title = '编辑竞赛';
            this.modal.mode = 'edit';
            this.modal.row = this.$store.state.Contest.contest;

            this.modal.stuTable.header = Maid.keys(this.modal.row.students);
            this.modal.stuTable.rows = this.modal.row.students;
            this.modal.stuTable.status = state.NORMAL;

            await this.requestStaffList();
            this.modal.selectedTutor.list = this.$store.state.Contest.staffList.length === 0 ? [{ value: this.modal.row.tutorNo, text: this.modal.row.tutorNo + ' - ' + this.modal.row.tutorName }] : this.$store.state.Contest.staffList.map(ele => ({ value: ele.no, text: ele.no + ' - ' + ele.name }));
            this.modal.selectedTutor.item = this.modal.row.tutorNo;
            this.modal.certSrc = import.meta.env.VITE_API_URL + '/contest/cert/' + this.modal.row.id;
            this.$refs.modal.open();
        },
        async refreshTable(_, force = false) {
            if (!force && this.modal.mode === 'view')
                return;
            this.currentStatus = state.LOADING;
            await this.requestList({ page: this.curPage, amount: this.per, query: this.query });
            this.table.header = this.$store.state.Contest.table.header;
            this.table.rows = this.sanitizeRows(this.$store.state.Contest.table.rows);
            if (this.table.rows.length === 0)
                this.currentStatus = state.NOTHING_LOADED;
            else
                this.currentStatus = state.NORMAL;
        },
        async callAdd() {
            if (!Maid.permissionMeeted('insert')) {
                Maid.notify('您没有权限手动添加竞赛');
                return;
            }
            this.modal.stuTable.header = [];
            this.modal.stuTable.rows = [];
            this.modal.stuTable.status = state.LOADING;

            this.modal.row = this.$store.state.Contest.contestTemplate;
            this.modal.title = '添加一条竞赛记录';
            this.modal.mode = 'add';

            await this.requestStaffList();
            this.modal.selectedTutor.list = this.$store.state.Contest.staffList.length === 0 ? [{ value: this.modal.row.tutorNo, text: this.modal.row.tutorNo + ' - ' + this.modal.row.tutorName }] : this.$store.state.Contest.staffList.map(ele => ({ value: ele.no, text: ele.no + ' - ' + ele.name }));
            this.modal.selectedTutor.item = '';
            this.modal.certSrc = '';
            this.$refs.modal.open();
        },
        storeCertTemporarily(cert) {
            this.modal.certFile = cert;
        },
        async confirmAdd() {
            if (this.modal.row.id && this.modal.row.id !== -1)
                return;
            let params = JSON.parse(JSON.stringify(this.modal.row));
            params.students = this.modal.stuTable.rows;
            params.tutorNo = this.modal.selectedTutor.item;
            if (this.modal.certFile == null)
                await this.requestAdd(params);
            else {
                await this.requestUploadCert({
                    cert: this.modal.certFile
                });
                if (this.$store.state.Contest.responseStatus) {
                    params.certificate = this.$store.state.Contest.uploadedCertInfo;
                    await this.requestAdd(params);
                    if (this.$store.state.Contest.responseStatus) {
                        this.$refs.modal.close();
                    }
                }
            }
        },
        async callDelete({ id, no }) {
            if (confirm(`确实要删除编号为 ${no} 的竞赛信息吗?此操作不可逆，并会删除之前上传的相关的证书文件`)) {
                await this.requestDelete({ id });
                this.refreshTable(null, true);
            }
        },
        sanitizeRows(requestedRows) {
            let result = [];
            for (let row of requestedRows) {
                result.push({
                    ...row,
                    edit: { type: 'btn', title: '编辑', icon: 'bi-pencil-square', color: 'var(--bs-primary)', click: this.callEdit },
                    delete: { type: 'btn', title: '删除', icon: 'bi-trash', color: 'var(--bs-danger)', click: this.callDelete }
                });
            }
            return result;
        },
        exportIt() {

        },
        importIt() {
        },
        uploadCert(file) {
            this.requestUpdateCert({
                cert: file,
                id: this.modal.row.id
            });
        }
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
        LabelDatePicker,
        FileUploader
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