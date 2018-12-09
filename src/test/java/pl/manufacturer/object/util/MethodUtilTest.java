package pl.manufacturer.object.util;

import org.junit.Test;
import pl.manufacturer.object.example.pojo.simple.SimpleStringObject;
import pl.manufacturer.object.example.pojo.test.ObjectWithTwoTheSameMethodNames;
import pl.manufacturer.object.exception.MethodDoesNotExistException;
import pl.manufacturer.object.exception.MoreThanOneAvailabeMethodException;
import pl.manufacturer.object.exception.WrongMethodInvocationException;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
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
        MethodUtil.invokeMethod(singleSetterObject, setterMethods.get(0), "stringValue");

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
        MethodUtil.invokeMethod(singleSetterObject, setterMethods.get(0), "stringValue");
    }

    @Test
    public void shouldReturnMethodByItsName() {
        // given
        String methodName = "put";

        // when
        Method result = MethodUtil.getMethod(Map.class, methodName);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo(methodName);
    }

    @Test(expected = MethodDoesNotExistException.class)
    public void shouldThrowExceptionWhenMethodDoesNotExist() {
        // given
        String methodName = "nonExistringMethodName";

        // when
        MethodUtil.getMethod(Map.class, methodName);
    }

    @Test(expected = MoreThanOneAvailabeMethodException.class)
    public void shouldThrowExceptionWhenMoreThanOneMethodFound() {
        // given
        String methodName = "sum";

        // when
        MethodUtil.getMethod(ObjectWithTwoTheSameMethodNames.class, methodName);
    }

}