<template>
    <ul class="nav nav-tabs" v-if="modelValue && list && list.length > 0">
        <Item
            @toggle-tab="$emit('update:modelValue', item)"
            v-for="item in list"
            :active="modelValue[keyColumn] === item[keyColumn]"
            :key="item[keyColumn]"
        >{{ item.text }}</Item>
    </ul>
</template>

<script>
import Item from "./Item.vue";

/**
 * props:
 *  modelValue: pass in an object that are in list props, it will serve as initial selected tab and keep track of selected tab when toggling tabs
 *  list: tab list should be rendered, every item within it should have a 'text' field which refers to tab name
 *  keyColumn: which field in modelValue should be served as key which is important
 */
export default {
    name: "Tab",
    props: {
        modelValue: {
            type: [null, Object],
            required: true
        },
        list: {
            type: Array,
            default: () => ([])
        },
        keyColumn: {
            type: String,
            required: true
        }
    },
    components: {
        Item,
    },
    emits: ['update:modelValue']
};
</script>

<style lang="scss" scoped>
.nav-tabs {
    font-size: 1.25em;
    border-bottom: 2px var(--bs-blue) solid;
}
</style>