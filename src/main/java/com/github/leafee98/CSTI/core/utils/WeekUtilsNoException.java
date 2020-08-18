package com.github.leafee98.CSTI.core.utils;

import com.github.leafee98.CSTI.core.exceptions.TimeZoneException;

import java.time.Month;

public class WeekUtilsNoException extends WeekUtils {

    @Override
    public int byIndicator(int dayOfMonthIndicator, Month month) {
        int result = 0;
        try {
            result = super.byIndicator(dayOfMonthIndicator, month);
        } catch (TimeZoneException e) {
            result = super.ordinalOfWeek(dayOfMonthIndicator);
        }
        return result;
    }
}
