<template>
    <input
        type="file"
        ref="uploader"
        hidden
        @change="onImageSelected"
        accept="image/png, image/jpeg, image/bmp, image/jpg, image/tif"
    />
</template>

<script>
import Cropper from "cropperjs";
export default {
    props: {
        limitSize: {
            type: Number,
            default: 5
        }
    },
    setup(props, context) {
        let imageRaw = ref(null);
        const uploader = ref(null);
        // call select dialog
        const call = () => {
            uploader.value.click();
        }
        const checkSize = fileObj => {
            return fileObj ? (fileObj.size <= props.limitSize * 1024 * 1024) : true;
        }
        const checkFormat = fileObj => {
            return fileObj ? (/\.(jpeg|jpg|bmp|png|tif)$/i.test(fileObj.name)) : true;
        }
        const getCroppedImage = () => {
            
        }
        const onImageSelected = () => {
            if (uploader.value.files == null || uploader.value.files.length === 0)
                return;
            imageRaw.value = uploader.value.files[0];

        }
        return {
            // data
            imageRaw,
            // refs
            uploader,
            // methods
            call
        }
    }
}
</script>

<style>
</style>