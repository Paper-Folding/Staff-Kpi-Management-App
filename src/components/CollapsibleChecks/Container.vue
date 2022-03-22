<template>
    <div class="grid">
        <collapsible-check
            :disabled="disabled"
            @sth-changed="recheck()"
            v-for="item in modelValue"
            :key="item.id"
            :parent="modelValue"
            :item="item"
        ></collapsible-check>
    </div>
</template>

<script>
/**
 * A multiple layers check group
 * props:
 *  modelValue for checks list, you should pass in an array with each item has fields that might include:
 *      text        label of check
 *      checked     check status, pass 0 for unchecked while 2 for checked, never pass in 1 for it is reserved for indeterminate; note that if current item has children, this field can be omitted
 *      collapsed   group collapse status, true for collapsed while false otherwise
 *      children    children of current item, it is an array has the same rule as modelValue itself 
 */
import CollapsibleCheck from "./Item.vue";
import tool from "./tool.js";
export default {
    props: {
        modelValue: {
            type: Array,
            default: () => ([])
        },
        disabled: {
            type: Boolean,
            default: false
        }
    },
    setup(props) {
        tool.tagUniqueId(props.modelValue);
        tool.initializeCheckStatus(props.modelValue);
        const recheck = () => {
            tool.initializeCheckStatus(props.modelValue);
        }
        return {
            recheck
        }
    },
    components: {
        CollapsibleCheck
    }
}
</script>

<style lang="scss" scoped>
.grid {
    column-gap: 1em;
    column-fill: auto;
    column-count: 4;
    @media (max-width: 992px) {
        column-count: 3;
    }
    @media (max-width: 768px) {
        column-count: 2;
    }
}
</style>