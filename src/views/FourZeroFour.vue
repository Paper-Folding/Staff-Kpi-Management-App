<template>
    <div class="wrapper">
        <img src="../assets/images/sad Ling.png" />
        <h3>Where is my page?</h3>
    </div>
</template>

<script>
export default {
    mounted() {
        document.body.classList.add('err');
    },
    unmounted() {
        document.body.classList.remove('err');
    }
}
</script>

<style lang="scss" scoped>
.wrapper {
    min-height: 100vh;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;

    img {
        width: 75vmin;
        height: 75vmin;
    }
}
</style>