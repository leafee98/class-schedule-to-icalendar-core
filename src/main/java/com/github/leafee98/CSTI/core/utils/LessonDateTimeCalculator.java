package com.github.leafee98.CSTI.core.utils;

import com.github.leafee98.CSTI.core.bean.Configure;
import com.github.leafee98.CSTI.core.bean.Lesson;
import com.github.leafee98.CSTI.core.bean.LessonRanges;
import com.github.leafee98.CSTI.core.bean.LessonSchedule;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LessonDateTimeCalculator {

    private LessonRanges ranges;
    private int firstDayOfWeek;
    private LocalDate semesterStartDate;

    public LessonDateTimeCalculator(LocalDate semesterStartDate, int firstDayOfWeek, LessonRanges ranges) {
        this.firstDayOfWeek = firstDayOfWeek;
        this.semesterStartDate = semesterStartDate;
        this.ranges = ranges;
    }

    public LessonDateTimeCalculator(Configure configure) {
        this(configure.getSemesterStartDate(), configure.getFirstDayOfWeek(), configure.getLessonRanges());
    }

    public List<LocalDateTimeRange> cal(Lesson lesson) {
        return cal(lesson.getSchedule());
    }

    public List<LocalDateTimeRange> cal(List<LessonSchedule> schedules) {
        List<LocalDateTimeRange> result = new ArrayList<>();

        for (LessonSchedule schedule : schedules) {
            for (int week : schedule.getWeeks()) {
                LocalDate firstDay = semesterStartDate.plusDays((week - 1) * 7);
                for (int day : schedule.getDayOfWeek()) {
                    LocalDate lessonDay = firstDay.plusDays(calDayOffset(day));

                    for (PairNumber pair : RangeNumber.renderToPair(schedule.getLessons())) {
                        LocalTime from = ranges.getRange(pair.getFirst()).from;
                        LocalTime to = ranges.getRange(pair.getSecond()).to;

                        LocalDateTimeRange dateTimeRange = new LocalDateTimeRange(
                                LocalDateTime.of(lessonDay, from),
                                LocalDateTime.of(lessonDay, to)
                        );
                        result.add(dateTimeRange);
                    }
                }
            }
        }
        return result;
    }

    private int calDayOffset(int day) {
        return (day + 7 - firstDayOfWeek) % 7;
    }

}
