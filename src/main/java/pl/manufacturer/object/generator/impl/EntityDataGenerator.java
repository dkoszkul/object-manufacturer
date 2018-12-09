package pl.manufacturer.object.generator.impl;

import pl.manufacturer.object.exception.ObjectIsNotAnEntityException;
import pl.manufacturer.object.generator.CommonDataGenerator;
import pl.manufacturer.object.generator.DataGenerator;

import javax.persistence.Entity;
import java.lang.reflect.Type;
import java.util.Arrays;

public class EntityDataGenerator implements DataGenerator {

    private final CommonDataGenerator commonDataGenerator;

    public EntityDataGenerator(CommonDataGenerator commonDataGenerator) {
        this.commonDataGenerator = commonDataGenerator;
    }

    @Override
    public <T> T generateObject(Class<T> clazz, Type... classArgsTypes) {
        if(!isEntity(clazz)) {
            throw new ObjectIsNotAnEntityException("Class " + clazz + " is not an entity. Use GenerationMode.POJO instead.");
        }

        return null;
    }

    private <T> boolean isEntity(Class<T> clazz) {
        return Arrays.stream(clazz.getAnnotations())
                .filter(annotation -> annotation.annotationType().equals(Entity.class))
                .count() == 1;
    }
}
