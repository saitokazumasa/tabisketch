package com.tabisketch.bean.form;

import com.tabisketch.bean.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterForm {
    @Email
    @NotBlank
    private String mailAddress;

    @NotBlank
    private String password;

    @NotBlank
    private String rePassword;

    public static RegisterForm empty() {
        return new RegisterForm("", "", "");
    }

    public User toUser() {
        return User.generate(this.mailAddress, this.password);
    }

    public boolean isNotMatchPasswordAndRePassword() {
        if (password == null || rePassword == null) return false;
        if (password.isEmpty() || rePassword.isEmpty()) return false;

        return !password.equals(rePassword);
    }
}
