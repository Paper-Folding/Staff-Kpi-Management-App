<!-- 用户与权限 - 用户信息 staff_info -->
<template>
    <div class="mt-3 mb-2 top">
        <div class="d-flex gap-3">
            <outline-button color="blue" @click="downloadTemplate">下载导入模板</outline-button>
            <excel-importer v-model="importingTable" @confirm-import="importIt"></excel-importer>
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
</template>

<script>
import { mapActions, mapGetters } from 'vuex';
import ExcelImporter from '../../components/Transaction/ExcelImporter.vue';
import excelHelper from '../../components/Excel/ExcelHelper.js';
import OutlineButton from '../../components/OutlineButton.vue';
import SearchInput from '../../components/SearchInput.vue';
import PaperTable from "../../components/PaperTable/PaperTable.vue";
import state from "../../components/PaperTable/Constants";
import Pagination from "../../components/Pagination.vue";
import { debounce } from "lodash";
import Auth from '../../utils/Auth';
export default {
    data() {
        return {
            table: { header: {}, rows: [] },
            currentStatus: state.LOADING,
            curPage: 1,
            per: 1,
            query: "",
            importingTable: {
                header: [],
                rows: []
            }
        }
    },
    async mounted() {
        await this.requestList({ page: this.curPage, amount: this.per });
        this.table.header = this.$store.state.User.table.header;
        this.table.rows = this.$store.state.User.table.rows;
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
            this.table.rows = this.$store.state.User.table.rows;
            this.currentStatus = state.NORMAL;
        },
        query: debounce(async function (newVal) {
            this.currentStatus = state.LOADING;
            await this.requestList({ page: this.curPage, amount: this.per, query: newVal });
            this.table.header = this.$store.state.User.table.header;
            this.table.rows = this.$store.state.User.table.rows;
            if (this.table.rows.length === 0)
                this.currentStatus = state.NOTHING_FOUND;
            else
                this.currentStatus = state.NORMAL;
        }, 800)
    },
    methods: {
        ...mapActions({ requestList: "User/requestStaffList", requestImport: "User/requestImport" }),
        downloadTemplate() {
            const template = [excelHelper.formatTableJsonToXlsxJson(this.$store.state.User.importTemplate)[1]];
            excelHelper.saveBlobAs(excelHelper.convertWorkbookToBlob(excelHelper.createNewWorkbook({ Author: Auth.getLoggedUser().realName }, template)), "template-staff-info.xlsx");
        },
        importIt() {
            console.log(this.importingTable);
            debugger
        }
    },
    components: {
        ExcelImporter,
        OutlineButton,
        SearchInput,
        PaperTable,
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