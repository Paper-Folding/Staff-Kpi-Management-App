<template>
    <datepicker
        :minimumView="type"
        :startingView="type"
        class="fs-4 selector-btn"
        :class="[disabled ? 'disabled' : '', $attrs.class]"
        :inputFormat="inputFormat"
        v-model="picked"
        :locale="locale"
        :disabled="disabled"
    />
</template>

<script>
import { computed } from "vue";
import Datepicker from "vue3-datepicker";
import { zhCN } from "date-fns/locale";
export default {
    props: {
        type: {
            // pick for month, day or time
            type: String,
            validator(value) {
                return ["month", "day", "time"].includes(value);
            },
            default: "day",
        },
        modelValue: Object,
        disabled: {
            type: Boolean,
            default: false
        }
    },
    setup(props, context) {
        const locale = zhCN;
        let inputFormat, startView;
        switch (props.type) {
            case 'day':
                inputFormat = "yyyy年MM月dd日";
                break;
            case 'month':
                inputFormat = "yyyy年MM月";
                break;
            case 'time':
                inputFormat = "hh时mm分";
        }
        const picked = computed({
            get: () => props.modelValue,
            set: (value) => {
                context.emit("update:modelValue", value)
            }
        })
        return {
            locale,
            inputFormat,
            startView,
            picked
        }
    },
    components: {
        Datepicker,
    },
    emits: ["update:modelValue"],
};
</script>

<style lang="scss" scoped>
::v-deep() {
    .v3dp__input_wrapper,
    .v3dp__input_wrapper > .selector-btn {
        transition: all 0.25s ease;
        border-radius: 0.25rem;
        user-select: none;
        cursor: pointer;
        &:hover,
        &:hover > .selector-btn {
            background-color: rgb(229, 229, 229);
            &::before {
                font-family: "bootstrap-icons";
                content: "\F214";
                font-size: 1.4em;
                visibility: visible;
                position: relative;
                top: 2px;
                margin-left: 0.8rem;
            }
        }

        &::before {
            font-family: "bootstrap-icons";
            content: "\F214";
            font-size: 1.4em;
            visibility: visible; /* change to hidden if hover visible */
            position: relative;
            top: 2px;
            margin-left: 0.8rem;
        }

        &:active {
            background-color: rgb(153, 153, 153);
        }

        &.disabled {
            cursor: not-allowed;

            &::before{
                cursor: not-allowed;
            }
        }
    }

    .selector-btn {
        outline: none;
        border: none;
        width: var(--date-picker-selector-btn-width, 10rem);
        text-align: center;
        padding: 0.18rem 3px 0.18rem 0;
        letter-spacing: 0.05em;
    }

    --vdp-hover-bg-color: #3d8bfd;
    --vdp-selected-bg-color: #3d8bfd;
    --vdp-heading-hover-color: #c8deff;
    --vdp-elem-font-size: 1em;
    --vdp-border-radius: 0.75em;
    --vdp-box-shadow: 3px 3px 8px #c5c5c5;

    .v3dp__popout {
        width: 20em;
        // right: 0rem;
    }

    .v3dp__heading__center {
        border-radius: 3em;
    }

    .v3dp__heading__button {
        border-radius: 50%;
    }
}
</style>