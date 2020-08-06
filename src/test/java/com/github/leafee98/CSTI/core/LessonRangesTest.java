package com.github.leafee98.CSTI.core;

import com.github.leafee98.CSTI.core.bean.LessonRanges;
import com.github.leafee98.CSTI.core.configure.KeyWords;
import com.github.leafee98.CSTI.core.wrapper.schedule.BreakLine;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LessonRangesTest {

    String input =
            "lesson-ranges:\n" +
            " 1=08:00:00-08:45:00,\n" +
            " 2=08:50:00-09:35:00,\n" +
            " 3=09:50:00-10:35:00,\n" +
            " 4=10:40:00-11:25:00,\n" +
            " 5=11:30:00-12:15:00,\n" +
            " 6=13:30:00-14:15:00,\n" +
            " 7=14:20:00-15:05:00,\n" +
            " 8=15:20:00-16:05:00,\n" +
            " 9=16:10:00-16:55:00,\n" +
            " 10=18:30:00-19:15:00,\n" +
            " 11=19:20:00-20:05:00,\n" +
            " 12=20:10:00-20:55:00\n";

    @Test
    public void fullTest() {
        String formatted = BreakLine.recovery(input);

        formatted = formatted.substring(KeyWords.lessonRanges.length() + 1);
        if (formatted.endsWith("\n"))
            formatted = formatted.substring(0, formatted.length() - 1);

        LessonRanges ranges = LessonRanges.load(formatted);
        String result = ranges.toString();

        String actual = BreakLine.doBreak(KeyWords.lessonRanges + ":" + result);

        Assertions.assertEquals(input, actual);
    }

}
