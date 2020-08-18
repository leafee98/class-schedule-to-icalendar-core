package com.github.leafee98.CSTI.core.utils;

import java.time.*;
import java.time.format.DateTimeFormatter;

public class TimeFormatter {

    private final ZoneId zoneId;

    public TimeFormatter(String zoneId) {
        this.zoneId = ZoneId.of(zoneId);
    }

    public TimeFormatter(ZoneId zoneId) {
        this.zoneId = zoneId;
    }

    /**
     * get string "19700101T000000Z" from datetime 19700101T080000 at Asia/Shanghai
     * @param dateTime local datetime
     * @return string formatted as "yyyyMMddThhmmssZ"
     */
    public String utc(LocalDateTime dateTime) {
        ZonedDateTime local = dateTime.atZone(zoneId);
        ZonedDateTime utc= local.withZoneSameInstant(ZoneId.of("UTC"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'hhmmss'Z'");
        return formatter.format(utc);
    }

    /**
     * get string "19700101T080000" from datetime 19700101T080000 at Asia/Shanghai
     * @param dateTime local datetime
     * @return string formatted as "yyyyMMddThhmmss"
     */
    public String local(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'hhmmss");
        return formatter.format(dateTime);
    }

    public String offset(ZoneOffset offset) {
        return offset.toString().replace(":", "");
    }

}
