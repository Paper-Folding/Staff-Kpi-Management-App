<template>
    <tr :style="{ 'border-bottom': borderBottom ? '#dee2e6 2px solid' : null }">
        <th>{{ left }}</th>
        <td>
            <!-- use template in v-for will not include this template element but its children in html view -->
            <template v-for="(cb, i) in right" :key="i">
                <input class="form-check-input" type="checkbox" @change="toggleCheck(i)" :disabled="cb.disabled" :checked="modelValue[i] || false" />
                <span :class="cb.disabled ? 'disabled' : null" @click="toggleCheck(i)">&nbsp;{{ cb.text || cb }}</span>
            </template>
        </td>
    </tr>
</template>

<script>
export default {
    props: {
        left: String,
        right: Array,
        borderBottom: {
            type: Boolean,
            default: true,
        },
        modelValue: Array,
    },
    methods: {
        toggleCheck(index) {
            if (!this.right[index].disabled) {
                let _modelValue = this.modelValue;
                _modelValue[index] = !_modelValue[index];
                this.$emit("update:modelValue", _modelValue);
            }
        },
    },
    emits: ["update:modelValue"],
};
</script>

<style lang="scss" scoped>
span {
    user-select: none;
    cursor: pointer;
    margin-right: 1em;

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
</style>