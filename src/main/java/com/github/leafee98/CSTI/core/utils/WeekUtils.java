package com.github.leafee98.CSTI.core.utils;

import com.github.leafee98.CSTI.core.exceptions.TimeZoneException;

import java.time.Month;

/**
 *
 SU MO TU WE TH FA SA
 1  2  3  4  5  6  7
 8  9 10 11 12 13 14
 15 16 17 18 19 20 21
 22 23 24 25 26 27 28
 29 30 31

 SU MO TU WE TH FA SA
 1  2  3  4  5  6
 7  8  9 10 11 12 13
 14 15 16 17 18 19 20
 21 22 23 24 25 26 27
 28 29 30 31

 SU MO TU WE TH FA SA
 1  2  3  4  5
 6  7  8  9 10 11 12
 13 14 15 16 17 18 19
 20 21 22 23 24 25 26
 27 28 29 30 31

 SU MO TU WE TH FA SA
 1  2  3  4
 5  6  7  8  9 10 11
 12 13 14 15 16 17 18
 19 20 21 22 23 24 25
 26 27 28 29 30 31

 SU MO TU WE TH FA SA
 1  2  3
 4  5  6  7  8  9 10
 11 12 13 14 15 16 17
 18 19 20 21 22 23 24
 25 26 27 28 29 30 31

 SU MO TU WE TH FA SA
 1  2
 3  4  5  6  7  8  9
 10 11 12 13 14 15 16
 17 18 19 20 21 22 23
 24 25 26 27 28 29 30
 31

 SU MO TU WE TH FA SA
 1
 2  3  4  5  6  7  8
 9 10 11 12 13 14 15
 16 17 18 19 20 21 22
 23 24 25 26 27 28 29
 30 31


 假设周X
 第一次一定在当月1-7日之间  [1+7*0 ~ 7*1] == [1 + 7*(y-1), 1 + 7*y) ==> y=(d-1)/7+1
 第二次一点在挡雨8-14日之间 [1+7*1 ~ 7*2]
 第三次一定在当月15-21日之间[1+7*2 ~ 7*3]
 第四次一定在当月22-28日之间[1+7*3 ~ 7*4]
 第五次一定在当月25-31日之间[1+7*4 ~ Z]

 1+7(y-1) <= d < 1+7y
 ===> y <= (d-1)/7+1
 ===> y >  (d-1)/7
 ======> y=floor((d-1)/7+1)

 每正数周次起始日d = 1 + 7(y-1)
 每正数周次结束如d = 7y

 假设周X, 当月最大日为Z
 倒数第一次一定在当月[Z-7*1+1 ~ Z-7*0](25 ~ 31)
 倒数第二次一定在当月[Z-7*2+1 ~ Z-7*1](18 ~ 24)
 倒数第三次一定在当月[Z-7*3+1 ~ Z-7*2](11 ~ 17)
 倒数第四次一定在当月[Z-7*4+1 ~ Z-7*3](4  ~ 10)
 倒数第五次一定在当月[Z-7*5+1 ~ Z-7*4](1  ~ 3)

 z-7y < d <= z-7(y-1)
 ===> y <= (z+7-d)/7
 ===> y >  (z-d)/7
 ======> y = floor((z-d)/7+1)

 每倒数周次起始日d = z - 7y + 1
 每倒数周次结束如d = z - 7(y-1)


 z=28, start:   22  15   8   1  -6 |    1   8  15  22  29
 z=28, end:     28  21  14   7   0 |    7  14  21  28  35

 z=29, start:   23  16   9   2  -5 |    1   8  15  22  29
 z=29, end:     29  22  15   8   1 |    7  14  21  28  35

 z=30, start:   24  17  10   3  -4 |    1   8  15  22  29
 z=30, end:     30  23  16   9   2 |    7  14  21  28  35

 z=31, start:   25  18  11   4  -3 |    1   8  15  22  29
 z=31, end:     31  24  17  10   3 |    7  14  21  28  35
 */

/**
 * member function to make it easy to rewrite by inherit
 */
