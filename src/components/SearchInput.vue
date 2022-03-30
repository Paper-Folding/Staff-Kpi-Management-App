<template>
    <label class="search" :class="focused ? 'focused' : ''">
        <input
            :value="modelValue"
            @input="$emit('update:modelValue', $event.target.value)"
            @focus="focused = true"
            @blur="focused = false"
            :placeholder="placeholder"
            type="search"
        />
    </label>
</template>

<script>
export default {
    data() {
        return {
            focused: false
        }
    },
    props: {
        modelValue: [String, Number],
        placeholder: String,
    },
    emits: ["update:modelValue"],
};
</script>

<style lang="scss" scoped>
$whole-color: #0d6efd;
.search {
    display: inline-block;
    position: relative;
    right: 1rem;
    height: 35px;
    width: 35px;
    box-sizing: border-box;
    padding: 0 9px 0 16px;
    border: 3px solid $whole-color;
    border-radius: 25px;
    transition: all 200ms ease;
    cursor: text;

    &:after {
        content: "";
        position: absolute;
        width: 3px;
        height: 20px;
        right: -6px;
        top: 21px;
        background: $whole-color;
        border-radius: 3px;
        transform: rotate(-45deg);
        transition: all 200ms ease;
    }

    &.active,
    &:hover {
        width: 250px;
        margin-right: 0;

        &:after {
            height: 0;
        }

        .dropdown-toggle {
            display: inline;
        }
    }

    input {
        width: 100%;
        border: none;
        box-sizing: border-box;
        font-size: 1.25em;
        color: inherit;
        background: transparent;
        outline-width: 0;
        position: relative;
        bottom: 2px;
    }

    &.focused {
        width: 250px;
        margin-right: 0;

        &:after {
            height: 0;
        }

        .dropdown-toggle {
            display: inline;
        }
    }
}
</style>