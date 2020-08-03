package com.github.leafee98.CSTI.core.bean;

public class Lesson {

    private String name;
    private String type;
    private String teacher;
    private String remark;
    private LessonRanges schedule;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public LessonRanges getSchedule() {
        return schedule;
    }

    public void setSchedule(LessonRanges schedule) {
        this.schedule = schedule;
    }

}
