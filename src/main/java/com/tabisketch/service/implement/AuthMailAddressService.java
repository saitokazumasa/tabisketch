package com.tabisketch.service.implement;

import com.tabisketch.mapper.IMailAddressAuthTokensMapper;
import com.tabisketch.mapper.IUsersMapper;
import com.tabisketch.service.IAuthMailAddressService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class AuthMailAddressService implements IAuthMailAddressService {
    private final IMailAddressAuthTokensMapper mailAddressAuthTokensMapper;
    private final IUsersMapper usersMapper;

    public AuthMailAddressService(
            final IMailAddressAuthTokensMapper mailAddressAuthTokensMapper,
            final IUsersMapper usersMapper
    ) {
        this.mailAddressAuthTokensMapper = mailAddressAuthTokensMapper;
        this.usersMapper = usersMapper;
    }

    @Override
    @Transactional
    public boolean execute(final String token) {
        final var tokenUUID = UUID.fromString(token);
        final var mailAuth = this.mailAddressAuthTokensMapper.selectByToken(tokenUUID);

        if (mailAuth == null) return false;

        this.usersMapper.updateMailVerified(mailAuth.getUserId(), true);

        // メールアドレス編集の認証時はメールアドレスを更新
        if (mailAuth.getNewMailAddress() != null && !mailAuth.getNewMailAddress().isEmpty())
            this.usersMapper.updateMailAddress(mailAuth.getUserId(), mailAuth.getNewMailAddress());

        this.mailAddressAuthTokensMapper.deleteById(mailAuth.getId());
        return true;
    }
}
