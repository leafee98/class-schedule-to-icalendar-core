package com.github.leafee98.CSTI.core.bean.loader;

import com.github.leafee98.CSTI.core.bean.*;
import com.github.leafee98.CSTI.core.bean.loader.builder.GenericConfigureBuilder;
import com.github.leafee98.CSTI.core.bean.loader.builder.GenericLessonBuilder;
import com.github.leafee98.CSTI.core.bean.loader.builder.GenericScheduleObjectBuilder;
import com.github.leafee98.CSTI.core.configure.KeyWords;
import org.json.*;

import java.util.ArrayList;
import java.util.List;

public class JSONLoader {
    public ScheduleObject load(String str) {
        JSONObject jsonObject = new JSONObject(str);

        Configure configure = this.loadConfigure(jsonObject.getJSONObject(KeyWords.global));
        List<Lesson> lessons = this.loadLessons(jsonObject.getJSONArray(KeyWords.lessons));

        GenericScheduleObjectBuilder builder = new GenericScheduleObjectBuilder();
        builder.setConfigure(configure);
        builder.setLessons(lessons);

        return builder.build();
    }

    public Configure loadConfigure(String str) {
        JSONObject configure = new JSONObject(str);
        return this.loadConfigure(configure);
    }

    public Configure loadConfigure(JSONObject configure) {
        GenericConfigureBuilder builder = new GenericConfigureBuilder();

        // load value from json
        if (configure.has(KeyWords.eventSummaryFormat))
            builder.setEventSummaryFormat(configure.getString(KeyWords.eventSummaryFormat));

        if (configure.has(KeyWords.eventDescriptionFormat))
            builder.setEventDescriptionFormat(configure.getString(KeyWords.eventDescriptionFormat));

        if (configure.has(KeyWords.timezone))
            builder.setTimezone(configure.getString(KeyWords.timezone));

        if (configure.has(KeyWords.firstDayOfWeek))
            builder.setFirstDayOfWeek(configure.getInt(KeyWords.firstDayOfWeek));

        if (configure.has(KeyWords.semesterStartDate))
            builder.setSemesterStartDate(configure.getString(KeyWords.semesterStartDate));

        // load reminder-time
        if (configure.has(KeyWords.reminderTime)) {
            JSONArray jsonArray = configure.getJSONArray(KeyWords.reminderTime);
            ArrayList<String> reminderTime = new ArrayList<>(jsonArray.length());
            for (int i = 0; i < jsonArray.length(); ++i)
                reminderTime.add(jsonArray.getString(i));
            builder.setReminderTime(reminderTime);
        }

        // load lesson-ranges by other methods.
        if (configure.has(KeyWords.lessonRanges)) {
            JSONArray jsonArray = configure.getJSONArray(KeyWords.lessonRanges);
            ArrayList<String> strList = new ArrayList<>(jsonArray.length());
            for (int i = 0; i < jsonArray.length(); ++i)
                strList.add(i, jsonArray.getString(i));
            builder.setLessonRanges(strList);
        }

        return builder.build();
    }

    public List<Lesson> loadLessons(JSONArray jsonArray) {
        ArrayList<Lesson> result = new ArrayList<>(jsonArray.length());

        for (int i = 0; i < jsonArray.length(); ++i) {
            result.add(this.loadLesson(jsonArray.getJSONObject(i)));
        }

        return result;
    }

    public Lesson loadLesson(JSONObject jsonObject) {
        GenericLessonBuilder builder = new GenericLessonBuilder();

        if (jsonObject.has(KeyWords.lessonName))
            builder.setName(jsonObject.getString(KeyWords.lessonName));

        if (jsonObject.has(KeyWords.lessonType))
            builder.setType(jsonObject.getString(KeyWords.lessonType));

        if (jsonObject.has(KeyWords.lessonTeacher))
            builder.setTeacher(jsonObject.getString(KeyWords.lessonTeacher));

        if (jsonObject.has(KeyWords.lessonLocation))
            builder.setLocation(jsonObject.getString(KeyWords.lessonLocation));

        if (jsonObject.has(KeyWords.lessonRemark))
            builder.setRemark(jsonObject.getString(KeyWords.lessonRemark));

        if (jsonObject.has(KeyWords.lessonSchedule)) {
            JSONArray jsonArray = jsonObject.getJSONArray(KeyWords.lessonSchedule);

            List<String> stringList = new ArrayList<>(jsonArray.length());
            for (int i = 0; i < jsonArray.length(); ++i) {
                stringList.add(jsonArray.getString(i));
            }

            builder.setSchedule(stringList);
        }

        return builder.build();
    }
}

