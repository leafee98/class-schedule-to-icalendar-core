package com.github.leafee98.CSTI.core;

import com.github.leafee98.CSTI.core.utils.RangeNumber;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RangeNumberTest {
    @Test
    public void testParse() {
        String input = "1,2,3-9,10,11,12-20";
        String expected = "1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20";

        List<Integer> list = RangeNumber.parse(input);

        StringBuilder builder = new StringBuilder();
        for (Integer i : list) {
            builder.append(i);
            builder.append(',');
        }

        Assertions.assertEquals(expected, builder.substring(0, builder.length() - 1));
    }

    @Test
    public void testRender() {
        String expected = "1-20";
        List<Integer> input = IntStream.range(1, 21).boxed().collect(Collectors.toList());

        String actual = RangeNumber.render(input);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testRenderToPair() {
        String expected = "[{1,3}, {5}, {7,10}, {19}]";
        String input = "1,2,3,5,7,8,9,10,19";

        String actual = RangeNumber.renderToPair(RangeNumber.parse(input)).toString();
        Assertions.assertEquals(expected, actual);
    }

}
