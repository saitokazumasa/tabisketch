package com.tabisketch.mapper;

import com.tabisketch.bean.entity.MailAuthenticationToken;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.jdbc.Sql;

import java.util.UUID;
import java.util.stream.Stream;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MailAuthenticationTokensMapperTest {
    @Autowired
    private IMailAuthenticationTokensMapper mailAuthenticationTokensMapper;

    @ParameterizedTest
    @Sql("classpath:/sql/CreateUser.sql")
    @MethodSource("INSERTできるかのテストデータ")
    public void INSERTできるか(final MailAuthenticationToken mailAuthenticationToken) {
        final int result = this.mailAuthenticationTokensMapper.insert(mailAuthenticationToken);
        assert result == 1;
        assert mailAuthenticationToken.getId() != -1;
    }

    private static Stream<MailAuthenticationToken> INSERTできるかのテストデータ() {
        final var m = MailAuthenticationToken.generate(1);
        return Stream.of(m);
    }

    @ParameterizedTest
    @Sql("classpath:/sql/CreateUser.sql")
    @MethodSource("新しいメール付きでINSERTできるかのテストデータ")
    public void 新しいメール付きでINSERTできるか(final MailAuthenticationToken mailAuthenticationToken) {
        final int result = this.mailAuthenticationTokensMapper.insertWithNewMail(mailAuthenticationToken);
        assert result == 1;
        assert mailAuthenticationToken.getId() != -1;
    }

    private static Stream<MailAuthenticationToken> 新しいメール付きでINSERTできるかのテストデータ() {
        final var m = MailAuthenticationToken.generate(1, "sample@example.com");
        return Stream.of(m);
    }

    @ParameterizedTest
    @Sql({
            "classpath:/sql/CreateUser.sql",
            "classpath:/sql/CreateMailAuthenticationToken.sql"
    })
    @MethodSource("SELECTできるかのテストデータ")
    public void SELECTできるか(final UUID token) {
        final MailAuthenticationToken mailAuthenticationToken =
                this.mailAuthenticationTokensMapper.selectByToken(token);
        assert mailAuthenticationToken != null;
    }

    private static Stream<UUID> SELECTできるかのテストデータ() {
        final var u = UUID.fromString("a2e69add-9d95-4cf1-a59b-cedbb95dcd6b");
        return Stream.of(u);
    }

    @ParameterizedTest
    @Sql({
            "classpath:/sql/CreateUser.sql",
            "classpath:/sql/CreateMailAuthenticationToken.sql"
    })
    @ValueSource(ints = {1})
    public void DELETEできるか(final int id) {
        final int result = this.mailAuthenticationTokensMapper.deleteById(id);
        assert result == 1;
    }
}
