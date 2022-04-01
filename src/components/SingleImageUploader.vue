<template>
    <div class="container">
        <div class="container-top">
            <input ref="uploader" hidden @change="onImageSelected" type="file" accept="image/png, image/jpeg, image/bmp, image/jpg, image/tif" />
            <img :src="currentPreview" :style="{ width: previewWidth || '10em', 'border-radius': borderRadius || null }" />
            <div class="alerts">
                <Alert v-if="!ifSizeOk" color="red" :fontSize="6">所选的图片要求大小小于{{ limit }}MB。</Alert>
                <Alert v-if="!ifFormatOk" color="red" :fontSize="6">所选的图片要求格式为 jpeg, jpg, bmp, png, tif中的一种。</Alert>
            </div>
        </div>
        <div v-if="!disableUpload" class="container-bottom">
            <Button @click="callSelectImage" color="blue" icon="file-earmark-plus">选择图片</Button>
            <Button @click="resetDefault" color="red" icon="trash">重置为默认</Button>
        </div>

        <!-- Edit Image Modal -->
        <div v-show="!disableUpload" class="modal fade" ref="editModal" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">裁切图像</h5>
                    </div>
                    <div class="modal-body">
                        <img ref="editImage" />
                    </div>
                    <div class="modal-footer">
                        <Button color="red" icon="none" @click="editCancel">取消</Button>
                        <Button color="green" icon="check-lg" @click="editSave">确认裁切</Button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
/**
 * Component documentatioon
 * previewWidth prop for preview image's width
 * borderRadius prop for preview image's border radius
 * limit prop for upload image max size, metered by MB
 * v-model for finally selected avatar, if it has null value, it means either user cancel the avatar selection operation, or user reset the avatar, in this case, it is recommended not to update user's avatar or just set avatar the default image.
 */
import Button from "@/components/Button";
import Alert from "@/components/AlertBanner";
import { Modal } from "bootstrap";
import Cropper from "cropperjs";

export default {
    data() {
        return {
            initialImage: null, // initial image passed in by v-model
            imageNotEdited: null,
            imageEdited: null,
            editModal: null,
            cropper: null,
            ifSizeOk: true,
            ifFormatOk: true,
        };
    },
    props: {
        previewWidth: String,
        borderRadius: String,
        limit: {
            // limit max size, metered by MB
            type: Number,
            default: 10,
        },
        disableUpload: {
            type: Boolean,
            default: false,
        },
        modelValue: [Object, String], // Object is for image file Object, String is for image url string
    },
    mounted() {
        this.initialImage = typeof this.modelValue === "string" ? this.modelValue : "/images/default-avatar.jpg";
        this.editModal = new Modal(this.$refs.editModal, { keyboard: false });
        this.cropper = new Cropper(this.$refs.editImage, {
            aspectRatio: 1 / 1,
            background: false,
            minContainerWidth: 700,
            minContainerHeight: 500,
            rotatable: false,
        });
    },
    methods: {
        // return true for size below limit, false otherwise
        checkSize(fileObject) {
            if (fileObject) {
                if (fileObject.size > this.limit * 1024 * 1024) return false;
                return true;
            }
            return true;
        },
        // return true for format is correct, false otherwise
        checkFormat(fileObject) {
            if (fileObject) {
                if (/\.(jpeg|jpg|bmp|png|tif)$/i.test(fileObject.name)) return true;
                return false;
            }
            return true;
        },
        // the combination of checkSize and check format
        checkValidity(fileObject) {
            return this.checkSize(fileObject) && this.checkFormat(fileObject);
        },
        // get cropped image from cropper
        getCroppedImage() {
            if (this.cropper && this.imageNotEdited) {
                let croppedImageStr = window.atob(this.cropper.getCroppedCanvas().toDataURL(this.imageNotEdited.type).split(",")[1]),
                    length = croppedImageStr.length;
                let arr = new Uint8Array(length);
                for (let i = 0; i < length; i++) arr[i] = croppedImageStr.charCodeAt(i);
                return new File([arr], this.imageNotEdited.name, { type: this.imageNotEdited.type });
            }
            return null;
        },
        // open select file dialog
        callSelectImage() {
            this.$refs.uploader.click();
        },
        // cancel selected and cropped image, reset to initialImage
        resetDefault() {
            this.$refs.uploader.value = null;
            this.imageEdited = null;
            this.imageNotEdited = null;
            this.$emit("update:modelValue", null);
            this.ifSizeOk = true;
            this.ifFormatOk = true;
        },
        onImageSelected() {
            if (this.$refs.uploader.files == null || this.$refs.uploader.files.length === 0) return;
            this.imageNotEdited = this.$refs.uploader.files[0];
            this.ifSizeOk = this.checkSize(this.imageNotEdited);
            this.ifFormatOk = this.checkFormat(this.imageNotEdited);
            if (this.ifSizeOk && this.ifFormatOk) {
                let blob = URL.createObjectURL(this.imageNotEdited);
                this.$refs.editImage.src = blob;
                this.cropper.replace(blob);
                this.editModal.show();
            }
        },
        editSave() {
            this.imageEdited = this.getCroppedImage();
            this.editModal.hide();
            this.imageNotEdited = null;
            this.cropper.reset();
            this.$emit("update:modelValue", this.imageEdited);
        },
        editCancel() {
            this.editModal.hide();
            this.resetDefault();
            this.cropper.reset();
        },
    },
    computed: {
        currentPreview() {
            if (this.imageEdited == null) return this.initialImage;
            return URL.createObjectURL(this.imageEdited);
        },
    },
    watch: {
        modelValue(value) {
            this.initialImage = value;
        },
    },
    components: {
        Button,
        Alert,
    },
    emits: ["update:modelValue"],
};
</script>

<style lang="scss" scoped>
@import "~cropperjs/dist/cropper.css";
.container-top {
    display: flex;
    gap: 5em;
    .alerts {
        display: inline-flex;
        flex-direction: column;
        justify-content: center;
        > div {
            letter-spacing: 0.03em;
            margin-bottom: 0;
            &:first-of-type {
                margin-bottom: 0.5rem;
            }
        }
    }
}

.container-bottom {
    margin-top: 0.8rem;
    display: flex;
    gap: 1.5rem;
}

.modal-body {
    margin: 0 auto;
}
</style>