package com.tchepannou.pdr.domain;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GenderTest {

    @Test
    public void testFromCode() throws Exception {
        assertThat(Gender.fromCode("M")).isEqualTo(Gender.MALE);
        assertThat(Gender.fromCode("m")).isEqualTo(Gender.MALE);

        assertThat(Gender.fromCode("F")).isEqualTo(Gender.FEMALE);
        assertThat(Gender.fromCode("f")).isEqualTo(Gender.FEMALE);

        assertThat(Gender.fromCode("unknown")).isEqualTo(Gender.UNKNOWN);
    }

    @Test
    public void testFromCode_null() throws Exception {
        assertThat(Gender.fromCode(null)).isEqualTo(Gender.UNKNOWN);
    }
}
