package com.tabisketch.bean.form;

import com.tabisketch.bean.entity.User;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateUserForm {
    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String rePassword;

    @AssertTrue(message = "パスワードが一致しません")
    public boolean isMatchPasswordAndRePassword() {
        if (this.password.isBlank()) return false;
        if (this.rePassword.isBlank()) return false;

        return password.equals(rePassword);
    }

    public User toUser() {
        return new User(
                -1,
                this.email,
                this.password,
                false
        );
    }
}
