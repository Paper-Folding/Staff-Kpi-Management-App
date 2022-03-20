<!-- 用户与权限 - 角色管理 role/role-scope -->
<template>
    <paper-table :status="currentStatus" key-column="id" v-model="table.rows" :header="table.header" @row-click="onRowClicked"></paper-table>
</template>

<script>
import PaperTable from "../../components/PaperTable/PaperTable.vue"
export default {
    data() {
        return {
            table: { header: this.$store.state.Role.fieldsMapper, rows: [] }
        }
    },
    components: {
        PaperTable
    }
}
</script>

<style lang="scss" scoped>
</style>