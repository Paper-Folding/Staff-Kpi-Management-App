<template>
    <router-view />
    <Notification ref="notifier"></Notification>
</template>

<script>
import { ref, provide, onMounted } from "vue"
import Notification from "./components/Notification.vue"
export default {
    setup() {
        let notifier = ref({});
        onMounted(() => {
            // expose notifier 'show' method globally to prepare calling it else where
            provide('showNotification', notifier.value.show);
        })
        return {
            notifier
        }
    },
    components: {
        Notification
    }
}
</script>

<style lang="scss">
/* import global style sheet here */
@import "bootstrap";
@import url("./global.scss");
@import "bootstrap-icons";
</style>
