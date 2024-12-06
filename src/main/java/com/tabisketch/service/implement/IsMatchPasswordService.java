package com.tabisketch.service.implement;

import com.tabisketch.bean.entity.User;
import com.tabisketch.bean.form.IsMatchPasswordForm;
import com.tabisketch.bean.valueobject.EncryptedPassword;
import com.tabisketch.mapper.IUsersMapper;
import com.tabisketch.service.IIsMatchPasswordService;
import org.springframework.stereotype.Service;

@Service
public class IsMatchPasswordService implements IIsMatchPasswordService {
    final IUsersMapper usersMapper;

    public IsMatchPasswordService(final IUsersMapper usersMapper) {
        this.usersMapper = usersMapper;
    }

    @Override
    public boolean execute(final IsMatchPasswordForm isMatchPasswordForm) {
        final User user = this.usersMapper.selectByMail(isMatchPasswordForm.getMail());
        final var encryptedPassword = EncryptedPassword.generateFromEncryptedPassword(user.getPassword());

        return encryptedPassword.isMatch(isMatchPasswordForm.getPassword());
    }
}
