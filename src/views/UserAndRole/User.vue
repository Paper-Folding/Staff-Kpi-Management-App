<!-- 用户与权限 - 用户信息 staff_info -->
<template>
    <paper-table :status="currentStatus" key-column="id"></paper-table>
</template>

<script>
import PaperTable from "../../components/PaperTable/PaperTable.vue";
import state from "../../components/PaperTable/Constants";
export default {
    data() {
        return {
            table: { header: [], rows: [] },
            currentStatus: state.LOADING,
            curPage: 1,
            per: 10,
        }
    },
    components: {
        PaperTable
    }
}
</script>

<style lang="scss" scoped>
</style>