package com.github.leafee98.CSTI.core;

import java.time.LocalTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import com.github.leafee98.CSTI.core.bean.LessonRanges;
import com.github.leafee98.CSTI.core.utils.LocalTimeRange;

public class LocalTimeRangeTest {

    @Test
    public void testFormatOfToString() {
        LocalTimeRange range1 = new LocalTimeRange(LocalTime.of(1, 0, 0), LocalTime.of(1, 40, 0));
        LocalTimeRange range2 = new LocalTimeRange(LocalTime.of(2, 2, 30), LocalTime.of(2, 50, 25));
        LocalTimeRange range3 = new LocalTimeRange(LocalTime.of(22, 59, 59), LocalTime.of(23, 0, 0));

        LessonRanges lessonRanges = new LessonRanges();
        lessonRanges.addRange(range1);
        lessonRanges.addRange(range2);
        lessonRanges.addRange(range3);

        String generated = lessonRanges.toString();
        String expected = "1=01:00:00-01:40:00,2=02:02:30-02:50:25,3=22:59:59-23:00:00";

        Assertions.assertEquals(expected, generated);
    }

    @Test
    public void testLoad() {
        String lessonRangeStr = "1=08:00:00-08:45:00,"
                + "2=08:50:00-09:35:00,"
                + "3=09:50:00-10:35:00,"
                + "4=10:40:00-11:25:00,"
                + "5=11:30:00-12:15:00,"
                + "6=13:30:00-14:15:00,"
                + "7=14:20:00-15:05:00,"
                + "8=15:20:00-16:05:00,"
                + "9=16:10:00-16:55:00,"
                + "10=18:30:00-19:15:00,"
                + "11=19:20:00-20:05:00,"
                + "12=20:10:00-20:55:00";
        
        LessonRanges lr = LessonRanges.load(lessonRangeStr);

        Assertions.assertEquals(lessonRangeStr, lr.toString());
    }
}