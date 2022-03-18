package ndky.paper.kpimgrapp.Models;

import java.util.Date;

/**
 * staff info model
 * many fields contained, Mama Mia~
 */
public class StaffInfo {
    private Long id;
    private String no, name;
    private String gender;
    private String nation;
    private Date birth, enrollTime;
    private String politic, major, level, levelUnit;
    private Date levelDate;
    private String jobAlias, researchDirection, job, department, idcard, phone, longPhone, shortPhone;

    public StaffInfo() {
    }

    public StaffInfo(Long id, String no, String name, String gender, String nation, Date birth, Date enrollTime, String politic, String major, String level, String levelUnit, Date levelDate, String jobAlias, String researchDirection, String job, String department, String idcard, String phone, String longPhone, String shortPhone) {
        this.id = id;
        this.no = no;
        this.name = name;
        this.gender = gender;
        this.nation = nation;
        this.birth = birth;
        this.enrollTime = enrollTime;
        this.politic = politic;
        this.major = major;
        this.level = level;
        this.levelUnit = levelUnit;
        this.levelDate = levelDate;
        this.jobAlias = jobAlias;
        this.researchDirection = researchDirection;
        this.job = job;
        this.department = department;
        this.idcard = idcard;
        this.phone = phone;
        this.longPhone = longPhone;
        this.shortPhone = shortPhone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public Date getEnrollTime() {
        return enrollTime;
    }

    public void setEnrollTime(Date enrollTime) {
        this.enrollTime = enrollTime;
    }

    public String getPolitic() {
        return politic;
    }

    public void setPolitic(String politic) {
        this.politic = politic;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getLevelUnit() {
        return levelUnit;
    }

    public void setLevelUnit(String levelUnit) {
        this.levelUnit = levelUnit;
    }

    public Date getLevelDate() {
        return levelDate;
    }

    public void setLevelDate(Date levelDate) {
        this.levelDate = levelDate;
    }

    public String getJobAlias() {
        return jobAlias;
    }

    public void setJobAlias(String jobAlias) {
        this.jobAlias = jobAlias;
    }

    public String getResearchDirection() {
        return researchDirection;
    }

    public void setResearchDirection(String researchDirection) {
        this.researchDirection = researchDirection;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLongPhone() {
        return longPhone;
    }

    public void setLongPhone(String longPhone) {
        this.longPhone = longPhone;
    }

    public String getShortPhone() {
        return shortPhone;
    }

    public void setShortPhone(String shortPhone) {
        this.shortPhone = shortPhone;
    }
}
