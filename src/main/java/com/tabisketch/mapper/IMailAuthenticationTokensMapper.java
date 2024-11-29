package com.tabisketch.mapper;

import com.tabisketch.bean.entity.MailAuthenticationToken;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

@Mapper
public interface IMailAuthenticationTokensMapper {
    @Insert("INSERT INTO mail_authentication_tokens (token, user_id) " +
            "VALUES (#{token}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(final MailAuthenticationToken mailAuthenticationToken);
}
