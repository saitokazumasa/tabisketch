package com.tabisketch.service.implement;

import com.tabisketch.bean.entity.MAAToken;
import com.tabisketch.bean.entity.User;
import com.tabisketch.exception.DeleteFailedException;
import com.tabisketch.exception.UpdateFailedException;
import com.tabisketch.mapper.IMAATokensMapper;
import com.tabisketch.mapper.IUsersMapper;
import com.tabisketch.service.IAuthMailAddressService;
import com.tabisketch.util.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class AuthMailAddressService implements IAuthMailAddressService {
    private final IMAATokensMapper maaTokensMapper;
    private final IUsersMapper usersMapper;

    public AuthMailAddressService(
            final IMAATokensMapper maaTokensMapper,
            final IUsersMapper usersMapper
    ) {
        this.maaTokensMapper = maaTokensMapper;
        this.usersMapper = usersMapper;
    }

    @Override
    @Transactional
    public void execute(final String maaTokenStr) throws UpdateFailedException, DeleteFailedException {
        final var token = UUID.fromString(maaTokenStr);
        final MAAToken maaToken = this.maaTokensMapper.selectByToken(token);
        final User user = this.usersMapper.selectById(maaToken.getUserId());

        // メールアドレスの更新があるかどうかで作るインスタンスを変える
        final User newUser = StringUtils.notNullAndNotEmpty(maaToken.getNewMailAddress()) ?
                new User(
                        user.getId(),
                        maaToken.getNewMailAddress(),
                        user.getPassword(),
                        true
                ) :
                new User(
                        user.getId(),
                        user.getMailAddress(),
                        user.getPassword(),
                        true
                );

        final int updateResult = this.usersMapper.update(newUser);
        final int deleteResult = this.maaTokensMapper.delete(maaToken.getId());

        if (updateResult != 1) throw new UpdateFailedException("Userの更新に失敗しました。");
        if (deleteResult != 1) throw new DeleteFailedException("MAATokenの削除に失敗しました。");
    }
}
