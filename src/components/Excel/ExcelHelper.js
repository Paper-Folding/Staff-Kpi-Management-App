import * as xlsx from "xlsx";
import Maid from "../../utils/Maid";

let excelHelper = {
    convertExcelFileObjectToXslxJson(fileObject) {
        return new Promise(resolve => {
            const reader = new FileReader();
            reader.onload = (e) => {
                const xlsxData = xlsx.read(new Uint8Array(e.target.result), { type: 'array', cellText: false, cellDates: true });
                let result = [];
                for (let sheetname of xlsxData.SheetNames) {
                    const sheet = xlsx.utils.sheet_to_json(xlsxData.Sheets[sheetname], { header: 1, raw: false, dateNF: 'yyyy-mm-dd HH:mm:ss' });
                    if (sheet == null || sheet.length === 0)
                        continue;
                    result.push({
                        name: sheetname,
                        header: sheet[0].filter(ele => ele),
                        rows: this.formatXlsxJsonToTableJson(sheet)
                    });
                }
                for (let item of result) {
                    if (item.header.length > 0)
                        resolve(result);
                }
                resolve([]);
            }
            reader.readAsArrayBuffer(fileObject);
        })
    },
    /**
     * this function will convert sheet json extracted from sheetjs to normal json table data,
     * in other words, it will convert
     * [ [ "id", "name" ], [ 1, "Ling" ], [ 2, "Tianyi" ], [ 3, "Paper" ] ]
     * to
     * [ { "id":1, "name": "Ling" }, { "id": 2, "name": "Tianyi" }, { "id": 3, "name": "Paper" } ]
     * Note that it can be only applied to one sheet.
     */
    formatXlsxJsonToTableJson(source) {
        let keys = source[0].filter(ele => ele), colCount = keys.length, result = [];
        source.forEach((row, index) => {
            if (index === 0 || row.length === 0)
                return; // continue
            let rowObj = {}, rowColCount = row.length;
            for (let i = 0; i < colCount; i++)
                rowObj[keys[i]] = i >= rowColCount || row[i] == null || row[i].trim() === "" ? null : row[i];
            result.push(rowObj);
        });
        return result;
    },
    /**
     * this function does opposite chore as the one above,
     * in other words, it will convert
     * [ { "id":1, "name": "Ling" }, { "id": 2, "name": "Tianyi" }, { "id": 3, "name": "Paper" } ]
     * to
     * [ [ "id", "name" ], [ 1, "Ling" ], [ 2, "Tianyi" ], [ 3, "Paper" ] ]
     * 
     * or
     * { "id":1, "name": "Ling" }
     * to
     * [ [ "id", "name" ], [ 1, "Ling" ] ]
     * Note that it can be only applied to one table.
     * 
     * @param source sample: [ { "id":1, "name": "Ling" }, { "id": 2, "name": "Tianyi" }, { "id": 3, "name": "Paper" } ]
     * @param header included fields, if you do not provide, I will call Maid.keys to generate fields
     * @param ignoreFields excluded fields
     */
    formatTableJsonToXlsxJson(source, header = [], ...ignoreFields) {
        let result = [];
        if (Array.isArray(source) && source.length === 0)
            return result;
        let temp = [];
        if (typeof source === 'object' && !Array.isArray(source))
            temp.push(source);
        else
            temp = source;
        if (header.length > 0)
            result.push(header.filter(ele => !ignoreFields.includes(ele)));
        else
            result.push(Maid.keys(temp).filter(ele => !ignoreFields.includes(ele)));
        temp.forEach(ele => {
            let arr = [];
            for (let key of result[0]) {
                if (ignoreFields.includes(key))
                    continue;
                arr.push(ele[key] ?? '');
            }
            result.push(arr);
        });
        return result;
    },
    createNewWorkbook(props, xlsxJson, format = 'xlsx') {
        let workbook = xlsx.utils.book_new();
        workbook.Props = props;
        workbook.SheetNames.push("default");
        let sheet = xlsx.utils.aoa_to_sheet(xlsxJson);
        workbook.Sheets["default"] = sheet;
        return workbook;
    },
    convertWorkbookToBlob(workbook, format = 'xlsx') {
        let output = xlsx.write(workbook, { bookType: format, type: 'binary' }), length = output.length;
        let buffer = new ArrayBuffer(length), uintArr = new Uint8Array(buffer);
        for (let i = 0; i < length; i++)
            uintArr[i] = output.charCodeAt(i) & 0xFF;
        return new Blob([buffer], { type: "application/octet-stream" });
    },
    /**
     * @param blob
     * @param filename extension should also be provided
     */
    saveBlobAs: (() => {
        let a = document.createElement("a");
        a.style.display = "none";
        document.body.appendChild(a);
        return (blob, filename) => {
            let url = window.URL.createObjectURL(blob);
            a.href = url;
            a.download = filename;
            a.click();
            window.URL.revokeObjectURL(url);
        }
    })(),
    /**
     * pass in 
     * targetArr: [{a: 1, b: 2}]
     * template: {g:'a', h:'b'}
     * 
     * @returns
     * [{g: 1, h: 2}]
     */
    replaceKeyForTableJson(targetArr, template) {
        let result = [];
        for (let item of targetArr) {
            let temp = {};
            for (let key in template) {
                temp[key] = item[template[key]];
            }
            result.push(temp);
        }
        return result;
    }
}

export default excelHelper;