<template>
    <tr :style="{ 'border-bottom': borderBottom ? '#dee2e6 2px solid' : null }">
        <th>{{ left }}</th>
        <td>
            <multiselect
                v-model="_modelValue"
                select-label="按下回车以选中"
                deselect-label="按下回车以移除"
                selected-label="已选"
                placeholder="在此键入以搜索"
                :label="right.label"
                :track-by="right.id"
                :options="right.options"
                :searchable="true"
                :closeOnSelect="!right.multiple"
                :multiple="right.multiple"
                ><template v-slot:noResult>未搜索到任何结果</template></multiselect
            >
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
    emits: ["update:modelValue"],
};
</script>

<style lang="scss" scoped>
th {
    vertical-align: middle;
}
</style>