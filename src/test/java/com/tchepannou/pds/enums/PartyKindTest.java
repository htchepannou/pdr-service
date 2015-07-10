package com.tchepannou.pds.enums;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PartyKindTest {
    @Test
    public void test_fromCode() throws Exception {
        assertThat(PartyKind.fromCode('P')).isEqualTo(PartyKind.PERSON);
        assertThat(PartyKind.fromCode('p')).isEqualTo(PartyKind.PERSON);

        assertThat(PartyKind.fromCode('O')).isEqualTo(PartyKind.ORGANIZATION);
        assertThat(PartyKind.fromCode('o')).isEqualTo(PartyKind.ORGANIZATION);

        assertThat(PartyKind.fromCode('X')).isNull();
    }

    @Test
    public void test_fromText() throws Exception {
        assertThat(PartyKind.fromText("PERSON")).isEqualTo(PartyKind.PERSON);
        assertThat(PartyKind.fromText("pErsON")).isEqualTo(PartyKind.PERSON);

        assertThat(PartyKind.fromText("ORGANIZATION")).isEqualTo(PartyKind.ORGANIZATION);
        assertThat(PartyKind.fromText("ORGANIZATion")).isEqualTo(PartyKind.ORGANIZATION);

        assertThat(PartyKind.fromText("?unknown???")).isNull();
    }


    @Test
    public void test_fromText_OneChar() throws Exception {
        assertThat(PartyKind.fromText("P")).isEqualTo(PartyKind.PERSON);
        assertThat(PartyKind.fromText("p")).isEqualTo(PartyKind.PERSON);

        assertThat(PartyKind.fromText("O")).isEqualTo(PartyKind.ORGANIZATION);
        assertThat(PartyKind.fromText("o")).isEqualTo(PartyKind.ORGANIZATION);

        assertThat(PartyKind.fromText("?")).isNull();
    }

    @Test
    public void test_fromText_null() throws Exception {
        assertThat(PartyKind.fromText(null)).isNull();
    }
}
