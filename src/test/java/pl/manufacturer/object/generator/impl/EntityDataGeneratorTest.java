package pl.manufacturer.object.generator.impl;

import org.junit.Ignore;
import org.junit.Test;
import pl.manufacturer.object.example.entity.manytooneandonetomany.Employee;
import pl.manufacturer.object.example.entity.manytooneandonetomany.Phone;
import pl.manufacturer.object.example.entity.onetoone.PostOneToOneEntity;
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

    @Test
    public void shouldCreateEntityWithOneToManyManyToOneAnnotation() {
        // when
        Employee result = dataGenerator.generateObject(Employee.class);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isNotNull();
        assertThat(result.getPostCode()).isNotNull().hasSize(255);
        assertThat(result.getPhones()).isNotNull().hasSize(5);
        result.getPhones().forEach(phone -> {
            assertThat(phone.getId()).isNotNull();
            assertThat(phone.getOwner()).isEqualTo(result);
        });
    }

    @Test
    public void shouldCreateEntityWithManyToOneOneToManyAnnotation() {
        // when
        Phone result = dataGenerator.generateObject(Phone.class);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isNotNull();
        assertThat(result.getOwner()).isNotNull();
        assertThat(result.getOwner()).isNotNull();
        assertThat(result.getOwner().getId()).isNotNull();
        assertThat(result.getOwner().getPhones()).isNotNull().hasSize(1);
        assertThat(result.getOwner().getPhones().get(0)).isEqualTo(result);
    }
}