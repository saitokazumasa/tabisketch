package com.tabisketch.mapper;

import com.tabisketch.bean.entity.User;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.jdbc.Sql;

import java.util.stream.Stream;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UsersMapperTest {
    @Autowired
    private IUsersMapper usersMapper;

    @ParameterizedTest
    @Sql("classpath:/sql/CreateUser.sql")
    @ValueSource(strings = {"sample@example.com"})
    public void SELECTできるか(final String mail) {
        final User user = this.usersMapper.selectByMail(mail);
        assert user != null;
    }

    @ParameterizedTest
    @MethodSource("INSERTできるかのテストデータ")
    public void INSERTできるか(final User user) {
        final int result = this.usersMapper.insert(user);
        assert result == 1;
        assert user.id != -1;
    }

    private static Stream<User> INSERTできるかのテストデータ() {
        final var u1 = new User(-1, "sample@example.com", "$2a$10$if7oiFZVmP9I59AOtzbSz.dWsdPUUuPTRkcIoR8iYhFpG/0COY.TO", false);
        return Stream.of(u1);
    }

    @ParameterizedTest
    @Sql("classpath:/sql/CreateUser.sql")
    @ValueSource(strings = {"sample2@example.com"})
    public void mailをUPDATEできるか(final String mail) {
        final int result = this.usersMapper.updateMail(1, mail);
        assert result == 1;
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    public void isMailVerifiedをUPDATEできるか(final boolean isMailVerified) {
        final int result = this.usersMapper.updateMailVerified(1, isMailVerified);
        assert result == 1;
    }

    @ParameterizedTest
    @Sql("classpath:/sql/CreateUser.sql")
    @ValueSource(strings = {"sample@example.com"})
    public void isExistMailが動作するか(final String mail) {
        assert this.usersMapper.isExistMail(mail) == 1;
        assert this.usersMapper.isExistMail(mail + "aaa") == 0;
    }
}
