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
            <div class="mb-3">
                <span class="form-label">角色名称</span>
                <input
                    type="text"
                    class="form-control"
                    v-model="modalEditor.name"
                    :disabled="modalEditor.disabled"
                    placeholder="角色名称"
                />
            </div>
            <div class="mb-3">
                <span class="form-label">角色描述</span>
                <textarea
                    placeholder="角色描述"
                    class="form-control"
                    v-model="modalEditor.description"
                    :disabled="modalEditor.disabled"
                    rows="3"
                ></textarea>
            </div>
            <span class="form-label">权限编辑</span>
            <power-editor
                ref="powerEditor"
                :initial="modalEditor.initial"
                :disabled="modalEditor.disabled"
            ></power-editor>
        </template>
        <template #footer>
            <outline-button color="blue" data-bs-dismiss="modal" icon="none">关闭</outline-button>
            <outline-button
                @click="editIt"
                v-if="modalEditor.mode === 'edit'"
                color="red"
                icon="none"
            >确认修改</outline-button>
        </template>
    </paper-modal>
</template>

<script>
import { mapActions, mapGetters } from 'vuex'
import PaperTable from "../../components/PaperTable/PaperTable.vue";
import state from '../../components/PaperTable/Constants';
import Pagination from '../../components/Pagination.vue';
import PaperModal from '../../components/PaperModal.vue';
import PowerEditor from '../../components/Transaction/PowerEditor.vue';
import OutlineButton from '../../components/OutlineButton.vue';
export default {
    data() {
        return {
            table: { header: this.$store.state.Role.fieldsMapper, rows: [] },
            currentStatus: state.LOADING,
            curPage: 1,
            per: 10,
            modalEditor: {
                mode: '',
                disabled: false,
                initial: [],
                id: -1,
                name: '',
                description: ''
            },
            modalTitle: ''
        }
    },
    async mounted() {
        await this.requestList({ page: this.curPage, amount: this.per });
        this.table.rows = this.sanitizeRows(this.$store.state.Role.rows);
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
            this.table.rows = this.sanitizeRows(this.$store.state.Role.rows);
            this.currentStatus = state.NORMAL;
        }
    },
    methods: {
        ...mapActions({ requestList: "Role/requestList", requestRole: "Role/requestRole", deleteRole: "Role/requestDeleteRole", addRole: "Role/requestAddRole", updateRole: "Role/requestUpdateRole" }),
        async seeDetail(row) {
            if (row.id === 1 || row.id === 2)
                return;
            await this.requestRole({ id: row.id });
            this.modalEditor = {
                mode: 'see',
                initial: this.$store.state.Role.singleRole?.roleScopes,
                disabled: true,
                id: row.id,
                name: row.name,
                description: row.description
            }
            this.modalTitle = row.name + ' 角色作用域';
            this.$refs.modal.open();
        },
        async callEdit(row) {
            if (row.id === 1 || row.id === 2) {
                this.$store.state.notify(row.name + ' 禁止更改！', 'warning');
                return;
            }
            await this.requestRole({ id: row.id });
            this.modalEditor = {
                mode: 'edit',
                initial: this.$store.state.Role.singleRole?.roleScopes,
                disabled: false,
                id: row.id,
                name: row.name,
                description: row.description
            }
            this.modalTitle = '编辑 ' + row.name + ' 角色';
            this.$refs.modal.open();
        },
        async callDelete(row) {
            if (row.id === 1 || row.id === 2) {
                this.$store.state.notify(row.name + ' 禁止删除！', 'warning');
                return;
            }
            if (confirm(`确实要删除${row.name}角色吗？与此角色相关联的用户也会直接失去此角色！`)) {
                await this.deleteRole({ id: row.id, name: row.name });
                await this.requestList({ page: this.curPage, amount: this.per });
                this.table.rows = this.sanitizeRows(this.$store.state.Role.rows);
            }
        },
        async editIt() {
            await this.updateRole({
                id: this.modalEditor.id,
                name: this.modalEditor.name,
                description: this.modalEditor.description,
                roleScopes: this.$refs.powerEditor.collect()
            });
            if (this.$store.state.Role.responseStatus) {
                this.$refs.modal.close();
                this.table.rows = [];
                this.currentStatus = state.LOADING;
                await this.requestList({ page: this.curPage, amount: this.per });
                this.table.rows = this.sanitizeRows(this.$store.state.Role.rows);
                this.currentStatus = state.NORMAL;
            }
        },
        sanitizeRows(requestedRows) {
            let result = [];
            for (let row of requestedRows) {
                result.push({ ...row, edit: { type: 'btn', title: '编辑角色', icon: 'bi-pencil-square', color: 'var(--bs-primary)', click: this.callEdit }, delete: { type: 'btn', title: '移除角色', icon: 'bi-trash', color: 'var(--bs-danger)', click: this.callDelete } });
            }
            return result;
        }
    },
    components: {
        PaperTable,
        Pagination,
        PaperModal,
        PowerEditor,
        OutlineButton
    }
}
</script>

<style lang="scss" scoped>
.form-label {
    user-select: none;
    font-weight: bold;
}
</style>