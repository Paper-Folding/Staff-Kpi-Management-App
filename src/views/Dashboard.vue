<template>
    <Navbar />
    <SideMenu />
    <div class="stage">
        <router-view />
    </div>
</template>

<script>
import SideMenu from "../components/Transaction/SideMenu.vue";
import Navbar from "../components/Transaction/Navbar.vue";
export default {
    mounted() {
        document.body.classList.add('dashboard');
    },
    unmounted() {
        document.body.classList.remove('dashboard');
    },
    components: {
        SideMenu,
        Navbar

    }
}
</script>

<style lang="scss" scoped>
.stage {
    margin: 0 3.5rem;
    @media (max-width: 1168px) {
        margin: 0 2rem;
    }
    @media (max-width: 768px) {
        margin: 0 0.5rem;
    }
}
</style>
