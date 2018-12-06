package pl.manufacturer.object.api;

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

public class ManufacturerFactory {

    private static final int BASE_ARRAY_SIZE = 2;

    private final DataGenerator dataGenerator;

    public ManufacturerFactory(DataGenerator dataGenerator) {
        this.dataGenerator = dataGenerator;
    }

    public <T> T generateObject(Class<T> clazz, Type... classArgsTypes) {
        if (ArgumentTypeUtil.isBaseType(clazz)) {
            return (T) dataGenerator.generateBaseTypeValue(clazz);
        } else if(clazz.isArray()) {
            return (T) generateArray(clazz.getComponentType());
        } else if(Collection.class.isAssignableFrom(clazz)) {
            return (T) generateCollection(clazz, (Class) classArgsTypes[0]);
        } else if (Map.class.isAssignableFrom(clazz)) {
            return (T) generateMap((Class) classArgsTypes[0], (Class) classArgsTypes[1]);
        }

        T object = instantiateClass(clazz);

        List<Method> setterMethods = Arrays.stream(clazz.getMethods())
                .filter(method -> method.getName().contains("set"))
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
                /////////////////////////////////////////////////////////////////////////
                Class keyClass;
                if(!isInstanceOfClass(keyType)) {
                    System.out.println("   key class is NOT an instance of Class");

                    keyClass = (Class) ((ParameterizedType)genericParameterType.getActualTypeArguments()[0]).getRawType();
                } else {
                    keyClass = (Class) keyType;
                }

                Class valueClass;
                if(!isInstanceOfClass(valueType)) {
                    valueClass = (Class) ((ParameterizedType)genericParameterType.getActualTypeArguments()[1]).getRawType();
                }else {
                    valueClass = (Class) valueType;
                }

                System.out.println(">>>>Key class " + keyType + " is base: " + ArgumentTypeUtil.isBaseType(keyType) + " | " + isInstanceOfClass(keyType));
                System.out.println("----Value class " + valueType + " is base: " + ArgumentTypeUtil.isBaseType(valueType) + " | " + isInstanceOfClass(valueType));

                /////////////////////////////////////////////////////////////////////////
                Map map = generateMap(keyClass, valueClass);
                invokeMethod(object, setterMethod, map);
            } else {
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

    private Map generateMap(Class keyClass, Class valueClass) {
        Map map = new HashMap();
        Method putMethod = MethodUtil.getMethod(map.getClass(), "put");

        for (int i = 0; i < BASE_ARRAY_SIZE; i++) {
            Object key = generateObject(keyClass);
            Object value = generateObject(valueClass);
            MethodUtil.invokeMethod(map, putMethod, key, value);
        }

        return map;
    }

    private boolean isInstanceOfClass(Type type) {
        return type instanceof Class;
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
