package com.github.leafee98.CSTI.core.bean.loader;

import com.github.leafee98.CSTI.core.bean.Configure;
import com.github.leafee98.CSTI.core.bean.Lesson;
import com.github.leafee98.CSTI.core.bean.LessonSchedule;
import com.github.leafee98.CSTI.core.bean.ScheduleObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class JSONLoaderTest {
    private final String jsonResource = "csti-example.json";

    @Test
    public void testLoad() throws IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(jsonResource);
        assert inputStream != null;
        InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        String inputText = new BufferedReader(reader).lines().parallel().collect(Collectors.joining("\n"));

        JSONLoader loader = new JSONLoader();
        ScheduleObject scheduleObject = loader.load(inputText);
        Configure configure = scheduleObject.getConfigure();
        List<Lesson> lessons = scheduleObject.getLessons();
        Lesson lesson = lessons.get(0);

        // Assert global configuration

        Assertions.assertEquals("${lessonName}-${location}", configure.getEventSummaryFormat());
        Assertions.assertEquals("name:${lessonName}\n"
                + "location:${location}\n"
                + "teacher:${teacher}\n"
                + "type:${lessonType}\n"
                + "remark:${remark}\n"
                + "schedule:${scheduleFull}", configure.getEventDescriptionFormat());
        Assertions.assertEquals(ZoneId.of("Asia/Shanghai"), configure.getTimezone());
        Assertions.assertEquals(1, configure.getFirstDayOfWeek());
        Assertions.assertEquals(LocalDate.of(2020, 2, 24), configure.getSemesterStartDate());

        Assertions.assertEquals(1, configure.getReminderTime().size());
        Assertions.assertEquals("-15m", configure.getReminderTime().get(0));

        Assertions.assertEquals(12, configure.getLessonRanges().size());
        Assertions.assertEquals(LocalTime.of(8, 0, 0), configure.getLessonRanges().getRange(1).from);
        Assertions.assertEquals(LocalTime.of(8, 45, 0), configure.getLessonRanges().getRange(1).to);
        Assertions.assertEquals(LocalTime.of(20, 10, 0), configure.getLessonRanges().getRange(12).from);
        Assertions.assertEquals(LocalTime.of(20, 55, 0), configure.getLessonRanges().getRange(12).to);

        // Assert lessons

        Assertions.assertEquals(3, lessons.size());
        Assertions.assertEquals("软件测试技术", lesson.getName());
        Assertions.assertEquals("专业必修", lesson.getType());
        Assertions.assertEquals("某教师", lesson.getTeacher());
        Assertions.assertEquals("某位置", lesson.getLocation());
        Assertions.assertEquals("暂无", lesson.getRemark());

        LessonSchedule schedule = lesson.getSchedule().get(0);
        Assertions.assertEquals(1, lesson.getSchedule().size());
        Assertions.assertEquals(IntStream.range(1, 15).boxed().collect(Collectors.toList()), schedule.getWeeks());
        Assertions.assertEquals(Collections.singletonList(1), schedule.getDayOfWeek());
        Assertions.assertEquals(IntStream.range(6, 10).boxed().collect(Collectors.toList()), schedule.getLessons());
    }
}
