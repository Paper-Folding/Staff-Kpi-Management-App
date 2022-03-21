<template>
    <div class="wrapper" ref="loading">
        <div v-if="this.status === state.LOADING" class="spinner-border" role="status">
            <span class="visually-hidden">We are loading....</span>
        </div>
        <div v-else-if="this.status === state.NOTHING_LOADED">
            <span class="nothing">
                <slot name="nothingLoaded"></slot>
            </span>
        </div>
        <div v-else>
            <span class="nothing">
                <slot name="nothingFound"></slot>
            </span>
        </div>
    </div>
</template>

<script>
import state from "./Constants.js";

export default {
    props: {
        size: String,
        status: {
            type: Number,
            default: () => state.LOADING
        }
    },
    created() {
        this.state = state;
    },
    mounted() {
        this.$refs.loading.style.setProperty('--loading-size', this.size || null);
    }
}
</script>

<style lang="scss" scoped>
.wrapper {
    text-align: center;

    .spinner-border {
        margin: 0 auto;
        width: var(--loading-size, 2.5em);
        height: var(--loading-size, 2.5em);
    }

    .nothing {
        font-size: 1.25em;
        font-weight: bold;
        letter-spacing: 0.03em;
    }
}
</style>