package pl.manufacturer.object.api;

import pl.manufacturer.object.generator.DataGenerator;
import pl.manufacturer.object.generator.impl.DataGeneratorImpl;
import pl.manufacturer.object.util.ArgumentTypeUtil;
import pl.manufacturer.object.util.MethodUtil;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static pl.manufacturer.object.util.MethodUtil.invokeMethod;

public class ManufacturerFactory {

    private static final int BASE_ARRAY_SIZE = 2;

    private final DataGenerator dataGenerator;

    public ManufacturerFactory(DataGenerator dataGenerator) {
        this.dataGenerator = dataGenerator;
    }

    public <T> T generateObject(Class<T> clazz) {
        if (ArgumentTypeUtil.isBaseType(clazz)) {
            return (T) dataGenerator.generateBaseTypeValue(clazz);
        }

        T object = instantiateClass(clazz);

        List<Method> setterMethods = Arrays.stream(clazz.getMethods())
                .filter(method -> method.getName().contains("set"))
                .collect(Collectors.toList());

        setterMethods.forEach(setterMethod -> {
            Class setterArgumentClass = setterMethod.getParameterTypes()[0];

            if (setterArgumentClass.isArray()) {
                generateArray(object, setterMethod);
            } else if (Collection.class.isAssignableFrom(setterArgumentClass)) {
                generateCollection(object, setterMethod);
            } else if (Map.class.isAssignableFrom(setterArgumentClass)) {
                generateMap(object, setterMethod);
            } else {
                invokeMethod(object, setterMethod, generateObject(setterArgumentClass));
            }
        });

        return object;
    }

    private <T> void generateArray(T object, Method setterMethod) {
        Object array = Array.newInstance(setterMethod.getParameterTypes()[0].getComponentType(), BASE_ARRAY_SIZE);
        IntStream.range(0, BASE_ARRAY_SIZE)
                .forEach(i -> Array.set(array, i, generateObject(setterMethod.getParameterTypes()[0].getComponentType())));
        invokeMethod(object, setterMethod, array);
    }

    private <T> void generateCollection(T object, Method setterMethod) {
        Class iterableType = ArgumentTypeUtil.getCollectionArgumentTypeFromSetterMethod(setterMethod);
        Stream valuesStream = IntStream.range(0, BASE_ARRAY_SIZE)
                .mapToObj(i -> generateObject(iterableType));
        Collection collection = null;
        if (List.class.equals(setterMethod.getParameterTypes()[0])) {
            collection = (Collection) valuesStream.collect(Collectors.toList());
        } else if (Set.class.equals(setterMethod.getParameterTypes()[0])) {
            collection = (Collection) valuesStream.collect(Collectors.toSet());
        }
        invokeMethod(object, setterMethod, collection);
    }

    private <T> void generateMap(T object, Method setterMethod) {
        ParameterizedType genericParameterType = (ParameterizedType) setterMethod.getGenericParameterTypes()[0];
        Class keyClass = (Class) genericParameterType.getActualTypeArguments()[0];
        Class valueClass = (Class) genericParameterType.getActualTypeArguments()[1];

        Map map = new HashMap();
        Method putMethod = MethodUtil.getMethod(map.getClass(), "put");

        for (int i = 0; i < BASE_ARRAY_SIZE; i++) {
            Object key = generateObject(keyClass);
            Object value = generateObject(valueClass);
            MethodUtil.invokeMethod(map, putMethod, key, value);
        }
        MethodUtil.invokeMethod(object, setterMethod, map);
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
