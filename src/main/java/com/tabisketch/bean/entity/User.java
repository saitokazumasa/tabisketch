package com.tabisketch.bean.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private int id;
    private String mailAddress;
    private String password;
    private Boolean mailAddressAuthenticated;

    public static User generate(final String mailAddress, final String password) {
        return new User(
                -1,
                mailAddress,
                password,
                false
        );
    }
}
