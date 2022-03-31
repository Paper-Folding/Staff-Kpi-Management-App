<template>
    <div ref="container" class="global-notification-toast-container toast-container p-5"></div>
</template>

<script>
/**
 * Notification
 * This is not actually a common vue component. To use this,
 * you need to expose 'show' method globally.
 */
import { Toast } from 'bootstrap';
export default {
    created() {
        this.type = {
            success: 'bi-check-lg',
            warning: 'bi-exclamation-lg',
            failure: 'bi-x-lg'
        }
    },
    methods: {
        /**
         * @param msg The message would like to show
         * @param type Should be one of 'success', 'warning' or 'failure'
         * @param delay A digit indicate how long will the notification's stay last, default is 5000(ms)
         */
        show(msg, type = "failure", delay = 5000) {
            let notification = document.createElement('div');
            notification.classList.add('fade', 'hide', 'fs-6', type)
            notification.setAttribute('data-bs-autohide', 'true');
            notification.setAttribute('data-bs-delay', `${delay}`);
            notification.setAttribute('role', 'alert');
            let wrapper = document.createElement('div');
            wrapper.classList.add('d-flex');
            let body = document.createElement('div');
            body.classList.add('toast-body');
            let icon = document.createElement('i');
            icon.classList.add(this.type[type])
            let msgSpan = document.createElement('span');
            msgSpan.innerText = msg;
            let closeBtn = document.createElement('button');
            closeBtn.classList.add('btn-close', 'btn-close-white');
            closeBtn.setAttribute('aria-label', 'Close');
            closeBtn.addEventListener('click', () => notification.classList.add('d-none'));

            body.appendChild(icon);
            body.appendChild(msgSpan);
            wrapper.appendChild(body);
            wrapper.appendChild(closeBtn);
            notification.appendChild(wrapper);
            this.$refs.container.appendChild(notification);
            let toast = new Toast(notification);
            toast.show();
            notification.addEventListener('hidden.bs.toast', notification.remove);
        }
    }
}
</script>

<style lang="scss">
.global-notification-toast-container {
    position: fixed;
    bottom: 0;
    right: 0;
    z-index: 13140;
    > div {
        width: 350px;
        max-width: 100%;
        font-weight: normal;
        pointer-events: auto;
        background-clip: padding-box;
        box-shadow: 0 0.5rem 1rem rgba(0, 0, 0, 0.15);
        border-radius: 0.25rem;
        transition: all 0.5s ease;

        .toast-body {
            letter-spacing: 0.1em;
            > i::after {
                content: "\00a0\00a0"; // just two spaces
            }
        }

        .btn-close {
            margin: auto !important;
            margin-right: 0.5rem !important;
        }

        &.success {
            color: white;
            background-color: rgb(25, 135, 84);
        }

        &.warning {
            color: #664d03;
            background-color: rgb(255, 193, 7);
        }

        &.failure {
            color: white;
            background-color: rgb(220, 53, 69);
        }
    }
}
</style>