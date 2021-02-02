package com.github.leafee98.CSTI.core.bean.loader.builder;

import com.github.leafee98.CSTI.core.bean.Configure;
import com.github.leafee98.CSTI.core.bean.Lesson;
import com.github.leafee98.CSTI.core.bean.ScheduleObject;

import java.util.ArrayList;
import java.util.List;

public class GenericScheduleObjectBuilder {
    private final ScheduleObject scheduleObject;

    public GenericScheduleObjectBuilder() {
        scheduleObject = new ScheduleObject();
    }

    public ScheduleObject build() {
        return scheduleObject;
    }

    public void setConfigure(Configure conf) {
        scheduleObject.setConfigure(conf);
    }

    public void addLesson(Lesson lesson) {
        if (scheduleObject.getLessons() == null)
            scheduleObject.setLessons(new ArrayList<>());
        scheduleObject.getLessons().add(lesson);
    }

    public void setLessons(List<Lesson> lessons) {
        scheduleObject.setLessons(lessons);
    }
}
