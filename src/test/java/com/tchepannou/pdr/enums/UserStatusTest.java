package com.tchepannou.pdr.enums;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserStatusTest {
    @Test
    public void test_fromCode() throws Exception {
        assertThat(UserStatusEnum.fromCode('A')).isEqualTo(UserStatusEnum.ACTIVE);
        assertThat(UserStatusEnum.fromCode('a')).isEqualTo(UserStatusEnum.ACTIVE);

        assertThat(UserStatusEnum.fromCode('C')).isEqualTo(UserStatusEnum.CREATED);
        assertThat(UserStatusEnum.fromCode('c')).isEqualTo(UserStatusEnum.CREATED);

        assertThat(UserStatusEnum.fromCode('S')).isEqualTo(UserStatusEnum.SUSPENDED);
        assertThat(UserStatusEnum.fromCode('s')).isEqualTo(UserStatusEnum.SUSPENDED);

        assertThat(UserStatusEnum.fromCode('X')).isNull();
    }

    @Test
    public void test_fromText() throws Exception {
        assertThat(UserStatusEnum.fromText("ACTIVE")).isEqualTo(UserStatusEnum.ACTIVE);
        assertThat(UserStatusEnum.fromText("ACTiVe")).isEqualTo(UserStatusEnum.ACTIVE);

        assertThat(UserStatusEnum.fromText("CREATED")).isEqualTo(UserStatusEnum.CREATED);
        assertThat(UserStatusEnum.fromText("cREaTEd")).isEqualTo(UserStatusEnum.CREATED);

        assertThat(UserStatusEnum.fromText("SUSPENDED")).isEqualTo(UserStatusEnum.SUSPENDED);
        assertThat(UserStatusEnum.fromText("SUSPeNDEd")).isEqualTo(UserStatusEnum.SUSPENDED);

        assertThat(UserStatusEnum.fromText("X????")).isNull();
    }


    @Test
    public void test_fromText_OneChar() throws Exception {
        assertThat(UserStatusEnum.fromText("A")).isEqualTo(UserStatusEnum.ACTIVE);
        assertThat(UserStatusEnum.fromText("a")).isEqualTo(UserStatusEnum.ACTIVE);

        assertThat(UserStatusEnum.fromText("C")).isEqualTo(UserStatusEnum.CREATED);
        assertThat(UserStatusEnum.fromText("c")).isEqualTo(UserStatusEnum.CREATED);

        assertThat(UserStatusEnum.fromText("S")).isEqualTo(UserStatusEnum.SUSPENDED);
        assertThat(UserStatusEnum.fromText("s")).isEqualTo(UserStatusEnum.SUSPENDED);

        assertThat(UserStatusEnum.fromText("X")).isNull();
    }

    @Test
    public void test_fromText_null() throws Exception {
        assertThat(UserStatusEnum.fromText(null)).isNull();
    }
}
