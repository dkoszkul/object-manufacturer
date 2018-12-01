package pl.manufacturer.object.generator;

import java.lang.reflect.Method;

public interface DataGenerator {

    <T> void generateDataForBaseType(T object, Method setterMethod, Class setterArgumentType);

    <T> void generateDataForArray(T object, Method setterMethod, Class setterArgumentType);

    <T> void generateDataForCollection(T object, Method setterMethod);
}
