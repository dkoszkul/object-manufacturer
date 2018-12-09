package pl.manufacturer.object.generator.impl;

import pl.manufacturer.object.exception.NotABaseClassException;
import pl.manufacturer.object.generator.CommonDataGenerator;
import pl.manufacturer.object.util.BasicTypeValueGeneratorUtil;

public class CommonDataGeneratorImpl implements CommonDataGenerator {

    @Override
    public Object generateBaseTypeValue(Class setterArgumentType) {
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
        throw new NotABaseClassException("Class " + setterArgumentType + " is not a base class.");
    }
}
