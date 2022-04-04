<template>
    <input type="file" hidden ref="uploader" @change="onFileSelected" accept=".xls, .xlsx" />
    <outline-button
        icon="file-earmark-spreadsheet"
        :color="ifFormatOk && !ifFileEmpty ? 'green' : 'red'"
        @click="callSelect"
    >{{ buttonText }}</outline-button>
    <paper-modal
        ref="selectionModal"
        size="xl"
        :scrollable="true"
        :escDismiss="false"
        :backdropDismiss="false"
        :closeBtn="false"
    >
        <template #title>{{ buttonText }} - 选择您要导入的工作簿</template>
        <template #body>
            <Tab keyColumn="id" v-model="tab.selected" :list="tab.list"></Tab>
            <paper-table
                :header="modelValue.header"
                :status="selectionTableStatus"
                v-model="modelValue.rows"
            ></paper-table>
        </template>
        <template #footer>
            <outline-button color="red" icon="none" @click="cancel">取消</outline-button>
            <outline-button color="green" icon="check-lg" @click="confirm">确认</outline-button>
        </template>
    </paper-modal>
</template>

<script>
import { reactive, ref, computed, watch } from "vue";
import OutlineButton from "../OutlineButton.vue";
import PaperTable from "../PaperTable/PaperTable.vue";
import state from "../PaperTable/Constants.js";
import Tab from "../Tab/Tab.vue";
import helper from "../Excel/ExcelHelper.js";
import PaperModal from "../PaperModal.vue";

export default {
    name: "ExcelImporter",
    props: {
        modelValue: Object,
    },
    setup(props, context) {
        let selectedFile = ref(null),
            ifFormatOk = ref(true),
            ifFileEmpty = ref(false),
            allSheetsData = ref([]),
            tableChosen = ref(false),
            selectionTableStatus = ref(state.NORMAL);
        const buttonTextHelper = {
            INITIAL: "导入用户",
            SELECTED: filename => filename,
            WRONG_FORMAT: "仅支持xls/xlsx格式的文件",
            EMPTY: "文件无数据"
        }
        let buttonText = computed(() => {
            if (selectedFile.value == null)
                return buttonTextHelper.INITIAL;
            if (ifFormatOk.value) {
                if (ifFileEmpty.value)
                    return buttonTextHelper.EMPTY;
                return buttonTextHelper.SELECTED(selectedFile.value.name);
            }
            return buttonTextHelper.WRONG_FORMAT;
        });
        // $refs
        const uploader = ref(null), selectionModal = ref(null);
        // return true for format is correct or file object is empty, false otherwise
        let checkFormat = (fileObject) => {
            if (fileObject) {
                if (/\.(xls|xlsx)$/i.test(fileObject.name)) return true;
                return false;
            }
            return true;
        }
        // open file selection dialog
        let callSelect = () => {
            uploader.value.click();
        }
        let tab = reactive({ selected: null, list: [] });
        // call when file selected
        let onFileSelected = async () => {
            if (uploader.value.files == null || uploader.value.files.length === 0) return;
            selectionTableStatus.value = state.LOADING;
            selectedFile.value = uploader.value.files[0];
            ifFormatOk.value = checkFormat(selectedFile.value);
            if (!ifFormatOk.value)
                return;
            tableChosen.value = false;
            allSheetsData.value = await helper.convertExcelFileObjectToXslxJson(selectedFile.value);
            if (allSheetsData.value.length > 0) {
                tab.list = allSheetsData.value.map((sheet, index) => {
                    return {
                        id: index,
                        text: sheet.name
                    }
                });
                tab.selected = tab.list[0];
                selectionTableStatus.value = state.NORMAL;
                selectionModal.value.show();
                ifFileEmpty.value = false;
            } else {
                props.modelValue.header = [];
                props.modelValue.rows = [];
                ifFileEmpty.value = true;
            }
        }

        watch(() => tab.selected, newTab => {
            selectionTableStatus.value = state.LOADING;
            setTimeout(() => {
                props.modelValue.header = allSheetsData.value[newTab.id].header;
                props.modelValue.rows = allSheetsData.value[newTab.id].rows;
                selectionTableStatus.value = state.NORMAL;
            }, 100);
        })

        function modalClose() {
            selectionModal.value.hide();
            uploader.value.value = null;
            selectedFile.value = null;
            context.emit('cancelImport');
        }

        let confirm = () => {
            tableChosen.value = true;
            modalClose();
            context.emit('confirmImport');
        }

        let cancel = () => {
            props.modelValue.header = [];
            props.modelValue.rows = [];
            modalClose();
        }

        return {
            // data
            selectedFile,
            ifFormatOk,
            ifFileEmpty,
            tableChosen,
            tab,
            selectionTableStatus,
            // constant, initialize at created lifecycle hook
            buttonTextHelper,
            // computed
            buttonText,
            // $refs
            uploader,
            selectionModal,
            // methods
            checkFormat,
            callSelect,
            onFileSelected,
            confirm,
            cancel
        }
    },
    emits: ["update:modelValue", "cancelImport", "confirmImport"],
    components: {
        PaperTable,
        OutlineButton,
        Tab,
        PaperModal
    }
}
</script>