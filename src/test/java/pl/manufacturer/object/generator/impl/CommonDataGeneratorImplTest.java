package pl.manufacturer.object.generator.impl;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import pl.manufacturer.object.example.simple.SimpleByteObject;
import pl.manufacturer.object.exception.NotABaseClassException;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Parameterized.class)
public class CommonDataGeneratorImplTest {

    private CommonDataGeneratorImpl dataGenerator = new CommonDataGeneratorImpl();

    private Class clazz;
    private Class expectedClazz;
    public Class<? extends Exception> expectedException;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    public CommonDataGeneratorImplTest(Class clazz, Class expectedClazz, Class expectedException) {
        this.clazz = clazz;
        this.expectedClazz = expectedClazz;
        this.expectedException = expectedException;
    }

    @Parameterized.Parameters
    public static Collection parameters() {
        return Arrays.asList(new Object[][]{
                {Long.class, Long.class, null},
                {Long.TYPE, Long.class, null},
                {Boolean.class, Boolean.class, null},
                {Boolean.TYPE, Boolean.class, null},
                {Integer.class, Integer.class, null},
                {Integer.TYPE, Integer.class, null},
                {Double.class, Double.class, null},
                {Double.TYPE, Double.class, null},
                {Float.class, Float.class, null},
                {Float.TYPE, Float.class, null},
                {Character.class, Character.class, null},
                {Character.TYPE, Character.class, null},
                {Byte.class, Byte.class, null},
                {Byte.TYPE, Byte.class, null},
                {Short.class, Short.class, null},
                {Short.TYPE, Short.class, null},
                {String.class, String.class, null},
                {List.class, null, NotABaseClassException.class},
                {SimpleByteObject.class, null, NotABaseClassException.class}
        });
    }
    
    @Test
    public void generateBaseTypeValue() throws NotABaseClassException {
        // given
        if(expectedException != null) {
            thrown.expect(expectedException);
        }

        // when
        Object result = dataGenerator.generateBaseTypeValue(clazz);

        // then
        assertThat(result).isNotNull().isInstanceOf(expectedClazz);
    }
}