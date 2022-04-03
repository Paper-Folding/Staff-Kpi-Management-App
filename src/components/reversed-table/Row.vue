<!-- DataRow is the base class of other type of rows. -->
<template>
    <RowString
        v-if="['str', 'string'].includes(row.type)"
        :left="row.left"
        :right="row.right"
        :borderBottom="row.borderBottom"
    />
    <RowImage
        v-else-if="['img', 'image'].includes(row.type)"
        :left="row.left"
        :right="{ src: row.right.src, borderRadius: row.right.borderRadius, width: row.right.width }"
        :borderBottom="row.borderBottom"
    />
    <RowSwitch
        v-else-if="['sw', 'switch'].includes(row.type)"
        :left="row.left"
        :right="row.right"
        :disabled="row.disabled"
        v-model="_modelValue"
        :borderBottom="row.borderBottom"
    />
    <RowSelect
        v-else-if="['sl', 'select'].includes(row.type)"
        :left="row.left"
        :right="row.right"
        v-model="_modelValue"
        :borderBottom="row.borderBottom"
    />
    <RowTag
        v-else-if="['tg', 'tag'].includes(row.type)"
        :left="row.left"
        :right="row.right"
        v-model="_modelValue"
        :borderBottom="row.borderBottom"
        @tag="onTagAdding"
    />
    <RowRadio
        v-else-if="['rd', 'radio'].includes(row.type)"
        :left="row.left"
        :right="row.right"
        :disabled="row.disabled"
        v-model="_modelValue"
        :borderBottom="row.borderBottom"
        :appearance="row.appearance"
    />
    <RowCheckBox
        v-else-if="['cb', 'checkbox'].includes(row.type)"
        :left="row.left"
        :right="row.right"
        v-model="_modelValue"
        :borderBottom="row.borderBottom"
    />
    <RowInput
        v-else-if="['i', 'input'].includes(row.type)"
        :left="row.left"
        :right="row.right"
        :borderBottom="row.borderBottom"
        v-model="_modelValue"
    />
    <RowCustom v-else :left="row.left" :borderBottom="row.borderBottom">
        <slot></slot>
    </RowCustom>
</template>

