package pl.manufacturer.object.generator.impl;

import pl.manufacturer.object.generator.DataGenerator;
import pl.manufacturer.object.util.ArgumentTypeUtil;
import pl.manufacturer.object.util.MethodUtil;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static pl.manufacturer.object.util.MethodUtil.invokeMethod;

public class PojoDataGenerator extends CommonDataGenerator implements DataGenerator {

    private static final int BASE_ARRAY_SIZE = 2;

    @Override
    public <T> T generateObject(Class<T> clazz, Type... classArgsTypes) {
        if (ArgumentTypeUtil.isBaseType(clazz)) {
            return (T) generateBaseTypeValue(clazz);
        } else if (clazz.isArray()) {
            return (T) generateArray(clazz.getComponentType());
        } else if (Collection.class.isAssignableFrom(clazz)) {
            return (T) generateCollection(clazz, (Class) classArgsTypes[0]);
        } else if (Map.class.isAssignableFrom(clazz)) {
            return (T) generateMap(classArgsTypes[0], classArgsTypes[1]);
        }

        T object;
        try {
            object = instantiateClass(clazz);
        } catch (InstantiationException e) {
            return handleInstantiationException(clazz);
        }

        List<Method> setterMethods = Arrays.stream(clazz.getMethods())
                .filter(method -> method.getName().startsWith("set"))
                .collect(Collectors.toList());

        setterMethods.forEach(setterMethod -> {
            Class setterArgumentClass = setterMethod.getParameterTypes()[0];

            if (setterArgumentClass.isArray()) {
                Object array = generateArray(setterArgumentClass.getComponentType());
                invokeMethod(object, setterMethod, array);
            } else if (Collection.class.isAssignableFrom(setterArgumentClass)) {
                Class collectionArgumentType = ArgumentTypeUtil.getCollectionArgumentTypeFromSetterMethod(setterMethod);
                Collection collection = generateCollection(setterArgumentClass, collectionArgumentType);
                invokeMethod(object, setterMethod, collection);
            } else if (Map.class.isAssignableFrom(setterArgumentClass)) {
                ParameterizedType genericParameterType = (ParameterizedType) setterMethod.getGenericParameterTypes()[0];
                Type keyType = genericParameterType.getActualTypeArguments()[0];
                Type valueType = genericParameterType.getActualTypeArguments()[1];

                Map map = generateMap(keyType, valueType);
                invokeMethod(object, setterMethod, map);
            } else if (Date.class.isAssignableFrom(setterArgumentClass)) {
                invokeMethod(object, setterMethod, new Date());
            }  else {
                invokeMethod(object, setterMethod, generateObject(setterArgumentClass));
            }
        });

        return object;
    }

    private Object generateArray(Class arrayType) {
        Object array = Array.newInstance(arrayType, BASE_ARRAY_SIZE);
        IntStream.range(0, BASE_ARRAY_SIZE).forEach(i -> Array.set(array, i, generateObject(arrayType)));
        return array;
    }

    private Collection generateCollection(Class collectionType, Class collectionArgumentType) {
        Stream valuesStream = IntStream.range(0, BASE_ARRAY_SIZE)
                .mapToObj(i -> generateObject(collectionArgumentType));
        Collection collection = null;
        if (List.class.equals(collectionType)) {
            collection = (Collection) valuesStream.collect(Collectors.toList());
        } else if (Set.class.equals(collectionType)) {
            collection = (Collection) valuesStream.collect(Collectors.toSet());
        }
        return collection;

    }

    private Map generateMap(Object keyObject, Object valueObject) {
        Map map = new HashMap();
        Method putMethod = MethodUtil.getMethod(map.getClass(), "put");

        Class keyClass = isInstanceOfClass(keyObject) ? (Class) keyObject : null;
        Class valueClass = isInstanceOfClass(valueObject) ? (Class) valueObject : null;

        ParameterizedType keyParametrizedType = keyObject instanceof ParameterizedType ? (ParameterizedType) keyObject : null;
        ParameterizedType valueParametrizedType = valueObject instanceof ParameterizedType ? (ParameterizedType) valueObject : null;

        IntStream.range(0, BASE_ARRAY_SIZE)
                .mapToObj(i -> generateMapElement(keyClass, keyParametrizedType, keyParametrizedType))
                .forEach(key -> {
                    Object value = generateMapElement(valueClass, keyParametrizedType, valueParametrizedType);
                    invokeMethod(map, putMethod, key, value);
                });

        return map;
    }

    private Object generateMapElement(Class valueClass, ParameterizedType keyParametrizedType, ParameterizedType valueParametrizedType) {
        Object value = null;
        if (valueClass != null) {
            value = generateObject(valueClass);
        } else if (keyParametrizedType != null) {
            Type rawType = valueParametrizedType.getRawType();
            if (List.class.equals(rawType)) {
                Type argument0 = valueParametrizedType.getActualTypeArguments()[0];
                value = generateObject((Class) rawType, argument0);
            } else if (Map.class.equals(rawType)) {
                Type argument0 = valueParametrizedType.getActualTypeArguments()[0];
                Type argument1 = valueParametrizedType.getActualTypeArguments()[1];
                value = generateObject((Class) rawType, argument0, argument1);
            }
        }
        return value;
    }

    private boolean isInstanceOfClass(Object type) {
        return type instanceof Class;
    }
}
