<!-- 用户与权限 - 角色管理 role/role-scope -->
<template>
    <paper-table
        :status="currentStatus"
        key-column="id"
        v-model="table.rows"
        :header="table.header"
        @row-click="seeDetail"
    ></paper-table>
    <pagination v-model="curPage" v-bind="{ total, per, showAmount: 10 }"></pagination>
    <paper-modal ref="modal" size="xl">
        <template #title>{{ modalTitle }}</template>
        <template #body>
            <power-editor ref="powerEditor" :initial="powerEditor.initial" :disabled="powerEditor.disabled"></power-editor>
        </template>
        <template #footer><button @click="collect">collect</button></template>
    </paper-modal>
</template>

<script>
import { mapActions, mapGetters } from 'vuex'
import PaperTable from "../../components/PaperTable/PaperTable.vue";
import state from '../../components/PaperTable/Constants';
import Pagination from '../../components/Pagination.vue';
import PaperModal from '../../components/PaperModal.vue';
import PowerEditor from '../../components/Transaction/PowerEditor.vue';
export default {
    data() {
        return {
            table: { header: this.$store.state.Role.fieldsMapper, rows: [] },
            currentStatus: state.LOADING,
            curPage: 1,
            per: 10,
            powerEditor: {
                disabled: false,
                initial: []
            },
            modalTitle: ''
        }
    },
    async mounted() {
        await this.requestList({ page: this.curPage, amount: this.per });
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
            await this.requestList({ page: turnTo, amount: this.per });
            this.table.rows = this.$store.state.Role.rows;
            this.currentStatus = state.NORMAL;
        }
    },
    methods: {
        ...mapActions({ requestList: "Role/requestList", requestRole: "Role/requestRole" }),
        async seeDetail(row) {
            await this.requestRole({ id: row.id });
            this.powerEditor.initial = this.$store.state.Role.singleRole?.roleScopes;
            this.modalTitle = row.name + ' 角色作用域';
            this.$refs.modal.open();
        },
        collect(){
            console.log(this.$refs.powerEditor.collect());
        }
    },
    components: {
        PaperTable,
        Pagination,
        PaperModal,
        PowerEditor
    }
}
</script>

<style lang="scss" scoped>
</style>