<template>
    <tr>
        <cell
            v-for="(key, index) in keys"
            :key="index"
            :scope="index === 0 ? 'row' : null"
            :row="value"
            :value="value[key]"
            :class="!Array.isArray(header) && header[key].hidden ? 'd-none' : null"
        ></cell>
    </tr>
</template>

<script>
/**
 * Use inside a table element only!
 */
import Cell from "./Cell.vue";

export default {
    props: {
        value: {
            type: Object,
            default: () => ({}),
        },
        header: [Array, Object],
        keys: Array
    },
    components: {
        Cell
    }
};
</script>

<style lang="scss" scoped>
tr {
    cursor: pointer;
    text-align: center;
    vertical-align: middle;
}
</style>