package com.github.leafee98.CSTI.core;

import com.github.leafee98.CSTI.core.bean.Lesson;
import com.github.leafee98.CSTI.core.bean.LessonSchedule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

public class LessonTest {

    @Test
    public void testLoad() {
        String input =
                "name:C语言课\n"
                + "type:专业必修课\n"
                + "teacher:某某大师\n"
                + "location:某校区某楼某层某室\n"
                + "remark:其实暂时没什么其他信息的啦\n"
                + "schedule:"
                + " 1,2,5,4|1|1,2,3;"
                + " 1-9,12-13,15-17|3|1-3";
        String name = "C语言课";
        String type = "专业必修课";
        String teacher = "某某大师";
        String location = "某校区某楼某层某室";
        String remark = "其实暂时没什么其他信息的啦";

        LessonSchedule schedule1 = new LessonSchedule();
        schedule1.setWeeks(Arrays.asList(1, 2, 4, 5));
        schedule1.setDayOfWeek(Collections.singletonList(1));
        schedule1.setLessons(Arrays.asList(1, 2, 3));

        LessonSchedule schedule2 = new LessonSchedule();
        schedule2.setWeeks(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 12, 13, 15, 16, 17));
        schedule2.setDayOfWeek(Collections.singletonList(3));
        schedule2.setLessons(Arrays.asList(1, 2, 3));

        Lesson lesson = Lesson.load(input);

        Assertions.assertEquals(name, lesson.getName());
        Assertions.assertEquals(type, lesson.getType());
        Assertions.assertEquals(teacher, lesson.getTeacher());
        Assertions.assertEquals(location, lesson.getLocation());
        Assertions.assertEquals(remark, lesson.getRemark());
        Assertions.assertEquals(2, lesson.getSchedule().size());
        Assertions.assertEquals(schedule1, lesson.getSchedule().get(0));
        Assertions.assertEquals(schedule2, lesson.getSchedule().get(1));
    }

}
