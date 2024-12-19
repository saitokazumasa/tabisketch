package com.tabisketch.bean.form;

import com.tabisketch.constant.Transportation;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalTime;

@Data
@AllArgsConstructor
public class CreatePlaceForm {
    @Min(1)
    private int googlePlaceId;

    @Min(1)
    private int dayId;

    @Min(0)
    private int budget;

    @NotNull
    private LocalTime startTime;

    @NotNull
    private LocalTime endTime;

    private LocalTime desiredStartTime;
    private LocalTime desiredEndTime;
    private String toPolyLine;
    private Integer toTravelTime;
    private Transportation toTransportation;
}
