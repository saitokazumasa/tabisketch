package com.tabisketch.mapper;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.jdbc.Sql;

import java.util.stream.Stream;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PasswordAuthTokensMapperTest {

    @Autowired
    private IPassWordAuthenticationTokensMapper passwordAuthTokensMapper;

    @ParameterizedTest
    @Sql("classpath:/sql/CreateUser.sql")
    @MethodSource("testDataForInsertingNewPasswordToken")
    public void testInsertNewPasswordToken(final IPassWordAuthenticationTokensMapper passwordAuthenticationToken) {
        // INSERTメソッドの実行
        final int result = this.passwordAuthTokensMapper.insert(passwordAuthenticationToken);

        // 結果の検証
        assert result == 1 : "挿入に失敗しました。";
        assert passwordAuthenticationToken.getId() != -1 : "トークンのIDが正しく設定されていません。";
    }

    private static Stream<PasswordAuthenticationToken> testDataForInsertingNewPasswordToken() {
        // サンプルデータの生成
        final PasswordAuthenticationToken token = PasswordAuthenticationToken.generate(1, "sample@example.com");
        return Stream.of(token);
    }
}
