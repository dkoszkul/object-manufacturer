package pl.manufacturer.object.generator;

import java.lang.reflect.Type;

public interface DataGenerator {
    <T> T generateObject(Class<T> clazz, Type... classArgsTypes);
}
