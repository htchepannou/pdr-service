package com.tchepannou.pds.enums;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PrivacyTest {
    @Test
    public void test_fromCode() throws Exception {
        assertThat(Privacy.fromCode('P')).isEqualTo(Privacy.PUBLIC);
        assertThat(Privacy.fromCode('p')).isEqualTo(Privacy.PUBLIC);

        assertThat(Privacy.fromCode('H')).isEqualTo(Privacy.HIDDEN);
        assertThat(Privacy.fromCode('h')).isEqualTo(Privacy.HIDDEN);
    }

    @Test
    public void test_fromText() throws Exception {
        assertThat(Privacy.fromText("PUBLIC")).isEqualTo(Privacy.PUBLIC);
        assertThat(Privacy.fromText("public")).isEqualTo(Privacy.PUBLIC);

        assertThat(Privacy.fromText("HIDDEN")).isEqualTo(Privacy.HIDDEN);
        assertThat(Privacy.fromText("Hidden")).isEqualTo(Privacy.HIDDEN);

        assertThat(Privacy.fromText("?unknown???")).isNull();
    }


    @Test
    public void test_fromText_OneChar() throws Exception {
        assertThat(Privacy.fromText("P")).isEqualTo(Privacy.PUBLIC);
        assertThat(Privacy.fromText("p")).isEqualTo(Privacy.PUBLIC);

        assertThat(Privacy.fromText("H")).isEqualTo(Privacy.HIDDEN);
        assertThat(Privacy.fromText("h")).isEqualTo(Privacy.HIDDEN);

        assertThat(Privacy.fromText("?")).isNull();
    }

    @Test
    public void test_fromText_null() throws Exception {
        assertThat(Privacy.fromText(null)).isNull();
    }
}
