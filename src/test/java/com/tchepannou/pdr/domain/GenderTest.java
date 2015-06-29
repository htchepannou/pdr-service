package com.tchepannou.pdr.domain;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GenderTest {

    @Test
    public void test_fromCode() throws Exception {
        assertThat(Gender.fromCode('M')).isEqualTo(Gender.MALE);
        assertThat(Gender.fromCode('m')).isEqualTo(Gender.MALE);

        assertThat(Gender.fromCode('F')).isEqualTo(Gender.FEMALE);
        assertThat(Gender.fromCode('f')).isEqualTo(Gender.FEMALE);

        assertThat(Gender.fromCode('-')).isEqualTo(Gender.UNKNOWN);
    }

    @Test
    public void test_fromText() throws Exception {
        assertThat(Gender.fromText("MALE")).isEqualTo(Gender.MALE);
        assertThat(Gender.fromText("mALe")).isEqualTo(Gender.MALE);

        assertThat(Gender.fromText("FEMALE")).isEqualTo(Gender.FEMALE);
        assertThat(Gender.fromText("fEmAle")).isEqualTo(Gender.FEMALE);

        assertThat(Gender.fromText("-??")).isEqualTo(Gender.UNKNOWN);
    }


    @Test
    public void test_fromText_null() throws Exception {
        assertThat(Gender.fromText(null)).isEqualTo(Gender.UNKNOWN);
    }

    @Test
    public void test_fromText_OnCharacter() throws Exception {
        assertThat(Gender.fromText("M")).isEqualTo(Gender.MALE);
        assertThat(Gender.fromText("m")).isEqualTo(Gender.MALE);

        assertThat(Gender.fromText("F")).isEqualTo(Gender.FEMALE);
        assertThat(Gender.fromText("f")).isEqualTo(Gender.FEMALE);

        assertThat(Gender.fromText("-")).isEqualTo(Gender.UNKNOWN);
    }
}
