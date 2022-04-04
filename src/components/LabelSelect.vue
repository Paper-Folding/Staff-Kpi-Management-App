<template>
    <div style="margin-top:0.15rem">
        <select
            class="paper-input"
            :placeholder="$attrs.placeholder"
            :disabled="$attrs.disabled"
            v-model="value"
        >
            <option v-for="item in list" :value="item.value" :key="item.value">{{ item.text }}</option>
        </select>
        <button @click="confirm" title="确认" class="latter btn btn-outline-success">
            <i class="bi-check-lg"></i>
        </button>
        <button @click="cancel" title="取消更改" class="latter btn btn-outline-danger">
            <i class="bi-x-lg"></i>
        </button>
    </div>
</template>

<script>
export default {
    props: {
        modelValue: [Number, String],
        list: Array
    },
    data() {
        return {
            value: this.modelValue
        }
    },
    methods: {
        confirm() {
            if (this.value === this.modelValue)
                return;
            this.$emit('update:modelValue', this.value);
            this.$emit("onConfirm");
        },
        cancel() {
            this.value = this.modelValue;
        }
    },
    watch: {
        modelValue(newVal) {
            this.value = newVal;
        }
    },
    emits: ["update:modelValue", "onConfirm"]
}
</script>

<style lang="scss" scoped>
.paper-input {
    min-width: 5rem;
    outline: none;
    border: none;
    appearance: none;
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

        &:hover,
        &:focus {
            cursor: initial;
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