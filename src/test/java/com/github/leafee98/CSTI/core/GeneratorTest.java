package com.github.leafee98.CSTI.core;

import com.github.leafee98.CSTI.core.bean.ScheduleObject;
import com.github.leafee98.CSTI.core.generate.Generator;
import com.github.leafee98.CSTI.core.ics.model.VTimezone;
import com.github.leafee98.CSTI.core.wrapper.schedule.BreakLine;
import org.junit.jupiter.api.Test;

import java.time.ZoneId;

public class GeneratorTest {

    private static String scheduleStr =
            "[[[\n" +
            "event-prefix:课-\n" +
            "reminder-time:-15m\n" +
            "timezone:Asia/Shanghai\n" +
            "first-day-of-week:5\n" +
            "semester-start-date:2020-02-21\n" +
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
            "\n" +
            "<<<\n" +
            "name:C语言课\n" +
            "type:专业必修课\n" +
            "teacher:某某大师\n" +
            "location:某校区某楼某层某室\n" +
            "remark:其实暂时没什么其他信息的啦\n" +
            "schedule:\n" +
            " 4,6-8,10|1|1-3;\n" +
            " 4-5,7-8,9,10|2|10-12;\n" +
            ">>>\n";

    ScheduleObject schedule;

    public GeneratorTest() {
        this.schedule = ScheduleObject.load(BreakLine.recovery(scheduleStr));
    }

    @Test
    public void testTimezone() {
        Generator generator = new Generator(schedule);
        VTimezone timezone = generator.generateVTimeZone();
        System.out.println(timezone);
    }

    @Test
    public void testTimeZoneWithDST1() {
        Generator generator = new Generator(schedule);
        // schedule.getConfigure().setTimezone(ZoneId.of("Pacific/Easter"));
        schedule.getConfigure().setTimezone(ZoneId.of("America/Los_Angeles"));
        VTimezone timezone = generator.generateVTimeZone();
        System.out.println(timezone);
    }

    @Test
    public void testTimeZoneWithDST2() {
        Generator generator = new Generator(schedule);
        schedule.getConfigure().setTimezone(ZoneId.of("Pacific/Fiji"));
        // schedule.getConfigure().setTimezone(ZoneId.of("America/Los_Angeles"));
        VTimezone timezone = generator.generateVTimeZone();
        System.out.println(timezone);
    }
    @Test
    public void testFinalGenerate() {
        Generator generator = new Generator(schedule);
        schedule.getConfigure().setTimezone(ZoneId.of("Pacific/Fiji"));
        // schedule.getConfigure().setTimezone(ZoneId.of("America/Los_Angeles"));
        System.out.println(generator.generate());
    }

}
