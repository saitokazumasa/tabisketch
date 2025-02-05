package com.tabisketch.optimize_route.bean.valueobject;

import lombok.Getter;

import java.time.LocalDateTime;

/// 出発地点
@Getter
public class DeparturePoint {
    /// 識別子
    private final int id;
    /// 地名
    private final String name;
    /// 住所
    private final String address;
    /// 出発日時
    private final LocalDateTime departureDateTime;

    public DeparturePoint() {
        this.id = -1;
        this.name = null;
        this.address = null;
        this.departureDateTime = null;
    }

    public DeparturePoint(final int id, final String name, final String address, final LocalDateTime departureDateTime) {
        assert id > 0;
        assert name != null && !name.isEmpty();
        assert address != null && !address.isEmpty();
        assert departureDateTime != null;

        this.id = id;
        this.name = name;
        this.address = address;
        this.departureDateTime = departureDateTime;
    }
}
