<template>
    <SideMenu />
    <div class="stage">
        <router-view />
    </div>
</template>

<script>
import SideMenu from "../components/Transaction/SideMenu.vue";
export default {
    components: {
        SideMenu
    }
}
</script>

<style lang="scss" scoped>
.stage {
    margin: 0 3.5rem;
}
</style>
