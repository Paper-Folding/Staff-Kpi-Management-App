<template>
    <SidebarMenu v-model:collapsed="collapsed" :menu="menuContent" theme="white-theme" :showChild="showChild" @item-click="onItemClick">
        <template v-slot:toggle-icon>
            <!-- hide pre-defined icon -->
            <span></span>
        </template>
    </SidebarMenu>
</template>

<script>
import { mapActions, mapGetters } from 'vuex'
import { SidebarMenu } from "vue-sidebar-menu";
export default {
    data() {
        return {
            showChild: true,
            collapsed: false,
        };
    },
    mounted() {
        // this.updateMenu();
        setTimeout(() => {
            this.showChild = false;
        }, 1000); // this is no method of methods
    },
    methods: {
        ...mapActions({ logoff: "Login/logoff" }),
        onItemClick(_, item) {
            if (item.logoffBtn) {
                this.logoff();
                this.$router.push("/");
            }
        },
    },
    computed: mapGetters({ menuContent: "Menu/getMenuContent" }),
    components: {
        SidebarMenu
    }
}
</script>

<style lang="scss">
@import "vue-sidebar-menu/dist/vue-sidebar-menu.css";

.v-sidebar-menu {
    box-shadow: 3px 0px 10px #c5c5c5;

    .vsm--header {
        font-size: 1.5em;
        text-align: center;
        color: rgba(3, 3, 3, 0.7) !important;
        letter-spacing: 0.025em;
    }

    .vsm--toggle-btn {
        font-size: 1.75em;
    }

    .vsm--child_mobile,
    .vsm--mobile-bg {
        box-shadow: 3px 0px 10px #c5c5c5;
        overflow-y: hidden !important;
    }

    &.vsm_expanded .vsm--toggle-btn {
        &:after {
            content: "\f113";
            font-family: "bootstrap-icons";
        }
    }
    &.vsm_collapsed .vsm--toggle-btn {
        &:after {
            content: "\f114";
            font-family: "bootstrap-icons";
        }
    }
}
</style>