package pl.manufacturer.object.util;

import pl.manufacturer.object.exception.MethodDoesNotExistException;
import pl.manufacturer.object.exception.MoreThanOneAvailabeMethodException;
import pl.manufacturer.object.exception.WrongMethodInvocationException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MethodUtil {

    private MethodUtil() {
    }

    public static <T> void invokeMethod(T object, Method method, Object... value) {
        try {
            method.invoke(object, value);
        } catch (IllegalAccessException | InvocationTargetException | IllegalArgumentException e) {
            throw new WrongMethodInvocationException("This is probably not a setter method", e);
        }
    }

    public static Method getMethod(Class clazz, String methodName) {
        List<Method> methodsList = Stream.of(clazz.getMethods())
                .filter(method -> method.getName().equals(methodName))
                .collect(Collectors.toList());
        if (methodsList.isEmpty()) {
            throw new MethodDoesNotExistException("There is no method " + methodName + " in " + clazz);
        } else if (methodsList.size() > 1) {
            throw new MoreThanOneAvailabeMethodException("There is more than one method " + methodName + " in " + clazz);
        }

        return methodsList.get(0);
    }
}
