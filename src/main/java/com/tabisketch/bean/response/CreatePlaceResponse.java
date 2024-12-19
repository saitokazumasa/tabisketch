package com.tabisketch.bean.response;

import lombok.Getter;

// TODO: IResponseを継承する
@Getter
public class CreatePlaceResponse {
    private enum Status {
        Success,
        Failed
    }

    private final String status;

    private CreatePlaceResponse(final String status) {
        this.status = status;
    }

    public static CreatePlaceResponse success() {
        return new CreatePlaceResponse(Status.Success.toString());
    }

    public static CreatePlaceResponse failed() {
        return new CreatePlaceResponse(Status.Failed.toString());
    }
}
