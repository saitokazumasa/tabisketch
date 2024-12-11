package com.tabisketch.bean.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MailAddressAuthToken {
    private int id;
    private UUID token;
    private String newMailAddress;
    private int userId;
    private LocalDateTime createdAt;

    /**
     * 新規登録時に使う
     */
    public static MailAddressAuthToken generate(final int userId) {
        return new MailAddressAuthToken(
                -1,
                UUID.randomUUID(),
                "",
                userId,
                LocalDateTime.now()
        );
    }

    /**
     * メール編集時に使う
     */
    public static MailAddressAuthToken generate(final int userId, final String newMailAddress) {
        return new MailAddressAuthToken(
                -1,
                UUID.randomUUID(),
                newMailAddress,
                userId,
                LocalDateTime.now()
        );
    }
}
