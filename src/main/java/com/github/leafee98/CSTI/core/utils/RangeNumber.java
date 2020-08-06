package com.github.leafee98.CSTI.core.utils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RangeNumber {

    /**
     * convert "1,2,5-7" to [1,2,5,6,7].
     * (will sort elements in Set).
     * @param str string like "1,2,5-7"
     * @return List of Integer like [1,2,5,6,7]
     */
    public static List<Integer> parse(String str) {
        Set<Integer> result = new TreeSet<>();

        String[] numbs = str.split(",");
        for (String n : numbs) {
            int ind = n.indexOf('-');
            if (ind > 0) {
                int from = Integer.parseInt(n.substring(0, ind));
                int to = Integer.parseInt(n.substring(ind + 1));
                result.addAll(IntStream.range(from, to + 1).boxed().collect(Collectors.toList()));
            } else {
                result.add(Integer.parseInt(n));
            }
        }

        return new ArrayList<>(result);
    }

    /**
     * convert  [1,2,5,6,7] to "1,2,5-7".
     * @param arr  List of Integer like [1,2,5,6,7]
     * @return string like "1,2,5-7"
     */
    public static String render(List<Integer> arr) {
        int front = arr.get(0);
        int back = front;

        StringBuilder builder = new StringBuilder();

        for (int i = 1; i < arr.size(); ++i) {
            if (arr.get(i - 1).equals(arr.get(i) - 1)) {
                back = arr.get(i);
            } else {
                if (front != back) {
                    builder.append(front);
                    builder.append('-');
                }
                builder.append(back);
                builder.append(',');
                back = front = arr.get(i);
            }
        }

        if (front != back) {
            builder.append(front);
            builder.append('-');
        }
        builder.append(back);
        builder.append(',');

        return builder.substring(0, builder.length() - 1);
    }

    /**
     * convert  [1,2,5,6,7] to [{1},{2},{5,7}].
     * @param arr  List of Integer like [1,2,5,6,7]
     * @return string like [{1},{2},{5,7}]
     */
    public static List<PairNumber> renderToPair(List<Integer> arr) {
        List<PairNumber> result = new ArrayList<>();
        int front, back;
        back = front = arr.get(0);
        for (int i = 1; i < arr.size(); ++i) {
            if (arr.get(i - 1).equals(arr.get(i) - 1)) {
                back = arr.get(i);
            } else {
                if (front != back) {
                    result.add(new PairNumber(front, back));
                } else {
                    result.add(new PairNumber(front));
                }
                back = front = arr.get(i);
            }
        }

        if (front != back) {
            result.add(new PairNumber(front, back));
        } else {
            result.add(new PairNumber(front, front));
        }

        return result;
    }

}
