package com.github.leafee98.CSTI.core.bean;

import com.github.leafee98.CSTI.core.configure.KeyWords;

import java.util.List;
import java.util.Objects;

public class Lesson {

    private String name;
    private String type;
    private String teacher;
    private String location;
    private String remark;
    private List<LessonSchedule> schedule;

    public static Lesson load(String scheduleStr) {
        Lesson result = new Lesson();
        String[] lines = scheduleStr.split("\n");

        for (String line : lines) {
            if (line.startsWith(KeyWords.lessonName)) {
                result.setName(line.substring(KeyWords.lessonName.length() + 1).trim());
            } else if (line.startsWith(KeyWords.lessonType)) {
                result.setType(line.substring(KeyWords.lessonType.length() + 1).trim());
            } else if (line.startsWith(KeyWords.lessonTeacher)) {
                result.setTeacher(line.substring(KeyWords.lessonTeacher.length() + 1).trim());
            } else if (line.startsWith(KeyWords.lessonLocation)) {
                result.setLocation(line.substring(KeyWords.lessonLocation.length() + 1).trim());
            } else if (line.startsWith(KeyWords.lessonRemark)) {
                result.setRemark(line.substring(KeyWords.lessonRemark.length() + 1).trim());
            } else if (line.startsWith(KeyWords.lessonSchedule)) {
                result.setSchedule(LessonSchedule.load(line.substring(KeyWords.lessonSchedule.length() + 1)));
            }
        }

        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(
                KeyWords.lessonName + ':' + name + '\n'
                + KeyWords.lessonType + ':' + type + '\n'
                + KeyWords.lessonTeacher + ':' + teacher + '\n'
                + KeyWords.lessonLocation + ':' + location + '\n'
                + KeyWords.lessonRemark + ':' + remark + '\n'
                + KeyWords.lessonSchedule + ':'
        );

        for (LessonSchedule s : schedule)
            builder.append(s);

        return builder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Lesson)) return false;
        Lesson lesson = (Lesson) o;
        return Objects.equals(getName(), lesson.getName()) &&
                Objects.equals(getType(), lesson.getType()) &&
                Objects.equals(getTeacher(), lesson.getTeacher()) &&
                Objects.equals(getRemark(), lesson.getRemark()) &&
                Objects.equals(getSchedule(), lesson.getSchedule());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getType(), getTeacher(), getRemark(), getSchedule());
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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
