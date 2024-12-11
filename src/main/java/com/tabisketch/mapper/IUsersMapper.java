package com.tabisketch.mapper;

import com.tabisketch.bean.entity.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface IUsersMapper {
    @Insert("INSERT INTO users (mail_address, password) VALUES (#{mailAddress}, #{password})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(final User user);

    @Select("SELECT * FROM users WHERE mail_address = #{mailAddress}")
    User selectByMailAddress(final String mailAddress);

    @Update("UPDATE users SET mail_address = #{mailAddress} WHERE id = #{id}")
    int updateMailAddress(final int id, final String mailAddress);

    @Update("UPDATE users SET is_mail_verified = #{isMailVerified} WHERE id = #{id}")
    int updateMailVerified(final int id, final boolean isMailVerified);

    @Select("SELECT COUNT(*) FROM users WHERE mail_address = #{mailAddress}")
    int isExistMailAddress(final String mailAddress);
}
