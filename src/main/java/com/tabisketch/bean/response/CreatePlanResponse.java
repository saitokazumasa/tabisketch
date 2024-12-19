package com.tabisketch.bean.response;

import lombok.Getter;

@Getter
public class CreatePlanResponse {
    private enum Status {
        SUCCESS,
        FAILED
    }

    private final String status;
    private final int planId;

    private CreatePlanResponse(final String status, final int planId) {
        this.status = status;
        this.planId = planId;
    }

    public static CreatePlanResponse success(final int planId) {
        return new CreatePlanResponse(Status.SUCCESS.toString(), planId);
    }

    public static CreatePlanResponse failed() {
        return new CreatePlanResponse(Status.FAILED.toString(), -1);
    }
}
