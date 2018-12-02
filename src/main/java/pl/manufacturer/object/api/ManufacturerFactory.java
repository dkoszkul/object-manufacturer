package pl.manufacturer.object.api;

import pl.manufacturer.object.generator.DataGenerator;
import pl.manufacturer.object.generator.impl.DataGeneratorImpl;
import pl.manufacturer.object.util.ArgumentTypeUtil;
import pl.manufacturer.object.util.MethodUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ManufacturerFactory {

    private final DataGenerator dataGenerator = new DataGeneratorImpl();

    public <T> T generateObject(Class<T> clazz) {
        if (ArgumentTypeUtil.isBaseType(clazz)) {
            return (T) dataGenerator.generateBaseTypeValue(clazz);
        }

        T object = instantiateClass(clazz);


        List<Method> setterMethods = Arrays.stream(clazz.getMethods())
                .filter(method -> method.getName().contains("set"))
                .collect(Collectors.toList());

        setterMethods.forEach(setterMethod -> {
            Class setterArgumentType = setterMethod.getParameterTypes()[0];
            if (ArgumentTypeUtil.isBaseType(setterArgumentType)) {
                dataGenerator.generateDataForBaseType(object, setterMethod, setterArgumentType);
            } else if (setterArgumentType.isArray()) {
                dataGenerator.generateDataForArray(object, setterMethod, setterArgumentType);
            } else if (Collection.class.isAssignableFrom(setterArgumentType)) {
                dataGenerator.generateDataForCollection(object, setterMethod);
            } else if (Map.class.isAssignableFrom(setterArgumentType)) {

                ParameterizedType genericParameterType = (ParameterizedType) setterMethod.getGenericParameterTypes()[0];
                Class keyClass = (Class) genericParameterType.getActualTypeArguments()[0];
                Class valueClass = (Class) genericParameterType.getActualTypeArguments()[1];

                Map map = new HashMap();
                Method putMethod = Stream.of(map.getClass().getMethods()).filter(method -> method.getName().equals("put")).collect(Collectors.toList()).get(0);

                for (int i = 0; i < 2; i++) {
                    Object key = generateObject(keyClass);
                    Object value = generateObject(valueClass);
                    try {
                        putMethod.invoke(map, key, value);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }

                }


                MethodUtil.invokeSetterMethod(object, setterMethod, map);
            } else {
                MethodUtil.invokeSetterMethod(object, setterMethod, generateObject(setterArgumentType));
            }
        });

        return object;
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
}
