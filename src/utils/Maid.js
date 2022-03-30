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
}