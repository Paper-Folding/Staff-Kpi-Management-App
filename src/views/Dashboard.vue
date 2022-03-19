<template>
    <SideMenu />
    <router-view />
</template>

<script>
import SideMenu from "../components/Transaction/SideMenu.vue";
export default {
    components: {
        SideMenu
    }
}
</script>
