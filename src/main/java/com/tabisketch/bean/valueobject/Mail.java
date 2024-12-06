package com.tabisketch.bean.valueobject;

import java.util.UUID;

public record Mail(String toMail, String subject, String content) {
    public static Mail generateRegisterMail(final String toMail, final UUID token) {
        return new Mail(
                toMail,
                "たびすけっち 登録確認のメール",
                "https://tabisketch.com/mail/confirm/" + token.toString()
        );
    }

    public static Mail generateEditMail(final String toMail, final UUID token) {
        return new Mail(
                toMail,
                "たびすけっち メールアドレス変更確認のメール",
                "https://tabisketch.com/mail/confirm/" + token.toString()
        );
    }
}
