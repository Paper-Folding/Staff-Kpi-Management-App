<template>
    <div class="group">
        <div class="parent" :style="childrenIndent">
            <i
                v-if="hasChildren(item)"
                class="bi-caret-right-fill"
                :class="item.collapsed ? '' : 'icon-rotate'"
                @click="item.collapsed = !item.collapsed"
            ></i>
            <div class="form-check">
                <input
                    type="checkbox"
                    class="form-check-input"
                    :disabled="disabled"
                    :indeterminate="item.checked === 1"
                    :checked="item.checked === 2"
                    @change="onValueChange()"
                    :id="`--collapsible-checks-${item.id}--`"
                />
                <label
                    :for="`--collapsible-checks-${item.id}--`"
                    :class="disabled ? 'disabled' : ''"
                >{{ item.text }}</label>
            </div>
        </div>
        <div v-if="hasChildren(item) && !item.collapsed">
            <collapsible-check
                @sth-changed="$emit('sthChanged')"
                v-for="subItem in item.children"
                :disabled="disabled"
                :key="subItem.id"
                :parent="item"
                :item="subItem"
                :indent="subItem.children && subItem.children.length > 0 ? (indent + 1) : (indent + 2)"
            ></collapsible-check>
        </div>
    </div>
</template>

<script>
import { computed } from 'vue';
import tool from "./tool.js";
export default {
    name: "CollapsibleCheck",
    props: {
        parent: Object,
        item: Object,
        indent: {
            type: Number,
            default: 0
        },
        disabled: {
            type: Boolean,
            default: false
        }
    },
    setup(props, context) {
        const childrenIndent = computed(() => ({ 'padding-left': `${props.indent}em` }));
        const onValueChange = () => {
            switch (props.item.checked) {
                case 0:
                case 1:
                    // unchecked or indeterminate checks, switch self and all decendants on
                    tool.setSelfAndAllDecendantsTo(props.item, true);
                    break;
                case 2:
                    // off
                    tool.setSelfAndAllDecendantsTo(props.item, false);
                    break;
            }
            context.emit("sthChanged");
        }
        return {
            childrenIndent,
            onValueChange,
            hasChildren: tool.hasChildren
        }
    },
    emits: ["sthChanged"]
}
</script>

<style lang="scss" scoped>
.group {
    column-break-inside: avoid;
    -webkit-column-break-inside: avoid;
    -moz-column-break-inside: avoid;
}

.parent {
    display: flex;
    overflow: hidden;

    > i {
        cursor: pointer;
        transition: transform 0.3s ease-in-out;

        ::after {
            content: "\00a0";
        }

        &.icon-rotate {
            transform: rotate(90deg);
            margin-bottom: auto;
        }
    }
}

label,
input[type="checkbox"] {
    user-select: none;
    cursor: pointer;

    &.disabled {
        cursor: default;
    }
}
</style>