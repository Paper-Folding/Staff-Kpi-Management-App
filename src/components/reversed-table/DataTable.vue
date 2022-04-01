<template>
    <table class="table table-borderless">
        <thead>
            <tr>
                <!-- Fuck your mommy! max-width cannot be used here! -->
                <th :style="'width:' + leftWidth"></th>
                <th></th>
            </tr>
        </thead>
        <tbody>
            <slot></slot>
        </tbody>
    </table>
</template>

<script>
export default {
    name: "DataTable",
    props: {
        leftWidth: {
            type: String,
            default: "50%",
        },
    },
};
</script>

<style lang="scss" scoped>
table {
    width: 100%;
    table-layout: fixed;
    thead {
        visibility: hidden;

        th {
            padding: 0;
        }
    }
}
</style>