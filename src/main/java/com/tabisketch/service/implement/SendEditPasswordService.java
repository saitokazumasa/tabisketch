package com.tabisketch.service.implement;

import com.tabisketch.valueobject.Mail;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;

public class SendEditPasswordService {

    @Value("${MAIL_USERNAME}@gmail.com")
    private String formMail;

    private final JavaMailSender mailSender;

    public SendEditPasswordService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Async
    public void execute(final Mail mail) throws MessagingException {
        final var message = this.mailSender.createMimeMessage();
        final var messageHelper = new MimeMessageHelper(message, true);

        messageHelper.setFrom(this.formMail);
        messageHelper.setTo(mail.getToMail());
        messageHelper.setSubject(mail.getSubject());

        this.mailSender.send(message);
    }
}
