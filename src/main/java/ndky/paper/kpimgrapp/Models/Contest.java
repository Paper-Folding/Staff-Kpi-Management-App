package ndky.paper.kpimgrapp.Models;

public class Contest {
    private Long id;
    private String no, type, name, students;
    private Long tutorStaffInfoId;
    private String tutorNo, tutorName;
    private String prize, level, awardTime, certificate, addTime;
    private Long addStaffInfoId;
    private String adderNo, adderName;

    public Contest() {
    }

    public Contest(Long id, String no, String type, String name, String students, Long tutorStaffInfoId, String tutorNo, String tutorName, String prize, String level, String awardTime, String certificate, String addTime, Long addStaffInfoId, String adderNo, String adderName) {
        this.id = id;
        this.no = no;
        this.type = type;
        this.name = name;
        this.students = students;
        this.tutorStaffInfoId = tutorStaffInfoId;
        this.tutorNo = tutorNo;
        this.tutorName = tutorName;
        this.prize = prize;
        this.level = level;
        this.awardTime = awardTime;
        this.certificate = certificate;
        this.addTime = addTime;
        this.addStaffInfoId = addStaffInfoId;
        this.adderNo = adderNo;
        this.adderName = adderName;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStudents() {
        return students;
    }

    public void setStudents(String students) {
        this.students = students;
    }

    public Long getTutorStaffInfoId() {
        return tutorStaffInfoId;
    }

    public void setTutorStaffInfoId(Long tutorStaffInfoId) {
        this.tutorStaffInfoId = tutorStaffInfoId;
    }

    public String getTutorNo() {
        return tutorNo;
    }

    public void setTutorNo(String tutorNo) {
        this.tutorNo = tutorNo;
    }

    public String getTutorName() {
        return tutorName;
    }

    public void setTutorName(String tutorName) {
        this.tutorName = tutorName;
    }

    public String getPrize() {
        return prize;
    }

    public void setPrize(String prize) {
        this.prize = prize;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getAwardTime() {
        return awardTime;
    }

    public void setAwardTime(String awardTime) {
        this.awardTime = awardTime;
    }

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public Long getAddStaffInfoId() {
        return addStaffInfoId;
    }

    public void setAddStaffInfoId(Long addStaffInfoId) {
        this.addStaffInfoId = addStaffInfoId;
    }

    public String getAdderNo() {
        return adderNo;
    }

    public void setAdderNo(String adderNo) {
        this.adderNo = adderNo;
    }

    public String getAdderName() {
        return adderName;
    }

    public void setAdderName(String adderName) {
        this.adderName = adderName;
    }
}
