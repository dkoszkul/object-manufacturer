package pl.manufacturer.object.generator.impl;

import pl.manufacturer.object.generator.CommonDataGenerator;
import pl.manufacturer.object.generator.DataGenerator;

import java.lang.reflect.Type;

public class EntityDataGenerator implements DataGenerator {

    private final CommonDataGenerator commonDataGenerator;

    public EntityDataGenerator(CommonDataGenerator commonDataGenerator) {
        this.commonDataGenerator = commonDataGenerator;
    }

    @Override
    public <T> T generateObject(Class<T> clazz, Type... classArgsTypes) {
        return null;
    }
}
