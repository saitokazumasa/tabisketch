package com.tabisketch.bean.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendEditMailForm {
    @Email
    @NotBlank
    private String currentMail;

    @Email
    @NotBlank
    private String newMail;

    @NotBlank
    private String currentPassword;

    public static SendEditMailForm generate(final String currentMail) {
        return new SendEditMailForm(currentMail, "", "");
    }
}
