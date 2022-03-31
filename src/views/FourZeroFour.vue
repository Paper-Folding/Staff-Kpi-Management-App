<template>
    <div class="wrapper">
        <img :src="imgSrc" />
        <h3>Where is my page?</h3>
    </div>
</template>

<script>
import imgSrc from "../assets/images/sad Ling.png";

export default {
    created() {
        this.imgSrc = imgSrc;
    },
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