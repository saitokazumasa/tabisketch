package com.tabisketch.service;

import com.tabisketch.valueobject.Mail;
import jakarta.mail.MessagingException;

public interface ISendMailService {
    void execute(final Mail mail) throws MessagingException;
}
