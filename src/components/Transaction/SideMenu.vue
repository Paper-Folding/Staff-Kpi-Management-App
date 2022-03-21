<template>
    <SidebarMenu v-model:collapsed="collapsed" :menu="menuContent" theme="white-theme" :showChild="showChild" @item-click="onItemClick">
        <template v-slot:toggle-icon>
            <!-- hide pre-defined icon -->
            <span></span>
        </template>
    </SidebarMenu>
</template>

<script>
import { mapActions, mapGetters } from 'vuex';
import { SidebarMenu } from "../vue-sidebar-menu";
export default {
    data() {
        return {
            showChild: true,
            collapsed: false,
        };
    },
    mounted() {
        document.body.classList.add('side-menu');
        setTimeout(() => {
            this.showChild = false;
        }, 1000); // this is no method of methods
        // make responsive
        this.menuResponsive();
        // window.addEventListener("resize", this.menuResponsive);
    },
    unmounted() {
        document.body.classList.remove("side-menu", "collapsed");
    },
    watch: {
        collapsed(changed) {
            if (changed) document.body.classList.add("collapsed");
            else document.body.classList.remove("collapsed");
        }
    },
    methods: {
        ...mapActions({ logoff: "Login/logoff" }),
        menuResponsive() {
            this.collapsed = window.innerWidth <= 960 ? true : false;
        },
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