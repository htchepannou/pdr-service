package com.tchepannou.pdr.domain;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserStatusTest {
    @Test
    public void test_fromCode() throws Exception {
        assertThat(UserStatus.fromCode('A')).isEqualTo(UserStatus.ACTIVE);
        assertThat(UserStatus.fromCode('a')).isEqualTo(UserStatus.ACTIVE);

        assertThat(UserStatus.fromCode('C')).isEqualTo(UserStatus.CREATED);
        assertThat(UserStatus.fromCode('c')).isEqualTo(UserStatus.CREATED);

        assertThat(UserStatus.fromCode('S')).isEqualTo(UserStatus.SUSPENDED);
        assertThat(UserStatus.fromCode('s')).isEqualTo(UserStatus.SUSPENDED);

        assertThat(UserStatus.fromCode('X')).isEqualTo(UserStatus.UNKNOWN);
    }

    @Test
    public void test_fromText() throws Exception {
        assertThat(UserStatus.fromText("ACTIVE")).isEqualTo(UserStatus.ACTIVE);
        assertThat(UserStatus.fromText("ACTiVe")).isEqualTo(UserStatus.ACTIVE);

        assertThat(UserStatus.fromText("CREATED")).isEqualTo(UserStatus.CREATED);
        assertThat(UserStatus.fromText("cREaTEd")).isEqualTo(UserStatus.CREATED);

        assertThat(UserStatus.fromText("SUSPENDED")).isEqualTo(UserStatus.SUSPENDED);
        assertThat(UserStatus.fromText("SUSPeNDEd")).isEqualTo(UserStatus.SUSPENDED);

        assertThat(UserStatus.fromText("X????")).isEqualTo(UserStatus.UNKNOWN);
    }


    @Test
    public void test_fromText_OneChar() throws Exception {
        assertThat(UserStatus.fromText("A")).isEqualTo(UserStatus.ACTIVE);
        assertThat(UserStatus.fromText("a")).isEqualTo(UserStatus.ACTIVE);

        assertThat(UserStatus.fromText("C")).isEqualTo(UserStatus.CREATED);
        assertThat(UserStatus.fromText("c")).isEqualTo(UserStatus.CREATED);

        assertThat(UserStatus.fromText("S")).isEqualTo(UserStatus.SUSPENDED);
        assertThat(UserStatus.fromText("s")).isEqualTo(UserStatus.SUSPENDED);

        assertThat(UserStatus.fromText("X")).isEqualTo(UserStatus.UNKNOWN);
    }

    @Test
    public void test_fromText_null() throws Exception {
        assertThat(UserStatus.fromText(null)).isEqualTo(UserStatus.UNKNOWN);
    }
}
