function formatDate(date, format = "YYYY-MM-DD hh:mm:ss") {
    let _date;
    if (typeof date === 'string')
        _date = new Date(date);
    else
        _date = date;
    let ret;
    const opt = {
        "Y+": _date.getFullYear().toString(),        // 年
        "M+": (_date.getMonth() + 1).toString(),     // 月
        "D+": _date.getDate().toString(),            // 日
        "h+": _date.getHours().toString(),           // 时
        "m+": _date.getMinutes().toString(),         // 分
        "s+": _date.getSeconds().toString()          // 秒
    };
    for (let k in opt) {
        ret = new RegExp("(" + k + ")").exec(format);
        if (ret) {
            format = format.replace(ret[1], (ret[1].length == 1) ? (opt[k]) : (opt[k].padStart(ret[1].length, "0")))
        }
    }
    return format;
}

export default formatDate;