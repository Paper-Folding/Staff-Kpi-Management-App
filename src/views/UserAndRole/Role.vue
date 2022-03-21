<!-- 用户与权限 - 角色管理 role/role-scope -->
<template>
    <paper-table :status="currentStatus" key-column="id" v-model="table.rows" :header="table.header" @row-click="onRowClicked"></paper-table>
</template>

<script>
import { mapActions, mapGetters } from 'vuex'
import PaperTable from "../../components/PaperTable/PaperTable.vue";
import state from '../../components/PaperTable/Constants';
export default {
    data() {
        return {
            table: { header: this.$store.state.Role.fieldsMapper, rows: [] },
            currentStatus: state.NORMAL
        }
    },
    async mounted() {
        await this.request();
        this.table.rows = this.$store.state.Role.rows;
    },
    methods: {
        ...mapActions({ request: "Role/requestList" }),
        onRowClicked() {

        }
    },
    components: {
        PaperTable
    }
}
</script>

<style lang="scss" scoped>
</style>