<template>
    <button :class="color" :disabled="disabled" @click="$emit('click', $event)">
        <i v-if="icon !== 'none'" :class="`bi-${icon}`">&nbsp;</i>
        <span>
            <slot></slot>
        </span>
    </button>
</template>

<script>
export default {
    props: {
        color: {
            type: String,
            default: "red",
        },
        icon: {
            type: String,
            default: "plus-circle",
        },
        disabled: {
            type: Boolean,
            default: false,
        },
    },
    data() {
        return {
            btnClass: null
        }
    },
    emits: ["click"],
};
</script>

<style lang="scss" scoped>
$red: var(--bs-red);
$blue: var(--bs-blue);
$orange: var(--bs-orange);
$green: var(--bs-green);

button {
    --color: 0, 100%; /*the base color*/
    --l: 50%; /*the initial lightness*/
    display: inline-flex;
    justify-content: center;
    align-items: center;
    padding: 0 1rem;
    border: 3px black solid;
    border-radius: 10em;
    font-size: 1.25rem;
    cursor: pointer;
    background-color: #fff;
    transition: color 0.15s ease-in-out, background-color 0.15s ease-in-out,
        border-color 0.15s ease-in-out, box-shadow 0.15s ease-in-out;

    > * {
        user-select: none;
    }

    > span {
        letter-spacing: 0.03em;
        font-weight: 550;
    }

    &.red {
        color: $red;
        border-color: $red;

        &:hover {
            color: #fff;
            background-color: $red;
            border-color: $red;
        }

        &:focus {
            box-shadow: 0 0 0 0.25rem rgba(220, 53, 69, 0.5);
        }
    }

    &.blue {
        color: $blue;
        border-color: $blue;

        &:hover {
            color: #fff;
            background-color: $blue;
            border-color: $blue;
        }

        &:focus {
            box-shadow: 0 0 0 0.25rem rgba(13, 110, 253, 0.25);
        }
    }

    &.orange {
        color: $orange;
        border-color: $orange;

        &:hover {
            color: #fff;
            background-color: $orange;
            border-color: $orange;
        }

        &:focus {
            box-shadow: 0 0 0 0.25rem rgba(255, 123, 0, 0.25);
        }
    }

    &.green {
        color: $green;
        border-color: $green;

        &:hover {
            color: #fff;
            background-color: $green;
            border-color: $green;
        }

        &:focus {
            box-shadow: 0 0 0 0.25rem rgba(25, 135, 84, 0.5);
        }
    }

    &.custom {
        color: var(--outline-button-custom-color);
        border-color: var(--outline-button-custom-color);

        &:hover {
            color: #fff;
            background-color: var(--outline-button-custom-color);
            border-color: var(--outline-button-custom-color);
        }

        &:focus {
            box-shadow: 0 0 0 0.25rem var(--outline-button-custom-shadow-color);
        }
    }

    &:disabled {
        pointer-events: none;
        opacity: 0.65;
    }
}

// ::v-deep(.btn-return) {
//     $btnReturnColor: #e00;
//     --outline-button-custom-color: #{$btnReturnColor};
//     --outline-button-custom-shadow-color: #{lighten($btnReturnColor, 10%)};
// }
</style>