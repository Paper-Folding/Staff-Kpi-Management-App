<!-- 用户与权限 - 用户信息 staff_info -->
<template>
    <div class="mt-3 mb-2 top">
        <div class="d-flex gap-3">
            <outline-button color="black" icon="arrow-down-circle" @click="downloadTemplate">下载导入模板</outline-button>
            <excel-importer v-model="importingTable" @confirm-import="importIt"></excel-importer>
            <outline-button color="black" icon="arrow-down-circle" @click="exportIt">导出</outline-button>
        </div>
        <search-input v-model="query" placeholder="键入以搜索"></search-input>
    </div>
    <paper-table
        :status="currentStatus"
        key-column="id"
        v-model="table.rows"
        :header="table.header"
    ></paper-table>
    <pagination v-model="curPage" v-bind="{ total, per, showAmount: 10 }"></pagination>
    <paper-modal ref="editModal" size="lg">
        <template #title>{{ roleEditor.title }}</template>
        <template #body>
            <vue-select
                v-model="roleEditor.userRoleList"
                :options="roleEditor.roleList"
                track-by="id"
                :multiple="true"
                :close-on-select="false"
                :clear-on-select="false"
                :preserve-search="true"
                label="name"
                :allow-empty="false"
                :custom-label="roleSelectorCustomLabel"
                select-label="按下回车以选中"
                deselect-label="当前"
                selected-label="当前"
                placeholder="可在此键入搜索"
            ></vue-select>
        </template>
        <template #footer>
            <outline-button color="blue" data-bs-dismiss="modal" icon="none">关闭</outline-button>
            <outline-button @click="attachIt" color="red" icon="check-lg">确认编辑</outline-button>
        </template>
    </paper-modal>
</template>

