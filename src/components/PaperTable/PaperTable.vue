<!-- A data table component to display traditional table data -->
<template>
    <table ref="table" class="table table-hover" :class="expanded ? 'expanded' : ''">
        <thead ref="header">
            <tr>
                <th
                    v-for="(val, key) in header"
                    :class="val.hidden ? 'd-none' : null"
                    :key="key"
                    scope="col"
                    :style="{ width: typeof val === 'object' ? val?.width : 'auto' }"
                >{{ typeof val === 'object' ? val?.text : val }}</th>
            </tr>
        </thead>
        <transition-group appear name="row" tag="tbody" v-if="this.status === state.NORMAL">
            <row
                @click="$emit('rowClick', row)"
                v-for="(row, index) in modelValue"
                :key="keyColumn ? row[keyColumn] : index"
                :value="row"
                :header="header"
                :keys="keys"
            ></row>
        </transition-group>
    </table>
    <Loading :status="this.status" v-if="this.status !== state.NORMAL">
        <template #nothingLoaded>
            <slot name="nothingLoaded">No data was loaded!</slot>
        </template>
        <template #nothingFound>
            <slot name="nothingFound">Nothing was found!</slot>
        </template>
    </Loading>
</template>

<script>
/**
 * Props:
 *  header: defines how table header should be rendered, if you are not passing in header, the table will use the key set of the first row passed in by modelValue, which is risky if your data is dynamic.
 *      e.g. :header = { id: { text: '#', hidden: true }, name: { text: "名字", width: '10%' }, enroll: { text: '参加时间', time: 'YYYY-MM-DD hh:mm:ss'} }, the table header will only show 'name' column in modelValue with '姓名' and 'enroll' column with its children formatted date.
 *  modelValue: defines how table data should be displayed, for more detail, please check @file('./Cell.vue') for more detailed variations.
 *  keyColumn: Type is String. If you'd like to dynamically change DOM, e.g. delete a row, you MUST give it a column name of modelValue which has unique values(e.g. 'id'). Else, the table with use index of vue's v-for loop.
 * status: defines when you want your table state is, it has four values: NORMAL, LOADING, NOTHING_LOADED, NOTHING_FOUND, use them in other files by `import {state}`,
 *  and plus, you must control table states yourself
 */
import Row from "./Row.vue";
import Loading from './Loading.vue';
import state from "./Constants.js";

export default {
    name: "PaperTable",
    data() {
        return {
            expanded: true
        }
    },
    props: {
        header: {
            type: [Object, Array],
            default: (props) => {
                if (props.modelValue == null || props.modelValue.length === 0) return [];
                return Object.keys(props.modelValue[0]);
            },
        },
        modelValue: {
            type: Array,
            default: () => []
        },
        keyColumn: String,
        status: {
            type: Number,
            default: () => state.NORMAL
        }
    },
    created() {
        this.state = state;
    },
    mounted() {
        setTimeout(this.determineTableDisplayStyle, 1000);
    },
    methods: {
        determineTableDisplayStyle() {
            this.expanded = true;
            if (this.$refs.table.clientWidth > this.$refs.header.clientWidth)
                this.expanded = false;
            else
                this.expanded = true;
            return this.expanded;
        },
    },
    computed: {
        keys() {
            if (!Array.isArray(this.header))
                return Object.keys(this.header);
            return this.header;
        }
    },
    components: {
        Row,
        Loading
    },
    emits: ["rowClick"]
};
</script>

<style lang="scss" scoped>
table {
    table-layout: fixed;
    margin-bottom: 0.25em;

    > :not(:first-child) {
        border-top: none;
    }

    > :first-child {
        border-bottom: 2px solid currentColor;
    }

    thead > tr {
        text-align: center;
        vertical-align: middle;
    }

    &.expanded {
        display: block;
        overflow-x: auto;
        white-space: nowrap;

        ::v-deep() {
            td.button i {
                position: initial;
                transform: initial;
                transform-origin: initial;
            }
        }
    }

    .cell-button {
        transition: transform 0.25s;

        &:hover {
            transform: scale(2.5);
        }
    }
}

.row-enter-from {
    opacity: 0;
}

.row-enter-active {
    transition: all 0.5s;
}

.row-leave-to {
    opacity: 0;
    transform: scale(0.6);
}

.row-leave-active {
    transition: all 0.5s;
}

.row-move {
    transition: all 0.25s;
}
</style>