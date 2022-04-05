<template>
    <input
        type="file"
        ref="uploader"
        hidden
        @change="onImageSelected"
        accept="image/gif, image/png, image/jpeg, image/bmp, image/jpg, image/tif"
    />
    <paper-modal ref="cropModal" size="lg">
        <template #title>裁切图像</template>
        <template #body>
            <img ref="cropperTarget" />
        </template>
        <template #footer>
            <outline-button color="red" icon="none" @click="cropCancel">取消</outline-button>
            <outline-button color="green" icon="check-lg" @click="cropSave">确认并修改头像</outline-button>
        </template>
    </paper-modal>
</template>

<script>
import { ref, onMounted } from "vue";
import { useStore } from "vuex";
import PaperModal from "../PaperModal.vue";
import OutlineButton from "../OutlineButton.vue";
import Cropper from "cropperjs";
import "cropperjs/dist/cropper.min.css";
export default {
    props: {
        limitSize: {
            type: Number,
            default: 5
        }
    },
    components: {
        PaperModal,
        OutlineButton
    },
    setup(props, context) {
        const store = useStore();
        let cropper, imageRaw;
        const uploader = ref(null), cropperTarget = ref(null), cropModal = ref(null);
        onMounted(() => {
            cropper = new Cropper(cropperTarget.value, {
                aspectRatio: 1 / 1,
                background: false,
                minContainerWidth: 700,
                minContainerHeight: 500,
                rotatable: false
            })
        });
        // call select dialog
        const call = () => {
            uploader.value.click();
        }
        const checkSize = fileObj => {
            return fileObj ? (fileObj.size <= props.limitSize * 1024 * 1024) : true;
        }
        const checkFormat = fileObj => {
            return fileObj ? (/\.(gif|jpeg|jpg|bmp|png|tif)$/i.test(fileObj.name)) : true;
        }
        const getCroppedImage = () => {
            if (cropper && imageRaw) {
                let croppedImageStr = window.atob(cropper.getCroppedCanvas().toDataURL(imageRaw.type).split(",")[1]),
                    length = croppedImageStr.length;
                let arr = new Uint8Array(length);
                for (let i = 0; i < length; i++) arr[i] = croppedImageStr.charCodeAt(i);
                return new File([arr], imageRaw.name, { type: imageRaw.type });
            }
            return null;
        }
        const onImageSelected = () => {
            if (uploader.value.files == null || uploader.value.files.length === 0)
                return;
            imageRaw = uploader.value.files[0];
            if (!checkFormat(imageRaw)) {
                store.state.notify("非图片格式！");
                return;
            }
            if (!checkSize(imageRaw)) {
                store.state.notify("图片大小超限！请选择大小小于" + props.limitSize + "MB的图片！");
                return;
            }
            let blob = URL.createObjectURL(imageRaw);
            cropperTarget.value.src = blob;
            cropper.replace(blob);
            cropModal.value.show();
        }
        const restoreInitial = () => {
            imageRaw = null;
            cropper.reset();
            uploader.value.value = null;
            cropModal.value.close();
        }
        const cropCancel = () => {
            restoreInitial();
        }
        const cropSave = () => {
            context.emit("cropDone", getCroppedImage());
            restoreInitial();
        }
        return {
            // refs
            uploader,
            cropModal,
            cropperTarget,
            // methods
            call,
            onImageSelected,
            cropCancel,
            cropSave
        }
    },
    emits: ["cropDone"],
}
</script>

<style lang="scss" scoped>
::v-deep() {
    .modal-body {
        margin: 0 auto;
    }
}
</style>