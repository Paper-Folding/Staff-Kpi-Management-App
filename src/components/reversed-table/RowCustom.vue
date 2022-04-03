<template>
    <tr
        :style="{ 'border-bottom': borderBottom ? '#dee2e6 2px solid' : null, 'vertical-align': 'middle' }"
    >
        <th>{{ left }}</th>
        <td>
            <slot></slot>
        </td>
    </tr>
</template>

<script>
export default {
    props: {
        left: String,
        borderBottom: {
            type: Boolean,
            default: true,
        },
    },
};
</script>