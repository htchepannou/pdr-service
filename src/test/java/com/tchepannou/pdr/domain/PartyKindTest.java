package com.tchepannou.pdr.domain;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PartyKindTest {

    @Test
    public void testFromCode() throws Exception {
        assertThat(PartyKind.fromCode("P")).isEqualTo(PartyKind.PERSON);
        assertThat(PartyKind.fromCode("p")).isEqualTo(PartyKind.PERSON);

        assertThat(PartyKind.fromCode("O")).isEqualTo(PartyKind.ORGANIZATION);
        assertThat(PartyKind.fromCode("o")).isEqualTo(PartyKind.ORGANIZATION);

        assertThat(PartyKind.fromCode("unknown")).isEqualTo(PartyKind.UNKNOWN);
    }

    @Test
    public void testFromCode_null() throws Exception {
        assertThat(PartyKind.fromCode(null)).isEqualTo(PartyKind.UNKNOWN);
    }
}
