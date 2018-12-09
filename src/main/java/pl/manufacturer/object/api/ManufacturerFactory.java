package pl.manufacturer.object.api;

import pl.manufacturer.object.generator.DataGenerator;
import pl.manufacturer.object.generator.impl.CommonDataGeneratorImpl;
import pl.manufacturer.object.generator.impl.EntityDataGenerator;
import pl.manufacturer.object.generator.impl.PojoDataGenerator;

import java.util.HashMap;
import java.util.Map;

public final class ManufacturerFactory {

    private static Map<GenerationMode, DataGenerator> generators;

    static {
        generators = new HashMap<>();
        generators.put(GenerationMode.POJO, new PojoDataGenerator(new CommonDataGeneratorImpl()));
        generators.put(GenerationMode.ENTITY, new EntityDataGenerator(new CommonDataGeneratorImpl()));
    }

    private ManufacturerFactory() {
        // private constructor to hide the implicit one
    }

    public static DataGenerator getGeneratorInstance(GenerationMode generationMode) {
        return generators.get(generationMode);
    }
}