<script>
/**
 * Component Documentation
 * You can Pass object as shown below:
 * 1. string row
 * {
 *      type: "str/string",
 *      left: "[a string value]",
 *      right: "[a string value]",
 *      borderBottom: "[a boolean value]" // if bottom line should be generated, default is true
 * }
 * e.g. <Row :row="{ type: 'str', left: 'name', right: 'Ah Ling' }" />
 * 2. image row
 * {
 *      type: "img/image",
 *      left: "[a string value]",
 *      right: {
 *          src: "[a string value]",    // image src
 *          borderRadius: [e.g. "50%"], // image border radius
 *          width: [e.g. "10em"],       // image width
 *      },
 *      borderBottom: "[a boolean value]" // if bottom line should be generated, default is true
 * }
 * e.g. <Row :row="{ type: 'img', left: 'avatar', right: { src: 'http://127.0.0.1/avatar.png', borderRadius: '50%', width: '10em' }, borderBottom: false }" />
 * 3. checkbox row
 * {
 *      type: "cb/checkbox",
 *      left: "[a string value]",
 *      right: [{
 *          text: "[a string value]",
 *          disabled: [a boolean value],
 *      }, ...],
 *      borderBottom: "[a boolean value]" // if bottom line should be generated, default is true
 * }
 * PLUS1: v-model for checked items' array
 * PLUS2: if passing in a pure array for 'right', it will reconginize it as array of right.text
 * e.g.1 <Row v-model="checks" :row="{ type: 'cb', left: 'shit', right: ['yes', 'no', 'or', 'you?'] }" />
 * e.g.2 <Row v-model="checks" :row="{ type: 'cb', left: 'second shit', right: [{ text: 'yes' }, { text: 'no' }, { text: 'or', disabled: true }, { text: 'you' }] }" />
 * In these two examples, I pass in checks: [false, true], it will render checks like [inchecked, checked, inchecked, inchecked]
 * 4. switch row
 * {
 *      type: "sw/switch",
 *      left: "[a string value]",
 *      disabled: "[a boolean value]",      // if changing value is disabled
 *      right: ["left string value", "right string value"], // left right displaying texts
 *      borderBottom: "[a boolean value]"   // if bottom line should be generated, default is true
 * }
 * PLUS: v-model for "checked" property, it's a boolean value
 * e.g. <Row v-model="checked" :row="{ type: 'sw', left: 'laser', right: ['no', 'yes'], disabled: true }" />
 * 5. tag row
 * {
 *      type: "tg/tag",
 *      left: "[a string value]",
 *      right: {
 *          id: [a string value],           // define which field passed in to use as unique id
 *          label: [a string value],        // defines which field passed in to use as showing label
 *          tags: [an object array],        // used for passing in initial data set of tags, must contain field 'id' and 'label' specified
 *      },
 *      borderBottom: "[a boolean value]"   // if bottom line should be generated, default is true
 * }
 * PLUS1: v-model for selected tags array, this can be used for passing in initial data(but must be a sub-set of 'right.tags'), and reading value from parent component scope
 * PLUS2: @tag for listening when a new tag was added, it provide a parameter that contains new tag's label
 * e.g. <Row v-model="values" :row="{ type: 'tag', left: 'gua', right: { id: 'id', label: 'name', tags: tags }}" @tag="onTagAdding"/>
 * I pass tags: [{ id: 1, name: "Yuezheng" }, { id: 2, name: "Ling" }, { id: 3, name: "Love" }, { id: 4, name: "forever" }], values: [{ id: 2, name: "Ling" }] to make them responsive and display Ling at start, and basically the "onTagAdding" method adds the new tag to arrays of "tags" and "values".
 * 6. select row (similar to tag row)
 * All is the same as tag row, except for right.tags rename as right.options, and added an right.multiple option to decide whether to use multiple select. Also, no add tag event was emited.
 * {
 *      type: "sl/select",
 *      left: "[a string value]",
 *      right: {
 *          id: [a string value],           // define which field passed in to use as unique id
 *          label: [a string value],        // defines which field passed in to use as showing label
 *          options: [an object array],     // used for passing in initial data set of options, must contain field 'id' and 'label' specified
 *          multiple: [a boolean value]     // indicate if multiple select is enabled
 *      },
 *      borderBottom: "[a boolean value]"   // if bottom line should be generated, default is true
 * }
 * PLUS: v-model for selected options array, this can be used for passing in initial data(but must be a sub-set of 'right.options'), and reading value from parent component scope
 * 7. input row
 * {
 *      type: "i/input",
 *      left: "[a string value]",
 *      right: {
 *          type: "text/textarea",
 *          disabled: [a boolean value],    // defines initial state for disabled property, default is false
 *          placeholder: [a string value]
 *      },
 *      borderBottom: "[a boolean value]"   // if bottom line should be generated, default is true
 * }
 * PLUS: v-model for setting and getting value
 * e.g. <Row v-model="ah" :row="{ type: 'i', left: 'Hola', right: { type: 'textarea', placeholder: 'input here' } }" />
 * PLUS: v-model for setting and getting value, this this value's detail, see @file:AreaPicker.vue for more details.
 * e.g. <Row :row="{ type: 'ap', left: 'place', disabled: false, borderBottom: false }" v-model="area" />
 * 9. radio row
 * {
 *      type: "rd/radio",
 *      left: "[a string value]",
 *      right: "[an array of strings]"      // use for display text for each radio
 *      disabled: "[a boolean value]",      // if changing value is disabled
 *      appearance: "radio/checkbox",       // should our radios look like radios(circle) or checkboxes(square)?
 *      borderBottom: "[a boolean value]"   // if bottom line should be generated, default is true
 * }
 * PLUS: v-model for which one is selected, it is a number indicates the selected one's index
 * 10. single image uploader row
 * {
 *      type: "su/singleuploader",
 *      left: "[a string value]",
 *      right: {
 *          width: "[a string value]",          // use for define preview image width
 *          limit: [a Number value],            // limit size, metered by MB
 *          borderRadius: "[a string value]",   // preview image border radius
 *          disableUpload: [a Boolean value],   // if upload is disabled, if true, it will only show a preview image
 *      },
 *      borderBottom: "[a boolean value]"       // if bottom line should be generated, default is true
 * }
 * PLUS: v-model for a fil
 * e.g. <Row v-model="image" :row="{ type: 'su', left: 'sss', right: { width: '10em', limit: 1, borderRadius: '50%' } }" />
 */
import RowString from "./RowString.vue";
import RowImage from "./RowImage.vue";
import RowCheckBox from "./RowCheckBox.vue";
import RowRadio from "./RowRadio.vue";
import RowSwitch from "./RowSwitch.vue";
import RowTag from "./RowTag.vue";
import RowSelect from "./RowSelect.vue";
import RowInput from "./RowInput.vue";
import RowCustom from './RowCustom.vue';
export default {
    name: "Row",
    props: {
        row: Object,
        validator(value) {
            return value.type != null;
        },
        modelValue: [String, Object, Boolean, Array, Number],
    },
    components: {
        RowString,
        RowImage,
        RowCheckBox,
        RowSwitch,
        RowTag,
        RowSelect,
        RowInput,
        RowRadio,
        RowCustom
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
    methods: {
        onTagAdding(newTagLabel) {
            if (["tg", "tag"].includes(this.row.type)) this.$emit("tag", newTagLabel);
            else console.error("Never use tag event when your row type is not a tag!");
        },
    },
    emits: ["update:modelValue", "tag"],
};
</script>

<style lang="scss" scoped>
</style>