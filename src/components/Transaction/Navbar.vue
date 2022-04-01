<template>
    <nav class="d-flex align-items-center bg-light">
        <div class="container-fluid d-flex justify-content-between">
            <div class="left-one d-flex align-items-center">
                <span>
                    <span class="fw-bold">{{ username }}</span>，&nbsp;您当前的角色为
                    <span class="fw-bold">{{ roleDisplay }}</span>.
                    <a
                        href="javascript:void(0)"
                        class="link-secondary text-decoration-none"
                        @click="openUpRoleModal"
                    >
                        &nbsp;&nbsp;&nbsp;
                        <i class="bi-arrow-left-right"></i>切换角色
                    </a>
                </span>
            </div>
            <div class="right-one">
                <VMenu
                    strategy="fixed"
                    :triggers="['focus']"
                    :showTriggers="triggers => [...triggers, 'hover', 'click']"
                    :hideTriggers="triggers => [...triggers, 'click']"
                    :skidding="-40"
                >
                    <img :src="imgSrc" class="avatar" />
                    <template #popper>
                        <ul class="dropdown-menu show position-relative">
                            <li>
                                <router-link class="dropdown-item" to="/me">
                                    <i class="bi-person"></i>&nbsp;&nbsp;我的
                                </router-link>
                            </li>
                            <li>
                                <a
                                    class="dropdown-item"
                                    href="javascript:void(0)"
                                    @click="openUpRoleModal"
                                >
                                    <i class="bi-arrow-left-right"></i>&nbsp;&nbsp;切换角色
                                </a>
                            </li>
                            <li>
                                <router-link class="dropdown-item" to="/" @click="logoff">
                                    <i class="bi-door-open"></i>&nbsp;&nbsp;登出
                                </router-link>
                            </li>
                        </ul>
                    </template>
                </VMenu>
            </div>
        </div>
    </nav>
    <paper-modal
        size="md"
        ref="modal"
        @modal-cancelled="$refs.modal.close()"
        @modal-confirmed="changeRole"
    >
        <template #title>切换您的角色</template>
        <template #body>
            <vueSelect
                v-model="role"
                :options="roleList"
                track-by="id"
                label="name"
                :allow-empty="false"
                :custom-label="roleSelectorCustomLabel"
                select-label="按下回车以选中"
                deselect-label="当前"
                selected-label="当前"
                placeholder="可在此键入搜索"
            ></vueSelect>
        </template>
    </paper-modal>
</template>

<script>
import imgSrc from "../../assets/images/default-avatar.jpg";
import { Menu as VMenu } from 'floating-vue';
import "floating-vue/dist/style.css";
import "vue-multiselect/dist/vue-multiselect.css";
import { mapActions } from 'vuex';
import paperModal from '../PaperModal.vue';
import vueSelect from "vue-multiselect";
import Auth from '../../utils/Auth';
export default {
    data() {
        return {
            roleDisplay: localStorage.getItem('role'), // role in navbar
            username: Auth.getLoggedUser().realName,
            role: null, // role in selector
            roleList: [],
        }
    },
    created() {
        this.imgSrc = imgSrc;
    },
    methods: {
        ...mapActions({ logoff: "Login/logoff", requestRole: "Navbar/requestUserRoleDetails" }),
        async openUpRoleModal() {
            await this.requestUserRoles();
            this.$refs.modal.open();
        },
        roleSelectorCustomLabel({ name, description }) {
            return `${name} - ${description}`;
        },
        async requestUserRoles() {
            await this.requestRole();
            this.roleList = this.$store.state.Navbar.roleList;
            this.role = this.$store.state.Navbar.role;
        },
        changeRole() {
            if (this.role == null || this.role.name === this.$store.state.Navbar.role.name) {
                this.$store.state.notify("注意，角色并未切换!", 'warning');
            } else {
                localStorage.setItem('role', this.role.name);
                this.roleDisplay = this.role.name;
                this.$store.state.notify(`角色已切换至${this.role.name}`, 'success');
                this.$router.push("/dashboard");
            }
            this.$refs.modal.close();
        }
    },
    components: {
        VMenu,
        paperModal,
        vueSelect
    }
}
</script>

<style lang="scss" scoped>
nav {
    width: 100vw;
    height: 3.5rem;
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    z-index: 520;
    box-shadow: 3px 0px 10px #c5c5c5;
    border-bottom: 1px #dfdfdf solid;

    .left-one {
        margin-left: 2rem;
    }

    .right-one {
        margin-right: 2rem;
        .avatar {
            cursor: pointer;
            border-radius: 100%;
            border: 1px #cacaca solid;
            width: 2.75rem;
            height: 2.75rem;
        }
    }
}
ul.dropdown-menu {
    border: 0;
}
</style>