<template>
    <tr :style="{ 'border-bottom': borderBottom ? '#dee2e6 2px solid' : null }">
        <th>{{ left }}</th>
        <td><img :src="right.src" alt="图片不见了" :style="{ width: right.width || '4em', 'border-radius': right.borderRadius || null }" /></td>
    </tr>
</template>

<script>
export default {
    props: {
        left: String,
        right: Object,
        borderBottom: { // if bottom has a line
            type: Boolean,
            default: true,
        },
    },
};
</script>

<style lang="scss" scoped>
img {
    border: 1px solid rgb(190, 190, 190);
}
</style>