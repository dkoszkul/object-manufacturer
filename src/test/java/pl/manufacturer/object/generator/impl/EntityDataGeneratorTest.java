package pl.manufacturer.object.generator.impl;

import org.junit.Test;
import pl.manufacturer.object.example.entity.PostOneToOneEntity;
import pl.manufacturer.object.example.entity.SimpleEntity;
import pl.manufacturer.object.example.pojo.simple.SimpleBooleanObject;
import pl.manufacturer.object.exception.ObjectIsNotAnEntityException;
import pl.manufacturer.object.generator.DataGenerator;

import static org.assertj.core.api.Assertions.assertThat;

public class EntityDataGeneratorTest {

    private DataGenerator dataGenerator = new EntityDataGenerator(new PojoDataGenerator());

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
        assertThat(result.getId()).isNotNull();
        assertThat(result.getFirstName()).isNotEmpty().hasSize(100);
        assertThat(result.getLastName()).isNotEmpty().hasSize(50);
    }

    @Test
    public void shouldCreateEntityWithOneToOneBidarectionalAnnotation() {
        // when
        PostOneToOneEntity result = dataGenerator.generateObject(PostOneToOneEntity.class);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isNotNull();
        assertThat(result.getTitle()).isNotNull().hasSize(255);
        assertThat(result.getDetails().getCreatedOn()).isNotNull();
        assertThat(result.getDetails().getCreatedBy()).isNotNull().hasSize(255);
        assertThat(result.getDetails().getPost()).isEqualTo(result);
    }
}