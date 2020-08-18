package com.github.leafee98.CSTI.core;

import com.github.leafee98.CSTI.core.ics.Component;
import com.github.leafee98.CSTI.core.ics.Property;
import com.github.leafee98.CSTI.core.ics.Value;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestIcs {

    String expected1 = "BEGIN:VEVENT\n" +
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

    String expected2 =
            "BEGIN:VALARM\n" +
                    "ACTION:DISPLAY\n" +
                    "DESCRIPTION:Default Mozilla Description\n" +
                    "TRIGGER;VALUE=DATE-TIME:20210103T080000Z\n" +
                    "END:VALARM";

}
