package com.github.leafee98.CSTI.core.bean.loader;

import com.github.leafee98.CSTI.core.bean.LessonRanges;
import com.github.leafee98.CSTI.core.bean.LessonSchedule;
import com.github.leafee98.CSTI.core.exceptions.InvalidLessonRange;
import com.github.leafee98.CSTI.core.exceptions.InvalidLessonSchedule;
import com.github.leafee98.CSTI.core.utils.LocalTimeRange;
import com.github.leafee98.CSTI.core.utils.RangeNumber;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * The common and small property loader used by other specified format loader
 *
 * If any exception occurred, just throw it out.
 */
public class SmallPropertyLoader {
    public ZoneId loadTimezone(String str) {
        return ZoneId.of(str);
    }

    public LocalDate loadSemesterStartDate(String str) {
        return LocalDate.parse(str);
    }

    public LessonRanges loadLessonRanges(List<String> strList) {
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

    public List<LessonSchedule> loadLessonSchedule(List<String> schedules) {
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
