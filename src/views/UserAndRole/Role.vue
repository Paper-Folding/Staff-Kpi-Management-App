<!-- 用户与权限 - 角色管理 role/role-scope -->
<template>
    <paper-table :status="currentStatus" key-column="id" v-model="table.rows" :header="table.header" @row-click="onRowClicked"></paper-table>
    <pagination v-model="curPage" v-bind="{ total, per, showAmount: 10 }"></pagination>
</template>

<script>
import { mapActions, mapGetters } from 'vuex'
import PaperTable from "../../components/PaperTable/PaperTable.vue";
import state from '../../components/PaperTable/Constants';
import Pagination from '../../components/Pagination.vue';
export default {
    data() {
        return {
            table: { header: this.$store.state.Role.fieldsMapper, rows: [] },
            currentStatus: state.LOADING,
            curPage: 1,
            per: 1
        }
    },
    async mounted() {
        await this.request({ page: this.curPage, amount: this.per });
        this.table.rows = this.$store.state.Role.rows;
        if (this.table.rows.length === 0)
            this.currentStatus = state.NOTHING_LOADED;
        else
            this.currentStatus = state.NORMAL;
    },
    computed: {
        ...mapGetters({ total: "Role/total" })
    },
    watch: {
        async curPage(turnTo) {
            this.currentStatus = state.LOADING;
            await this.request({ page: turnTo, amount: this.per });
            this.table.rows = this.$store.state.Role.rows;
            this.currentStatus = state.NORMAL;
        }
    },
    methods: {
        ...mapActions({ request: "Role/requestList" }),
        onRowClicked() {

        }
    },
    components: {
        PaperTable,
        Pagination
    }
}
</script>

<style lang="scss" scoped>
</style>