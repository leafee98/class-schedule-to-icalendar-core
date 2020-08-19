package com.github.leafee98.CSTI.core.generate;

import com.github.leafee98.CSTI.core.bean.Lesson;
import com.github.leafee98.CSTI.core.bean.ScheduleObject;
import com.github.leafee98.CSTI.core.configure.KeyWords;
import com.github.leafee98.CSTI.core.exceptions.InvalidConfigure;
import com.github.leafee98.CSTI.core.exceptions.TimeZoneException;
import com.github.leafee98.CSTI.core.ics.Value;
import com.github.leafee98.CSTI.core.ics.model.*;
import com.github.leafee98.CSTI.core.utils.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.zone.ZoneOffsetTransitionRule;
import java.time.zone.ZoneRules;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Generator {

    public static final String FREQ = "FREQ";
    public static final String RRULE = "RRULE";
    public static final String YEARLY = "YEARLY";
    public static final String BYDAY = "BYDAY";
    public static final String BYMONTHDAY = "BYMONTHDAY";
    public static final String BYMONTH = "BYMONTH";
    public static final String OPAQUE = "OPAQUE";
    public static final String VALUE = "VALUE";
    public static final String DURATION = "DURATION";
    public static final String DISPLAY = "DISPLAY";
    public static final String TZID = "TZID";

    private final ScheduleObject scheduleObj;
    private final TimeFormatter formatter;

    private final WeekUtils weekUtil = new WeekUtils();
    private final LessonDateTimeCalculator calculator;

    public Generator(ScheduleObject obj) {
        this.scheduleObj = obj;
        this.formatter = new TimeFormatter(scheduleObj.getConfigure().getTimezone());
        this.calculator = new LessonDateTimeCalculator(scheduleObj.getConfigure());
    }

    public VCalendar generate() {
        VCalendar result = new VCalendar();
        result.setTimeZone(generateVTimeZone());
        for (Lesson l : scheduleObj.getLessons()) {
            result.getVEvents().addAll(generateVEvent(l));
        }

        return result;
    }

    /**
     * there is RRULE with generated DAYLIGHT and STANDARD
     * @return VTIMEZONE generate from timezone
     */
    public VTimezone generateVTimeZone() {
        VTimezone timezone = new VTimezone();
        ZoneId zoneId = this.scheduleObj.getConfigure().getTimezone();
        ZoneRules zoneRules = zoneId.getRules();

        timezone.getTzid().setValue(zoneId.getId());

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

                    assignTimeZoneRrule(standard.getRRule().getValue(), rule);

                    timezone.setStandard(standard);
                } else {
                    Daylight daylight = new Daylight();

                    daylight.getTzName().setValue("DST(irregular)");
                    daylight.getDtStart().setValue(formatter.local(dateStart));
                    daylight.getTzOffsetFrom().setValue(formatter.offset(rule.getOffsetBefore()));
                    daylight.getTzOffsetTo().setValue(formatter.offset(rule.getOffsetAfter()));

                    assignTimeZoneRrule(daylight.getRRule().getValue(), rule);

                    timezone.setDaylight(daylight);
                }
            }
        }

        return timezone;
    }

    private void assignTimeZoneRrule(Value value, ZoneOffsetTransitionRule rule) {
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
                    byMonthDayValue.append(",").append(indicator);
            } else {
                indicator = monthLength + 1 + indicator;
                byMonthDayValue.append(indicator--);
                for (int i = 0; i < 6 && indicator >= 1; ++i, --indicator) {
                    byMonthDayValue.append(",").append(indicator);
                }
            }

            value.putParameter(FREQ, YEARLY);
            value.putParameter(BYDAY, rule.getDayOfWeek().name().substring(0, 2));
            value.putParameter(BYMONTHDAY, byMonthDayValue.toString());
            value.putParameter(BYMONTH, monthValue);
        }
    }

    public List<VEvent> generateVEvent(Lesson lesson) {
        List<VEvent> result = new ArrayList<>();

        // constant properties in different events of the same lesson
        String created = formatter.utc(LocalDateTime.now()); // created, dtstamp, lastModified
        String location = lesson.getLocation();

        List<VAlarm> alarms = generateVAlarm();
        String summary = generateEventSummary(lesson);
        String description = generateEventDescription(lesson);

        // variable properties: UID, DTSTART, DTEND
        List<LocalDateTimeRange> lessonDateTimes = calculator.cal(lesson);

        // as parameter of dtStart
        String tzId = scheduleObj.getConfigure().getTimezone().getId();

        for (LocalDateTimeRange dateTimeRange : lessonDateTimes) {
            VEvent event = new VEvent();

            // datetime start and end
            event.getDtStart().setValue(formatter.local(dateTimeRange.from));
            event.getDtStart().putParameter(TZID, tzId);

            event.getDtEnd().setValue(formatter.local(dateTimeRange.to));
            event.getDtEnd().putParameter(TZID, tzId);

            // uid
            event.getUid().setValue(UUID.randomUUID().toString());

            // constant property
            event.getCreated().setValue(created);
            event.getDtStamp().setValue(created);
            event.getLastModified().setValue(created);
            event.getTransp().setValue(OPAQUE);
            event.getLocation().setValue(location);

            event.getSummary().setValue(summary);
            event.getDescription().setValue(description);

            // VALARM
            for (VAlarm alarm : alarms) {
                event.getVAlarms().add(alarm);
            }

            // save to result
            result.add(event);
        }

        return result;
    }

    // event title and description is custom, which may contain special prefix or other remarks.
    private String generateEventSummary(Lesson lesson) {
        return VariableSubstitution.doSubstitute(scheduleObj.getConfigure().getEventSummaryFormat(), lesson);
    }

    private String generateEventDescription(Lesson lesson) {
        return VariableSubstitution.doSubstitute(scheduleObj.getConfigure().getEventDescriptionFormat(), lesson);
    }

    private List<VAlarm> generateVAlarm() {
        Pattern reminderFormat = Pattern.compile("([+-]?\\d+)([HMS])");

        List<VAlarm> result = new ArrayList<>();

        List<String> reminders = scheduleObj.getConfigure().getReminderTime();
        for (String reminderTime : reminders) {
            VAlarm alarm = new VAlarm();
            alarm.getAction().setValue(DISPLAY);
            alarm.getDescription().setValue("generated by class-schedule-to-icalendar");

            reminderTime = reminderTime.toUpperCase();
            Matcher matcher = reminderFormat.matcher(reminderTime);
            if (matcher.matches()) {
                int timeValue = Integer.parseInt(matcher.group(1));
                String timeUnit = matcher.group(2);

                alarm.getTrigger().putParameter(VALUE, DURATION);
                if (timeValue < 0) {
                    timeValue *= -1;
                    alarm.getTrigger().setValue("-PT" + timeValue + timeUnit);
                } else {
                    alarm.getTrigger().setValue("PT" + timeValue + timeUnit);
                }
            } else {
                throw new InvalidConfigure("cannot parse " + KeyWords.reminderTime + "'s value: " + reminderTime);
            }

            result.add(alarm);
        }

        return result;
    }

}
