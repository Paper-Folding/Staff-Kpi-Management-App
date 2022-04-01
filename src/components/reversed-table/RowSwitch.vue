<template>
    <tr :style="{ 'border-bottom': borderBottom ? '#dee2e6 2px solid' : null }">
        <th>{{ left }}</th>
        <td>
            <span :class="disabled ? 'disabled' : null" @click="disabled ? null : $emit('update:modelValue', false)">{{ right[0] }}</span>
            <div class="form-check form-switch">
                <input class="form-check-input" type="checkbox" role="switch" :checked="modelValue" @change="$emit('update:modelValue', $event.target.checked)" :disabled="disabled" />
            </div>
            <span :class="disabled ? 'disabled' : null" @click="disabled ? null : $emit('update:modelValue', true)">{{ right[1] }}</span>
        </td>
    </tr>
</template>

<script>
export default {
    props: {
        left: String,
        right: Object,
        disabled: Boolean,
        borderBottom: {
            type: Boolean,
            default: true,
        },
        modelValue: Boolean,
    },
    emits: ["update:modelValue"],
};
</script>

<style lang="scss" scoped>
tr > td:last-of-type {
    display: flex;
    span {
        user-select: none;
        cursor: pointer;
        &:first-of-type {
            margin-right: 0.6em;
        }

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

.form-check-input {
    margin-top: 0.15em;
}

.form-switch {
    font-size: 1.25em;
}

.form-check {
    margin-bottom: 0;
}
</style>