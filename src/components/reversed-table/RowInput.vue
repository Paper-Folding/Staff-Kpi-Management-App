<template>
    <tr :style="{ 'border-bottom': borderBottom ? '#dee2e6 2px solid' : null }">
        <th :style="{ 'vertical-align': right.type === 'text' ? 'middle' : null }">{{ left }}</th>
        <td>
            <input v-if="right.type === 'text'" type="text" class="form-control" :placeholder="right.placeholder" @input="$emit('update:modelValue', $event.target.value)" :value="modelValue" :disabled="right.disabled" :readonly="right.disabled" />
            <textarea v-else rows="4" class="form-control" :placeholder="right.placeholder" @input="$emit('update:modelValue', $event.target.value)" :value="modelValue" :disabled="right.disabled" :readonly="right.disabled"></textarea>
        </td>
    </tr>
</template>
<script>
export default {
    props: {
        left: String,
        right: Object,
        borderBottom: {
            type: Boolean,
            default: true,
        },
        modelValue: [String, Number],
    },
    emits: ["update:modelValue"],
};
</script>