package com.tabisketch.valueobject;

import lombok.Getter;

import java.util.UUID;

@Getter
public class Mail {
    final String toMail;
    final String subject;
    final String content;

    private Mail(final String toMail, final String subject, final String content) {
        this.toMail = toMail;
        this.subject = subject;
        this.content = content;
    }

    /**
     * 新規登録メールを生成する
     */
    public static Mail generateRegisterMail(final String toMail, final UUID token) {
        return new Mail(
                toMail,
                "たびすけっち 登録確認のメール",
                "https://tabisketch.com/mail/confirm/" + token.toString()
        );
    }

    /**
     * メールアドレス編集メールを生成する
     */
    public static Mail generateEditMail(final String toMail, final UUID token) {
        return new Mail(
                toMail,
                "たびすけっち メールアドレス変更確認のメール",
                "https://tabisketch.com/mail/confirm/" + token.toString()
        );
    }
}
