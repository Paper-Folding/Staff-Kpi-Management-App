<template>
    <div style="margin-top:0.15rem">
        <input
            :type="$attrs.type"
            :placeholder="$attrs.placeholder"
            class="paper-input"
            :disabled="$attrs.disabled"
            v-model="value"
        />
        <template v-if="!hideButton && !$attrs.disabled">
            <button @click="confirm" title="确认" class="latter btn btn-outline-success">
                <i class="bi-check-lg"></i>
            </button>
            <button @click="cancel" title="取消更改" class="latter btn btn-outline-danger">
                <i class="bi-x-lg"></i>
            </button>
        </template>
    </div>
</template>

<script>
import Maid from "../utils/Maid";
export default {
    props: {
        modelValue: String,
        hideButton: {
            type: Boolean,
            default: false
        }
    },
    data() {
        return {
            value: this.formatToCorrectDate(this.modelValue)
        }
    },
    methods: {
        formatToCorrectDate(dateString) {
            return Maid.formatDate(dateString, this.$attrs.type === 'date' ? 'YYYY-MM-DD' : 'YYYY-MM-DD hh:mm:ss');
        },
        confirm() {
            if (this.value === this.formatToCorrectDate(this.modelValue))
                return;
            this.$emit('update:modelValue', this.value);
            this.$emit("onConfirm");
        },
        cancel() {
            this.value = this.formatToCorrectDate(this.modelValue);
        }
    },
    watch: {
        modelValue(newVal) {
            this.value = this.formatToCorrectDate(newVal);
        },
        value(newVal) {
            if (this.hideButton) {
                this.$emit('update:modelValue', newVal);
            }
        }
    },
    emits: ["update:modelValue", "onConfirm"]

}
</script>

<style lang="scss" scoped>
.paper-input {
    outline: none;
    border: none;
    padding-left: 0.25rem;
    font-size: 1.15em;
    border-bottom: transparent 2px solid;
    transition: all 0.2s ease;

    &:hover {
        cursor: pointer;
        border-bottom: rgb(168, 168, 168) 2px solid;
    }

    &:focus {
        cursor: text;
        border-bottom: black 2px solid;

        & ~ .latter {
            visibility: visible;
            opacity: 1;
        }
    }

    &:disabled {
        background: transparent;
        border-bottom: none !important;
        color: black;
        cursor: initial;

        &:hover,
        &:focus {
            border-bottom: none !important;
        }
    }
}

.latter {
    visibility: hidden;
    opacity: 0;
    transition: all 0.2s ease;
    padding: 0;
    padding-left: 0.2em;
    padding-right: 0.2em;
    margin-left: 0.25rem;
}
</style>