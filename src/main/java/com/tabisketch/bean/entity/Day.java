package com.tabisketch.bean.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Day {
    private int id;
    private int day;
    private int planId;
    private int walkThreshold;
    private String preferTransportationListBinary;
    private boolean useTollRoad;
    private boolean useFerry;

    public static Day generate(
            final int day,
            final int planId,
            final int walkThreshold,
            final String preferTransportationListBinary
    ) {
        return new Day(
                -1,
                day,
                planId,
                walkThreshold,
                preferTransportationListBinary,
                true,
                true
        );
    }
}
