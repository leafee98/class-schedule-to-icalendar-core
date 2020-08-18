package com.github.leafee98.CSTI.core.generate;

import com.github.leafee98.CSTI.core.bean.ScheduleObject;
import com.github.leafee98.CSTI.core.exceptions.TimeZoneException;
import com.github.leafee98.CSTI.core.ics.Value;
import com.github.leafee98.CSTI.core.ics.model.Daylight;
import com.github.leafee98.CSTI.core.ics.model.Standard;
import com.github.leafee98.CSTI.core.ics.model.VCalendar;
import com.github.leafee98.CSTI.core.ics.model.VTimezone;
import com.github.leafee98.CSTI.core.utils.TimeFormatter;
import com.github.leafee98.CSTI.core.utils.WeekUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.zone.ZoneOffsetTransitionRule;
import java.time.zone.ZoneRules;

public class Generator {

    public static final String FREQ = "FREQ";
    public static final String RRULE = "RRULE";
    public static final String YEARLY = "YEARLY";
    public static final String BYDAY = "BYDAY";
    public static final String BYMONTHDAY = "BYMONTHDAY";
    public static final String BYMONTH = "BYMONTH";

    private final ScheduleObject schedule;
    private final VCalendar calendar;

    private final WeekUtils weekUtil = new WeekUtils();

    public Generator(ScheduleObject obj) {
        this.schedule = obj;
        this.calendar = new VCalendar();
    }

    /**
     * the generated VTIMEZONE 's DAYLIGHT and STANDARD are without any RRULE
     * @return VTIMEZONE generate from timezone
     */
    public VTimezone generateVTimeZone() {
        VTimezone timezone = new VTimezone();
        ZoneId zoneId = this.schedule.getConfigure().getTimezone();
        ZoneRules zoneRules = zoneId.getRules();
        TimeFormatter formatter = new TimeFormatter(zoneId);

        LocalDateTime dateStart = LocalDateTime.of(1970, 1, 1, 0, 0);

        if (zoneRules.getTransitionRules().isEmpty()) {
            Standard standard = new Standard();

            ZoneOffset offset = zoneRules.getOffset(LocalDateTime.now());
            // ZoneOffset offset = zoneRules.getStandardOffset(Instant.from(dateStart));

            standard.getTzName().setValue("STD(irregular)");
            standard.getDtStart().setValue(formatter.local(dateStart));
            standard.getTzOffsetFrom().setValue("+0000");
            standard.getTzOffsetTo().setValue(formatter.offset(offset));

            timezone.setStandard(standard);
        } else {
            for (ZoneOffsetTransitionRule rule : zoneRules.getTransitionRules()) {
                if (rule.getOffsetAfter().equals(rule.getStandardOffset())) {
                    Standard standard = new Standard();

                    standard.getTzName().setValue("STD(irregular)");
                    standard.getDtStart().setValue(formatter.local(dateStart));
                    standard.getTzOffsetFrom().setValue(formatter.offset(rule.getOffsetBefore()));
                    standard.getTzOffsetTo().setValue(formatter.offset(rule.getOffsetAfter()));

                    assignRrule(standard.getRRule().getValue(), rule);

                    timezone.setStandard(standard);
                } else {
                    Daylight daylight = new Daylight();

                    daylight.getTzName().setValue("DST(irregular)");
                    daylight.getDtStart().setValue(formatter.local(dateStart));
                    daylight.getTzOffsetFrom().setValue(formatter.offset(rule.getOffsetBefore()));
                    daylight.getTzOffsetTo().setValue(formatter.offset(rule.getOffsetAfter()));

                    assignRrule(daylight.getRRule().getValue(), rule);

                    timezone.setDaylight(daylight);
                }
            }
        }

        return timezone;
    }

    private void assignRrule(Value value, ZoneOffsetTransitionRule rule) {
        String monthValue = String.valueOf(rule.getMonth().getValue());

        try {
            int week = weekUtil.byIndicator(rule.getDayOfMonthIndicator(), rule.getMonth());

            String weekValue = week + rule.getDayOfWeek().name().substring(0, 2);
            value.putParameter(FREQ, YEARLY);
            value.putParameter(BYDAY, weekValue);
            value.putParameter(BYMONTH, monthValue);
        } catch (TimeZoneException e) {
            StringBuilder byMonthDayValue = new StringBuilder();

            // no February appeared in rules of java tz database, treat as no-leap year.
            int monthLength = rule.getMonth().length(false);
            int indicator = rule.getDayOfMonthIndicator();

            if (indicator > 0) {
                byMonthDayValue.append(indicator++);
                for (int i = 1; i < 7 && indicator <= monthLength; ++i, ++indicator)
                    byMonthDayValue.append("," + indicator);
            } else {
                indicator = monthLength + 1 + indicator;
                byMonthDayValue.append(indicator--);
                for (int i = 0; i < 6 && indicator >= 1; ++i, --indicator) {
                    byMonthDayValue.append("," + indicator);
                }
            }

            value.putParameter(FREQ, YEARLY);
            value.putParameter(BYDAY, rule.getDayOfWeek().name().substring(0, 2));
            value.putParameter(BYMONTHDAY, byMonthDayValue.toString());
            value.putParameter(BYMONTH, monthValue);
        }
    }



    // private Component vAlarm() {
    //     Component valarm = new Component(IcsWords.valarm);
    //     valarm.add(new Property(IcsWords.trigger, IcsWords.DURATION, IcsWords.))
    // }

}
