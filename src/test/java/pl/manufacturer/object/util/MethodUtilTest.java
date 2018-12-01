package pl.manufacturer.object.util;

import org.junit.Test;
import pl.manufacturer.object.example.simple.SimpleStringObject;
import pl.manufacturer.object.exception.WrongMethodInvocationException;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class MethodUtilTest {

    @Test
    public void shouldInvokeSetterMethodOfTheObject() {
        // given
        SimpleStringObject singleSetterObject = new SimpleStringObject();

        List<Method> setterMethods = Arrays.stream(SimpleStringObject.class.getMethods())
                .filter(method -> method.getName().contains("set"))
                .collect(Collectors.toList());

        assertThat(setterMethods).hasSize(1);

        // when
        MethodUtil.invokeSetterMethod(singleSetterObject, setterMethods.get(0), "stringValue");

        // then
        assertThat(singleSetterObject.getStringObject()).isEqualTo("stringValue");
    }

    @Test(expected = WrongMethodInvocationException.class)
    public void shouldThrowWrongMethodInvocationException() {
        // given
        SimpleStringObject singleSetterObject = new SimpleStringObject();

        List<Method> setterMethods = Arrays.stream(SimpleStringObject.class.getMethods())
                .filter(method -> method.getName().contains("getString"))
                .collect(Collectors.toList());

        assertThat(setterMethods).hasSize(1);

        // when
        MethodUtil.invokeSetterMethod(singleSetterObject, setterMethods.get(0), "stringValue");
    }
}