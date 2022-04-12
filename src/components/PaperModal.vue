<template>
    <div
        ref="target"
        class="modal fade"
        :data-bs-backdrop="backdropDismiss ? null : 'static'"
        :data-bs-keyboard="escDismiss"
        tabindex="-1"
    >
        <div
            class="modal-dialog"
            :class="[`modal-${size}`, scrollable ? 'modal-dialog-scrollable' : '']"
        >
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">
                        <slot name="title"></slot>
                    </h5>
                    <button
                        v-if="closeBtn"
                        type="button"
                        class="btn-close"
                        data-bs-dismiss="modal"
                        aria-label="Close"
                    ></button>
                </div>
                <div class="modal-body">
                    <slot name="body"></slot>
                </div>
                <div class="modal-footer">
                    <slot name="footer">
                        <outline-button color="red" icon="none" @click="cancel">
                            <slot name="cancelBtnText">取消</slot>
                        </outline-button>
                        <outline-button color="green" icon="check-lg" @click="confirm">
                            <slot name="confirmBtnText">确认</slot>
                        </outline-button>
                    </slot>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
/**
 * Quick paper modal(from bootstrap)
 *  props:
 *      backdropDismiss if modal closable by click on backdrop, true for yes
 *      escDismiss      if modal closable by esc key, true for yes
 *      closeBtn        if modal should show close button at top right corner
 *      size            how wide will a modal be, 'xl', 'lg', 'md' and 'sm' should be passed, default is 'md' 
 *      scrollable      should modal body scrollable? default is false
 *  slots:
 *      their names are: 'title', 'body', 'footer'
 *      'cancelBtnText' for cancel button and 'confirmBtnText' for confirm button if footer slot is not specified
 *  emits:
 *      modalCancelled, modalConfirmed for cancel button and confirm button if footer slot is not specified
 *  for events, just use them as bootstrap documentation said: https://getbootstrap.com/docs/5.1/components/modal/#events
 */
import { onMounted, ref } from 'vue';
import { Modal } from "bootstrap";
import OutlineButton from './OutlineButton.vue';
export default {
    props: {
        backdropDismiss: {
            type: Boolean,
            default: true
        },
        escDismiss: {
            type: Boolean,
            default: true
        },
        closeBtn: {
            type: Boolean,
            default: true
        },
        size: {
            type: String,
            default: 'sm'
        },
        scrollable: {
            type: Boolean,
            default: false
        }
    },
    setup(_, context) {
        let target = ref(null), modal = null;
        onMounted(() => {
            modal = new Modal(target.value);
            target.value.addEventListener('hide.bs.modal', e => {
                context.emit('onClose', e);
            })
        });
        const close = () => {
            modal?.hide();
        }
        const open = () => {
            modal?.show();
        }
        const toggle = () => {
            modal?.toggle();
        }
        const cancel = () => {
            context.emit("modalCancelled");
        }
        const confirm = () => {
            context.emit("modalConfirmed");
        }
        return {
            // ref
            target,
            // methods
            open,
            show: open,
            close,
            hide: close,
            toggle,
            cancel,
            confirm,
        }
    },
    components: {
        OutlineButton
    },
    emits: ["modalCancelled", "modalConfirmed", "onClose"]
}
</script>

<style lang="scss" scoped>
.btn-close:focus {
    box-shadow: none;
}
</style>