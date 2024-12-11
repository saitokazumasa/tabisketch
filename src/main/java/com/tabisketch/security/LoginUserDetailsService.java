package com.tabisketch.security;

import com.tabisketch.bean.entity.User;
import com.tabisketch.mapper.IUsersMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class LoginUserDetailsService implements UserDetailsService {
    private final IUsersMapper usersMapper;

    public LoginUserDetailsService(final IUsersMapper usersMapper) {
        this.usersMapper = usersMapper;
    }

    @Override
    public UserDetails loadUserByUsername(final String mail) throws UsernameNotFoundException {
        final User user = usersMapper.selectByMailAddress(mail);

        if (user == null) throw new UsernameNotFoundException(mail);

        return new LoginUserDetails(user);
    }
}
