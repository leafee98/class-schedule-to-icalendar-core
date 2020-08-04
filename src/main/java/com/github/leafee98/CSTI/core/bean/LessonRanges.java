package com.github.leafee98.CSTI.core.bean;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.github.leafee98.CSTI.core.exceptions.InvalidLessonRange;

public class LessonRanges {

    private final ArrayList<LocalTimeRange> ranges;

    /**
     * @param scheduleStr should like the follows, the whitespaces will not be
     *                    checked there:
     *                    1=01:00:00-01:40:00,2=02:02:30-02:50:25,3=22:59:59-23:00:00
     *
     * @return a LessonRanges object generated from {@code scheduleStr}
     *
     * @throws InvalidLessonRange
     */
    public static LessonRanges load(String scheduleStr) {
        LessonRanges lr = new LessonRanges();

        HashMap<Integer, LocalTimeRange> map = new HashMap<>();
        String[] list = scheduleStr.split(",");

        for (String str : list) {
            // str: 1=01:00:00-01:40:00
            String[] components = str.split("[=-]");

            if (components.length != 3) {
                throw new InvalidLessonRange("invalid description of lesson range: " + str);
            }

            try {
                int lessonIndex = Integer.parseInt(components[0]);
                LocalTime from = LocalTime.parse(components[1]);
                LocalTime to = LocalTime.parse(components[2]);

                map.put(lessonIndex, new LocalTimeRange(from, to));
            } catch (NumberFormatException e) {
                throw new InvalidLessonRange("lesson index is not a valid number: " + components[0], e);
            } catch (DateTimeParseException e) {
                throw new InvalidLessonRange("lesson range time is not a valid time: "
                        + components[1] + '-' + components[2], e);
            }
        }

        List<Integer> lessonNumbers =  IntStream.range(1, map.keySet().size()).boxed().collect(Collectors.toList());
        if (! map.keySet().containsAll(lessonNumbers)) {
            throw new InvalidLessonRange("the lesson indexes are not from 1 to n");
        }

        for (int lessonIndex : map.keySet())
            lr.addRange(map.get(lessonIndex));

        return lr;
    }

    public LessonRanges() {
        this.ranges = new ArrayList<LocalTimeRange>();
    }

    public void addRange(LocalTimeRange t) {
        ranges.add(t);
    }

    public void reset() {
        ranges.clear();
    }

    public LocalTimeRange getRange(int i) {
        return ranges.get(i - 1);
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        StringBuilder builder = new StringBuilder();

        boolean comma = false;
        for (int i = 0; i < ranges.size(); ++i) {
            if (comma) {
                builder.append(',');
            } else {
                comma = true;
            }

            LocalTimeRange range = ranges.get(i);
            builder.append(i + 1);
            builder.append('=');
            builder.append(range.from.format(formatter));
            builder.append('-');
            builder.append(range.to.format(formatter));
        }

        return builder.toString();
    }

}
