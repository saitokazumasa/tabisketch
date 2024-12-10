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
public class PlansMapperTest {
    @Autowired
    private IPlansMapper plansMapper;

    @ParameterizedTest
    @MethodSource("sampleId")
    @Sql({
            "classpath:/sql/CreateUser.sql",
            "classpath:/sql/CreatePlan.sql"
    })
    public void SELECTできるか(final int id) {
        final var planList = this.plansMapper.selectByUserId(id);
        assert planList != null;
        assert !planList.isEmpty();
    }

    private static Stream<Integer> sampleId() {
        final var id = 1;
        return Stream.of(id);
    }
}
