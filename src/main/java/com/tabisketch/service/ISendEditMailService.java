package com.tabisketch.service;

import com.tabisketch.bean.form.SendEditMailForm;
import jakarta.mail.MessagingException;

public interface ISendEditMailService {
    void execute(final SendEditMailForm sendEditMailForm) throws MessagingException;
}
