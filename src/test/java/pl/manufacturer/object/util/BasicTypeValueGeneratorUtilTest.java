package pl.manufacturer.object.util;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BasicTypeValueGeneratorUtilTest {

    @Test
    public void shouldGenerateString() {
        // when
        String result = BasicTypeValueGeneratorUtil.generateString();

        // then
        assertThat(result).isNotNull();
    }

    @Test
    public void shouldGenerateBoolean() {
        // when
        Boolean result = BasicTypeValueGeneratorUtil.generateBoolean();

        // then
        assertThat(result).isNotNull();
    }

    @Test
    public void shouldGenerateInteger() {
        // when
        Integer result = BasicTypeValueGeneratorUtil.generateInteger();

        // then
        assertThat(result).isNotNull();
    }

    @Test
    public void shouldGenerateLong() {
        // when
        Long result = BasicTypeValueGeneratorUtil.generateLong();

        // then
        assertThat(result).isNotNull();
    }

    @Test
    public void shouldGenerateDouble() {
        // when
        Double result = BasicTypeValueGeneratorUtil.generateDouble();

        // then
        assertThat(result).isNotNull();
    }

    @Test
    public void shouldGenerateFloat() {
        // when
        Float result = BasicTypeValueGeneratorUtil.generateFloat();

        // then
        assertThat(result).isNotNull();
    }

    @Test
    public void shouldGenerateCharacter() {
        // when
        Character result = BasicTypeValueGeneratorUtil.generateCharacter();

        // then
        assertThat(result).isNotNull();
    }

    @Test
    public void shouldGenerateByte() {
        // when
        Byte result = BasicTypeValueGeneratorUtil.generateByte();

        // then
        assertThat(result).isNotNull();
    }

    @Test
    public void shouldGenerateShort() {
        // when
        Short result = BasicTypeValueGeneratorUtil.generateShort();

        // then
        assertThat(result).isNotNull();
    }
}