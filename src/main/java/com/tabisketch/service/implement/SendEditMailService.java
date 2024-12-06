package com.tabisketch.service.implement;

import com.tabisketch.bean.entity.MailAuthenticationToken;
import com.tabisketch.bean.entity.User;
import com.tabisketch.bean.form.SendEditMailForm;
import com.tabisketch.bean.valueobject.Mail;
import com.tabisketch.mapper.IMailAuthenticationTokensMapper;
import com.tabisketch.mapper.IUsersMapper;
import com.tabisketch.service.ISendEditMailService;
import com.tabisketch.service.ISendMailService;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;

@Service
public class SendEditMailService implements ISendEditMailService {
    private final IUsersMapper usersMapper;
    private final IMailAuthenticationTokensMapper mailAuthenticationTokensMapper;
    private final ISendMailService sendMailService;

    public SendEditMailService(
            final IUsersMapper usersMapper,
            final IMailAuthenticationTokensMapper mailAuthenticationTokensMapper,
            final ISendMailService sendMailService
    ) {
        this.usersMapper = usersMapper;
        this.mailAuthenticationTokensMapper = mailAuthenticationTokensMapper;
        this.sendMailService = sendMailService;
    }

    @Override
    public void execute(final SendEditMailForm sendEditMailForm) throws MessagingException {
        final User user = this.usersMapper.selectByMail(sendEditMailForm.getCurrentMail());

        final var mailAuthToken = MailAuthenticationToken.generate(user.id, sendEditMailForm.getNewMail());
        this.mailAuthenticationTokensMapper.insertWithNewMail(mailAuthToken);

        final var mail = Mail.generateEditMail(sendEditMailForm.getNewMail(), mailAuthToken.getToken());
        this.sendMailService.execute(mail);
    }
}
