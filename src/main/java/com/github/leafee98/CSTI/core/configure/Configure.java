package com.github.leafee98.CSTI.core.configure;

import java.time.ZoneOffset;
import java.util.List;

import com.github.leafee98.CSTI.core.bean.LessonRanges;

public class Configure {

    private String eventPrefix;
    private ZoneOffset timezone;
    private String firstDayOfWeek;
    private String semesterStartDate;
    private List<LessonRanges> lessonRanges;

    public String getEventPrefix() {
        return eventPrefix;
    }

    public void setEventPrefix(String eventPrefix) {
        this.eventPrefix = eventPrefix;
    }

    public ZoneOffset getTimezone() {
        return timezone;
    }

    public void setTimezone(ZoneOffset timezone) {
        this.timezone = timezone;
    }

    public String getFirstDayOfWeek() {
        return firstDayOfWeek;
    }

    public void setFirstDayOfWeek(String firstDayOfWeek) {
        this.firstDayOfWeek = firstDayOfWeek;
    }

    public String getSemesterStartDate() {
        return semesterStartDate;
    }

    public void setSemesterStartDate(String semesterStartDate) {
        this.semesterStartDate = semesterStartDate;
    }

    public List<LessonRanges> getLessonRanges() {
        return lessonRanges;
    }

    public void setLessonRanges(List<LessonRanges> lessonRanges) {
        this.lessonRanges = lessonRanges;
    }

}
