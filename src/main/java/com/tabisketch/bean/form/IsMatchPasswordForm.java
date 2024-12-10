package com.tabisketch.bean.form;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class IsMatchPasswordForm {
    private String mailAddress;
    private String password;
}
