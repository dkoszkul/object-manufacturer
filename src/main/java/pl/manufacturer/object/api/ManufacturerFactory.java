package pl.manufacturer.object.api;

import pl.manufacturer.object.util.BasicTypeValueGeneratorUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ManufacturerFactory {

    private static final int BASE_ARRAY_SIZE = 2;

    public <T> T generatePojo(Class<T> clazz) {
        T object = instantiateClass(clazz);

        List<Method> setterMethods = Arrays.stream(clazz.getMethods())
                .filter(method -> method.getName().contains("set"))
                .collect(Collectors.toList());

        setterMethods.forEach(setterMethod -> {
            Class setterArgumentType = setterMethod.getParameterTypes()[0];
            if (isBaseType(setterArgumentType)) {
                invokeSetterMethod(object, setterMethod, generateValue(setterArgumentType));
            } else if (setterArgumentType.isArray()) {
                System.out.println("Array");
            } else if (Collection.class.isAssignableFrom(setterArgumentType)) {
                Class iterableType = getIterableType(setterMethod);
                Collection collection = IntStream.range(0, BASE_ARRAY_SIZE)
                        .mapToObj(i -> generateValue(iterableType))
                        .collect(Collectors.toList());
                invokeSetterMethod(object, setterMethod, collection);
            } else {
                invokeSetterMethod(object, setterMethod, generatePojo(setterArgumentType));
            }
        });

        return object;
    }

    private Class getIterableType(Method method) {
        Type[] types = method.getGenericParameterTypes();
        ParameterizedType pType = (ParameterizedType) types[0];
        return (Class<?>) pType.getActualTypeArguments()[0];
    }

    private <T> T instantiateClass(Class<T> clazz) {
        T object;
        try {
            object = clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("Cannot instantiate class " + clazz);
        }
        return object;
    }

    private boolean isBaseType(Class<?> type) {
        return type.equals(String.class) ||

                type.equals(Boolean.class) ||
                type.equals(Boolean.TYPE) ||

                type.equals(Integer.class) ||
                type.equals(Integer.TYPE) ||

                type.equals(Long.class) ||
                type.equals(Long.TYPE) ||

                type.equals(Double.class) ||
                type.equals(Double.TYPE) ||

                type.equals(Float.class) ||
                type.equals(Float.TYPE) ||

                type.equals(Character.class) ||
                type.equals(Character.TYPE) ||

                type.equals(Byte.class) ||
                type.equals(Byte.TYPE) ||

                type.equals(Short.class) ||
                type.equals(Short.TYPE);
    }

    private <T> void invokeSetterMethod(T object, Method setterMethod, Object value) {
        try {
            setterMethod.invoke(object, value);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private Object generateValue(Class setterArgumentType) {
        if (setterArgumentType.equals(String.class)) {
            return BasicTypeValueGeneratorUtil.generateString();
        } else if (setterArgumentType.equals(Boolean.class) || setterArgumentType.equals(Boolean.TYPE)) {
            return BasicTypeValueGeneratorUtil.generateBoolean();
        } else if (setterArgumentType.equals(Integer.class) || setterArgumentType.equals(Integer.TYPE)) {
            return BasicTypeValueGeneratorUtil.generateInteger();
        } else if (setterArgumentType.equals(Long.class) || setterArgumentType.equals(Long.TYPE)) {
            return BasicTypeValueGeneratorUtil.generateLong();
        } else if (setterArgumentType.equals(Double.class) || setterArgumentType.equals(Double.TYPE)) {
            return BasicTypeValueGeneratorUtil.generateDouble();
        } else if (setterArgumentType.equals(Float.class) || setterArgumentType.equals(Float.TYPE)) {
            return BasicTypeValueGeneratorUtil.generateFloat();
        } else if (setterArgumentType.equals(Character.class) || setterArgumentType.equals(Character.TYPE)) {
            return BasicTypeValueGeneratorUtil.generateCharacter();
        } else if (setterArgumentType.equals(Byte.class) || setterArgumentType.equals(Byte.TYPE)) {
            return BasicTypeValueGeneratorUtil.generateByte();
        } else if (setterArgumentType.equals(Short.class) || setterArgumentType.equals(Short.TYPE)) {
            return BasicTypeValueGeneratorUtil.generateShort();
        }
        return null;
    }
}
