<template>
    <button @click="$emit('click', $event)" :class="[`skew-${direction}-${slash === '\\' ? 'backslash' : 'slash'}`, `${$attrs.disabled ? 'disabled' : ''}`]">
        <slot :class="$attrs.class" :style="$attrs.style"></slot>
    </button>
</template>

<script>
/**
 * Props:
 *  direction: which direction should the button be filled during the hover, should be passed two kinds of values only: 'LR' for left-to-right while 'RL' otherwise, default is 'LR'.
 *  slash: which style should the fill be skewed, should be passed two kinds of values only: '/' for slash type while '\\' for backslash type, default is '/'.
 *  class and style: class and style decalred on this component will apply to slot
 * Slot:
 *  default slot for button inner html
 */
export default {
    props: {
        direction: {
            type: String,
            default: "LR"
        },
        slash: {
            type: String,
            default: "/"
        },
    },
    emits: ["click"],
}
</script>

<style lang="scss" scoped>
/** user adjustable styles */
$default-bg-color: var(--skew-btn-default-bg-color, #e00);
$hover-bg-color: var(--skew-btn-hover-bg-color, #fff);
$border-radius: var(--skew-btn-border-radius, 10em);
$border: var(--skew-btn-border, 0.25rem solid $default-bg-color);
$transition: var(--skew-btn-transition, 0.3s ease-in-out);
$size: var(--skew-btn-size, 1.25em);
$padding: var(--skew-btn-padding, 0.5em 0.75em);
$skew-degree: var(--skew-btn-skew-degree, 20deg);
$skew-degree-negative: calc(
    var(--skew-btn-skew-degree, 20deg) * -1
); // get negative angle for --skew-btn-skew-degree

button {
    font-size: $size;
    background: $default-bg-color;
    color: $hover-bg-color;
    border: $border;
    padding: $padding;
    position: relative;
    z-index: 1;
    overflow: hidden;
    border-radius: $border-radius;
    transition: color $transition;

    &:hover {
        color: $default-bg-color;
    }

    &::after {
        content: "";
        background: $hover-bg-color;
        position: absolute;
        z-index: -1;
        padding: $padding;
        transition: all $transition;
        top: 0;
        bottom: 0;
    }

    &.skew-LR-slash {
        &::after {
            left: -200%;
            right: -20%;
            transform: skewX($skew-degree-negative) scale(0, 1);
        }

        &:hover::after {
            transform: skewX($skew-degree-negative) scale(1, 1);
        }
    }

    &.skew-RL-slash {
        &::after {
            left: -20%;
            right: -200%;
            transform: skewX($skew-degree-negative) scale(0, 1);
        }

        &:hover::after {
            transform: skewX($skew-degree-negative) scale(1, 1);
        }
    }

    &.skew-LR-backslash {
        &::after {
            left: -200%;
            right: -20%;
            transform: skewX($skew-degree) scale(0, 1);
        }

        &:hover::after {
            transform: skewX($skew-degree) scale(1, 1);
        }
    }

    &.skew-RL-backslash {
        &::after {
            left: -20%;
            right: -200%;
            transform: skewX($skew-degree) scale(0, 1);
        }

        &:hover::after {
            transform: skewX($skew-degree) scale(1, 1);
        }
    }

    &.disabled {
        cursor: not-allowed;
    }
}
</style>