<template>
    <li class="nav-item">
        <button
            :class="['nav-link', active ? 'active' : '']"
            data-bs-toggle="tab"
            type="button"
            @click="$emit('toggle-tab')"
        >
            <slot></slot>
        </button>
    </li>
</template>

<script>
export default {
    props: {
        active: Boolean,
    },
    emits: ['toggle-tab']
};
</script>

<style lang="scss">
.nav-link {
    border-top-left-radius: 0.5rem !important;
    border-top-right-radius: 0.5rem !important;
}

.nav-link {
    border-bottom: none !important;

    &.active {
        background-color: var(--bs-blue) !important;
        color: #fff !important;
        border-color: transparent !important;
    }
}
</style>