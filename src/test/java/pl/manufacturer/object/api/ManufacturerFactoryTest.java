package pl.manufacturer.object.api;

import org.junit.Test;
import pl.manufacturer.object.example.extended.ExtendedArrayOfStringsObject;
import pl.manufacturer.object.example.extended.ExtendedBooleanStringObject;
import pl.manufacturer.object.example.extended.ExtendedListOfStringsObject;
import pl.manufacturer.object.example.extended.ExtendedSetOfStringsObject;
import pl.manufacturer.object.example.simple.*;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class ManufacturerFactoryTest {

    private ManufacturerFactory manufacturerFactory = new ManufacturerFactory();

    @Test
    public void shouldGenerateExampleSimpleStringObject() {
        // when
        SimpleStringObject object = manufacturerFactory.generatePojo(SimpleStringObject.class);

        // then
        assertThat(object).isNotNull();
        assertThat(object.getStringObject()).isNotNull().isNotEmpty();
    }

    @Test
    public void shouldGenerateExampleSimpleBooleanObject() {
        // when
        SimpleBooleanObject object = manufacturerFactory.generatePojo(SimpleBooleanObject.class);

        // then
        assertThat(object).isNotNull();
        assertThat(object.getBooleanObject()).isNotNull();
        assertThat(object.isBooleanPrimitive()).isNotNull();
    }

    @Test
    public void shouldGenerateExampleSimpleIntegerObject() {
        // when
        SimpleIntegerObject object = manufacturerFactory.generatePojo(SimpleIntegerObject.class);

        // then
        assertThat(object).isNotNull();
        assertThat(object.getIntegerObject()).isNotNull();
        assertThat(object.getIntegerPrimitive()).isNotNull();
    }

    @Test
    public void shouldGenerateExampleSimpleDoubleObject() {
        // when
        SimpleDoubleObject object = manufacturerFactory.generatePojo(SimpleDoubleObject.class);

        // then
        assertThat(object).isNotNull();
        assertThat(object.getDoubleObject()).isNotNull();
        assertThat(object.getDoublePrimitive()).isNotNull();
    }

    @Test
    public void shouldGenerateExampleSimpleFloatObject() {
        // when
        SimpleFloatObject object = manufacturerFactory.generatePojo(SimpleFloatObject.class);

        // then
        assertThat(object).isNotNull();
        assertThat(object.getFloatObject()).isNotNull();
        assertThat(object.getFloatPrimitive()).isNotNull();
    }

    @Test
    public void shouldGenerateExampleSimpleLongObject() {
        // when
        SimpleLongObject object = manufacturerFactory.generatePojo(SimpleLongObject.class);

        // then
        assertThat(object).isNotNull();
        assertThat(object.getLongObject()).isNotNull();
        assertThat(object.getLongPrimitive()).isNotNull();
    }

    @Test
    public void shouldGenerateExampleSimpleCharacterObject() {
        // when
        SimpleCharacterObject object = manufacturerFactory.generatePojo(SimpleCharacterObject.class);

        // then
        assertThat(object).isNotNull();
        assertThat(object.getCharacterObject()).isNotNull();
        assertThat(object.getCharacterPrimitive()).isNotNull();
    }

    @Test
    public void shouldGenerateExampleSimpleByteObject() {
        // when
        SimpleByteObject object = manufacturerFactory.generatePojo(SimpleByteObject.class);

        // then
        assertThat(object).isNotNull();
        assertThat(object.getByteObject()).isNotNull();
        assertThat(object.getBytePrimitive()).isNotNull();
    }

    @Test
    public void shouldGenerateExampleSimpleShortObject() {
        // when
        SimpleShortObject object = manufacturerFactory.generatePojo(SimpleShortObject.class);

        // then
        assertThat(object).isNotNull();
        assertThat(object.getShortObject()).isNotNull();
        assertThat(object.getShortPrimitive()).isNotNull();
    }

    @Test
    public void shouldGenerateExtendedObject() {
        // when
        ExtendedBooleanStringObject object = manufacturerFactory.generatePojo(ExtendedBooleanStringObject.class);

        // then
        assertThat(object).isNotNull();
        assertThat(object.getSimpleBoolean()).isNotNull();
        assertThat(object.getSimpleString()).isNotNull();

        assertThat(object.getSimpleBooleanObject()).isNotNull();
        assertThat(object.getSimpleBooleanObject().getBooleanObject()).isNotNull();
        assertThat(object.getSimpleBooleanObject().isBooleanPrimitive()).isNotNull();

        assertThat(object.getSimpleStringObject()).isNotNull();
        assertThat(object.getSimpleStringObject().getStringObject()).isNotNull().isNotEmpty();
    }

    @Test
    public void shouldGenerateExtendedListOfSimpleObjects() {
        // when
        ExtendedListOfStringsObject object = manufacturerFactory.generatePojo(ExtendedListOfStringsObject.class);

        // then
        assertThat(object).isNotNull();
        assertThat(object.getStringObjects()).isNotNull().hasSize(2);
        object.getStringObjects().forEach(o -> assertThat(o).isNotNull().isNotEmpty());
    }

    @Test
    public void shouldGenerateExtendedSetOfSimpleObjects() {
        // when
        ExtendedSetOfStringsObject object = manufacturerFactory.generatePojo(ExtendedSetOfStringsObject.class);

        // then
        assertThat(object).isNotNull();
        assertThat(object.getStringObjects()).isNotNull().hasSize(2);
        object.getStringObjects().forEach(o -> assertThat(o).isNotNull().isNotEmpty());
    }

    @Test
    public void shouldGenerateExtendedArrayOfStringsObject() {
        // when
        ExtendedArrayOfStringsObject object = manufacturerFactory.generatePojo(ExtendedArrayOfStringsObject.class);

        // then
        assertThat(object).isNotNull();
        assertThat(object.getArray()).isNotNull().hasSize(2);
        for(int i = 0; i< object.getArray().length; i++) {
            assertThat(object.getArray()[i]).isNotNull().isNotEmpty();
        }
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowRuntimeExceptionWhileInstantiatingClassExceptionOccured() {
        // when
        manufacturerFactory.generatePojo(LocalDateTime.class);
    }

    @Test
    public void checkPerformance() {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            manufacturerFactory.generatePojo(ExtendedBooleanStringObject.class);
        }
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println(elapsedTime);
    }
}
