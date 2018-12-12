package pl.manufacturer.object.generator.impl;

import org.junit.Test;
import pl.manufacturer.object.example.entity.SimpleEntity;
import pl.manufacturer.object.example.pojo.simple.SimpleBooleanObject;
import pl.manufacturer.object.exception.ObjectIsNotAnEntityException;
import pl.manufacturer.object.generator.DataGenerator;

import static org.assertj.core.api.Assertions.assertThat;

public class EntityDataGeneratorTest {

    private DataGenerator dataGenerator = new EntityDataGenerator();

    @Test
    public void shouldNotThrowExceptionIfObjectIsAnEntity() {
        // when
        dataGenerator.generateObject(SimpleEntity.class);
    }

    @Test(expected = ObjectIsNotAnEntityException.class)
    public void shouldThrowObjectIsNotAnEntityException() {
        // when
        dataGenerator.generateObject(SimpleBooleanObject.class);
    }

    @Test
    public void shouldCreateSimpleEntity() {
        // when
        SimpleEntity result = dataGenerator.generateObject(SimpleEntity.class);
        // then
        assertThat(result).isNotNull();
    }

}