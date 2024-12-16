package com.tabisketch.bean.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordAuthToken {
    private int id;
    private UUID token;
    private String newPassword;
    private int userId;
    private LocalDateTime createdAt;

    /**
     * パスワード編集時に使う
     */
    public static PasswordAuthToken generate(final int userId,final String newPassword) {
        return new PasswordAuthToken(
                -1,
                UUID.randomUUID(),
                newMailAddress,
                userId,
                LocalDateTime.now()
        );
    }
}
