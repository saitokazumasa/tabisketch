package com.tabisketch.service.implement;

import com.tabisketch.mapper.IUsersMapper;
import com.tabisketch.service.IIsExistMailService;
import org.springframework.stereotype.Service;

@Service
public class IsExistMailService implements IIsExistMailService {
    private final IUsersMapper usersMapper;

    public IsExistMailService(final IUsersMapper usersMapper) {
        this.usersMapper = usersMapper;
    }

    @Override
    public boolean execute(final String mail) {
        return this.usersMapper.isExistMailAddress(mail) == 1;
    }
}
