package pl.manufacturer.object.util;

import pl.manufacturer.object.exception.WrongMethodInvocationException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MethodUtil {

    private MethodUtil() {
    }

    public static <T> void invokeSetterMethod(T object, Method setterMethod, Object value) {
        try {
            setterMethod.invoke(object, value);
        } catch (IllegalAccessException | InvocationTargetException | IllegalArgumentException e) {
            throw new WrongMethodInvocationException("This is probably not a setter method", e);
        }
    }
}
