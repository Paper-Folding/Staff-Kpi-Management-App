<template>
    <tr :style="{ 'border-bottom': borderBottom ? '#dee2e6 2px solid' : null }">
        <th>{{ left }}</th>
        <td>{{ right }}</td>
    </tr>
</template>

<script>
export default {
    props: {
        left: String,
        right: [String,Number],
        borderBottom: {
            type: Boolean,
            default: true,
        },
    },
};
</script>