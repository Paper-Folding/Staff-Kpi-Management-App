<template>
    <tr :style="{ 'border-bottom': borderBottom ? '#dee2e6 2px solid' : null }">
        <th>{{ left }}</th>
        <td>
            <multiselect
                v-model="_modelValue"
                select-label="按下回车以选中"
                deselect-label="按下回车以移除"
                :close-on-select="false"
                tag-placeholder="添加此类别"
                selected-label="已选"
                :placeholder="right.disabled? '暂无' : '搜索类别或在此键入以添加一个新的类别'"
                :label="right.label"
                :track-by="right.id"
                :options="right.tags"
                :multiple="true"
                :taggable="true"
                :disabled="right.disabled || false"
                @tag="onTagAdding"
            ></multiselect>
        </td>
    </tr>
</template>

<script>
import Multiselect from "vue-multiselect";
export default {
    props: {
        left: String,
        right: Object,
        borderBottom: {
            type: Boolean,
            default: true,
        },
        modelValue: [Object, Array],
    },
    components: {
        Multiselect,
    },
    methods: {
        onTagAdding(newTagLabel) {
            this.$emit("tag", newTagLabel);
        },
    },
    computed: {
        _modelValue: {
            get() {
                return this.modelValue;
            },
            set(value) {
                this.$emit("update:modelValue", value);
            },
        },
    },
    emits: ["tag", "update:modelValue"],
};
</script>

<style lang="scss" scoped>
th {
    vertical-align: middle;
}
</style>