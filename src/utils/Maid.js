import formateDate from "../components/PaperTable/TimeUtil.js";

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
     * a safer and evo version of Object.keys
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
}