public class WeekUtils {

//    private static final Set<Integer> afterPositive =
//            new TreeSet<>(Stream.of(1, 8, 15, 22, 29).collect(Collectors.toSet()));
//    private static final Set<Integer> beforePositive =
//            new TreeSet<>(Stream.of(7, 14, 21, 28, 35).collect(Collectors.toSet()));

//    private static Map<Integer, Set<Integer>> afterNegative;
//    private static Map<Integer, Set<Integer>> beforeNegative;

//    static {
//        for (int z = 28; z <= 31; ++z) {
//            Set<Integer> set = new TreeSet<>();
//            for (int i = 1; i <= 5; ++i) {
//                int day = z - 7 * i + 1;
//                if (day > 0 && day <= z) {
//                    set.add(day);
//                }
//            }
//            afterNegative.put(z, set);
//        }

//        for (int z = 28; z <= 31; ++z) {
//            Set<Integer> set = new TreeSet<>();
//            for (int i = 1; i <= 5; ++i) {
//                int day = z - 7 * (i - 1);
//                if (day > 0 && day <= z) {
//                    set.add(day);
//                }
//            }
//            beforeNegative.put(z, set);
//        }

//    }



    public int onOrAfter(int dayOfMonth, int monthLength) {
        // // on or after, positive
        // // 每正数周次起始日 d = 1 + 7(y-1) --> y = (d - 1) / 7 + 1
        // if ((dayOfMonth - 1) % 7 != 0 || dayOfMonth > monthLength) {
        //     throw new TimeZoneException("failed to determine the ordinal of week with dayOfMonth: " + dayOfMonth);
        // } else {
        //     return (dayOfMonth - 1) / 7 + 1;
        // }

        // // on or after, negative
        // // 每倒数周次起始日 d = z - 7y + 1 --> y = (z - d + 1) / 7
        // if ((monthLength - dayOfMonth + 1) % 7 != 0 || dayOfMonth < 1) {
        //     throw new TimeZoneException("failed to determine the ordinal of week with dayOfMonth: " + dayOfMonth);
        // } else {
        //     return -(monthLength - dayOfMonth + 1) / 7;
        // }

        if (dayOfMonth < 1 || dayOfMonth > monthLength) {
            throw new TimeZoneException("failed to determine the ordinal of week with dayOfMonth: " + dayOfMonth);
        }

        if ((dayOfMonth - 1) % 7 == 0) {
            return (dayOfMonth - 1) / 7 + 1;
        } else if ((monthLength - dayOfMonth + 1) % 7 == 0) {
            return -1 * ((monthLength - dayOfMonth + 1) / 7);
        } else {
            throw new TimeZoneException("failed to determine the ordinal of week with dayOfMonth: " + dayOfMonth);
        }
    }

    public int byIndicator(int dayOfMonthIndicator, int monthLength) {
        // if (dayOfMonthIndicator > 0) {
        //     if ((dayOfMonthIndicator - 1) % 7 == 0) {
        //         return (dayOfMonthIndicator - 1) / 7 + 1;
        //     }
        // } else {
        //     dayOfMonthIndicator = -dayOfMonthIndicator;
        //     if ((dayOfMonthIndicator - 1) % 7 == 0) {
        //         return -((dayOfMonthIndicator - 1) / 7 + 1);
        //     }
        // }
        // throw new TimeZoneException("failed to determine the ordinal of week with dayOfMonth: " + dayOfMonthIndicator);

        if (dayOfMonthIndicator > 0) {
            return onOrAfter(dayOfMonthIndicator, monthLength);
        } else {
            int day = monthLength + dayOfMonthIndicator + 1;
            return onOrBefore(day, monthLength);
        }
    }

    /**
     * February not occurred in Java Timezone, so treat month's length as non-leap year
     */
    public int byIndicator(int dayOfMonthIndicator, Month month) {
        return byIndicator(dayOfMonthIndicator, month.length(false));
    }


    public int onOrBefore(int dayOfMonth, int monthLength) {
        if (dayOfMonth < 1 || dayOfMonth > monthLength) {
            throw new TimeZoneException("failed to determine the ordinal of week with dayOfMonth: " + dayOfMonth);
        }

        // 每正数周次结束日d = 7y          -> y = d / 7
        // 每倒数周次结束日d = z - 7(y-1)  -> y = (z - d) / 7 + 1
        if (dayOfMonth % 7 == 0) {
            // positive
            return dayOfMonth / 7;
        } else if ((monthLength - dayOfMonth) % 7 == 0) {
            // negative
            return -1 * ((monthLength - dayOfMonth) / 7 + 1);
        } else {
            throw new TimeZoneException("failed to determine the ordinal of week with dayOfMonth: " + dayOfMonth);
        }
    }

    public int ordinalOfWeek(int dayOfMonth) {
        return (dayOfMonth - 1) / 7 + 1;
    }

    public int reversedOrdinalOfWeek(int dayOfMonth, int monthLength) {
        return (monthLength - dayOfMonth) / 7 + 1;
    }

}