<script>
import { mapActions, mapGetters } from 'vuex';
import ExcelImporter from '../../components/Transaction/ExcelImporter.vue';
import excelHelper from '../../components/Excel/ExcelHelper.js';
import OutlineButton from '../../components/OutlineButton.vue';
import SearchInput from '../../components/SearchInput.vue';
import PaperTable from "../../components/PaperTable/PaperTable.vue";
import PaperModal from '../../components/PaperModal.vue';
import state from "../../components/PaperTable/Constants";
import Pagination from "../../components/Pagination.vue";
import VueSelect from "vue-multiselect";
import { debounce } from "lodash";
import Auth from '../../utils/Auth';
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
            roleEditor: {
                title: '',
                userId: -1,
                roleList: [],
                userRoleList: []
            }
        }
    },
    async mounted() {
        await this.requestList({ page: this.curPage, amount: this.per });
        this.table.header = this.$store.state.User.table.header;
        this.table.rows = this.sanitizeRows(this.$store.state.User.table.rows);
        if (this.table.rows.length === 0)
            this.currentStatus = state.NOTHING_LOADED;
        else
            this.currentStatus = state.NORMAL;
    },
    computed: {
        ...mapGetters({ total: "User/getTotal" })
    },
    watch: {
        async curPage(turnTo) {
            this.currentStatus = state.LOADING;
            await this.requestList({ page: turnTo, amount: this.per, query: this.query });
            this.table.header = this.$store.state.User.table.header;
            this.table.rows = this.sanitizeRows(this.$store.state.User.table.rows);
            this.currentStatus = state.NORMAL;
        },
        query: debounce(async function (newVal) {
            this.currentStatus = state.LOADING;
            await this.requestList({ page: this.curPage, amount: this.per, query: newVal });
            this.table.header = this.$store.state.User.table.header;
            this.table.rows = this.sanitizeRows(this.$store.state.User.table.rows);
            if (this.table.rows.length === 0)
                this.currentStatus = state.NOTHING_FOUND;
            else
                this.currentStatus = state.NORMAL;
        }, 800)
    },
    methods: {
        ...mapActions({ requestList: "User/requestStaffList", requestImport: "User/requestImport", requestDelete: "User/requestDelete", requestExport: "User/requestExport", attachRole: "User/attachRole", requestRoleList: "User/requestRoleList", requestUserRoleList: "User/requestUserRoleList" }),
        downloadTemplate() {
            const template = [excelHelper.formatTableJsonToXlsxJson(this.$store.state.User.importTemplate)[1]];
            excelHelper.saveBlobAs(excelHelper.convertWorkbookToBlob(excelHelper.createNewWorkbook({ Author: Auth.getLoggedUser().realName }, template)), "template-staff-info.xlsx");
        },
        roleSelectorCustomLabel({ name, description }) {
            return `${name} - ${description}`;
        },
        async importIt() {
            this.currentStatus = state.LOADING;
            await this.requestImport({
                list: excelHelper.replaceKeyForTableJson(this.importingTable.rows, this.$store.state.User.importTemplate)
            });
            await this.requestList({ page: this.curPage, amount: this.per, query: this.query });
            this.table.header = this.$store.state.User.table.header;
            this.table.rows = this.sanitizeRows(this.$store.state.User.table.rows);
            if (this.table.rows.length === 0)
                this.currentStatus = state.NOTHING_FOUND;
            else
                this.currentStatus = state.NORMAL;
        },
        async callDelete(row) {
            if (confirm(`确实要删除${(row.name || ('id为' + row.id + '的'))}用户吗？注意，此操作不可逆！`)) {
                this.currentStatus = state.LOADING;
                await this.requestDelete({
                    id: row.id
                });
                await this.requestList({ page: this.curPage, amount: this.per, query: this.query });
                this.table.header = this.$store.state.User.table.header;
                this.table.rows = this.sanitizeRows(this.$store.state.User.table.rows);
                if (this.table.rows.length === 0)
                    this.currentStatus = state.NOTHING_FOUND;
                else
                    this.currentStatus = state.NORMAL;
            }
        },
        async exportIt() {
            await this.requestExport();
            if (!this.$store.state.User.responseStatus)
                return;
            let exportData = excelHelper.formatTableJsonToXlsxJson(this.$store.state.User.exportsData.rows);
            exportData[0] = this.$store.state.User.exportsData.header;
            for (let i = 0; i < this.$store.state.User.exportsData.header.length; i++) {
                exportData[0][i] = this.$store.state.User.importTemplate[exportData[0][i]];
            }
            excelHelper.saveBlobAs(excelHelper.convertWorkbookToBlob(excelHelper.createNewWorkbook({ Author: Auth.getLoggedUser().realName }, exportData)), "staff-info-exported.xlsx");
        },
        sanitizeRows(requestedRows) {
            let result = [];
            for (let row of requestedRows) {
                result.push({
                    ...row,
                    edit: { type: 'btn', title: '编辑用户的角色', icon: 'bi-pencil-square', color: 'var(--bs-primary)', click: this.callAttach },
                    delete: { type: 'btn', title: '移除用户', icon: 'bi-trash', color: 'var(--bs-danger)', click: this.callDelete }
                })
            }
            return result;
        },
        async callAttach(row) {
            this.roleEditor.title = '正在编辑' + (row.name || ('id为' + row.id + '的用户')) + '的角色';
            this.roleEditor.userId = row.id;
            await this.requestUserRoleList({ id: this.roleEditor.userId });
            await this.requestRoleList();
            this.roleEditor.roleList = this.$store.state.User.roleList;
            this.roleEditor.userRoleList = this.$store.state.User.userRoleList;
            this.$refs.editModal.open();
        },
        async attachIt() {
            await this.attachRole({
                id: this.roleEditor.userId,
                roles: this.roleEditor.userRoleList
            });
            this.$refs.editModal.close();
        }
    },
    components: {
        ExcelImporter,
        OutlineButton,
        SearchInput,
        PaperTable,
        PaperModal,
        VueSelect,
        Pagination
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

::v-deep() {
    .paper-table {
        display: block;
        overflow-x: auto;
        white-space: nowrap;
    }
}
</style>