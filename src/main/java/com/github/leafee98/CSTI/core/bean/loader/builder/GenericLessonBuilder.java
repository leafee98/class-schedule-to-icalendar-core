package com.github.leafee98.CSTI.core.bean.loader.builder;

import com.github.leafee98.CSTI.core.bean.Lesson;
import com.github.leafee98.CSTI.core.bean.LessonSchedule;
import com.github.leafee98.CSTI.core.exceptions.InvalidLessonSchedule;
import com.github.leafee98.CSTI.core.utils.RangeNumber;

import java.util.ArrayList;
import java.util.List;

public class GenericLessonBuilder {
    private final Lesson lesson;

    public GenericLessonBuilder() {
        lesson = new Lesson();

        // assign default value
        lesson.setName("default name");
        lesson.setType("default type");
        lesson.setTeacher("default teacher");
        lesson.setLocation("default location");
        lesson.setRemark("no remark");
        lesson.setSchedule(new ArrayList<>());
    }

    public Lesson build() {
        return lesson;
    }

    public void setName(String str) {
        lesson.setName(str);
    }

    public void setType(String str) {
        lesson.setType(str);
    }

    public void setTeacher(String str) {
        lesson.setTeacher(str);
    }

    public void setLocation(String str) {
        lesson.setLocation(str);
    }

    public void setRemark(String str) {
        lesson.setRemark(str);
    }

    public void setSchedule(List<String> strList) {
        lesson.setSchedule(loadSchedule(strList));
    }

    private List<LessonSchedule> loadSchedule(List<String> schedules) {
        List<LessonSchedule> result = new ArrayList<>();

        for (String scheduleStr : schedules) {
            scheduleStr = scheduleStr.trim();
            if (scheduleStr.length() == 0)
                continue;

            String[] components = scheduleStr.split("\\|");

            if (components.length != 3)
                throw new InvalidLessonSchedule("there should be 3 components (weeks, dayOfWeek, lessons)" +
                        " in a schedule description: " + scheduleStr);

            for (int i = 0; i < 3; ++i)
                components[i] = components[i].trim();

            LessonSchedule schedule = new LessonSchedule();
            schedule.setWeeks(RangeNumber.parse(components[0]));
            schedule.setDayOfWeek(RangeNumber.parse(components[1]));
            schedule.setLessons(RangeNumber.parse(components[2]));
            result.add(schedule);
        }

        return result;
    }

}
