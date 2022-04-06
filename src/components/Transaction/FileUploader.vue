<template>
    <input
        type="file"
        ref="uploader"
        hidden
        @change="onSelected"
        accept=".doc, .docx, .pdf, image/gif, image/png, image/jpeg, image/bmp, image/jpg, image/tif"
    />
    <div>
        <a class="link-secondary text-decoration-none" @click="openFile" href="javascript:void(0)">
            <i v-if="fileExt === 'doc' || fileExt === 'docx'" class="bi-file-earmark-word"></i>
            <i v-else-if="fileExt === 'pdf'" class="bi-file-earmark-pdf"></i>
            <i v-else-if="fileExt === ''"></i>
            <i v-else class="bi-file-earmark-image"></i>
            {{ fileName }}
        </a>
    </div>
    <template v-if="!disabled">
        <outline-button
            v-if="fileExt == null || fileExt === '' || fileRaw == null"
            icon="file-earmark-fill"
            style="transform:scale(0.9)"
            color="blue"
            @click="$refs.uploader.click()"
        >选择文件以覆盖上传</outline-button>
        <div v-else>
            <outline-button
                icon="cloud-arrow-up-fill"
                style="transform:scale(0.9)"
                color="green"
                @click="onConfirmed"
            >确认上传</outline-button>
            <outline-button
                icon="arrow-clockwise"
                style="transform:scale(0.9)"
                color="red"
                @click="restoreInitial(); $refs.uploader.click()"
            >重新选择</outline-button>
        </div>
    </template>
</template>

<script>
import { computed, ref, watch } from "vue";
import { useStore } from "vuex";
import OutlineButton from "../OutlineButton.vue";
import "cropperjs/dist/cropper.min.css";
export default {
    props: {
        limitSize: {
            type: Number,
            default: 20
        },
        disabled: {
            type: Boolean,
            default: false
        },
        // json string: {"ori": "xxx.xxx", "store": "xxx.xxx"}
        certificate: String,
        contestId: Number
    },
    components: {
        OutlineButton
    },
    setup(props, context) {
        const uploader = ref(null), store = useStore();
        let fileRaw = ref(null), fileName = ref(''), fileLink = ref('');
        watch(() => props.certificate, (val) => {
            if (val != null && val != '') {
                const obj = JSON.parse(val);
                fileName.value = obj.ori;
            } else {
                fileName.value = '';
            }
        });
        watch(() => props.contestId, (val) => {
            fileLink.value = import.meta.env.VITE_API_URL + '/contest/cert/' + val;
        })
        // call select dialog
        const call = () => {
            uploader.value.click();
        }
        const checkSize = fileObj => {
            return fileObj ? (fileObj.size <= props.limitSize * 1024 * 1024) : true;
        }
        const checkFormat = fileObj => {
            return fileObj ? (/\.(doc|docx|pdf|gif|jpeg|jpg|bmp|png|tif)$/i.test(fileObj.name)) : true;
        }
        const onSelected = () => {
            if (uploader.value.files == null || uploader.value.files.length === 0)
                return;
            fileRaw.value = uploader.value.files[0];
            if (!checkFormat(fileRaw.value)) {
                store.state.notify("格式不对！");
                return;
            }
            if (!checkSize(fileRaw.value)) {
                store.state.notify("大小超限！请选择大小小于" + props.limitSize + "MB的文件！");
                return;
            }
            if (fileRaw.value != null) {
                fileName.value = fileRaw.value.name ?? '';
                fileLink.value = URL.createObjectURL(fileRaw.value);
                context.emit("selected", fileRaw.value);
            }
        }
        const fileExt = computed(() => {
            return fileName.value.substring(fileName.value.lastIndexOf('.') + 1)
        });
        const restoreInitial = () => {
            fileRaw.value = null;
            uploader.value.value = null;
        }
        const onConfirmed = () => {
            context.emit("confirmed", fileRaw.value);
            restoreInitial();
        }
        const openFile = () => {
            window.open(fileLink.value);
        }
        return {
            // data
            fileName,
            fileRaw,
            // computed
            fileExt,
            // refs
            uploader,
            // methods
            call,
            restoreInitial,
            onSelected,
            onConfirmed,
            openFile
        }
    },
    emits: ["selected", "confirmed"],
}
</script>

<style lang="scss" scoped>
::v-deep() {
    .modal-body {
        margin: 0 auto;
    }
}

span i {
    font-size: 1.1em;
}
</style>