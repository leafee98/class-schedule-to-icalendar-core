package com.github.leafee98.CSTI.core.bean;

import com.github.leafee98.CSTI.core.configure.KeyWords;
import com.github.leafee98.CSTI.core.exceptions.InvalidScheduleFileStruct;

import java.util.ArrayList;
import java.util.List;

public class ScheduleObject {

    private List<Lesson> lessons;
    private Configure configure;

    ScheduleObject() {
    }

    public static ScheduleObject load(String str) {
        ScheduleObject result = new ScheduleObject();

        int confBegin = str.indexOf(KeyWords.confBegin);
        int confEnd = str.indexOf(KeyWords.confEnd);

        if( confBegin < 0 || confEnd < 0 || confBegin > confEnd
                || confBegin != str.lastIndexOf(KeyWords.confBegin)
                || confEnd != str.lastIndexOf(KeyWords.confEnd)) {
            throw new InvalidScheduleFileStruct(String.format("'%s' and '%s' must occur in order.",
                    KeyWords.confBegin, KeyWords.confEnd));
        }


        List<Lesson> lessons = new ArrayList<>();
        int lessonBegin = str.indexOf(KeyWords.lessonBegin);
        int lessonEnd = str.indexOf(KeyWords.lessonEnd);
        while (lessonBegin > 0) {
            if (lessonBegin > lessonEnd) {
                throw new InvalidScheduleFileStruct(String.format("No '%s' after '%s', or '%s' is in front of '%s'\n" +
                                "Take care that they must correspond with each other.",
                        KeyWords.lessonEnd, KeyWords.lessonBegin,
                        KeyWords.lessonBegin, KeyWords.lessonEnd));
            }

            lessons.add(Lesson.load(str.substring(lessonBegin + KeyWords.lessonBegin.length() + 1, lessonEnd)));

            lessonBegin = str.indexOf(KeyWords.lessonBegin, lessonBegin + KeyWords.lessonBegin.length());
            lessonEnd = str.indexOf(KeyWords.lessonEnd, lessonEnd + KeyWords.lessonEnd.length());
        }

        result.setConfigure(Configure.load(str.substring(confBegin + KeyWords.confBegin.length() + 1, confEnd)));
        result.setLessons(lessons);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(KeyWords.confBegin + '\n'
                + this.configure.toString() + '\n'
                + KeyWords.confEnd + '\n');
        for (Lesson lesson : lessons) {
            builder.append(KeyWords.lessonBegin);
            builder.append('\n');
            builder.append(lesson.toString());
            builder.append('\n');
            builder.append(KeyWords.lessonEnd);
            builder.append('\n');
        }

        return builder.toString();
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }

    public Configure getConfigure() {
        return configure;
    }

    public void setConfigure(Configure configure) {
        this.configure = configure;
    }

}
