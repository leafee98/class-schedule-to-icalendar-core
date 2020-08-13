package com.github.leafee98.CSTI.core;

import com.github.leafee98.CSTI.core.utils.TimeFormatter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class TimeFormatterTest {

    @Test
    public void testFormat() {
        LocalDateTime dateTime = LocalDateTime.of(2021, 1, 2, 12, 30);
        TimeFormatter formatter = new TimeFormatter("Asia/Shanghai");

        Assertions.assertEquals("20210102T123000", formatter.local(dateTime));
        Assertions.assertEquals("20210102T043000Z", formatter.utc(dateTime));
    }

}
