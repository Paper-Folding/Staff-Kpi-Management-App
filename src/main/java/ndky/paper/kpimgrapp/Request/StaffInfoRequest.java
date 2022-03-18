package ndky.paper.kpimgrapp.Request;

import ndky.paper.kpimgrapp.Models.StaffInfo;

import java.util.Date;

public class StaffInfoRequest extends StaffInfo {
    // page is start from 1
    private Integer page;
    // count decides how many records should be returned
    private Integer count;

    public StaffInfoRequest() {
    }

    public StaffInfoRequest(Long id, String no, String name, String gender, String nation, Date birth, Date enrollTime, String politic, String major, String level, String levelUnit, Date levelDate, String jobAlias, String researchDirection, String job, String department, String idcard, String phone, String longPhone, String shortPhone, Integer page, Integer count) {
        super(id, no, name, gender, nation, birth, enrollTime, politic, major, level, levelUnit, levelDate, jobAlias, researchDirection, job, department, idcard, phone, longPhone, shortPhone);
        this.page = page;
        this.count = count;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
