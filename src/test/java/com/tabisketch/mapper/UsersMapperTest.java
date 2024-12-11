package com.tabisketch.mapper;

import com.tabisketch.bean.entity.User;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
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
    @MethodSource("sampleMailAddress")
    @Sql("classpath:/sql/CreateUser.sql")
    public void SELECTできるか(final String mailAddress) {
        final var user = this.usersMapper.selectByMailAddress(mailAddress);
        assert user != null;
    }

    @ParameterizedTest
    @MethodSource("sampleUser")
    public void INSERTできるか(final User user) {
        final var result = this.usersMapper.insert(user);
        assert result == 1;
        assert user.getId() != -1;
    }

    @ParameterizedTest
    @MethodSource("sampleUpdateUser")
    @Sql("classpath:/sql/CreateUser.sql")
    public void mailをUPDATEできるか(final User user) {
        final var result = this.usersMapper.updateMailAddress(user.getId(), user.getMailAddress());
        assert result == 1;
    }

    @ParameterizedTest
    @MethodSource("sampleId")
    @Sql("classpath:/sql/CreateUser.sql")
    public void isMailVerifiedをUPDATEできるか(final int id) {
        final var result1 = this.usersMapper.updateMailVerified(id, true);
        final var result2 = this.usersMapper.updateMailVerified(id, false);
        assert result1 == 1;
        assert result2 == 1;
    }

    @ParameterizedTest
    @MethodSource("sampleMailAddress")
    @Sql("classpath:/sql/CreateUser.sql")
    public void isExistMailが動作するか(final String mailAddress) {
        final var result1 = this.usersMapper.isExistMailAddress(mailAddress);
        final var result2 = this.usersMapper.isExistMailAddress(mailAddress + "aaa");
        assert result1 == 1;
        assert result2 == 0;
    }

    private static Stream<Integer> sampleId() {
        final var id = 1;
        return Stream.of(id);
    }

    private static Stream<String> sampleMailAddress() {
        final var mailAddress = "sample@example.com";
        return Stream.of(mailAddress);
    }

    private static Stream<User> sampleUser() {
        final var user = User.generate("sample@example.com", "password");
        return Stream.of(user);
    }

    private static Stream<User> sampleUpdateUser() {
        final var user = new User (1, "sample2@example.com", "password", false);
        return Stream.of(user);
    }
}
