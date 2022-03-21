<template>
    <ul class="gap-2" v-if="totalPage > 1">
        <li @click="$emit('update:modelValue', modelValue - 1)" v-if="modelValue !== 1">
            <i class="pi-left-arrow"></i>
        </li>
        <li @click="$emit('update:modelValue', 1)" v-if="range.start > 1" class="fw-bold">1</li>
        <li @click="$emit('update:modelValue', range.start - 1)" v-if="range.start > 2">
            <i class="pi-three-dots"></i>
        </li>
        <li
            @click="$emit('update:modelValue', i + range.start - 1)"
            class="fw-bold"
            :class="modelValue === i + range.start - 1 ? ((i + range.start - 1) < 100 ? 'active-two-digits' : 'active-three-or-more-digits') : null"
            v-for="i in range.length"
            :key="i"
        >
            <span>{{ i + range.start - 1 }}</span>
        </li>
        <li
            @click="$emit('update:modelValue', range.start + range.length)"
            v-if="range.start + range.length < totalPage"
        >
            <i class="pi-three-dots"></i>
        </li>
        <li
            @click="$emit('update:modelValue', totalPage)"
            v-if="range.start + range.length - 1 < totalPage"
            class="fw-bold"
        >{{ totalPage }}</li>
        <li @click="$emit('update:modelValue', modelValue + 1)" v-if="modelValue !== totalPage">
            <i class="pi-right-arrow"></i>
        </li>
    </ul>
</template>

<script>
import { computed } from 'vue';

export default {
    name: "Pagination",
    props: {
        // v-model for current page
        modelValue: {
            type: Number,
            required: true,
        },
        // total records amount
        total: {
            type: Number,
            required: true,
        },
        // how many records should be shown per page
        per: {
            type: Number,
            default: 10,
        },
        // how many centered page indexes should be displayed at most
        showAmount: {
            type: Number,
            default: 5,
        },
    },
    setup(props) {
        // data + watch props = computed, so use computed here
        const totalPage = computed(() => Math.ceil(props.total / props.per));
        const _showAmount = computed(() => {
            if (window.innerWidth <= 768)
                return props.showAmount <= 6 ? props.showAmount : 6;
            return props.showAmount;
        })
        const range = computed(() => {
            let result = {};
            if (totalPage.value <= _showAmount.value) {
                result = {
                    start: 1,
                    length: totalPage.value
                }
            } else {
                let pivot = Math.floor(_showAmount.value / 2);
                if (props.modelValue <= pivot) {
                    result = {
                        start: 1,
                        length: _showAmount.value
                    }
                } else if (props.modelValue >= totalPage.value - pivot) {
                    result = {
                        start: totalPage.value - _showAmount.value + 1,
                        length: _showAmount.value
                    }
                } else {
                    result.start = props.modelValue - pivot;
                    result.length = result.start + _showAmount.value - props.modelValue + pivot;
                }
            }
            return result;
        });
        return {
            totalPage,
            range
        }
    },
    emits: ["update:modelValue"],
};
</script>

<style lang="scss" scoped>
@import url("../assets/paper-icon/style.css");

$size: 1.25em;
$color: var(--bs-primary);

ul {
    display: flex;
    list-style: none;
    justify-content: flex-end;

    > li {
        float: left;
        margin: 0 0.1em;
        user-select: none;
        cursor: pointer;
        transition: line-height 0.1s linear;
        color: $color;
        font-size: $size;

        &:hover {
            line-height: 1;
            position: relative;

            &::after {
                content: "";
                position: absolute;
                width: 100%;
                height: 0;
                left: 0;
                bottom: 4px;
                border-bottom: 2px solid $color;
            }
        }

        &.active-two-digits {
            background: $color;
            border-radius: 100%;
            width: 1.5em;
            height: 1.5em;
            color: white;
            text-align: center;

            @media (max-width: 600px) {
                padding: 0 0.3em;
                border-radius: 0.5em;
            }

            &:hover {
                line-height: unset;
                cursor: initial;

                &::after {
                    border-bottom: unset;
                }
            }
        }

        &.active-three-or-more-digits {
            background: $color;
            height: 1.5em;
            padding: 0 0.15em;
            color: white;
            text-align: center;
            border-radius: 0.5em;

            &:hover {
                line-height: unset;
                cursor: initial;

                &::after {
                    border-bottom: unset;
                }
            }
        }
    }
}
</style>