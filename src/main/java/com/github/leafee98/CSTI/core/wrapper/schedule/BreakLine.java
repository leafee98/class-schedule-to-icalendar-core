package com.github.leafee98.CSTI.core.wrapper.schedule;

import com.github.leafee98.CSTI.core.configure.KeyWords;

public class BreakLine {

    /**
     * Make lessonSchedule and lessonRanges output more friendly.
     * @param input Cschedule configuration without break line
     * @return formatted configuration
     */
    public static String doBreak(String input) {
        StringBuilder builder = new StringBuilder();
        String[] lines = input.split("\n");
        for (String line : lines) {
            if (line.startsWith(KeyWords.lessonRanges)) {
                builder.append(handleLessonRanges(line));
            } else if (line.startsWith(KeyWords.lessonSchedule)) {
                builder.append(handleLessonSchedule(line));
            } else {
                builder.append(line);
            }
            builder.append('\n');
        }
        return builder.toString();
    }

    private static String BreakLineByStr(String line, String c) {
        int colonIndex = line.indexOf(':');
        StringBuilder builder = new StringBuilder(line.substring(0, colonIndex + 1));
        String[] contents = line.substring(colonIndex + 1).split(c);
        for (String content : contents) {
            builder.append("\n ");
            builder.append(content);
            builder.append(c);
        }
        return builder.substring(0, builder.length());
    }

    private static String handleLessonRanges(String line) {
        String res = BreakLineByStr(line, ",");
        return res.substring(0, res.length() - 1);
    }

    private static String handleLessonSchedule(String line) {
        return BreakLineByStr(line, ";");
    }

    /**
     * Undo the break line to simplify parsing Cschedule.
     * The first space character will be ignored is it is at the first line.
     * (The trailing space characters will be kept.)
     * (Will append a blank line at the end.)
     * @param input Cschedule configuration with break line
     * @return string without break line.
     */
    public static String recovery(String input) {
        StringBuilder builder = new StringBuilder();
        String[] lines = input.split("\n");
        for (String line : lines) {
            // remove the trailing spaces
            String verifyBlankLine = line.replaceAll("\\s+$", "");
            if (verifyBlankLine.length() == 0)
                continue;

            if (line.startsWith(" ")) {
                builder.append(line.substring(1));
            } else {
                builder.append('\n');
                builder.append(line);
            }
        }
        builder.append('\n');
        return builder.substring(1);
    }

}
