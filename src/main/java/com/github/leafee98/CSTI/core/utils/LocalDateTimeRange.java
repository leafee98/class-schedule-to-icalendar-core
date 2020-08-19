package com.github.leafee98.CSTI.core.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class LocalDateTimeRange {

    public LocalDateTime from;
    public LocalDateTime to;

    public LocalDateTimeRange(LocalDateTime from, LocalDateTime to) {
        this.from = from;
        this.to = to;
    }

}
