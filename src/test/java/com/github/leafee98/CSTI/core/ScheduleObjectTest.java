package com.github.leafee98.CSTI.core;

import com.github.leafee98.CSTI.core.bean.ScheduleObject;
import com.github.leafee98.CSTI.core.wrapper.schedule.BreakLine;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ScheduleObjectTest {

    @Test
    public void test() {
        String input = "[[[\n" +
                "event-summary-format:lesson${lessonName}location${location}-${schedule}${teacher}\n" +
                "event-description-format:\n" +
                "timezone:Asia/Shanghai\n" +
                "first-day-of-week:5\n" +
                "semester-start-date:2020-02-21\n" +
                "reminder-time:-15m\n" +
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
                " 12=20:10:00-20:55:00\n" +
                "]]]\n" +
                "<<<\n" +
                "name:C语言课\n" +
                "type:专业必修课\n" +
                "teacher:某某大师\n" +
                "location:某校区某楼某层某室\n" +
                "remark:其实暂时没什么其他信息的啦\n" +
                "schedule:\n" +
                " 4,7-8,10|1|1,3;\n" +
                " 4,6-7,9-10|1|1-3;\n" +
                ">>>\n";

        String expected = "[[[\n" +
                "event-summary-format:lesson${lessonName}location${location}-${schedule}${teacher}\n" +
                "event-description-format:name:${lessonName}\\nlocation:${location}\\nteacher:${teacher}\\ntype:${lessonType}\\nremark:${remark}\\nschedule:${scheduleFull}\n" +
                "timezone:Asia/Shanghai\n" +
                "first-day-of-week:5\n" +
                "semester-start-date:2020-02-21\n" +
                "reminder-time:-15m\n" +
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
                " 12=20:10:00-20:55:00\n" +
                "]]]\n" +
                "<<<\n" +
                "name:C语言课\n" +
                "type:专业必修课\n" +
                "teacher:某某大师\n" +
                "location:某校区某楼某层某室\n" +
                "remark:其实暂时没什么其他信息的啦\n" +
                "schedule:\n" +
                " 4,7-8,10|1|1,3;\n" +
                " 4,6-7,9-10|1|1-3;\n" +
                ">>>\n";

        ScheduleObject obj = ScheduleObject.load(BreakLine.recovery(input));
        Assertions.assertEquals(expected, BreakLine.doBreak(obj.toString(), false));
    }

}
