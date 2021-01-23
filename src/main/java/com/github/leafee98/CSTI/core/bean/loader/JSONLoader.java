package com.github.leafee98.CSTI.core.bean.loader;

import com.github.leafee98.CSTI.core.bean.*;
import com.github.leafee98.CSTI.core.configure.KeyWords;
import org.json.*;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JSONLoader {
    private final SmallPropertyLoader smallPropertyLoader = new SmallPropertyLoader();

    public ScheduleObject load(String str) {
        ScheduleObject result = new ScheduleObject();

        JSONObject jsonObject = new JSONObject(str);

        Configure configure = this.loadConfigure(jsonObject.getJSONObject(KeyWords.global));
        List<Lesson> lessons = this.loadLessons(jsonObject.getJSONArray(KeyWords.lessons));

        result.setConfigure(configure);
        result.setLessons(lessons);

        return result;
    }

    public Configure loadConfigure(String str) {
        JSONObject configure = new JSONObject(str);
        return this.loadConfigure(configure);
    }

    public Configure loadConfigure(JSONObject configure) {
        Configure result = new Configure();

        // assign default value, two properties has no default value.
        result.setEventSummaryFormat("${lessonName}-${location}");
        result.setEventDescriptionFormat(
                "name:${lessonName}\n" +
                "location:${location}\n" +
                "teacher:${teacher}\n" +
                "type:${lessonType}\n" +
                "remark:${remark}\n" +
                "schedule:${scheduleFull}");
        result.setTimezone(ZoneId.of("Asia/Shanghai"));
        result.setFirstDayOfWeek(1);
        // result.setSemesterStartDate();
        result.setReminderTime(Collections.singletonList("-15m"));
        // result.setLessonRanges();

        // load value from json
        if (configure.has(KeyWords.eventSummaryFormat))
            result.setEventSummaryFormat(configure.getString(KeyWords.eventSummaryFormat));
        if (configure.has(KeyWords.eventDescriptionFormat))
            result.setEventDescriptionFormat(configure.getString(KeyWords.eventDescriptionFormat));


        // load timezone
        if (configure.has(KeyWords.timezone)) {
            String timezone = configure.getString(KeyWords.timezone);
            result.setTimezone(smallPropertyLoader.loadTimezone(timezone));
        }

        // load first-day-of-week
        if (configure.has(KeyWords.firstDayOfWeek))
            result.setFirstDayOfWeek(configure.getInt(KeyWords.firstDayOfWeek));

        // load semester-start-date
        if (configure.has(KeyWords.semesterStartDate)) {
            String semesterStartDate = configure.getString(KeyWords.semesterStartDate);
            result.setSemesterStartDate(smallPropertyLoader.loadSemesterStartDate(semesterStartDate));
        }

        // load reminder-time
        if (configure.has(KeyWords.reminderTime)) {
            JSONArray jsonArray = configure.getJSONArray(KeyWords.reminderTime);
            ArrayList<String> reminderTime = new ArrayList<>(jsonArray.length());
            for (int i = 0; i < jsonArray.length(); ++i)
                reminderTime.add(jsonArray.getString(i));
            result.setReminderTime(reminderTime);
        }

        // load lesson-ranges by other methods.
        if (configure.has(KeyWords.lessonRanges)) {
            JSONArray jsonArray = configure.getJSONArray(KeyWords.lessonRanges);
            LessonRanges lessonRanges = this.loadLessonRanges(jsonArray);
            result.setLessonRanges(lessonRanges);
        }

        return result;
    }

    public LessonRanges loadLessonRanges(String str) {
        return loadLessonRanges(new JSONArray(str));
    }

    public LessonRanges loadLessonRanges(JSONArray jsonArray) {
        ArrayList<String> strList = new ArrayList<>(jsonArray.length());
        for (int i = 0; i < jsonArray.length(); ++i)
            strList.add(i, jsonArray.getString(i));
        return smallPropertyLoader.loadLessonRanges(strList);
    }

    public List<Lesson> loadLessons(JSONArray jsonArray) {
        ArrayList<Lesson> result = new ArrayList<>(jsonArray.length());

        for (int i = 0; i < jsonArray.length(); ++i) {
            result.add(this.loadLesson(jsonArray.getJSONObject(i)));
        }

        return result;
    }

    public Lesson loadLesson(JSONObject jsonObject) {
        Lesson result = new Lesson();

        // assign default value
        result.setName("default name");
        result.setType("default type");
        result.setTeacher("default teacher");
        result.setLocation("default location");
        result.setRemark("no remark");
        result.setSchedule(new ArrayList<>());

        if (jsonObject.has(KeyWords.lessonName))
            result.setName(jsonObject.getString(KeyWords.lessonName));

        if (jsonObject.has(KeyWords.lessonType))
            result.setType(jsonObject.getString(KeyWords.lessonType));

        if (jsonObject.has(KeyWords.lessonTeacher))
            result.setTeacher(jsonObject.getString(KeyWords.lessonTeacher));

        if (jsonObject.has(KeyWords.lessonLocation))
            result.setLocation(jsonObject.getString(KeyWords.lessonLocation));

        if (jsonObject.has(KeyWords.lessonRemark))
            result.setRemark(jsonObject.getString(KeyWords.lessonRemark));

        if (jsonObject.has(KeyWords.lessonSchedule)) {
            JSONArray jsonArray = jsonObject.getJSONArray(KeyWords.lessonSchedule);

            List<String> stringList = new ArrayList<>(jsonArray.length());
            for (int i = 0; i < jsonArray.length(); ++i) {
                stringList.add(jsonArray.getString(i));
            }

            List<LessonSchedule> lessonSchedule = smallPropertyLoader.loadLessonSchedule(stringList);
            result.setSchedule(lessonSchedule);
        }

        return result;
    }
}

