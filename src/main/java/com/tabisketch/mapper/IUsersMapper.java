package com.tabisketch.mapper;

import com.tabisketch.bean.entity.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface IUsersMapper {
    @Insert("INSERT INTO users (mail, password) VALUES (#{mail}, #{password})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(final User user);

    @Select("SELECT * FROM users WHERE mail = #{mail}")
    User selectByMail(final String mail);

    @Update("UPDATE users SET mail = #{mail} WHERE id = #{id}")
    int updateMail(final int id, final String mail);

    @Update("UPDATE users SET is_mail_verified = #{isMailVerified} WHERE id = #{id}")
    int updateMailVerified(final int id, final boolean isMailVerified);

    @Select("SELECT COUNT(*) FROM users WHERE mail = #{mail}")
    int isExistMail(final String mail);
}
