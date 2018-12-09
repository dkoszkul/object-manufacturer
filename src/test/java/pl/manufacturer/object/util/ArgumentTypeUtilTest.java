package pl.manufacturer.object.util;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import pl.manufacturer.object.example.pojo.extended.ExtendedListOfStringsObject;
import pl.manufacturer.object.example.pojo.simple.SimpleByteObject;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Enclosed.class)
public class ArgumentTypeUtilTest {

    @RunWith(Parameterized.class)
    public static class TheParameterizedPart {
        private Class input;
        private boolean expectedResult;

        public TheParameterizedPart(Class input, boolean expectedResult) {
            this.input = input;
            this.expectedResult = expectedResult;
        }

        @Parameterized.Parameters
        public static Collection parameters() {
            return Arrays.asList(new Object[][]{
                    {Long.class, true},
                    {Long.TYPE, true},
                    {Boolean.class, true},
                    {Boolean.TYPE, true},
                    {Integer.class, true},
                    {Integer.TYPE, true},
                    {Double.class, true},
                    {Double.TYPE, true},
                    {Float.class, true},
                    {Float.TYPE, true},
                    {Character.class, true},
                    {Character.TYPE, true},
                    {Byte.class, true},
                    {Byte.TYPE, true},
                    {Short.class, true},
                    {Short.TYPE, true},
                    {String.class, true},
                    {List.class, false},
                    {SimpleByteObject.class, false}
            });
        }

        @Test
        public void shouldCheckIfClassIsABaseType() {
            // when
            boolean result = ArgumentTypeUtil.isBaseType(input);

            // then
            assertThat(result).isEqualTo(expectedResult);
        }
    }

    public static class NotParameterizedPart {

        @Test
        public void shouldRetrieveCollectionArgumentType() {
            // given
            List<Method> setterMethods = Arrays.stream(ExtendedListOfStringsObject.class.getMethods())
                    .filter(method -> method.getName().contains("set"))
                    .collect(Collectors.toList());

            // when
            Class result = ArgumentTypeUtil.getCollectionArgumentTypeFromSetterMethod(setterMethods.get(0));

            // then
            assertThat(result).isEqualTo(String.class);
        }
    }
}