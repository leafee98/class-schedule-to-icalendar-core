package com.github.leafee98.CSTI.core.bean;

import com.github.leafee98.CSTI.core.exceptions.InvalidLessonSchedule;
import com.github.leafee98.CSTI.core.utils.RangeNumber;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LessonSchedule {

    private List<Integer> weeks;
    private List<Integer> dayOfWeek;
    private List<Integer> lessons;

    public static List<LessonSchedule> load(String str) {
        List<LessonSchedule> result = new ArrayList<>();
        String[] schedules = str.split(";");

        for (String scheduleStr : schedules) {
            scheduleStr = scheduleStr.trim();
            if (scheduleStr.length() == 0)
                continue;

            String[] components = scheduleStr.split("\\|");

            if (components.length != 3) {
                throw new InvalidLessonSchedule("there should be 3 components (weeks, dayOfWeek, lessons)" +
                        " in a schedule description: " + scheduleStr);
            }

            LessonSchedule schedule = new LessonSchedule();
            schedule.setWeeks(RangeNumber.parse(components[0]));
            schedule.setDayOfWeek(RangeNumber.parse(components[1]));
            schedule.setLessons(RangeNumber.parse(components[2]));
            result.add(schedule);
        }

        return result;
    }

    @Override
    public String toString() {
        return RangeNumber.render(this.getWeeks()) +
                '|' +
                RangeNumber.render(this.getDayOfWeek()) +
                '|' +
                RangeNumber.render(this.getLessons()) +
                ';';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LessonSchedule)) return false;
        LessonSchedule schedule = (LessonSchedule) o;
        return Objects.equals(getWeeks(), schedule.getWeeks()) &&
                Objects.equals(getDayOfWeek(), schedule.getDayOfWeek()) &&
                Objects.equals(getLessons(), schedule.getLessons());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getWeeks(), getDayOfWeek(), getLessons());
    }

    public List<Integer> getWeeks() {
        return weeks;
    }

    public void setWeeks(List<Integer> weeks) {
        this.weeks = weeks;
    }

    public List<Integer> getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(List<Integer> dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public List<Integer> getLessons() {
        return lessons;
    }

    public void setLessons(List<Integer> lessons) {
        this.lessons = lessons;
    }
}
