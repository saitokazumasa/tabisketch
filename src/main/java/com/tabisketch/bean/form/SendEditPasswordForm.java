package com.tabisketch.bean.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SendEditPasswordForm{
    @Email
    @NotBlank
    private String currentPassword;

    @Email
    @NotBlank
    private String newPassword;

    @NotBlank
    private String confirmPassword;
    public static SendEditPasswordForm empty(){return new SendEditPasswordForm("","","");}
}
