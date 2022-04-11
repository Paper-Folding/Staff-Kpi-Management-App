import formateDate from "../components/PaperTable/TimeUtil.js";
import store from "../store";

export default class Maid {
    static formatDate = formateDate;

    static compareTime(time1, time2) {
        let t1 = time1, t2 = time2;
        if (typeof time1 === 'string')
            t1 = new Date(time1);
        if (typeof time2 === 'string')
            t2 = new Date(time2);
        return t1.getTime() - t2.getTime();
    }

    static truncateString(targetStr, size) {
        if (size < 4 && targetStr.length > 3)
            return '...';
        return targetStr.length <= size ? targetStr : targetStr.substring(0, size - 3) + '...';
    }

    /**
     * a safer and evo version of Object.keys and also can be a Array.keys
     * @param target an object or an array of objects
     * @returns e.g. target = [{x:1, y:2}, {z:1}, {x:8}] ===> ['x','y','z']
     */
    static keys(target) {
        if (target == null || (typeof target !== 'object'))
            return [];
        if (Array.isArray(target)) {
            let temp = {};
            for (let item of target)
                Object.assign(temp, item);
            return Object.keys(temp);
        }
        return Object.keys(target);
    }

    /**
     * Check from permissionList that if a specific operation/permission is meeted
     * 
     * @param operationName 'select', 'insert' and so on
     * @param columnName default is '*' 
     * @returns true if meeted while false otherwise
     */
    static permissionMeeted(operationName, columnName = '*') {
        const pList = store.state.Util.permissionList;
        if (pList === '*')
            return true;
        return pList.filter(ele => ele.operationName === operationName && ele.columnName === columnName).length > 0;
    }

    // call global notify component directly
    static notify = store.state.notify;
}