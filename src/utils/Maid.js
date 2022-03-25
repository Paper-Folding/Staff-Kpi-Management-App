export default class Maid {
    static formatDate(date, format = "YYYY-mm-dd HH:MM:SS") {
        let _date;
        if (typeof date === 'string')
            _date = new Date(date);
        else
            _date = date;
        let ret;
        const opt = {
            "Y+": _date.getFullYear().toString(),        // 年
            "m+": (_date.getMonth() + 1).toString(),     // 月
            "d+": _date.getDate().toString(),            // 日
            "H+": _date.getHours().toString(),           // 时
            "M+": _date.getMinutes().toString(),         // 分
            "S+": _date.getSeconds().toString()          // 秒
        };
        for (let k in opt) {
            ret = new RegExp("(" + k + ")").exec(format);
            if (ret) {
                format = format.replace(ret[1], (ret[1].length == 1) ? (opt[k]) : (opt[k].padStart(ret[1].length, "0")))
            }
        }
        return format;
    }

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