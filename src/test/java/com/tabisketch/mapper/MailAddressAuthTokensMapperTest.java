package com.tabisketch.mapper;

import com.tabisketch.bean.entity.MailAddressAuthToken;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.jdbc.Sql;

import java.util.UUID;
import java.util.stream.Stream;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MailAddressAuthTokensMapperTest {
    @Autowired
    private IMailAddressAuthTokensMapper mailAddressAuthTokensMapper;

    @ParameterizedTest
    @MethodSource("sampleMailAddressAuthToken")
    @Sql("classpath:/sql/CreateUser.sql")
    public void INSERTできるか(final MailAddressAuthToken mailAddressAuthToken) {
        final var result = this.mailAddressAuthTokensMapper.insert(mailAddressAuthToken);
        assert result == 1;
        assert mailAddressAuthToken.getId() != -1;
    }

    @ParameterizedTest
    @MethodSource("sampleToken")
    @Sql({
            "classpath:/sql/CreateUser.sql",
            "classpath:/sql/CreateMailAuthenticationToken.sql"
    })
    public void SELECTできるか(final UUID token) {
        final var mailAuthenticationToken = this.mailAddressAuthTokensMapper.selectByToken(token);
        assert mailAuthenticationToken != null;
    }

    @ParameterizedTest
    @MethodSource("sampleId")
    @Sql({
            "classpath:/sql/CreateUser.sql",
            "classpath:/sql/CreateMailAuthenticationToken.sql"
    })
    public void DELETEできるか(final int id) {
        final var result = this.mailAddressAuthTokensMapper.deleteById(id);
        assert result == 1;
    }

    private static Stream<Integer> sampleId() {
        final var id = 1;
        return Stream.of(id);
    }

    private static Stream<UUID> sampleToken() {
        final var token = UUID.fromString("a2e69add-9d95-4cf1-a59b-cedbb95dcd6b");
        return Stream.of(token);
    }

    private static Stream<MailAddressAuthToken> sampleMailAddressAuthToken() {
        final var mailAddressAuthToken = MailAddressAuthToken.generate(1);
        final var mailAddressAuthToken2 = MailAddressAuthToken.generate(1, "sample2@example.com");
        return Stream.of(mailAddressAuthToken, mailAddressAuthToken2);
    }
}
