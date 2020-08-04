package com.github.leafee98.CSTI.core.bean;

import com.github.leafee98.CSTI.core.configure.KeyWords;

import java.util.List;

public class Lesson {

    private String name;
    private String type;
    private String teacher;
    private String remark;
    private List<LessonSchedule> schedule;

    public static void load(String scheduleStr) {
        Lesson result = new Lesson();
        String[] lines = scheduleStr.split("\n");

        for (String line : lines) {
            if (line.startsWith(KeyWords.lessonName)) {
                result.setName(line.substring(KeyWords.lessonName.length() + 2));
            } else if (line.startsWith(KeyWords.lessonType)) {
                result.setType(line.substring(KeyWords.lessonType.length() + 2));
            } else if (line.startsWith(KeyWords.lessonTeacher)) {
                result.setTeacher(line.substring(KeyWords.lessonTeacher.length() + 2));
            } else if (line.startsWith(KeyWords.lessonRemark)) {
                result.setRemark(line.substring(KeyWords.lessonRemark.length() + 2));
            } else if (line.startsWith(KeyWords.lessonSchedule)) {
                LessonSchedule schedule = new LessonSchedule();
            }
        }
    }

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

    public List<LessonSchedule> getSchedule() {
        return schedule;
    }

    public void setSchedule(List<LessonSchedule> schedule) {
        this.schedule = schedule;
    }

}
