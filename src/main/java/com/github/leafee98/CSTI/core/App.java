package com.github.leafee98.CSTI.core;

import java.time.LocalTime;

import com.github.leafee98.CSTI.core.bean.LessonRanges;
import com.github.leafee98.CSTI.core.utils.LocalTimeRange;

public class App {

    public static void main(String[] args) {
        LocalTimeRange range1 = new LocalTimeRange(LocalTime.of(22, 3, 9), LocalTime.now());
        LocalTimeRange range2 = new LocalTimeRange(LocalTime.of(22, 3, 9), LocalTime.now());
        LocalTimeRange range3 = new LocalTimeRange(LocalTime.of(22, 3, 9), LocalTime.now());

        LessonRanges lessonRanges = new LessonRanges();
        lessonRanges.addRange(range1);
        lessonRanges.addRange(range2);
        lessonRanges.addRange(range3);

        System.out.println(lessonRanges.toString());
    }
}
