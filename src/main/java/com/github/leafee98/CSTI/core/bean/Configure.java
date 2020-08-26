package com.github.leafee98.CSTI.core.bean;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.zone.ZoneRulesException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.github.leafee98.CSTI.core.configure.KeyWords;
import com.github.leafee98.CSTI.core.exceptions.InvalidConfigure;

public class Configure {

    // default event-summary-format is ``${lessonName}-${location}``
    private String eventSummaryFormat = "${lessonName}-${location}";

    // default value of event-description-format
    private String eventDescriptionFormat =
            "name:${lessonName}\\n" +
            "location:${location}\\n" +
            "teacher:${teacher}\\n" +
            "type:${lessonType}\\n" +
            "remark:${remark}\\n" +
            "schedule:${scheduleFull}";

    // default is system default time zone
    private ZoneId timezone = ZoneId.systemDefault();

    // default unset, init when loading from configure file
    // use day of semesterStartDate if not present in configure file
    private int firstDayOfWeek = -1;

    // no default, and must occur in config file
    private LocalDate semesterStartDate;

    // reminder time default is before 15 minutes class start
    private List<String> reminderTime = new ArrayList<>(Collections.singleton("-15m"));

    // no default, and must occur in config file
    private LessonRanges lessonRanges;

    public static Configure load(String str) {
        Configure result = new Configure();

        String[] lines = str.split("\n");
        for (int i = 0; i < lines.length; ++i)
            lines[i] = lines[i].trim();

        for (String line : lines) {
            if (line.startsWith(KeyWords.eventSummaryFormat)) {
                // keep white spaces of format description
                String tmp = line.substring(KeyWords.eventSummaryFormat.length() + 1);
                if (tmp.trim().length() != 0) {
                    result.setEventSummaryFormat(tmp);
                }
                // result.setEventSummaryFormat(line.substring(KeyWords.eventSummaryFormat.length() + 1));
            } else if (line.startsWith(KeyWords.eventDescriptionFormat)) {
                String tmp = line.substring(KeyWords.eventDescriptionFormat.length() + 1);
                if (tmp.trim().length() != 0) {
                    result.setEventDescriptionFormat(tmp);
                }
                // result.setEventDescriptionFormat(line.substring(KeyWords.eventDescriptionFormat.length() + 1));
            } else if (line.startsWith(KeyWords.firstDayOfWeek)) {
                // take 0 as Sunday
                String tmp = line.substring(KeyWords.firstDayOfWeek.length() + 1).trim();
                if (tmp.length() != 0) {
                    int dow = Integer.parseInt(tmp);
                    result.setFirstDayOfWeek(dow % 7);
                }
            } else if (line.startsWith(KeyWords.semesterStartDate)) {
                result.setSemesterStartDate(
                        LocalDate.parse(line.substring(KeyWords.semesterStartDate.length() + 1).trim()));
                if (result.getFirstDayOfWeek() < 0) {
                    result.setFirstDayOfWeek(result.getSemesterStartDate().getDayOfWeek().getValue());
                }
            } else if (line.startsWith(KeyWords.reminderTime)) {
                // get reminder description and remove redundant spaces
                List<String> reminder = new ArrayList<>();
                String reminderStr = line.substring(KeyWords.reminderTime.length() + 1).trim();
                String[] reminderArr = reminderStr.split(",");
                for (String s : reminderArr) {
                    String tmp = s.trim();
                    if (tmp.length() > 0)
                        reminder.add(tmp);
                }
                result.setReminderTime(reminder);
            } else if (line.startsWith(KeyWords.timezone)) {
                try {
                    ZoneId id = ZoneId.of(line.substring(KeyWords.timezone.length() + 1).trim());
                    result.setTimezone(id);
                } catch (ZoneRulesException e) {
                    throw new InvalidConfigure("timezone must be region format, follows are available:\n"
                            + ZoneId.getAvailableZoneIds());
                }
            } else if (line.startsWith(KeyWords.lessonRanges)) {
                String remain = line.substring(KeyWords.lessonRanges.length() + 1).trim();
                result.setLessonRanges(LessonRanges.load(remain));
            }
        }

        // check, throw exception if failed
        result.checkSemesterStartDate();

        return result;
    }

    private void checkSemesterStartDate() {
        if (semesterStartDate.getDayOfWeek().getValue() % 7 != firstDayOfWeek) {
            throw new InvalidConfigure("semester-start-date is not compatible with first-day-of-week\n" +
                    "it's " + getSemesterStartDate().getDayOfWeek().name() + " on " +
                    getSemesterStartDate());
        }
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        StringBuilder builder = new StringBuilder();

        builder.append(KeyWords.eventSummaryFormat).append(':');
        builder.append(eventSummaryFormat).append('\n');

        builder.append(KeyWords.eventDescriptionFormat).append(':');
        builder.append(eventDescriptionFormat).append('\n');

        builder.append(KeyWords.timezone).append(':');
        builder.append(timezone).append('\n');

        builder.append(KeyWords.firstDayOfWeek).append(':');
        builder.append(firstDayOfWeek).append('\n');

        builder.append(KeyWords.semesterStartDate).append(':');
        builder.append(semesterStartDate.format(formatter)).append('\n');

        // append list of reminder time with comma split
        builder.append(KeyWords.reminderTime).append(':');
        for (String s : reminderTime) {
            builder.append(s).append(',');
        }
        builder.setLength(builder.length() - 1);
        builder.append('\n');

        // no newline at the end of a string description
        builder.append(KeyWords.lessonRanges).append(':');
        builder.append(lessonRanges.toString());

        return builder.toString();
    }

    public String getEventSummaryFormat() {
        return eventSummaryFormat;
    }

    public void setEventSummaryFormat(String eventSummaryFormat) {
        this.eventSummaryFormat = eventSummaryFormat;
    }

    public String getEventDescriptionFormat() {
        return eventDescriptionFormat;
    }

    public void setEventDescriptionFormat(String eventDescriptionFormat) {
        this.eventDescriptionFormat = eventDescriptionFormat;
    }

    public ZoneId getTimezone() {
        return timezone;
    }

    public void setTimezone(ZoneId timezone) {
        this.timezone = timezone;
    }

    public int getFirstDayOfWeek() {
        return firstDayOfWeek;
    }

    public void setFirstDayOfWeek(int firstDayOfWeek) {
        this.firstDayOfWeek = firstDayOfWeek;
    }

    public LocalDate getSemesterStartDate() {
        return semesterStartDate;
    }

    public void setSemesterStartDate(LocalDate semesterStartDate) {
        this.semesterStartDate = semesterStartDate;
    }

    public List<String> getReminderTime() {
        return reminderTime;
    }

    public void setReminderTime(List<String> reminderTime) {
        this.reminderTime = reminderTime;
    }

    public LessonRanges getLessonRanges() {
        return lessonRanges;
    }

    public void setLessonRanges(LessonRanges lessonRanges) {
        this.lessonRanges = lessonRanges;
    }

}
