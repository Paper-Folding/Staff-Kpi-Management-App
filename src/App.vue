<template>
    <router-view />
    <Notification ref="notifier"></Notification>
</template>

<script>
import { ref, onMounted } from "vue";
import { useStore } from 'vuex';
import Notification from "./components/Notification.vue"
export default {
    setup() {
        let notifier = ref({});
        onMounted(() => {
            // expose notifier 'show' method globally to prepare calling it else where
            useStore().state.notify = notifier.value.show;
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
@import "./global.scss";
@import "bootstrap-icons";
</style>
