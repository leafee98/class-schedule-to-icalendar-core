package com.github.leafee98.CSTI.core;

import com.github.leafee98.CSTI.core.bean.LessonSchedule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class LessonScheduleTest {

    @Test
    public void fullTest() {
        String input = "6-9,2|4|1,2,3;"
                + "3,2,5|5|1,2-3;";

        String expected1 = "2,6-9|4|1-3;";
        String expected2 = "2-3,5|5|1-3;";

        List<LessonSchedule> schedules = LessonSchedule.load(input);

        Assertions.assertEquals(expected1, schedules.get(0).toString());
        Assertions.assertEquals(expected2, schedules.get(1).toString());
        Assertions.assertEquals(2, schedules.size());
    }

}
