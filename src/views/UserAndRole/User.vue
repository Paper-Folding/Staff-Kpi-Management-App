<!-- 用户与权限 - 用户信息 staff_info -->
<template>
    <paper-table
        :status="currentStatus"
        key-column="id"
        v-model="table.rows"
        :header="table.header"
    ></paper-table>
</template>

<script>
import PaperTable from "../../components/PaperTable/PaperTable.vue";
import state from "../../components/PaperTable/Constants";
import { mapActions, mapGetters } from 'vuex';
export default {
    data() {
        return {
            table: { header: {}, rows: [] },
            currentStatus: state.LOADING,
            curPage: 1,
            per: 10,
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
    methods: {
        ...mapActions({ requestList: "User/requestStaffList" })
    },
    components: {
        PaperTable
    }
}
</script>

<style lang="scss" scoped>
::v-deep() {
    .paper-table {
        display: block;
        overflow-x: auto;
        white-space: nowrap;
    }
}
</style>