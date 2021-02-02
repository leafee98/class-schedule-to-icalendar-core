package com.github.leafee98.CSTI.core.bean.loader.builder;

import com.github.leafee98.CSTI.core.bean.Configure;
import com.github.leafee98.CSTI.core.bean.LessonRanges;
import com.github.leafee98.CSTI.core.exceptions.InvalidLessonRange;
import com.github.leafee98.CSTI.core.utils.LocalTimeRange;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GenericConfigureBuilder {
    private final Configure configure;

    /**
     * create new Configure and assign default value of Configure
     */
    public GenericConfigureBuilder() {
        configure = new Configure();

        // two properties have no default value
        configure.setEventSummaryFormat("${lessonName}-${location}");
        configure.setEventDescriptionFormat(
                "name:${lessonName}\n" +
                        "location:${location}\n" +
                        "teacher:${teacher}\n" +
                        "type:${lessonType}\n" +
                        "remark:${remark}\n" +
                        "schedule:${scheduleFull}");
        configure.setTimezone(ZoneId.of("Asia/Shanghai"));
        configure.setFirstDayOfWeek(1);
        // configure.setSemesterStartDate();
        configure.setReminderTime(Collections.singletonList("-15m"));
        // configure.setLessonRanges();
    }

    /**
     * @return the result built
     */
    public Configure build() {
        return configure;
    }

    public void setEventSummaryFormat(String str) {
        configure.setEventSummaryFormat(str);
    }

    public void setEventDescriptionFormat(String str) {
        configure.setEventDescriptionFormat(str);
    }

    public void setTimezone(String str) {
        configure.setTimezone(ZoneId.of(str));
    }

    public void setFirstDayOfWeek(String str) {
        this.setFirstDayOfWeek(Integer.parseInt(str) % 7);
    }

    public void setFirstDayOfWeek(int n) {
        configure.setFirstDayOfWeek(n);
    }

    public void setSemesterStartDate(String str) {
        configure.setSemesterStartDate(LocalDate.parse(str));
    }

    public void setReminderTime(List<String> reminderTimes) {
        configure.setReminderTime(reminderTimes);
    }

    public void setLessonRanges(List<String> ranges) {
        configure.setLessonRanges(loadLessonRanges(ranges));
    }

    private LessonRanges loadLessonRanges(List<String> strList) {
        LessonRanges result = new LessonRanges();
        Map<Integer, LocalTimeRange> map = new TreeMap<>();

        for (String str : strList) {
            // str: 1=01:00:00-01:40:00
            String[] components = str.split("[=-]");

            if (components.length != 3) {
                throw new InvalidLessonRange("invalid description of lesson range: " + str);
            }

            for (int j = 0; j < 3; ++j)
                components[j] = components[j].trim();

            try {
                int lessonIndex = Integer.parseInt(components[0]);
                LocalTime from = LocalTime.parse(components[1]);
                LocalTime to = LocalTime.parse(components[2]);

                map.put(lessonIndex, new LocalTimeRange(from, to));
            } catch (NumberFormatException e) {
                throw new InvalidLessonRange("lesson index is not a valid number: " + components[0], e);
            } catch (DateTimeParseException e) {
                throw new InvalidLessonRange("lesson range time is not a valid time: " + components[1] + '-' + components[2], e);
            }
        }

        List<Integer> lessonNumbers =  IntStream.range(1, map.keySet().size()).boxed().collect(Collectors.toList());
        if (! map.keySet().containsAll(lessonNumbers)) {
            throw new InvalidLessonRange("the lesson indexes are not from 1 to n");
        }

        for (int lessonIndex : map.keySet())
            result.addRange(map.get(lessonIndex));

        return result;
    }

}
