package pl.manufacturer.object.api;

import org.junit.Test;
import pl.manufacturer.object.generator.DataGenerator;
import pl.manufacturer.object.generator.impl.EntityDataGenerator;
import pl.manufacturer.object.generator.impl.PojoDataGenerator;

import static org.assertj.core.api.Assertions.assertThat;

public class ManufacturerFactoryTest {

    @Test
    public void shouldReturnPojoDataGeneratorInstance() {
        // when
        DataGenerator result = ManufacturerFactory.getGeneratorInstance(GenerationMode.POJO);

        // then
        assertThat(result).isInstanceOf(PojoDataGenerator.class);
    }

    @Test
    public void shouldReturnEntityDataGeneratorInstance() {
        // when
        DataGenerator result = ManufacturerFactory.getGeneratorInstance(GenerationMode.ENTITY);

        // then
        assertThat(result).isInstanceOf(EntityDataGenerator.class);
    }

}