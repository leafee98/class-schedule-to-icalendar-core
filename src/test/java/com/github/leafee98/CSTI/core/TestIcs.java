package com.github.leafee98.CSTI.core;

import com.github.leafee98.CSTI.core.ics.Component;
import com.github.leafee98.CSTI.core.ics.Property;
import com.github.leafee98.CSTI.core.ics.Value;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestIcs {

    @Test
    public void testProperty1() {
        Property p = new Property("DTSTART", new Value("20210103"));
        p.putParameter("VALUE", "DATE");

        Assertions.assertEquals("DTSTART;VALUE=DATE:20210103", p.toString());
    }

    @Test
    public void testProperty2() {
        Property p = new Property("ATTENDEE", new Value("mailto:jsmith@example.com"),
                "RSVP", "TRUE", "ROLE", "REQ-PARTICIPANT");
        String expected = "ATTENDEE;ROLE=REQ-PARTICIPANT;RSVP=TRUE:mailto:" +
                "jsmith@example.com";
        Assertions.assertEquals(expected, p.toString());
    }

    @Test
    public void testComponent1() {
        Component c = new Component("VALARM");
        Property action = new Property("ACTION", new Value("DISPLAY"));
        Property trigger = new Property("TRIGGER");
        trigger.putParameter("VALUE", "DATE-TIME");
        trigger.setValue(new Value("20210103T080000Z"));
        Property description = new Property("DESCRIPTION", new Value("Default Mozilla Description"));

        c.add(action);
        c.add(trigger);
        c.add(description);

        String expected =
                "BEGIN:VALARM\n" +
                "ACTION:DISPLAY\n" +
                "DESCRIPTION:Default Mozilla Description\n" +
                "TRIGGER;VALUE=DATE-TIME:20210103T080000Z\n" +
                "END:VALARM";

        Assertions.assertEquals(expected, c.toString());
    }

    @Test
    public void testComponent2() {
        String expected =
                "BEGIN:VEVENT\n" +
                "CREATED:20200809T013446Z\n" +
                "DESCRIPTION:重复事件\\n地点\\n2021年1月1日每周重复直到2021年" +
                        "3月1日\\n(2021年1月1日为周五)\\n提醒时间为15分钟前\n" +
                "DTEND;TZID=Asia/Shanghai:20210101T090000\n" +
                "DTSTAMP:20200809T013819Z\n" +
                "DTSTART;TZID=Asia/Shanghai:20210101T080000\n" +
                "LAST-MODIFIED:20200809T013819Z\n" +
                "LOCATION:地点\n" +
                "RRULE:FREQ=WEEKLY;UNTIL=20210301T000000Z\n" +
                "SEQUENCE:2\n" +
                "SUMMARY:重复事件\n" +
                "TRANSP:OPAQUE\n" +
                "UID:fa3aa1a0-6209-4b71-aa87-a3ac9a32ec1c\n" +
                "X-MOZ-GENERATION:2\n" +

                "BEGIN:VALARM\n" +
                "ACTION:DISPLAY\n" +
                "DESCRIPTION:Default Mozilla Description\n" +
                "TRIGGER;VALUE=DURATION:-PT15M\n" +
                "END:VALARM\n" +
                "END:VEVENT";

        Component alarm = new Component("VALARM");
        alarm.add(new Property("ACTION", new Value("DISPLAY")));
        alarm.add(new Property("TRIGGER", new Value("-PT15M"), "VALUE", "DURATION"));
        alarm.add(new Property("DESCRIPTION", new Value("Default Mozilla Description")));

        Component event = new Component("VEVENT");
        event.add(alarm);
        event.add(new Property("CREATED", new Value("20200809T013446Z")));
        event.add(new Property("LAST-MODIFIED", new Value("20200809T013819Z")));
        event.add(new Property("DTSTAMP", new Value("20200809T013819Z")));
        event.add(new Property("UID", new Value("fa3aa1a0-6209-4b71-aa87-a3ac9a32ec1c")));
        event.add(new Property("SUMMARY", new Value("重复事件")));
        event.add(new Property("RRULE",
                new Value("", "FREQ", "WEEKLY" ,"UNTIL", "20210301T000000Z")));
        event.add(new Property("DTSTART", new Value("20210101T080000"), "TZID", "Asia/Shanghai"));
        event.add(new Property("DTEND", new Value("20210101T090000"), "TZID", "Asia/Shanghai"));
        event.add(new Property("TRANSP", new Value("OPAQUE")));
        event.add(new Property("LOCATION", new Value("地点")));
        event.add(new Property("DESCRIPTION", new Value("重复事件\\n地点\\n2021年1月1日每周重复直到2021年" +
                "3月1日\\n(2021年1月1日为周五)\\n提醒时间为15分钟前")));
        event.add(new Property("SEQUENCE", new Value("2")));
        event.add(new Property("X-MOZ-GENERATION", new Value("2")));

        Assertions.assertEquals(expected, event.toString());
    }

}
