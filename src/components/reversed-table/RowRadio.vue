<template>
    <tr :style="{ 'border-bottom': borderBottom ? '#dee2e6 2px solid' : null }">
        <th>{{ left }}</th>
        <td>
            <div class="form-check" v-for="(rd, i) in right" :key="i">
                <input class="form-check-input" :type="appearance" @change="toggleRadio(i)" :checked="i === modelValue" :disabled="disabled" />
                <span :class="disabled ? 'disabled' : null" @click="toggleRadio(i)">{{ rd }}</span>
            </div>
        </td>
    </tr>
</template>

<script>
export default {
    props: {
        left: String,
        right: Array,
        disabled: Boolean,
        appearance: {
            type: String,
            validator(value) {
                return ["radio", "checkbox"].includes(value);
            },
            default: "checkbox",
        },
        borderBottom: {
            type: Boolean,
            default: true,
        },
        modelValue: {
            type: Number,
            default: 0,
        },
    },
    methods: {
        toggleRadio(index) {
            if (!this.disabled) this.$emit("update:modelValue", index);
        },
    },
    emits: ["update:modelValue"],
};
</script>

<style lang="scss" scoped>
td {
    display: flex;
    gap: 1em;

    span {
        user-select: none;
        cursor: pointer;
        margin-left: 0.2em;

        &:hover {
            color: #0c63e4;
        }

        &.disabled {
            cursor: inherit;
            &:hover {
                color: inherit;
            }
        }
    }
}

.form-check .form-check-input {
    float: none;
}
</style>