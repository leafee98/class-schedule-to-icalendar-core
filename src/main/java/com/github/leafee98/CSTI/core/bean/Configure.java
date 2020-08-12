package com.github.leafee98.CSTI.core.bean;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.github.leafee98.CSTI.core.configure.KeyWords;

public class Configure {

    private String eventPrefix;
    private String timezone;
    private int firstDayOfWeek;
    private LocalDate semesterStartDate;
    private LessonRanges lessonRanges;

    public static Configure load(String str) {
        Configure result = new Configure();

        String[] lines = str.split("\n");
        for (int i = 0; i < lines.length; ++i)
            lines[i] = lines[i].trim();

        for (String line : lines) {
            if (line.startsWith(KeyWords.eventPrefix)) {
                // keep white spaces of eventPrefix
                result.setEventPrefix(line.substring(KeyWords.eventPrefix.length() + 1));
            } else if (line.startsWith(KeyWords.firstDayOfWeek)) {
                result.setFirstDayOfWeek(
                        Integer.parseInt(line.substring(KeyWords.firstDayOfWeek.length() + 1).trim()));
            } else if (line.startsWith(KeyWords.semesterStartDate)) {
                result.setSemesterStartDate(
                        LocalDate.parse(line.substring(KeyWords.semesterStartDate.length() + 1).trim()));
            } else if (line.startsWith(KeyWords.timezone)) {
                result.setTimezone(line.substring(KeyWords.timezone.length() + 1).trim());
            } else if (line.startsWith(KeyWords.lessonRanges)) {
                String remain = line.substring(KeyWords.lessonRanges.length() + 1).trim();
                result.setLessonRanges(LessonRanges.load(remain));
            }
        }

        return result;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return KeyWords.eventPrefix + ":" + getEventPrefix() + '\n'
                + KeyWords.timezone + ':' + getTimezone() + '\n'
                + KeyWords.firstDayOfWeek + ':' + getFirstDayOfWeek() + '\n'
                + KeyWords.semesterStartDate + ':' + getSemesterStartDate().format(formatter) + '\n'
                + KeyWords.lessonRanges + ':' + getLessonRanges().toString();

    }

    public String getEventPrefix() {
        return eventPrefix;
    }

    public void setEventPrefix(String eventPrefix) {
        this.eventPrefix = eventPrefix;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public int getFirstDayOfWeek() {
        return firstDayOfWeek;
    }

    public void setFirstDayOfWeek(int firstDayOfWeek) {
        this.firstDayOfWeek = firstDayOfWeek;
    }

    public LocalDate getSemesterStartDate() {
        return semesterStartDate;
    }

    public void setSemesterStartDate(LocalDate semesterStartDate) {
        this.semesterStartDate = semesterStartDate;
    }

    public LessonRanges getLessonRanges() {
        return lessonRanges;
    }

    public void setLessonRanges(LessonRanges lessonRanges) {
        this.lessonRanges = lessonRanges;
    }

}
