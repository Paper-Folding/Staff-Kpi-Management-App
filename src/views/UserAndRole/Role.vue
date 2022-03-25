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
                    v-model="modalEditor.data.name"
                    :disabled="modalEditor.disabled"
                    placeholder="角色名称"
                />
            </div>
            <div class="mb-3">
                <span class="form-label">角色描述</span>
                <textarea
                    placeholder="角色描述"
                    class="form-control"
                    v-model="modalEditor.data.description"
                    :disabled="modalEditor.disabled"
                    rows="3"
                ></textarea>
            </div>
            <div class="mb-3">
                <span class="form-label">失效时间</span>
                <div class="date-scope">
                    <date-picker
                        v-model="modalEditor.data.expirationDay"
                        :disabled="modalEditor.disabled"
                        class="date-day"
                        type="day"
                    ></date-picker>
                    <date-picker
                        v-model="modalEditor.data.expirationTime"
                        :disabled="modalEditor.disabled"
                        class="date-time"
                        type="time"
                    ></date-picker>
                </div>
            </div>
            <div class="mb-3" v-if="modalEditor.mode === 'see'">
                <span class="form-label">创建者用户名</span>
                <input
                    type="text"
                    class="form-control"
                    disabled
                    v-model="modalEditor.data.creator"
                    placeholder="创建者"
                />
            </div>
            <span class="form-label">权限编辑</span>
            <power-editor
                ref="powerEditor"
                :initial="modalEditor.data.roleScopes"
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
import DatePicker from '../../components/DatePicker.vue';
import Maid from '../../utils/Maid.js';
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
                data: {
                    id: -1,
                    name: '',
                    description: '',
                    expirationDay: null,
                    expirationTime: null,
                    creator: '',
                    roleScopes: []
                }
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
            await this.requestRole({ id: row.id });
            this.modalEditor = {
                mode: 'see',
                disabled: true,
                data: {
                    id: row.id,
                    name: row.name,
                    description: row.description,
                    expirationDay: new Date(row.expiration),
                    expirationTime: new Date(row.expiration),
                    creator: row.creatorName,
                    roleScopes: this.$store.state.Role.singleRole?.roleScopes
                }
            }
            this.modalTitle = row.name + ' 角色作用域';
            this.$refs.modal.open();
        },
        async callEdit(row) {
            await this.requestRole({ id: row.id });
            this.modalEditor = {
                mode: 'edit',
                disabled: false,
                data: {
                    id: row.id,
                    name: row.name,
                    description: row.description,
                    expirationDay: new Date(row.expiration),
                    expirationTime: new Date(row.expiration),
                    creator: row.creatorName,
                    roleScopes: this.$store.state.Role.singleRole?.roleScopes
                }
            }
            this.modalTitle = '编辑 ' + row.name + ' 角色';
            this.$refs.modal.open();
        },
        async callDelete(row) {
            if (confirm(`确实要删除${row.name}角色吗？与此角色相关联的用户也会因此直接失去此角色！`)) {
                await this.deleteRole({ id: row.id, name: row.name, creatorName: row.creatorName });
                await this.requestList({ page: this.curPage, amount: this.per });
                this.table.rows = this.sanitizeRows(this.$store.state.Role.rows);
            }
        },
        async editIt() {
            await this.updateRole({
                id: this.modalEditor.data.id,
                name: this.modalEditor.data.name,
                description: this.modalEditor.data.description,
                expiration: Maid.formatDate(this.modalEditor.data.expirationDay, 'YYYY-mm-dd') + ' ' + Maid.formatDate(this.modalEditor.data.expirationTime, 'HH:MM'),
                roleScopes: this.$refs.powerEditor.collect(),
                creatorName: this.modalEditor.data.creator
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
            let now = new Date();
            for (let row of requestedRows) {
                result.push({
                    ...row,
                    isExpired: Maid.compareTime(row.expiration, now) < 0 ? { type: 'i', icon: 'bi-x-lg' } : { type: 'i', icon: 'bi-check-lg' },
                    edit: { type: 'btn', title: '编辑角色', icon: 'bi-pencil-square', color: 'var(--bs-primary)', click: this.callEdit },
                    delete: { type: 'btn', title: '移除角色', icon: 'bi-trash', color: 'var(--bs-danger)', click: this.callDelete }
                });
            }
            return result;
        }
    },
    components: {
        PaperTable,
        Pagination,
        PaperModal,
        PowerEditor,
        OutlineButton,
        DatePicker
    }
}
</script>

<style lang="scss" scoped>
.form-label {
    user-select: none;
    font-weight: bold;
}

.date-scope {
    display: flex;
    justify-content: flex-start;
}

::v-deep() {
    .date-day {
        width: 9em;
    }

    .date-time {
        width: 5.5em;
    }
}
</style>