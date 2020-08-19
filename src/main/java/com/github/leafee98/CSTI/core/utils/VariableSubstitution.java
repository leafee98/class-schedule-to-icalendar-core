package com.github.leafee98.CSTI.core.utils;

import com.github.leafee98.CSTI.core.bean.Lesson;
import com.github.leafee98.CSTI.core.bean.LessonSchedule;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VariableSubstitution {

    public static final String lessonName = "lessonName";
    public static final String location = "location";
    public static final String teacher = "teacher";
    public static final String lessonType = "lessonType";
    public static final String remark = "remark";
    public static final String scheduleFull = "scheduleFull";

    public static String doSubstitute(String input, Lesson lesson) {
        Matcher matcher1 = generatePattern(lessonName).matcher(input);
        String res1 = matcher1.replaceAll(lesson.getName());

        Matcher matcher2 = generatePattern(location).matcher(res1);
        String res2 = matcher2.replaceAll(lesson.getLocation());

        Matcher matcher3 = generatePattern(teacher).matcher(res2);
        String res3 = matcher3.replaceAll(lesson.getTeacher());

        Matcher matcher4 = generatePattern(lessonType).matcher(res3);
        String res4 = matcher4.replaceAll(lesson.getType());

        Matcher matcher5 = generatePattern(remark).matcher(res4);
        String res5 = matcher5.replaceAll(lesson.getRemark());

        Matcher matcher6 = generatePattern(scheduleFull).matcher(res5);
        StringBuilder builder = new StringBuilder();
        for (LessonSchedule s : lesson.getSchedule()) {
            // there is escape in methods replaceAll of matcher
            builder.append("\\\\n ").append(s);
        }
        String res6 = matcher6.replaceAll(builder.toString());

        return suitEscape(res6);
    }

    private static Pattern generatePattern(String variableName) {
        String regVariableStart = "\\$\\{";
        String regVariableEnd = "}";

        return Pattern.compile("(?<!\\\\)" + regVariableStart + variableName + regVariableEnd,
                Pattern.CASE_INSENSITIVE);
    }

    private static String suitEscape(String input) {
        // escape \\ \; \, \n
        // keep \\ and \n because they are synonymous

        return input.replaceAll("(?<!\\\\);", "\\;")
                .replaceAll("(?<!\\\\),", "\\,");
    }

}
