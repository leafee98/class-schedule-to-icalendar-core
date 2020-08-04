package com.github.leafee98.CSTI.core.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RangeNumber {

    public static List<Integer> parse(String str) {
        Set<Integer> result = new HashSet<>();

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

    public static String render(List<Integer> arr) {
        int front = arr.get(0);
        int back = front;

        StringBuilder builder = new StringBuilder();

        for (int i = 1; i < arr.size(); ++i) {
            if (arr.get(i - 1).equals(arr.get(i) - 1)) {
                back = arr.get(i);
            } else {
                builder.append(front);
                builder.append('-');
                builder.append(back);
                builder.append(',');
                // for (int x = front; x <= back; ++x) {
                //     builder.append(x);
                //     builder.append(',');
                // }
                front = arr.get(i);
            }
        }

        if (back == arr.get(arr.size() - 1)) {
            builder.append(front);
            builder.append('-');
            builder.append(back);
            builder.append(',');
            // for (int x = front; x <= back; ++x) {
            //     builder.append(x);
            //     builder.append(',');
            // }
        } else {
            builder.append(front);
            builder.append(',');
        }

        return builder.substring(0, builder.length() - 1);

    }

}
