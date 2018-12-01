package pl.manufacturer.object.generator.impl;

import pl.manufacturer.object.generator.DataGenerator;
import pl.manufacturer.object.util.ArgumentTypeUtil;
import pl.manufacturer.object.util.BasicTypeValueGeneratorUtil;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static pl.manufacturer.object.util.MethodUtil.invokeSetterMethod;

public class DataGeneratorImpl implements DataGenerator {

    private static final int BASE_ARRAY_SIZE = 2;

    @Override
    public <T> void generateDataForBaseType(T object, Method setterMethod, Class setterArgumentType) {
        invokeSetterMethod(object, setterMethod, generateBaseTypeValue(setterArgumentType));
    }

    @Override
    public <T> void generateDataForArray(T object, Method setterMethod, Class setterArgumentType) {
        System.out.println("Array");
    }

    @Override
    public <T> void generateDataForCollection(T object, Method setterMethod) {
        Class iterableType = ArgumentTypeUtil.getCollectionArgumentTypeFromSetterMethod(setterMethod);
        Stream valuesStream = IntStream.range(0, BASE_ARRAY_SIZE)
                .mapToObj(i -> generateBaseTypeValue(iterableType));
        Collection collection = null;
        if(List.class.equals(setterMethod.getParameterTypes()[0])){
            collection = (Collection) valuesStream.collect(Collectors.toList());
        } else if(Set.class.equals(setterMethod.getParameterTypes()[0])){
            collection = (Collection) valuesStream.collect(Collectors.toSet());
        }
        invokeSetterMethod(object, setterMethod, collection);
    }


    private Object generateBaseTypeValue(Class setterArgumentType) {
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
