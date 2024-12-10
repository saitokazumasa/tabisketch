package com.tabisketch.mapper;

import com.tabisketch.bean.entity.MailAddressAuthToken;
import org.apache.ibatis.annotations.*;

import java.util.UUID;

@Mapper
public interface IMailAddressAuthTokensMapper {
    @Insert("INSERT INTO mail_address_auth_tokens (user_id) VALUES (#{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "id,token")
    int insert(final MailAddressAuthToken mailAddressAuthToken);

    @Insert("INSERT INTO mail_address_auth_tokens (user_id, new_mail_address) VALUES (#{userId}, #{newMailAddress})")
    @Options(useGeneratedKeys = true, keyProperty = "id,token")
    int insertWithNewMail(final MailAddressAuthToken mailAddressAuthToken);

    @Select("SELECT * FROM mail_address_auth_tokens WHERE token = #{token}")
    MailAddressAuthToken selectByToken(final UUID token);

    @Delete("DELETE FROM mail_address_auth_tokens WHERE id = #{id}")
    int deleteById(final int id);
}
