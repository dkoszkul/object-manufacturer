package pl.manufacturer.object.util;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ArgumentTypeUtil {

    private ArgumentTypeUtil() {
    }

    public static boolean isBaseType(Type type) {
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

    public static Class getCollectionArgumentTypeFromSetterMethod(Method method) {
        Type[] types = method.getGenericParameterTypes();
        ParameterizedType pType = (ParameterizedType) types[0];
        return (Class<?>) pType.getActualTypeArguments()[0];
    }
}
